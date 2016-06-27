package com.sck.utility.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KINCERS on 8/31/2015.
 */
public class RsqlSpecification<T> implements Specification<T> {

    private String property;
    private boolean propertyConverted = false;
    private String[] entities;
    private boolean hasNestedEntities = false;
    private ComparisonOperator operator;
    private List<String> arguments;

    public RsqlSpecification(String property, ComparisonOperator operator, List<String> arguments) {
        super();
        String[] propertySplit = property.split("\\.");
        if(propertySplit.length > 1) {
            this.hasNestedEntities = true;
            this.entities = propertySplit;
            this.property = propertySplit[propertySplit.length-1];
        }else {
            this.property = property;
        }
        this.operator = operator;
        this.arguments = arguments;

    }


    //TODO- Fix this sloppy mess
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Object> args;
        try {
            args = castArguments(root);
        }catch (TurtleShellLampException e) {
            System.out.println(e.getMessage());
            return null;
        }

        Object argument = args.get(0);
        switch (RsqlSearchOperation.getSimpleOperator(operator)) {

            case EQUAL: {
                if (argument instanceof String) {
                    if(((String) argument).contains("*")) {
                        // LIKE
                        if(hasNestedEntities) {
                            return builder.like(this.getNested(entities, root).get(property), argument.toString().replace('*', '%'));
                        }else {
                            return builder.like(root.<String> get(property), argument.toString().replace('*', '%'));
                        }
                    }else {
                        // EQUALS
                        if(hasNestedEntities) {
                            return builder.equal(this.getNested(entities, root).get(property), argument);
                        }else {
                            return builder.equal(root.get(property), argument);
                        }
                    }
                } else if (argument == null) {
                    if(hasNestedEntities) {
                        return builder.isNull(this.getNested(entities, root).get(property));
                    }else {
                        return builder.isNull(root.get(property));
                    }
                } else {
                    if(hasNestedEntities) {
                        return builder.equal(this.getNested(entities, root).get(property), argument);
                    }else {
                        return builder.equal(root.get(property), argument);
                    }
                }
            }
            case NOT_EQUAL: {
                if (argument instanceof String) {
                    if(((String) argument).contains("*")){
                        if(hasNestedEntities) {
                            return builder.notLike(this.getNested(entities, root).get(property), argument.toString().replace('*', '%'));
                        }else {
                            return builder.notLike(root.<String> get(property), argument.toString().replace('*', '%'));
                        }
                    }else {
                        if(hasNestedEntities) {
                            return builder.notEqual(this.getNested(entities, root).get(property), argument);
                        }else {
                            return builder.notEqual(root.get(property), argument);
                        }
                    }
                } else if (argument == null) {
                    if(hasNestedEntities){
                        return builder.isNotNull(this.getNested(entities, root).get(property));
                    }else {
                        return builder.isNotNull(root.get(property));
                    }
                } else {
                    if(hasNestedEntities) {
                        return builder.notEqual(this.getNested(entities, root).get(property), argument);
                    }else {
                        return builder.notEqual(root.get(property), argument);
                    }
                }
            }
            case GREATER_THAN: {
                if(hasNestedEntities) {
                    return builder.greaterThan(this.getNested(entities, root).get(property), argument.toString());
                }else {
                    return builder.greaterThan(root.<String> get(property), argument.toString());
                }
            }
            case GREATER_THAN_OR_EQUAL: {
                if(hasNestedEntities) {
                    return builder.greaterThanOrEqualTo(this.getNested(entities, root).get(property), argument.toString());
                }else {
                    return builder.greaterThanOrEqualTo(root.<String> get(property), argument.toString());
                }
            }
            case LESS_THAN: {
                if(hasNestedEntities) {
                    return builder.lessThan(this.getNested(entities, root).get(property), argument.toString());
                }else {
                    return builder.lessThan(root.<String> get(property), argument.toString());
                }
            }
            case LESS_THAN_OR_EQUAL: {
                if(hasNestedEntities) {
                    return builder.lessThanOrEqualTo(this.getNested(entities, root).get(property), argument.toString());
                }else {
                    return builder.lessThanOrEqualTo(root.<String> get(property), argument.toString());
                }
            }
            case IN:
                if(hasNestedEntities) {
                    return this.getNested(entities, root).get(property).in(args);
                }else {
                    return root.get(property).in(args);
                }
            case NOT_IN:
                if(hasNestedEntities) {
                    return builder.not(this.getNested(entities, root).get(property).in(args));
                }else {
                    return builder.not(root.get(property).in(args));
                }
        }

        return null;
    }


    private Path getNested(String[] properties, Root<T> root) {

        Path currentPath = root.get(properties[0]);
        for(int i=0;i<(properties.length-1);i++) {
            currentPath = root.get(properties[i]);
        }
        return currentPath;
    }

    private void convertToProperCase(Class entityClass) {
        for(Field field : entityClass.getDeclaredFields()) {
            if(property.equalsIgnoreCase(field.getName())) {
                property = field.getName();
                return;
            }
        }
    }

    private List<Object> castArguments(Root<T> root) throws TurtleShellLampException {

        List<Object> args = new ArrayList<>();

        if(!propertyConverted) {
            convertToProperCase(root.getJavaType());
            propertyConverted = true;
        }

        Class<? extends Object> type;
        // One value, property of this entity
        if(hasNestedEntities) {
            Path path = getNested(entities, root);

            Path propertyPath;

            try {
                 propertyPath = path.get(property);
            }catch (IllegalStateException e) {
                throw new TurtleShellLampException("Cannot currently do a nested search on a one to many relationship");
            }
            type = propertyPath.getJavaType();
        }else {

            Path path = root.get(property);

            type = path.getJavaType();

        }

        for (String argument : arguments) {
            if (type.equals(Integer.class)) {
                args.add(Integer.parseInt(argument));
            } else if (type.equals(Long.class)) {
                args.add(Long.parseLong(argument));
            } else {
                args.add(argument);
            }
        }

        return args;
    }
}

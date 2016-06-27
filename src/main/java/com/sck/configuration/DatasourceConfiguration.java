package com.sck.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Created by Steve on 11/1/2015.
 */
@Configuration
@PropertySource({ "classpath:datasource.properties" })
@EnableJpaRepositories(
        basePackages = "com.sck.repository",
        entityManagerFactoryRef = "PrimaryEntityManagerFactory",
        transactionManagerRef = "PrimaryTransactionManager"
)
public class DatasourceConfiguration {

    @Autowired
    private Environment env;

    @Bean(name = "PrimaryDatasource")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(env.getProperty("primary.url"));
        config.setUsername(env.getProperty("primary.username"));
        config.setPassword(env.getProperty("primary.password"));

        config.setDriverClassName(env.getProperty("primary.driverClassName"));

        config.setPoolName("PrimaryPool");

        config.setMaximumPoolSize(4);
        config.setMaxLifetime(28700000L); // Just under 8 hrs

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "false");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("zeroDateTimeBehavior", "convertToNull");

        return new HikariDataSource(config);
    }

    @Bean(name = "PrimaryEntityManager")
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean(name = "PrimaryEntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource());
        em.setPackagesToScan("com.sck.domain");
        em.setPersistenceUnitName("PrimaryPersistanceUnit");


        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(Boolean.getBoolean(env.getProperty("primary.showSql")));
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();

        properties.put("hibernate.hbm2ddl.auto", env.getProperty("primary.hbm2ddl.auto"));
        properties.put("hibernate.enable_lazy_load_no_trans", "true");

        //properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));

        em.setJpaPropertyMap(properties);

        em.afterPropertiesSet();

        return em.getObject();
    }

    @Bean(name = "PrimaryTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
}

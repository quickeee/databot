package com.sck.utility.web;

import com.sck.domain.RestInitializable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.*;

/**
 * Created by KINCERS on 8/7/2015.
 */
public class ControllerUtility {

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());

            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }else if(srcValue.getClass().getSimpleName().equals("Integer")) {
                if(srcValue.equals(0)) {
                    src.setPropertyValue(pd.getName(), null);
                }
            }else if(srcValue.getClass().getSimpleName().equals("Long")) {
                if(srcValue.equals(0L)) {
                    src.setPropertyValue(pd.getName(), null);
                }
            }else if(srcValue.getClass().getSimpleName().equals("String")) {
                if(srcValue.equals("0")) {
                    src.setPropertyValue(pd.getName(), null);
                }
            }else if(srcValue.getClass().getSimpleName().equals("Date")) {
                if(((Date)srcValue).getTime() == -62170138800000L) {
                    src.setPropertyValue(pd.getName(), null);
                }
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    // then use Spring BeanUtils to copy and ignore null
    public static void copyProperties(Object src, Object target, boolean ignoreNull) {
        if(ignoreNull) {
            BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
        }else {
            BeanUtils.copyProperties(src, target);
        }
    }
}

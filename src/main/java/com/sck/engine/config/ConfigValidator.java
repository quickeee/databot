package com.sck.engine.config;


import com.sck.domain.JobConfig;

/**
 * Created by KINCERS on 7/13/2015.
 */
public class ConfigValidator {

    public ConfigValidator() {

    }


    public boolean validate(String type) {

        return false;
    }

    private int findConfigIndex(String type, String configType, String name) {

        /*
        int listSize = jobConfigList.size();
        for(int i=0;i<listSize;i++) {
            JobConfig jobConfig = jobConfigList.get(i);
            if(
                jobConfig.getJobConfigItem().getType().equalsIgnoreCase(type) &&
                jobConfig.getJobConfigItem().getConfigType().equalsIgnoreCase(configType) &&
                jobConfig.getJobConfigItem().getVariableName().equalsIgnoreCase(name)
            ) {
                return i;
            }
        }
        */
        return -1;
    }

}

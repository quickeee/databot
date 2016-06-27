package com.sck.engine.config;


import com.sck.domain.JobConfig;
import com.sck.engine.config.file.ConfigFetchFile;
import com.sck.engine.config.file.ConfigFile;
import com.sck.engine.config.file.imp.ConfigFileFTP;
import com.sck.engine.config.file.imp.ConfigFileLocal;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by KINCERS on 4/21/2015.
 */
@Service
public class ConfigBuilder {

    List<JobConfig> jobConfigList;

    public void init(List<JobConfig> jobConfigList) {
        this.jobConfigList = jobConfigList;
    }

    private int findConfigIndex(String type, String configType, String name) {

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
        return -1;
    }


    private boolean findAndSetVariables(String type, String configType, Object configObject) throws IllegalAccessException, InvocationTargetException {

        Method[] methods = configObject.getClass().getDeclaredMethods();

        for(Method method : methods) {
            // Loop thru setters
            String methodName = method.getName();
            if(methodName.startsWith("set")) {
                // See if we have a value being passed in
                String varName = methodName.replaceFirst("set","");
                System.out.println(varName);

                int index = findConfigIndex(type, configType, varName);
                if(index < 0) {
                    System.out.println("No index match, var not passed");
                }else {
                    System.out.println("Match! "+varName+" passed at "+index);
                    if(jobConfigList.get(index).getJobConfigItem().getDataType().equalsIgnoreCase("Integer")) {
                        method.invoke(configObject, Integer.valueOf(jobConfigList.get(index).getValue()));

                    }else if(jobConfigList.get(index).getJobConfigItem().getDataType().equalsIgnoreCase("Boolean")) {
                        method.invoke(configObject, Boolean.valueOf(jobConfigList.get(index).getValue()));

                    }else {
                        method.invoke(configObject, jobConfigList.get(index).getValue());

                    }
                }

            }

        }
        return false;
    }


    private boolean areRequiredValuesPresent(String type) {
        if(type.equalsIgnoreCase("FILE")) {
            for(String val : ConfigValue.FILE_REQUIRED_VALUES) {
                if(findConfigIndex(ConfigValue.FILE_TYPE, ConfigValue.FILE_CONFIG_TYPE, val) < 0) {
                    return false;
                }
            }
        }else if(type.equalsIgnoreCase("DATABASE")) {

            return false;

        }else if(type.equalsIgnoreCase("WEBSERVICE")) {

            return false;

        }else {
            return false;
        }
        return true;
    }


    // FILE CONFIGS
    public ConfigFile buildFile() throws IllegalAccessException, InvocationTargetException {
        System.out.println("Building file");

        ConfigFile configFile = new ConfigFile();

        areRequiredValuesPresent("FILE");

        findAndSetVariables("FILE", "File", configFile);

        return configFile;
    }

    public ConfigFetchFile buildFileFetch() throws IllegalAccessException, InvocationTargetException {
        ConfigFetchFile configFetchFile = new ConfigFetchFile();

        //configFetchFile.setDirectory("C:\\com");

        findAndSetVariables("FILE", "FileFetch", configFetchFile);

        return configFetchFile;
    }

    public ConfigFileLocal buildFileLocal() {
        ConfigFileLocal configFileLocal = new ConfigFileLocal();

        configFileLocal.setUsername(null);

        return configFileLocal;
    }

    public ConfigFileFTP buildFileFTP() {
        ConfigFileFTP configFileFTP = new ConfigFileFTP();

        configFileFTP.setActiveMode(false);

        return configFileFTP;
    }


    // GENERAL CONFIGS
    public ConversionConfig buildConversion() throws IllegalAccessException, InvocationTargetException {
        System.out.println("Building conversion");
        ConversionConfig conversionConfig = new ConversionConfig();

        try {
            findAndSetVariables("CONVERSION", "Conversion", conversionConfig);

        }catch (Exception e) {
            e.printStackTrace();
        }

        return conversionConfig;
    }
/*
    public TranslationConfig buildTranslation() throws IllegalAccessException, InvocationTargetException {
        System.out.println("Building translation");
        TranslationConfig translationConfig = new TranslationConfig();

        try {
            findAndSetVariables("TRANSLATION", "Translation", translationConfig);

        }catch (Exception e) {
            e.printStackTrace();
        }

        return translationConfig;
    }
*/
}

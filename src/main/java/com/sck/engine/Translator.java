package com.sck.engine;

import com.sck.engine.config.ConfigBuilder;
import com.sck.engine.model.data.DataGroup;

/**
 * Created by KINCERS on 4/21/2015.
 */
public class Translator {

    ActivityLog activityLog;
    ConfigBuilder configBuilder;

    public Translator(ActivityLog activityLog, ConfigBuilder configBuilder) {
        this.activityLog = activityLog;
        this.configBuilder = configBuilder;
    }

    public DataGroup doTranslate(DataGroup dataGroup) {

        //Trim

        //Validate

        //Calculate

        //Validate

        //Return Result

        return dataGroup;
    }
}

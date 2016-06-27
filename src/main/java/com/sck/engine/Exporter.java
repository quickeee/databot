package com.sck.engine;

import com.sck.engine.config.ConfigBuilder;
import com.sck.engine.model.data.DataGroup;

/**
 * Created by KINCERS on 4/21/2015.
 */
public class Exporter {

    ActivityLog activityLog;
    ConfigBuilder configBuilder;

    public Exporter(ActivityLog activityLog, ConfigBuilder configBuilder) {
        this.activityLog = activityLog;
        this.configBuilder = configBuilder;
    }


    public void doExport(DataGroup dataGroup) {

    }

}

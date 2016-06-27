package com.sck.engine;

import com.sck.domain.Job;
import com.sck.engine.config.ConfigBuilder;
import com.sck.engine.model.data.DataField;
import com.sck.engine.model.data.DataGroup;
import com.sck.engine.model.data.DataItem;
import com.sck.engine.model.data.DataRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Created by KINCERS on 12/2/2015.
 */
@Service
public class DataEngine {

    @Autowired
    private ConfigBuilder configBuilder;

    public void runJob(Job job) {

        configBuilder.init(job.getJobConfigs());

        DataGroup dataGroup;


        //<- ActivityLog, ConfigBuilder
        // TODO Pre-processing
        // PRE-PROCESS
        //-> Void


        //<- ActivityLog, ConfigBuilder
        // TODO Import data
        String sourceType;
        if(job.getSource() != null) {
            sourceType = job.getSource().getType();
        }else {
            return;
        }
        if(sourceType == null) {
            return;
        }
        Importer importer = new Importer();
        System.out.println("Doing import");
        try {
            dataGroup = importer.doImport(sourceType, configBuilder);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        for(DataItem dataItem : dataGroup.getDataItems()) {
            System.out.println(dataItem.getFileNameOriginal());
            System.out.println(dataItem.getFileNameSaved());
            for(DataRow dataRow : dataItem.getDataRows()) {
                for(DataField dataField : dataRow.getFields()) {
                    System.out.print(dataField.getContent());
                    System.out.print(", ");
                }
                System.out.println();
            }
        }

        //-> DataGroup


        //<- ActivityLog, ConfigBuilder, DataGroup
        // TODO Translate / Validate data
        //Translator translator = new Translator(activityLog, configBuilder);
        //dataGroup = translator.doTranslate(dataGroup);
        //-> DataGroup


        //<- ActivityLog, ConfigBuilder, DataGroup
        // TODO Export data
        //Exporter exporter = new Exporter(activityLog, configBuilder);
        //exporter.doExport(dataGroup);
        //-> Void


        //<- ActivityLog, ConfigBuilder
        // TODO Post-processing
        // POST-PROCESS
        //-> Void


        //<- ActivityLog, ConfigBuilder
        // TODO Clean-up
        // CLEANUP
        //-> Void

    }

}

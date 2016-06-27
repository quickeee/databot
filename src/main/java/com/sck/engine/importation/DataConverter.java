package com.sck.engine.importation;

import com.sck.engine.config.ConfigBuilder;
import com.sck.engine.config.ConversionConfig;
import com.sck.engine.config.constant.DataTypes;
import com.sck.engine.importation.convert.*;
import com.sck.engine.model.data.DataGroup;
import com.sck.engine.model.data.DataItem;
import com.sck.engine.model.data.DataRow;
import com.sck.engine.model.fetch.FetchedItem;
import com.sck.engine.utility.DirectoryFile;
import org.apache.poi.hssf.OldExcelFormatException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KINCERS on 4/20/2015.
 */
public class DataConverter {

    public DataGroup convert(List<FetchedItem> fetchedItems, ConfigBuilder configBuilder) throws Exception {

        ConversionConfig conversionConfig = configBuilder.buildConversion();
        DataGroup dataGroup = new DataGroup();
        List<DataItem> dataItems = new ArrayList<>();

        System.out.println(fetchedItems.size());

        for (FetchedItem item : fetchedItems) {

            System.out.println(item.isSuccessful());
            System.out.println(item.getErrorMessage());
            System.out.println(item.getSourceValue());
            System.out.println(item.getSavedFile().getFileName());

            if(item.isSuccessful()) {

                DataItem dataItem = new DataItem();
                DirectoryFile savedFile = item.getSavedFile();
                String fileType = savedFile.getFileType();
                String configuredFileType = conversionConfig.getFileType();

                List<DataRow> dataRowList;

                if(DataTypes.EXCEL.contains(fileType)) {

                    XLSXConverter xlsxConverter = new XLSXConverter(conversionConfig);

                    dataRowList = xlsxConverter.convert(savedFile);

                }else if(DataTypes.EXCEL_LEGACY.contains(fileType)) {

                    XLSConverter xlsConverter = new XLSConverter(conversionConfig);
                    try {
                        dataRowList = xlsConverter.convert(savedFile);
                    } catch (OldExcelFormatException e) {
                        System.out.println("Old Excel file. Using alternate parser.");
                        XLS95Converter xls95Converter = new XLS95Converter(conversionConfig);
                        dataRowList = xls95Converter.convert(savedFile);
                    }

                }else if(DataTypes.FLATFILE.contains(fileType)) {

                    // Are we overridden as a fixed width?
                    if(configuredFileType.equalsIgnoreCase("FIXED_WIDTH")) {
                        // Expecting columns to be properly set with start stop positions
                        FixedWidthConverter fixedWidthConverter = new FixedWidthConverter(conversionConfig);
                        dataRowList = fixedWidthConverter.convert(savedFile);

                    }else {
                        // Try to autodetect the delimiter and fall back to manual
                        DelimitedConverter delimitedConverter = new DelimitedConverter(conversionConfig);
                        dataRowList = delimitedConverter.convert(savedFile);

                    }


                }else if(DataTypes.JSON.contains(fileType)) {

                    throw new Exception("Unsupported file type of: "+fileType);

                }else if(DataTypes.XML.contains(fileType)) {

                    throw new Exception("Unsupported file type of: "+fileType);

                }else if(DataTypes.HTML.contains(fileType)) {

                    throw new Exception("Unsupported file type of: "+fileType);

                }else {
                    throw new Exception("Unsupported file type of: "+fileType);
                }

                dataItem.setDataRows(dataRowList);
                dataItem.setFileNameOriginal(item.getSourceValue());
                dataItem.setFileNameSaved(item.getSavedFile().getFileName());
                dataItems.add(dataItem);
            }


        }


        dataGroup.setDataItems(dataItems);

        return dataGroup;
    }



}

package com.sck.engine.importation.convert;

import com.sck.engine.config.ConversionConfig;
import com.sck.engine.model.data.DataRow;
import com.sck.engine.utility.DirectoryFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KINCERS on 4/21/2015.
 */
public class FixedWidthConverter {

    ConversionConfig conversionConfig;

    public FixedWidthConverter(ConversionConfig conversionConfig) {
        this.conversionConfig = conversionConfig;
    }

    public List<DataRow> convert(DirectoryFile file) {

        List<DataRow> dataRowList = new ArrayList<>();
        return dataRowList;
    }
}

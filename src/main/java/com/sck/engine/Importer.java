package com.sck.engine;

import com.sck.engine.config.ConfigBuilder;
import com.sck.engine.importation.DataConverter;
import com.sck.engine.importation.DataFetcher;
import com.sck.engine.model.data.DataGroup;
import com.sck.engine.model.fetch.FetchedItem;

import java.util.List;

/**
 * Created by KINCERS on 4/21/2015.
 */
public class Importer {

    public DataGroup doImport(String sourceType, ConfigBuilder configBuilder) throws Exception{

        DataGroup dataGroup;

        System.out.println("Fetching data");
        // Raw data to local text file to be read in
        DataFetcher dataFetcher = new DataFetcher();
        List<FetchedItem> fetchedItems;
        fetchedItems = dataFetcher.fetchData(sourceType, configBuilder);

        System.out.println("Converting data");
        // Read in text file and convert to custom object
        DataConverter dataConverter = new DataConverter();
        dataGroup = dataConverter.convert(fetchedItems, configBuilder);

        return dataGroup;
    }
}

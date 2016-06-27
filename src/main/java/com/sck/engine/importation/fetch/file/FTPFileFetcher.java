package com.sck.engine.importation.fetch.file;

import com.sck.engine.config.file.ConfigFetchFile;
import com.sck.engine.config.file.imp.ConfigFileFTP;
import com.sck.engine.importation.fetch.FetchInterface;
import com.sck.engine.model.fetch.FetchedItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KINCERS on 4/21/2015.
 */
public class FTPFileFetcher implements FetchInterface {

    private ConfigFileFTP ftpConfig;
    private ConfigFetchFile fetchConfig;

    public FTPFileFetcher(ConfigFetchFile fetchConfig, ConfigFileFTP ftpConfig) {
        this.ftpConfig = ftpConfig;
        this.fetchConfig = fetchConfig;
    }

    public List<FetchedItem> fetchItems() {
        List<FetchedItem> fetchedItems = new ArrayList<>();


        return fetchedItems;
    }



}

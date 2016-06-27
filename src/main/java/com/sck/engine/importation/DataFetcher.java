package com.sck.engine.importation;

import com.sck.engine.config.ConfigBuilder;
import com.sck.engine.config.file.ConfigFetchFile;
import com.sck.engine.config.file.ConfigFile;
import com.sck.engine.config.file.imp.ConfigFileFTP;
import com.sck.engine.config.file.imp.ConfigFileLocal;
import com.sck.engine.importation.fetch.file.FTPFileFetcher;
import com.sck.engine.importation.fetch.file.LocalFileFetcher;
import com.sck.engine.model.fetch.FetchedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by KINCERS on 4/21/2015.
 */
public class DataFetcher {

    private static final Logger LOGGER = Logger.getLogger(DataFetcher.class.getName());

    public List<FetchedItem> fetchData(String sourceType, ConfigBuilder configBuilder) throws Exception {
        List<FetchedItem> fetchedItems = new ArrayList<>();

        // Fetching from files
        if(sourceType.equalsIgnoreCase("FILE")) {

            LOGGER.log(Level.INFO, "Fetching type FILE");

            ConfigFile config = configBuilder.buildFile();

            if(config == null) {
                System.out.println("File Config is null");
            }

            ConfigFetchFile fetchConfig = configBuilder.buildFileFetch();

            if(config == null) {
                System.out.println("Fetch Config is null");
            }

            String locationType = config.getLocationType();
            LOGGER.log(Level.INFO, locationType);

            if (locationType.equalsIgnoreCase("LOCAL")) {
                LOGGER.log(Level.INFO, "Fetching type LOCAL");

                ConfigFileLocal localConfig = configBuilder.buildFileLocal();

                LocalFileFetcher localFileFetcher = new LocalFileFetcher(fetchConfig, localConfig);

                fetchedItems =  localFileFetcher.fetchItems();

                System.out.println("llfetch count " + fetchedItems.size());

            } else if (locationType.equalsIgnoreCase("FTP")) {
                LOGGER.log(Level.INFO, "Fetching type FTP");

                ConfigFileFTP ftpConfig = configBuilder.buildFileFTP();

                FTPFileFetcher ftpFileFetcher = new FTPFileFetcher(fetchConfig, ftpConfig);

                fetchedItems = ftpFileFetcher.fetchItems();

            } else {

                throw new Exception("Unsupported location type of: " + locationType);

            }

        }else if(sourceType.equalsIgnoreCase("DATABASE")) {




        }else {

            throw new Exception("Unsupported source type of: "+ sourceType);

        }

        return fetchedItems;
    }

}

package com.sck.engine.importation.fetch.file;

import com.sck.engine.config.file.ConfigFetchFile;
import com.sck.engine.config.file.imp.ConfigFileLocal;
import com.sck.engine.importation.fetch.FetchInterface;
import com.sck.engine.model.fetch.FetchedItem;
import com.sck.engine.utility.DirectoryFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by KINCERS on 4/21/2015.
 */
public class LocalFileFetcher implements FetchInterface {

    private ConfigFileLocal localConfig;
    private ConfigFetchFile fetchConfig;

    public LocalFileFetcher(ConfigFetchFile fetchConfig, ConfigFileLocal localConfig) {
        this.localConfig = localConfig;
        this.fetchConfig = fetchConfig;
    }

    public List<FetchedItem> fetchItems() {
        List<FetchedItem> fetchedItems = new ArrayList<>();

        System.out.println(fetchConfig.getDirectory());

        DirectoryFile directoryFile = new DirectoryFile(fetchConfig.getDirectory());

        List<DirectoryFile> filesFound = directoryFile.listFiles(
                fetchConfig.getSortDirection(),
                fetchConfig.getIncludeGlob(),
                fetchConfig.getExcludeGlob(),
                fetchConfig.getFetchLimit()
        );

        if(filesFound.size() < 1) {
            System.out.println("No files found!");
        }

        for(DirectoryFile file : filesFound) {
            FetchedItem fetchedItem = new FetchedItem();

            fetchedItem.setSourceValue(file.getFilePath().toString());

            System.out.println(file.getFilePath().toString());

            DirectoryFile savedFile;
            try {
                savedFile = saveFile(file);
            }catch (IOException e) {
                // Record a failed fetch and continue
                System.out.println("Failed to copy file to save dir!");
                fetchedItem.setErrorMessage("Failed to copy file to save dir!");

                continue;
            }

            fetchedItem.setSavedFile(savedFile);
            fetchedItem.setSuccessful(true);
            fetchedItems.add(fetchedItem);
        }

        return fetchedItems;
    }

    private DirectoryFile saveFile(DirectoryFile file) throws IOException {

        String uniqueId = UUID.randomUUID().toString();

        String savePath = fetchConfig.getSavePath() + uniqueId + "." + file.getExtension();

        System.out.println(savePath);

        if(!file.copy(savePath)) {
            System.out.println("Failed to copy file");
        }

        return new DirectoryFile(savePath);
    }

}

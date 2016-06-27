package com.sck.engine.model.fetch;


import com.sck.engine.utility.DirectoryFile;

/**
 * Created by KINCERS on 4/22/2015.
 */
public class FetchedItem {

    private String sourceValue = "unknown";
    private DirectoryFile savedFile;
    private boolean successful = false;
    private String errorMessage = "No Error Message";

    public String getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(String sourceValue) {
        this.sourceValue = sourceValue;
    }

    public DirectoryFile getSavedFile() {
        return savedFile;
    }

    public void setSavedFile(DirectoryFile savedFile) {
        this.savedFile = savedFile;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

package com.sck.engine.model.data;

import java.util.List;

/**
 * Created by KINCERS on 4/6/2015.
 */
public class DataItem {

    private String fileNameOriginal;
    private String fileNameSaved;
    private List<DataRow> dataRows;

    public String getFileNameOriginal() {
        return fileNameOriginal;
    }

    public void setFileNameOriginal(String fileNameOriginal) {
        this.fileNameOriginal = fileNameOriginal;
    }

    public String getFileNameSaved() {
        return fileNameSaved;
    }

    public void setFileNameSaved(String fileNameSaved) {
        this.fileNameSaved = fileNameSaved;
    }

    public List<DataRow> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<DataRow> dataRows) {
        this.dataRows = dataRows;
    }
}

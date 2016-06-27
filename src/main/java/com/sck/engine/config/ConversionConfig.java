package com.sck.engine.config;

/**
 * Created by KINCERS on 4/23/2015.
 */
public class ConversionConfig {

    private String fileType = "";
    private Boolean getAllSheets = false;
    private Integer sheetIndex = 0;
    private String decrypt;


    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Boolean getGetAllSheets() {
        return getAllSheets;
    }

    public void setGetAllSheets(Boolean getAllSheets) {
        this.getAllSheets = getAllSheets;
    }

    public Integer getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getDecrypt() {
        return decrypt;
    }

    public void setDecrypt(String decrypt) {
        this.decrypt = decrypt;
    }

}

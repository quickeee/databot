package com.sck.engine.config.file;

/**
 * Created by KINCERS on 4/21/2015.
 */
public class ConfigFetchFile {

    private String sortDirection = "DESC";
    private String sortType = "modified";
    private String includeGlob = null;
    private String excludeGlob = null;
    private Integer fetchLimit = -1;
    private Boolean catchDuplicates = false;
    private String directory = "/";
    private String savePath = "\\SavePath\\";

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getIncludeGlob() {
        return includeGlob;
    }

    public void setIncludeGlob(String includeGlob) {
        this.includeGlob = includeGlob;
    }

    public String getExcludeGlob() {
        return excludeGlob;
    }

    public void setExcludeGlob(String excludeGlob) {
        this.excludeGlob = excludeGlob;
    }

    public Integer getFetchLimit() {
        return fetchLimit;
    }

    public void setFetchLimit(Integer fetchLimit) {
        this.fetchLimit = fetchLimit;
    }

    public Boolean isCatchDuplicates() {
        return catchDuplicates;
    }

    public void setCatchDuplicates(Boolean catchDuplicates) {
        this.catchDuplicates = catchDuplicates;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}

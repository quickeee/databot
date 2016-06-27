package com.sck.engine.config.translator;

/**
 * Created by KINCERS on 12/11/2015.
 */
public class InboundValidatorConfig {

    private Boolean trackColumns = true;
    private Boolean hasHeader = true;
    private Integer rowStart = 0;
    private Integer rowStop = -1;

    public Boolean getTrackColumns() {
        return trackColumns;
    }

    public void setTrackColumns(Boolean trackColumns) {
        this.trackColumns = trackColumns;
    }

    public Boolean getHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(Boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public Integer getRowStart() {
        return rowStart;
    }

    public void setRowStart(Integer rowStart) {
        this.rowStart = rowStart;
    }

    public Integer getRowStop() {
        return rowStop;
    }

    public void setRowStop(Integer rowStop) {
        this.rowStop = rowStop;
    }
}

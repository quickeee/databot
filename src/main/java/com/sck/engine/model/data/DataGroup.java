package com.sck.engine.model.data;

import java.util.List;

/**
 * Created by KINCERS on 4/6/2015.
 */
public class DataGroup {

    private String type;
    private List<DataItem> dataItems;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DataItem> getDataItems() {
        return dataItems;
    }

    public void setDataItems(List<DataItem> dataItems) {
        this.dataItems = dataItems;
    }
}

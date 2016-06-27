package com.sck.engine.model.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KINCERS on 4/6/2015.
 */
public class DataRow {

    private String parentName;

    private List<DataField> fields = new ArrayList<>();

    public List<DataField> getFields() {
        return fields;
    }

    public void setFields(List<DataField> fields) {
        this.fields = fields;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}

package com.sck.engine.model.data;

/**
 * Created by KINCERS on 4/6/2015.
 */
public class DataField<T> {

    private String contentFormula;
    private boolean empty;
    private T content;

    public String getContentFormula() {
        return contentFormula;
    }

    public void setContentFormula(String contentFormula) {
        this.contentFormula = contentFormula;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}

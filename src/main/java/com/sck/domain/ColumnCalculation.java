package com.sck.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by KINCERS on 4/27/2015.
 */
@Entity
@Table(name = "column_calculation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ColumnCalculation implements Serializable, RestInitializable {

    @Id @GeneratedValue
    private Long id;

    @Generated(GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/New_York")
    @Column(updatable = false)
    private Date createdAt;

    @Generated(GenerationTime.ALWAYS)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/New_York")
    @Column(updatable = false)
    private Date updatedAt;

    private Integer sequence;

    private String params;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_map_id")
    private ColumnMap columnMap;
    public ColumnMap getColumnMap() {
        return columnMap;
    }
    public void setColumnMap(ColumnMap columnMap) {
        this.columnMap = columnMap;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_calculation_item_id")
    private ColumnCalculationItem columnCalculationItem;
    public ColumnCalculationItem getColumnCalculationItem() {
        return columnCalculationItem;
    }
    public void setColumnCalculationItem(ColumnCalculationItem columnCalculationItem) {
        this.columnCalculationItem = columnCalculationItem;
    }

    @Override
    public void initializeRelated() {
        columnMap.getId();
        columnCalculationItem.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}

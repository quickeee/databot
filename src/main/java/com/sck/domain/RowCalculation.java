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
@Table(name = "row_calculation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RowCalculation implements Serializable, RestInitializable {

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
    @JoinColumn(name = "job_id")
    private Job job;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "row_calculation_item_id")
    private RowCalculationItem rowCalculationItem;
    public RowCalculationItem getRowCalculationItem() {
        return rowCalculationItem;
    }
    public void setRowCalculationItem(RowCalculationItem rowCalculationItem) {
        this.rowCalculationItem = rowCalculationItem;
    }


    @Override
    public void initializeRelated() {
        job.getId();
        rowCalculationItem.getId();
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

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
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

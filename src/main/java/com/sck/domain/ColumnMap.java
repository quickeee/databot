package com.sck.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by KINCERS on 4/29/2015.
 */
@Entity
@Table(name = "column_map")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ColumnMap {

    @Id @GeneratedValue
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    private Job job;
    public Job getJob() {
        return job;
    }
    public void setJob(Job job) {
        this.job = job;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    private SourceColumn sourceColumn;
    public SourceColumn getSourceColumn() {
        return sourceColumn;
    }
    public void setSourceColumn(SourceColumn sourceColumn) {
        this.sourceColumn = sourceColumn;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    private DestinationColumn destinationColumn;
    public DestinationColumn getDestinationColumn() {
        return destinationColumn;
    }
    public void setDestinationColumn(DestinationColumn destinationColumn) {
        this.destinationColumn = destinationColumn;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_map_id")
    private Set<ColumnCalculation> columnCalculations = new HashSet<>();
    public Set<ColumnCalculation> getColumnCalculations() {
        return columnCalculations;
    }
    public void setColumnCalculations(Set<ColumnCalculation> columnCalculations) {
        this.columnCalculations = columnCalculations;
    }
}

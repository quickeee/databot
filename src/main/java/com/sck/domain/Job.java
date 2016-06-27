package com.sck.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KINCERS on 4/27/2015.
 */
@Entity
@Table(name = "job")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Job implements Serializable, RestInitializable {

    @Id
    @GeneratedValue
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

    private String notes;

    @NotNull(message = "You must specify a title!")
    private String title;

    private String type;

    private Boolean enabled;

    private Boolean running;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private List<JobLog> jobLogs = new ArrayList<>();
    public List<JobLog> getJobLogs() { return jobLogs; }
    public void setJobLogs(List<JobLog> jobLogs) { this.jobLogs = jobLogs; }


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private List<Schedule> schedules = new ArrayList<>();
    public List<Schedule> getSchedules() {
        return schedules;
    }
    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private List<JobConfig> jobConfigs = new ArrayList<>();
    public List<JobConfig> getJobConfigs() {
        return jobConfigs;
    }
    public void setJobConfigs(List<JobConfig> jobConfigs) {
        this.jobConfigs = jobConfigs;
    }


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private List<RowCalculation> rowCalculations = new ArrayList<>();
    public List<RowCalculation> getRowCalculations() {
        return rowCalculations;
    }
    public void setRowCalculations(List<RowCalculation> rowCalculations) {
        this.rowCalculations = rowCalculations;
    }


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private List<ColumnMap> columnMaps;
    public List<ColumnMap> getColumnMaps() {
        return columnMaps;
    }
    public void setColumnMaps(List<ColumnMap> columnMaps) {
        this.columnMaps = columnMaps;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id")
    private Source source;
    public Source getSource() {
        return source;
    }
    public void setSource(Source source) {
        this.source = source;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id")
    private Destination destination;
    public Destination getDestination() {
        return destination;
    }
    public void setDestination(Destination destination) {
        this.destination = destination;
    }


    @Override
    public void initializeRelated() {
        jobLogs.size();
        jobConfigs.size();
        schedules.size();
        rowCalculations.size();
        columnMaps.size();
        source.getId();
        destination.getId();
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }
}

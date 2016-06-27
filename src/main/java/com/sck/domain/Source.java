package com.sck.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by KINCERS on 4/27/2015.
 */
@Entity
@Table(name = "source")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Source implements Serializable, RestInitializable {

    @Id @GeneratedValue
    private Long id;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    @Generated(GenerationTime.INSERT)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/New_York")
    private Date createdAt;
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }


    @Generated(GenerationTime.ALWAYS)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="America/New_York")
    private Date updatedAt;
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }


    private String title;
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }


    private String type;
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Set<SourceConfig> sourceConfigs = new HashSet<>();
    public Set<SourceConfig> getSourceConfigs() {
        return sourceConfigs;
    }
    public void setSourceConfigs(Set<SourceConfig> sourceConfigs) {
        this.sourceConfigs = sourceConfigs;
    }


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Set<Job> jobs = new HashSet<>();
    public Set<Job> getJobs() {
        return jobs;
    }
    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Set<SourceColumn> sourceColumns = new HashSet<>();
    public Set<SourceColumn> getSourceColumns() {
        return sourceColumns;
    }
    public void setSourceColumns(Set<SourceColumn> sourceColumns) {
        this.sourceColumns = sourceColumns;
    }


    @Override
    public void initializeRelated() {
        sourceColumns.size();
        sourceConfigs.size();
        jobs.size();
    }
}

package com.totient.dataintegration.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

import com.totient.dataintegration.domain.enumeration.Status;

import com.totient.dataintegration.domain.enumeration.RecordStatus;

/**
 * A DataSource.
 */
@Entity
@Table(name = "data_source")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "datasource_id")
    private Long datasourceId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "process_group_id")
    private UUID processGroupId;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "configuration")
    private String configuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_status")
    private RecordStatus recordStatus;

    @Column(name = "version")
    private Integer version;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasourceId() {
        return datasourceId;
    }

    public DataSource datasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
        return this;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getName() {
        return name;
    }

    public DataSource name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public DataSource status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UUID getProcessGroupId() {
        return processGroupId;
    }

    public DataSource processGroupId(UUID processGroupId) {
        this.processGroupId = processGroupId;
        return this;
    }

    public void setProcessGroupId(UUID processGroupId) {
        this.processGroupId = processGroupId;
    }

    public String getSchedule() {
        return schedule;
    }

    public DataSource schedule(String schedule) {
        this.schedule = schedule;
        return this;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getConfiguration() {
        return configuration;
    }

    public DataSource configuration(String configuration) {
        this.configuration = configuration;
        return this;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public DataSource recordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
        return this;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getVersion() {
        return version;
    }

    public DataSource version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataSource)) {
            return false;
        }
        return id != null && id.equals(((DataSource) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataSource{" +
            "id=" + getId() +
            ", datasourceId=" + getDatasourceId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", processGroupId='" + getProcessGroupId() + "'" +
            ", schedule='" + getSchedule() + "'" +
            ", configuration='" + getConfiguration() + "'" +
            ", recordStatus='" + getRecordStatus() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}

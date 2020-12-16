package com.totient.dataintegration.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.UUID;

import com.totient.dataintegration.domain.enumeration.RecordStatus;

/**
 * A ProcessGroupTemplate.
 */
@Entity
@Table(name = "process_group_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessGroupTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "process_group_template_id")
    private Long processGroupTemplateId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "registry_id", nullable = false)
    private UUID registryId;

    @NotNull
    @Column(name = "bucket_id", nullable = false)
    private UUID bucketId;

    @NotNull
    @Column(name = "flow_id", nullable = false)
    private UUID flowId;

    @Column(name = "process_group_version")
    private Integer processGroupVersion;

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

    public Long getProcessGroupTemplateId() {
        return processGroupTemplateId;
    }

    public ProcessGroupTemplate processGroupTemplateId(Long processGroupTemplateId) {
        this.processGroupTemplateId = processGroupTemplateId;
        return this;
    }

    public void setProcessGroupTemplateId(Long processGroupTemplateId) {
        this.processGroupTemplateId = processGroupTemplateId;
    }

    public String getName() {
        return name;
    }

    public ProcessGroupTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getRegistryId() {
        return registryId;
    }

    public ProcessGroupTemplate registryId(UUID registryId) {
        this.registryId = registryId;
        return this;
    }

    public void setRegistryId(UUID registryId) {
        this.registryId = registryId;
    }

    public UUID getBucketId() {
        return bucketId;
    }

    public ProcessGroupTemplate bucketId(UUID bucketId) {
        this.bucketId = bucketId;
        return this;
    }

    public void setBucketId(UUID bucketId) {
        this.bucketId = bucketId;
    }

    public UUID getFlowId() {
        return flowId;
    }

    public ProcessGroupTemplate flowId(UUID flowId) {
        this.flowId = flowId;
        return this;
    }

    public void setFlowId(UUID flowId) {
        this.flowId = flowId;
    }

    public Integer getProcessGroupVersion() {
        return processGroupVersion;
    }

    public ProcessGroupTemplate processGroupVersion(Integer processGroupVersion) {
        this.processGroupVersion = processGroupVersion;
        return this;
    }

    public void setProcessGroupVersion(Integer processGroupVersion) {
        this.processGroupVersion = processGroupVersion;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public ProcessGroupTemplate recordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
        return this;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getVersion() {
        return version;
    }

    public ProcessGroupTemplate version(Integer version) {
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
        if (!(o instanceof ProcessGroupTemplate)) {
            return false;
        }
        return id != null && id.equals(((ProcessGroupTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessGroupTemplate{" +
            "id=" + getId() +
            ", processGroupTemplateId=" + getProcessGroupTemplateId() +
            ", name='" + getName() + "'" +
            ", registryId='" + getRegistryId() + "'" +
            ", bucketId='" + getBucketId() + "'" +
            ", flowId='" + getFlowId() + "'" +
            ", processGroupVersion=" + getProcessGroupVersion() +
            ", recordStatus='" + getRecordStatus() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}

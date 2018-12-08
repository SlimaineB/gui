package com.bnpp.cm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A CmError.
 */
@Entity
@Table(name = "cm_error")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CmError implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "error_component")
    private Integer errorComponent;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "error_description")
    private String errorDescription;

    @Column(name = "error_stack_trace")
    private String errorStackTrace;

    @Column(name = "errorn_date_time")
    private Instant errornDateTime;

    @ManyToOne
    @JsonIgnoreProperties("errors")
    private CmRequest request;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getErrorComponent() {
        return errorComponent;
    }

    public CmError errorComponent(Integer errorComponent) {
        this.errorComponent = errorComponent;
        return this;
    }

    public void setErrorComponent(Integer errorComponent) {
        this.errorComponent = errorComponent;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public CmError errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public CmError errorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorStackTrace() {
        return errorStackTrace;
    }

    public CmError errorStackTrace(String errorStackTrace) {
        this.errorStackTrace = errorStackTrace;
        return this;
    }

    public void setErrorStackTrace(String errorStackTrace) {
        this.errorStackTrace = errorStackTrace;
    }

    public Instant getErrornDateTime() {
        return errornDateTime;
    }

    public CmError errornDateTime(Instant errornDateTime) {
        this.errornDateTime = errornDateTime;
        return this;
    }

    public void setErrornDateTime(Instant errornDateTime) {
        this.errornDateTime = errornDateTime;
    }

    public CmRequest getRequest() {
        return request;
    }

    public CmError request(CmRequest cmRequest) {
        this.request = cmRequest;
        return this;
    }

    public void setRequest(CmRequest cmRequest) {
        this.request = cmRequest;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CmError cmError = (CmError) o;
        if (cmError.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cmError.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CmError{" +
            "id=" + getId() +
            ", errorComponent=" + getErrorComponent() +
            ", errorCode='" + getErrorCode() + "'" +
            ", errorDescription='" + getErrorDescription() + "'" +
            ", errorStackTrace='" + getErrorStackTrace() + "'" +
            ", errornDateTime='" + getErrornDateTime() + "'" +
            "}";
    }
}

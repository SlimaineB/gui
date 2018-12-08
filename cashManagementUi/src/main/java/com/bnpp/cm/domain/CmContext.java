package com.bnpp.cm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A CmContext.
 */
@Entity
@Table(name = "cm_context")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CmContext implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "context_type")
    private Integer contextType;

    @Column(name = "context_name")
    private String contextName;

    @Column(name = "context_value")
    private String contextValue;

    @Column(name = "context_date_time")
    private Instant contextDateTime;

    @ManyToOne
    @JsonIgnoreProperties("contexts")
    private CmRequest request;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getContextType() {
        return contextType;
    }

    public CmContext contextType(Integer contextType) {
        this.contextType = contextType;
        return this;
    }

    public void setContextType(Integer contextType) {
        this.contextType = contextType;
    }

    public String getContextName() {
        return contextName;
    }

    public CmContext contextName(String contextName) {
        this.contextName = contextName;
        return this;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getContextValue() {
        return contextValue;
    }

    public CmContext contextValue(String contextValue) {
        this.contextValue = contextValue;
        return this;
    }

    public void setContextValue(String contextValue) {
        this.contextValue = contextValue;
    }

    public Instant getContextDateTime() {
        return contextDateTime;
    }

    public CmContext contextDateTime(Instant contextDateTime) {
        this.contextDateTime = contextDateTime;
        return this;
    }

    public void setContextDateTime(Instant contextDateTime) {
        this.contextDateTime = contextDateTime;
    }

    public CmRequest getRequest() {
        return request;
    }

    public CmContext request(CmRequest cmRequest) {
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
        CmContext cmContext = (CmContext) o;
        if (cmContext.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cmContext.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CmContext{" +
            "id=" + getId() +
            ", contextType=" + getContextType() +
            ", contextName='" + getContextName() + "'" +
            ", contextValue='" + getContextValue() + "'" +
            ", contextDateTime='" + getContextDateTime() + "'" +
            "}";
    }
}

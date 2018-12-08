package com.bnpp.cm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CmRequest.
 */
@Entity
@Table(name = "cm_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CmRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_uuid")
    private String requestUuid;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_endpoint")
    private String serviceEndpoint;

    @Column(name = "instance_hostname")
    private String instanceHostname;

    @Column(name = "instance_port")
    private Integer instancePort;

    @Column(name = "request_body")
    private String requestBody;

    @Column(name = "request_header")
    private String requestHeader;

    @Column(name = "response_body")
    private String responseBody;

    @Column(name = "response_header")
    private String responseHeader;

    @Column(name = "return_http_code")
    private Integer returnHttpCode;

    @Column(name = "technical_status")
    private String technicalStatus;

    @Column(name = "functional_status")
    private String functionalStatus;

    @Column(name = "start_date_time")
    private Instant startDateTime;

    @Column(name = "end_date_time")
    private Instant endDateTime;

    @Column(name = "request_duration")
    private Integer requestDuration;

    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CmAction> actions = new HashSet<>();
    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CmContext> contexts = new HashSet<>();
    @OneToMany(mappedBy = "request")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CmError> errors = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestUuid() {
        return requestUuid;
    }

    public CmRequest requestUuid(String requestUuid) {
        this.requestUuid = requestUuid;
        return this;
    }

    public void setRequestUuid(String requestUuid) {
        this.requestUuid = requestUuid;
    }

    public String getServiceName() {
        return serviceName;
    }

    public CmRequest serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceEndpoint() {
        return serviceEndpoint;
    }

    public CmRequest serviceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
        return this;
    }

    public void setServiceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public String getInstanceHostname() {
        return instanceHostname;
    }

    public CmRequest instanceHostname(String instanceHostname) {
        this.instanceHostname = instanceHostname;
        return this;
    }

    public void setInstanceHostname(String instanceHostname) {
        this.instanceHostname = instanceHostname;
    }

    public Integer getInstancePort() {
        return instancePort;
    }

    public CmRequest instancePort(Integer instancePort) {
        this.instancePort = instancePort;
        return this;
    }

    public void setInstancePort(Integer instancePort) {
        this.instancePort = instancePort;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public CmRequest requestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public CmRequest requestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
        return this;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public CmRequest responseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public CmRequest responseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
        return this;
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
    }

    public Integer getReturnHttpCode() {
        return returnHttpCode;
    }

    public CmRequest returnHttpCode(Integer returnHttpCode) {
        this.returnHttpCode = returnHttpCode;
        return this;
    }

    public void setReturnHttpCode(Integer returnHttpCode) {
        this.returnHttpCode = returnHttpCode;
    }

    public String getTechnicalStatus() {
        return technicalStatus;
    }

    public CmRequest technicalStatus(String technicalStatus) {
        this.technicalStatus = technicalStatus;
        return this;
    }

    public void setTechnicalStatus(String technicalStatus) {
        this.technicalStatus = technicalStatus;
    }

    public String getFunctionalStatus() {
        return functionalStatus;
    }

    public CmRequest functionalStatus(String functionalStatus) {
        this.functionalStatus = functionalStatus;
        return this;
    }

    public void setFunctionalStatus(String functionalStatus) {
        this.functionalStatus = functionalStatus;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public CmRequest startDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    public void setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    public CmRequest endDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public void setEndDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Integer getRequestDuration() {
        return requestDuration;
    }

    public CmRequest requestDuration(Integer requestDuration) {
        this.requestDuration = requestDuration;
        return this;
    }

    public void setRequestDuration(Integer requestDuration) {
        this.requestDuration = requestDuration;
    }

    public Set<CmAction> getActions() {
        return actions;
    }

    public CmRequest actions(Set<CmAction> cmActions) {
        this.actions = cmActions;
        return this;
    }

    public CmRequest addAction(CmAction cmAction) {
        this.actions.add(cmAction);
        cmAction.setRequest(this);
        return this;
    }

    public CmRequest removeAction(CmAction cmAction) {
        this.actions.remove(cmAction);
        cmAction.setRequest(null);
        return this;
    }

    public void setActions(Set<CmAction> cmActions) {
        this.actions = cmActions;
    }

    public Set<CmContext> getContexts() {
        return contexts;
    }

    public CmRequest contexts(Set<CmContext> cmContexts) {
        this.contexts = cmContexts;
        return this;
    }

    public CmRequest addContext(CmContext cmContext) {
        this.contexts.add(cmContext);
        cmContext.setRequest(this);
        return this;
    }

    public CmRequest removeContext(CmContext cmContext) {
        this.contexts.remove(cmContext);
        cmContext.setRequest(null);
        return this;
    }

    public void setContexts(Set<CmContext> cmContexts) {
        this.contexts = cmContexts;
    }

    public Set<CmError> getErrors() {
        return errors;
    }

    public CmRequest errors(Set<CmError> cmErrors) {
        this.errors = cmErrors;
        return this;
    }

    public CmRequest addError(CmError cmError) {
        this.errors.add(cmError);
        cmError.setRequest(this);
        return this;
    }

    public CmRequest removeError(CmError cmError) {
        this.errors.remove(cmError);
        cmError.setRequest(null);
        return this;
    }

    public void setErrors(Set<CmError> cmErrors) {
        this.errors = cmErrors;
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
        CmRequest cmRequest = (CmRequest) o;
        if (cmRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cmRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CmRequest{" +
            "id=" + getId() +
            ", requestUuid='" + getRequestUuid() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", serviceEndpoint='" + getServiceEndpoint() + "'" +
            ", instanceHostname='" + getInstanceHostname() + "'" +
            ", instancePort=" + getInstancePort() +
            ", requestBody='" + getRequestBody() + "'" +
            ", requestHeader='" + getRequestHeader() + "'" +
            ", responseBody='" + getResponseBody() + "'" +
            ", responseHeader='" + getResponseHeader() + "'" +
            ", returnHttpCode=" + getReturnHttpCode() +
            ", technicalStatus='" + getTechnicalStatus() + "'" +
            ", functionalStatus='" + getFunctionalStatus() + "'" +
            ", startDateTime='" + getStartDateTime() + "'" +
            ", endDateTime='" + getEndDateTime() + "'" +
            ", requestDuration=" + getRequestDuration() +
            "}";
    }
}

package com.bnpp.cm.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the CmRequest entity. This class is used in CmRequestResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /cm-requests?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CmRequestCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter requestUuid;

    private StringFilter serviceName;

    private StringFilter serviceEndpoint;

    private StringFilter instanceHostname;

    private IntegerFilter instancePort;

    private StringFilter requestBody;

    private StringFilter requestHeader;

    private StringFilter responseBody;

    private StringFilter responseHeader;

    private IntegerFilter returnHttpCode;

    private StringFilter technicalStatus;

    private StringFilter functionalStatus;

    private InstantFilter startDateTime;

    private InstantFilter endDateTime;

    private IntegerFilter requestDuration;

    private LongFilter actionId;

    private LongFilter contextId;

    private LongFilter errorId;

    public CmRequestCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRequestUuid() {
        return requestUuid;
    }

    public void setRequestUuid(StringFilter requestUuid) {
        this.requestUuid = requestUuid;
    }

    public StringFilter getServiceName() {
        return serviceName;
    }

    public void setServiceName(StringFilter serviceName) {
        this.serviceName = serviceName;
    }

    public StringFilter getServiceEndpoint() {
        return serviceEndpoint;
    }

    public void setServiceEndpoint(StringFilter serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public StringFilter getInstanceHostname() {
        return instanceHostname;
    }

    public void setInstanceHostname(StringFilter instanceHostname) {
        this.instanceHostname = instanceHostname;
    }

    public IntegerFilter getInstancePort() {
        return instancePort;
    }

    public void setInstancePort(IntegerFilter instancePort) {
        this.instancePort = instancePort;
    }

    public StringFilter getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(StringFilter requestBody) {
        this.requestBody = requestBody;
    }

    public StringFilter getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(StringFilter requestHeader) {
        this.requestHeader = requestHeader;
    }

    public StringFilter getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(StringFilter responseBody) {
        this.responseBody = responseBody;
    }

    public StringFilter getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(StringFilter responseHeader) {
        this.responseHeader = responseHeader;
    }

    public IntegerFilter getReturnHttpCode() {
        return returnHttpCode;
    }

    public void setReturnHttpCode(IntegerFilter returnHttpCode) {
        this.returnHttpCode = returnHttpCode;
    }

    public StringFilter getTechnicalStatus() {
        return technicalStatus;
    }

    public void setTechnicalStatus(StringFilter technicalStatus) {
        this.technicalStatus = technicalStatus;
    }

    public StringFilter getFunctionalStatus() {
        return functionalStatus;
    }

    public void setFunctionalStatus(StringFilter functionalStatus) {
        this.functionalStatus = functionalStatus;
    }

    public InstantFilter getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(InstantFilter startDateTime) {
        this.startDateTime = startDateTime;
    }

    public InstantFilter getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(InstantFilter endDateTime) {
        this.endDateTime = endDateTime;
    }

    public IntegerFilter getRequestDuration() {
        return requestDuration;
    }

    public void setRequestDuration(IntegerFilter requestDuration) {
        this.requestDuration = requestDuration;
    }

    public LongFilter getActionId() {
        return actionId;
    }

    public void setActionId(LongFilter actionId) {
        this.actionId = actionId;
    }

    public LongFilter getContextId() {
        return contextId;
    }

    public void setContextId(LongFilter contextId) {
        this.contextId = contextId;
    }

    public LongFilter getErrorId() {
        return errorId;
    }

    public void setErrorId(LongFilter errorId) {
        this.errorId = errorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CmRequestCriteria that = (CmRequestCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(requestUuid, that.requestUuid) &&
            Objects.equals(serviceName, that.serviceName) &&
            Objects.equals(serviceEndpoint, that.serviceEndpoint) &&
            Objects.equals(instanceHostname, that.instanceHostname) &&
            Objects.equals(instancePort, that.instancePort) &&
            Objects.equals(requestBody, that.requestBody) &&
            Objects.equals(requestHeader, that.requestHeader) &&
            Objects.equals(responseBody, that.responseBody) &&
            Objects.equals(responseHeader, that.responseHeader) &&
            Objects.equals(returnHttpCode, that.returnHttpCode) &&
            Objects.equals(technicalStatus, that.technicalStatus) &&
            Objects.equals(functionalStatus, that.functionalStatus) &&
            Objects.equals(startDateTime, that.startDateTime) &&
            Objects.equals(endDateTime, that.endDateTime) &&
            Objects.equals(requestDuration, that.requestDuration) &&
            Objects.equals(actionId, that.actionId) &&
            Objects.equals(contextId, that.contextId) &&
            Objects.equals(errorId, that.errorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        requestUuid,
        serviceName,
        serviceEndpoint,
        instanceHostname,
        instancePort,
        requestBody,
        requestHeader,
        responseBody,
        responseHeader,
        returnHttpCode,
        technicalStatus,
        functionalStatus,
        startDateTime,
        endDateTime,
        requestDuration,
        actionId,
        contextId,
        errorId
        );
    }

    @Override
    public String toString() {
        return "CmRequestCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (requestUuid != null ? "requestUuid=" + requestUuid + ", " : "") +
                (serviceName != null ? "serviceName=" + serviceName + ", " : "") +
                (serviceEndpoint != null ? "serviceEndpoint=" + serviceEndpoint + ", " : "") +
                (instanceHostname != null ? "instanceHostname=" + instanceHostname + ", " : "") +
                (instancePort != null ? "instancePort=" + instancePort + ", " : "") +
                (requestBody != null ? "requestBody=" + requestBody + ", " : "") +
                (requestHeader != null ? "requestHeader=" + requestHeader + ", " : "") +
                (responseBody != null ? "responseBody=" + responseBody + ", " : "") +
                (responseHeader != null ? "responseHeader=" + responseHeader + ", " : "") +
                (returnHttpCode != null ? "returnHttpCode=" + returnHttpCode + ", " : "") +
                (technicalStatus != null ? "technicalStatus=" + technicalStatus + ", " : "") +
                (functionalStatus != null ? "functionalStatus=" + functionalStatus + ", " : "") +
                (startDateTime != null ? "startDateTime=" + startDateTime + ", " : "") +
                (endDateTime != null ? "endDateTime=" + endDateTime + ", " : "") +
                (requestDuration != null ? "requestDuration=" + requestDuration + ", " : "") +
                (actionId != null ? "actionId=" + actionId + ", " : "") +
                (contextId != null ? "contextId=" + contextId + ", " : "") +
                (errorId != null ? "errorId=" + errorId + ", " : "") +
            "}";
    }

}

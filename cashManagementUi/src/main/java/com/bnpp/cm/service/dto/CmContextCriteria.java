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
 * Criteria class for the CmContext entity. This class is used in CmContextResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /cm-contexts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CmContextCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter contextId;

    private IntegerFilter contextType;

    private StringFilter contextName;

    private StringFilter contextValue;

    private InstantFilter contextDateTime;

    private LongFilter requestId;

    public CmContextCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getContextId() {
        return contextId;
    }

    public void setContextId(LongFilter contextId) {
        this.contextId = contextId;
    }

    public IntegerFilter getContextType() {
        return contextType;
    }

    public void setContextType(IntegerFilter contextType) {
        this.contextType = contextType;
    }

    public StringFilter getContextName() {
        return contextName;
    }

    public void setContextName(StringFilter contextName) {
        this.contextName = contextName;
    }

    public StringFilter getContextValue() {
        return contextValue;
    }

    public void setContextValue(StringFilter contextValue) {
        this.contextValue = contextValue;
    }

    public InstantFilter getContextDateTime() {
        return contextDateTime;
    }

    public void setContextDateTime(InstantFilter contextDateTime) {
        this.contextDateTime = contextDateTime;
    }

    public LongFilter getRequestId() {
        return requestId;
    }

    public void setRequestId(LongFilter requestId) {
        this.requestId = requestId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CmContextCriteria that = (CmContextCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(contextId, that.contextId) &&
            Objects.equals(contextType, that.contextType) &&
            Objects.equals(contextName, that.contextName) &&
            Objects.equals(contextValue, that.contextValue) &&
            Objects.equals(contextDateTime, that.contextDateTime) &&
            Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        contextId,
        contextType,
        contextName,
        contextValue,
        contextDateTime,
        requestId
        );
    }

    @Override
    public String toString() {
        return "CmContextCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (contextId != null ? "contextId=" + contextId + ", " : "") +
                (contextType != null ? "contextType=" + contextType + ", " : "") +
                (contextName != null ? "contextName=" + contextName + ", " : "") +
                (contextValue != null ? "contextValue=" + contextValue + ", " : "") +
                (contextDateTime != null ? "contextDateTime=" + contextDateTime + ", " : "") +
                (requestId != null ? "requestId=" + requestId + ", " : "") +
            "}";
    }

}

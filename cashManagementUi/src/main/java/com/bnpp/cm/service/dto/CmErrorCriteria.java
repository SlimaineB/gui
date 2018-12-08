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
 * Criteria class for the CmError entity. This class is used in CmErrorResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /cm-errors?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CmErrorCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter errorComponent;

    private StringFilter errorCode;

    private StringFilter errorDescription;

    private StringFilter errorStackTrace;

    private InstantFilter errornDateTime;

    private LongFilter requestId;

    public CmErrorCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getErrorComponent() {
        return errorComponent;
    }

    public void setErrorComponent(IntegerFilter errorComponent) {
        this.errorComponent = errorComponent;
    }

    public StringFilter getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(StringFilter errorCode) {
        this.errorCode = errorCode;
    }

    public StringFilter getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(StringFilter errorDescription) {
        this.errorDescription = errorDescription;
    }

    public StringFilter getErrorStackTrace() {
        return errorStackTrace;
    }

    public void setErrorStackTrace(StringFilter errorStackTrace) {
        this.errorStackTrace = errorStackTrace;
    }

    public InstantFilter getErrornDateTime() {
        return errornDateTime;
    }

    public void setErrornDateTime(InstantFilter errornDateTime) {
        this.errornDateTime = errornDateTime;
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
        final CmErrorCriteria that = (CmErrorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(errorComponent, that.errorComponent) &&
            Objects.equals(errorCode, that.errorCode) &&
            Objects.equals(errorDescription, that.errorDescription) &&
            Objects.equals(errorStackTrace, that.errorStackTrace) &&
            Objects.equals(errornDateTime, that.errornDateTime) &&
            Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        errorComponent,
        errorCode,
        errorDescription,
        errorStackTrace,
        errornDateTime,
        requestId
        );
    }

    @Override
    public String toString() {
        return "CmErrorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (errorComponent != null ? "errorComponent=" + errorComponent + ", " : "") +
                (errorCode != null ? "errorCode=" + errorCode + ", " : "") +
                (errorDescription != null ? "errorDescription=" + errorDescription + ", " : "") +
                (errorStackTrace != null ? "errorStackTrace=" + errorStackTrace + ", " : "") +
                (errornDateTime != null ? "errornDateTime=" + errornDateTime + ", " : "") +
                (requestId != null ? "requestId=" + requestId + ", " : "") +
            "}";
    }

}

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
 * Criteria class for the CmAction entity. This class is used in CmActionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /cm-actions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CmActionCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter actionId;

    private IntegerFilter actionNum;

    private StringFilter actionType;

    private StringFilter actionDescription;

    private StringFilter actionInput;

    private StringFilter actionOutput;

    private InstantFilter actionDateTime;

    private IntegerFilter actionDuration;

    private LongFilter requestId;

    public CmActionCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getActionId() {
        return actionId;
    }

    public void setActionId(LongFilter actionId) {
        this.actionId = actionId;
    }

    public IntegerFilter getActionNum() {
        return actionNum;
    }

    public void setActionNum(IntegerFilter actionNum) {
        this.actionNum = actionNum;
    }

    public StringFilter getActionType() {
        return actionType;
    }

    public void setActionType(StringFilter actionType) {
        this.actionType = actionType;
    }

    public StringFilter getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(StringFilter actionDescription) {
        this.actionDescription = actionDescription;
    }

    public StringFilter getActionInput() {
        return actionInput;
    }

    public void setActionInput(StringFilter actionInput) {
        this.actionInput = actionInput;
    }

    public StringFilter getActionOutput() {
        return actionOutput;
    }

    public void setActionOutput(StringFilter actionOutput) {
        this.actionOutput = actionOutput;
    }

    public InstantFilter getActionDateTime() {
        return actionDateTime;
    }

    public void setActionDateTime(InstantFilter actionDateTime) {
        this.actionDateTime = actionDateTime;
    }

    public IntegerFilter getActionDuration() {
        return actionDuration;
    }

    public void setActionDuration(IntegerFilter actionDuration) {
        this.actionDuration = actionDuration;
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
        final CmActionCriteria that = (CmActionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(actionId, that.actionId) &&
            Objects.equals(actionNum, that.actionNum) &&
            Objects.equals(actionType, that.actionType) &&
            Objects.equals(actionDescription, that.actionDescription) &&
            Objects.equals(actionInput, that.actionInput) &&
            Objects.equals(actionOutput, that.actionOutput) &&
            Objects.equals(actionDateTime, that.actionDateTime) &&
            Objects.equals(actionDuration, that.actionDuration) &&
            Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        actionId,
        actionNum,
        actionType,
        actionDescription,
        actionInput,
        actionOutput,
        actionDateTime,
        actionDuration,
        requestId
        );
    }

    @Override
    public String toString() {
        return "CmActionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (actionId != null ? "actionId=" + actionId + ", " : "") +
                (actionNum != null ? "actionNum=" + actionNum + ", " : "") +
                (actionType != null ? "actionType=" + actionType + ", " : "") +
                (actionDescription != null ? "actionDescription=" + actionDescription + ", " : "") +
                (actionInput != null ? "actionInput=" + actionInput + ", " : "") +
                (actionOutput != null ? "actionOutput=" + actionOutput + ", " : "") +
                (actionDateTime != null ? "actionDateTime=" + actionDateTime + ", " : "") +
                (actionDuration != null ? "actionDuration=" + actionDuration + ", " : "") +
                (requestId != null ? "requestId=" + requestId + ", " : "") +
            "}";
    }

}

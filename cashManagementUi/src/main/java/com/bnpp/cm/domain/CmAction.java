package com.bnpp.cm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A CmAction.
 */
@Entity
@Table(name = "cm_action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CmAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action_num")
    private Integer actionNum;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "action_description")
    private String actionDescription;

    @Column(name = "action_input")
    private String actionInput;

    @Column(name = "action_output")
    private String actionOutput;

    @Column(name = "action_date_time")
    private Instant actionDateTime;

    @Column(name = "action_duration")
    private Integer actionDuration;

    @ManyToOne
    @JsonIgnoreProperties("actions")
    private CmRequest request;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getActionNum() {
        return actionNum;
    }

    public CmAction actionNum(Integer actionNum) {
        this.actionNum = actionNum;
        return this;
    }

    public void setActionNum(Integer actionNum) {
        this.actionNum = actionNum;
    }

    public String getActionType() {
        return actionType;
    }

    public CmAction actionType(String actionType) {
        this.actionType = actionType;
        return this;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public CmAction actionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
        return this;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public String getActionInput() {
        return actionInput;
    }

    public CmAction actionInput(String actionInput) {
        this.actionInput = actionInput;
        return this;
    }

    public void setActionInput(String actionInput) {
        this.actionInput = actionInput;
    }

    public String getActionOutput() {
        return actionOutput;
    }

    public CmAction actionOutput(String actionOutput) {
        this.actionOutput = actionOutput;
        return this;
    }

    public void setActionOutput(String actionOutput) {
        this.actionOutput = actionOutput;
    }

    public Instant getActionDateTime() {
        return actionDateTime;
    }

    public CmAction actionDateTime(Instant actionDateTime) {
        this.actionDateTime = actionDateTime;
        return this;
    }

    public void setActionDateTime(Instant actionDateTime) {
        this.actionDateTime = actionDateTime;
    }

    public Integer getActionDuration() {
        return actionDuration;
    }

    public CmAction actionDuration(Integer actionDuration) {
        this.actionDuration = actionDuration;
        return this;
    }

    public void setActionDuration(Integer actionDuration) {
        this.actionDuration = actionDuration;
    }

    public CmRequest getRequest() {
        return request;
    }

    public CmAction request(CmRequest cmRequest) {
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
        CmAction cmAction = (CmAction) o;
        if (cmAction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cmAction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CmAction{" +
            "id=" + getId() +
            ", actionNum=" + getActionNum() +
            ", actionType='" + getActionType() + "'" +
            ", actionDescription='" + getActionDescription() + "'" +
            ", actionInput='" + getActionInput() + "'" +
            ", actionOutput='" + getActionOutput() + "'" +
            ", actionDateTime='" + getActionDateTime() + "'" +
            ", actionDuration=" + getActionDuration() +
            "}";
    }
}

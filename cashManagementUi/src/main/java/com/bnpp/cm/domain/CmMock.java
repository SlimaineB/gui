package com.bnpp.cm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CmMock.
 */
@Entity
@Table(name = "cm_mock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CmMock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mock_id")
    private Long mockId;

    @Column(name = "mock_service_name")
    private Integer mockServiceName;

    @Column(name = "mock_search_key")
    private String mockSearchKey;

    @Column(name = "mock_search_value")
    private String mockSearchValue;

    @Column(name = "mocked_body")
    private String mockedBody;

    @Column(name = "mocked_http_code")
    private String mockedHttpCode;

    @Column(name = "mocked_time")
    private Integer mockedTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMockId() {
        return mockId;
    }

    public CmMock mockId(Long mockId) {
        this.mockId = mockId;
        return this;
    }

    public void setMockId(Long mockId) {
        this.mockId = mockId;
    }

    public Integer getMockServiceName() {
        return mockServiceName;
    }

    public CmMock mockServiceName(Integer mockServiceName) {
        this.mockServiceName = mockServiceName;
        return this;
    }

    public void setMockServiceName(Integer mockServiceName) {
        this.mockServiceName = mockServiceName;
    }

    public String getMockSearchKey() {
        return mockSearchKey;
    }

    public CmMock mockSearchKey(String mockSearchKey) {
        this.mockSearchKey = mockSearchKey;
        return this;
    }

    public void setMockSearchKey(String mockSearchKey) {
        this.mockSearchKey = mockSearchKey;
    }

    public String getMockSearchValue() {
        return mockSearchValue;
    }

    public CmMock mockSearchValue(String mockSearchValue) {
        this.mockSearchValue = mockSearchValue;
        return this;
    }

    public void setMockSearchValue(String mockSearchValue) {
        this.mockSearchValue = mockSearchValue;
    }

    public String getMockedBody() {
        return mockedBody;
    }

    public CmMock mockedBody(String mockedBody) {
        this.mockedBody = mockedBody;
        return this;
    }

    public void setMockedBody(String mockedBody) {
        this.mockedBody = mockedBody;
    }

    public String getMockedHttpCode() {
        return mockedHttpCode;
    }

    public CmMock mockedHttpCode(String mockedHttpCode) {
        this.mockedHttpCode = mockedHttpCode;
        return this;
    }

    public void setMockedHttpCode(String mockedHttpCode) {
        this.mockedHttpCode = mockedHttpCode;
    }

    public Integer getMockedTime() {
        return mockedTime;
    }

    public CmMock mockedTime(Integer mockedTime) {
        this.mockedTime = mockedTime;
        return this;
    }

    public void setMockedTime(Integer mockedTime) {
        this.mockedTime = mockedTime;
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
        CmMock cmMock = (CmMock) o;
        if (cmMock.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cmMock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CmMock{" +
            "id=" + getId() +
            ", mockId=" + getMockId() +
            ", mockServiceName=" + getMockServiceName() +
            ", mockSearchKey='" + getMockSearchKey() + "'" +
            ", mockSearchValue='" + getMockSearchValue() + "'" +
            ", mockedBody='" + getMockedBody() + "'" +
            ", mockedHttpCode='" + getMockedHttpCode() + "'" +
            ", mockedTime=" + getMockedTime() +
            "}";
    }
}

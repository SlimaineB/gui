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

/**
 * Criteria class for the CmMock entity. This class is used in CmMockResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /cm-mocks?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CmMockCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter mockId;

    private IntegerFilter mockServiceName;

    private StringFilter mockSearchKey;

    private StringFilter mockSearchValue;

    private StringFilter mockedBody;

    private StringFilter mockedHttpCode;

    private IntegerFilter mockedTime;

    public CmMockCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getMockId() {
        return mockId;
    }

    public void setMockId(LongFilter mockId) {
        this.mockId = mockId;
    }

    public IntegerFilter getMockServiceName() {
        return mockServiceName;
    }

    public void setMockServiceName(IntegerFilter mockServiceName) {
        this.mockServiceName = mockServiceName;
    }

    public StringFilter getMockSearchKey() {
        return mockSearchKey;
    }

    public void setMockSearchKey(StringFilter mockSearchKey) {
        this.mockSearchKey = mockSearchKey;
    }

    public StringFilter getMockSearchValue() {
        return mockSearchValue;
    }

    public void setMockSearchValue(StringFilter mockSearchValue) {
        this.mockSearchValue = mockSearchValue;
    }

    public StringFilter getMockedBody() {
        return mockedBody;
    }

    public void setMockedBody(StringFilter mockedBody) {
        this.mockedBody = mockedBody;
    }

    public StringFilter getMockedHttpCode() {
        return mockedHttpCode;
    }

    public void setMockedHttpCode(StringFilter mockedHttpCode) {
        this.mockedHttpCode = mockedHttpCode;
    }

    public IntegerFilter getMockedTime() {
        return mockedTime;
    }

    public void setMockedTime(IntegerFilter mockedTime) {
        this.mockedTime = mockedTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CmMockCriteria that = (CmMockCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(mockId, that.mockId) &&
            Objects.equals(mockServiceName, that.mockServiceName) &&
            Objects.equals(mockSearchKey, that.mockSearchKey) &&
            Objects.equals(mockSearchValue, that.mockSearchValue) &&
            Objects.equals(mockedBody, that.mockedBody) &&
            Objects.equals(mockedHttpCode, that.mockedHttpCode) &&
            Objects.equals(mockedTime, that.mockedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        mockId,
        mockServiceName,
        mockSearchKey,
        mockSearchValue,
        mockedBody,
        mockedHttpCode,
        mockedTime
        );
    }

    @Override
    public String toString() {
        return "CmMockCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (mockId != null ? "mockId=" + mockId + ", " : "") +
                (mockServiceName != null ? "mockServiceName=" + mockServiceName + ", " : "") +
                (mockSearchKey != null ? "mockSearchKey=" + mockSearchKey + ", " : "") +
                (mockSearchValue != null ? "mockSearchValue=" + mockSearchValue + ", " : "") +
                (mockedBody != null ? "mockedBody=" + mockedBody + ", " : "") +
                (mockedHttpCode != null ? "mockedHttpCode=" + mockedHttpCode + ", " : "") +
                (mockedTime != null ? "mockedTime=" + mockedTime + ", " : "") +
            "}";
    }

}

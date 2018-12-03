package com.bnpp.cm.web.rest;

import com.bnpp.cm.CashManagementUiApp;

import com.bnpp.cm.domain.CmMock;
import com.bnpp.cm.repository.CmMockRepository;
import com.bnpp.cm.service.CmMockService;
import com.bnpp.cm.web.rest.errors.ExceptionTranslator;
import com.bnpp.cm.service.dto.CmMockCriteria;
import com.bnpp.cm.service.CmMockQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.bnpp.cm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CmMockResource REST controller.
 *
 * @see CmMockResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashManagementUiApp.class)
public class CmMockResourceIntTest {

    private static final Long DEFAULT_MOCK_ID = 1L;
    private static final Long UPDATED_MOCK_ID = 2L;

    private static final Integer DEFAULT_MOCK_SERVICE_NAME = 1;
    private static final Integer UPDATED_MOCK_SERVICE_NAME = 2;

    private static final String DEFAULT_MOCK_SEARCH_KEY = "AAAAAAAAAA";
    private static final String UPDATED_MOCK_SEARCH_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_MOCK_SEARCH_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_MOCK_SEARCH_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_MOCKED_BODY = "AAAAAAAAAA";
    private static final String UPDATED_MOCKED_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_MOCKED_HTTP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MOCKED_HTTP_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MOCKED_TIME = 1;
    private static final Integer UPDATED_MOCKED_TIME = 2;

    @Autowired
    private CmMockRepository cmMockRepository;

    @Autowired
    private CmMockService cmMockService;

    @Autowired
    private CmMockQueryService cmMockQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCmMockMockMvc;

    private CmMock cmMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CmMockResource cmMockResource = new CmMockResource(cmMockService, cmMockQueryService);
        this.restCmMockMockMvc = MockMvcBuilders.standaloneSetup(cmMockResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CmMock createEntity(EntityManager em) {
        CmMock cmMock = new CmMock()
            .mockId(DEFAULT_MOCK_ID)
            .mockServiceName(DEFAULT_MOCK_SERVICE_NAME)
            .mockSearchKey(DEFAULT_MOCK_SEARCH_KEY)
            .mockSearchValue(DEFAULT_MOCK_SEARCH_VALUE)
            .mockedBody(DEFAULT_MOCKED_BODY)
            .mockedHttpCode(DEFAULT_MOCKED_HTTP_CODE)
            .mockedTime(DEFAULT_MOCKED_TIME);
        return cmMock;
    }

    @Before
    public void initTest() {
        cmMock = createEntity(em);
    }

    @Test
    @Transactional
    public void createCmMock() throws Exception {
        int databaseSizeBeforeCreate = cmMockRepository.findAll().size();

        // Create the CmMock
        restCmMockMockMvc.perform(post("/api/cm-mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmMock)))
            .andExpect(status().isCreated());

        // Validate the CmMock in the database
        List<CmMock> cmMockList = cmMockRepository.findAll();
        assertThat(cmMockList).hasSize(databaseSizeBeforeCreate + 1);
        CmMock testCmMock = cmMockList.get(cmMockList.size() - 1);
        assertThat(testCmMock.getMockId()).isEqualTo(DEFAULT_MOCK_ID);
        assertThat(testCmMock.getMockServiceName()).isEqualTo(DEFAULT_MOCK_SERVICE_NAME);
        assertThat(testCmMock.getMockSearchKey()).isEqualTo(DEFAULT_MOCK_SEARCH_KEY);
        assertThat(testCmMock.getMockSearchValue()).isEqualTo(DEFAULT_MOCK_SEARCH_VALUE);
        assertThat(testCmMock.getMockedBody()).isEqualTo(DEFAULT_MOCKED_BODY);
        assertThat(testCmMock.getMockedHttpCode()).isEqualTo(DEFAULT_MOCKED_HTTP_CODE);
        assertThat(testCmMock.getMockedTime()).isEqualTo(DEFAULT_MOCKED_TIME);
    }

    @Test
    @Transactional
    public void createCmMockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cmMockRepository.findAll().size();

        // Create the CmMock with an existing ID
        cmMock.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCmMockMockMvc.perform(post("/api/cm-mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmMock)))
            .andExpect(status().isBadRequest());

        // Validate the CmMock in the database
        List<CmMock> cmMockList = cmMockRepository.findAll();
        assertThat(cmMockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCmMocks() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList
        restCmMockMockMvc.perform(get("/api/cm-mocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmMock.getId().intValue())))
            .andExpect(jsonPath("$.[*].mockId").value(hasItem(DEFAULT_MOCK_ID.intValue())))
            .andExpect(jsonPath("$.[*].mockServiceName").value(hasItem(DEFAULT_MOCK_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].mockSearchKey").value(hasItem(DEFAULT_MOCK_SEARCH_KEY.toString())))
            .andExpect(jsonPath("$.[*].mockSearchValue").value(hasItem(DEFAULT_MOCK_SEARCH_VALUE.toString())))
            .andExpect(jsonPath("$.[*].mockedBody").value(hasItem(DEFAULT_MOCKED_BODY.toString())))
            .andExpect(jsonPath("$.[*].mockedHttpCode").value(hasItem(DEFAULT_MOCKED_HTTP_CODE.toString())))
            .andExpect(jsonPath("$.[*].mockedTime").value(hasItem(DEFAULT_MOCKED_TIME)));
    }
    
    @Test
    @Transactional
    public void getCmMock() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get the cmMock
        restCmMockMockMvc.perform(get("/api/cm-mocks/{id}", cmMock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cmMock.getId().intValue()))
            .andExpect(jsonPath("$.mockId").value(DEFAULT_MOCK_ID.intValue()))
            .andExpect(jsonPath("$.mockServiceName").value(DEFAULT_MOCK_SERVICE_NAME))
            .andExpect(jsonPath("$.mockSearchKey").value(DEFAULT_MOCK_SEARCH_KEY.toString()))
            .andExpect(jsonPath("$.mockSearchValue").value(DEFAULT_MOCK_SEARCH_VALUE.toString()))
            .andExpect(jsonPath("$.mockedBody").value(DEFAULT_MOCKED_BODY.toString()))
            .andExpect(jsonPath("$.mockedHttpCode").value(DEFAULT_MOCKED_HTTP_CODE.toString()))
            .andExpect(jsonPath("$.mockedTime").value(DEFAULT_MOCKED_TIME));
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockId equals to DEFAULT_MOCK_ID
        defaultCmMockShouldBeFound("mockId.equals=" + DEFAULT_MOCK_ID);

        // Get all the cmMockList where mockId equals to UPDATED_MOCK_ID
        defaultCmMockShouldNotBeFound("mockId.equals=" + UPDATED_MOCK_ID);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockIdIsInShouldWork() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockId in DEFAULT_MOCK_ID or UPDATED_MOCK_ID
        defaultCmMockShouldBeFound("mockId.in=" + DEFAULT_MOCK_ID + "," + UPDATED_MOCK_ID);

        // Get all the cmMockList where mockId equals to UPDATED_MOCK_ID
        defaultCmMockShouldNotBeFound("mockId.in=" + UPDATED_MOCK_ID);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockId is not null
        defaultCmMockShouldBeFound("mockId.specified=true");

        // Get all the cmMockList where mockId is null
        defaultCmMockShouldNotBeFound("mockId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockId greater than or equals to DEFAULT_MOCK_ID
        defaultCmMockShouldBeFound("mockId.greaterOrEqualThan=" + DEFAULT_MOCK_ID);

        // Get all the cmMockList where mockId greater than or equals to UPDATED_MOCK_ID
        defaultCmMockShouldNotBeFound("mockId.greaterOrEqualThan=" + UPDATED_MOCK_ID);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockIdIsLessThanSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockId less than or equals to DEFAULT_MOCK_ID
        defaultCmMockShouldNotBeFound("mockId.lessThan=" + DEFAULT_MOCK_ID);

        // Get all the cmMockList where mockId less than or equals to UPDATED_MOCK_ID
        defaultCmMockShouldBeFound("mockId.lessThan=" + UPDATED_MOCK_ID);
    }


    @Test
    @Transactional
    public void getAllCmMocksByMockServiceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockServiceName equals to DEFAULT_MOCK_SERVICE_NAME
        defaultCmMockShouldBeFound("mockServiceName.equals=" + DEFAULT_MOCK_SERVICE_NAME);

        // Get all the cmMockList where mockServiceName equals to UPDATED_MOCK_SERVICE_NAME
        defaultCmMockShouldNotBeFound("mockServiceName.equals=" + UPDATED_MOCK_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockServiceNameIsInShouldWork() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockServiceName in DEFAULT_MOCK_SERVICE_NAME or UPDATED_MOCK_SERVICE_NAME
        defaultCmMockShouldBeFound("mockServiceName.in=" + DEFAULT_MOCK_SERVICE_NAME + "," + UPDATED_MOCK_SERVICE_NAME);

        // Get all the cmMockList where mockServiceName equals to UPDATED_MOCK_SERVICE_NAME
        defaultCmMockShouldNotBeFound("mockServiceName.in=" + UPDATED_MOCK_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockServiceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockServiceName is not null
        defaultCmMockShouldBeFound("mockServiceName.specified=true");

        // Get all the cmMockList where mockServiceName is null
        defaultCmMockShouldNotBeFound("mockServiceName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockServiceNameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockServiceName greater than or equals to DEFAULT_MOCK_SERVICE_NAME
        defaultCmMockShouldBeFound("mockServiceName.greaterOrEqualThan=" + DEFAULT_MOCK_SERVICE_NAME);

        // Get all the cmMockList where mockServiceName greater than or equals to UPDATED_MOCK_SERVICE_NAME
        defaultCmMockShouldNotBeFound("mockServiceName.greaterOrEqualThan=" + UPDATED_MOCK_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockServiceNameIsLessThanSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockServiceName less than or equals to DEFAULT_MOCK_SERVICE_NAME
        defaultCmMockShouldNotBeFound("mockServiceName.lessThan=" + DEFAULT_MOCK_SERVICE_NAME);

        // Get all the cmMockList where mockServiceName less than or equals to UPDATED_MOCK_SERVICE_NAME
        defaultCmMockShouldBeFound("mockServiceName.lessThan=" + UPDATED_MOCK_SERVICE_NAME);
    }


    @Test
    @Transactional
    public void getAllCmMocksByMockSearchKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockSearchKey equals to DEFAULT_MOCK_SEARCH_KEY
        defaultCmMockShouldBeFound("mockSearchKey.equals=" + DEFAULT_MOCK_SEARCH_KEY);

        // Get all the cmMockList where mockSearchKey equals to UPDATED_MOCK_SEARCH_KEY
        defaultCmMockShouldNotBeFound("mockSearchKey.equals=" + UPDATED_MOCK_SEARCH_KEY);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockSearchKeyIsInShouldWork() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockSearchKey in DEFAULT_MOCK_SEARCH_KEY or UPDATED_MOCK_SEARCH_KEY
        defaultCmMockShouldBeFound("mockSearchKey.in=" + DEFAULT_MOCK_SEARCH_KEY + "," + UPDATED_MOCK_SEARCH_KEY);

        // Get all the cmMockList where mockSearchKey equals to UPDATED_MOCK_SEARCH_KEY
        defaultCmMockShouldNotBeFound("mockSearchKey.in=" + UPDATED_MOCK_SEARCH_KEY);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockSearchKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockSearchKey is not null
        defaultCmMockShouldBeFound("mockSearchKey.specified=true");

        // Get all the cmMockList where mockSearchKey is null
        defaultCmMockShouldNotBeFound("mockSearchKey.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockSearchValueIsEqualToSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockSearchValue equals to DEFAULT_MOCK_SEARCH_VALUE
        defaultCmMockShouldBeFound("mockSearchValue.equals=" + DEFAULT_MOCK_SEARCH_VALUE);

        // Get all the cmMockList where mockSearchValue equals to UPDATED_MOCK_SEARCH_VALUE
        defaultCmMockShouldNotBeFound("mockSearchValue.equals=" + UPDATED_MOCK_SEARCH_VALUE);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockSearchValueIsInShouldWork() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockSearchValue in DEFAULT_MOCK_SEARCH_VALUE or UPDATED_MOCK_SEARCH_VALUE
        defaultCmMockShouldBeFound("mockSearchValue.in=" + DEFAULT_MOCK_SEARCH_VALUE + "," + UPDATED_MOCK_SEARCH_VALUE);

        // Get all the cmMockList where mockSearchValue equals to UPDATED_MOCK_SEARCH_VALUE
        defaultCmMockShouldNotBeFound("mockSearchValue.in=" + UPDATED_MOCK_SEARCH_VALUE);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockSearchValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockSearchValue is not null
        defaultCmMockShouldBeFound("mockSearchValue.specified=true");

        // Get all the cmMockList where mockSearchValue is null
        defaultCmMockShouldNotBeFound("mockSearchValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockedBodyIsEqualToSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockedBody equals to DEFAULT_MOCKED_BODY
        defaultCmMockShouldBeFound("mockedBody.equals=" + DEFAULT_MOCKED_BODY);

        // Get all the cmMockList where mockedBody equals to UPDATED_MOCKED_BODY
        defaultCmMockShouldNotBeFound("mockedBody.equals=" + UPDATED_MOCKED_BODY);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockedBodyIsInShouldWork() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockedBody in DEFAULT_MOCKED_BODY or UPDATED_MOCKED_BODY
        defaultCmMockShouldBeFound("mockedBody.in=" + DEFAULT_MOCKED_BODY + "," + UPDATED_MOCKED_BODY);

        // Get all the cmMockList where mockedBody equals to UPDATED_MOCKED_BODY
        defaultCmMockShouldNotBeFound("mockedBody.in=" + UPDATED_MOCKED_BODY);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockedBodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockedBody is not null
        defaultCmMockShouldBeFound("mockedBody.specified=true");

        // Get all the cmMockList where mockedBody is null
        defaultCmMockShouldNotBeFound("mockedBody.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockedHttpCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockedHttpCode equals to DEFAULT_MOCKED_HTTP_CODE
        defaultCmMockShouldBeFound("mockedHttpCode.equals=" + DEFAULT_MOCKED_HTTP_CODE);

        // Get all the cmMockList where mockedHttpCode equals to UPDATED_MOCKED_HTTP_CODE
        defaultCmMockShouldNotBeFound("mockedHttpCode.equals=" + UPDATED_MOCKED_HTTP_CODE);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockedHttpCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockedHttpCode in DEFAULT_MOCKED_HTTP_CODE or UPDATED_MOCKED_HTTP_CODE
        defaultCmMockShouldBeFound("mockedHttpCode.in=" + DEFAULT_MOCKED_HTTP_CODE + "," + UPDATED_MOCKED_HTTP_CODE);

        // Get all the cmMockList where mockedHttpCode equals to UPDATED_MOCKED_HTTP_CODE
        defaultCmMockShouldNotBeFound("mockedHttpCode.in=" + UPDATED_MOCKED_HTTP_CODE);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockedHttpCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockedHttpCode is not null
        defaultCmMockShouldBeFound("mockedHttpCode.specified=true");

        // Get all the cmMockList where mockedHttpCode is null
        defaultCmMockShouldNotBeFound("mockedHttpCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockedTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockedTime equals to DEFAULT_MOCKED_TIME
        defaultCmMockShouldBeFound("mockedTime.equals=" + DEFAULT_MOCKED_TIME);

        // Get all the cmMockList where mockedTime equals to UPDATED_MOCKED_TIME
        defaultCmMockShouldNotBeFound("mockedTime.equals=" + UPDATED_MOCKED_TIME);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockedTimeIsInShouldWork() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockedTime in DEFAULT_MOCKED_TIME or UPDATED_MOCKED_TIME
        defaultCmMockShouldBeFound("mockedTime.in=" + DEFAULT_MOCKED_TIME + "," + UPDATED_MOCKED_TIME);

        // Get all the cmMockList where mockedTime equals to UPDATED_MOCKED_TIME
        defaultCmMockShouldNotBeFound("mockedTime.in=" + UPDATED_MOCKED_TIME);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockedTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockedTime is not null
        defaultCmMockShouldBeFound("mockedTime.specified=true");

        // Get all the cmMockList where mockedTime is null
        defaultCmMockShouldNotBeFound("mockedTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockedTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockedTime greater than or equals to DEFAULT_MOCKED_TIME
        defaultCmMockShouldBeFound("mockedTime.greaterOrEqualThan=" + DEFAULT_MOCKED_TIME);

        // Get all the cmMockList where mockedTime greater than or equals to UPDATED_MOCKED_TIME
        defaultCmMockShouldNotBeFound("mockedTime.greaterOrEqualThan=" + UPDATED_MOCKED_TIME);
    }

    @Test
    @Transactional
    public void getAllCmMocksByMockedTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        cmMockRepository.saveAndFlush(cmMock);

        // Get all the cmMockList where mockedTime less than or equals to DEFAULT_MOCKED_TIME
        defaultCmMockShouldNotBeFound("mockedTime.lessThan=" + DEFAULT_MOCKED_TIME);

        // Get all the cmMockList where mockedTime less than or equals to UPDATED_MOCKED_TIME
        defaultCmMockShouldBeFound("mockedTime.lessThan=" + UPDATED_MOCKED_TIME);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCmMockShouldBeFound(String filter) throws Exception {
        restCmMockMockMvc.perform(get("/api/cm-mocks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmMock.getId().intValue())))
            .andExpect(jsonPath("$.[*].mockId").value(hasItem(DEFAULT_MOCK_ID.intValue())))
            .andExpect(jsonPath("$.[*].mockServiceName").value(hasItem(DEFAULT_MOCK_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].mockSearchKey").value(hasItem(DEFAULT_MOCK_SEARCH_KEY.toString())))
            .andExpect(jsonPath("$.[*].mockSearchValue").value(hasItem(DEFAULT_MOCK_SEARCH_VALUE.toString())))
            .andExpect(jsonPath("$.[*].mockedBody").value(hasItem(DEFAULT_MOCKED_BODY.toString())))
            .andExpect(jsonPath("$.[*].mockedHttpCode").value(hasItem(DEFAULT_MOCKED_HTTP_CODE.toString())))
            .andExpect(jsonPath("$.[*].mockedTime").value(hasItem(DEFAULT_MOCKED_TIME)));

        // Check, that the count call also returns 1
        restCmMockMockMvc.perform(get("/api/cm-mocks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCmMockShouldNotBeFound(String filter) throws Exception {
        restCmMockMockMvc.perform(get("/api/cm-mocks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCmMockMockMvc.perform(get("/api/cm-mocks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCmMock() throws Exception {
        // Get the cmMock
        restCmMockMockMvc.perform(get("/api/cm-mocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmMock() throws Exception {
        // Initialize the database
        cmMockService.save(cmMock);

        int databaseSizeBeforeUpdate = cmMockRepository.findAll().size();

        // Update the cmMock
        CmMock updatedCmMock = cmMockRepository.findById(cmMock.getId()).get();
        // Disconnect from session so that the updates on updatedCmMock are not directly saved in db
        em.detach(updatedCmMock);
        updatedCmMock
            .mockId(UPDATED_MOCK_ID)
            .mockServiceName(UPDATED_MOCK_SERVICE_NAME)
            .mockSearchKey(UPDATED_MOCK_SEARCH_KEY)
            .mockSearchValue(UPDATED_MOCK_SEARCH_VALUE)
            .mockedBody(UPDATED_MOCKED_BODY)
            .mockedHttpCode(UPDATED_MOCKED_HTTP_CODE)
            .mockedTime(UPDATED_MOCKED_TIME);

        restCmMockMockMvc.perform(put("/api/cm-mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCmMock)))
            .andExpect(status().isOk());

        // Validate the CmMock in the database
        List<CmMock> cmMockList = cmMockRepository.findAll();
        assertThat(cmMockList).hasSize(databaseSizeBeforeUpdate);
        CmMock testCmMock = cmMockList.get(cmMockList.size() - 1);
        assertThat(testCmMock.getMockId()).isEqualTo(UPDATED_MOCK_ID);
        assertThat(testCmMock.getMockServiceName()).isEqualTo(UPDATED_MOCK_SERVICE_NAME);
        assertThat(testCmMock.getMockSearchKey()).isEqualTo(UPDATED_MOCK_SEARCH_KEY);
        assertThat(testCmMock.getMockSearchValue()).isEqualTo(UPDATED_MOCK_SEARCH_VALUE);
        assertThat(testCmMock.getMockedBody()).isEqualTo(UPDATED_MOCKED_BODY);
        assertThat(testCmMock.getMockedHttpCode()).isEqualTo(UPDATED_MOCKED_HTTP_CODE);
        assertThat(testCmMock.getMockedTime()).isEqualTo(UPDATED_MOCKED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCmMock() throws Exception {
        int databaseSizeBeforeUpdate = cmMockRepository.findAll().size();

        // Create the CmMock

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCmMockMockMvc.perform(put("/api/cm-mocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmMock)))
            .andExpect(status().isBadRequest());

        // Validate the CmMock in the database
        List<CmMock> cmMockList = cmMockRepository.findAll();
        assertThat(cmMockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCmMock() throws Exception {
        // Initialize the database
        cmMockService.save(cmMock);

        int databaseSizeBeforeDelete = cmMockRepository.findAll().size();

        // Get the cmMock
        restCmMockMockMvc.perform(delete("/api/cm-mocks/{id}", cmMock.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CmMock> cmMockList = cmMockRepository.findAll();
        assertThat(cmMockList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CmMock.class);
        CmMock cmMock1 = new CmMock();
        cmMock1.setId(1L);
        CmMock cmMock2 = new CmMock();
        cmMock2.setId(cmMock1.getId());
        assertThat(cmMock1).isEqualTo(cmMock2);
        cmMock2.setId(2L);
        assertThat(cmMock1).isNotEqualTo(cmMock2);
        cmMock1.setId(null);
        assertThat(cmMock1).isNotEqualTo(cmMock2);
    }
}

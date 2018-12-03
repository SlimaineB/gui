package com.bnpp.cm.web.rest;

import com.bnpp.cm.CashManagementUiApp;

import com.bnpp.cm.domain.CmRequest;
import com.bnpp.cm.domain.CmAction;
import com.bnpp.cm.domain.CmContext;
import com.bnpp.cm.domain.CmError;
import com.bnpp.cm.repository.CmRequestRepository;
import com.bnpp.cm.service.CmRequestService;
import com.bnpp.cm.web.rest.errors.ExceptionTranslator;
import com.bnpp.cm.service.dto.CmRequestCriteria;
import com.bnpp.cm.service.CmRequestQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.bnpp.cm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CmRequestResource REST controller.
 *
 * @see CmRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashManagementUiApp.class)
public class CmRequestResourceIntTest {

    private static final Long DEFAULT_REQUEST_ID = 1L;
    private static final Long UPDATED_REQUEST_ID = 2L;

    private static final String DEFAULT_REQUEST_UUID = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_ENDPOINT = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_ENDPOINT = "BBBBBBBBBB";

    private static final Instant DEFAULT_INSTANCE_HOSTNAME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSTANCE_HOSTNAME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_INSTANCE_PORT = 1;
    private static final Integer UPDATED_INSTANCE_PORT = 2;

    private static final String DEFAULT_REQUEST_BODY = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_HEADER = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_HEADER = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_BODY = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_HEADER = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_HEADER = "BBBBBBBBBB";

    private static final Integer DEFAULT_RETURN_HTTP_CODE = 1;
    private static final Integer UPDATED_RETURN_HTTP_CODE = 2;

    private static final String DEFAULT_TECHNICAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TECHNICAL_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTIONAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTIONAL_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_REQUEST_DURATION = 1;
    private static final Integer UPDATED_REQUEST_DURATION = 2;

    @Autowired
    private CmRequestRepository cmRequestRepository;

    @Autowired
    private CmRequestService cmRequestService;

    @Autowired
    private CmRequestQueryService cmRequestQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCmRequestMockMvc;

    private CmRequest cmRequest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CmRequestResource cmRequestResource = new CmRequestResource(cmRequestService, cmRequestQueryService);
        this.restCmRequestMockMvc = MockMvcBuilders.standaloneSetup(cmRequestResource)
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
    public static CmRequest createEntity(EntityManager em) {
        CmRequest cmRequest = new CmRequest()
            .requestId(DEFAULT_REQUEST_ID)
            .requestUuid(DEFAULT_REQUEST_UUID)
            .serviceName(DEFAULT_SERVICE_NAME)
            .serviceEndpoint(DEFAULT_SERVICE_ENDPOINT)
            .instanceHostname(DEFAULT_INSTANCE_HOSTNAME)
            .instancePort(DEFAULT_INSTANCE_PORT)
            .requestBody(DEFAULT_REQUEST_BODY)
            .requestHeader(DEFAULT_REQUEST_HEADER)
            .responseBody(DEFAULT_RESPONSE_BODY)
            .responseHeader(DEFAULT_RESPONSE_HEADER)
            .returnHttpCode(DEFAULT_RETURN_HTTP_CODE)
            .technicalStatus(DEFAULT_TECHNICAL_STATUS)
            .functionalStatus(DEFAULT_FUNCTIONAL_STATUS)
            .startDateTime(DEFAULT_START_DATE_TIME)
            .endDateTime(DEFAULT_END_DATE_TIME)
            .requestDuration(DEFAULT_REQUEST_DURATION);
        return cmRequest;
    }

    @Before
    public void initTest() {
        cmRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createCmRequest() throws Exception {
        int databaseSizeBeforeCreate = cmRequestRepository.findAll().size();

        // Create the CmRequest
        restCmRequestMockMvc.perform(post("/api/cm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmRequest)))
            .andExpect(status().isCreated());

        // Validate the CmRequest in the database
        List<CmRequest> cmRequestList = cmRequestRepository.findAll();
        assertThat(cmRequestList).hasSize(databaseSizeBeforeCreate + 1);
        CmRequest testCmRequest = cmRequestList.get(cmRequestList.size() - 1);
        assertThat(testCmRequest.getRequestId()).isEqualTo(DEFAULT_REQUEST_ID);
        assertThat(testCmRequest.getRequestUuid()).isEqualTo(DEFAULT_REQUEST_UUID);
        assertThat(testCmRequest.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testCmRequest.getServiceEndpoint()).isEqualTo(DEFAULT_SERVICE_ENDPOINT);
        assertThat(testCmRequest.getInstanceHostname()).isEqualTo(DEFAULT_INSTANCE_HOSTNAME);
        assertThat(testCmRequest.getInstancePort()).isEqualTo(DEFAULT_INSTANCE_PORT);
        assertThat(testCmRequest.getRequestBody()).isEqualTo(DEFAULT_REQUEST_BODY);
        assertThat(testCmRequest.getRequestHeader()).isEqualTo(DEFAULT_REQUEST_HEADER);
        assertThat(testCmRequest.getResponseBody()).isEqualTo(DEFAULT_RESPONSE_BODY);
        assertThat(testCmRequest.getResponseHeader()).isEqualTo(DEFAULT_RESPONSE_HEADER);
        assertThat(testCmRequest.getReturnHttpCode()).isEqualTo(DEFAULT_RETURN_HTTP_CODE);
        assertThat(testCmRequest.getTechnicalStatus()).isEqualTo(DEFAULT_TECHNICAL_STATUS);
        assertThat(testCmRequest.getFunctionalStatus()).isEqualTo(DEFAULT_FUNCTIONAL_STATUS);
        assertThat(testCmRequest.getStartDateTime()).isEqualTo(DEFAULT_START_DATE_TIME);
        assertThat(testCmRequest.getEndDateTime()).isEqualTo(DEFAULT_END_DATE_TIME);
        assertThat(testCmRequest.getRequestDuration()).isEqualTo(DEFAULT_REQUEST_DURATION);
    }

    @Test
    @Transactional
    public void createCmRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cmRequestRepository.findAll().size();

        // Create the CmRequest with an existing ID
        cmRequest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCmRequestMockMvc.perform(post("/api/cm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmRequest)))
            .andExpect(status().isBadRequest());

        // Validate the CmRequest in the database
        List<CmRequest> cmRequestList = cmRequestRepository.findAll();
        assertThat(cmRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCmRequests() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList
        restCmRequestMockMvc.perform(get("/api/cm-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestId").value(hasItem(DEFAULT_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].requestUuid").value(hasItem(DEFAULT_REQUEST_UUID.toString())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceEndpoint").value(hasItem(DEFAULT_SERVICE_ENDPOINT.toString())))
            .andExpect(jsonPath("$.[*].instanceHostname").value(hasItem(DEFAULT_INSTANCE_HOSTNAME.toString())))
            .andExpect(jsonPath("$.[*].instancePort").value(hasItem(DEFAULT_INSTANCE_PORT)))
            .andExpect(jsonPath("$.[*].requestBody").value(hasItem(DEFAULT_REQUEST_BODY.toString())))
            .andExpect(jsonPath("$.[*].requestHeader").value(hasItem(DEFAULT_REQUEST_HEADER.toString())))
            .andExpect(jsonPath("$.[*].responseBody").value(hasItem(DEFAULT_RESPONSE_BODY.toString())))
            .andExpect(jsonPath("$.[*].responseHeader").value(hasItem(DEFAULT_RESPONSE_HEADER.toString())))
            .andExpect(jsonPath("$.[*].returnHttpCode").value(hasItem(DEFAULT_RETURN_HTTP_CODE)))
            .andExpect(jsonPath("$.[*].technicalStatus").value(hasItem(DEFAULT_TECHNICAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].functionalStatus").value(hasItem(DEFAULT_FUNCTIONAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].startDateTime").value(hasItem(DEFAULT_START_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].endDateTime").value(hasItem(DEFAULT_END_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].requestDuration").value(hasItem(DEFAULT_REQUEST_DURATION)));
    }
    
    @Test
    @Transactional
    public void getCmRequest() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get the cmRequest
        restCmRequestMockMvc.perform(get("/api/cm-requests/{id}", cmRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cmRequest.getId().intValue()))
            .andExpect(jsonPath("$.requestId").value(DEFAULT_REQUEST_ID.intValue()))
            .andExpect(jsonPath("$.requestUuid").value(DEFAULT_REQUEST_UUID.toString()))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME.toString()))
            .andExpect(jsonPath("$.serviceEndpoint").value(DEFAULT_SERVICE_ENDPOINT.toString()))
            .andExpect(jsonPath("$.instanceHostname").value(DEFAULT_INSTANCE_HOSTNAME.toString()))
            .andExpect(jsonPath("$.instancePort").value(DEFAULT_INSTANCE_PORT))
            .andExpect(jsonPath("$.requestBody").value(DEFAULT_REQUEST_BODY.toString()))
            .andExpect(jsonPath("$.requestHeader").value(DEFAULT_REQUEST_HEADER.toString()))
            .andExpect(jsonPath("$.responseBody").value(DEFAULT_RESPONSE_BODY.toString()))
            .andExpect(jsonPath("$.responseHeader").value(DEFAULT_RESPONSE_HEADER.toString()))
            .andExpect(jsonPath("$.returnHttpCode").value(DEFAULT_RETURN_HTTP_CODE))
            .andExpect(jsonPath("$.technicalStatus").value(DEFAULT_TECHNICAL_STATUS.toString()))
            .andExpect(jsonPath("$.functionalStatus").value(DEFAULT_FUNCTIONAL_STATUS.toString()))
            .andExpect(jsonPath("$.startDateTime").value(DEFAULT_START_DATE_TIME.toString()))
            .andExpect(jsonPath("$.endDateTime").value(DEFAULT_END_DATE_TIME.toString()))
            .andExpect(jsonPath("$.requestDuration").value(DEFAULT_REQUEST_DURATION));
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestId equals to DEFAULT_REQUEST_ID
        defaultCmRequestShouldBeFound("requestId.equals=" + DEFAULT_REQUEST_ID);

        // Get all the cmRequestList where requestId equals to UPDATED_REQUEST_ID
        defaultCmRequestShouldNotBeFound("requestId.equals=" + UPDATED_REQUEST_ID);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestIdIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestId in DEFAULT_REQUEST_ID or UPDATED_REQUEST_ID
        defaultCmRequestShouldBeFound("requestId.in=" + DEFAULT_REQUEST_ID + "," + UPDATED_REQUEST_ID);

        // Get all the cmRequestList where requestId equals to UPDATED_REQUEST_ID
        defaultCmRequestShouldNotBeFound("requestId.in=" + UPDATED_REQUEST_ID);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestId is not null
        defaultCmRequestShouldBeFound("requestId.specified=true");

        // Get all the cmRequestList where requestId is null
        defaultCmRequestShouldNotBeFound("requestId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestId greater than or equals to DEFAULT_REQUEST_ID
        defaultCmRequestShouldBeFound("requestId.greaterOrEqualThan=" + DEFAULT_REQUEST_ID);

        // Get all the cmRequestList where requestId greater than or equals to UPDATED_REQUEST_ID
        defaultCmRequestShouldNotBeFound("requestId.greaterOrEqualThan=" + UPDATED_REQUEST_ID);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestIdIsLessThanSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestId less than or equals to DEFAULT_REQUEST_ID
        defaultCmRequestShouldNotBeFound("requestId.lessThan=" + DEFAULT_REQUEST_ID);

        // Get all the cmRequestList where requestId less than or equals to UPDATED_REQUEST_ID
        defaultCmRequestShouldBeFound("requestId.lessThan=" + UPDATED_REQUEST_ID);
    }


    @Test
    @Transactional
    public void getAllCmRequestsByRequestUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestUuid equals to DEFAULT_REQUEST_UUID
        defaultCmRequestShouldBeFound("requestUuid.equals=" + DEFAULT_REQUEST_UUID);

        // Get all the cmRequestList where requestUuid equals to UPDATED_REQUEST_UUID
        defaultCmRequestShouldNotBeFound("requestUuid.equals=" + UPDATED_REQUEST_UUID);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestUuidIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestUuid in DEFAULT_REQUEST_UUID or UPDATED_REQUEST_UUID
        defaultCmRequestShouldBeFound("requestUuid.in=" + DEFAULT_REQUEST_UUID + "," + UPDATED_REQUEST_UUID);

        // Get all the cmRequestList where requestUuid equals to UPDATED_REQUEST_UUID
        defaultCmRequestShouldNotBeFound("requestUuid.in=" + UPDATED_REQUEST_UUID);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestUuid is not null
        defaultCmRequestShouldBeFound("requestUuid.specified=true");

        // Get all the cmRequestList where requestUuid is null
        defaultCmRequestShouldNotBeFound("requestUuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByServiceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where serviceName equals to DEFAULT_SERVICE_NAME
        defaultCmRequestShouldBeFound("serviceName.equals=" + DEFAULT_SERVICE_NAME);

        // Get all the cmRequestList where serviceName equals to UPDATED_SERVICE_NAME
        defaultCmRequestShouldNotBeFound("serviceName.equals=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByServiceNameIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where serviceName in DEFAULT_SERVICE_NAME or UPDATED_SERVICE_NAME
        defaultCmRequestShouldBeFound("serviceName.in=" + DEFAULT_SERVICE_NAME + "," + UPDATED_SERVICE_NAME);

        // Get all the cmRequestList where serviceName equals to UPDATED_SERVICE_NAME
        defaultCmRequestShouldNotBeFound("serviceName.in=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByServiceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where serviceName is not null
        defaultCmRequestShouldBeFound("serviceName.specified=true");

        // Get all the cmRequestList where serviceName is null
        defaultCmRequestShouldNotBeFound("serviceName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByServiceEndpointIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where serviceEndpoint equals to DEFAULT_SERVICE_ENDPOINT
        defaultCmRequestShouldBeFound("serviceEndpoint.equals=" + DEFAULT_SERVICE_ENDPOINT);

        // Get all the cmRequestList where serviceEndpoint equals to UPDATED_SERVICE_ENDPOINT
        defaultCmRequestShouldNotBeFound("serviceEndpoint.equals=" + UPDATED_SERVICE_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByServiceEndpointIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where serviceEndpoint in DEFAULT_SERVICE_ENDPOINT or UPDATED_SERVICE_ENDPOINT
        defaultCmRequestShouldBeFound("serviceEndpoint.in=" + DEFAULT_SERVICE_ENDPOINT + "," + UPDATED_SERVICE_ENDPOINT);

        // Get all the cmRequestList where serviceEndpoint equals to UPDATED_SERVICE_ENDPOINT
        defaultCmRequestShouldNotBeFound("serviceEndpoint.in=" + UPDATED_SERVICE_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByServiceEndpointIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where serviceEndpoint is not null
        defaultCmRequestShouldBeFound("serviceEndpoint.specified=true");

        // Get all the cmRequestList where serviceEndpoint is null
        defaultCmRequestShouldNotBeFound("serviceEndpoint.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByInstanceHostnameIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where instanceHostname equals to DEFAULT_INSTANCE_HOSTNAME
        defaultCmRequestShouldBeFound("instanceHostname.equals=" + DEFAULT_INSTANCE_HOSTNAME);

        // Get all the cmRequestList where instanceHostname equals to UPDATED_INSTANCE_HOSTNAME
        defaultCmRequestShouldNotBeFound("instanceHostname.equals=" + UPDATED_INSTANCE_HOSTNAME);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByInstanceHostnameIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where instanceHostname in DEFAULT_INSTANCE_HOSTNAME or UPDATED_INSTANCE_HOSTNAME
        defaultCmRequestShouldBeFound("instanceHostname.in=" + DEFAULT_INSTANCE_HOSTNAME + "," + UPDATED_INSTANCE_HOSTNAME);

        // Get all the cmRequestList where instanceHostname equals to UPDATED_INSTANCE_HOSTNAME
        defaultCmRequestShouldNotBeFound("instanceHostname.in=" + UPDATED_INSTANCE_HOSTNAME);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByInstanceHostnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where instanceHostname is not null
        defaultCmRequestShouldBeFound("instanceHostname.specified=true");

        // Get all the cmRequestList where instanceHostname is null
        defaultCmRequestShouldNotBeFound("instanceHostname.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByInstancePortIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where instancePort equals to DEFAULT_INSTANCE_PORT
        defaultCmRequestShouldBeFound("instancePort.equals=" + DEFAULT_INSTANCE_PORT);

        // Get all the cmRequestList where instancePort equals to UPDATED_INSTANCE_PORT
        defaultCmRequestShouldNotBeFound("instancePort.equals=" + UPDATED_INSTANCE_PORT);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByInstancePortIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where instancePort in DEFAULT_INSTANCE_PORT or UPDATED_INSTANCE_PORT
        defaultCmRequestShouldBeFound("instancePort.in=" + DEFAULT_INSTANCE_PORT + "," + UPDATED_INSTANCE_PORT);

        // Get all the cmRequestList where instancePort equals to UPDATED_INSTANCE_PORT
        defaultCmRequestShouldNotBeFound("instancePort.in=" + UPDATED_INSTANCE_PORT);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByInstancePortIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where instancePort is not null
        defaultCmRequestShouldBeFound("instancePort.specified=true");

        // Get all the cmRequestList where instancePort is null
        defaultCmRequestShouldNotBeFound("instancePort.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByInstancePortIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where instancePort greater than or equals to DEFAULT_INSTANCE_PORT
        defaultCmRequestShouldBeFound("instancePort.greaterOrEqualThan=" + DEFAULT_INSTANCE_PORT);

        // Get all the cmRequestList where instancePort greater than or equals to UPDATED_INSTANCE_PORT
        defaultCmRequestShouldNotBeFound("instancePort.greaterOrEqualThan=" + UPDATED_INSTANCE_PORT);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByInstancePortIsLessThanSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where instancePort less than or equals to DEFAULT_INSTANCE_PORT
        defaultCmRequestShouldNotBeFound("instancePort.lessThan=" + DEFAULT_INSTANCE_PORT);

        // Get all the cmRequestList where instancePort less than or equals to UPDATED_INSTANCE_PORT
        defaultCmRequestShouldBeFound("instancePort.lessThan=" + UPDATED_INSTANCE_PORT);
    }


    @Test
    @Transactional
    public void getAllCmRequestsByRequestBodyIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestBody equals to DEFAULT_REQUEST_BODY
        defaultCmRequestShouldBeFound("requestBody.equals=" + DEFAULT_REQUEST_BODY);

        // Get all the cmRequestList where requestBody equals to UPDATED_REQUEST_BODY
        defaultCmRequestShouldNotBeFound("requestBody.equals=" + UPDATED_REQUEST_BODY);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestBodyIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestBody in DEFAULT_REQUEST_BODY or UPDATED_REQUEST_BODY
        defaultCmRequestShouldBeFound("requestBody.in=" + DEFAULT_REQUEST_BODY + "," + UPDATED_REQUEST_BODY);

        // Get all the cmRequestList where requestBody equals to UPDATED_REQUEST_BODY
        defaultCmRequestShouldNotBeFound("requestBody.in=" + UPDATED_REQUEST_BODY);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestBodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestBody is not null
        defaultCmRequestShouldBeFound("requestBody.specified=true");

        // Get all the cmRequestList where requestBody is null
        defaultCmRequestShouldNotBeFound("requestBody.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestHeaderIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestHeader equals to DEFAULT_REQUEST_HEADER
        defaultCmRequestShouldBeFound("requestHeader.equals=" + DEFAULT_REQUEST_HEADER);

        // Get all the cmRequestList where requestHeader equals to UPDATED_REQUEST_HEADER
        defaultCmRequestShouldNotBeFound("requestHeader.equals=" + UPDATED_REQUEST_HEADER);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestHeaderIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestHeader in DEFAULT_REQUEST_HEADER or UPDATED_REQUEST_HEADER
        defaultCmRequestShouldBeFound("requestHeader.in=" + DEFAULT_REQUEST_HEADER + "," + UPDATED_REQUEST_HEADER);

        // Get all the cmRequestList where requestHeader equals to UPDATED_REQUEST_HEADER
        defaultCmRequestShouldNotBeFound("requestHeader.in=" + UPDATED_REQUEST_HEADER);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestHeaderIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestHeader is not null
        defaultCmRequestShouldBeFound("requestHeader.specified=true");

        // Get all the cmRequestList where requestHeader is null
        defaultCmRequestShouldNotBeFound("requestHeader.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByResponseBodyIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where responseBody equals to DEFAULT_RESPONSE_BODY
        defaultCmRequestShouldBeFound("responseBody.equals=" + DEFAULT_RESPONSE_BODY);

        // Get all the cmRequestList where responseBody equals to UPDATED_RESPONSE_BODY
        defaultCmRequestShouldNotBeFound("responseBody.equals=" + UPDATED_RESPONSE_BODY);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByResponseBodyIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where responseBody in DEFAULT_RESPONSE_BODY or UPDATED_RESPONSE_BODY
        defaultCmRequestShouldBeFound("responseBody.in=" + DEFAULT_RESPONSE_BODY + "," + UPDATED_RESPONSE_BODY);

        // Get all the cmRequestList where responseBody equals to UPDATED_RESPONSE_BODY
        defaultCmRequestShouldNotBeFound("responseBody.in=" + UPDATED_RESPONSE_BODY);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByResponseBodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where responseBody is not null
        defaultCmRequestShouldBeFound("responseBody.specified=true");

        // Get all the cmRequestList where responseBody is null
        defaultCmRequestShouldNotBeFound("responseBody.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByResponseHeaderIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where responseHeader equals to DEFAULT_RESPONSE_HEADER
        defaultCmRequestShouldBeFound("responseHeader.equals=" + DEFAULT_RESPONSE_HEADER);

        // Get all the cmRequestList where responseHeader equals to UPDATED_RESPONSE_HEADER
        defaultCmRequestShouldNotBeFound("responseHeader.equals=" + UPDATED_RESPONSE_HEADER);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByResponseHeaderIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where responseHeader in DEFAULT_RESPONSE_HEADER or UPDATED_RESPONSE_HEADER
        defaultCmRequestShouldBeFound("responseHeader.in=" + DEFAULT_RESPONSE_HEADER + "," + UPDATED_RESPONSE_HEADER);

        // Get all the cmRequestList where responseHeader equals to UPDATED_RESPONSE_HEADER
        defaultCmRequestShouldNotBeFound("responseHeader.in=" + UPDATED_RESPONSE_HEADER);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByResponseHeaderIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where responseHeader is not null
        defaultCmRequestShouldBeFound("responseHeader.specified=true");

        // Get all the cmRequestList where responseHeader is null
        defaultCmRequestShouldNotBeFound("responseHeader.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByReturnHttpCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where returnHttpCode equals to DEFAULT_RETURN_HTTP_CODE
        defaultCmRequestShouldBeFound("returnHttpCode.equals=" + DEFAULT_RETURN_HTTP_CODE);

        // Get all the cmRequestList where returnHttpCode equals to UPDATED_RETURN_HTTP_CODE
        defaultCmRequestShouldNotBeFound("returnHttpCode.equals=" + UPDATED_RETURN_HTTP_CODE);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByReturnHttpCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where returnHttpCode in DEFAULT_RETURN_HTTP_CODE or UPDATED_RETURN_HTTP_CODE
        defaultCmRequestShouldBeFound("returnHttpCode.in=" + DEFAULT_RETURN_HTTP_CODE + "," + UPDATED_RETURN_HTTP_CODE);

        // Get all the cmRequestList where returnHttpCode equals to UPDATED_RETURN_HTTP_CODE
        defaultCmRequestShouldNotBeFound("returnHttpCode.in=" + UPDATED_RETURN_HTTP_CODE);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByReturnHttpCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where returnHttpCode is not null
        defaultCmRequestShouldBeFound("returnHttpCode.specified=true");

        // Get all the cmRequestList where returnHttpCode is null
        defaultCmRequestShouldNotBeFound("returnHttpCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByReturnHttpCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where returnHttpCode greater than or equals to DEFAULT_RETURN_HTTP_CODE
        defaultCmRequestShouldBeFound("returnHttpCode.greaterOrEqualThan=" + DEFAULT_RETURN_HTTP_CODE);

        // Get all the cmRequestList where returnHttpCode greater than or equals to UPDATED_RETURN_HTTP_CODE
        defaultCmRequestShouldNotBeFound("returnHttpCode.greaterOrEqualThan=" + UPDATED_RETURN_HTTP_CODE);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByReturnHttpCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where returnHttpCode less than or equals to DEFAULT_RETURN_HTTP_CODE
        defaultCmRequestShouldNotBeFound("returnHttpCode.lessThan=" + DEFAULT_RETURN_HTTP_CODE);

        // Get all the cmRequestList where returnHttpCode less than or equals to UPDATED_RETURN_HTTP_CODE
        defaultCmRequestShouldBeFound("returnHttpCode.lessThan=" + UPDATED_RETURN_HTTP_CODE);
    }


    @Test
    @Transactional
    public void getAllCmRequestsByTechnicalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where technicalStatus equals to DEFAULT_TECHNICAL_STATUS
        defaultCmRequestShouldBeFound("technicalStatus.equals=" + DEFAULT_TECHNICAL_STATUS);

        // Get all the cmRequestList where technicalStatus equals to UPDATED_TECHNICAL_STATUS
        defaultCmRequestShouldNotBeFound("technicalStatus.equals=" + UPDATED_TECHNICAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByTechnicalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where technicalStatus in DEFAULT_TECHNICAL_STATUS or UPDATED_TECHNICAL_STATUS
        defaultCmRequestShouldBeFound("technicalStatus.in=" + DEFAULT_TECHNICAL_STATUS + "," + UPDATED_TECHNICAL_STATUS);

        // Get all the cmRequestList where technicalStatus equals to UPDATED_TECHNICAL_STATUS
        defaultCmRequestShouldNotBeFound("technicalStatus.in=" + UPDATED_TECHNICAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByTechnicalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where technicalStatus is not null
        defaultCmRequestShouldBeFound("technicalStatus.specified=true");

        // Get all the cmRequestList where technicalStatus is null
        defaultCmRequestShouldNotBeFound("technicalStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByFunctionalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where functionalStatus equals to DEFAULT_FUNCTIONAL_STATUS
        defaultCmRequestShouldBeFound("functionalStatus.equals=" + DEFAULT_FUNCTIONAL_STATUS);

        // Get all the cmRequestList where functionalStatus equals to UPDATED_FUNCTIONAL_STATUS
        defaultCmRequestShouldNotBeFound("functionalStatus.equals=" + UPDATED_FUNCTIONAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByFunctionalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where functionalStatus in DEFAULT_FUNCTIONAL_STATUS or UPDATED_FUNCTIONAL_STATUS
        defaultCmRequestShouldBeFound("functionalStatus.in=" + DEFAULT_FUNCTIONAL_STATUS + "," + UPDATED_FUNCTIONAL_STATUS);

        // Get all the cmRequestList where functionalStatus equals to UPDATED_FUNCTIONAL_STATUS
        defaultCmRequestShouldNotBeFound("functionalStatus.in=" + UPDATED_FUNCTIONAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByFunctionalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where functionalStatus is not null
        defaultCmRequestShouldBeFound("functionalStatus.specified=true");

        // Get all the cmRequestList where functionalStatus is null
        defaultCmRequestShouldNotBeFound("functionalStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByStartDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where startDateTime equals to DEFAULT_START_DATE_TIME
        defaultCmRequestShouldBeFound("startDateTime.equals=" + DEFAULT_START_DATE_TIME);

        // Get all the cmRequestList where startDateTime equals to UPDATED_START_DATE_TIME
        defaultCmRequestShouldNotBeFound("startDateTime.equals=" + UPDATED_START_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByStartDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where startDateTime in DEFAULT_START_DATE_TIME or UPDATED_START_DATE_TIME
        defaultCmRequestShouldBeFound("startDateTime.in=" + DEFAULT_START_DATE_TIME + "," + UPDATED_START_DATE_TIME);

        // Get all the cmRequestList where startDateTime equals to UPDATED_START_DATE_TIME
        defaultCmRequestShouldNotBeFound("startDateTime.in=" + UPDATED_START_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByStartDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where startDateTime is not null
        defaultCmRequestShouldBeFound("startDateTime.specified=true");

        // Get all the cmRequestList where startDateTime is null
        defaultCmRequestShouldNotBeFound("startDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByEndDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where endDateTime equals to DEFAULT_END_DATE_TIME
        defaultCmRequestShouldBeFound("endDateTime.equals=" + DEFAULT_END_DATE_TIME);

        // Get all the cmRequestList where endDateTime equals to UPDATED_END_DATE_TIME
        defaultCmRequestShouldNotBeFound("endDateTime.equals=" + UPDATED_END_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByEndDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where endDateTime in DEFAULT_END_DATE_TIME or UPDATED_END_DATE_TIME
        defaultCmRequestShouldBeFound("endDateTime.in=" + DEFAULT_END_DATE_TIME + "," + UPDATED_END_DATE_TIME);

        // Get all the cmRequestList where endDateTime equals to UPDATED_END_DATE_TIME
        defaultCmRequestShouldNotBeFound("endDateTime.in=" + UPDATED_END_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByEndDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where endDateTime is not null
        defaultCmRequestShouldBeFound("endDateTime.specified=true");

        // Get all the cmRequestList where endDateTime is null
        defaultCmRequestShouldNotBeFound("endDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestDuration equals to DEFAULT_REQUEST_DURATION
        defaultCmRequestShouldBeFound("requestDuration.equals=" + DEFAULT_REQUEST_DURATION);

        // Get all the cmRequestList where requestDuration equals to UPDATED_REQUEST_DURATION
        defaultCmRequestShouldNotBeFound("requestDuration.equals=" + UPDATED_REQUEST_DURATION);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestDurationIsInShouldWork() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestDuration in DEFAULT_REQUEST_DURATION or UPDATED_REQUEST_DURATION
        defaultCmRequestShouldBeFound("requestDuration.in=" + DEFAULT_REQUEST_DURATION + "," + UPDATED_REQUEST_DURATION);

        // Get all the cmRequestList where requestDuration equals to UPDATED_REQUEST_DURATION
        defaultCmRequestShouldNotBeFound("requestDuration.in=" + UPDATED_REQUEST_DURATION);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestDuration is not null
        defaultCmRequestShouldBeFound("requestDuration.specified=true");

        // Get all the cmRequestList where requestDuration is null
        defaultCmRequestShouldNotBeFound("requestDuration.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestDuration greater than or equals to DEFAULT_REQUEST_DURATION
        defaultCmRequestShouldBeFound("requestDuration.greaterOrEqualThan=" + DEFAULT_REQUEST_DURATION);

        // Get all the cmRequestList where requestDuration greater than or equals to UPDATED_REQUEST_DURATION
        defaultCmRequestShouldNotBeFound("requestDuration.greaterOrEqualThan=" + UPDATED_REQUEST_DURATION);
    }

    @Test
    @Transactional
    public void getAllCmRequestsByRequestDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        cmRequestRepository.saveAndFlush(cmRequest);

        // Get all the cmRequestList where requestDuration less than or equals to DEFAULT_REQUEST_DURATION
        defaultCmRequestShouldNotBeFound("requestDuration.lessThan=" + DEFAULT_REQUEST_DURATION);

        // Get all the cmRequestList where requestDuration less than or equals to UPDATED_REQUEST_DURATION
        defaultCmRequestShouldBeFound("requestDuration.lessThan=" + UPDATED_REQUEST_DURATION);
    }


    @Test
    @Transactional
    public void getAllCmRequestsByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        CmAction action = CmActionResourceIntTest.createEntity(em);
        em.persist(action);
        em.flush();
        cmRequest.addAction(action);
        cmRequestRepository.saveAndFlush(cmRequest);
        Long actionId = action.getId();

        // Get all the cmRequestList where action equals to actionId
        defaultCmRequestShouldBeFound("actionId.equals=" + actionId);

        // Get all the cmRequestList where action equals to actionId + 1
        defaultCmRequestShouldNotBeFound("actionId.equals=" + (actionId + 1));
    }


    @Test
    @Transactional
    public void getAllCmRequestsByContextIsEqualToSomething() throws Exception {
        // Initialize the database
        CmContext context = CmContextResourceIntTest.createEntity(em);
        em.persist(context);
        em.flush();
        cmRequest.addContext(context);
        cmRequestRepository.saveAndFlush(cmRequest);
        Long contextId = context.getId();

        // Get all the cmRequestList where context equals to contextId
        defaultCmRequestShouldBeFound("contextId.equals=" + contextId);

        // Get all the cmRequestList where context equals to contextId + 1
        defaultCmRequestShouldNotBeFound("contextId.equals=" + (contextId + 1));
    }


    @Test
    @Transactional
    public void getAllCmRequestsByErrorIsEqualToSomething() throws Exception {
        // Initialize the database
        CmError error = CmErrorResourceIntTest.createEntity(em);
        em.persist(error);
        em.flush();
        cmRequest.addError(error);
        cmRequestRepository.saveAndFlush(cmRequest);
        Long errorId = error.getId();

        // Get all the cmRequestList where error equals to errorId
        defaultCmRequestShouldBeFound("errorId.equals=" + errorId);

        // Get all the cmRequestList where error equals to errorId + 1
        defaultCmRequestShouldNotBeFound("errorId.equals=" + (errorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCmRequestShouldBeFound(String filter) throws Exception {
        restCmRequestMockMvc.perform(get("/api/cm-requests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestId").value(hasItem(DEFAULT_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].requestUuid").value(hasItem(DEFAULT_REQUEST_UUID.toString())))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceEndpoint").value(hasItem(DEFAULT_SERVICE_ENDPOINT.toString())))
            .andExpect(jsonPath("$.[*].instanceHostname").value(hasItem(DEFAULT_INSTANCE_HOSTNAME.toString())))
            .andExpect(jsonPath("$.[*].instancePort").value(hasItem(DEFAULT_INSTANCE_PORT)))
            .andExpect(jsonPath("$.[*].requestBody").value(hasItem(DEFAULT_REQUEST_BODY.toString())))
            .andExpect(jsonPath("$.[*].requestHeader").value(hasItem(DEFAULT_REQUEST_HEADER.toString())))
            .andExpect(jsonPath("$.[*].responseBody").value(hasItem(DEFAULT_RESPONSE_BODY.toString())))
            .andExpect(jsonPath("$.[*].responseHeader").value(hasItem(DEFAULT_RESPONSE_HEADER.toString())))
            .andExpect(jsonPath("$.[*].returnHttpCode").value(hasItem(DEFAULT_RETURN_HTTP_CODE)))
            .andExpect(jsonPath("$.[*].technicalStatus").value(hasItem(DEFAULT_TECHNICAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].functionalStatus").value(hasItem(DEFAULT_FUNCTIONAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].startDateTime").value(hasItem(DEFAULT_START_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].endDateTime").value(hasItem(DEFAULT_END_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].requestDuration").value(hasItem(DEFAULT_REQUEST_DURATION)));

        // Check, that the count call also returns 1
        restCmRequestMockMvc.perform(get("/api/cm-requests/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCmRequestShouldNotBeFound(String filter) throws Exception {
        restCmRequestMockMvc.perform(get("/api/cm-requests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCmRequestMockMvc.perform(get("/api/cm-requests/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCmRequest() throws Exception {
        // Get the cmRequest
        restCmRequestMockMvc.perform(get("/api/cm-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmRequest() throws Exception {
        // Initialize the database
        cmRequestService.save(cmRequest);

        int databaseSizeBeforeUpdate = cmRequestRepository.findAll().size();

        // Update the cmRequest
        CmRequest updatedCmRequest = cmRequestRepository.findById(cmRequest.getId()).get();
        // Disconnect from session so that the updates on updatedCmRequest are not directly saved in db
        em.detach(updatedCmRequest);
        updatedCmRequest
            .requestId(UPDATED_REQUEST_ID)
            .requestUuid(UPDATED_REQUEST_UUID)
            .serviceName(UPDATED_SERVICE_NAME)
            .serviceEndpoint(UPDATED_SERVICE_ENDPOINT)
            .instanceHostname(UPDATED_INSTANCE_HOSTNAME)
            .instancePort(UPDATED_INSTANCE_PORT)
            .requestBody(UPDATED_REQUEST_BODY)
            .requestHeader(UPDATED_REQUEST_HEADER)
            .responseBody(UPDATED_RESPONSE_BODY)
            .responseHeader(UPDATED_RESPONSE_HEADER)
            .returnHttpCode(UPDATED_RETURN_HTTP_CODE)
            .technicalStatus(UPDATED_TECHNICAL_STATUS)
            .functionalStatus(UPDATED_FUNCTIONAL_STATUS)
            .startDateTime(UPDATED_START_DATE_TIME)
            .endDateTime(UPDATED_END_DATE_TIME)
            .requestDuration(UPDATED_REQUEST_DURATION);

        restCmRequestMockMvc.perform(put("/api/cm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCmRequest)))
            .andExpect(status().isOk());

        // Validate the CmRequest in the database
        List<CmRequest> cmRequestList = cmRequestRepository.findAll();
        assertThat(cmRequestList).hasSize(databaseSizeBeforeUpdate);
        CmRequest testCmRequest = cmRequestList.get(cmRequestList.size() - 1);
        assertThat(testCmRequest.getRequestId()).isEqualTo(UPDATED_REQUEST_ID);
        assertThat(testCmRequest.getRequestUuid()).isEqualTo(UPDATED_REQUEST_UUID);
        assertThat(testCmRequest.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testCmRequest.getServiceEndpoint()).isEqualTo(UPDATED_SERVICE_ENDPOINT);
        assertThat(testCmRequest.getInstanceHostname()).isEqualTo(UPDATED_INSTANCE_HOSTNAME);
        assertThat(testCmRequest.getInstancePort()).isEqualTo(UPDATED_INSTANCE_PORT);
        assertThat(testCmRequest.getRequestBody()).isEqualTo(UPDATED_REQUEST_BODY);
        assertThat(testCmRequest.getRequestHeader()).isEqualTo(UPDATED_REQUEST_HEADER);
        assertThat(testCmRequest.getResponseBody()).isEqualTo(UPDATED_RESPONSE_BODY);
        assertThat(testCmRequest.getResponseHeader()).isEqualTo(UPDATED_RESPONSE_HEADER);
        assertThat(testCmRequest.getReturnHttpCode()).isEqualTo(UPDATED_RETURN_HTTP_CODE);
        assertThat(testCmRequest.getTechnicalStatus()).isEqualTo(UPDATED_TECHNICAL_STATUS);
        assertThat(testCmRequest.getFunctionalStatus()).isEqualTo(UPDATED_FUNCTIONAL_STATUS);
        assertThat(testCmRequest.getStartDateTime()).isEqualTo(UPDATED_START_DATE_TIME);
        assertThat(testCmRequest.getEndDateTime()).isEqualTo(UPDATED_END_DATE_TIME);
        assertThat(testCmRequest.getRequestDuration()).isEqualTo(UPDATED_REQUEST_DURATION);
    }

    @Test
    @Transactional
    public void updateNonExistingCmRequest() throws Exception {
        int databaseSizeBeforeUpdate = cmRequestRepository.findAll().size();

        // Create the CmRequest

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCmRequestMockMvc.perform(put("/api/cm-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmRequest)))
            .andExpect(status().isBadRequest());

        // Validate the CmRequest in the database
        List<CmRequest> cmRequestList = cmRequestRepository.findAll();
        assertThat(cmRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCmRequest() throws Exception {
        // Initialize the database
        cmRequestService.save(cmRequest);

        int databaseSizeBeforeDelete = cmRequestRepository.findAll().size();

        // Get the cmRequest
        restCmRequestMockMvc.perform(delete("/api/cm-requests/{id}", cmRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CmRequest> cmRequestList = cmRequestRepository.findAll();
        assertThat(cmRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CmRequest.class);
        CmRequest cmRequest1 = new CmRequest();
        cmRequest1.setId(1L);
        CmRequest cmRequest2 = new CmRequest();
        cmRequest2.setId(cmRequest1.getId());
        assertThat(cmRequest1).isEqualTo(cmRequest2);
        cmRequest2.setId(2L);
        assertThat(cmRequest1).isNotEqualTo(cmRequest2);
        cmRequest1.setId(null);
        assertThat(cmRequest1).isNotEqualTo(cmRequest2);
    }
}

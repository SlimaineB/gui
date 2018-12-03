package com.bnpp.cm.web.rest;

import com.bnpp.cm.CashManagementUiApp;

import com.bnpp.cm.domain.CmAction;
import com.bnpp.cm.domain.CmRequest;
import com.bnpp.cm.repository.CmActionRepository;
import com.bnpp.cm.service.CmActionService;
import com.bnpp.cm.web.rest.errors.ExceptionTranslator;
import com.bnpp.cm.service.dto.CmActionCriteria;
import com.bnpp.cm.service.CmActionQueryService;

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
 * Test class for the CmActionResource REST controller.
 *
 * @see CmActionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashManagementUiApp.class)
public class CmActionResourceIntTest {

    private static final Long DEFAULT_ACTION_ID = 1L;
    private static final Long UPDATED_ACTION_ID = 2L;

    private static final Integer DEFAULT_ACTION_NUM = 1;
    private static final Integer UPDATED_ACTION_NUM = 2;

    private static final String DEFAULT_ACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_INPUT = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_INPUT = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_OUTPUT = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_OUTPUT = "BBBBBBBBBB";

    private static final Instant DEFAULT_ACTION_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTION_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ACTION_DURATION = 1;
    private static final Integer UPDATED_ACTION_DURATION = 2;

    @Autowired
    private CmActionRepository cmActionRepository;

    @Autowired
    private CmActionService cmActionService;

    @Autowired
    private CmActionQueryService cmActionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCmActionMockMvc;

    private CmAction cmAction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CmActionResource cmActionResource = new CmActionResource(cmActionService, cmActionQueryService);
        this.restCmActionMockMvc = MockMvcBuilders.standaloneSetup(cmActionResource)
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
    public static CmAction createEntity(EntityManager em) {
        CmAction cmAction = new CmAction()
            .actionId(DEFAULT_ACTION_ID)
            .actionNum(DEFAULT_ACTION_NUM)
            .actionType(DEFAULT_ACTION_TYPE)
            .actionDescription(DEFAULT_ACTION_DESCRIPTION)
            .actionInput(DEFAULT_ACTION_INPUT)
            .actionOutput(DEFAULT_ACTION_OUTPUT)
            .actionDateTime(DEFAULT_ACTION_DATE_TIME)
            .actionDuration(DEFAULT_ACTION_DURATION);
        return cmAction;
    }

    @Before
    public void initTest() {
        cmAction = createEntity(em);
    }

    @Test
    @Transactional
    public void createCmAction() throws Exception {
        int databaseSizeBeforeCreate = cmActionRepository.findAll().size();

        // Create the CmAction
        restCmActionMockMvc.perform(post("/api/cm-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmAction)))
            .andExpect(status().isCreated());

        // Validate the CmAction in the database
        List<CmAction> cmActionList = cmActionRepository.findAll();
        assertThat(cmActionList).hasSize(databaseSizeBeforeCreate + 1);
        CmAction testCmAction = cmActionList.get(cmActionList.size() - 1);
        assertThat(testCmAction.getActionId()).isEqualTo(DEFAULT_ACTION_ID);
        assertThat(testCmAction.getActionNum()).isEqualTo(DEFAULT_ACTION_NUM);
        assertThat(testCmAction.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
        assertThat(testCmAction.getActionDescription()).isEqualTo(DEFAULT_ACTION_DESCRIPTION);
        assertThat(testCmAction.getActionInput()).isEqualTo(DEFAULT_ACTION_INPUT);
        assertThat(testCmAction.getActionOutput()).isEqualTo(DEFAULT_ACTION_OUTPUT);
        assertThat(testCmAction.getActionDateTime()).isEqualTo(DEFAULT_ACTION_DATE_TIME);
        assertThat(testCmAction.getActionDuration()).isEqualTo(DEFAULT_ACTION_DURATION);
    }

    @Test
    @Transactional
    public void createCmActionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cmActionRepository.findAll().size();

        // Create the CmAction with an existing ID
        cmAction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCmActionMockMvc.perform(post("/api/cm-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmAction)))
            .andExpect(status().isBadRequest());

        // Validate the CmAction in the database
        List<CmAction> cmActionList = cmActionRepository.findAll();
        assertThat(cmActionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCmActions() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList
        restCmActionMockMvc.perform(get("/api/cm-actions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].actionId").value(hasItem(DEFAULT_ACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].actionNum").value(hasItem(DEFAULT_ACTION_NUM)))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].actionDescription").value(hasItem(DEFAULT_ACTION_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].actionInput").value(hasItem(DEFAULT_ACTION_INPUT.toString())))
            .andExpect(jsonPath("$.[*].actionOutput").value(hasItem(DEFAULT_ACTION_OUTPUT.toString())))
            .andExpect(jsonPath("$.[*].actionDateTime").value(hasItem(DEFAULT_ACTION_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].actionDuration").value(hasItem(DEFAULT_ACTION_DURATION)));
    }
    
    @Test
    @Transactional
    public void getCmAction() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get the cmAction
        restCmActionMockMvc.perform(get("/api/cm-actions/{id}", cmAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cmAction.getId().intValue()))
            .andExpect(jsonPath("$.actionId").value(DEFAULT_ACTION_ID.intValue()))
            .andExpect(jsonPath("$.actionNum").value(DEFAULT_ACTION_NUM))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE.toString()))
            .andExpect(jsonPath("$.actionDescription").value(DEFAULT_ACTION_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.actionInput").value(DEFAULT_ACTION_INPUT.toString()))
            .andExpect(jsonPath("$.actionOutput").value(DEFAULT_ACTION_OUTPUT.toString()))
            .andExpect(jsonPath("$.actionDateTime").value(DEFAULT_ACTION_DATE_TIME.toString()))
            .andExpect(jsonPath("$.actionDuration").value(DEFAULT_ACTION_DURATION));
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionId equals to DEFAULT_ACTION_ID
        defaultCmActionShouldBeFound("actionId.equals=" + DEFAULT_ACTION_ID);

        // Get all the cmActionList where actionId equals to UPDATED_ACTION_ID
        defaultCmActionShouldNotBeFound("actionId.equals=" + UPDATED_ACTION_ID);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionIdIsInShouldWork() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionId in DEFAULT_ACTION_ID or UPDATED_ACTION_ID
        defaultCmActionShouldBeFound("actionId.in=" + DEFAULT_ACTION_ID + "," + UPDATED_ACTION_ID);

        // Get all the cmActionList where actionId equals to UPDATED_ACTION_ID
        defaultCmActionShouldNotBeFound("actionId.in=" + UPDATED_ACTION_ID);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionId is not null
        defaultCmActionShouldBeFound("actionId.specified=true");

        // Get all the cmActionList where actionId is null
        defaultCmActionShouldNotBeFound("actionId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionId greater than or equals to DEFAULT_ACTION_ID
        defaultCmActionShouldBeFound("actionId.greaterOrEqualThan=" + DEFAULT_ACTION_ID);

        // Get all the cmActionList where actionId greater than or equals to UPDATED_ACTION_ID
        defaultCmActionShouldNotBeFound("actionId.greaterOrEqualThan=" + UPDATED_ACTION_ID);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionId less than or equals to DEFAULT_ACTION_ID
        defaultCmActionShouldNotBeFound("actionId.lessThan=" + DEFAULT_ACTION_ID);

        // Get all the cmActionList where actionId less than or equals to UPDATED_ACTION_ID
        defaultCmActionShouldBeFound("actionId.lessThan=" + UPDATED_ACTION_ID);
    }


    @Test
    @Transactional
    public void getAllCmActionsByActionNumIsEqualToSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionNum equals to DEFAULT_ACTION_NUM
        defaultCmActionShouldBeFound("actionNum.equals=" + DEFAULT_ACTION_NUM);

        // Get all the cmActionList where actionNum equals to UPDATED_ACTION_NUM
        defaultCmActionShouldNotBeFound("actionNum.equals=" + UPDATED_ACTION_NUM);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionNumIsInShouldWork() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionNum in DEFAULT_ACTION_NUM or UPDATED_ACTION_NUM
        defaultCmActionShouldBeFound("actionNum.in=" + DEFAULT_ACTION_NUM + "," + UPDATED_ACTION_NUM);

        // Get all the cmActionList where actionNum equals to UPDATED_ACTION_NUM
        defaultCmActionShouldNotBeFound("actionNum.in=" + UPDATED_ACTION_NUM);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionNum is not null
        defaultCmActionShouldBeFound("actionNum.specified=true");

        // Get all the cmActionList where actionNum is null
        defaultCmActionShouldNotBeFound("actionNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionNum greater than or equals to DEFAULT_ACTION_NUM
        defaultCmActionShouldBeFound("actionNum.greaterOrEqualThan=" + DEFAULT_ACTION_NUM);

        // Get all the cmActionList where actionNum greater than or equals to UPDATED_ACTION_NUM
        defaultCmActionShouldNotBeFound("actionNum.greaterOrEqualThan=" + UPDATED_ACTION_NUM);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionNumIsLessThanSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionNum less than or equals to DEFAULT_ACTION_NUM
        defaultCmActionShouldNotBeFound("actionNum.lessThan=" + DEFAULT_ACTION_NUM);

        // Get all the cmActionList where actionNum less than or equals to UPDATED_ACTION_NUM
        defaultCmActionShouldBeFound("actionNum.lessThan=" + UPDATED_ACTION_NUM);
    }


    @Test
    @Transactional
    public void getAllCmActionsByActionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionType equals to DEFAULT_ACTION_TYPE
        defaultCmActionShouldBeFound("actionType.equals=" + DEFAULT_ACTION_TYPE);

        // Get all the cmActionList where actionType equals to UPDATED_ACTION_TYPE
        defaultCmActionShouldNotBeFound("actionType.equals=" + UPDATED_ACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionType in DEFAULT_ACTION_TYPE or UPDATED_ACTION_TYPE
        defaultCmActionShouldBeFound("actionType.in=" + DEFAULT_ACTION_TYPE + "," + UPDATED_ACTION_TYPE);

        // Get all the cmActionList where actionType equals to UPDATED_ACTION_TYPE
        defaultCmActionShouldNotBeFound("actionType.in=" + UPDATED_ACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionType is not null
        defaultCmActionShouldBeFound("actionType.specified=true");

        // Get all the cmActionList where actionType is null
        defaultCmActionShouldNotBeFound("actionType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionDescription equals to DEFAULT_ACTION_DESCRIPTION
        defaultCmActionShouldBeFound("actionDescription.equals=" + DEFAULT_ACTION_DESCRIPTION);

        // Get all the cmActionList where actionDescription equals to UPDATED_ACTION_DESCRIPTION
        defaultCmActionShouldNotBeFound("actionDescription.equals=" + UPDATED_ACTION_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionDescription in DEFAULT_ACTION_DESCRIPTION or UPDATED_ACTION_DESCRIPTION
        defaultCmActionShouldBeFound("actionDescription.in=" + DEFAULT_ACTION_DESCRIPTION + "," + UPDATED_ACTION_DESCRIPTION);

        // Get all the cmActionList where actionDescription equals to UPDATED_ACTION_DESCRIPTION
        defaultCmActionShouldNotBeFound("actionDescription.in=" + UPDATED_ACTION_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionDescription is not null
        defaultCmActionShouldBeFound("actionDescription.specified=true");

        // Get all the cmActionList where actionDescription is null
        defaultCmActionShouldNotBeFound("actionDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionInputIsEqualToSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionInput equals to DEFAULT_ACTION_INPUT
        defaultCmActionShouldBeFound("actionInput.equals=" + DEFAULT_ACTION_INPUT);

        // Get all the cmActionList where actionInput equals to UPDATED_ACTION_INPUT
        defaultCmActionShouldNotBeFound("actionInput.equals=" + UPDATED_ACTION_INPUT);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionInputIsInShouldWork() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionInput in DEFAULT_ACTION_INPUT or UPDATED_ACTION_INPUT
        defaultCmActionShouldBeFound("actionInput.in=" + DEFAULT_ACTION_INPUT + "," + UPDATED_ACTION_INPUT);

        // Get all the cmActionList where actionInput equals to UPDATED_ACTION_INPUT
        defaultCmActionShouldNotBeFound("actionInput.in=" + UPDATED_ACTION_INPUT);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionInputIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionInput is not null
        defaultCmActionShouldBeFound("actionInput.specified=true");

        // Get all the cmActionList where actionInput is null
        defaultCmActionShouldNotBeFound("actionInput.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionOutputIsEqualToSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionOutput equals to DEFAULT_ACTION_OUTPUT
        defaultCmActionShouldBeFound("actionOutput.equals=" + DEFAULT_ACTION_OUTPUT);

        // Get all the cmActionList where actionOutput equals to UPDATED_ACTION_OUTPUT
        defaultCmActionShouldNotBeFound("actionOutput.equals=" + UPDATED_ACTION_OUTPUT);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionOutputIsInShouldWork() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionOutput in DEFAULT_ACTION_OUTPUT or UPDATED_ACTION_OUTPUT
        defaultCmActionShouldBeFound("actionOutput.in=" + DEFAULT_ACTION_OUTPUT + "," + UPDATED_ACTION_OUTPUT);

        // Get all the cmActionList where actionOutput equals to UPDATED_ACTION_OUTPUT
        defaultCmActionShouldNotBeFound("actionOutput.in=" + UPDATED_ACTION_OUTPUT);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionOutputIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionOutput is not null
        defaultCmActionShouldBeFound("actionOutput.specified=true");

        // Get all the cmActionList where actionOutput is null
        defaultCmActionShouldNotBeFound("actionOutput.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionDateTime equals to DEFAULT_ACTION_DATE_TIME
        defaultCmActionShouldBeFound("actionDateTime.equals=" + DEFAULT_ACTION_DATE_TIME);

        // Get all the cmActionList where actionDateTime equals to UPDATED_ACTION_DATE_TIME
        defaultCmActionShouldNotBeFound("actionDateTime.equals=" + UPDATED_ACTION_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionDateTime in DEFAULT_ACTION_DATE_TIME or UPDATED_ACTION_DATE_TIME
        defaultCmActionShouldBeFound("actionDateTime.in=" + DEFAULT_ACTION_DATE_TIME + "," + UPDATED_ACTION_DATE_TIME);

        // Get all the cmActionList where actionDateTime equals to UPDATED_ACTION_DATE_TIME
        defaultCmActionShouldNotBeFound("actionDateTime.in=" + UPDATED_ACTION_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionDateTime is not null
        defaultCmActionShouldBeFound("actionDateTime.specified=true");

        // Get all the cmActionList where actionDateTime is null
        defaultCmActionShouldNotBeFound("actionDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionDuration equals to DEFAULT_ACTION_DURATION
        defaultCmActionShouldBeFound("actionDuration.equals=" + DEFAULT_ACTION_DURATION);

        // Get all the cmActionList where actionDuration equals to UPDATED_ACTION_DURATION
        defaultCmActionShouldNotBeFound("actionDuration.equals=" + UPDATED_ACTION_DURATION);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionDurationIsInShouldWork() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionDuration in DEFAULT_ACTION_DURATION or UPDATED_ACTION_DURATION
        defaultCmActionShouldBeFound("actionDuration.in=" + DEFAULT_ACTION_DURATION + "," + UPDATED_ACTION_DURATION);

        // Get all the cmActionList where actionDuration equals to UPDATED_ACTION_DURATION
        defaultCmActionShouldNotBeFound("actionDuration.in=" + UPDATED_ACTION_DURATION);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionDuration is not null
        defaultCmActionShouldBeFound("actionDuration.specified=true");

        // Get all the cmActionList where actionDuration is null
        defaultCmActionShouldNotBeFound("actionDuration.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionDuration greater than or equals to DEFAULT_ACTION_DURATION
        defaultCmActionShouldBeFound("actionDuration.greaterOrEqualThan=" + DEFAULT_ACTION_DURATION);

        // Get all the cmActionList where actionDuration greater than or equals to UPDATED_ACTION_DURATION
        defaultCmActionShouldNotBeFound("actionDuration.greaterOrEqualThan=" + UPDATED_ACTION_DURATION);
    }

    @Test
    @Transactional
    public void getAllCmActionsByActionDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        cmActionRepository.saveAndFlush(cmAction);

        // Get all the cmActionList where actionDuration less than or equals to DEFAULT_ACTION_DURATION
        defaultCmActionShouldNotBeFound("actionDuration.lessThan=" + DEFAULT_ACTION_DURATION);

        // Get all the cmActionList where actionDuration less than or equals to UPDATED_ACTION_DURATION
        defaultCmActionShouldBeFound("actionDuration.lessThan=" + UPDATED_ACTION_DURATION);
    }


    @Test
    @Transactional
    public void getAllCmActionsByRequestIsEqualToSomething() throws Exception {
        // Initialize the database
        CmRequest request = CmRequestResourceIntTest.createEntity(em);
        em.persist(request);
        em.flush();
        cmAction.setRequest(request);
        cmActionRepository.saveAndFlush(cmAction);
        Long requestId = request.getId();

        // Get all the cmActionList where request equals to requestId
        defaultCmActionShouldBeFound("requestId.equals=" + requestId);

        // Get all the cmActionList where request equals to requestId + 1
        defaultCmActionShouldNotBeFound("requestId.equals=" + (requestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCmActionShouldBeFound(String filter) throws Exception {
        restCmActionMockMvc.perform(get("/api/cm-actions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].actionId").value(hasItem(DEFAULT_ACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].actionNum").value(hasItem(DEFAULT_ACTION_NUM)))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].actionDescription").value(hasItem(DEFAULT_ACTION_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].actionInput").value(hasItem(DEFAULT_ACTION_INPUT.toString())))
            .andExpect(jsonPath("$.[*].actionOutput").value(hasItem(DEFAULT_ACTION_OUTPUT.toString())))
            .andExpect(jsonPath("$.[*].actionDateTime").value(hasItem(DEFAULT_ACTION_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].actionDuration").value(hasItem(DEFAULT_ACTION_DURATION)));

        // Check, that the count call also returns 1
        restCmActionMockMvc.perform(get("/api/cm-actions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCmActionShouldNotBeFound(String filter) throws Exception {
        restCmActionMockMvc.perform(get("/api/cm-actions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCmActionMockMvc.perform(get("/api/cm-actions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCmAction() throws Exception {
        // Get the cmAction
        restCmActionMockMvc.perform(get("/api/cm-actions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmAction() throws Exception {
        // Initialize the database
        cmActionService.save(cmAction);

        int databaseSizeBeforeUpdate = cmActionRepository.findAll().size();

        // Update the cmAction
        CmAction updatedCmAction = cmActionRepository.findById(cmAction.getId()).get();
        // Disconnect from session so that the updates on updatedCmAction are not directly saved in db
        em.detach(updatedCmAction);
        updatedCmAction
            .actionId(UPDATED_ACTION_ID)
            .actionNum(UPDATED_ACTION_NUM)
            .actionType(UPDATED_ACTION_TYPE)
            .actionDescription(UPDATED_ACTION_DESCRIPTION)
            .actionInput(UPDATED_ACTION_INPUT)
            .actionOutput(UPDATED_ACTION_OUTPUT)
            .actionDateTime(UPDATED_ACTION_DATE_TIME)
            .actionDuration(UPDATED_ACTION_DURATION);

        restCmActionMockMvc.perform(put("/api/cm-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCmAction)))
            .andExpect(status().isOk());

        // Validate the CmAction in the database
        List<CmAction> cmActionList = cmActionRepository.findAll();
        assertThat(cmActionList).hasSize(databaseSizeBeforeUpdate);
        CmAction testCmAction = cmActionList.get(cmActionList.size() - 1);
        assertThat(testCmAction.getActionId()).isEqualTo(UPDATED_ACTION_ID);
        assertThat(testCmAction.getActionNum()).isEqualTo(UPDATED_ACTION_NUM);
        assertThat(testCmAction.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testCmAction.getActionDescription()).isEqualTo(UPDATED_ACTION_DESCRIPTION);
        assertThat(testCmAction.getActionInput()).isEqualTo(UPDATED_ACTION_INPUT);
        assertThat(testCmAction.getActionOutput()).isEqualTo(UPDATED_ACTION_OUTPUT);
        assertThat(testCmAction.getActionDateTime()).isEqualTo(UPDATED_ACTION_DATE_TIME);
        assertThat(testCmAction.getActionDuration()).isEqualTo(UPDATED_ACTION_DURATION);
    }

    @Test
    @Transactional
    public void updateNonExistingCmAction() throws Exception {
        int databaseSizeBeforeUpdate = cmActionRepository.findAll().size();

        // Create the CmAction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCmActionMockMvc.perform(put("/api/cm-actions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmAction)))
            .andExpect(status().isBadRequest());

        // Validate the CmAction in the database
        List<CmAction> cmActionList = cmActionRepository.findAll();
        assertThat(cmActionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCmAction() throws Exception {
        // Initialize the database
        cmActionService.save(cmAction);

        int databaseSizeBeforeDelete = cmActionRepository.findAll().size();

        // Get the cmAction
        restCmActionMockMvc.perform(delete("/api/cm-actions/{id}", cmAction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CmAction> cmActionList = cmActionRepository.findAll();
        assertThat(cmActionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CmAction.class);
        CmAction cmAction1 = new CmAction();
        cmAction1.setId(1L);
        CmAction cmAction2 = new CmAction();
        cmAction2.setId(cmAction1.getId());
        assertThat(cmAction1).isEqualTo(cmAction2);
        cmAction2.setId(2L);
        assertThat(cmAction1).isNotEqualTo(cmAction2);
        cmAction1.setId(null);
        assertThat(cmAction1).isNotEqualTo(cmAction2);
    }
}

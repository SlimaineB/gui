package com.bnpp.cm.web.rest;

import com.bnpp.cm.CashManagementUiApp;

import com.bnpp.cm.domain.CmContext;
import com.bnpp.cm.domain.CmRequest;
import com.bnpp.cm.repository.CmContextRepository;
import com.bnpp.cm.service.CmContextService;
import com.bnpp.cm.web.rest.errors.ExceptionTranslator;
import com.bnpp.cm.service.dto.CmContextCriteria;
import com.bnpp.cm.service.CmContextQueryService;

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
 * Test class for the CmContextResource REST controller.
 *
 * @see CmContextResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashManagementUiApp.class)
public class CmContextResourceIntTest {

    private static final Long DEFAULT_CONTEXT_ID = 1L;
    private static final Long UPDATED_CONTEXT_ID = 2L;

    private static final Integer DEFAULT_CONTEXT_TYPE = 1;
    private static final Integer UPDATED_CONTEXT_TYPE = 2;

    private static final String DEFAULT_CONTEXT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT_VALUE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CONTEXT_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONTEXT_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CmContextRepository cmContextRepository;

    @Autowired
    private CmContextService cmContextService;

    @Autowired
    private CmContextQueryService cmContextQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCmContextMockMvc;

    private CmContext cmContext;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CmContextResource cmContextResource = new CmContextResource(cmContextService, cmContextQueryService);
        this.restCmContextMockMvc = MockMvcBuilders.standaloneSetup(cmContextResource)
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
    public static CmContext createEntity(EntityManager em) {
        CmContext cmContext = new CmContext()
            .contextId(DEFAULT_CONTEXT_ID)
            .contextType(DEFAULT_CONTEXT_TYPE)
            .contextName(DEFAULT_CONTEXT_NAME)
            .contextValue(DEFAULT_CONTEXT_VALUE)
            .contextDateTime(DEFAULT_CONTEXT_DATE_TIME);
        return cmContext;
    }

    @Before
    public void initTest() {
        cmContext = createEntity(em);
    }

    @Test
    @Transactional
    public void createCmContext() throws Exception {
        int databaseSizeBeforeCreate = cmContextRepository.findAll().size();

        // Create the CmContext
        restCmContextMockMvc.perform(post("/api/cm-contexts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmContext)))
            .andExpect(status().isCreated());

        // Validate the CmContext in the database
        List<CmContext> cmContextList = cmContextRepository.findAll();
        assertThat(cmContextList).hasSize(databaseSizeBeforeCreate + 1);
        CmContext testCmContext = cmContextList.get(cmContextList.size() - 1);
        assertThat(testCmContext.getContextId()).isEqualTo(DEFAULT_CONTEXT_ID);
        assertThat(testCmContext.getContextType()).isEqualTo(DEFAULT_CONTEXT_TYPE);
        assertThat(testCmContext.getContextName()).isEqualTo(DEFAULT_CONTEXT_NAME);
        assertThat(testCmContext.getContextValue()).isEqualTo(DEFAULT_CONTEXT_VALUE);
        assertThat(testCmContext.getContextDateTime()).isEqualTo(DEFAULT_CONTEXT_DATE_TIME);
    }

    @Test
    @Transactional
    public void createCmContextWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cmContextRepository.findAll().size();

        // Create the CmContext with an existing ID
        cmContext.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCmContextMockMvc.perform(post("/api/cm-contexts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmContext)))
            .andExpect(status().isBadRequest());

        // Validate the CmContext in the database
        List<CmContext> cmContextList = cmContextRepository.findAll();
        assertThat(cmContextList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCmContexts() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList
        restCmContextMockMvc.perform(get("/api/cm-contexts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmContext.getId().intValue())))
            .andExpect(jsonPath("$.[*].contextId").value(hasItem(DEFAULT_CONTEXT_ID.intValue())))
            .andExpect(jsonPath("$.[*].contextType").value(hasItem(DEFAULT_CONTEXT_TYPE)))
            .andExpect(jsonPath("$.[*].contextName").value(hasItem(DEFAULT_CONTEXT_NAME.toString())))
            .andExpect(jsonPath("$.[*].contextValue").value(hasItem(DEFAULT_CONTEXT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].contextDateTime").value(hasItem(DEFAULT_CONTEXT_DATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getCmContext() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get the cmContext
        restCmContextMockMvc.perform(get("/api/cm-contexts/{id}", cmContext.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cmContext.getId().intValue()))
            .andExpect(jsonPath("$.contextId").value(DEFAULT_CONTEXT_ID.intValue()))
            .andExpect(jsonPath("$.contextType").value(DEFAULT_CONTEXT_TYPE))
            .andExpect(jsonPath("$.contextName").value(DEFAULT_CONTEXT_NAME.toString()))
            .andExpect(jsonPath("$.contextValue").value(DEFAULT_CONTEXT_VALUE.toString()))
            .andExpect(jsonPath("$.contextDateTime").value(DEFAULT_CONTEXT_DATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextIdIsEqualToSomething() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextId equals to DEFAULT_CONTEXT_ID
        defaultCmContextShouldBeFound("contextId.equals=" + DEFAULT_CONTEXT_ID);

        // Get all the cmContextList where contextId equals to UPDATED_CONTEXT_ID
        defaultCmContextShouldNotBeFound("contextId.equals=" + UPDATED_CONTEXT_ID);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextIdIsInShouldWork() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextId in DEFAULT_CONTEXT_ID or UPDATED_CONTEXT_ID
        defaultCmContextShouldBeFound("contextId.in=" + DEFAULT_CONTEXT_ID + "," + UPDATED_CONTEXT_ID);

        // Get all the cmContextList where contextId equals to UPDATED_CONTEXT_ID
        defaultCmContextShouldNotBeFound("contextId.in=" + UPDATED_CONTEXT_ID);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextId is not null
        defaultCmContextShouldBeFound("contextId.specified=true");

        // Get all the cmContextList where contextId is null
        defaultCmContextShouldNotBeFound("contextId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextId greater than or equals to DEFAULT_CONTEXT_ID
        defaultCmContextShouldBeFound("contextId.greaterOrEqualThan=" + DEFAULT_CONTEXT_ID);

        // Get all the cmContextList where contextId greater than or equals to UPDATED_CONTEXT_ID
        defaultCmContextShouldNotBeFound("contextId.greaterOrEqualThan=" + UPDATED_CONTEXT_ID);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextIdIsLessThanSomething() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextId less than or equals to DEFAULT_CONTEXT_ID
        defaultCmContextShouldNotBeFound("contextId.lessThan=" + DEFAULT_CONTEXT_ID);

        // Get all the cmContextList where contextId less than or equals to UPDATED_CONTEXT_ID
        defaultCmContextShouldBeFound("contextId.lessThan=" + UPDATED_CONTEXT_ID);
    }


    @Test
    @Transactional
    public void getAllCmContextsByContextTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextType equals to DEFAULT_CONTEXT_TYPE
        defaultCmContextShouldBeFound("contextType.equals=" + DEFAULT_CONTEXT_TYPE);

        // Get all the cmContextList where contextType equals to UPDATED_CONTEXT_TYPE
        defaultCmContextShouldNotBeFound("contextType.equals=" + UPDATED_CONTEXT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextType in DEFAULT_CONTEXT_TYPE or UPDATED_CONTEXT_TYPE
        defaultCmContextShouldBeFound("contextType.in=" + DEFAULT_CONTEXT_TYPE + "," + UPDATED_CONTEXT_TYPE);

        // Get all the cmContextList where contextType equals to UPDATED_CONTEXT_TYPE
        defaultCmContextShouldNotBeFound("contextType.in=" + UPDATED_CONTEXT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextType is not null
        defaultCmContextShouldBeFound("contextType.specified=true");

        // Get all the cmContextList where contextType is null
        defaultCmContextShouldNotBeFound("contextType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextType greater than or equals to DEFAULT_CONTEXT_TYPE
        defaultCmContextShouldBeFound("contextType.greaterOrEqualThan=" + DEFAULT_CONTEXT_TYPE);

        // Get all the cmContextList where contextType greater than or equals to UPDATED_CONTEXT_TYPE
        defaultCmContextShouldNotBeFound("contextType.greaterOrEqualThan=" + UPDATED_CONTEXT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextType less than or equals to DEFAULT_CONTEXT_TYPE
        defaultCmContextShouldNotBeFound("contextType.lessThan=" + DEFAULT_CONTEXT_TYPE);

        // Get all the cmContextList where contextType less than or equals to UPDATED_CONTEXT_TYPE
        defaultCmContextShouldBeFound("contextType.lessThan=" + UPDATED_CONTEXT_TYPE);
    }


    @Test
    @Transactional
    public void getAllCmContextsByContextNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextName equals to DEFAULT_CONTEXT_NAME
        defaultCmContextShouldBeFound("contextName.equals=" + DEFAULT_CONTEXT_NAME);

        // Get all the cmContextList where contextName equals to UPDATED_CONTEXT_NAME
        defaultCmContextShouldNotBeFound("contextName.equals=" + UPDATED_CONTEXT_NAME);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextNameIsInShouldWork() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextName in DEFAULT_CONTEXT_NAME or UPDATED_CONTEXT_NAME
        defaultCmContextShouldBeFound("contextName.in=" + DEFAULT_CONTEXT_NAME + "," + UPDATED_CONTEXT_NAME);

        // Get all the cmContextList where contextName equals to UPDATED_CONTEXT_NAME
        defaultCmContextShouldNotBeFound("contextName.in=" + UPDATED_CONTEXT_NAME);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextName is not null
        defaultCmContextShouldBeFound("contextName.specified=true");

        // Get all the cmContextList where contextName is null
        defaultCmContextShouldNotBeFound("contextName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextValueIsEqualToSomething() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextValue equals to DEFAULT_CONTEXT_VALUE
        defaultCmContextShouldBeFound("contextValue.equals=" + DEFAULT_CONTEXT_VALUE);

        // Get all the cmContextList where contextValue equals to UPDATED_CONTEXT_VALUE
        defaultCmContextShouldNotBeFound("contextValue.equals=" + UPDATED_CONTEXT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextValueIsInShouldWork() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextValue in DEFAULT_CONTEXT_VALUE or UPDATED_CONTEXT_VALUE
        defaultCmContextShouldBeFound("contextValue.in=" + DEFAULT_CONTEXT_VALUE + "," + UPDATED_CONTEXT_VALUE);

        // Get all the cmContextList where contextValue equals to UPDATED_CONTEXT_VALUE
        defaultCmContextShouldNotBeFound("contextValue.in=" + UPDATED_CONTEXT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextValue is not null
        defaultCmContextShouldBeFound("contextValue.specified=true");

        // Get all the cmContextList where contextValue is null
        defaultCmContextShouldNotBeFound("contextValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextDateTime equals to DEFAULT_CONTEXT_DATE_TIME
        defaultCmContextShouldBeFound("contextDateTime.equals=" + DEFAULT_CONTEXT_DATE_TIME);

        // Get all the cmContextList where contextDateTime equals to UPDATED_CONTEXT_DATE_TIME
        defaultCmContextShouldNotBeFound("contextDateTime.equals=" + UPDATED_CONTEXT_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextDateTime in DEFAULT_CONTEXT_DATE_TIME or UPDATED_CONTEXT_DATE_TIME
        defaultCmContextShouldBeFound("contextDateTime.in=" + DEFAULT_CONTEXT_DATE_TIME + "," + UPDATED_CONTEXT_DATE_TIME);

        // Get all the cmContextList where contextDateTime equals to UPDATED_CONTEXT_DATE_TIME
        defaultCmContextShouldNotBeFound("contextDateTime.in=" + UPDATED_CONTEXT_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCmContextsByContextDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmContextRepository.saveAndFlush(cmContext);

        // Get all the cmContextList where contextDateTime is not null
        defaultCmContextShouldBeFound("contextDateTime.specified=true");

        // Get all the cmContextList where contextDateTime is null
        defaultCmContextShouldNotBeFound("contextDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmContextsByRequestIsEqualToSomething() throws Exception {
        // Initialize the database
        CmRequest request = CmRequestResourceIntTest.createEntity(em);
        em.persist(request);
        em.flush();
        cmContext.setRequest(request);
        cmContextRepository.saveAndFlush(cmContext);
        Long requestId = request.getId();

        // Get all the cmContextList where request equals to requestId
        defaultCmContextShouldBeFound("requestId.equals=" + requestId);

        // Get all the cmContextList where request equals to requestId + 1
        defaultCmContextShouldNotBeFound("requestId.equals=" + (requestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCmContextShouldBeFound(String filter) throws Exception {
        restCmContextMockMvc.perform(get("/api/cm-contexts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmContext.getId().intValue())))
            .andExpect(jsonPath("$.[*].contextId").value(hasItem(DEFAULT_CONTEXT_ID.intValue())))
            .andExpect(jsonPath("$.[*].contextType").value(hasItem(DEFAULT_CONTEXT_TYPE)))
            .andExpect(jsonPath("$.[*].contextName").value(hasItem(DEFAULT_CONTEXT_NAME.toString())))
            .andExpect(jsonPath("$.[*].contextValue").value(hasItem(DEFAULT_CONTEXT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].contextDateTime").value(hasItem(DEFAULT_CONTEXT_DATE_TIME.toString())));

        // Check, that the count call also returns 1
        restCmContextMockMvc.perform(get("/api/cm-contexts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCmContextShouldNotBeFound(String filter) throws Exception {
        restCmContextMockMvc.perform(get("/api/cm-contexts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCmContextMockMvc.perform(get("/api/cm-contexts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCmContext() throws Exception {
        // Get the cmContext
        restCmContextMockMvc.perform(get("/api/cm-contexts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmContext() throws Exception {
        // Initialize the database
        cmContextService.save(cmContext);

        int databaseSizeBeforeUpdate = cmContextRepository.findAll().size();

        // Update the cmContext
        CmContext updatedCmContext = cmContextRepository.findById(cmContext.getId()).get();
        // Disconnect from session so that the updates on updatedCmContext are not directly saved in db
        em.detach(updatedCmContext);
        updatedCmContext
            .contextId(UPDATED_CONTEXT_ID)
            .contextType(UPDATED_CONTEXT_TYPE)
            .contextName(UPDATED_CONTEXT_NAME)
            .contextValue(UPDATED_CONTEXT_VALUE)
            .contextDateTime(UPDATED_CONTEXT_DATE_TIME);

        restCmContextMockMvc.perform(put("/api/cm-contexts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCmContext)))
            .andExpect(status().isOk());

        // Validate the CmContext in the database
        List<CmContext> cmContextList = cmContextRepository.findAll();
        assertThat(cmContextList).hasSize(databaseSizeBeforeUpdate);
        CmContext testCmContext = cmContextList.get(cmContextList.size() - 1);
        assertThat(testCmContext.getContextId()).isEqualTo(UPDATED_CONTEXT_ID);
        assertThat(testCmContext.getContextType()).isEqualTo(UPDATED_CONTEXT_TYPE);
        assertThat(testCmContext.getContextName()).isEqualTo(UPDATED_CONTEXT_NAME);
        assertThat(testCmContext.getContextValue()).isEqualTo(UPDATED_CONTEXT_VALUE);
        assertThat(testCmContext.getContextDateTime()).isEqualTo(UPDATED_CONTEXT_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCmContext() throws Exception {
        int databaseSizeBeforeUpdate = cmContextRepository.findAll().size();

        // Create the CmContext

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCmContextMockMvc.perform(put("/api/cm-contexts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmContext)))
            .andExpect(status().isBadRequest());

        // Validate the CmContext in the database
        List<CmContext> cmContextList = cmContextRepository.findAll();
        assertThat(cmContextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCmContext() throws Exception {
        // Initialize the database
        cmContextService.save(cmContext);

        int databaseSizeBeforeDelete = cmContextRepository.findAll().size();

        // Get the cmContext
        restCmContextMockMvc.perform(delete("/api/cm-contexts/{id}", cmContext.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CmContext> cmContextList = cmContextRepository.findAll();
        assertThat(cmContextList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CmContext.class);
        CmContext cmContext1 = new CmContext();
        cmContext1.setId(1L);
        CmContext cmContext2 = new CmContext();
        cmContext2.setId(cmContext1.getId());
        assertThat(cmContext1).isEqualTo(cmContext2);
        cmContext2.setId(2L);
        assertThat(cmContext1).isNotEqualTo(cmContext2);
        cmContext1.setId(null);
        assertThat(cmContext1).isNotEqualTo(cmContext2);
    }
}

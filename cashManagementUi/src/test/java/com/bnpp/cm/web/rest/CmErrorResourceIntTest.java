package com.bnpp.cm.web.rest;

import com.bnpp.cm.CashManagementUiApp;

import com.bnpp.cm.domain.CmError;
import com.bnpp.cm.domain.CmRequest;
import com.bnpp.cm.repository.CmErrorRepository;
import com.bnpp.cm.service.CmErrorService;
import com.bnpp.cm.web.rest.errors.ExceptionTranslator;
import com.bnpp.cm.service.dto.CmErrorCriteria;
import com.bnpp.cm.service.CmErrorQueryService;

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
 * Test class for the CmErrorResource REST controller.
 *
 * @see CmErrorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashManagementUiApp.class)
public class CmErrorResourceIntTest {

    private static final Integer DEFAULT_ERROR_COMPONENT = 1;
    private static final Integer UPDATED_ERROR_COMPONENT = 2;

    private static final String DEFAULT_ERROR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_STACK_TRACE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_STACK_TRACE = "BBBBBBBBBB";

    private static final Instant DEFAULT_ERRORN_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ERRORN_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CmErrorRepository cmErrorRepository;

    @Autowired
    private CmErrorService cmErrorService;

    @Autowired
    private CmErrorQueryService cmErrorQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCmErrorMockMvc;

    private CmError cmError;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CmErrorResource cmErrorResource = new CmErrorResource(cmErrorService, cmErrorQueryService);
        this.restCmErrorMockMvc = MockMvcBuilders.standaloneSetup(cmErrorResource)
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
    public static CmError createEntity(EntityManager em) {
        CmError cmError = new CmError()
            .errorComponent(DEFAULT_ERROR_COMPONENT)
            .errorCode(DEFAULT_ERROR_CODE)
            .errorDescription(DEFAULT_ERROR_DESCRIPTION)
            .errorStackTrace(DEFAULT_ERROR_STACK_TRACE)
            .errornDateTime(DEFAULT_ERRORN_DATE_TIME);
        return cmError;
    }

    @Before
    public void initTest() {
        cmError = createEntity(em);
    }

    @Test
    @Transactional
    public void createCmError() throws Exception {
        int databaseSizeBeforeCreate = cmErrorRepository.findAll().size();

        // Create the CmError
        restCmErrorMockMvc.perform(post("/api/cm-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmError)))
            .andExpect(status().isCreated());

        // Validate the CmError in the database
        List<CmError> cmErrorList = cmErrorRepository.findAll();
        assertThat(cmErrorList).hasSize(databaseSizeBeforeCreate + 1);
        CmError testCmError = cmErrorList.get(cmErrorList.size() - 1);
        assertThat(testCmError.getErrorComponent()).isEqualTo(DEFAULT_ERROR_COMPONENT);
        assertThat(testCmError.getErrorCode()).isEqualTo(DEFAULT_ERROR_CODE);
        assertThat(testCmError.getErrorDescription()).isEqualTo(DEFAULT_ERROR_DESCRIPTION);
        assertThat(testCmError.getErrorStackTrace()).isEqualTo(DEFAULT_ERROR_STACK_TRACE);
        assertThat(testCmError.getErrornDateTime()).isEqualTo(DEFAULT_ERRORN_DATE_TIME);
    }

    @Test
    @Transactional
    public void createCmErrorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cmErrorRepository.findAll().size();

        // Create the CmError with an existing ID
        cmError.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCmErrorMockMvc.perform(post("/api/cm-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmError)))
            .andExpect(status().isBadRequest());

        // Validate the CmError in the database
        List<CmError> cmErrorList = cmErrorRepository.findAll();
        assertThat(cmErrorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCmErrors() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList
        restCmErrorMockMvc.perform(get("/api/cm-errors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmError.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorComponent").value(hasItem(DEFAULT_ERROR_COMPONENT)))
            .andExpect(jsonPath("$.[*].errorCode").value(hasItem(DEFAULT_ERROR_CODE.toString())))
            .andExpect(jsonPath("$.[*].errorDescription").value(hasItem(DEFAULT_ERROR_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].errorStackTrace").value(hasItem(DEFAULT_ERROR_STACK_TRACE.toString())))
            .andExpect(jsonPath("$.[*].errornDateTime").value(hasItem(DEFAULT_ERRORN_DATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getCmError() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get the cmError
        restCmErrorMockMvc.perform(get("/api/cm-errors/{id}", cmError.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cmError.getId().intValue()))
            .andExpect(jsonPath("$.errorComponent").value(DEFAULT_ERROR_COMPONENT))
            .andExpect(jsonPath("$.errorCode").value(DEFAULT_ERROR_CODE.toString()))
            .andExpect(jsonPath("$.errorDescription").value(DEFAULT_ERROR_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.errorStackTrace").value(DEFAULT_ERROR_STACK_TRACE.toString()))
            .andExpect(jsonPath("$.errornDateTime").value(DEFAULT_ERRORN_DATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorComponentIsEqualToSomething() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorComponent equals to DEFAULT_ERROR_COMPONENT
        defaultCmErrorShouldBeFound("errorComponent.equals=" + DEFAULT_ERROR_COMPONENT);

        // Get all the cmErrorList where errorComponent equals to UPDATED_ERROR_COMPONENT
        defaultCmErrorShouldNotBeFound("errorComponent.equals=" + UPDATED_ERROR_COMPONENT);
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorComponentIsInShouldWork() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorComponent in DEFAULT_ERROR_COMPONENT or UPDATED_ERROR_COMPONENT
        defaultCmErrorShouldBeFound("errorComponent.in=" + DEFAULT_ERROR_COMPONENT + "," + UPDATED_ERROR_COMPONENT);

        // Get all the cmErrorList where errorComponent equals to UPDATED_ERROR_COMPONENT
        defaultCmErrorShouldNotBeFound("errorComponent.in=" + UPDATED_ERROR_COMPONENT);
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorComponentIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorComponent is not null
        defaultCmErrorShouldBeFound("errorComponent.specified=true");

        // Get all the cmErrorList where errorComponent is null
        defaultCmErrorShouldNotBeFound("errorComponent.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorComponentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorComponent greater than or equals to DEFAULT_ERROR_COMPONENT
        defaultCmErrorShouldBeFound("errorComponent.greaterOrEqualThan=" + DEFAULT_ERROR_COMPONENT);

        // Get all the cmErrorList where errorComponent greater than or equals to UPDATED_ERROR_COMPONENT
        defaultCmErrorShouldNotBeFound("errorComponent.greaterOrEqualThan=" + UPDATED_ERROR_COMPONENT);
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorComponentIsLessThanSomething() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorComponent less than or equals to DEFAULT_ERROR_COMPONENT
        defaultCmErrorShouldNotBeFound("errorComponent.lessThan=" + DEFAULT_ERROR_COMPONENT);

        // Get all the cmErrorList where errorComponent less than or equals to UPDATED_ERROR_COMPONENT
        defaultCmErrorShouldBeFound("errorComponent.lessThan=" + UPDATED_ERROR_COMPONENT);
    }


    @Test
    @Transactional
    public void getAllCmErrorsByErrorCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorCode equals to DEFAULT_ERROR_CODE
        defaultCmErrorShouldBeFound("errorCode.equals=" + DEFAULT_ERROR_CODE);

        // Get all the cmErrorList where errorCode equals to UPDATED_ERROR_CODE
        defaultCmErrorShouldNotBeFound("errorCode.equals=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorCode in DEFAULT_ERROR_CODE or UPDATED_ERROR_CODE
        defaultCmErrorShouldBeFound("errorCode.in=" + DEFAULT_ERROR_CODE + "," + UPDATED_ERROR_CODE);

        // Get all the cmErrorList where errorCode equals to UPDATED_ERROR_CODE
        defaultCmErrorShouldNotBeFound("errorCode.in=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorCode is not null
        defaultCmErrorShouldBeFound("errorCode.specified=true");

        // Get all the cmErrorList where errorCode is null
        defaultCmErrorShouldNotBeFound("errorCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorDescription equals to DEFAULT_ERROR_DESCRIPTION
        defaultCmErrorShouldBeFound("errorDescription.equals=" + DEFAULT_ERROR_DESCRIPTION);

        // Get all the cmErrorList where errorDescription equals to UPDATED_ERROR_DESCRIPTION
        defaultCmErrorShouldNotBeFound("errorDescription.equals=" + UPDATED_ERROR_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorDescription in DEFAULT_ERROR_DESCRIPTION or UPDATED_ERROR_DESCRIPTION
        defaultCmErrorShouldBeFound("errorDescription.in=" + DEFAULT_ERROR_DESCRIPTION + "," + UPDATED_ERROR_DESCRIPTION);

        // Get all the cmErrorList where errorDescription equals to UPDATED_ERROR_DESCRIPTION
        defaultCmErrorShouldNotBeFound("errorDescription.in=" + UPDATED_ERROR_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorDescription is not null
        defaultCmErrorShouldBeFound("errorDescription.specified=true");

        // Get all the cmErrorList where errorDescription is null
        defaultCmErrorShouldNotBeFound("errorDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorStackTraceIsEqualToSomething() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorStackTrace equals to DEFAULT_ERROR_STACK_TRACE
        defaultCmErrorShouldBeFound("errorStackTrace.equals=" + DEFAULT_ERROR_STACK_TRACE);

        // Get all the cmErrorList where errorStackTrace equals to UPDATED_ERROR_STACK_TRACE
        defaultCmErrorShouldNotBeFound("errorStackTrace.equals=" + UPDATED_ERROR_STACK_TRACE);
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorStackTraceIsInShouldWork() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorStackTrace in DEFAULT_ERROR_STACK_TRACE or UPDATED_ERROR_STACK_TRACE
        defaultCmErrorShouldBeFound("errorStackTrace.in=" + DEFAULT_ERROR_STACK_TRACE + "," + UPDATED_ERROR_STACK_TRACE);

        // Get all the cmErrorList where errorStackTrace equals to UPDATED_ERROR_STACK_TRACE
        defaultCmErrorShouldNotBeFound("errorStackTrace.in=" + UPDATED_ERROR_STACK_TRACE);
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrorStackTraceIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errorStackTrace is not null
        defaultCmErrorShouldBeFound("errorStackTrace.specified=true");

        // Get all the cmErrorList where errorStackTrace is null
        defaultCmErrorShouldNotBeFound("errorStackTrace.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrornDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errornDateTime equals to DEFAULT_ERRORN_DATE_TIME
        defaultCmErrorShouldBeFound("errornDateTime.equals=" + DEFAULT_ERRORN_DATE_TIME);

        // Get all the cmErrorList where errornDateTime equals to UPDATED_ERRORN_DATE_TIME
        defaultCmErrorShouldNotBeFound("errornDateTime.equals=" + UPDATED_ERRORN_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrornDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errornDateTime in DEFAULT_ERRORN_DATE_TIME or UPDATED_ERRORN_DATE_TIME
        defaultCmErrorShouldBeFound("errornDateTime.in=" + DEFAULT_ERRORN_DATE_TIME + "," + UPDATED_ERRORN_DATE_TIME);

        // Get all the cmErrorList where errornDateTime equals to UPDATED_ERRORN_DATE_TIME
        defaultCmErrorShouldNotBeFound("errornDateTime.in=" + UPDATED_ERRORN_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCmErrorsByErrornDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cmErrorRepository.saveAndFlush(cmError);

        // Get all the cmErrorList where errornDateTime is not null
        defaultCmErrorShouldBeFound("errornDateTime.specified=true");

        // Get all the cmErrorList where errornDateTime is null
        defaultCmErrorShouldNotBeFound("errornDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCmErrorsByRequestIsEqualToSomething() throws Exception {
        // Initialize the database
        CmRequest request = CmRequestResourceIntTest.createEntity(em);
        em.persist(request);
        em.flush();
        cmError.setRequest(request);
        cmErrorRepository.saveAndFlush(cmError);
        Long requestId = request.getId();

        // Get all the cmErrorList where request equals to requestId
        defaultCmErrorShouldBeFound("requestId.equals=" + requestId);

        // Get all the cmErrorList where request equals to requestId + 1
        defaultCmErrorShouldNotBeFound("requestId.equals=" + (requestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCmErrorShouldBeFound(String filter) throws Exception {
        restCmErrorMockMvc.perform(get("/api/cm-errors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cmError.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorComponent").value(hasItem(DEFAULT_ERROR_COMPONENT)))
            .andExpect(jsonPath("$.[*].errorCode").value(hasItem(DEFAULT_ERROR_CODE.toString())))
            .andExpect(jsonPath("$.[*].errorDescription").value(hasItem(DEFAULT_ERROR_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].errorStackTrace").value(hasItem(DEFAULT_ERROR_STACK_TRACE.toString())))
            .andExpect(jsonPath("$.[*].errornDateTime").value(hasItem(DEFAULT_ERRORN_DATE_TIME.toString())));

        // Check, that the count call also returns 1
        restCmErrorMockMvc.perform(get("/api/cm-errors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCmErrorShouldNotBeFound(String filter) throws Exception {
        restCmErrorMockMvc.perform(get("/api/cm-errors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCmErrorMockMvc.perform(get("/api/cm-errors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCmError() throws Exception {
        // Get the cmError
        restCmErrorMockMvc.perform(get("/api/cm-errors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCmError() throws Exception {
        // Initialize the database
        cmErrorService.save(cmError);

        int databaseSizeBeforeUpdate = cmErrorRepository.findAll().size();

        // Update the cmError
        CmError updatedCmError = cmErrorRepository.findById(cmError.getId()).get();
        // Disconnect from session so that the updates on updatedCmError are not directly saved in db
        em.detach(updatedCmError);
        updatedCmError
            .errorComponent(UPDATED_ERROR_COMPONENT)
            .errorCode(UPDATED_ERROR_CODE)
            .errorDescription(UPDATED_ERROR_DESCRIPTION)
            .errorStackTrace(UPDATED_ERROR_STACK_TRACE)
            .errornDateTime(UPDATED_ERRORN_DATE_TIME);

        restCmErrorMockMvc.perform(put("/api/cm-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCmError)))
            .andExpect(status().isOk());

        // Validate the CmError in the database
        List<CmError> cmErrorList = cmErrorRepository.findAll();
        assertThat(cmErrorList).hasSize(databaseSizeBeforeUpdate);
        CmError testCmError = cmErrorList.get(cmErrorList.size() - 1);
        assertThat(testCmError.getErrorComponent()).isEqualTo(UPDATED_ERROR_COMPONENT);
        assertThat(testCmError.getErrorCode()).isEqualTo(UPDATED_ERROR_CODE);
        assertThat(testCmError.getErrorDescription()).isEqualTo(UPDATED_ERROR_DESCRIPTION);
        assertThat(testCmError.getErrorStackTrace()).isEqualTo(UPDATED_ERROR_STACK_TRACE);
        assertThat(testCmError.getErrornDateTime()).isEqualTo(UPDATED_ERRORN_DATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCmError() throws Exception {
        int databaseSizeBeforeUpdate = cmErrorRepository.findAll().size();

        // Create the CmError

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCmErrorMockMvc.perform(put("/api/cm-errors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cmError)))
            .andExpect(status().isBadRequest());

        // Validate the CmError in the database
        List<CmError> cmErrorList = cmErrorRepository.findAll();
        assertThat(cmErrorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCmError() throws Exception {
        // Initialize the database
        cmErrorService.save(cmError);

        int databaseSizeBeforeDelete = cmErrorRepository.findAll().size();

        // Get the cmError
        restCmErrorMockMvc.perform(delete("/api/cm-errors/{id}", cmError.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CmError> cmErrorList = cmErrorRepository.findAll();
        assertThat(cmErrorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CmError.class);
        CmError cmError1 = new CmError();
        cmError1.setId(1L);
        CmError cmError2 = new CmError();
        cmError2.setId(cmError1.getId());
        assertThat(cmError1).isEqualTo(cmError2);
        cmError2.setId(2L);
        assertThat(cmError1).isNotEqualTo(cmError2);
        cmError1.setId(null);
        assertThat(cmError1).isNotEqualTo(cmError2);
    }
}

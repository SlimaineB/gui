package com.bnpp.cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bnpp.cm.domain.CmError;
import com.bnpp.cm.service.CmErrorService;
import com.bnpp.cm.web.rest.errors.BadRequestAlertException;
import com.bnpp.cm.web.rest.util.HeaderUtil;
import com.bnpp.cm.web.rest.util.PaginationUtil;
import com.bnpp.cm.service.dto.CmErrorCriteria;
import com.bnpp.cm.service.CmErrorQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CmError.
 */
@RestController
@RequestMapping("/api")
public class CmErrorResource {

    private final Logger log = LoggerFactory.getLogger(CmErrorResource.class);

    private static final String ENTITY_NAME = "cmError";

    private final CmErrorService cmErrorService;

    private final CmErrorQueryService cmErrorQueryService;

    public CmErrorResource(CmErrorService cmErrorService, CmErrorQueryService cmErrorQueryService) {
        this.cmErrorService = cmErrorService;
        this.cmErrorQueryService = cmErrorQueryService;
    }

    /**
     * POST  /cm-errors : Create a new cmError.
     *
     * @param cmError the cmError to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cmError, or with status 400 (Bad Request) if the cmError has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cm-errors")
    @Timed
    public ResponseEntity<CmError> createCmError(@RequestBody CmError cmError) throws URISyntaxException {
        log.debug("REST request to save CmError : {}", cmError);
        if (cmError.getId() != null) {
            throw new BadRequestAlertException("A new cmError cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmError result = cmErrorService.save(cmError);
        return ResponseEntity.created(new URI("/api/cm-errors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cm-errors : Updates an existing cmError.
     *
     * @param cmError the cmError to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cmError,
     * or with status 400 (Bad Request) if the cmError is not valid,
     * or with status 500 (Internal Server Error) if the cmError couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cm-errors")
    @Timed
    public ResponseEntity<CmError> updateCmError(@RequestBody CmError cmError) throws URISyntaxException {
        log.debug("REST request to update CmError : {}", cmError);
        if (cmError.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmError result = cmErrorService.save(cmError);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cmError.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cm-errors : get all the cmErrors.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of cmErrors in body
     */
    @GetMapping("/cm-errors")
    @Timed
    public ResponseEntity<List<CmError>> getAllCmErrors(CmErrorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CmErrors by criteria: {}", criteria);
        Page<CmError> page = cmErrorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cm-errors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /cm-errors/count : count all the cmErrors.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/cm-errors/count")
    @Timed
    public ResponseEntity<Long> countCmErrors(CmErrorCriteria criteria) {
        log.debug("REST request to count CmErrors by criteria: {}", criteria);
        return ResponseEntity.ok().body(cmErrorQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /cm-errors/:id : get the "id" cmError.
     *
     * @param id the id of the cmError to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cmError, or with status 404 (Not Found)
     */
    @GetMapping("/cm-errors/{id}")
    @Timed
    public ResponseEntity<CmError> getCmError(@PathVariable Long id) {
        log.debug("REST request to get CmError : {}", id);
        Optional<CmError> cmError = cmErrorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmError);
    }

    /**
     * DELETE  /cm-errors/:id : delete the "id" cmError.
     *
     * @param id the id of the cmError to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cm-errors/{id}")
    @Timed
    public ResponseEntity<Void> deleteCmError(@PathVariable Long id) {
        log.debug("REST request to delete CmError : {}", id);
        cmErrorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

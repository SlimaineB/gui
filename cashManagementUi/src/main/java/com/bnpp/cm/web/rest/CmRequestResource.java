package com.bnpp.cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bnpp.cm.domain.CmRequest;
import com.bnpp.cm.service.CmRequestService;
import com.bnpp.cm.web.rest.errors.BadRequestAlertException;
import com.bnpp.cm.web.rest.util.HeaderUtil;
import com.bnpp.cm.web.rest.util.PaginationUtil;
import com.bnpp.cm.service.dto.CmRequestCriteria;
import com.bnpp.cm.service.CmRequestQueryService;
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
 * REST controller for managing CmRequest.
 */
@RestController
@RequestMapping("/api")
public class CmRequestResource {

    private final Logger log = LoggerFactory.getLogger(CmRequestResource.class);

    private static final String ENTITY_NAME = "cmRequest";

    private final CmRequestService cmRequestService;

    private final CmRequestQueryService cmRequestQueryService;

    public CmRequestResource(CmRequestService cmRequestService, CmRequestQueryService cmRequestQueryService) {
        this.cmRequestService = cmRequestService;
        this.cmRequestQueryService = cmRequestQueryService;
    }

    /**
     * POST  /cm-requests : Create a new cmRequest.
     *
     * @param cmRequest the cmRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cmRequest, or with status 400 (Bad Request) if the cmRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cm-requests")
    @Timed
    public ResponseEntity<CmRequest> createCmRequest(@RequestBody CmRequest cmRequest) throws URISyntaxException {
        log.debug("REST request to save CmRequest : {}", cmRequest);
        if (cmRequest.getId() != null) {
            throw new BadRequestAlertException("A new cmRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmRequest result = cmRequestService.save(cmRequest);
        return ResponseEntity.created(new URI("/api/cm-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cm-requests : Updates an existing cmRequest.
     *
     * @param cmRequest the cmRequest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cmRequest,
     * or with status 400 (Bad Request) if the cmRequest is not valid,
     * or with status 500 (Internal Server Error) if the cmRequest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cm-requests")
    @Timed
    public ResponseEntity<CmRequest> updateCmRequest(@RequestBody CmRequest cmRequest) throws URISyntaxException {
        log.debug("REST request to update CmRequest : {}", cmRequest);
        if (cmRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmRequest result = cmRequestService.save(cmRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cmRequest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cm-requests : get all the cmRequests.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of cmRequests in body
     */
    @GetMapping("/cm-requests")
    @Timed
    public ResponseEntity<List<CmRequest>> getAllCmRequests(CmRequestCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CmRequests by criteria: {}", criteria);
        Page<CmRequest> page = cmRequestQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cm-requests");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /cm-requests/count : count all the cmRequests.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/cm-requests/count")
    @Timed
    public ResponseEntity<Long> countCmRequests(CmRequestCriteria criteria) {
        log.debug("REST request to count CmRequests by criteria: {}", criteria);
        return ResponseEntity.ok().body(cmRequestQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /cm-requests/:id : get the "id" cmRequest.
     *
     * @param id the id of the cmRequest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cmRequest, or with status 404 (Not Found)
     */
    @GetMapping("/cm-requests/{id}")
    @Timed
    public ResponseEntity<CmRequest> getCmRequest(@PathVariable Long id) {
        log.debug("REST request to get CmRequest : {}", id);
        Optional<CmRequest> cmRequest = cmRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmRequest);
    }

    /**
     * DELETE  /cm-requests/:id : delete the "id" cmRequest.
     *
     * @param id the id of the cmRequest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cm-requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteCmRequest(@PathVariable Long id) {
        log.debug("REST request to delete CmRequest : {}", id);
        cmRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

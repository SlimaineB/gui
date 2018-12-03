package com.bnpp.cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bnpp.cm.domain.CmContext;
import com.bnpp.cm.service.CmContextService;
import com.bnpp.cm.web.rest.errors.BadRequestAlertException;
import com.bnpp.cm.web.rest.util.HeaderUtil;
import com.bnpp.cm.web.rest.util.PaginationUtil;
import com.bnpp.cm.service.dto.CmContextCriteria;
import com.bnpp.cm.service.CmContextQueryService;
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
 * REST controller for managing CmContext.
 */
@RestController
@RequestMapping("/api")
public class CmContextResource {

    private final Logger log = LoggerFactory.getLogger(CmContextResource.class);

    private static final String ENTITY_NAME = "cmContext";

    private final CmContextService cmContextService;

    private final CmContextQueryService cmContextQueryService;

    public CmContextResource(CmContextService cmContextService, CmContextQueryService cmContextQueryService) {
        this.cmContextService = cmContextService;
        this.cmContextQueryService = cmContextQueryService;
    }

    /**
     * POST  /cm-contexts : Create a new cmContext.
     *
     * @param cmContext the cmContext to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cmContext, or with status 400 (Bad Request) if the cmContext has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cm-contexts")
    @Timed
    public ResponseEntity<CmContext> createCmContext(@RequestBody CmContext cmContext) throws URISyntaxException {
        log.debug("REST request to save CmContext : {}", cmContext);
        if (cmContext.getId() != null) {
            throw new BadRequestAlertException("A new cmContext cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmContext result = cmContextService.save(cmContext);
        return ResponseEntity.created(new URI("/api/cm-contexts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cm-contexts : Updates an existing cmContext.
     *
     * @param cmContext the cmContext to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cmContext,
     * or with status 400 (Bad Request) if the cmContext is not valid,
     * or with status 500 (Internal Server Error) if the cmContext couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cm-contexts")
    @Timed
    public ResponseEntity<CmContext> updateCmContext(@RequestBody CmContext cmContext) throws URISyntaxException {
        log.debug("REST request to update CmContext : {}", cmContext);
        if (cmContext.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmContext result = cmContextService.save(cmContext);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cmContext.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cm-contexts : get all the cmContexts.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of cmContexts in body
     */
    @GetMapping("/cm-contexts")
    @Timed
    public ResponseEntity<List<CmContext>> getAllCmContexts(CmContextCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CmContexts by criteria: {}", criteria);
        Page<CmContext> page = cmContextQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cm-contexts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /cm-contexts/count : count all the cmContexts.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/cm-contexts/count")
    @Timed
    public ResponseEntity<Long> countCmContexts(CmContextCriteria criteria) {
        log.debug("REST request to count CmContexts by criteria: {}", criteria);
        return ResponseEntity.ok().body(cmContextQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /cm-contexts/:id : get the "id" cmContext.
     *
     * @param id the id of the cmContext to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cmContext, or with status 404 (Not Found)
     */
    @GetMapping("/cm-contexts/{id}")
    @Timed
    public ResponseEntity<CmContext> getCmContext(@PathVariable Long id) {
        log.debug("REST request to get CmContext : {}", id);
        Optional<CmContext> cmContext = cmContextService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmContext);
    }

    /**
     * DELETE  /cm-contexts/:id : delete the "id" cmContext.
     *
     * @param id the id of the cmContext to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cm-contexts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCmContext(@PathVariable Long id) {
        log.debug("REST request to delete CmContext : {}", id);
        cmContextService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

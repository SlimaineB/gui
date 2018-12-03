package com.bnpp.cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bnpp.cm.domain.CmAction;
import com.bnpp.cm.service.CmActionService;
import com.bnpp.cm.web.rest.errors.BadRequestAlertException;
import com.bnpp.cm.web.rest.util.HeaderUtil;
import com.bnpp.cm.web.rest.util.PaginationUtil;
import com.bnpp.cm.service.dto.CmActionCriteria;
import com.bnpp.cm.service.CmActionQueryService;
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
 * REST controller for managing CmAction.
 */
@RestController
@RequestMapping("/api")
public class CmActionResource {

    private final Logger log = LoggerFactory.getLogger(CmActionResource.class);

    private static final String ENTITY_NAME = "cmAction";

    private final CmActionService cmActionService;

    private final CmActionQueryService cmActionQueryService;

    public CmActionResource(CmActionService cmActionService, CmActionQueryService cmActionQueryService) {
        this.cmActionService = cmActionService;
        this.cmActionQueryService = cmActionQueryService;
    }

    /**
     * POST  /cm-actions : Create a new cmAction.
     *
     * @param cmAction the cmAction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cmAction, or with status 400 (Bad Request) if the cmAction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cm-actions")
    @Timed
    public ResponseEntity<CmAction> createCmAction(@RequestBody CmAction cmAction) throws URISyntaxException {
        log.debug("REST request to save CmAction : {}", cmAction);
        if (cmAction.getId() != null) {
            throw new BadRequestAlertException("A new cmAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmAction result = cmActionService.save(cmAction);
        return ResponseEntity.created(new URI("/api/cm-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cm-actions : Updates an existing cmAction.
     *
     * @param cmAction the cmAction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cmAction,
     * or with status 400 (Bad Request) if the cmAction is not valid,
     * or with status 500 (Internal Server Error) if the cmAction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cm-actions")
    @Timed
    public ResponseEntity<CmAction> updateCmAction(@RequestBody CmAction cmAction) throws URISyntaxException {
        log.debug("REST request to update CmAction : {}", cmAction);
        if (cmAction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmAction result = cmActionService.save(cmAction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cmAction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cm-actions : get all the cmActions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of cmActions in body
     */
    @GetMapping("/cm-actions")
    @Timed
    public ResponseEntity<List<CmAction>> getAllCmActions(CmActionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CmActions by criteria: {}", criteria);
        Page<CmAction> page = cmActionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cm-actions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /cm-actions/count : count all the cmActions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/cm-actions/count")
    @Timed
    public ResponseEntity<Long> countCmActions(CmActionCriteria criteria) {
        log.debug("REST request to count CmActions by criteria: {}", criteria);
        return ResponseEntity.ok().body(cmActionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /cm-actions/:id : get the "id" cmAction.
     *
     * @param id the id of the cmAction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cmAction, or with status 404 (Not Found)
     */
    @GetMapping("/cm-actions/{id}")
    @Timed
    public ResponseEntity<CmAction> getCmAction(@PathVariable Long id) {
        log.debug("REST request to get CmAction : {}", id);
        Optional<CmAction> cmAction = cmActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmAction);
    }

    /**
     * DELETE  /cm-actions/:id : delete the "id" cmAction.
     *
     * @param id the id of the cmAction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cm-actions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCmAction(@PathVariable Long id) {
        log.debug("REST request to delete CmAction : {}", id);
        cmActionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.bnpp.cm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bnpp.cm.domain.CmMock;
import com.bnpp.cm.service.CmMockService;
import com.bnpp.cm.web.rest.errors.BadRequestAlertException;
import com.bnpp.cm.web.rest.util.HeaderUtil;
import com.bnpp.cm.web.rest.util.PaginationUtil;
import com.bnpp.cm.service.dto.CmMockCriteria;
import com.bnpp.cm.service.CmMockQueryService;
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
 * REST controller for managing CmMock.
 */
@RestController
@RequestMapping("/api")
public class CmMockResource {

    private final Logger log = LoggerFactory.getLogger(CmMockResource.class);

    private static final String ENTITY_NAME = "cmMock";

    private final CmMockService cmMockService;

    private final CmMockQueryService cmMockQueryService;

    public CmMockResource(CmMockService cmMockService, CmMockQueryService cmMockQueryService) {
        this.cmMockService = cmMockService;
        this.cmMockQueryService = cmMockQueryService;
    }

    /**
     * POST  /cm-mocks : Create a new cmMock.
     *
     * @param cmMock the cmMock to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cmMock, or with status 400 (Bad Request) if the cmMock has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cm-mocks")
    @Timed
    public ResponseEntity<CmMock> createCmMock(@RequestBody CmMock cmMock) throws URISyntaxException {
        log.debug("REST request to save CmMock : {}", cmMock);
        if (cmMock.getId() != null) {
            throw new BadRequestAlertException("A new cmMock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CmMock result = cmMockService.save(cmMock);
        return ResponseEntity.created(new URI("/api/cm-mocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cm-mocks : Updates an existing cmMock.
     *
     * @param cmMock the cmMock to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cmMock,
     * or with status 400 (Bad Request) if the cmMock is not valid,
     * or with status 500 (Internal Server Error) if the cmMock couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cm-mocks")
    @Timed
    public ResponseEntity<CmMock> updateCmMock(@RequestBody CmMock cmMock) throws URISyntaxException {
        log.debug("REST request to update CmMock : {}", cmMock);
        if (cmMock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CmMock result = cmMockService.save(cmMock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cmMock.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cm-mocks : get all the cmMocks.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of cmMocks in body
     */
    @GetMapping("/cm-mocks")
    @Timed
    public ResponseEntity<List<CmMock>> getAllCmMocks(CmMockCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CmMocks by criteria: {}", criteria);
        Page<CmMock> page = cmMockQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cm-mocks");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /cm-mocks/count : count all the cmMocks.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/cm-mocks/count")
    @Timed
    public ResponseEntity<Long> countCmMocks(CmMockCriteria criteria) {
        log.debug("REST request to count CmMocks by criteria: {}", criteria);
        return ResponseEntity.ok().body(cmMockQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /cm-mocks/:id : get the "id" cmMock.
     *
     * @param id the id of the cmMock to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cmMock, or with status 404 (Not Found)
     */
    @GetMapping("/cm-mocks/{id}")
    @Timed
    public ResponseEntity<CmMock> getCmMock(@PathVariable Long id) {
        log.debug("REST request to get CmMock : {}", id);
        Optional<CmMock> cmMock = cmMockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cmMock);
    }

    /**
     * DELETE  /cm-mocks/:id : delete the "id" cmMock.
     *
     * @param id the id of the cmMock to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cm-mocks/{id}")
    @Timed
    public ResponseEntity<Void> deleteCmMock(@PathVariable Long id) {
        log.debug("REST request to delete CmMock : {}", id);
        cmMockService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

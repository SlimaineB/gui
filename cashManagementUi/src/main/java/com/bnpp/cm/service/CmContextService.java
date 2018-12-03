package com.bnpp.cm.service;

import com.bnpp.cm.domain.CmContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CmContext.
 */
public interface CmContextService {

    /**
     * Save a cmContext.
     *
     * @param cmContext the entity to save
     * @return the persisted entity
     */
    CmContext save(CmContext cmContext);

    /**
     * Get all the cmContexts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CmContext> findAll(Pageable pageable);


    /**
     * Get the "id" cmContext.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CmContext> findOne(Long id);

    /**
     * Delete the "id" cmContext.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

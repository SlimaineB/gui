package com.bnpp.cm.service;

import com.bnpp.cm.domain.CmAction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CmAction.
 */
public interface CmActionService {

    /**
     * Save a cmAction.
     *
     * @param cmAction the entity to save
     * @return the persisted entity
     */
    CmAction save(CmAction cmAction);

    /**
     * Get all the cmActions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CmAction> findAll(Pageable pageable);


    /**
     * Get the "id" cmAction.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CmAction> findOne(Long id);

    /**
     * Delete the "id" cmAction.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

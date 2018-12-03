package com.bnpp.cm.service;

import com.bnpp.cm.domain.CmError;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CmError.
 */
public interface CmErrorService {

    /**
     * Save a cmError.
     *
     * @param cmError the entity to save
     * @return the persisted entity
     */
    CmError save(CmError cmError);

    /**
     * Get all the cmErrors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CmError> findAll(Pageable pageable);


    /**
     * Get the "id" cmError.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CmError> findOne(Long id);

    /**
     * Delete the "id" cmError.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package com.bnpp.cm.service;

import com.bnpp.cm.domain.CmRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CmRequest.
 */
public interface CmRequestService {

    /**
     * Save a cmRequest.
     *
     * @param cmRequest the entity to save
     * @return the persisted entity
     */
    CmRequest save(CmRequest cmRequest);

    /**
     * Get all the cmRequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CmRequest> findAll(Pageable pageable);


    /**
     * Get the "id" cmRequest.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CmRequest> findOne(Long id);

    /**
     * Delete the "id" cmRequest.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

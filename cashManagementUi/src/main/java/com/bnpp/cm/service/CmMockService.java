package com.bnpp.cm.service;

import com.bnpp.cm.domain.CmMock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CmMock.
 */
public interface CmMockService {

    /**
     * Save a cmMock.
     *
     * @param cmMock the entity to save
     * @return the persisted entity
     */
    CmMock save(CmMock cmMock);

    /**
     * Get all the cmMocks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CmMock> findAll(Pageable pageable);


    /**
     * Get the "id" cmMock.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CmMock> findOne(Long id);

    /**
     * Delete the "id" cmMock.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

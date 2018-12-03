package com.bnpp.cm.service.impl;

import com.bnpp.cm.service.CmRequestService;
import com.bnpp.cm.domain.CmRequest;
import com.bnpp.cm.repository.CmRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CmRequest.
 */
@Service
@Transactional
public class CmRequestServiceImpl implements CmRequestService {

    private final Logger log = LoggerFactory.getLogger(CmRequestServiceImpl.class);

    private final CmRequestRepository cmRequestRepository;

    public CmRequestServiceImpl(CmRequestRepository cmRequestRepository) {
        this.cmRequestRepository = cmRequestRepository;
    }

    /**
     * Save a cmRequest.
     *
     * @param cmRequest the entity to save
     * @return the persisted entity
     */
    @Override
    public CmRequest save(CmRequest cmRequest) {
        log.debug("Request to save CmRequest : {}", cmRequest);
        return cmRequestRepository.save(cmRequest);
    }

    /**
     * Get all the cmRequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CmRequest> findAll(Pageable pageable) {
        log.debug("Request to get all CmRequests");
        return cmRequestRepository.findAll(pageable);
    }


    /**
     * Get one cmRequest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CmRequest> findOne(Long id) {
        log.debug("Request to get CmRequest : {}", id);
        return cmRequestRepository.findById(id);
    }

    /**
     * Delete the cmRequest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CmRequest : {}", id);
        cmRequestRepository.deleteById(id);
    }
}

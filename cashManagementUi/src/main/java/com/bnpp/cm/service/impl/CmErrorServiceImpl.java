package com.bnpp.cm.service.impl;

import com.bnpp.cm.service.CmErrorService;
import com.bnpp.cm.domain.CmError;
import com.bnpp.cm.repository.CmErrorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CmError.
 */
@Service
@Transactional
public class CmErrorServiceImpl implements CmErrorService {

    private final Logger log = LoggerFactory.getLogger(CmErrorServiceImpl.class);

    private final CmErrorRepository cmErrorRepository;

    public CmErrorServiceImpl(CmErrorRepository cmErrorRepository) {
        this.cmErrorRepository = cmErrorRepository;
    }

    /**
     * Save a cmError.
     *
     * @param cmError the entity to save
     * @return the persisted entity
     */
    @Override
    public CmError save(CmError cmError) {
        log.debug("Request to save CmError : {}", cmError);
        return cmErrorRepository.save(cmError);
    }

    /**
     * Get all the cmErrors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CmError> findAll(Pageable pageable) {
        log.debug("Request to get all CmErrors");
        return cmErrorRepository.findAll(pageable);
    }


    /**
     * Get one cmError by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CmError> findOne(Long id) {
        log.debug("Request to get CmError : {}", id);
        return cmErrorRepository.findById(id);
    }

    /**
     * Delete the cmError by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CmError : {}", id);
        cmErrorRepository.deleteById(id);
    }
}

package com.bnpp.cm.service.impl;

import com.bnpp.cm.service.CmContextService;
import com.bnpp.cm.domain.CmContext;
import com.bnpp.cm.repository.CmContextRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CmContext.
 */
@Service
@Transactional
public class CmContextServiceImpl implements CmContextService {

    private final Logger log = LoggerFactory.getLogger(CmContextServiceImpl.class);

    private final CmContextRepository cmContextRepository;

    public CmContextServiceImpl(CmContextRepository cmContextRepository) {
        this.cmContextRepository = cmContextRepository;
    }

    /**
     * Save a cmContext.
     *
     * @param cmContext the entity to save
     * @return the persisted entity
     */
    @Override
    public CmContext save(CmContext cmContext) {
        log.debug("Request to save CmContext : {}", cmContext);
        return cmContextRepository.save(cmContext);
    }

    /**
     * Get all the cmContexts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CmContext> findAll(Pageable pageable) {
        log.debug("Request to get all CmContexts");
        return cmContextRepository.findAll(pageable);
    }


    /**
     * Get one cmContext by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CmContext> findOne(Long id) {
        log.debug("Request to get CmContext : {}", id);
        return cmContextRepository.findById(id);
    }

    /**
     * Delete the cmContext by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CmContext : {}", id);
        cmContextRepository.deleteById(id);
    }
}

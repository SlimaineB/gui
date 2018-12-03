package com.bnpp.cm.service.impl;

import com.bnpp.cm.service.CmActionService;
import com.bnpp.cm.domain.CmAction;
import com.bnpp.cm.repository.CmActionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CmAction.
 */
@Service
@Transactional
public class CmActionServiceImpl implements CmActionService {

    private final Logger log = LoggerFactory.getLogger(CmActionServiceImpl.class);

    private final CmActionRepository cmActionRepository;

    public CmActionServiceImpl(CmActionRepository cmActionRepository) {
        this.cmActionRepository = cmActionRepository;
    }

    /**
     * Save a cmAction.
     *
     * @param cmAction the entity to save
     * @return the persisted entity
     */
    @Override
    public CmAction save(CmAction cmAction) {
        log.debug("Request to save CmAction : {}", cmAction);
        return cmActionRepository.save(cmAction);
    }

    /**
     * Get all the cmActions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CmAction> findAll(Pageable pageable) {
        log.debug("Request to get all CmActions");
        return cmActionRepository.findAll(pageable);
    }


    /**
     * Get one cmAction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CmAction> findOne(Long id) {
        log.debug("Request to get CmAction : {}", id);
        return cmActionRepository.findById(id);
    }

    /**
     * Delete the cmAction by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CmAction : {}", id);
        cmActionRepository.deleteById(id);
    }
}

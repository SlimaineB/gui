package com.bnpp.cm.service.impl;

import com.bnpp.cm.service.CmMockService;
import com.bnpp.cm.domain.CmMock;
import com.bnpp.cm.repository.CmMockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CmMock.
 */
@Service
@Transactional
public class CmMockServiceImpl implements CmMockService {

    private final Logger log = LoggerFactory.getLogger(CmMockServiceImpl.class);

    private final CmMockRepository cmMockRepository;

    public CmMockServiceImpl(CmMockRepository cmMockRepository) {
        this.cmMockRepository = cmMockRepository;
    }

    /**
     * Save a cmMock.
     *
     * @param cmMock the entity to save
     * @return the persisted entity
     */
    @Override
    public CmMock save(CmMock cmMock) {
        log.debug("Request to save CmMock : {}", cmMock);
        return cmMockRepository.save(cmMock);
    }

    /**
     * Get all the cmMocks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CmMock> findAll(Pageable pageable) {
        log.debug("Request to get all CmMocks");
        return cmMockRepository.findAll(pageable);
    }


    /**
     * Get one cmMock by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CmMock> findOne(Long id) {
        log.debug("Request to get CmMock : {}", id);
        return cmMockRepository.findById(id);
    }

    /**
     * Delete the cmMock by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CmMock : {}", id);
        cmMockRepository.deleteById(id);
    }
}

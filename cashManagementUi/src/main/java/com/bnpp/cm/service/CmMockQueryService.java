package com.bnpp.cm.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bnpp.cm.domain.CmMock;
import com.bnpp.cm.domain.*; // for static metamodels
import com.bnpp.cm.repository.CmMockRepository;
import com.bnpp.cm.service.dto.CmMockCriteria;

/**
 * Service for executing complex queries for CmMock entities in the database.
 * The main input is a {@link CmMockCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CmMock} or a {@link Page} of {@link CmMock} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CmMockQueryService extends QueryService<CmMock> {

    private final Logger log = LoggerFactory.getLogger(CmMockQueryService.class);

    private final CmMockRepository cmMockRepository;

    public CmMockQueryService(CmMockRepository cmMockRepository) {
        this.cmMockRepository = cmMockRepository;
    }

    /**
     * Return a {@link List} of {@link CmMock} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CmMock> findByCriteria(CmMockCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CmMock> specification = createSpecification(criteria);
        return cmMockRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CmMock} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CmMock> findByCriteria(CmMockCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CmMock> specification = createSpecification(criteria);
        return cmMockRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CmMockCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CmMock> specification = createSpecification(criteria);
        return cmMockRepository.count(specification);
    }

    /**
     * Function to convert CmMockCriteria to a {@link Specification}
     */
    private Specification<CmMock> createSpecification(CmMockCriteria criteria) {
        Specification<CmMock> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CmMock_.id));
            }
            if (criteria.getMockId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMockId(), CmMock_.mockId));
            }
            if (criteria.getMockServiceName() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMockServiceName(), CmMock_.mockServiceName));
            }
            if (criteria.getMockSearchKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMockSearchKey(), CmMock_.mockSearchKey));
            }
            if (criteria.getMockSearchValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMockSearchValue(), CmMock_.mockSearchValue));
            }
            if (criteria.getMockedBody() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMockedBody(), CmMock_.mockedBody));
            }
            if (criteria.getMockedHttpCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMockedHttpCode(), CmMock_.mockedHttpCode));
            }
            if (criteria.getMockedTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMockedTime(), CmMock_.mockedTime));
            }
        }
        return specification;
    }
}

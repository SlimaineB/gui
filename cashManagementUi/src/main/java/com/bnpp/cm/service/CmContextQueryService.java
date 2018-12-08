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

import com.bnpp.cm.domain.CmContext;
import com.bnpp.cm.domain.*; // for static metamodels
import com.bnpp.cm.repository.CmContextRepository;
import com.bnpp.cm.service.dto.CmContextCriteria;

/**
 * Service for executing complex queries for CmContext entities in the database.
 * The main input is a {@link CmContextCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CmContext} or a {@link Page} of {@link CmContext} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CmContextQueryService extends QueryService<CmContext> {

    private final Logger log = LoggerFactory.getLogger(CmContextQueryService.class);

    private final CmContextRepository cmContextRepository;

    public CmContextQueryService(CmContextRepository cmContextRepository) {
        this.cmContextRepository = cmContextRepository;
    }

    /**
     * Return a {@link List} of {@link CmContext} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CmContext> findByCriteria(CmContextCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CmContext> specification = createSpecification(criteria);
        return cmContextRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CmContext} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CmContext> findByCriteria(CmContextCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CmContext> specification = createSpecification(criteria);
        return cmContextRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CmContextCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CmContext> specification = createSpecification(criteria);
        return cmContextRepository.count(specification);
    }

    /**
     * Function to convert CmContextCriteria to a {@link Specification}
     */
    private Specification<CmContext> createSpecification(CmContextCriteria criteria) {
        Specification<CmContext> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CmContext_.id));
            }
            if (criteria.getContextType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContextType(), CmContext_.contextType));
            }
            if (criteria.getContextName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContextName(), CmContext_.contextName));
            }
            if (criteria.getContextValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContextValue(), CmContext_.contextValue));
            }
            if (criteria.getContextDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContextDateTime(), CmContext_.contextDateTime));
            }
            if (criteria.getRequestId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequestId(),
                    root -> root.join(CmContext_.request, JoinType.LEFT).get(CmRequest_.id)));
            }
        }
        return specification;
    }
}

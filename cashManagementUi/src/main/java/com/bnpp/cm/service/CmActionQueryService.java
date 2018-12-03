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

import com.bnpp.cm.domain.CmAction;
import com.bnpp.cm.domain.*; // for static metamodels
import com.bnpp.cm.repository.CmActionRepository;
import com.bnpp.cm.service.dto.CmActionCriteria;

/**
 * Service for executing complex queries for CmAction entities in the database.
 * The main input is a {@link CmActionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CmAction} or a {@link Page} of {@link CmAction} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CmActionQueryService extends QueryService<CmAction> {

    private final Logger log = LoggerFactory.getLogger(CmActionQueryService.class);

    private final CmActionRepository cmActionRepository;

    public CmActionQueryService(CmActionRepository cmActionRepository) {
        this.cmActionRepository = cmActionRepository;
    }

    /**
     * Return a {@link List} of {@link CmAction} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CmAction> findByCriteria(CmActionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CmAction> specification = createSpecification(criteria);
        return cmActionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CmAction} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CmAction> findByCriteria(CmActionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CmAction> specification = createSpecification(criteria);
        return cmActionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CmActionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CmAction> specification = createSpecification(criteria);
        return cmActionRepository.count(specification);
    }

    /**
     * Function to convert CmActionCriteria to a {@link Specification}
     */
    private Specification<CmAction> createSpecification(CmActionCriteria criteria) {
        Specification<CmAction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CmAction_.id));
            }
            if (criteria.getActionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActionId(), CmAction_.actionId));
            }
            if (criteria.getActionNum() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActionNum(), CmAction_.actionNum));
            }
            if (criteria.getActionType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActionType(), CmAction_.actionType));
            }
            if (criteria.getActionDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActionDescription(), CmAction_.actionDescription));
            }
            if (criteria.getActionInput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActionInput(), CmAction_.actionInput));
            }
            if (criteria.getActionOutput() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActionOutput(), CmAction_.actionOutput));
            }
            if (criteria.getActionDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActionDateTime(), CmAction_.actionDateTime));
            }
            if (criteria.getActionDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActionDuration(), CmAction_.actionDuration));
            }
            if (criteria.getRequestId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequestId(),
                    root -> root.join(CmAction_.request, JoinType.LEFT).get(CmRequest_.id)));
            }
        }
        return specification;
    }
}

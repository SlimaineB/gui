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

import com.bnpp.cm.domain.CmError;
import com.bnpp.cm.domain.*; // for static metamodels
import com.bnpp.cm.repository.CmErrorRepository;
import com.bnpp.cm.service.dto.CmErrorCriteria;

/**
 * Service for executing complex queries for CmError entities in the database.
 * The main input is a {@link CmErrorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CmError} or a {@link Page} of {@link CmError} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CmErrorQueryService extends QueryService<CmError> {

    private final Logger log = LoggerFactory.getLogger(CmErrorQueryService.class);

    private final CmErrorRepository cmErrorRepository;

    public CmErrorQueryService(CmErrorRepository cmErrorRepository) {
        this.cmErrorRepository = cmErrorRepository;
    }

    /**
     * Return a {@link List} of {@link CmError} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CmError> findByCriteria(CmErrorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CmError> specification = createSpecification(criteria);
        return cmErrorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CmError} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CmError> findByCriteria(CmErrorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CmError> specification = createSpecification(criteria);
        return cmErrorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CmErrorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CmError> specification = createSpecification(criteria);
        return cmErrorRepository.count(specification);
    }

    /**
     * Function to convert CmErrorCriteria to a {@link Specification}
     */
    private Specification<CmError> createSpecification(CmErrorCriteria criteria) {
        Specification<CmError> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CmError_.id));
            }
            if (criteria.getErrorId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getErrorId(), CmError_.errorId));
            }
            if (criteria.getErrorComponent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getErrorComponent(), CmError_.errorComponent));
            }
            if (criteria.getErrorCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrorCode(), CmError_.errorCode));
            }
            if (criteria.getErrorDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrorDescription(), CmError_.errorDescription));
            }
            if (criteria.getErrorStackTrace() != null) {
                specification = specification.and(buildStringSpecification(criteria.getErrorStackTrace(), CmError_.errorStackTrace));
            }
            if (criteria.getErrornDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getErrornDateTime(), CmError_.errornDateTime));
            }
            if (criteria.getRequestId() != null) {
                specification = specification.and(buildSpecification(criteria.getRequestId(),
                    root -> root.join(CmError_.request, JoinType.LEFT).get(CmRequest_.id)));
            }
        }
        return specification;
    }
}

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

import com.bnpp.cm.domain.CmRequest;
import com.bnpp.cm.domain.*; // for static metamodels
import com.bnpp.cm.repository.CmRequestRepository;
import com.bnpp.cm.service.dto.CmRequestCriteria;

/**
 * Service for executing complex queries for CmRequest entities in the database.
 * The main input is a {@link CmRequestCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CmRequest} or a {@link Page} of {@link CmRequest} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CmRequestQueryService extends QueryService<CmRequest> {

    private final Logger log = LoggerFactory.getLogger(CmRequestQueryService.class);

    private final CmRequestRepository cmRequestRepository;

    public CmRequestQueryService(CmRequestRepository cmRequestRepository) {
        this.cmRequestRepository = cmRequestRepository;
    }

    /**
     * Return a {@link List} of {@link CmRequest} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CmRequest> findByCriteria(CmRequestCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CmRequest> specification = createSpecification(criteria);
        return cmRequestRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CmRequest} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CmRequest> findByCriteria(CmRequestCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CmRequest> specification = createSpecification(criteria);
        return cmRequestRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CmRequestCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CmRequest> specification = createSpecification(criteria);
        return cmRequestRepository.count(specification);
    }

    /**
     * Function to convert CmRequestCriteria to a {@link Specification}
     */
    private Specification<CmRequest> createSpecification(CmRequestCriteria criteria) {
        Specification<CmRequest> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CmRequest_.id));
            }
            if (criteria.getRequestId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequestId(), CmRequest_.requestId));
            }
            if (criteria.getRequestUuid() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequestUuid(), CmRequest_.requestUuid));
            }
            if (criteria.getServiceName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceName(), CmRequest_.serviceName));
            }
            if (criteria.getServiceEndpoint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceEndpoint(), CmRequest_.serviceEndpoint));
            }
            if (criteria.getInstanceHostname() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInstanceHostname(), CmRequest_.instanceHostname));
            }
            if (criteria.getInstancePort() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInstancePort(), CmRequest_.instancePort));
            }
            if (criteria.getRequestBody() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequestBody(), CmRequest_.requestBody));
            }
            if (criteria.getRequestHeader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRequestHeader(), CmRequest_.requestHeader));
            }
            if (criteria.getResponseBody() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponseBody(), CmRequest_.responseBody));
            }
            if (criteria.getResponseHeader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResponseHeader(), CmRequest_.responseHeader));
            }
            if (criteria.getReturnHttpCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReturnHttpCode(), CmRequest_.returnHttpCode));
            }
            if (criteria.getTechnicalStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTechnicalStatus(), CmRequest_.technicalStatus));
            }
            if (criteria.getFunctionalStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFunctionalStatus(), CmRequest_.functionalStatus));
            }
            if (criteria.getStartDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDateTime(), CmRequest_.startDateTime));
            }
            if (criteria.getEndDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDateTime(), CmRequest_.endDateTime));
            }
            if (criteria.getRequestDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequestDuration(), CmRequest_.requestDuration));
            }
            if (criteria.getActionId() != null) {
                specification = specification.and(buildSpecification(criteria.getActionId(),
                    root -> root.join(CmRequest_.actions, JoinType.LEFT).get(CmAction_.id)));
            }
            if (criteria.getContextId() != null) {
                specification = specification.and(buildSpecification(criteria.getContextId(),
                    root -> root.join(CmRequest_.contexts, JoinType.LEFT).get(CmContext_.id)));
            }
            if (criteria.getErrorId() != null) {
                specification = specification.and(buildSpecification(criteria.getErrorId(),
                    root -> root.join(CmRequest_.errors, JoinType.LEFT).get(CmError_.id)));
            }
        }
        return specification;
    }
}

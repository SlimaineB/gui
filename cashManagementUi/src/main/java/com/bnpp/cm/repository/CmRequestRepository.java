package com.bnpp.cm.repository;

import com.bnpp.cm.domain.CmRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CmRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmRequestRepository extends JpaRepository<CmRequest, Long>, JpaSpecificationExecutor<CmRequest> {

}

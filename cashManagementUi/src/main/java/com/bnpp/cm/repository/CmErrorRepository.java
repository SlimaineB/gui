package com.bnpp.cm.repository;

import com.bnpp.cm.domain.CmError;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CmError entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmErrorRepository extends JpaRepository<CmError, Long>, JpaSpecificationExecutor<CmError> {

}

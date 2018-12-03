package com.bnpp.cm.repository;

import com.bnpp.cm.domain.CmAction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CmAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmActionRepository extends JpaRepository<CmAction, Long>, JpaSpecificationExecutor<CmAction> {

}

package com.bnpp.cm.repository;

import com.bnpp.cm.domain.CmContext;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CmContext entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmContextRepository extends JpaRepository<CmContext, Long>, JpaSpecificationExecutor<CmContext> {

}

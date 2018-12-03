package com.bnpp.cm.repository;

import com.bnpp.cm.domain.CmMock;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CmMock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CmMockRepository extends JpaRepository<CmMock, Long>, JpaSpecificationExecutor<CmMock> {

}

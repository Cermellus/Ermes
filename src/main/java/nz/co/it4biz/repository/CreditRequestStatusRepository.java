package nz.co.it4biz.repository;

import nz.co.it4biz.domain.CreditRequestStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CreditRequestStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditRequestStatusRepository extends JpaRepository<CreditRequestStatus, Long> {

}

package nz.co.it4biz.repository;

import nz.co.it4biz.domain.CreditReason;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CreditReason entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditReasonRepository extends JpaRepository<CreditReason, Long> {

}

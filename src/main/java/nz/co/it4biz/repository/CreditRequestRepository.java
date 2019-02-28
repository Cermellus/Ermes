package nz.co.it4biz.repository;

import nz.co.it4biz.domain.CreditRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CreditRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditRequestRepository extends JpaRepository<CreditRequest, Long> {

}

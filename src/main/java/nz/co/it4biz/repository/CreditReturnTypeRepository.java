package nz.co.it4biz.repository;

import nz.co.it4biz.domain.CreditReturnType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CreditReturnType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditReturnTypeRepository extends JpaRepository<CreditReturnType, Long> {

}

package nz.co.it4biz.repository;

import nz.co.it4biz.domain.CreditReferenceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CreditReferenceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditReferenceTypeRepository extends JpaRepository<CreditReferenceType, Long> {

}

package nz.co.it4biz.repository;

import nz.co.it4biz.domain.CreditRequestLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CreditRequestLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditRequestLineRepository extends JpaRepository<CreditRequestLine, Long> {

}

package nz.co.it4biz.repository;

import nz.co.it4biz.domain.CallLogLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CallLogLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CallLogLineRepository extends JpaRepository<CallLogLine, Long> {

}

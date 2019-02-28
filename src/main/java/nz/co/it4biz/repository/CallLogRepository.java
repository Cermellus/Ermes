package nz.co.it4biz.repository;

import nz.co.it4biz.domain.CallLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CallLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CallLogRepository extends JpaRepository<CallLog, Long> {

}

package nz.co.it4biz.repository;

import nz.co.it4biz.domain.CallLogAction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CallLogAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CallLogActionRepository extends JpaRepository<CallLogAction, Long> {

}

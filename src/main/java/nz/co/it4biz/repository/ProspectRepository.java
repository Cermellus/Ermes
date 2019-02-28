package nz.co.it4biz.repository;

import nz.co.it4biz.domain.Prospect;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Prospect entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProspectRepository extends JpaRepository<Prospect, Long> {

}

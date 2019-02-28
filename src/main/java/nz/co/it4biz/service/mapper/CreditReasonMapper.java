package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.CreditReasonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CreditReason and its DTO CreditReasonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CreditReasonMapper extends EntityMapper<CreditReasonDTO, CreditReason> {



    default CreditReason fromId(Long id) {
        if (id == null) {
            return null;
        }
        CreditReason creditReason = new CreditReason();
        creditReason.setId(id);
        return creditReason;
    }
}

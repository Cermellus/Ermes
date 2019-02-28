package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.CreditRequestStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CreditRequestStatus and its DTO CreditRequestStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CreditRequestStatusMapper extends EntityMapper<CreditRequestStatusDTO, CreditRequestStatus> {



    default CreditRequestStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        CreditRequestStatus creditRequestStatus = new CreditRequestStatus();
        creditRequestStatus.setId(id);
        return creditRequestStatus;
    }
}

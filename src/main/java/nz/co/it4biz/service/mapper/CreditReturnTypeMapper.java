package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.CreditReturnTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CreditReturnType and its DTO CreditReturnTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CreditReturnTypeMapper extends EntityMapper<CreditReturnTypeDTO, CreditReturnType> {



    default CreditReturnType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CreditReturnType creditReturnType = new CreditReturnType();
        creditReturnType.setId(id);
        return creditReturnType;
    }
}

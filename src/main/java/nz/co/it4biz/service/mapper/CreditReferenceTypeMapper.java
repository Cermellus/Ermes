package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.CreditReferenceTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CreditReferenceType and its DTO CreditReferenceTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CreditReferenceTypeMapper extends EntityMapper<CreditReferenceTypeDTO, CreditReferenceType> {



    default CreditReferenceType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CreditReferenceType creditReferenceType = new CreditReferenceType();
        creditReferenceType.setId(id);
        return creditReferenceType;
    }
}

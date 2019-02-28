package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.CreditRequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CreditRequest and its DTO CreditRequestDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, CreditReferenceTypeMapper.class, CreditRequestStatusMapper.class, AppUserMapper.class})
public interface CreditRequestMapper extends EntityMapper<CreditRequestDTO, CreditRequest> {

    @Mapping(source = "customerId.id", target = "customerIdId")
    @Mapping(source = "creditReferenceTypeId.id", target = "creditReferenceTypeIdId")
    @Mapping(source = "creditRequestStatusId.id", target = "creditRequestStatusIdId")
    @Mapping(source = "appUserId.id", target = "appUserIdId")
    CreditRequestDTO toDto(CreditRequest creditRequest);

    @Mapping(source = "customerIdId", target = "customerId")
    @Mapping(source = "creditReferenceTypeIdId", target = "creditReferenceTypeId")
    @Mapping(source = "creditRequestStatusIdId", target = "creditRequestStatusId")
    @Mapping(source = "appUserIdId", target = "appUserId")
    CreditRequest toEntity(CreditRequestDTO creditRequestDTO);

    default CreditRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setId(id);
        return creditRequest;
    }
}

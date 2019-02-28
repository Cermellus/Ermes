package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.CreditRequestLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CreditRequestLine and its DTO CreditRequestLineDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, CreditReasonMapper.class, CreditReturnTypeMapper.class, CreditRequestMapper.class})
public interface CreditRequestLineMapper extends EntityMapper<CreditRequestLineDTO, CreditRequestLine> {

    @Mapping(source = "productId.id", target = "productIdId")
    @Mapping(source = "creditReasonId.id", target = "creditReasonIdId")
    @Mapping(source = "creditReturnTypeId.id", target = "creditReturnTypeIdId")
    @Mapping(source = "creditRequestId.id", target = "creditRequestIdId")
    CreditRequestLineDTO toDto(CreditRequestLine creditRequestLine);

    @Mapping(source = "productIdId", target = "productId")
    @Mapping(source = "creditReasonIdId", target = "creditReasonId")
    @Mapping(source = "creditReturnTypeIdId", target = "creditReturnTypeId")
    @Mapping(source = "creditRequestIdId", target = "creditRequestId")
    CreditRequestLine toEntity(CreditRequestLineDTO creditRequestLineDTO);

    default CreditRequestLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CreditRequestLine creditRequestLine = new CreditRequestLine();
        creditRequestLine.setId(id);
        return creditRequestLine;
    }
}

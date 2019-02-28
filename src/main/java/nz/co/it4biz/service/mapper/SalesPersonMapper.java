package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.SalesPersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SalesPerson and its DTO SalesPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalesPersonMapper extends EntityMapper<SalesPersonDTO, SalesPerson> {



    default SalesPerson fromId(Long id) {
        if (id == null) {
            return null;
        }
        SalesPerson salesPerson = new SalesPerson();
        salesPerson.setId(id);
        return salesPerson;
    }
}

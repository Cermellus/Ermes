package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.CustomerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Customer and its DTO CustomerDTO.
 */
@Mapper(componentModel = "spring", uses = {ProspectMapper.class, SalesPersonMapper.class})
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {

    @Mapping(source = "prospectId.id", target = "prospectIdId")
    @Mapping(source = "salesPersonId.id", target = "salesPersonIdId")
    CustomerDTO toDto(Customer customer);

    @Mapping(source = "prospectIdId", target = "prospectId")
    @Mapping(source = "salesPersonIdId", target = "salesPersonId")
    Customer toEntity(CustomerDTO customerDTO);

    default Customer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}

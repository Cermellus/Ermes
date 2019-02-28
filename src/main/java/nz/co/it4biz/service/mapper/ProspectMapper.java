package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.ProspectDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Prospect and its DTO ProspectDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProspectMapper extends EntityMapper<ProspectDTO, Prospect> {



    default Prospect fromId(Long id) {
        if (id == null) {
            return null;
        }
        Prospect prospect = new Prospect();
        prospect.setId(id);
        return prospect;
    }
}

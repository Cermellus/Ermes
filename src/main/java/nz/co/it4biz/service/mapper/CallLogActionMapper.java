package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.CallLogActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CallLogAction and its DTO CallLogActionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CallLogActionMapper extends EntityMapper<CallLogActionDTO, CallLogAction> {



    default CallLogAction fromId(Long id) {
        if (id == null) {
            return null;
        }
        CallLogAction callLogAction = new CallLogAction();
        callLogAction.setId(id);
        return callLogAction;
    }
}

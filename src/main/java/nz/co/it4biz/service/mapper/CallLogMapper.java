package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.CallLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CallLog and its DTO CallLogDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, ProspectMapper.class, AppUserMapper.class})
public interface CallLogMapper extends EntityMapper<CallLogDTO, CallLog> {

    @Mapping(source = "customerId.id", target = "customerIdId")
    @Mapping(source = "prospectId.id", target = "prospectIdId")
    @Mapping(source = "appUserId.id", target = "appUserIdId")
    CallLogDTO toDto(CallLog callLog);

    @Mapping(source = "customerIdId", target = "customerId")
    @Mapping(source = "prospectIdId", target = "prospectId")
    @Mapping(source = "appUserIdId", target = "appUserId")
    CallLog toEntity(CallLogDTO callLogDTO);

    default CallLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        CallLog callLog = new CallLog();
        callLog.setId(id);
        return callLog;
    }
}

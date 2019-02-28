package nz.co.it4biz.service.mapper;

import nz.co.it4biz.domain.*;
import nz.co.it4biz.service.dto.CallLogLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CallLogLine and its DTO CallLogLineDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class, CallLogActionMapper.class, CallLogMapper.class})
public interface CallLogLineMapper extends EntityMapper<CallLogLineDTO, CallLogLine> {

    @Mapping(source = "productId.id", target = "productIdId")
    @Mapping(source = "callLogActionId.id", target = "callLogActionIdId")
    @Mapping(source = "callLogId.id", target = "callLogIdId")
    CallLogLineDTO toDto(CallLogLine callLogLine);

    @Mapping(source = "productIdId", target = "productId")
    @Mapping(source = "callLogActionIdId", target = "callLogActionId")
    @Mapping(source = "callLogIdId", target = "callLogId")
    CallLogLine toEntity(CallLogLineDTO callLogLineDTO);

    default CallLogLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CallLogLine callLogLine = new CallLogLine();
        callLogLine.setId(id);
        return callLogLine;
    }
}

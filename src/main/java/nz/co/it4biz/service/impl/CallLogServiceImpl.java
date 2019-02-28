package nz.co.it4biz.service.impl;

import nz.co.it4biz.service.CallLogService;
import nz.co.it4biz.domain.CallLog;
import nz.co.it4biz.repository.CallLogRepository;
import nz.co.it4biz.service.dto.CallLogDTO;
import nz.co.it4biz.service.mapper.CallLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CallLog.
 */
@Service
@Transactional
public class CallLogServiceImpl implements CallLogService {

    private final Logger log = LoggerFactory.getLogger(CallLogServiceImpl.class);

    private final CallLogRepository callLogRepository;

    private final CallLogMapper callLogMapper;

    public CallLogServiceImpl(CallLogRepository callLogRepository, CallLogMapper callLogMapper) {
        this.callLogRepository = callLogRepository;
        this.callLogMapper = callLogMapper;
    }

    /**
     * Save a callLog.
     *
     * @param callLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CallLogDTO save(CallLogDTO callLogDTO) {
        log.debug("Request to save CallLog : {}", callLogDTO);
        CallLog callLog = callLogMapper.toEntity(callLogDTO);
        callLog = callLogRepository.save(callLog);
        return callLogMapper.toDto(callLog);
    }

    /**
     * Get all the callLogs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CallLogDTO> findAll() {
        log.debug("Request to get all CallLogs");
        return callLogRepository.findAll().stream()
            .map(callLogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one callLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CallLogDTO> findOne(Long id) {
        log.debug("Request to get CallLog : {}", id);
        return callLogRepository.findById(id)
            .map(callLogMapper::toDto);
    }

    /**
     * Delete the callLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CallLog : {}", id);        callLogRepository.deleteById(id);
    }
}

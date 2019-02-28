package nz.co.it4biz.service.impl;

import nz.co.it4biz.service.CallLogActionService;
import nz.co.it4biz.domain.CallLogAction;
import nz.co.it4biz.repository.CallLogActionRepository;
import nz.co.it4biz.service.dto.CallLogActionDTO;
import nz.co.it4biz.service.mapper.CallLogActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CallLogAction.
 */
@Service
@Transactional
public class CallLogActionServiceImpl implements CallLogActionService {

    private final Logger log = LoggerFactory.getLogger(CallLogActionServiceImpl.class);

    private final CallLogActionRepository callLogActionRepository;

    private final CallLogActionMapper callLogActionMapper;

    public CallLogActionServiceImpl(CallLogActionRepository callLogActionRepository, CallLogActionMapper callLogActionMapper) {
        this.callLogActionRepository = callLogActionRepository;
        this.callLogActionMapper = callLogActionMapper;
    }

    /**
     * Save a callLogAction.
     *
     * @param callLogActionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CallLogActionDTO save(CallLogActionDTO callLogActionDTO) {
        log.debug("Request to save CallLogAction : {}", callLogActionDTO);
        CallLogAction callLogAction = callLogActionMapper.toEntity(callLogActionDTO);
        callLogAction = callLogActionRepository.save(callLogAction);
        return callLogActionMapper.toDto(callLogAction);
    }

    /**
     * Get all the callLogActions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CallLogActionDTO> findAll() {
        log.debug("Request to get all CallLogActions");
        return callLogActionRepository.findAll().stream()
            .map(callLogActionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one callLogAction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CallLogActionDTO> findOne(Long id) {
        log.debug("Request to get CallLogAction : {}", id);
        return callLogActionRepository.findById(id)
            .map(callLogActionMapper::toDto);
    }

    /**
     * Delete the callLogAction by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CallLogAction : {}", id);        callLogActionRepository.deleteById(id);
    }
}

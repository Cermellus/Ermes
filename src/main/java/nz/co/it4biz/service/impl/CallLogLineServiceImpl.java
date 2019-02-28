package nz.co.it4biz.service.impl;

import nz.co.it4biz.service.CallLogLineService;
import nz.co.it4biz.domain.CallLogLine;
import nz.co.it4biz.repository.CallLogLineRepository;
import nz.co.it4biz.service.dto.CallLogLineDTO;
import nz.co.it4biz.service.mapper.CallLogLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CallLogLine.
 */
@Service
@Transactional
public class CallLogLineServiceImpl implements CallLogLineService {

    private final Logger log = LoggerFactory.getLogger(CallLogLineServiceImpl.class);

    private final CallLogLineRepository callLogLineRepository;

    private final CallLogLineMapper callLogLineMapper;

    public CallLogLineServiceImpl(CallLogLineRepository callLogLineRepository, CallLogLineMapper callLogLineMapper) {
        this.callLogLineRepository = callLogLineRepository;
        this.callLogLineMapper = callLogLineMapper;
    }

    /**
     * Save a callLogLine.
     *
     * @param callLogLineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CallLogLineDTO save(CallLogLineDTO callLogLineDTO) {
        log.debug("Request to save CallLogLine : {}", callLogLineDTO);
        CallLogLine callLogLine = callLogLineMapper.toEntity(callLogLineDTO);
        callLogLine = callLogLineRepository.save(callLogLine);
        return callLogLineMapper.toDto(callLogLine);
    }

    /**
     * Get all the callLogLines.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CallLogLineDTO> findAll() {
        log.debug("Request to get all CallLogLines");
        return callLogLineRepository.findAll().stream()
            .map(callLogLineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one callLogLine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CallLogLineDTO> findOne(Long id) {
        log.debug("Request to get CallLogLine : {}", id);
        return callLogLineRepository.findById(id)
            .map(callLogLineMapper::toDto);
    }

    /**
     * Delete the callLogLine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CallLogLine : {}", id);        callLogLineRepository.deleteById(id);
    }
}

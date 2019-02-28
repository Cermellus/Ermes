package nz.co.it4biz.service.impl;

import nz.co.it4biz.service.CreditRequestStatusService;
import nz.co.it4biz.domain.CreditRequestStatus;
import nz.co.it4biz.repository.CreditRequestStatusRepository;
import nz.co.it4biz.service.dto.CreditRequestStatusDTO;
import nz.co.it4biz.service.mapper.CreditRequestStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CreditRequestStatus.
 */
@Service
@Transactional
public class CreditRequestStatusServiceImpl implements CreditRequestStatusService {

    private final Logger log = LoggerFactory.getLogger(CreditRequestStatusServiceImpl.class);

    private final CreditRequestStatusRepository creditRequestStatusRepository;

    private final CreditRequestStatusMapper creditRequestStatusMapper;

    public CreditRequestStatusServiceImpl(CreditRequestStatusRepository creditRequestStatusRepository, CreditRequestStatusMapper creditRequestStatusMapper) {
        this.creditRequestStatusRepository = creditRequestStatusRepository;
        this.creditRequestStatusMapper = creditRequestStatusMapper;
    }

    /**
     * Save a creditRequestStatus.
     *
     * @param creditRequestStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CreditRequestStatusDTO save(CreditRequestStatusDTO creditRequestStatusDTO) {
        log.debug("Request to save CreditRequestStatus : {}", creditRequestStatusDTO);
        CreditRequestStatus creditRequestStatus = creditRequestStatusMapper.toEntity(creditRequestStatusDTO);
        creditRequestStatus = creditRequestStatusRepository.save(creditRequestStatus);
        return creditRequestStatusMapper.toDto(creditRequestStatus);
    }

    /**
     * Get all the creditRequestStatuses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CreditRequestStatusDTO> findAll() {
        log.debug("Request to get all CreditRequestStatuses");
        return creditRequestStatusRepository.findAll().stream()
            .map(creditRequestStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one creditRequestStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CreditRequestStatusDTO> findOne(Long id) {
        log.debug("Request to get CreditRequestStatus : {}", id);
        return creditRequestStatusRepository.findById(id)
            .map(creditRequestStatusMapper::toDto);
    }

    /**
     * Delete the creditRequestStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditRequestStatus : {}", id);        creditRequestStatusRepository.deleteById(id);
    }
}

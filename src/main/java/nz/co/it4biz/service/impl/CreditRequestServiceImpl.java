package nz.co.it4biz.service.impl;

import nz.co.it4biz.service.CreditRequestService;
import nz.co.it4biz.domain.CreditRequest;
import nz.co.it4biz.repository.CreditRequestRepository;
import nz.co.it4biz.service.dto.CreditRequestDTO;
import nz.co.it4biz.service.mapper.CreditRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CreditRequest.
 */
@Service
@Transactional
public class CreditRequestServiceImpl implements CreditRequestService {

    private final Logger log = LoggerFactory.getLogger(CreditRequestServiceImpl.class);

    private final CreditRequestRepository creditRequestRepository;

    private final CreditRequestMapper creditRequestMapper;

    public CreditRequestServiceImpl(CreditRequestRepository creditRequestRepository, CreditRequestMapper creditRequestMapper) {
        this.creditRequestRepository = creditRequestRepository;
        this.creditRequestMapper = creditRequestMapper;
    }

    /**
     * Save a creditRequest.
     *
     * @param creditRequestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CreditRequestDTO save(CreditRequestDTO creditRequestDTO) {
        log.debug("Request to save CreditRequest : {}", creditRequestDTO);
        CreditRequest creditRequest = creditRequestMapper.toEntity(creditRequestDTO);
        creditRequest = creditRequestRepository.save(creditRequest);
        return creditRequestMapper.toDto(creditRequest);
    }

    /**
     * Get all the creditRequests.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CreditRequestDTO> findAll() {
        log.debug("Request to get all CreditRequests");
        return creditRequestRepository.findAll().stream()
            .map(creditRequestMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one creditRequest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CreditRequestDTO> findOne(Long id) {
        log.debug("Request to get CreditRequest : {}", id);
        return creditRequestRepository.findById(id)
            .map(creditRequestMapper::toDto);
    }

    /**
     * Delete the creditRequest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditRequest : {}", id);        creditRequestRepository.deleteById(id);
    }
}

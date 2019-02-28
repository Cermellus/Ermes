package nz.co.it4biz.service.impl;

import nz.co.it4biz.service.CreditReasonService;
import nz.co.it4biz.domain.CreditReason;
import nz.co.it4biz.repository.CreditReasonRepository;
import nz.co.it4biz.service.dto.CreditReasonDTO;
import nz.co.it4biz.service.mapper.CreditReasonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CreditReason.
 */
@Service
@Transactional
public class CreditReasonServiceImpl implements CreditReasonService {

    private final Logger log = LoggerFactory.getLogger(CreditReasonServiceImpl.class);

    private final CreditReasonRepository creditReasonRepository;

    private final CreditReasonMapper creditReasonMapper;

    public CreditReasonServiceImpl(CreditReasonRepository creditReasonRepository, CreditReasonMapper creditReasonMapper) {
        this.creditReasonRepository = creditReasonRepository;
        this.creditReasonMapper = creditReasonMapper;
    }

    /**
     * Save a creditReason.
     *
     * @param creditReasonDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CreditReasonDTO save(CreditReasonDTO creditReasonDTO) {
        log.debug("Request to save CreditReason : {}", creditReasonDTO);
        CreditReason creditReason = creditReasonMapper.toEntity(creditReasonDTO);
        creditReason = creditReasonRepository.save(creditReason);
        return creditReasonMapper.toDto(creditReason);
    }

    /**
     * Get all the creditReasons.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CreditReasonDTO> findAll() {
        log.debug("Request to get all CreditReasons");
        return creditReasonRepository.findAll().stream()
            .map(creditReasonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one creditReason by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CreditReasonDTO> findOne(Long id) {
        log.debug("Request to get CreditReason : {}", id);
        return creditReasonRepository.findById(id)
            .map(creditReasonMapper::toDto);
    }

    /**
     * Delete the creditReason by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditReason : {}", id);        creditReasonRepository.deleteById(id);
    }
}

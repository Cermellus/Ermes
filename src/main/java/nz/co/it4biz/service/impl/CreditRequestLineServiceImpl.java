package nz.co.it4biz.service.impl;

import nz.co.it4biz.service.CreditRequestLineService;
import nz.co.it4biz.domain.CreditRequestLine;
import nz.co.it4biz.repository.CreditRequestLineRepository;
import nz.co.it4biz.service.dto.CreditRequestLineDTO;
import nz.co.it4biz.service.mapper.CreditRequestLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CreditRequestLine.
 */
@Service
@Transactional
public class CreditRequestLineServiceImpl implements CreditRequestLineService {

    private final Logger log = LoggerFactory.getLogger(CreditRequestLineServiceImpl.class);

    private final CreditRequestLineRepository creditRequestLineRepository;

    private final CreditRequestLineMapper creditRequestLineMapper;

    public CreditRequestLineServiceImpl(CreditRequestLineRepository creditRequestLineRepository, CreditRequestLineMapper creditRequestLineMapper) {
        this.creditRequestLineRepository = creditRequestLineRepository;
        this.creditRequestLineMapper = creditRequestLineMapper;
    }

    /**
     * Save a creditRequestLine.
     *
     * @param creditRequestLineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CreditRequestLineDTO save(CreditRequestLineDTO creditRequestLineDTO) {
        log.debug("Request to save CreditRequestLine : {}", creditRequestLineDTO);
        CreditRequestLine creditRequestLine = creditRequestLineMapper.toEntity(creditRequestLineDTO);
        creditRequestLine = creditRequestLineRepository.save(creditRequestLine);
        return creditRequestLineMapper.toDto(creditRequestLine);
    }

    /**
     * Get all the creditRequestLines.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CreditRequestLineDTO> findAll() {
        log.debug("Request to get all CreditRequestLines");
        return creditRequestLineRepository.findAll().stream()
            .map(creditRequestLineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one creditRequestLine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CreditRequestLineDTO> findOne(Long id) {
        log.debug("Request to get CreditRequestLine : {}", id);
        return creditRequestLineRepository.findById(id)
            .map(creditRequestLineMapper::toDto);
    }

    /**
     * Delete the creditRequestLine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditRequestLine : {}", id);        creditRequestLineRepository.deleteById(id);
    }
}

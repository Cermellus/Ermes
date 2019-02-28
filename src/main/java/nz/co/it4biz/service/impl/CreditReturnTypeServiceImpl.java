package nz.co.it4biz.service.impl;

import nz.co.it4biz.service.CreditReturnTypeService;
import nz.co.it4biz.domain.CreditReturnType;
import nz.co.it4biz.repository.CreditReturnTypeRepository;
import nz.co.it4biz.service.dto.CreditReturnTypeDTO;
import nz.co.it4biz.service.mapper.CreditReturnTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CreditReturnType.
 */
@Service
@Transactional
public class CreditReturnTypeServiceImpl implements CreditReturnTypeService {

    private final Logger log = LoggerFactory.getLogger(CreditReturnTypeServiceImpl.class);

    private final CreditReturnTypeRepository creditReturnTypeRepository;

    private final CreditReturnTypeMapper creditReturnTypeMapper;

    public CreditReturnTypeServiceImpl(CreditReturnTypeRepository creditReturnTypeRepository, CreditReturnTypeMapper creditReturnTypeMapper) {
        this.creditReturnTypeRepository = creditReturnTypeRepository;
        this.creditReturnTypeMapper = creditReturnTypeMapper;
    }

    /**
     * Save a creditReturnType.
     *
     * @param creditReturnTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CreditReturnTypeDTO save(CreditReturnTypeDTO creditReturnTypeDTO) {
        log.debug("Request to save CreditReturnType : {}", creditReturnTypeDTO);
        CreditReturnType creditReturnType = creditReturnTypeMapper.toEntity(creditReturnTypeDTO);
        creditReturnType = creditReturnTypeRepository.save(creditReturnType);
        return creditReturnTypeMapper.toDto(creditReturnType);
    }

    /**
     * Get all the creditReturnTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CreditReturnTypeDTO> findAll() {
        log.debug("Request to get all CreditReturnTypes");
        return creditReturnTypeRepository.findAll().stream()
            .map(creditReturnTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one creditReturnType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CreditReturnTypeDTO> findOne(Long id) {
        log.debug("Request to get CreditReturnType : {}", id);
        return creditReturnTypeRepository.findById(id)
            .map(creditReturnTypeMapper::toDto);
    }

    /**
     * Delete the creditReturnType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditReturnType : {}", id);        creditReturnTypeRepository.deleteById(id);
    }
}

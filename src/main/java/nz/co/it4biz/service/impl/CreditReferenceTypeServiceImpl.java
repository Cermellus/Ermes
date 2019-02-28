package nz.co.it4biz.service.impl;

import nz.co.it4biz.service.CreditReferenceTypeService;
import nz.co.it4biz.domain.CreditReferenceType;
import nz.co.it4biz.repository.CreditReferenceTypeRepository;
import nz.co.it4biz.service.dto.CreditReferenceTypeDTO;
import nz.co.it4biz.service.mapper.CreditReferenceTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CreditReferenceType.
 */
@Service
@Transactional
public class CreditReferenceTypeServiceImpl implements CreditReferenceTypeService {

    private final Logger log = LoggerFactory.getLogger(CreditReferenceTypeServiceImpl.class);

    private final CreditReferenceTypeRepository creditReferenceTypeRepository;

    private final CreditReferenceTypeMapper creditReferenceTypeMapper;

    public CreditReferenceTypeServiceImpl(CreditReferenceTypeRepository creditReferenceTypeRepository, CreditReferenceTypeMapper creditReferenceTypeMapper) {
        this.creditReferenceTypeRepository = creditReferenceTypeRepository;
        this.creditReferenceTypeMapper = creditReferenceTypeMapper;
    }

    /**
     * Save a creditReferenceType.
     *
     * @param creditReferenceTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CreditReferenceTypeDTO save(CreditReferenceTypeDTO creditReferenceTypeDTO) {
        log.debug("Request to save CreditReferenceType : {}", creditReferenceTypeDTO);
        CreditReferenceType creditReferenceType = creditReferenceTypeMapper.toEntity(creditReferenceTypeDTO);
        creditReferenceType = creditReferenceTypeRepository.save(creditReferenceType);
        return creditReferenceTypeMapper.toDto(creditReferenceType);
    }

    /**
     * Get all the creditReferenceTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CreditReferenceTypeDTO> findAll() {
        log.debug("Request to get all CreditReferenceTypes");
        return creditReferenceTypeRepository.findAll().stream()
            .map(creditReferenceTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one creditReferenceType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CreditReferenceTypeDTO> findOne(Long id) {
        log.debug("Request to get CreditReferenceType : {}", id);
        return creditReferenceTypeRepository.findById(id)
            .map(creditReferenceTypeMapper::toDto);
    }

    /**
     * Delete the creditReferenceType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditReferenceType : {}", id);        creditReferenceTypeRepository.deleteById(id);
    }
}

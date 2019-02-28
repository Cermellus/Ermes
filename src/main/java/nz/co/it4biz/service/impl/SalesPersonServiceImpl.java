package nz.co.it4biz.service.impl;

import nz.co.it4biz.service.SalesPersonService;
import nz.co.it4biz.domain.SalesPerson;
import nz.co.it4biz.repository.SalesPersonRepository;
import nz.co.it4biz.service.dto.SalesPersonDTO;
import nz.co.it4biz.service.mapper.SalesPersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SalesPerson.
 */
@Service
@Transactional
public class SalesPersonServiceImpl implements SalesPersonService {

    private final Logger log = LoggerFactory.getLogger(SalesPersonServiceImpl.class);

    private final SalesPersonRepository salesPersonRepository;

    private final SalesPersonMapper salesPersonMapper;

    public SalesPersonServiceImpl(SalesPersonRepository salesPersonRepository, SalesPersonMapper salesPersonMapper) {
        this.salesPersonRepository = salesPersonRepository;
        this.salesPersonMapper = salesPersonMapper;
    }

    /**
     * Save a salesPerson.
     *
     * @param salesPersonDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SalesPersonDTO save(SalesPersonDTO salesPersonDTO) {
        log.debug("Request to save SalesPerson : {}", salesPersonDTO);
        SalesPerson salesPerson = salesPersonMapper.toEntity(salesPersonDTO);
        salesPerson = salesPersonRepository.save(salesPerson);
        return salesPersonMapper.toDto(salesPerson);
    }

    /**
     * Get all the salesPeople.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SalesPersonDTO> findAll() {
        log.debug("Request to get all SalesPeople");
        return salesPersonRepository.findAll().stream()
            .map(salesPersonMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one salesPerson by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SalesPersonDTO> findOne(Long id) {
        log.debug("Request to get SalesPerson : {}", id);
        return salesPersonRepository.findById(id)
            .map(salesPersonMapper::toDto);
    }

    /**
     * Delete the salesPerson by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalesPerson : {}", id);        salesPersonRepository.deleteById(id);
    }
}

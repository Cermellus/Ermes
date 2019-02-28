package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.SalesPersonDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SalesPerson.
 */
public interface SalesPersonService {

    /**
     * Save a salesPerson.
     *
     * @param salesPersonDTO the entity to save
     * @return the persisted entity
     */
    SalesPersonDTO save(SalesPersonDTO salesPersonDTO);

    /**
     * Get all the salesPeople.
     *
     * @return the list of entities
     */
    List<SalesPersonDTO> findAll();


    /**
     * Get the "id" salesPerson.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SalesPersonDTO> findOne(Long id);

    /**
     * Delete the "id" salesPerson.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

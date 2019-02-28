package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.CreditRequestLineDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CreditRequestLine.
 */
public interface CreditRequestLineService {

    /**
     * Save a creditRequestLine.
     *
     * @param creditRequestLineDTO the entity to save
     * @return the persisted entity
     */
    CreditRequestLineDTO save(CreditRequestLineDTO creditRequestLineDTO);

    /**
     * Get all the creditRequestLines.
     *
     * @return the list of entities
     */
    List<CreditRequestLineDTO> findAll();


    /**
     * Get the "id" creditRequestLine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CreditRequestLineDTO> findOne(Long id);

    /**
     * Delete the "id" creditRequestLine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

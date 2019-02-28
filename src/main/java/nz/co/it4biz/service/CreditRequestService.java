package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.CreditRequestDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CreditRequest.
 */
public interface CreditRequestService {

    /**
     * Save a creditRequest.
     *
     * @param creditRequestDTO the entity to save
     * @return the persisted entity
     */
    CreditRequestDTO save(CreditRequestDTO creditRequestDTO);

    /**
     * Get all the creditRequests.
     *
     * @return the list of entities
     */
    List<CreditRequestDTO> findAll();


    /**
     * Get the "id" creditRequest.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CreditRequestDTO> findOne(Long id);

    /**
     * Delete the "id" creditRequest.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

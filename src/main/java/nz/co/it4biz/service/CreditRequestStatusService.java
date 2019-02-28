package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.CreditRequestStatusDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CreditRequestStatus.
 */
public interface CreditRequestStatusService {

    /**
     * Save a creditRequestStatus.
     *
     * @param creditRequestStatusDTO the entity to save
     * @return the persisted entity
     */
    CreditRequestStatusDTO save(CreditRequestStatusDTO creditRequestStatusDTO);

    /**
     * Get all the creditRequestStatuses.
     *
     * @return the list of entities
     */
    List<CreditRequestStatusDTO> findAll();


    /**
     * Get the "id" creditRequestStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CreditRequestStatusDTO> findOne(Long id);

    /**
     * Delete the "id" creditRequestStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

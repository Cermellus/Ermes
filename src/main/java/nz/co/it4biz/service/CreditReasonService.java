package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.CreditReasonDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CreditReason.
 */
public interface CreditReasonService {

    /**
     * Save a creditReason.
     *
     * @param creditReasonDTO the entity to save
     * @return the persisted entity
     */
    CreditReasonDTO save(CreditReasonDTO creditReasonDTO);

    /**
     * Get all the creditReasons.
     *
     * @return the list of entities
     */
    List<CreditReasonDTO> findAll();


    /**
     * Get the "id" creditReason.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CreditReasonDTO> findOne(Long id);

    /**
     * Delete the "id" creditReason.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

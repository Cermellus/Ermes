package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.CreditReferenceTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CreditReferenceType.
 */
public interface CreditReferenceTypeService {

    /**
     * Save a creditReferenceType.
     *
     * @param creditReferenceTypeDTO the entity to save
     * @return the persisted entity
     */
    CreditReferenceTypeDTO save(CreditReferenceTypeDTO creditReferenceTypeDTO);

    /**
     * Get all the creditReferenceTypes.
     *
     * @return the list of entities
     */
    List<CreditReferenceTypeDTO> findAll();


    /**
     * Get the "id" creditReferenceType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CreditReferenceTypeDTO> findOne(Long id);

    /**
     * Delete the "id" creditReferenceType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

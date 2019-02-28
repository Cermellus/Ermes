package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.CreditReturnTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CreditReturnType.
 */
public interface CreditReturnTypeService {

    /**
     * Save a creditReturnType.
     *
     * @param creditReturnTypeDTO the entity to save
     * @return the persisted entity
     */
    CreditReturnTypeDTO save(CreditReturnTypeDTO creditReturnTypeDTO);

    /**
     * Get all the creditReturnTypes.
     *
     * @return the list of entities
     */
    List<CreditReturnTypeDTO> findAll();


    /**
     * Get the "id" creditReturnType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CreditReturnTypeDTO> findOne(Long id);

    /**
     * Delete the "id" creditReturnType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.CallLogActionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CallLogAction.
 */
public interface CallLogActionService {

    /**
     * Save a callLogAction.
     *
     * @param callLogActionDTO the entity to save
     * @return the persisted entity
     */
    CallLogActionDTO save(CallLogActionDTO callLogActionDTO);

    /**
     * Get all the callLogActions.
     *
     * @return the list of entities
     */
    List<CallLogActionDTO> findAll();


    /**
     * Get the "id" callLogAction.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CallLogActionDTO> findOne(Long id);

    /**
     * Delete the "id" callLogAction.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.CallLogLineDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CallLogLine.
 */
public interface CallLogLineService {

    /**
     * Save a callLogLine.
     *
     * @param callLogLineDTO the entity to save
     * @return the persisted entity
     */
    CallLogLineDTO save(CallLogLineDTO callLogLineDTO);

    /**
     * Get all the callLogLines.
     *
     * @return the list of entities
     */
    List<CallLogLineDTO> findAll();


    /**
     * Get the "id" callLogLine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CallLogLineDTO> findOne(Long id);

    /**
     * Delete the "id" callLogLine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

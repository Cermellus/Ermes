package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.CallLogDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CallLog.
 */
public interface CallLogService {

    /**
     * Save a callLog.
     *
     * @param callLogDTO the entity to save
     * @return the persisted entity
     */
    CallLogDTO save(CallLogDTO callLogDTO);

    /**
     * Get all the callLogs.
     *
     * @return the list of entities
     */
    List<CallLogDTO> findAll();


    /**
     * Get the "id" callLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CallLogDTO> findOne(Long id);

    /**
     * Delete the "id" callLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

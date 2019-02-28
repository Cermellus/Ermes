package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.ProspectDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Prospect.
 */
public interface ProspectService {

    /**
     * Save a prospect.
     *
     * @param prospectDTO the entity to save
     * @return the persisted entity
     */
    ProspectDTO save(ProspectDTO prospectDTO);

    /**
     * Get all the prospects.
     *
     * @return the list of entities
     */
    List<ProspectDTO> findAll();


    /**
     * Get the "id" prospect.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProspectDTO> findOne(Long id);

    /**
     * Delete the "id" prospect.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

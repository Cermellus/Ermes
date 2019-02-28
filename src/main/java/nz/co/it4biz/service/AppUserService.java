package nz.co.it4biz.service;

import nz.co.it4biz.service.dto.AppUserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AppUser.
 */
public interface AppUserService {

    /**
     * Save a appUser.
     *
     * @param appUserDTO the entity to save
     * @return the persisted entity
     */
    AppUserDTO save(AppUserDTO appUserDTO);

    /**
     * Get all the appUsers.
     *
     * @return the list of entities
     */
    List<AppUserDTO> findAll();


    /**
     * Get the "id" appUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AppUserDTO> findOne(Long id);

    /**
     * Delete the "id" appUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

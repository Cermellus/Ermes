package nz.co.it4biz.web.rest;
import nz.co.it4biz.domain.ErmesUser;
import nz.co.it4biz.repository.ErmesUserRepository;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ErmesUser.
 */
@RestController
@RequestMapping("/api")
public class ErmesUserResource {

    private final Logger log = LoggerFactory.getLogger(ErmesUserResource.class);

    private static final String ENTITY_NAME = "ermesUser";

    private final ErmesUserRepository ermesUserRepository;

    public ErmesUserResource(ErmesUserRepository ermesUserRepository) {
        this.ermesUserRepository = ermesUserRepository;
    }

    /**
     * POST  /ermes-users : Create a new ermesUser.
     *
     * @param ermesUser the ermesUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ermesUser, or with status 400 (Bad Request) if the ermesUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ermes-users")
    public ResponseEntity<ErmesUser> createErmesUser(@Valid @RequestBody ErmesUser ermesUser) throws URISyntaxException {
        log.debug("REST request to save ErmesUser : {}", ermesUser);
        if (ermesUser.getId() != null) {
            throw new BadRequestAlertException("A new ermesUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ErmesUser result = ermesUserRepository.save(ermesUser);
        return ResponseEntity.created(new URI("/api/ermes-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ermes-users : Updates an existing ermesUser.
     *
     * @param ermesUser the ermesUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ermesUser,
     * or with status 400 (Bad Request) if the ermesUser is not valid,
     * or with status 500 (Internal Server Error) if the ermesUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ermes-users")
    public ResponseEntity<ErmesUser> updateErmesUser(@Valid @RequestBody ErmesUser ermesUser) throws URISyntaxException {
        log.debug("REST request to update ErmesUser : {}", ermesUser);
        if (ermesUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ErmesUser result = ermesUserRepository.save(ermesUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ermesUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ermes-users : get all the ermesUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ermesUsers in body
     */
    @GetMapping("/ermes-users")
    public List<ErmesUser> getAllErmesUsers() {
        log.debug("REST request to get all ErmesUsers");
        return ermesUserRepository.findAll();
    }

    /**
     * GET  /ermes-users/:id : get the "id" ermesUser.
     *
     * @param id the id of the ermesUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ermesUser, or with status 404 (Not Found)
     */
    @GetMapping("/ermes-users/{id}")
    public ResponseEntity<ErmesUser> getErmesUser(@PathVariable Long id) {
        log.debug("REST request to get ErmesUser : {}", id);
        Optional<ErmesUser> ermesUser = ermesUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ermesUser);
    }

    /**
     * DELETE  /ermes-users/:id : delete the "id" ermesUser.
     *
     * @param id the id of the ermesUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ermes-users/{id}")
    public ResponseEntity<Void> deleteErmesUser(@PathVariable Long id) {
        log.debug("REST request to delete ErmesUser : {}", id);
        ermesUserRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

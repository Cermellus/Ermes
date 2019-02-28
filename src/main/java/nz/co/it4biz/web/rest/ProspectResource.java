package nz.co.it4biz.web.rest;
import nz.co.it4biz.service.ProspectService;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import nz.co.it4biz.service.dto.ProspectDTO;
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
 * REST controller for managing Prospect.
 */
@RestController
@RequestMapping("/api")
public class ProspectResource {

    private final Logger log = LoggerFactory.getLogger(ProspectResource.class);

    private static final String ENTITY_NAME = "prospect";

    private final ProspectService prospectService;

    public ProspectResource(ProspectService prospectService) {
        this.prospectService = prospectService;
    }

    /**
     * POST  /prospects : Create a new prospect.
     *
     * @param prospectDTO the prospectDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prospectDTO, or with status 400 (Bad Request) if the prospect has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prospects")
    public ResponseEntity<ProspectDTO> createProspect(@Valid @RequestBody ProspectDTO prospectDTO) throws URISyntaxException {
        log.debug("REST request to save Prospect : {}", prospectDTO);
        if (prospectDTO.getId() != null) {
            throw new BadRequestAlertException("A new prospect cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProspectDTO result = prospectService.save(prospectDTO);
        return ResponseEntity.created(new URI("/api/prospects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prospects : Updates an existing prospect.
     *
     * @param prospectDTO the prospectDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prospectDTO,
     * or with status 400 (Bad Request) if the prospectDTO is not valid,
     * or with status 500 (Internal Server Error) if the prospectDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prospects")
    public ResponseEntity<ProspectDTO> updateProspect(@Valid @RequestBody ProspectDTO prospectDTO) throws URISyntaxException {
        log.debug("REST request to update Prospect : {}", prospectDTO);
        if (prospectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProspectDTO result = prospectService.save(prospectDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prospectDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prospects : get all the prospects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prospects in body
     */
    @GetMapping("/prospects")
    public List<ProspectDTO> getAllProspects() {
        log.debug("REST request to get all Prospects");
        return prospectService.findAll();
    }

    /**
     * GET  /prospects/:id : get the "id" prospect.
     *
     * @param id the id of the prospectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prospectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prospects/{id}")
    public ResponseEntity<ProspectDTO> getProspect(@PathVariable Long id) {
        log.debug("REST request to get Prospect : {}", id);
        Optional<ProspectDTO> prospectDTO = prospectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prospectDTO);
    }

    /**
     * DELETE  /prospects/:id : delete the "id" prospect.
     *
     * @param id the id of the prospectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prospects/{id}")
    public ResponseEntity<Void> deleteProspect(@PathVariable Long id) {
        log.debug("REST request to delete Prospect : {}", id);
        prospectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

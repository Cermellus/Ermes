package nz.co.it4biz.web.rest;
import nz.co.it4biz.service.CreditRequestStatusService;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import nz.co.it4biz.service.dto.CreditRequestStatusDTO;
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
 * REST controller for managing CreditRequestStatus.
 */
@RestController
@RequestMapping("/api")
public class CreditRequestStatusResource {

    private final Logger log = LoggerFactory.getLogger(CreditRequestStatusResource.class);

    private static final String ENTITY_NAME = "creditRequestStatus";

    private final CreditRequestStatusService creditRequestStatusService;

    public CreditRequestStatusResource(CreditRequestStatusService creditRequestStatusService) {
        this.creditRequestStatusService = creditRequestStatusService;
    }

    /**
     * POST  /credit-request-statuses : Create a new creditRequestStatus.
     *
     * @param creditRequestStatusDTO the creditRequestStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditRequestStatusDTO, or with status 400 (Bad Request) if the creditRequestStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/credit-request-statuses")
    public ResponseEntity<CreditRequestStatusDTO> createCreditRequestStatus(@Valid @RequestBody CreditRequestStatusDTO creditRequestStatusDTO) throws URISyntaxException {
        log.debug("REST request to save CreditRequestStatus : {}", creditRequestStatusDTO);
        if (creditRequestStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditRequestStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditRequestStatusDTO result = creditRequestStatusService.save(creditRequestStatusDTO);
        return ResponseEntity.created(new URI("/api/credit-request-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /credit-request-statuses : Updates an existing creditRequestStatus.
     *
     * @param creditRequestStatusDTO the creditRequestStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creditRequestStatusDTO,
     * or with status 400 (Bad Request) if the creditRequestStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the creditRequestStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/credit-request-statuses")
    public ResponseEntity<CreditRequestStatusDTO> updateCreditRequestStatus(@Valid @RequestBody CreditRequestStatusDTO creditRequestStatusDTO) throws URISyntaxException {
        log.debug("REST request to update CreditRequestStatus : {}", creditRequestStatusDTO);
        if (creditRequestStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreditRequestStatusDTO result = creditRequestStatusService.save(creditRequestStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creditRequestStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /credit-request-statuses : get all the creditRequestStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of creditRequestStatuses in body
     */
    @GetMapping("/credit-request-statuses")
    public List<CreditRequestStatusDTO> getAllCreditRequestStatuses() {
        log.debug("REST request to get all CreditRequestStatuses");
        return creditRequestStatusService.findAll();
    }

    /**
     * GET  /credit-request-statuses/:id : get the "id" creditRequestStatus.
     *
     * @param id the id of the creditRequestStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditRequestStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/credit-request-statuses/{id}")
    public ResponseEntity<CreditRequestStatusDTO> getCreditRequestStatus(@PathVariable Long id) {
        log.debug("REST request to get CreditRequestStatus : {}", id);
        Optional<CreditRequestStatusDTO> creditRequestStatusDTO = creditRequestStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditRequestStatusDTO);
    }

    /**
     * DELETE  /credit-request-statuses/:id : delete the "id" creditRequestStatus.
     *
     * @param id the id of the creditRequestStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/credit-request-statuses/{id}")
    public ResponseEntity<Void> deleteCreditRequestStatus(@PathVariable Long id) {
        log.debug("REST request to delete CreditRequestStatus : {}", id);
        creditRequestStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

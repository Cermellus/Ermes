package nz.co.it4biz.web.rest;
import nz.co.it4biz.service.CreditRequestService;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import nz.co.it4biz.service.dto.CreditRequestDTO;
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
 * REST controller for managing CreditRequest.
 */
@RestController
@RequestMapping("/api")
public class CreditRequestResource {

    private final Logger log = LoggerFactory.getLogger(CreditRequestResource.class);

    private static final String ENTITY_NAME = "creditRequest";

    private final CreditRequestService creditRequestService;

    public CreditRequestResource(CreditRequestService creditRequestService) {
        this.creditRequestService = creditRequestService;
    }

    /**
     * POST  /credit-requests : Create a new creditRequest.
     *
     * @param creditRequestDTO the creditRequestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditRequestDTO, or with status 400 (Bad Request) if the creditRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/credit-requests")
    public ResponseEntity<CreditRequestDTO> createCreditRequest(@Valid @RequestBody CreditRequestDTO creditRequestDTO) throws URISyntaxException {
        log.debug("REST request to save CreditRequest : {}", creditRequestDTO);
        if (creditRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditRequestDTO result = creditRequestService.save(creditRequestDTO);
        return ResponseEntity.created(new URI("/api/credit-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /credit-requests : Updates an existing creditRequest.
     *
     * @param creditRequestDTO the creditRequestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creditRequestDTO,
     * or with status 400 (Bad Request) if the creditRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the creditRequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/credit-requests")
    public ResponseEntity<CreditRequestDTO> updateCreditRequest(@Valid @RequestBody CreditRequestDTO creditRequestDTO) throws URISyntaxException {
        log.debug("REST request to update CreditRequest : {}", creditRequestDTO);
        if (creditRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreditRequestDTO result = creditRequestService.save(creditRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creditRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /credit-requests : get all the creditRequests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of creditRequests in body
     */
    @GetMapping("/credit-requests")
    public List<CreditRequestDTO> getAllCreditRequests() {
        log.debug("REST request to get all CreditRequests");
        return creditRequestService.findAll();
    }

    /**
     * GET  /credit-requests/:id : get the "id" creditRequest.
     *
     * @param id the id of the creditRequestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditRequestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/credit-requests/{id}")
    public ResponseEntity<CreditRequestDTO> getCreditRequest(@PathVariable Long id) {
        log.debug("REST request to get CreditRequest : {}", id);
        Optional<CreditRequestDTO> creditRequestDTO = creditRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditRequestDTO);
    }

    /**
     * DELETE  /credit-requests/:id : delete the "id" creditRequest.
     *
     * @param id the id of the creditRequestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/credit-requests/{id}")
    public ResponseEntity<Void> deleteCreditRequest(@PathVariable Long id) {
        log.debug("REST request to delete CreditRequest : {}", id);
        creditRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

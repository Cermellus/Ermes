package nz.co.it4biz.web.rest;
import nz.co.it4biz.service.CreditReasonService;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import nz.co.it4biz.service.dto.CreditReasonDTO;
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
 * REST controller for managing CreditReason.
 */
@RestController
@RequestMapping("/api")
public class CreditReasonResource {

    private final Logger log = LoggerFactory.getLogger(CreditReasonResource.class);

    private static final String ENTITY_NAME = "creditReason";

    private final CreditReasonService creditReasonService;

    public CreditReasonResource(CreditReasonService creditReasonService) {
        this.creditReasonService = creditReasonService;
    }

    /**
     * POST  /credit-reasons : Create a new creditReason.
     *
     * @param creditReasonDTO the creditReasonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditReasonDTO, or with status 400 (Bad Request) if the creditReason has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/credit-reasons")
    public ResponseEntity<CreditReasonDTO> createCreditReason(@Valid @RequestBody CreditReasonDTO creditReasonDTO) throws URISyntaxException {
        log.debug("REST request to save CreditReason : {}", creditReasonDTO);
        if (creditReasonDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditReason cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditReasonDTO result = creditReasonService.save(creditReasonDTO);
        return ResponseEntity.created(new URI("/api/credit-reasons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /credit-reasons : Updates an existing creditReason.
     *
     * @param creditReasonDTO the creditReasonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creditReasonDTO,
     * or with status 400 (Bad Request) if the creditReasonDTO is not valid,
     * or with status 500 (Internal Server Error) if the creditReasonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/credit-reasons")
    public ResponseEntity<CreditReasonDTO> updateCreditReason(@Valid @RequestBody CreditReasonDTO creditReasonDTO) throws URISyntaxException {
        log.debug("REST request to update CreditReason : {}", creditReasonDTO);
        if (creditReasonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreditReasonDTO result = creditReasonService.save(creditReasonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creditReasonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /credit-reasons : get all the creditReasons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of creditReasons in body
     */
    @GetMapping("/credit-reasons")
    public List<CreditReasonDTO> getAllCreditReasons() {
        log.debug("REST request to get all CreditReasons");
        return creditReasonService.findAll();
    }

    /**
     * GET  /credit-reasons/:id : get the "id" creditReason.
     *
     * @param id the id of the creditReasonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditReasonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/credit-reasons/{id}")
    public ResponseEntity<CreditReasonDTO> getCreditReason(@PathVariable Long id) {
        log.debug("REST request to get CreditReason : {}", id);
        Optional<CreditReasonDTO> creditReasonDTO = creditReasonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditReasonDTO);
    }

    /**
     * DELETE  /credit-reasons/:id : delete the "id" creditReason.
     *
     * @param id the id of the creditReasonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/credit-reasons/{id}")
    public ResponseEntity<Void> deleteCreditReason(@PathVariable Long id) {
        log.debug("REST request to delete CreditReason : {}", id);
        creditReasonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

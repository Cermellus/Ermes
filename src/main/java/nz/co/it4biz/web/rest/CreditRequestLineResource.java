package nz.co.it4biz.web.rest;
import nz.co.it4biz.service.CreditRequestLineService;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import nz.co.it4biz.service.dto.CreditRequestLineDTO;
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
 * REST controller for managing CreditRequestLine.
 */
@RestController
@RequestMapping("/api")
public class CreditRequestLineResource {

    private final Logger log = LoggerFactory.getLogger(CreditRequestLineResource.class);

    private static final String ENTITY_NAME = "creditRequestLine";

    private final CreditRequestLineService creditRequestLineService;

    public CreditRequestLineResource(CreditRequestLineService creditRequestLineService) {
        this.creditRequestLineService = creditRequestLineService;
    }

    /**
     * POST  /credit-request-lines : Create a new creditRequestLine.
     *
     * @param creditRequestLineDTO the creditRequestLineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditRequestLineDTO, or with status 400 (Bad Request) if the creditRequestLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/credit-request-lines")
    public ResponseEntity<CreditRequestLineDTO> createCreditRequestLine(@Valid @RequestBody CreditRequestLineDTO creditRequestLineDTO) throws URISyntaxException {
        log.debug("REST request to save CreditRequestLine : {}", creditRequestLineDTO);
        if (creditRequestLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditRequestLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditRequestLineDTO result = creditRequestLineService.save(creditRequestLineDTO);
        return ResponseEntity.created(new URI("/api/credit-request-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /credit-request-lines : Updates an existing creditRequestLine.
     *
     * @param creditRequestLineDTO the creditRequestLineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creditRequestLineDTO,
     * or with status 400 (Bad Request) if the creditRequestLineDTO is not valid,
     * or with status 500 (Internal Server Error) if the creditRequestLineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/credit-request-lines")
    public ResponseEntity<CreditRequestLineDTO> updateCreditRequestLine(@Valid @RequestBody CreditRequestLineDTO creditRequestLineDTO) throws URISyntaxException {
        log.debug("REST request to update CreditRequestLine : {}", creditRequestLineDTO);
        if (creditRequestLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreditRequestLineDTO result = creditRequestLineService.save(creditRequestLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creditRequestLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /credit-request-lines : get all the creditRequestLines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of creditRequestLines in body
     */
    @GetMapping("/credit-request-lines")
    public List<CreditRequestLineDTO> getAllCreditRequestLines() {
        log.debug("REST request to get all CreditRequestLines");
        return creditRequestLineService.findAll();
    }

    /**
     * GET  /credit-request-lines/:id : get the "id" creditRequestLine.
     *
     * @param id the id of the creditRequestLineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditRequestLineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/credit-request-lines/{id}")
    public ResponseEntity<CreditRequestLineDTO> getCreditRequestLine(@PathVariable Long id) {
        log.debug("REST request to get CreditRequestLine : {}", id);
        Optional<CreditRequestLineDTO> creditRequestLineDTO = creditRequestLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditRequestLineDTO);
    }

    /**
     * DELETE  /credit-request-lines/:id : delete the "id" creditRequestLine.
     *
     * @param id the id of the creditRequestLineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/credit-request-lines/{id}")
    public ResponseEntity<Void> deleteCreditRequestLine(@PathVariable Long id) {
        log.debug("REST request to delete CreditRequestLine : {}", id);
        creditRequestLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package nz.co.it4biz.web.rest;
import nz.co.it4biz.service.CreditReferenceTypeService;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import nz.co.it4biz.service.dto.CreditReferenceTypeDTO;
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
 * REST controller for managing CreditReferenceType.
 */
@RestController
@RequestMapping("/api")
public class CreditReferenceTypeResource {

    private final Logger log = LoggerFactory.getLogger(CreditReferenceTypeResource.class);

    private static final String ENTITY_NAME = "creditReferenceType";

    private final CreditReferenceTypeService creditReferenceTypeService;

    public CreditReferenceTypeResource(CreditReferenceTypeService creditReferenceTypeService) {
        this.creditReferenceTypeService = creditReferenceTypeService;
    }

    /**
     * POST  /credit-reference-types : Create a new creditReferenceType.
     *
     * @param creditReferenceTypeDTO the creditReferenceTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditReferenceTypeDTO, or with status 400 (Bad Request) if the creditReferenceType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/credit-reference-types")
    public ResponseEntity<CreditReferenceTypeDTO> createCreditReferenceType(@Valid @RequestBody CreditReferenceTypeDTO creditReferenceTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CreditReferenceType : {}", creditReferenceTypeDTO);
        if (creditReferenceTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditReferenceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditReferenceTypeDTO result = creditReferenceTypeService.save(creditReferenceTypeDTO);
        return ResponseEntity.created(new URI("/api/credit-reference-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /credit-reference-types : Updates an existing creditReferenceType.
     *
     * @param creditReferenceTypeDTO the creditReferenceTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creditReferenceTypeDTO,
     * or with status 400 (Bad Request) if the creditReferenceTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the creditReferenceTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/credit-reference-types")
    public ResponseEntity<CreditReferenceTypeDTO> updateCreditReferenceType(@Valid @RequestBody CreditReferenceTypeDTO creditReferenceTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CreditReferenceType : {}", creditReferenceTypeDTO);
        if (creditReferenceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreditReferenceTypeDTO result = creditReferenceTypeService.save(creditReferenceTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creditReferenceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /credit-reference-types : get all the creditReferenceTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of creditReferenceTypes in body
     */
    @GetMapping("/credit-reference-types")
    public List<CreditReferenceTypeDTO> getAllCreditReferenceTypes() {
        log.debug("REST request to get all CreditReferenceTypes");
        return creditReferenceTypeService.findAll();
    }

    /**
     * GET  /credit-reference-types/:id : get the "id" creditReferenceType.
     *
     * @param id the id of the creditReferenceTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditReferenceTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/credit-reference-types/{id}")
    public ResponseEntity<CreditReferenceTypeDTO> getCreditReferenceType(@PathVariable Long id) {
        log.debug("REST request to get CreditReferenceType : {}", id);
        Optional<CreditReferenceTypeDTO> creditReferenceTypeDTO = creditReferenceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditReferenceTypeDTO);
    }

    /**
     * DELETE  /credit-reference-types/:id : delete the "id" creditReferenceType.
     *
     * @param id the id of the creditReferenceTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/credit-reference-types/{id}")
    public ResponseEntity<Void> deleteCreditReferenceType(@PathVariable Long id) {
        log.debug("REST request to delete CreditReferenceType : {}", id);
        creditReferenceTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

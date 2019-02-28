package nz.co.it4biz.web.rest;
import nz.co.it4biz.service.CreditReturnTypeService;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import nz.co.it4biz.service.dto.CreditReturnTypeDTO;
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
 * REST controller for managing CreditReturnType.
 */
@RestController
@RequestMapping("/api")
public class CreditReturnTypeResource {

    private final Logger log = LoggerFactory.getLogger(CreditReturnTypeResource.class);

    private static final String ENTITY_NAME = "creditReturnType";

    private final CreditReturnTypeService creditReturnTypeService;

    public CreditReturnTypeResource(CreditReturnTypeService creditReturnTypeService) {
        this.creditReturnTypeService = creditReturnTypeService;
    }

    /**
     * POST  /credit-return-types : Create a new creditReturnType.
     *
     * @param creditReturnTypeDTO the creditReturnTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditReturnTypeDTO, or with status 400 (Bad Request) if the creditReturnType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/credit-return-types")
    public ResponseEntity<CreditReturnTypeDTO> createCreditReturnType(@Valid @RequestBody CreditReturnTypeDTO creditReturnTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CreditReturnType : {}", creditReturnTypeDTO);
        if (creditReturnTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditReturnType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditReturnTypeDTO result = creditReturnTypeService.save(creditReturnTypeDTO);
        return ResponseEntity.created(new URI("/api/credit-return-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /credit-return-types : Updates an existing creditReturnType.
     *
     * @param creditReturnTypeDTO the creditReturnTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creditReturnTypeDTO,
     * or with status 400 (Bad Request) if the creditReturnTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the creditReturnTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/credit-return-types")
    public ResponseEntity<CreditReturnTypeDTO> updateCreditReturnType(@Valid @RequestBody CreditReturnTypeDTO creditReturnTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CreditReturnType : {}", creditReturnTypeDTO);
        if (creditReturnTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreditReturnTypeDTO result = creditReturnTypeService.save(creditReturnTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creditReturnTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /credit-return-types : get all the creditReturnTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of creditReturnTypes in body
     */
    @GetMapping("/credit-return-types")
    public List<CreditReturnTypeDTO> getAllCreditReturnTypes() {
        log.debug("REST request to get all CreditReturnTypes");
        return creditReturnTypeService.findAll();
    }

    /**
     * GET  /credit-return-types/:id : get the "id" creditReturnType.
     *
     * @param id the id of the creditReturnTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditReturnTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/credit-return-types/{id}")
    public ResponseEntity<CreditReturnTypeDTO> getCreditReturnType(@PathVariable Long id) {
        log.debug("REST request to get CreditReturnType : {}", id);
        Optional<CreditReturnTypeDTO> creditReturnTypeDTO = creditReturnTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditReturnTypeDTO);
    }

    /**
     * DELETE  /credit-return-types/:id : delete the "id" creditReturnType.
     *
     * @param id the id of the creditReturnTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/credit-return-types/{id}")
    public ResponseEntity<Void> deleteCreditReturnType(@PathVariable Long id) {
        log.debug("REST request to delete CreditReturnType : {}", id);
        creditReturnTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

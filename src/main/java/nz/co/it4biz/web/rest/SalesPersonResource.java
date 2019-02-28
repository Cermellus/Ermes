package nz.co.it4biz.web.rest;
import nz.co.it4biz.service.SalesPersonService;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import nz.co.it4biz.service.dto.SalesPersonDTO;
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
 * REST controller for managing SalesPerson.
 */
@RestController
@RequestMapping("/api")
public class SalesPersonResource {

    private final Logger log = LoggerFactory.getLogger(SalesPersonResource.class);

    private static final String ENTITY_NAME = "salesPerson";

    private final SalesPersonService salesPersonService;

    public SalesPersonResource(SalesPersonService salesPersonService) {
        this.salesPersonService = salesPersonService;
    }

    /**
     * POST  /sales-people : Create a new salesPerson.
     *
     * @param salesPersonDTO the salesPersonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salesPersonDTO, or with status 400 (Bad Request) if the salesPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sales-people")
    public ResponseEntity<SalesPersonDTO> createSalesPerson(@Valid @RequestBody SalesPersonDTO salesPersonDTO) throws URISyntaxException {
        log.debug("REST request to save SalesPerson : {}", salesPersonDTO);
        if (salesPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new salesPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalesPersonDTO result = salesPersonService.save(salesPersonDTO);
        return ResponseEntity.created(new URI("/api/sales-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sales-people : Updates an existing salesPerson.
     *
     * @param salesPersonDTO the salesPersonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salesPersonDTO,
     * or with status 400 (Bad Request) if the salesPersonDTO is not valid,
     * or with status 500 (Internal Server Error) if the salesPersonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sales-people")
    public ResponseEntity<SalesPersonDTO> updateSalesPerson(@Valid @RequestBody SalesPersonDTO salesPersonDTO) throws URISyntaxException {
        log.debug("REST request to update SalesPerson : {}", salesPersonDTO);
        if (salesPersonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalesPersonDTO result = salesPersonService.save(salesPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salesPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sales-people : get all the salesPeople.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of salesPeople in body
     */
    @GetMapping("/sales-people")
    public List<SalesPersonDTO> getAllSalesPeople() {
        log.debug("REST request to get all SalesPeople");
        return salesPersonService.findAll();
    }

    /**
     * GET  /sales-people/:id : get the "id" salesPerson.
     *
     * @param id the id of the salesPersonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salesPersonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sales-people/{id}")
    public ResponseEntity<SalesPersonDTO> getSalesPerson(@PathVariable Long id) {
        log.debug("REST request to get SalesPerson : {}", id);
        Optional<SalesPersonDTO> salesPersonDTO = salesPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salesPersonDTO);
    }

    /**
     * DELETE  /sales-people/:id : delete the "id" salesPerson.
     *
     * @param id the id of the salesPersonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sales-people/{id}")
    public ResponseEntity<Void> deleteSalesPerson(@PathVariable Long id) {
        log.debug("REST request to delete SalesPerson : {}", id);
        salesPersonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

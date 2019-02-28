package nz.co.it4biz.web.rest;
import nz.co.it4biz.service.CallLogActionService;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import nz.co.it4biz.service.dto.CallLogActionDTO;
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
 * REST controller for managing CallLogAction.
 */
@RestController
@RequestMapping("/api")
public class CallLogActionResource {

    private final Logger log = LoggerFactory.getLogger(CallLogActionResource.class);

    private static final String ENTITY_NAME = "callLogAction";

    private final CallLogActionService callLogActionService;

    public CallLogActionResource(CallLogActionService callLogActionService) {
        this.callLogActionService = callLogActionService;
    }

    /**
     * POST  /call-log-actions : Create a new callLogAction.
     *
     * @param callLogActionDTO the callLogActionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new callLogActionDTO, or with status 400 (Bad Request) if the callLogAction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/call-log-actions")
    public ResponseEntity<CallLogActionDTO> createCallLogAction(@Valid @RequestBody CallLogActionDTO callLogActionDTO) throws URISyntaxException {
        log.debug("REST request to save CallLogAction : {}", callLogActionDTO);
        if (callLogActionDTO.getId() != null) {
            throw new BadRequestAlertException("A new callLogAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CallLogActionDTO result = callLogActionService.save(callLogActionDTO);
        return ResponseEntity.created(new URI("/api/call-log-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /call-log-actions : Updates an existing callLogAction.
     *
     * @param callLogActionDTO the callLogActionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated callLogActionDTO,
     * or with status 400 (Bad Request) if the callLogActionDTO is not valid,
     * or with status 500 (Internal Server Error) if the callLogActionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/call-log-actions")
    public ResponseEntity<CallLogActionDTO> updateCallLogAction(@Valid @RequestBody CallLogActionDTO callLogActionDTO) throws URISyntaxException {
        log.debug("REST request to update CallLogAction : {}", callLogActionDTO);
        if (callLogActionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CallLogActionDTO result = callLogActionService.save(callLogActionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, callLogActionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /call-log-actions : get all the callLogActions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of callLogActions in body
     */
    @GetMapping("/call-log-actions")
    public List<CallLogActionDTO> getAllCallLogActions() {
        log.debug("REST request to get all CallLogActions");
        return callLogActionService.findAll();
    }

    /**
     * GET  /call-log-actions/:id : get the "id" callLogAction.
     *
     * @param id the id of the callLogActionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the callLogActionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/call-log-actions/{id}")
    public ResponseEntity<CallLogActionDTO> getCallLogAction(@PathVariable Long id) {
        log.debug("REST request to get CallLogAction : {}", id);
        Optional<CallLogActionDTO> callLogActionDTO = callLogActionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(callLogActionDTO);
    }

    /**
     * DELETE  /call-log-actions/:id : delete the "id" callLogAction.
     *
     * @param id the id of the callLogActionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/call-log-actions/{id}")
    public ResponseEntity<Void> deleteCallLogAction(@PathVariable Long id) {
        log.debug("REST request to delete CallLogAction : {}", id);
        callLogActionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

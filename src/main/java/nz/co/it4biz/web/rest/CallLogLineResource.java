package nz.co.it4biz.web.rest;
import nz.co.it4biz.service.CallLogLineService;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import nz.co.it4biz.service.dto.CallLogLineDTO;
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
 * REST controller for managing CallLogLine.
 */
@RestController
@RequestMapping("/api")
public class CallLogLineResource {

    private final Logger log = LoggerFactory.getLogger(CallLogLineResource.class);

    private static final String ENTITY_NAME = "callLogLine";

    private final CallLogLineService callLogLineService;

    public CallLogLineResource(CallLogLineService callLogLineService) {
        this.callLogLineService = callLogLineService;
    }

    /**
     * POST  /call-log-lines : Create a new callLogLine.
     *
     * @param callLogLineDTO the callLogLineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new callLogLineDTO, or with status 400 (Bad Request) if the callLogLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/call-log-lines")
    public ResponseEntity<CallLogLineDTO> createCallLogLine(@Valid @RequestBody CallLogLineDTO callLogLineDTO) throws URISyntaxException {
        log.debug("REST request to save CallLogLine : {}", callLogLineDTO);
        if (callLogLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new callLogLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CallLogLineDTO result = callLogLineService.save(callLogLineDTO);
        return ResponseEntity.created(new URI("/api/call-log-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /call-log-lines : Updates an existing callLogLine.
     *
     * @param callLogLineDTO the callLogLineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated callLogLineDTO,
     * or with status 400 (Bad Request) if the callLogLineDTO is not valid,
     * or with status 500 (Internal Server Error) if the callLogLineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/call-log-lines")
    public ResponseEntity<CallLogLineDTO> updateCallLogLine(@Valid @RequestBody CallLogLineDTO callLogLineDTO) throws URISyntaxException {
        log.debug("REST request to update CallLogLine : {}", callLogLineDTO);
        if (callLogLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CallLogLineDTO result = callLogLineService.save(callLogLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, callLogLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /call-log-lines : get all the callLogLines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of callLogLines in body
     */
    @GetMapping("/call-log-lines")
    public List<CallLogLineDTO> getAllCallLogLines() {
        log.debug("REST request to get all CallLogLines");
        return callLogLineService.findAll();
    }

    /**
     * GET  /call-log-lines/:id : get the "id" callLogLine.
     *
     * @param id the id of the callLogLineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the callLogLineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/call-log-lines/{id}")
    public ResponseEntity<CallLogLineDTO> getCallLogLine(@PathVariable Long id) {
        log.debug("REST request to get CallLogLine : {}", id);
        Optional<CallLogLineDTO> callLogLineDTO = callLogLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(callLogLineDTO);
    }

    /**
     * DELETE  /call-log-lines/:id : delete the "id" callLogLine.
     *
     * @param id the id of the callLogLineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/call-log-lines/{id}")
    public ResponseEntity<Void> deleteCallLogLine(@PathVariable Long id) {
        log.debug("REST request to delete CallLogLine : {}", id);
        callLogLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

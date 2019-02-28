package nz.co.it4biz.web.rest;
import nz.co.it4biz.service.CallLogService;
import nz.co.it4biz.web.rest.errors.BadRequestAlertException;
import nz.co.it4biz.web.rest.util.HeaderUtil;
import nz.co.it4biz.service.dto.CallLogDTO;
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
 * REST controller for managing CallLog.
 */
@RestController
@RequestMapping("/api")
public class CallLogResource {

    private final Logger log = LoggerFactory.getLogger(CallLogResource.class);

    private static final String ENTITY_NAME = "callLog";

    private final CallLogService callLogService;

    public CallLogResource(CallLogService callLogService) {
        this.callLogService = callLogService;
    }

    /**
     * POST  /call-logs : Create a new callLog.
     *
     * @param callLogDTO the callLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new callLogDTO, or with status 400 (Bad Request) if the callLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/call-logs")
    public ResponseEntity<CallLogDTO> createCallLog(@Valid @RequestBody CallLogDTO callLogDTO) throws URISyntaxException {
        log.debug("REST request to save CallLog : {}", callLogDTO);
        if (callLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new callLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CallLogDTO result = callLogService.save(callLogDTO);
        return ResponseEntity.created(new URI("/api/call-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /call-logs : Updates an existing callLog.
     *
     * @param callLogDTO the callLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated callLogDTO,
     * or with status 400 (Bad Request) if the callLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the callLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/call-logs")
    public ResponseEntity<CallLogDTO> updateCallLog(@Valid @RequestBody CallLogDTO callLogDTO) throws URISyntaxException {
        log.debug("REST request to update CallLog : {}", callLogDTO);
        if (callLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CallLogDTO result = callLogService.save(callLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, callLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /call-logs : get all the callLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of callLogs in body
     */
    @GetMapping("/call-logs")
    public List<CallLogDTO> getAllCallLogs() {
        log.debug("REST request to get all CallLogs");
        return callLogService.findAll();
    }

    /**
     * GET  /call-logs/:id : get the "id" callLog.
     *
     * @param id the id of the callLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the callLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/call-logs/{id}")
    public ResponseEntity<CallLogDTO> getCallLog(@PathVariable Long id) {
        log.debug("REST request to get CallLog : {}", id);
        Optional<CallLogDTO> callLogDTO = callLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(callLogDTO);
    }

    /**
     * DELETE  /call-logs/:id : delete the "id" callLog.
     *
     * @param id the id of the callLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/call-logs/{id}")
    public ResponseEntity<Void> deleteCallLog(@PathVariable Long id) {
        log.debug("REST request to delete CallLog : {}", id);
        callLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

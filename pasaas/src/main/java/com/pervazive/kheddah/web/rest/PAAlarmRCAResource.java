package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAAlarmRCA;
import com.pervazive.kheddah.service.PAAlarmRCAService;
import com.pervazive.kheddah.web.rest.util.HeaderUtil;
import com.pervazive.kheddah.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PAAlarmRCA.
 */
@RestController
@RequestMapping("/api")
public class PAAlarmRCAResource {

    private final Logger log = LoggerFactory.getLogger(PAAlarmRCAResource.class);
        
    @Inject
    private PAAlarmRCAService pAAlarmRCAService;

    /**
     * POST  /p-a-alarm-rcas : Create a new pAAlarmRCA.
     *
     * @param pAAlarmRCA the pAAlarmRCA to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAAlarmRCA, or with status 400 (Bad Request) if the pAAlarmRCA has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-alarm-rcas")
    @Timed
    public ResponseEntity<PAAlarmRCA> createPAAlarmRCA(@RequestBody PAAlarmRCA pAAlarmRCA) throws URISyntaxException {
        log.debug("REST request to save PAAlarmRCA : {}", pAAlarmRCA);
        if (pAAlarmRCA.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAAlarmRCA", "idexists", "A new pAAlarmRCA cannot already have an ID")).body(null);
        }
        PAAlarmRCA result = pAAlarmRCAService.save(pAAlarmRCA);
        return ResponseEntity.created(new URI("/api/p-a-alarm-rcas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAAlarmRCA", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-alarm-rcas : Updates an existing pAAlarmRCA.
     *
     * @param pAAlarmRCA the pAAlarmRCA to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAAlarmRCA,
     * or with status 400 (Bad Request) if the pAAlarmRCA is not valid,
     * or with status 500 (Internal Server Error) if the pAAlarmRCA couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-alarm-rcas")
    @Timed
    public ResponseEntity<PAAlarmRCA> updatePAAlarmRCA(@RequestBody PAAlarmRCA pAAlarmRCA) throws URISyntaxException {
        log.debug("REST request to update PAAlarmRCA : {}", pAAlarmRCA);
        if (pAAlarmRCA.getId() == null) {
            return createPAAlarmRCA(pAAlarmRCA);
        }
        PAAlarmRCA result = pAAlarmRCAService.save(pAAlarmRCA);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAAlarmRCA", pAAlarmRCA.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-alarm-rcas : get all the pAAlarmRCAS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAAlarmRCAS in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-alarm-rcas")
    @Timed
    public ResponseEntity<List<PAAlarmRCA>> getAllPAAlarmRCAS(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAAlarmRCAS");
        Page<PAAlarmRCA> page = pAAlarmRCAService.findAll(pageable, request.getUserPrincipal().getName());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-alarm-rcas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-alarm-rcas/:id : get the "id" pAAlarmRCA.
     *
     * @param id the id of the pAAlarmRCA to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAAlarmRCA, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-alarm-rcas/{id}")
    @Timed
    public ResponseEntity<PAAlarmRCA> getPAAlarmRCA(@PathVariable Long id) {
        log.debug("REST request to get PAAlarmRCA : {}", id);
        PAAlarmRCA pAAlarmRCA = pAAlarmRCAService.findOne(id);
        return Optional.ofNullable(pAAlarmRCA)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-alarm-rcas/:id : delete the "id" pAAlarmRCA.
     *
     * @param id the id of the pAAlarmRCA to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-alarm-rcas/{id}")
    @Timed
    public ResponseEntity<Void> deletePAAlarmRCA(@PathVariable Long id) {
        log.debug("REST request to delete PAAlarmRCA : {}", id);
        pAAlarmRCAService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAAlarmRCA", id.toString())).build();
    }

}

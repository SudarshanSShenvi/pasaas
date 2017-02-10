package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PASchedulerInterval;
import com.pervazive.kheddah.service.PASchedulerIntervalService;
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
 * REST controller for managing PASchedulerInterval.
 */
@RestController
@RequestMapping("/api")
public class PASchedulerIntervalResource {

    private final Logger log = LoggerFactory.getLogger(PASchedulerIntervalResource.class);
        
    @Inject
    private PASchedulerIntervalService pASchedulerIntervalService;

    /**
     * POST  /p-a-scheduler-intervals : Create a new pASchedulerInterval.
     *
     * @param pASchedulerInterval the pASchedulerInterval to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pASchedulerInterval, or with status 400 (Bad Request) if the pASchedulerInterval has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-scheduler-intervals")
    @Timed
    public ResponseEntity<PASchedulerInterval> createPASchedulerInterval(@RequestBody PASchedulerInterval pASchedulerInterval) throws URISyntaxException {
        log.debug("REST request to save PASchedulerInterval : {}", pASchedulerInterval);
        if (pASchedulerInterval.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pASchedulerInterval", "idexists", "A new pASchedulerInterval cannot already have an ID")).body(null);
        }
        PASchedulerInterval result = pASchedulerIntervalService.save(pASchedulerInterval);
        return ResponseEntity.created(new URI("/api/p-a-scheduler-intervals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pASchedulerInterval", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-scheduler-intervals : Updates an existing pASchedulerInterval.
     *
     * @param pASchedulerInterval the pASchedulerInterval to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pASchedulerInterval,
     * or with status 400 (Bad Request) if the pASchedulerInterval is not valid,
     * or with status 500 (Internal Server Error) if the pASchedulerInterval couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-scheduler-intervals")
    @Timed
    public ResponseEntity<PASchedulerInterval> updatePASchedulerInterval(@RequestBody PASchedulerInterval pASchedulerInterval) throws URISyntaxException {
        log.debug("REST request to update PASchedulerInterval : {}", pASchedulerInterval);
        if (pASchedulerInterval.getId() == null) {
            return createPASchedulerInterval(pASchedulerInterval);
        }
        PASchedulerInterval result = pASchedulerIntervalService.save(pASchedulerInterval);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pASchedulerInterval", pASchedulerInterval.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-scheduler-intervals : get all the pASchedulerIntervals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pASchedulerIntervals in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-scheduler-intervals")
    @Timed
    public ResponseEntity<List<PASchedulerInterval>> getAllPASchedulerIntervals(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PASchedulerIntervals");
        Page<PASchedulerInterval> page = pASchedulerIntervalService.findAll(pageable,(List<PAOrganization>) request.getSession().getAttribute("organizationsess"));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-scheduler-intervals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-scheduler-intervals/:id : get the "id" pASchedulerInterval.
     *
     * @param id the id of the pASchedulerInterval to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pASchedulerInterval, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-scheduler-intervals/{id}")
    @Timed
    public ResponseEntity<PASchedulerInterval> getPASchedulerInterval(@PathVariable Long id) {
        log.debug("REST request to get PASchedulerInterval : {}", id);
        PASchedulerInterval pASchedulerInterval = pASchedulerIntervalService.findOne(id);
        return Optional.ofNullable(pASchedulerInterval)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-scheduler-intervals/:id : delete the "id" pASchedulerInterval.
     *
     * @param id the id of the pASchedulerInterval to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-scheduler-intervals/{id}")
    @Timed
    public ResponseEntity<Void> deletePASchedulerInterval(@PathVariable Long id) {
        log.debug("REST request to delete PASchedulerInterval : {}", id);
        pASchedulerIntervalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pASchedulerInterval", id.toString())).build();
    }

}

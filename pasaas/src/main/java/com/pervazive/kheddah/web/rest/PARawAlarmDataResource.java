package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PARawAlarmData;
import com.pervazive.kheddah.service.PARawAlarmDataService;
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
 * REST controller for managing PARawAlarmData.
 */
@RestController
@RequestMapping("/api")
public class PARawAlarmDataResource {

    private final Logger log = LoggerFactory.getLogger(PARawAlarmDataResource.class);
        
    @Inject
    private PARawAlarmDataService pARawAlarmDataService;

    /**
     * POST  /p-a-raw-alarm-data : Create a new pARawAlarmData.
     *
     * @param pARawAlarmData the pARawAlarmData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pARawAlarmData, or with status 400 (Bad Request) if the pARawAlarmData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-raw-alarm-data")
    @Timed
    public ResponseEntity<PARawAlarmData> createPARawAlarmData(@RequestBody PARawAlarmData pARawAlarmData) throws URISyntaxException {
        log.debug("REST request to save PARawAlarmData : {}", pARawAlarmData);
        if (pARawAlarmData.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pARawAlarmData", "idexists", "A new pARawAlarmData cannot already have an ID")).body(null);
        }
        PARawAlarmData result = pARawAlarmDataService.save(pARawAlarmData);
        return ResponseEntity.created(new URI("/api/p-a-raw-alarm-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pARawAlarmData", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-raw-alarm-data : Updates an existing pARawAlarmData.
     *
     * @param pARawAlarmData the pARawAlarmData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pARawAlarmData,
     * or with status 400 (Bad Request) if the pARawAlarmData is not valid,
     * or with status 500 (Internal Server Error) if the pARawAlarmData couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-raw-alarm-data")
    @Timed
    public ResponseEntity<PARawAlarmData> updatePARawAlarmData(@RequestBody PARawAlarmData pARawAlarmData) throws URISyntaxException {
        log.debug("REST request to update PARawAlarmData : {}", pARawAlarmData);
        if (pARawAlarmData.getId() == null) {
            return createPARawAlarmData(pARawAlarmData);
        }
        PARawAlarmData result = pARawAlarmDataService.save(pARawAlarmData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pARawAlarmData", pARawAlarmData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-raw-alarm-data : get all the pARawAlarmData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pARawAlarmData in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-raw-alarm-data")
    @Timed
    public ResponseEntity<List<PARawAlarmData>> getAllPARawAlarmData(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PARawAlarmData");
        Page<PARawAlarmData> page = pARawAlarmDataService.findAll(pageable, (List<PAOrganization>) request.getSession().getAttribute("organizationsess"));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-raw-alarm-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-raw-alarm-data/:id : get the "id" pARawAlarmData.
     *
     * @param id the id of the pARawAlarmData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pARawAlarmData, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-raw-alarm-data/{id}")
    @Timed
    public ResponseEntity<PARawAlarmData> getPARawAlarmData(@PathVariable Long id) {
        log.debug("REST request to get PARawAlarmData : {}", id);
        PARawAlarmData pARawAlarmData = pARawAlarmDataService.findOne(id);
        return Optional.ofNullable(pARawAlarmData)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-raw-alarm-data/:id : delete the "id" pARawAlarmData.
     *
     * @param id the id of the pARawAlarmData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-raw-alarm-data/{id}")
    @Timed
    public ResponseEntity<Void> deletePARawAlarmData(@PathVariable Long id) {
        log.debug("REST request to delete PARawAlarmData : {}", id);
        pARawAlarmDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pARawAlarmData", id.toString())).build();
    }

}

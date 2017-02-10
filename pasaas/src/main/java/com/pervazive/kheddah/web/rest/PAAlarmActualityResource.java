package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAAlarmActuality;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.service.PAAlarmActualityService;
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
 * REST controller for managing PAAlarmActuality.
 */
@RestController
@RequestMapping("/api")
public class PAAlarmActualityResource {

    private final Logger log = LoggerFactory.getLogger(PAAlarmActualityResource.class);
        
    @Inject
    private PAAlarmActualityService pAAlarmActualityService;

    /**
     * POST  /p-a-alarm-actualities : Create a new pAAlarmActuality.
     *
     * @param pAAlarmActuality the pAAlarmActuality to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAAlarmActuality, or with status 400 (Bad Request) if the pAAlarmActuality has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-alarm-actualities")
    @Timed
    public ResponseEntity<PAAlarmActuality> createPAAlarmActuality(@RequestBody PAAlarmActuality pAAlarmActuality) throws URISyntaxException {
        log.debug("REST request to save PAAlarmActuality : {}", pAAlarmActuality);
        if (pAAlarmActuality.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAAlarmActuality", "idexists", "A new pAAlarmActuality cannot already have an ID")).body(null);
        }
        PAAlarmActuality result = pAAlarmActualityService.save(pAAlarmActuality);
        return ResponseEntity.created(new URI("/api/p-a-alarm-actualities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAAlarmActuality", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-alarm-actualities : Updates an existing pAAlarmActuality.
     *
     * @param pAAlarmActuality the pAAlarmActuality to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAAlarmActuality,
     * or with status 400 (Bad Request) if the pAAlarmActuality is not valid,
     * or with status 500 (Internal Server Error) if the pAAlarmActuality couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-alarm-actualities")
    @Timed
    public ResponseEntity<PAAlarmActuality> updatePAAlarmActuality(@RequestBody PAAlarmActuality pAAlarmActuality) throws URISyntaxException {
        log.debug("REST request to update PAAlarmActuality : {}", pAAlarmActuality);
        if (pAAlarmActuality.getId() == null) {
            return createPAAlarmActuality(pAAlarmActuality);
        }
        PAAlarmActuality result = pAAlarmActualityService.save(pAAlarmActuality);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAAlarmActuality", pAAlarmActuality.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-alarm-actualities : get all the pAAlarmActualities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAAlarmActualities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-alarm-actualities")
    @Timed
    public ResponseEntity<List<PAAlarmActuality>> getAllPAAlarmActualities(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAAlarmActualities");
        Page<PAAlarmActuality> page = pAAlarmActualityService.findAll(pageable, (List<PAOrganization>) request.getSession().getAttribute("organizationsess"));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-alarm-actualities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-alarm-actualities/:id : get the "id" pAAlarmActuality.
     *
     * @param id the id of the pAAlarmActuality to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAAlarmActuality, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-alarm-actualities/{id}")
    @Timed
    public ResponseEntity<PAAlarmActuality> getPAAlarmActuality(@PathVariable Long id) {
        log.debug("REST request to get PAAlarmActuality : {}", id);
        PAAlarmActuality pAAlarmActuality = pAAlarmActualityService.findOne(id);
        return Optional.ofNullable(pAAlarmActuality)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-alarm-actualities/:id : delete the "id" pAAlarmActuality.
     *
     * @param id the id of the pAAlarmActuality to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-alarm-actualities/{id}")
    @Timed
    public ResponseEntity<Void> deletePAAlarmActuality(@PathVariable Long id) {
        log.debug("REST request to delete PAAlarmActuality : {}", id);
        pAAlarmActualityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAAlarmActuality", id.toString())).build();
    }

}

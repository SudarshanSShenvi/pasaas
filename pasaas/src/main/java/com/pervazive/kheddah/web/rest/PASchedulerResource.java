package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.custom.CurrentOrganization;
import com.pervazive.kheddah.domain.PAAccPrecision;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAScheduler;
import com.pervazive.kheddah.security.SecurityUtils;
import com.pervazive.kheddah.service.PAOrganizationService;
import com.pervazive.kheddah.service.PASchedulerService;
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
 * REST controller for managing PAScheduler.
 */
@RestController
@RequestMapping("/api")
public class PASchedulerResource {

    private final Logger log = LoggerFactory.getLogger(PASchedulerResource.class);
        
    @Inject
    private PASchedulerService pASchedulerService;
    
    @Inject
    private PAOrganizationService paOrganizationService;


    /**
     * POST  /p-a-schedulers : Create a new pAScheduler.
     *
     * @param pAScheduler the pAScheduler to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAScheduler, or with status 400 (Bad Request) if the pAScheduler has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-schedulers")
    @Timed
    public ResponseEntity<PAScheduler> createPAScheduler(@RequestBody PAScheduler pAScheduler) throws URISyntaxException {
        log.debug("REST request to save PAScheduler : {}", pAScheduler);
        if (pAScheduler.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAScheduler", "idexists", "A new pAScheduler cannot already have an ID")).body(null);
        }
        PAScheduler result = pASchedulerService.save(pAScheduler);
        return ResponseEntity.created(new URI("/api/p-a-schedulers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAScheduler", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-schedulers : Updates an existing pAScheduler.
     *
     * @param pAScheduler the pAScheduler to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAScheduler,
     * or with status 400 (Bad Request) if the pAScheduler is not valid,
     * or with status 500 (Internal Server Error) if the pAScheduler couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-schedulers")
    @Timed
    public ResponseEntity<PAScheduler> updatePAScheduler(@RequestBody PAScheduler pAScheduler) throws URISyntaxException {
        log.debug("REST request to update PAScheduler : {}", pAScheduler);
        if (pAScheduler.getId() == null) {
            return createPAScheduler(pAScheduler);
        }
        PAScheduler result = pASchedulerService.save(pAScheduler);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAScheduler", pAScheduler.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-schedulers : get all the pASchedulers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pASchedulers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-schedulers")
    @Timed
    public ResponseEntity<List<PAScheduler>> getAllPASchedulers(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PASchedulers");
        if(CurrentOrganization.getCurrentOrganization() == null) 
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAAlarmActuality", "Organization missing", "Create one to proceed")).body(null);
        
        Page<PAScheduler> page = pASchedulerService.findAll(pageable, paOrganizationService.findOrganizationByName(CurrentOrganization.getCurrentOrganization()) );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-schedulers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-schedulers/:id : get the "id" pAScheduler.
     *
     * @param id the id of the pAScheduler to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAScheduler, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-schedulers/{id}")
    @Timed
    public ResponseEntity<PAScheduler> getPAScheduler(@PathVariable Long id) {
        log.debug("REST request to get PAScheduler : {}", id);
        PAScheduler pAScheduler = pASchedulerService.findOne(id);
        return Optional.ofNullable(pAScheduler)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-schedulers/:id : delete the "id" pAScheduler.
     *
     * @param id the id of the pAScheduler to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-schedulers/{id}")
    @Timed
    public ResponseEntity<Void> deletePAScheduler(@PathVariable Long id) {
        log.debug("REST request to delete PAScheduler : {}", id);
        pASchedulerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAScheduler", id.toString())).build();
    }

}

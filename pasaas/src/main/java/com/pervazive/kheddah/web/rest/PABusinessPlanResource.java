package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PABusinessPlan;
import com.pervazive.kheddah.service.PABusinessPlanService;
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
 * REST controller for managing PABusinessPlan.
 */
@RestController
@RequestMapping("/api")
public class PABusinessPlanResource {

    private final Logger log = LoggerFactory.getLogger(PABusinessPlanResource.class);
        
    @Inject
    private PABusinessPlanService pABusinessPlanService;

    /**
     * POST  /p-a-business-plans : Create a new pABusinessPlan.
     *
     * @param pABusinessPlan the pABusinessPlan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pABusinessPlan, or with status 400 (Bad Request) if the pABusinessPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-business-plans")
    @Timed
    public ResponseEntity<PABusinessPlan> createPABusinessPlan(@RequestBody PABusinessPlan pABusinessPlan) throws URISyntaxException {
        log.debug("REST request to save PABusinessPlan : {}", pABusinessPlan);
        if (pABusinessPlan.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pABusinessPlan", "idexists", "A new pABusinessPlan cannot already have an ID")).body(null);
        }
        PABusinessPlan result = pABusinessPlanService.save(pABusinessPlan);
        return ResponseEntity.created(new URI("/api/p-a-business-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pABusinessPlan", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-business-plans : Updates an existing pABusinessPlan.
     *
     * @param pABusinessPlan the pABusinessPlan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pABusinessPlan,
     * or with status 400 (Bad Request) if the pABusinessPlan is not valid,
     * or with status 500 (Internal Server Error) if the pABusinessPlan couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-business-plans")
    @Timed
    public ResponseEntity<PABusinessPlan> updatePABusinessPlan(@RequestBody PABusinessPlan pABusinessPlan) throws URISyntaxException {
        log.debug("REST request to update PABusinessPlan : {}", pABusinessPlan);
        if (pABusinessPlan.getId() == null) {
            return createPABusinessPlan(pABusinessPlan);
        }
        PABusinessPlan result = pABusinessPlanService.save(pABusinessPlan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pABusinessPlan", pABusinessPlan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-business-plans : get all the pABusinessPlans.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pABusinessPlans in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-business-plans")
    @Timed
    public ResponseEntity<List<PABusinessPlan>> getAllPABusinessPlans(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PABusinessPlans");
        Page<PABusinessPlan> page = pABusinessPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-business-plans");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-business-plans/:id : get the "id" pABusinessPlan.
     *
     * @param id the id of the pABusinessPlan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pABusinessPlan, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-business-plans/{id}")
    @Timed
    public ResponseEntity<PABusinessPlan> getPABusinessPlan(@PathVariable Long id) {
        log.debug("REST request to get PABusinessPlan : {}", id);
        PABusinessPlan pABusinessPlan = pABusinessPlanService.findOne(id);
        return Optional.ofNullable(pABusinessPlan)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-business-plans/:id : delete the "id" pABusinessPlan.
     *
     * @param id the id of the pABusinessPlan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-business-plans/{id}")
    @Timed
    public ResponseEntity<Void> deletePABusinessPlan(@PathVariable Long id) {
        log.debug("REST request to delete PABusinessPlan : {}", id);
        pABusinessPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pABusinessPlan", id.toString())).build();
    }

}

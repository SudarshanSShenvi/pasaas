package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAAccPrecision;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAReliabilityScore;
import com.pervazive.kheddah.security.SecurityUtils;
import com.pervazive.kheddah.service.PAOrganizationService;
import com.pervazive.kheddah.service.PAReliabilityScoreService;
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
 * REST controller for managing PAReliabilityScore.
 */
@RestController
@RequestMapping("/api")
public class PAReliabilityScoreResource {

    private final Logger log = LoggerFactory.getLogger(PAReliabilityScoreResource.class);
        
    @Inject
    private PAReliabilityScoreService pAReliabilityScoreService;

    @Inject
    private PAOrganizationService paOrganizationService;
    
    /**
     * POST  /p-a-reliability-scores : Create a new pAReliabilityScore.
     *
     * @param pAReliabilityScore the pAReliabilityScore to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAReliabilityScore, or with status 400 (Bad Request) if the pAReliabilityScore has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-reliability-scores")
    @Timed
    public ResponseEntity<PAReliabilityScore> createPAReliabilityScore(@RequestBody PAReliabilityScore pAReliabilityScore) throws URISyntaxException {
        log.debug("REST request to save PAReliabilityScore : {}", pAReliabilityScore);
        if (pAReliabilityScore.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAReliabilityScore", "idexists", "A new pAReliabilityScore cannot already have an ID")).body(null);
        }
        PAReliabilityScore result = pAReliabilityScoreService.save(pAReliabilityScore);
        return ResponseEntity.created(new URI("/api/p-a-reliability-scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAReliabilityScore", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-reliability-scores : Updates an existing pAReliabilityScore.
     *
     * @param pAReliabilityScore the pAReliabilityScore to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAReliabilityScore,
     * or with status 400 (Bad Request) if the pAReliabilityScore is not valid,
     * or with status 500 (Internal Server Error) if the pAReliabilityScore couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-reliability-scores")
    @Timed
    public ResponseEntity<PAReliabilityScore> updatePAReliabilityScore(@RequestBody PAReliabilityScore pAReliabilityScore) throws URISyntaxException {
        log.debug("REST request to update PAReliabilityScore : {}", pAReliabilityScore);
        if (pAReliabilityScore.getId() == null) {
            return createPAReliabilityScore(pAReliabilityScore);
        }
        PAReliabilityScore result = pAReliabilityScoreService.save(pAReliabilityScore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAReliabilityScore", pAReliabilityScore.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-reliability-scores : get all the pAReliabilityScores.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAReliabilityScores in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-reliability-scores")
    @Timed
    public ResponseEntity<List<PAReliabilityScore>> getAllPAReliabilityScores(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAReliabilityScores");
        if(SecurityUtils.currentOrganization == null) 
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAReliabilityScore", "Organization missing", "Create one to proceed")).body(null);
        
        Page<PAReliabilityScore> page = pAReliabilityScoreService.findAll(pageable, paOrganizationService.findOrganizationByName(SecurityUtils.currentOrganization));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-reliability-scores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-reliability-scores/:id : get the "id" pAReliabilityScore.
     *
     * @param id the id of the pAReliabilityScore to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAReliabilityScore, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-reliability-scores/{id}")
    @Timed
    public ResponseEntity<PAReliabilityScore> getPAReliabilityScore(@PathVariable Long id) {
        log.debug("REST request to get PAReliabilityScore : {}", id);
        PAReliabilityScore pAReliabilityScore = pAReliabilityScoreService.findOne(id);
        return Optional.ofNullable(pAReliabilityScore)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-reliability-scores/:id : delete the "id" pAReliabilityScore.
     *
     * @param id the id of the pAReliabilityScore to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-reliability-scores/{id}")
    @Timed
    public ResponseEntity<Void> deletePAReliabilityScore(@PathVariable Long id) {
        log.debug("REST request to delete PAReliabilityScore : {}", id);
        pAReliabilityScoreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAReliabilityScore", id.toString())).build();
    }

}

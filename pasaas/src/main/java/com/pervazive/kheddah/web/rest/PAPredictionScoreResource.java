package com.pervazive.kheddah.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAPredictionScore;
import com.pervazive.kheddah.service.PAOrganizationService;
import com.pervazive.kheddah.service.PAPredictionScoreService;
import com.pervazive.kheddah.service.dto.PAPredictionScoreDTO;
import com.pervazive.kheddah.web.rest.util.HeaderUtil;
import com.pervazive.kheddah.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing PAPredictionScore.
 */
@RestController
@RequestMapping("/api")
public class PAPredictionScoreResource {

    private final Logger log = LoggerFactory.getLogger(PAPredictionScoreResource.class);
        
    @Inject
    private PAPredictionScoreService pAPredictionScoreService;
    
    @Inject
    private PAOrganizationService paOrganizationService;


    /**
     * POST  /p-a-prediction-scores : Create a new pAPredictionScore.
     *
     * @param pAPredictionScore the pAPredictionScore to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAPredictionScore, or with status 400 (Bad Request) if the pAPredictionScore has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-prediction-scores")
    @Timed
    public ResponseEntity<PAPredictionScore> createPAPredictionScore(@RequestBody PAPredictionScore pAPredictionScore) throws URISyntaxException {
        log.debug("REST request to save PAPredictionScore : {}", pAPredictionScore);
        if (pAPredictionScore.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAPredictionScore", "idexists", "A new pAPredictionScore cannot already have an ID")).body(null);
        }
        PAPredictionScore result = pAPredictionScoreService.save(pAPredictionScore);
        return ResponseEntity.created(new URI("/api/p-a-prediction-scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAPredictionScore", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-prediction-scores : Updates an existing pAPredictionScore.
     *
     * @param pAPredictionScore the pAPredictionScore to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAPredictionScore,
     * or with status 400 (Bad Request) if the pAPredictionScore is not valid,
     * or with status 500 (Internal Server Error) if the pAPredictionScore couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-prediction-scores")
    @Timed
    public ResponseEntity<PAPredictionScore> updatePAPredictionScore(@RequestBody PAPredictionScore pAPredictionScore) throws URISyntaxException {
        log.debug("REST request to update PAPredictionScore : {}", pAPredictionScore);
        if (pAPredictionScore.getId() == null) {
            return createPAPredictionScore(pAPredictionScore);
        }
        PAPredictionScore result = pAPredictionScoreService.save(pAPredictionScore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAPredictionScore", pAPredictionScore.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-prediction-scores : get all the pAPredictionScores.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAPredictionScores in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-prediction-scores")
    @Timed
    public ResponseEntity<List<PAPredictionScore>> getAllPAPredictionScores(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAPredictionScores");
       /* if(CurrentOrganization.getCurrentOrganization() == null) 
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAPredictionScore", "Organization missing", "Create one to proceed")).body(null);*/
        
        Page<PAPredictionScore> page = pAPredictionScoreService.findAll(pageable, paOrganizationService.findOrganizationByName(((PAOrganization) request.getSession().getAttribute("s_organization")).getOrganization()) );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-prediction-scores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/p-a-prediction-scoresfailing/{probStart}/{probEnd:.+}")
    @Timed
    public ResponseEntity<List<PAPredictionScoreDTO>> getAllFailingHighProbElements(@PathVariable String probStart, @PathVariable String probEnd, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug(probStart +" : "+probEnd + " - REST request to get a page of PAPredictionScores"+ Float.parseFloat(probStart) +" : "+Float.parseFloat(probEnd));
        Page<PAPredictionScore> page = pAPredictionScoreService.findFailuresAbove(pageable, Float.parseFloat(probStart), Float.parseFloat(probEnd));
        
        List<PAPredictionScoreDTO> paPredictionScoreDTOs = page.getContent().stream()
                .map(PAPredictionScoreDTO::new)
                .collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-prediction-scoresfailing");
        return new ResponseEntity<>(paPredictionScoreDTOs, headers, HttpStatus.OK);
    }
    
    @GetMapping("/p-a-prediction-scoresstable/{probStart}/{probEnd:.+}")
    @Timed
    public ResponseEntity<List<PAPredictionScoreDTO>> getAllStableHighProbElements(@PathVariable String probStart, @PathVariable String probEnd, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug(probStart +" : "+probEnd + " - REST request to get a page of PAPredictionScores"+ Float.parseFloat(probStart) +" : "+Float.parseFloat(probEnd));
        Page<PAPredictionScore> page = pAPredictionScoreService.findStableAbove(pageable, Float.parseFloat(probStart), Float.parseFloat(probEnd));
        List<PAPredictionScoreDTO> paPredictionScoreDTOs = page.getContent().stream()
                .map(PAPredictionScoreDTO::new)
                .collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-prediction-scoresstable");
        return new ResponseEntity<>(paPredictionScoreDTOs, headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-prediction-scores/:id : get the "id" pAPredictionScore.
     *
     * @param id the id of the pAPredictionScore to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAPredictionScore, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-prediction-scores/{id}")
    @Timed
    public ResponseEntity<PAPredictionScore> getPAPredictionScore(@PathVariable Long id) {
        log.debug("REST request to get PAPredictionScore : {}", id);
        PAPredictionScore pAPredictionScore = pAPredictionScoreService.findOne(id);
        return Optional.ofNullable(pAPredictionScore)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-prediction-scores/:id : delete the "id" pAPredictionScore.
     *
     * @param id the id of the pAPredictionScore to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-prediction-scores/{id}")
    @Timed
    public ResponseEntity<Void> deletePAPredictionScore(@PathVariable Long id) {
        log.debug("REST request to delete PAPredictionScore : {}", id);
        pAPredictionScoreService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAPredictionScore", id.toString())).build();
    }

}

package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.custom.CurrentOrganization;
import com.pervazive.kheddah.domain.PAAccPrecision;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAPrediction;
import com.pervazive.kheddah.security.SecurityUtils;
import com.pervazive.kheddah.service.PAOrganizationService;
import com.pervazive.kheddah.service.PAPredictionService;
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
 * REST controller for managing PAPrediction.
 */
@RestController
@RequestMapping("/api")
public class PAPredictionResource {

    private final Logger log = LoggerFactory.getLogger(PAPredictionResource.class);
        
    @Inject
    private PAPredictionService pAPredictionService;
    
    @Inject
    private PAOrganizationService paOrganizationService;


    /**
     * POST  /p-a-predictions : Create a new pAPrediction.
     *
     * @param pAPrediction the pAPrediction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAPrediction, or with status 400 (Bad Request) if the pAPrediction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-predictions")
    @Timed
    public ResponseEntity<PAPrediction> createPAPrediction(@RequestBody PAPrediction pAPrediction) throws URISyntaxException {
        log.debug("REST request to save PAPrediction : {}", pAPrediction);
        if (pAPrediction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAPrediction", "idexists", "A new pAPrediction cannot already have an ID")).body(null);
        }
        PAPrediction result = pAPredictionService.save(pAPrediction);
        return ResponseEntity.created(new URI("/api/p-a-predictions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAPrediction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-predictions : Updates an existing pAPrediction.
     *
     * @param pAPrediction the pAPrediction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAPrediction,
     * or with status 400 (Bad Request) if the pAPrediction is not valid,
     * or with status 500 (Internal Server Error) if the pAPrediction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-predictions")
    @Timed
    public ResponseEntity<PAPrediction> updatePAPrediction(@RequestBody PAPrediction pAPrediction) throws URISyntaxException {
        log.debug("REST request to update PAPrediction : {}", pAPrediction);
        if (pAPrediction.getId() == null) {
            return createPAPrediction(pAPrediction);
        }
        PAPrediction result = pAPredictionService.save(pAPrediction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAPrediction", pAPrediction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-predictions : get all the pAPredictions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAPredictions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-predictions")
    @Timed
    public ResponseEntity<List<PAPrediction>> getAllPAPredictions(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAPredictions");
        if(CurrentOrganization.getCurrentOrganization() == null) 
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAPrediction", "Organization missing", "Create one to proceed")).body(null);
        
        Page<PAPrediction> page = pAPredictionService.findAll(pageable, paOrganizationService.findOrganizationByName(CurrentOrganization.getCurrentOrganization()) );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-predictions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-predictions/:id : get the "id" pAPrediction.
     *
     * @param id the id of the pAPrediction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAPrediction, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-predictions/{id}")
    @Timed
    public ResponseEntity<PAPrediction> getPAPrediction(@PathVariable Long id) {
        log.debug("REST request to get PAPrediction : {}", id);
        PAPrediction pAPrediction = pAPredictionService.findOne(id);
        return Optional.ofNullable(pAPrediction)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-predictions/:id : delete the "id" pAPrediction.
     *
     * @param id the id of the pAPrediction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-predictions/{id}")
    @Timed
    public ResponseEntity<Void> deletePAPrediction(@PathVariable Long id) {
        log.debug("REST request to delete PAPrediction : {}", id);
        pAPredictionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAPrediction", id.toString())).build();
    }

}

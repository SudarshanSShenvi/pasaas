package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PANEDetails;
import com.pervazive.kheddah.service.PANEDetailsService;
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
 * REST controller for managing PANEDetails.
 */
@RestController
@RequestMapping("/api")
public class PANEDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PANEDetailsResource.class);
        
    @Inject
    private PANEDetailsService pANEDetailsService;

    /**
     * POST  /p-ane-details : Create a new pANEDetails.
     *
     * @param pANEDetails the pANEDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pANEDetails, or with status 400 (Bad Request) if the pANEDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-ane-details")
    @Timed
    public ResponseEntity<PANEDetails> createPANEDetails(@RequestBody PANEDetails pANEDetails) throws URISyntaxException {
        log.debug("REST request to save PANEDetails : {}", pANEDetails);
        if (pANEDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pANEDetails", "idexists", "A new pANEDetails cannot already have an ID")).body(null);
        }
        PANEDetails result = pANEDetailsService.save(pANEDetails);
        return ResponseEntity.created(new URI("/api/p-ane-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pANEDetails", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-ane-details : Updates an existing pANEDetails.
     *
     * @param pANEDetails the pANEDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pANEDetails,
     * or with status 400 (Bad Request) if the pANEDetails is not valid,
     * or with status 500 (Internal Server Error) if the pANEDetails couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-ane-details")
    @Timed
    public ResponseEntity<PANEDetails> updatePANEDetails(@RequestBody PANEDetails pANEDetails) throws URISyntaxException {
        log.debug("REST request to update PANEDetails : {}", pANEDetails);
        if (pANEDetails.getId() == null) {
            return createPANEDetails(pANEDetails);
        }
        PANEDetails result = pANEDetailsService.save(pANEDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pANEDetails", pANEDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-ane-details : get all the pANEDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pANEDetails in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-ane-details")
    @Timed
    public ResponseEntity<List<PANEDetails>> getAllPANEDetails(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PANEDetails");
        Page<PANEDetails> page = pANEDetailsService.findAll(pageable, request.getUserPrincipal().getName());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-ane-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-ane-details/:id : get the "id" pANEDetails.
     *
     * @param id the id of the pANEDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pANEDetails, or with status 404 (Not Found)
     */
    @GetMapping("/p-ane-details/{id}")
    @Timed
    public ResponseEntity<PANEDetails> getPANEDetails(@PathVariable Long id) {
        log.debug("REST request to get PANEDetails : {}", id);
        PANEDetails pANEDetails = pANEDetailsService.findOne(id);
        return Optional.ofNullable(pANEDetails)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-ane-details/:id : delete the "id" pANEDetails.
     *
     * @param id the id of the pANEDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-ane-details/{id}")
    @Timed
    public ResponseEntity<Void> deletePANEDetails(@PathVariable Long id) {
        log.debug("REST request to delete PANEDetails : {}", id);
        pANEDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pANEDetails", id.toString())).build();
    }

}

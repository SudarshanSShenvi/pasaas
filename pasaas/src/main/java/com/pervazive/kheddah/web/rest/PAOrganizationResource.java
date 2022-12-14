package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.service.PAOrganizationService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PAOrganization.
 */
@RestController
@RequestMapping("/api")
public class PAOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(PAOrganizationResource.class);
        
    @Inject
    private PAOrganizationService pAOrganizationService;

    /**
     * POST  /p-a-organizations : Create a new pAOrganization.
     *
     * @param pAOrganization the pAOrganization to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAOrganization, or with status 400 (Bad Request) if the pAOrganization has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-organizations")
    @Timed
    public ResponseEntity<PAOrganization> createPAOrganization(@RequestBody PAOrganization pAOrganization) throws URISyntaxException {
        log.debug("REST request to save PAOrganization : {}", pAOrganization);
        if (pAOrganization.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAOrganization", "idexists", "A new pAOrganization cannot already have an ID")).body(null);
        }
        PAOrganization result = pAOrganizationService.save(pAOrganization);
        return ResponseEntity.created(new URI("/api/p-a-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAOrganization", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-organizations : Updates an existing pAOrganization.
     *
     * @param pAOrganization the pAOrganization to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAOrganization,
     * or with status 400 (Bad Request) if the pAOrganization is not valid,
     * or with status 500 (Internal Server Error) if the pAOrganization couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-organizations")
    @Timed
    public ResponseEntity<PAOrganization> updatePAOrganization(@RequestBody PAOrganization pAOrganization) throws URISyntaxException {
        log.debug("REST request to update PAOrganization : {}", pAOrganization);
        if (pAOrganization.getId() == null) {
            return createPAOrganization(pAOrganization);
        }
        PAOrganization result = pAOrganizationService.save(pAOrganization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAOrganization", pAOrganization.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-organizations : get all the pAOrganizations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAOrganizations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-organizations")
    @Timed
    public ResponseEntity<List<PAOrganization>> getAllPAOrganizations(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAOrganizations");
        Page<PAOrganization> page = pAOrganizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-organizations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-organizations/:id : get the "id" pAOrganization.
     *
     * @param id the id of the pAOrganization to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAOrganization, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-organizations/{id}")
    @Timed
    public ResponseEntity<PAOrganization> getPAOrganization(@PathVariable Long id) {
        log.debug("REST request to get PAOrganization : {}", id);
        PAOrganization pAOrganization = pAOrganizationService.findOne(id);
        return Optional.ofNullable(pAOrganization)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-organizations/:id : delete the "id" pAOrganization.
     *
     * @param id the id of the pAOrganization to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-organizations/{id}")
    @Timed
    public ResponseEntity<Void> deletePAOrganization(@PathVariable Long id) {
        log.debug("REST request to delete PAOrganization : {}", id);
        pAOrganizationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAOrganization", id.toString())).build();
    }

}

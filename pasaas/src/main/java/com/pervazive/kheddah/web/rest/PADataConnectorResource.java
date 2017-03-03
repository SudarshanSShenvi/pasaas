package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAAccPrecision;
import com.pervazive.kheddah.domain.PADataConnector;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.security.SecurityUtils;
import com.pervazive.kheddah.service.PADataConnectorService;
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
import javax.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PADataConnector.
 */
@RestController
@RequestMapping("/api")
public class PADataConnectorResource {

    private final Logger log = LoggerFactory.getLogger(PADataConnectorResource.class);
        
    @Inject
    private PADataConnectorService pADataConnectorService;
    
    @Inject
    private PAOrganizationService paOrganizationService;


    /**
     * POST  /p-a-data-connectors : Create a new pADataConnector.
     *
     * @param pADataConnector the pADataConnector to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pADataConnector, or with status 400 (Bad Request) if the pADataConnector has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-data-connectors")
    @Timed
    public ResponseEntity<PADataConnector> createPADataConnector(@RequestBody PADataConnector pADataConnector) throws URISyntaxException {
        log.debug("REST request to save PADataConnector : {}", pADataConnector);
        if (pADataConnector.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pADataConnector", "idexists", "A new pADataConnector cannot already have an ID")).body(null);
        }
        PADataConnector result = pADataConnectorService.save(pADataConnector);
        return ResponseEntity.created(new URI("/api/p-a-data-connectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pADataConnector", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-data-connectors : Updates an existing pADataConnector.
     *
     * @param pADataConnector the pADataConnector to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pADataConnector,
     * or with status 400 (Bad Request) if the pADataConnector is not valid,
     * or with status 500 (Internal Server Error) if the pADataConnector couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-data-connectors")
    @Timed
    public ResponseEntity<PADataConnector> updatePADataConnector(@RequestBody PADataConnector pADataConnector) throws URISyntaxException {
        log.debug("REST request to update PADataConnector : {}", pADataConnector);
        if (pADataConnector.getId() == null) {
            return createPADataConnector(pADataConnector);
        }
        PADataConnector result = pADataConnectorService.save(pADataConnector);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pADataConnector", pADataConnector.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-data-connectors : get all the pADataConnectors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pADataConnectors in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-data-connectors")
    @Timed
    public ResponseEntity<List<PADataConnector>> getAllPADataConnectors(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PADataConnectors");
        if(SecurityUtils.currentOrganization == null) 
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pADataConnector", "Organization missing", "Create one to proceed")).body(null);
        
        Page<PADataConnector> page = pADataConnectorService.findAll(pageable, paOrganizationService.findOrganizationByName(SecurityUtils.currentOrganization) );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-data-connectors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-data-connectors/:id : get the "id" pADataConnector.
     *
     * @param id the id of the pADataConnector to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pADataConnector, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-data-connectors/{id}")
    @Timed
    public ResponseEntity<PADataConnector> getPADataConnector(@PathVariable Long id) {
        log.debug("REST request to get PADataConnector : {}", id);
        PADataConnector pADataConnector = pADataConnectorService.findOne(id);
        return Optional.ofNullable(pADataConnector)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-data-connectors/:id : delete the "id" pADataConnector.
     *
     * @param id the id of the pADataConnector to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-data-connectors/{id}")
    @Timed
    public ResponseEntity<Void> deletePADataConnector(@PathVariable Long id) {
        log.debug("REST request to delete PADataConnector : {}", id);
        pADataConnectorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pADataConnector", id.toString())).build();
    }

}

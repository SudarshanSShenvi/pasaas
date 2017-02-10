package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAPMTRequest;
import com.pervazive.kheddah.service.PAPMTRequestService;
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
 * REST controller for managing PAPMTRequest.
 */
@RestController
@RequestMapping("/api")
public class PAPMTRequestResource {

    private final Logger log = LoggerFactory.getLogger(PAPMTRequestResource.class);
        
    @Inject
    private PAPMTRequestService pAPMTRequestService;

    /**
     * POST  /p-apmt-requests : Create a new pAPMTRequest.
     *
     * @param pAPMTRequest the pAPMTRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAPMTRequest, or with status 400 (Bad Request) if the pAPMTRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-apmt-requests")
    @Timed
    public ResponseEntity<PAPMTRequest> createPAPMTRequest(@RequestBody PAPMTRequest pAPMTRequest) throws URISyntaxException {
        log.debug("REST request to save PAPMTRequest : {}", pAPMTRequest);
        if (pAPMTRequest.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAPMTRequest", "idexists", "A new pAPMTRequest cannot already have an ID")).body(null);
        }
        PAPMTRequest result = pAPMTRequestService.save(pAPMTRequest);
        return ResponseEntity.created(new URI("/api/p-apmt-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAPMTRequest", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-apmt-requests : Updates an existing pAPMTRequest.
     *
     * @param pAPMTRequest the pAPMTRequest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAPMTRequest,
     * or with status 400 (Bad Request) if the pAPMTRequest is not valid,
     * or with status 500 (Internal Server Error) if the pAPMTRequest couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-apmt-requests")
    @Timed
    public ResponseEntity<PAPMTRequest> updatePAPMTRequest(@RequestBody PAPMTRequest pAPMTRequest) throws URISyntaxException {
        log.debug("REST request to update PAPMTRequest : {}", pAPMTRequest);
        if (pAPMTRequest.getId() == null) {
            return createPAPMTRequest(pAPMTRequest);
        }
        PAPMTRequest result = pAPMTRequestService.save(pAPMTRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAPMTRequest", pAPMTRequest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-apmt-requests : get all the pAPMTRequests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAPMTRequests in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-apmt-requests")
    @Timed
    public ResponseEntity<List<PAPMTRequest>> getAllPAPMTRequests(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAPMTRequests");
        Page<PAPMTRequest> page = pAPMTRequestService.findAll(pageable, (List<PAOrganization>) request.getSession().getAttribute("organizationsess"));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-apmt-requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-apmt-requests/:id : get the "id" pAPMTRequest.
     *
     * @param id the id of the pAPMTRequest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAPMTRequest, or with status 404 (Not Found)
     */
    @GetMapping("/p-apmt-requests/{id}")
    @Timed
    public ResponseEntity<PAPMTRequest> getPAPMTRequest(@PathVariable Long id) {
        log.debug("REST request to get PAPMTRequest : {}", id);
        PAPMTRequest pAPMTRequest = pAPMTRequestService.findOne(id);
        return Optional.ofNullable(pAPMTRequest)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-apmt-requests/:id : delete the "id" pAPMTRequest.
     *
     * @param id the id of the pAPMTRequest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-apmt-requests/{id}")
    @Timed
    public ResponseEntity<Void> deletePAPMTRequest(@PathVariable Long id) {
        log.debug("REST request to delete PAPMTRequest : {}", id);
        pAPMTRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAPMTRequest", id.toString())).build();
    }

}

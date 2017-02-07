package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAAccPrecision;
import com.pervazive.kheddah.service.PAAccPrecisionService;
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
 * REST controller for managing PAAccPrecision.
 */
@RestController
@RequestMapping("/api")
public class PAAccPrecisionResource {

    private final Logger log = LoggerFactory.getLogger(PAAccPrecisionResource.class);
        
    @Inject
    private PAAccPrecisionService pAAccPrecisionService;

    /**
     * POST  /p-a-acc-precisions : Create a new pAAccPrecision.
     *
     * @param pAAccPrecision the pAAccPrecision to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAAccPrecision, or with status 400 (Bad Request) if the pAAccPrecision has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-acc-precisions")
    @Timed
    public ResponseEntity<PAAccPrecision> createPAAccPrecision(@RequestBody PAAccPrecision pAAccPrecision) throws URISyntaxException {
        log.debug("REST request to save PAAccPrecision : {}", pAAccPrecision);
        if (pAAccPrecision.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAAccPrecision", "idexists", "A new pAAccPrecision cannot already have an ID")).body(null);
        }
        PAAccPrecision result = pAAccPrecisionService.save(pAAccPrecision);
        return ResponseEntity.created(new URI("/api/p-a-acc-precisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAAccPrecision", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-acc-precisions : Updates an existing pAAccPrecision.
     *
     * @param pAAccPrecision the pAAccPrecision to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAAccPrecision,
     * or with status 400 (Bad Request) if the pAAccPrecision is not valid,
     * or with status 500 (Internal Server Error) if the pAAccPrecision couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-acc-precisions")
    @Timed
    public ResponseEntity<PAAccPrecision> updatePAAccPrecision(@RequestBody PAAccPrecision pAAccPrecision) throws URISyntaxException {
        log.debug("REST request to update PAAccPrecision : {}", pAAccPrecision);
        if (pAAccPrecision.getId() == null) {
            return createPAAccPrecision(pAAccPrecision);
        }
        PAAccPrecision result = pAAccPrecisionService.save(pAAccPrecision);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAAccPrecision", pAAccPrecision.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-acc-precisions : get all the pAAccPrecisions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAAccPrecisions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-acc-precisions")
    @Timed
    public ResponseEntity<List<PAAccPrecision>> getAllPAAccPrecisions(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAAccPrecisions");
        Page<PAAccPrecision> page = pAAccPrecisionService.findAll(pageable, request.getUserPrincipal().getName());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-acc-precisions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-acc-precisions/:id : get the "id" pAAccPrecision.
     *
     * @param id the id of the pAAccPrecision to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAAccPrecision, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-acc-precisions/{id}")
    @Timed
    public ResponseEntity<PAAccPrecision> getPAAccPrecision(@PathVariable Long id) {
        log.debug("REST request to get PAAccPrecision : {}", id);
        PAAccPrecision pAAccPrecision = pAAccPrecisionService.findOne(id);
        return Optional.ofNullable(pAAccPrecision)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-acc-precisions/:id : delete the "id" pAAccPrecision.
     *
     * @param id the id of the pAAccPrecision to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-acc-precisions/{id}")
    @Timed
    public ResponseEntity<Void> deletePAAccPrecision(@PathVariable Long id) {
        log.debug("REST request to delete PAAccPrecision : {}", id);
        pAAccPrecisionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAAccPrecision", id.toString())).build();
    }

}

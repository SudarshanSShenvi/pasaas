package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PASaxCode;
import com.pervazive.kheddah.service.PASaxCodeService;
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
 * REST controller for managing PASaxCode.
 */
@RestController
@RequestMapping("/api")
public class PASaxCodeResource {

    private final Logger log = LoggerFactory.getLogger(PASaxCodeResource.class);
        
    @Inject
    private PASaxCodeService pASaxCodeService;

    /**
     * POST  /p-a-sax-codes : Create a new pASaxCode.
     *
     * @param pASaxCode the pASaxCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pASaxCode, or with status 400 (Bad Request) if the pASaxCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-sax-codes")
    @Timed
    public ResponseEntity<PASaxCode> createPASaxCode(@RequestBody PASaxCode pASaxCode) throws URISyntaxException {
        log.debug("REST request to save PASaxCode : {}", pASaxCode);
        if (pASaxCode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pASaxCode", "idexists", "A new pASaxCode cannot already have an ID")).body(null);
        }
        PASaxCode result = pASaxCodeService.save(pASaxCode);
        return ResponseEntity.created(new URI("/api/p-a-sax-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pASaxCode", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-sax-codes : Updates an existing pASaxCode.
     *
     * @param pASaxCode the pASaxCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pASaxCode,
     * or with status 400 (Bad Request) if the pASaxCode is not valid,
     * or with status 500 (Internal Server Error) if the pASaxCode couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-sax-codes")
    @Timed
    public ResponseEntity<PASaxCode> updatePASaxCode(@RequestBody PASaxCode pASaxCode) throws URISyntaxException {
        log.debug("REST request to update PASaxCode : {}", pASaxCode);
        if (pASaxCode.getId() == null) {
            return createPASaxCode(pASaxCode);
        }
        PASaxCode result = pASaxCodeService.save(pASaxCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pASaxCode", pASaxCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-sax-codes : get all the pASaxCodes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pASaxCodes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-sax-codes")
    @Timed
    public ResponseEntity<List<PASaxCode>> getAllPASaxCodes(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PASaxCodes");
        @SuppressWarnings("unchecked")
		Page<PASaxCode> page = pASaxCodeService.findAll(pageable, (List<PAOrganization>) request.getSession().getAttribute("organizationsess"));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-sax-codes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-sax-codes/:id : get the "id" pASaxCode.
     *
     * @param id the id of the pASaxCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pASaxCode, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-sax-codes/{id}")
    @Timed
    public ResponseEntity<PASaxCode> getPASaxCode(@PathVariable Long id) {
        log.debug("REST request to get PASaxCode : {}", id);
        PASaxCode pASaxCode = pASaxCodeService.findOne(id);
        return Optional.ofNullable(pASaxCode)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-sax-codes/:id : delete the "id" pASaxCode.
     *
     * @param id the id of the pASaxCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-sax-codes/{id}")
    @Timed
    public ResponseEntity<Void> deletePASaxCode(@PathVariable Long id) {
        log.debug("REST request to delete PASaxCode : {}", id);
        pASaxCodeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pASaxCode", id.toString())).build();
    }

}

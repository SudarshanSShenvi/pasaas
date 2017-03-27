package com.pervazive.kheddah.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
import com.pervazive.kheddah.custom.CurrentOrganization;
import com.pervazive.kheddah.domain.PASaxCodeTmp;
import com.pervazive.kheddah.service.PAOrganizationService;
import com.pervazive.kheddah.service.PASaxCodeTmpService;
import com.pervazive.kheddah.web.rest.util.HeaderUtil;
import com.pervazive.kheddah.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing PASaxCodeTmp.
 */
@RestController
@RequestMapping("/api")
public class PASaxCodeTmpResource {

    private final Logger log = LoggerFactory.getLogger(PASaxCodeTmpResource.class);
        
    @Inject
    private PASaxCodeTmpService pASaxCodeTmpService;
    
    @Inject
    private PAOrganizationService paOrganizationService;


    /**
     * POST  /p-a-sax-code-tmps : Create a new pASaxCodeTmp.
     *
     * @param pASaxCodeTmp the pASaxCodeTmp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pASaxCodeTmp, or with status 400 (Bad Request) if the pASaxCodeTmp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-sax-code-tmps")
    @Timed
    public ResponseEntity<PASaxCodeTmp> createPASaxCodeTmp(@RequestBody PASaxCodeTmp pASaxCodeTmp) throws URISyntaxException {
        log.debug("REST request to save PASaxCodeTmp : {}", pASaxCodeTmp);
        if (pASaxCodeTmp.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pASaxCodeTmp", "idexists", "A new pASaxCodeTmp cannot already have an ID")).body(null);
        }
        PASaxCodeTmp result = pASaxCodeTmpService.save(pASaxCodeTmp);
        return ResponseEntity.created(new URI("/api/p-a-sax-code-tmps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pASaxCodeTmp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-sax-code-tmps : Updates an existing pASaxCodeTmp.
     *
     * @param pASaxCodeTmp the pASaxCodeTmp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pASaxCodeTmp,
     * or with status 400 (Bad Request) if the pASaxCodeTmp is not valid,
     * or with status 500 (Internal Server Error) if the pASaxCodeTmp couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-sax-code-tmps")
    @Timed
    public ResponseEntity<PASaxCodeTmp> updatePASaxCodeTmp(@RequestBody PASaxCodeTmp pASaxCodeTmp) throws URISyntaxException {
        log.debug("REST request to update PASaxCodeTmp : {}", pASaxCodeTmp);
        if (pASaxCodeTmp.getId() == null) {
            return createPASaxCodeTmp(pASaxCodeTmp);
        }
        PASaxCodeTmp result = pASaxCodeTmpService.save(pASaxCodeTmp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pASaxCodeTmp", pASaxCodeTmp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-sax-code-tmps : get all the pASaxCodeTmps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pASaxCodeTmps in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-sax-code-tmps")
    @Timed
    public ResponseEntity<List<PASaxCodeTmp>> getAllPASaxCodeTmps(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PASaxCodeTmps");
        
        if(CurrentOrganization.getCurrentOrganization() == null) 
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAReliabilityScore", "Organization missing", "Create one to proceed")).body(null);
        
        Page<PASaxCodeTmp> page = pASaxCodeTmpService.findAll(pageable, paOrganizationService.findOrganizationByName(CurrentOrganization.getCurrentOrganization()));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-sax-code-tmps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-sax-code-tmps/:id : get the "id" pASaxCodeTmp.
     *
     * @param id the id of the pASaxCodeTmp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pASaxCodeTmp, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-sax-code-tmps/{id}")
    @Timed
    public ResponseEntity<PASaxCodeTmp> getPASaxCodeTmp(@PathVariable Long id) {
        log.debug("REST request to get PASaxCodeTmp : {}", id);
        PASaxCodeTmp pASaxCodeTmp = pASaxCodeTmpService.findOne(id);
        return Optional.ofNullable(pASaxCodeTmp)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-sax-code-tmps/:id : delete the "id" pASaxCodeTmp.
     *
     * @param id the id of the pASaxCodeTmp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-sax-code-tmps/{id}")
    @Timed
    public ResponseEntity<Void> deletePASaxCodeTmp(@PathVariable Long id) {
        log.debug("REST request to delete PASaxCodeTmp : {}", id);
        pASaxCodeTmpService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pASaxCodeTmp", id.toString())).build();
    }

}

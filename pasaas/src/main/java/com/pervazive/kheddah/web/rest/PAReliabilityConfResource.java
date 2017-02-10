package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAReliabilityConf;
import com.pervazive.kheddah.service.PAReliabilityConfService;
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
 * REST controller for managing PAReliabilityConf.
 */
@RestController
@RequestMapping("/api")
public class PAReliabilityConfResource {

    private final Logger log = LoggerFactory.getLogger(PAReliabilityConfResource.class);
        
    @Inject
    private PAReliabilityConfService pAReliabilityConfService;

    /**
     * POST  /p-a-reliability-confs : Create a new pAReliabilityConf.
     *
     * @param pAReliabilityConf the pAReliabilityConf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAReliabilityConf, or with status 400 (Bad Request) if the pAReliabilityConf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-reliability-confs")
    @Timed
    public ResponseEntity<PAReliabilityConf> createPAReliabilityConf(@RequestBody PAReliabilityConf pAReliabilityConf) throws URISyntaxException {
        log.debug("REST request to save PAReliabilityConf : {}", pAReliabilityConf);
        if (pAReliabilityConf.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAReliabilityConf", "idexists", "A new pAReliabilityConf cannot already have an ID")).body(null);
        }
        PAReliabilityConf result = pAReliabilityConfService.save(pAReliabilityConf);
        return ResponseEntity.created(new URI("/api/p-a-reliability-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAReliabilityConf", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-reliability-confs : Updates an existing pAReliabilityConf.
     *
     * @param pAReliabilityConf the pAReliabilityConf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAReliabilityConf,
     * or with status 400 (Bad Request) if the pAReliabilityConf is not valid,
     * or with status 500 (Internal Server Error) if the pAReliabilityConf couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-reliability-confs")
    @Timed
    public ResponseEntity<PAReliabilityConf> updatePAReliabilityConf(@RequestBody PAReliabilityConf pAReliabilityConf) throws URISyntaxException {
        log.debug("REST request to update PAReliabilityConf : {}", pAReliabilityConf);
        if (pAReliabilityConf.getId() == null) {
            return createPAReliabilityConf(pAReliabilityConf);
        }
        PAReliabilityConf result = pAReliabilityConfService.save(pAReliabilityConf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAReliabilityConf", pAReliabilityConf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-reliability-confs : get all the pAReliabilityConfs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAReliabilityConfs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-reliability-confs")
    @Timed
    public ResponseEntity<List<PAReliabilityConf>> getAllPAReliabilityConfs(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAReliabilityConfs");
        Page<PAReliabilityConf> page = pAReliabilityConfService.findAll(pageable, (List<PAOrganization>) request.getSession().getAttribute("organizationsess"));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-reliability-confs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-reliability-confs/:id : get the "id" pAReliabilityConf.
     *
     * @param id the id of the pAReliabilityConf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAReliabilityConf, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-reliability-confs/{id}")
    @Timed
    public ResponseEntity<PAReliabilityConf> getPAReliabilityConf(@PathVariable Long id) {
        log.debug("REST request to get PAReliabilityConf : {}", id);
        PAReliabilityConf pAReliabilityConf = pAReliabilityConfService.findOne(id);
        return Optional.ofNullable(pAReliabilityConf)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-reliability-confs/:id : delete the "id" pAReliabilityConf.
     *
     * @param id the id of the pAReliabilityConf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-reliability-confs/{id}")
    @Timed
    public ResponseEntity<Void> deletePAReliabilityConf(@PathVariable Long id) {
        log.debug("REST request to delete PAReliabilityConf : {}", id);
        pAReliabilityConfService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAReliabilityConf", id.toString())).build();
    }

}

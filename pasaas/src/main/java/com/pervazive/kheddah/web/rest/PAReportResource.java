package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAReliabilityConf;
import com.pervazive.kheddah.domain.PAReport;
import com.pervazive.kheddah.security.SecurityUtils;
import com.pervazive.kheddah.service.PAOrganizationService;
import com.pervazive.kheddah.service.PAReportService;
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
 * REST controller for managing PAReport.
 */
@RestController
@RequestMapping("/api")
public class PAReportResource {

    private final Logger log = LoggerFactory.getLogger(PAReportResource.class);
        
    @Inject
    private PAReportService pAReportService;
    
    @Inject
    private PAOrganizationService paOrganizationService;


    /**
     * POST  /p-a-reports : Create a new pAReport.
     *
     * @param pAReport the pAReport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAReport, or with status 400 (Bad Request) if the pAReport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-reports")
    @Timed
    public ResponseEntity<PAReport> createPAReport(@RequestBody PAReport pAReport) throws URISyntaxException {
        log.debug("REST request to save PAReport : {}", pAReport);
        if (pAReport.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAReport", "idexists", "A new pAReport cannot already have an ID")).body(null);
        }
        PAReport result = pAReportService.save(pAReport);
        return ResponseEntity.created(new URI("/api/p-a-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAReport", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-reports : Updates an existing pAReport.
     *
     * @param pAReport the pAReport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAReport,
     * or with status 400 (Bad Request) if the pAReport is not valid,
     * or with status 500 (Internal Server Error) if the pAReport couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-reports")
    @Timed
    public ResponseEntity<PAReport> updatePAReport(@RequestBody PAReport pAReport) throws URISyntaxException {
        log.debug("REST request to update PAReport : {}", pAReport);
        if (pAReport.getId() == null) {
            return createPAReport(pAReport);
        }
        PAReport result = pAReportService.save(pAReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAReport", pAReport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-reports : get all the pAReports.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAReports in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-reports")
    @Timed
    public ResponseEntity<List<PAReport>> getAllPAReports(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAReports");
        if(SecurityUtils.currentOrganization == null) 
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAReliabilityScore", "Organization missing", "Create one to proceed")).body(null);
        
        Page<PAReport> page = pAReportService.findAll(pageable, paOrganizationService.findOrganizationByName(SecurityUtils.currentOrganization));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-reports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-reports/:id : get the "id" pAReport.
     *
     * @param id the id of the pAReport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAReport, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-reports/{id}")
    @Timed
    public ResponseEntity<PAReport> getPAReport(@PathVariable Long id) {
        log.debug("REST request to get PAReport : {}", id);
        PAReport pAReport = pAReportService.findOne(id);
        return Optional.ofNullable(pAReport)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-reports/:id : delete the "id" pAReport.
     *
     * @param id the id of the pAReport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-reports/{id}")
    @Timed
    public ResponseEntity<Void> deletePAReport(@PathVariable Long id) {
        log.debug("REST request to delete PAReport : {}", id);
        pAReportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAReport", id.toString())).build();
    }

}

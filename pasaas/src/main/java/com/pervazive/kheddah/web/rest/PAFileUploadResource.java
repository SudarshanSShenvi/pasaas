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
import com.pervazive.kheddah.domain.PAFileUpload;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.repository.UserRepository;
import com.pervazive.kheddah.service.PAFileUploadService;
import com.pervazive.kheddah.service.PAOrganizationService;
import com.pervazive.kheddah.web.rest.util.HeaderUtil;
import com.pervazive.kheddah.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing PAFileUpload.
 */
@RestController
@RequestMapping("/api")
public class PAFileUploadResource {

    private final Logger log = LoggerFactory.getLogger(PAFileUploadResource.class);
        
    @Inject
    private PAFileUploadService pAFileUploadService;
  
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private PAOrganizationService paOrganizationService;

   
    /**
     * POST  /p-a-file-uploads : Create a new pAFileUpload.
     *
     * @param pAFileUpload the pAFileUpload to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAFileUpload, or with status 400 (Bad Request) if the pAFileUpload has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-file-uploads")
    @Timed
    public ResponseEntity<PAFileUpload> createPAFileUpload(@RequestBody PAFileUpload pAFileUpload) throws URISyntaxException {
        log.debug("REST request to save PAFileUpload : {}", pAFileUpload);
        if (pAFileUpload.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAFileUpload", "idexists", "A new pAFileUpload cannot already have an ID")).body(null);
        }
        PAFileUpload result = pAFileUploadService.save(pAFileUpload);
        return ResponseEntity.created(new URI("/api/p-a-file-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAFileUpload", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-file-uploads : Updates an existing pAFileUpload.
     *
     * @param pAFileUpload the pAFileUpload to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAFileUpload,
     * or with status 400 (Bad Request) if the pAFileUpload is not valid,
     * or with status 500 (Internal Server Error) if the pAFileUpload couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-file-uploads")
    @Timed
    public ResponseEntity<PAFileUpload> updatePAFileUpload(@RequestBody PAFileUpload pAFileUpload) throws URISyntaxException {
        log.debug("REST request to update PAFileUpload : {}", pAFileUpload);
        if (pAFileUpload.getId() == null) {
            return createPAFileUpload(pAFileUpload);
        }
        PAFileUpload result = pAFileUploadService.save(pAFileUpload);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAFileUpload", pAFileUpload.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-file-uploads : get all the pAFileUploads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAFileUploads in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-file-uploads")
    @Timed

    public ResponseEntity<List<PAFileUpload>> getAllPAFileUploads(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
             /*  if(CurrentOrganization.getCurrentOrganization() == null) 
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAFileUpload", "Organization missing", "Create one to proceed")).body(null);*/
        
        Page<PAFileUpload> page = pAFileUploadService.findAll(pageable, paOrganizationService.findOrganizationByName(((PAOrganization) request.getSession().getAttribute("s_organization")).getOrganization()));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-file-uploads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-file-uploads/:id : get the "id" pAFileUpload.
     *
     * @param id the id of the pAFileUpload to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAFileUpload, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-file-uploads/{id}")
    @Timed
    public ResponseEntity<PAFileUpload> getPAFileUpload(@PathVariable Long id) {
        log.debug("REST request to get PAFileUpload : {}", id);
        PAFileUpload pAFileUpload = pAFileUploadService.findOne(id);
        return Optional.ofNullable(pAFileUpload)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-file-uploads/:id : delete the "id" pAFileUpload.
     *
     * @param id the id of the pAFileUpload to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-file-uploads/{id}")
    @Timed
    public ResponseEntity<Void> deletePAFileUpload(@PathVariable Long id) {
        log.debug("REST request to delete PAFileUpload : {}", id);
        pAFileUploadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAFileUpload", id.toString())).build();
    }

}

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
import com.pervazive.kheddah.domain.PAErrorMessage;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.service.PAErrorMessageService;
import com.pervazive.kheddah.service.PAOrganizationService;
import com.pervazive.kheddah.web.rest.util.HeaderUtil;
import com.pervazive.kheddah.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing PAErrorMessage.
 */
@RestController
@RequestMapping("/api")
public class PAErrorMessageResource {

    private final Logger log = LoggerFactory.getLogger(PAErrorMessageResource.class);
        
    @Inject
    private PAErrorMessageService pAErrorMessageService;
    
    @Inject
    private PAOrganizationService paOrganizationService;


    /**
     * POST  /p-a-error-messages : Create a new pAErrorMessage.
     *
     * @param pAErrorMessage the pAErrorMessage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAErrorMessage, or with status 400 (Bad Request) if the pAErrorMessage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-error-messages")
    @Timed
    public ResponseEntity<PAErrorMessage> createPAErrorMessage(@RequestBody PAErrorMessage pAErrorMessage) throws URISyntaxException {
        log.debug("REST request to save PAErrorMessage : {}", pAErrorMessage);
        if (pAErrorMessage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAErrorMessage", "idexists", "A new pAErrorMessage cannot already have an ID")).body(null);
        }
        PAErrorMessage result = pAErrorMessageService.save(pAErrorMessage);
        return ResponseEntity.created(new URI("/api/p-a-error-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAErrorMessage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-error-messages : Updates an existing pAErrorMessage.
     *
     * @param pAErrorMessage the pAErrorMessage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAErrorMessage,
     * or with status 400 (Bad Request) if the pAErrorMessage is not valid,
     * or with status 500 (Internal Server Error) if the pAErrorMessage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-error-messages")
    @Timed
    public ResponseEntity<PAErrorMessage> updatePAErrorMessage(@RequestBody PAErrorMessage pAErrorMessage) throws URISyntaxException {
        log.debug("REST request to update PAErrorMessage : {}", pAErrorMessage);
        if (pAErrorMessage.getId() == null) {
            return createPAErrorMessage(pAErrorMessage);
        }
        PAErrorMessage result = pAErrorMessageService.save(pAErrorMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAErrorMessage", pAErrorMessage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-error-messages : get all the pAErrorMessages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAErrorMessages in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-error-messages")
    @Timed
    public ResponseEntity<List<PAErrorMessage>> getAllPAErrorMessages(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAErrorMessages");
        /*if(CurrentOrganization.getCurrentOrganization() == null) 
        	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAErrorMessage", "Organization missing", "Create one to proceed")).body(null);*/
        
        Page<PAErrorMessage> page = pAErrorMessageService.findAll(pageable, paOrganizationService.findOrganizationByName(((PAOrganization) request.getSession().getAttribute("s_organization")).getOrganization()));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-error-messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-error-messages/:id : get the "id" pAErrorMessage.
     *
     * @param id the id of the pAErrorMessage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAErrorMessage, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-error-messages/{id}")
    @Timed
    public ResponseEntity<PAErrorMessage> getPAErrorMessage(@PathVariable Long id) {
        log.debug("REST request to get PAErrorMessage : {}", id);
        PAErrorMessage pAErrorMessage = pAErrorMessageService.findOne(id);
        return Optional.ofNullable(pAErrorMessage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-error-messages/:id : delete the "id" pAErrorMessage.
     *
     * @param id the id of the pAErrorMessage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-error-messages/{id}")
    @Timed
    public ResponseEntity<Void> deletePAErrorMessage(@PathVariable Long id) {
        log.debug("REST request to delete PAErrorMessage : {}", id);
        pAErrorMessageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAErrorMessage", id.toString())).build();
    }

}

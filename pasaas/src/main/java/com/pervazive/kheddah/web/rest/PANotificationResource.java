package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PANotification;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.service.PANotificationService;
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
 * REST controller for managing PANotification.
 */
@RestController
@RequestMapping("/api")
public class PANotificationResource {

    private final Logger log = LoggerFactory.getLogger(PANotificationResource.class);
        
    @Inject
    private PANotificationService pANotificationService;

    /**
     * POST  /p-a-notifications : Create a new pANotification.
     *
     * @param pANotification the pANotification to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pANotification, or with status 400 (Bad Request) if the pANotification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-notifications")
    @Timed
    public ResponseEntity<PANotification> createPANotification(@RequestBody PANotification pANotification) throws URISyntaxException {
        log.debug("REST request to save PANotification : {}", pANotification);
        if (pANotification.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pANotification", "idexists", "A new pANotification cannot already have an ID")).body(null);
        }
        PANotification result = pANotificationService.save(pANotification);
        return ResponseEntity.created(new URI("/api/p-a-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pANotification", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-notifications : Updates an existing pANotification.
     *
     * @param pANotification the pANotification to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pANotification,
     * or with status 400 (Bad Request) if the pANotification is not valid,
     * or with status 500 (Internal Server Error) if the pANotification couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-notifications")
    @Timed
    public ResponseEntity<PANotification> updatePANotification(@RequestBody PANotification pANotification) throws URISyntaxException {
        log.debug("REST request to update PANotification : {}", pANotification);
        if (pANotification.getId() == null) {
            return createPANotification(pANotification);
        }
        PANotification result = pANotificationService.save(pANotification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pANotification", pANotification.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-notifications : get all the pANotifications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pANotifications in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-notifications")
    @Timed
    public ResponseEntity<List<PANotification>> getAllPANotifications(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PANotifications");
        Page<PANotification> page = pANotificationService.findAll(pageable, (List<PAOrganization>) request.getSession().getAttribute("organizationsess"));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-notifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-notifications/:id : get the "id" pANotification.
     *
     * @param id the id of the pANotification to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pANotification, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-notifications/{id}")
    @Timed
    public ResponseEntity<PANotification> getPANotification(@PathVariable Long id) {
        log.debug("REST request to get PANotification : {}", id);
        PANotification pANotification = pANotificationService.findOne(id);
        return Optional.ofNullable(pANotification)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-notifications/:id : delete the "id" pANotification.
     *
     * @param id the id of the pANotification to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-notifications/{id}")
    @Timed
    public ResponseEntity<Void> deletePANotification(@PathVariable Long id) {
        log.debug("REST request to delete PANotification : {}", id);
        pANotificationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pANotification", id.toString())).build();
    }

}

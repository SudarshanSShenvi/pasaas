package com.pervazive.kheddah.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.service.PAProjectService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PAProject.
 */
@RestController
@RequestMapping("/api")
public class PAProjectResource {

    private final Logger log = LoggerFactory.getLogger(PAProjectResource.class);
        
    @Inject
    private PAProjectService pAProjectService;

    /**
     * POST  /p-a-projects : Create a new pAProject.
     *
     * @param pAProject the pAProject to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAProject, or with status 400 (Bad Request) if the pAProject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/p-a-projects")
    @Timed
    public ResponseEntity<PAProject> createPAProject(@RequestBody PAProject pAProject) throws URISyntaxException {
        log.debug("REST request to save PAProject : {}", pAProject);
        if (pAProject.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAProject", "idexists", "A new pAProject cannot already have an ID")).body(null);
        }
        PAProject result = pAProjectService.save(pAProject);
        return ResponseEntity.created(new URI("/api/p-a-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAProject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /p-a-projects : Updates an existing pAProject.
     *
     * @param pAProject the pAProject to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAProject,
     * or with status 400 (Bad Request) if the pAProject is not valid,
     * or with status 500 (Internal Server Error) if the pAProject couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-projects")
    @Timed
    public ResponseEntity<PAProject> updatePAProject(@RequestBody PAProject pAProject) throws URISyntaxException {
        log.debug("REST request to update PAProject : {}", pAProject);
        if (pAProject.getId() == null) {
            return createPAProject(pAProject);
        }
        PAProject result = pAProjectService.save(pAProject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAProject", pAProject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /p-a-projects : get all the pAProjects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAProjects in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-projects")
    @Timed
    public ResponseEntity<List<PAProject>> getAllPAProjects(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAProjects");
        Page<PAProject> page = pAProjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /p-a-projects/:id : get the "id" pAProject.
     *
     * @param id the id of the pAProject to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAProject, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-projects/{id}")
    @Timed
    public ResponseEntity<PAProject> getPAProject(@PathVariable Long id) {
        log.debug("REST request to get PAProject : {}", id);
        PAProject pAProject = pAProjectService.findOne(id);
        return Optional.ofNullable(pAProject)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-projects/:id : delete the "id" pAProject.
     *
     * @param id the id of the pAProject to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/p-a-projects/{id}")
    @Timed
    public ResponseEntity<Void> deletePAProject(@PathVariable Long id) {
        log.debug("REST request to delete PAProject : {}", id);
        pAProjectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAProject", id.toString())).build();
    }

}

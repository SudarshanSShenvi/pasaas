package com.pervazive.kheddah.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.Issues;
import com.pervazive.kheddah.repository.IssuesRepository;
import com.pervazive.kheddah.service.dto.IssueDTO;
import com.pervazive.kheddah.web.rest.util.HeaderUtil;
import com.pervazive.kheddah.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Issues.
 */
@RestController
@RequestMapping("/api")
public class IssuesResource {

    private final Logger log = LoggerFactory.getLogger(IssuesResource.class);
        
    @Inject
    private IssuesRepository issuesRepository;
    
    /**
     * POST  /issues : Create a new issues.
     *
     * @param issues the issues to create
     * @return the ResponseEntity with status 201 (Created) and with body the new issues, or with status 400 (Bad Request) if the issues has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/issues",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Issues> createIssues(@RequestBody Issues issues) throws URISyntaxException {
        log.debug("REST request to save Issues : {}", issues);
        if (issues.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("issues", "idexists", "A new issues cannot already have an ID")).body(null);
        }
        Issues result = issuesRepository.save(issues);
        return ResponseEntity.created(new URI("/api/issues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("issues", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /issues : Updates an existing issues.
     *
     * @param issues the issues to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated issues,
     * or with status 400 (Bad Request) if the issues is not valid,
     * or with status 500 (Internal Server Error) if the issues couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/issues",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Issues> updateIssues(@RequestBody Issues issues) throws URISyntaxException {
        log.debug("REST request to update Issues : {}", issues);
        if (issues.getId() == null) {
            return createIssues(issues);
        }
        Issues result = issuesRepository.save(issues);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("issues", issues.getId().toString()))
            .body(result);
    }

    /**
     * GET  /issues : get all the issues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of issues in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/issues",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IssueDTO>> getAllIssues(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Issues");
        Page<Issues> page = issuesRepository.findAll(pageable); 
        
        List<IssueDTO> issueDto = page.getContent().stream().map(IssueDTO::new)
        		 .collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/issues");
        return new ResponseEntity<>(issueDto, headers, HttpStatus.OK);
    }

    /**
     * GET  /issues/:id : get the "id" issues.
     *
     * @param id the id of the issues to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the issues, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/issues/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IssueDTO> getIssues(@PathVariable Long id) {
        log.debug("REST request to get Issues : {}", id);
        Issues issues = issuesRepository.findOne(id);
        
        IssueDTO issueDTO = new IssueDTO(issues);
        return Optional.ofNullable(issueDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /issues/:id : delete the "id" issues.
     *
     * @param id the id of the issues to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/issues/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIssues(@PathVariable Long id) {
        log.debug("REST request to delete Issues : {}", id);
        issuesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("issues", id.toString())).build();
    }

}

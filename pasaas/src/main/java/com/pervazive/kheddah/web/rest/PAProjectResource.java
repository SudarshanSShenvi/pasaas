package com.pervazive.kheddah.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.repository.UserRepository;
import com.pervazive.kheddah.security.SecurityUtils;
import com.pervazive.kheddah.service.PAProjectService;
import com.pervazive.kheddah.service.dto.PAOrganizationDTO;
import com.pervazive.kheddah.service.dto.PAProjectDTO;
import com.pervazive.kheddah.web.rest.util.HeaderUtil;
import com.pervazive.kheddah.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing PAProject.
 */
@RestController
@RequestMapping("/api")
public class PAProjectResource {

    private final Logger log = LoggerFactory.getLogger(PAProjectResource.class);
        
    @Inject
    private PAProjectService pAProjectService;
    
    @Inject
    private UserRepository userRepository;

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
        if (pAProject.getProjectname() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAProject", "NotNull", "Field Project Name cannot be empty!")).body(null);
        }
        if (pAProject.getPaorgpro() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAProject", "NotNull", "Field Organization cannot be empty!")).body(null);
        }
        
        Set<User> pausers = new HashSet<User>();
        User currentUser = userRepository.findOneByLoginName(SecurityUtils.getCurrentUserLogin());
        pausers.add(currentUser);
        pAProject.setPausers(pausers);
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
    public ResponseEntity<PAProjectDTO> updatePAProject(@RequestBody PAProjectDTO pAProject) throws URISyntaxException {
        log.debug("REST request to update PAProject : {}", pAProject);
        /*if (pAProject.getId() == null) {
            return createPAProject(pAProject);
        }*/
        /*PAProject result = pAProjectService.save(pAProject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAProject", pAProject.getId().toString()))
            .body(new PAProjectDTO(result));*/
        
        pAProjectService.updateProjectwithUsers(pAProject.getId(), pAProject.getProjectname(), pAProject.getDescription(), 
        		pAProject.getPaorgpro(), pAProject.getPausers());
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert("pAProject.updated", pAProject.getProjectname()))
            .body(new PAProjectDTO(pAProjectService.getProjectWithUser(pAProject.getId())));

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
    public ResponseEntity<List<PAProjectDTO>> getAllPAProjects(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAProjects");
        
        Page<PAProject> page = pAProjectService.findAll(pageable, (List<PAOrganization>) request.getSession().getAttribute("organizationsess"));
        List<PAProjectDTO> paProjectDTO = page.getContent().stream()
            .map(PAProjectDTO::new)
            .collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-projects");
        return new ResponseEntity<>(paProjectDTO, headers, HttpStatus.OK);
        
        //ORIGINAL IMPLEMENTATION
        /*Page<PAProject> page = pAProjectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);*/
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

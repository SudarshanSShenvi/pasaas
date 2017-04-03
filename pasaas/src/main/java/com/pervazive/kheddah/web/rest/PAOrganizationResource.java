package com.pervazive.kheddah.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.repository.UserRepository;
import com.pervazive.kheddah.security.AuthoritiesConstants;
import com.pervazive.kheddah.security.SecurityUtils;
import com.pervazive.kheddah.service.HDFSFileOperationsService;
import com.pervazive.kheddah.service.PAOrganizationService;
import com.pervazive.kheddah.service.dto.PAOrganizationDTO;
import com.pervazive.kheddah.web.rest.util.HeaderUtil;
import com.pervazive.kheddah.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing PAOrganization.
 */
@RestController
@RequestMapping("/api")
public class PAOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(PAOrganizationResource.class);
        
    @Inject
    private PAOrganizationService pAOrganizationService;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private HDFSFileOperationsService hdfsFileOperationsService;

    /**
     * POST  /p-a-organizations : Create a new pAOrganization.
     *
     * @param pAOrganization the pAOrganization to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pAOrganization, or with status 400 (Bad Request) if the pAOrganization has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @throws IOException 
     */
    @PostMapping("/p-a-organizations")
    @Timed
    public ResponseEntity<PAOrganizationDTO> createPAOrganization(@RequestBody PAOrganization pAOrganization) throws URISyntaxException, IOException {
        log.debug("REST request to save PAOrganization : {}", pAOrganization);
        if (pAOrganization.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAOrganization", "idexists", "A new pAOrganization cannot already have an ID")).body(null);
        }
        if (pAOrganization.getOrganization() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pAOrganization", "NotNull", "Field Organization cannot be empty!")).body(null);
        }
        
        //Add Current User to the organization
        Set<User> pausers = new HashSet<User>();
        User currentUser = userRepository.findOneByLoginName(SecurityUtils.getCurrentUserLogin());
        pausers.add(currentUser);
        pAOrganization.setPAUsers(pausers);
        PAOrganization result = pAOrganizationService.save(pAOrganization);
        
        PAOrganizationDTO paOrganizationDTO = new PAOrganizationDTO(result);
        
        if(currentUser.getDefaultOrganization() == null ) {
        	currentUser.setDefaultOrganization(pAOrganization.getOrganization());
        	userRepository.save(currentUser);
        }
        //Create Organization Directory
        hdfsFileOperationsService.mkdirOrgStructure(paOrganizationDTO.getOrganization());
        
        return ResponseEntity.created(new URI("/api/p-a-organizations/" + paOrganizationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pAOrganization", paOrganizationDTO.getId().toString()))
            .body(paOrganizationDTO);
    }

    /**
     * PUT  /p-a-organizations : Updates an existing pAOrganization.
     *
     * @param pAOrganization the pAOrganization to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pAOrganization,
     * or with status 400 (Bad Request) if the pAOrganization is not valid,
     * or with status 500 (Internal Server Error) if the pAOrganization couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/p-a-organizations")
    @Timed
    public ResponseEntity<PAOrganizationDTO> updatePAOrganization(@RequestBody PAOrganizationDTO pAOrganization) throws URISyntaxException {
        log.debug("REST request to update PAOrganizationDTO : {}", pAOrganization);
        /*Iterator<String> userList = pAOrganization.getPausers().iterator();
        while(userList.hasNext()) {
        	 log.debug("**** CHECK THIS ***** "+userList.toString());
        }*/
        /*if (pAOrganization.getId() == null) {
            return createPAOrganization(pAOrganizationDTO);
        }*/
       /* PAOrganization result = pAOrganizationService.save(pAOrganization);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pAOrganization", pAOrganization.getId().toString()))
            .body(result);*/
        
        pAOrganizationService.updateOrganizationwithUsers(pAOrganization.getId(), pAOrganization.getOrganization(), pAOrganization.getValidfrom(), 
        		pAOrganization.getValidto(), pAOrganization.getPastatus(), pAOrganization.getPabporg(), pAOrganization.getPausers(), 
        		pAOrganization.getIndustrytype(), pAOrganization.getWebsite());

        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert("pAOrganization.updated", pAOrganization.getOrganization()))
            .body(new PAOrganizationDTO(pAOrganizationService.getOrganizationWithUser(pAOrganization.getId())));
    }

    /**
     * GET  /p-a-organizations : get all the pAOrganizations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pAOrganizations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/p-a-organizations/suops")
    @Timed
    @Secured(AuthoritiesConstants.SUPERADMIN)
    public ResponseEntity<List<PAOrganizationDTO>> getAllPAOrganizations(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAOrganizations");
        
        Page<PAOrganization> page = pAOrganizationService.findAll(pageable);
        
        List<PAOrganizationDTO> paOrganization = page.getContent().stream()
            .map(PAOrganizationDTO::new)
            .collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-organizations");
        return new ResponseEntity<>(paOrganization, headers, HttpStatus.OK);
        
        //ORIGINAL Implementation
        //Page<PAOrganization> page = pAOrganizationService.findAll(pageable);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-organizations");
        //return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/p-a-organizations")
    @Timed
    //@Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<PAOrganizationDTO>> getAllPAOrganizations(@ApiParam Pageable pageable, HttpServletRequest request)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAOrganizations");
        List<PAOrganization> listOrganization = new ArrayList<PAOrganization>();
        listOrganization.add((PAOrganization) request.getSession().getAttribute("s_organization"));
        Page<PAOrganization> page = new PageImpl<>(listOrganization);
        		
        if (page.getContent().isEmpty()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("paorganization", "emptyorganization", "No Organizations Associated. Please contact admin to include you as part of an Organization"))
                .body(null);
        } 
        List<PAOrganizationDTO> paOrganization = page.getContent().stream()
            .map(PAOrganizationDTO::new)
            .collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-organizations");
        return new ResponseEntity<>(paOrganization, headers, HttpStatus.OK);
        
       
    }
    
    @GetMapping("/p-a-organizations/u/{user}")
    @Timed
    //@Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<PAOrganizationDTO>> getAllPAOrganizationsForUser(@ApiParam Pageable pageable, @PathVariable String user)
        throws URISyntaxException {
        log.debug("REST request to get a page of PAOrganizations");
        
        Page<PAOrganization> page = pAOrganizationService.findAll(pageable, user);
        		
        if (page.getContent().isEmpty()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("paorganization", "emptyorganization", "No Organizations Associated. Please contact admin to include you as part of an Organization"))
                .body(null);
        } 
        List<PAOrganizationDTO> paOrganization = page.getContent().stream()
            .map(PAOrganizationDTO::new)
            .collect(Collectors.toList());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/p-a-organizations-u");
        return new ResponseEntity<>(paOrganization, headers, HttpStatus.OK);
        
       
    }

    /**
     * GET  /p-a-organizations/:id : get the "id" pAOrganization.
     *
     * @param id the id of the pAOrganization to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAOrganization, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-organizations/{id}")
    @Timed
    public ResponseEntity<PAOrganizationDTO> getPAOrganization(@PathVariable Long id) {
        log.debug("REST request to get PAOrganization : {}", id);
        PAOrganization pAOrganization = pAOrganizationService.findOne(id);
PAOrganizationDTO paOrganizationDTO = new PAOrganizationDTO(pAOrganization);
        
        return Optional.ofNullable(paOrganizationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    /**
     * GET  /p-a-organizations/:id : get the "id" pAOrganization.
     *
     * @param id the id of the pAOrganization to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pAOrganization, or with status 404 (Not Found)
     */
    @GetMapping("/p-a-organizations/name/{organizationName}")
    @Timed
    public ResponseEntity<PAOrganizationDTO> getPAOrganizationByName(@PathVariable String organizationName) {
        log.debug("REST request to get PAOrganization : {}", organizationName);
        PAOrganization pAOrganization = pAOrganizationService.findOrganizationByName(organizationName);
        PAOrganizationDTO paOrganizationDTO = new PAOrganizationDTO(pAOrganization);
        
        return Optional.ofNullable(paOrganizationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /p-a-organizations/:id : delete the "id" pAOrganization.
     *
     * @param id the id of the pAOrganization to delete
     * @return the ResponseEntity with status 200 (OK)
     * @throws IOException 
     */
    @DeleteMapping("/p-a-organizations/{id}")
    @Timed
    public ResponseEntity<Void> deletePAOrganization(@PathVariable Long id) throws IOException {
        log.debug("REST request to delete PAOrganization : {}", id);
      //Create Organization Directory
        hdfsFileOperationsService.deleteOrgStructure(pAOrganizationService.getOrganizationWithUser(id).getOrganization());
        pAOrganizationService.delete(id);
        
        
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pAOrganization", id.toString())).build();
    }

}

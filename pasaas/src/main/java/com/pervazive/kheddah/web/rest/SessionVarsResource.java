package com.pervazive.kheddah.web.rest;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.security.SecurityUtils;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class SessionVarsResource {

    private final Logger log = LoggerFactory.getLogger(SessionVarsResource.class);

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    
    /**
     * POST  /sessionVars : update Organization
     *
     * @return nothing
     * TODO: return Updated organization with message; verify if user belongs to the requested group
     */
    @PostMapping("/sessionVars/{newOrganization}")
    @Timed
    public void setSessionOrganization(@PathVariable String newOrganization, HttpSession session) {
    	
    	log.debug("Current Organization "+session.getAttribute("s_organization"));
    	log.debug("request to change Organization to "+newOrganization);
    	log.debug("session ID"+session.getId());
    	session.setAttribute("s_organization", paOrganizationRepository.findByOrganization(newOrganization));
    	log.debug("RESETTING VALUES HERE "+session.getAttribute("s_organization"));
    	
    	/*log.debug("Current Organization "+CurrentOrganization.getCurrentOrganization()); 
    	log.debug("request to change Organization to "+newOrganization);
    	//SecurityUtils.currentOrganization = newOrganization;
    	CurrentOrganization.setCurrentOrganization(newOrganization); 
    	log.debug("RESETTING VALUES HERE "+CurrentOrganization.getCurrentOrganization());*/
    	log.debug("for user :"+SecurityUtils.getCurrentUserLogin());
            
    }
    
}

package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAOrganizationService;
import com.pervazive.kheddah.web.rest.util.PaginationUtil;
import com.pervazive.kheddah.web.rest.vm.ManagedUserVM;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PAOrganization.
 */
@Service
@Transactional
public class PAOrganizationServiceImpl implements PAOrganizationService{

    private final Logger log = LoggerFactory.getLogger(PAOrganizationServiceImpl.class);
    
    @Inject
    private PAOrganizationRepository pAOrganizationRepository;

    /**
     * Save a pAOrganization.
     *
     * @param pAOrganization the entity to save
     * @return the persisted entity
     */
    public PAOrganization save(PAOrganization pAOrganization) {
        log.debug("Request to save PAOrganization : {}", pAOrganization);
        PAOrganization result = pAOrganizationRepository.save(pAOrganization);
        return result;
    }

    /**
     *  Get all the pAOrganizations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAOrganization> findAll(Pageable pageable) {
        log.debug("Request to get all PAOrganizations");
        Page<PAOrganization> result = pAOrganizationRepository.findAll(pageable);
        //Page<PAOrganization> result = pAOrganizationRepository.findAllPAUsers(pageable);
        return result;
    }

    /**
     *  Get one pAOrganization by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAOrganization findOne(Long id) {
        log.debug("Request to get PAOrganization : {}", id);
        PAOrganization pAOrganization = pAOrganizationRepository.findOne(id);
        return pAOrganization;
    }

    /**
     *  Delete the  pAOrganization by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAOrganization : {}", id);
        pAOrganizationRepository.delete(id);
    }
    
    /*@Transactional(readOnly = true)
    public List<PAOrganization> getOrgsWithUsers(Pageable pageable) {
    	Page<PAOrganization> page = pAOrganizationRepository.findAllPAUsers(pageable);
        List<PAOrganization> paOrganization = page.getContent().stream()
            .map(PAOrganization::new)
            .collect(Collectors.toList());
        return paOrganization;
    }*/
}

package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAPMTRequestService;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAPMTRequest;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.repository.PAPMTRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PAPMTRequest.
 */
@Service
@Transactional
public class PAPMTRequestServiceImpl implements PAPMTRequestService{

    private final Logger log = LoggerFactory.getLogger(PAPMTRequestServiceImpl.class);
    
    @Inject
    private PAPMTRequestRepository pAPMTRequestRepository;

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pAPMTRequest.
     *
     * @param pAPMTRequest the entity to save
     * @return the persisted entity
     */
    public PAPMTRequest save(PAPMTRequest pAPMTRequest) {
        log.debug("Request to save PAPMTRequest : {}", pAPMTRequest);
        PAPMTRequest result = pAPMTRequestRepository.save(pAPMTRequest);
        return result;
    }

    /**
     *  Get all the pAPMTRequests.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAPMTRequest> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PAPMTRequests");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PAPMTRequest> result = pAPMTRequestRepository.findByPaorgpmtIn(organizationames, pageable);
        //Page<PAPMTRequest> result = pAPMTRequestRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get all the pAPMTRequests.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAPMTRequest> findAll(Pageable pageable, List<PAOrganization> organizationames ) {
        log.debug("Request to get all PAPMTRequests");
        Page<PAPMTRequest> result = pAPMTRequestRepository.findByPaorgpmtIn(organizationames, pageable);
        //Page<PAPMTRequest> result = pAPMTRequestRepository.findAll(pageable);
        return result;
    }
    
    /**
     *  Get one pAPMTRequest by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAPMTRequest findOne(Long id) {
        log.debug("Request to get PAPMTRequest : {}", id);
        PAPMTRequest pAPMTRequest = pAPMTRequestRepository.findOne(id);
        return pAPMTRequest;
    }

    /**
     *  Delete the  pAPMTRequest by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAPMTRequest : {}", id);
        pAPMTRequestRepository.delete(id);
    }
}

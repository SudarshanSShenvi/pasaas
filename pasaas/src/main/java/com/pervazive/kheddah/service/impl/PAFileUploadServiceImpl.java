package com.pervazive.kheddah.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pervazive.kheddah.domain.PAFileUpload;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.repository.PAFileUploadRepository;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.service.PAFileUploadService;

/**
 * Service Implementation for managing PAFileUpload.
 */
@Service
@Transactional
public class PAFileUploadServiceImpl implements PAFileUploadService{

    private final Logger log = LoggerFactory.getLogger(PAFileUploadServiceImpl.class);
    
    @Inject
    private PAFileUploadRepository pAFileUploadRepository;
    
    @Inject
    private PAOrganizationRepository paOrganizationRepository;

    /**
     * Save a pAFileUpload.
     *
     * @param pAFileUpload the entity to save
     * @return the persisted entity
     */
    public PAFileUpload save(PAFileUpload pAFileUpload) {
        log.debug("Request to save PAFileUpload : {}", pAFileUpload);
        PAFileUpload result = pAFileUploadRepository.save(pAFileUpload);
        return result;
    }

    /**
     *  Get all the pAFileUploads.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAFileUpload> findAll(Pageable pageable, String pausers) {
    	
    	List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
    	for (int i = 0; i < organizationames.size(); i++) {
    		 log.debug("Request to get all PAFileUploads ****"+organizationames.get(i).getOrganization());
		}
    	// log.debug("Request to get all PAFileUploads ****"+paOrganization.getOrganizations());
        //Page<PAFileUpload> result = pAFileUploadRepository.findAll(pageable);
        //Page<PAFileUpload> result = pAFileUploadRepository.findByPaorgfu(pageable, organizationames.get(0).getId());
        
        Page<PAFileUpload> result = pAFileUploadRepository.findByPaorgfuIn(organizationames, pageable);
        return result;
    }
    
    @Transactional(readOnly = true) 
    public Page<PAFileUpload> findAll(Pageable pageable, PAOrganization paOrganization) {
    	
    	//List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
    	/*for (int i = 0; i < paOrganizations.size(); i++) {
    		 log.debug("Request to get all PAFileUploads ****"+paOrganizations.get(i).getOrganization().toString());
		}*/
    	// log.debug("Request to get all PAFileUploads ****"+paOrganization.getOrganizations());
        //Page<PAFileUpload> result = pAFileUploadRepository.findAll(pageable);
        //Page<PAFileUpload> result = pAFileUploadRepository.findByPaorgfu(pageable, organizationames.get(0).getId());
        
        Page<PAFileUpload> result = pAFileUploadRepository.findByPaorgfu(paOrganization, pageable);
        return result;
    }
    


    /**
     *  Get one pAFileUpload by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAFileUpload findOne(Long id) {
        log.debug("Request to get PAFileUpload : {}", id);
        PAFileUpload pAFileUpload = pAFileUploadRepository.findOne(id);
        return pAFileUpload;
    }

    /**
     *  Delete the  pAFileUpload by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAFileUpload : {}", id);
        pAFileUploadRepository.delete(id);
    }
}

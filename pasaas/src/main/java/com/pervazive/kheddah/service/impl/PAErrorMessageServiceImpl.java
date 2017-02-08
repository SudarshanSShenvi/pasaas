package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAErrorMessageService;
import com.pervazive.kheddah.domain.PAErrorMessage;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.repository.PAErrorMessageRepository;
import com.pervazive.kheddah.repository.PAOrganizationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PAErrorMessage.
 */
@Service
@Transactional
public class PAErrorMessageServiceImpl implements PAErrorMessageService{

    private final Logger log = LoggerFactory.getLogger(PAErrorMessageServiceImpl.class);
    
    @Inject
    private PAErrorMessageRepository pAErrorMessageRepository;

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pAErrorMessage.
     *
     * @param pAErrorMessage the entity to save
     * @return the persisted entity
     */
    public PAErrorMessage save(PAErrorMessage pAErrorMessage) {
        log.debug("Request to save PAErrorMessage : {}", pAErrorMessage);
        PAErrorMessage result = pAErrorMessageRepository.save(pAErrorMessage);
        return result;
    }

    /**
     *  Get all the pAErrorMessages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAErrorMessage> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PAErrorMessages");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PAErrorMessage> result = pAErrorMessageRepository.findByPaorgem(organizationames, pageable);
        //Page<PAErrorMessage> result = pAErrorMessageRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pAErrorMessage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAErrorMessage findOne(Long id) {
        log.debug("Request to get PAErrorMessage : {}", id);
        PAErrorMessage pAErrorMessage = pAErrorMessageRepository.findOne(id);
        return pAErrorMessage;
    }

    /**
     *  Delete the  pAErrorMessage by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAErrorMessage : {}", id);
        pAErrorMessageRepository.delete(id);
    }
}

package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAAccPrecisionService;
import com.pervazive.kheddah.domain.PAAccPrecision;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.repository.PAAccPrecisionRepository;
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
 * Service Implementation for managing PAAccPrecision.
 */
@Service
@Transactional
public class PAAccPrecisionServiceImpl implements PAAccPrecisionService{

    private final Logger log = LoggerFactory.getLogger(PAAccPrecisionServiceImpl.class);
    
    @Inject
    private PAAccPrecisionRepository pAAccPrecisionRepository;

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pAAccPrecision.
     *
     * @param pAAccPrecision the entity to save
     * @return the persisted entity
     */
    public PAAccPrecision save(PAAccPrecision pAAccPrecision) {
        log.debug("Request to save PAAccPrecision : {}", pAAccPrecision);
        PAAccPrecision result = pAAccPrecisionRepository.save(pAAccPrecision);
        return result;
    }

    /**
     *  Get all the pAAccPrecisions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAAccPrecision> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PAAccPrecisions");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PAAccPrecision> result = pAAccPrecisionRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pAAccPrecision by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAAccPrecision findOne(Long id) {
        log.debug("Request to get PAAccPrecision : {}", id);
        PAAccPrecision pAAccPrecision = pAAccPrecisionRepository.findOne(id);
        return pAAccPrecision;
    }

    /**
     *  Delete the  pAAccPrecision by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAAccPrecision : {}", id);
        pAAccPrecisionRepository.delete(id);
    }
}

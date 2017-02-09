package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAPredictionService;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAPrediction;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.repository.PAPredictionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PAPrediction.
 */
@Service
@Transactional
public class PAPredictionServiceImpl implements PAPredictionService{

    private final Logger log = LoggerFactory.getLogger(PAPredictionServiceImpl.class);
    
    @Inject
    private PAPredictionRepository pAPredictionRepository;

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pAPrediction.
     *
     * @param pAPrediction the entity to save
     * @return the persisted entity
     */
    public PAPrediction save(PAPrediction pAPrediction) {
        log.debug("Request to save PAPrediction : {}", pAPrediction);
        PAPrediction result = pAPredictionRepository.save(pAPrediction);
        return result;
    }

    /**
     *  Get all the pAPredictions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAPrediction> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PAPredictions");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PAPrediction> result = pAPredictionRepository.findByPaorgpreIn(organizationames, pageable);
        return result;
    }
    /**
     *  Get all the pAPredictions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAPrediction> findAll(Pageable pageable, List<PAOrganization> organizationames) {
        log.debug("Request to get all PAPredictions");
        Page<PAPrediction> result = pAPredictionRepository.findByPaorgpreIn(organizationames, pageable);
        return result;
    }
    /**
     *  Get one pAPrediction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAPrediction findOne(Long id) {
        log.debug("Request to get PAPrediction : {}", id);
        PAPrediction pAPrediction = pAPredictionRepository.findOne(id);
        return pAPrediction;
    }

    /**
     *  Delete the  pAPrediction by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAPrediction : {}", id);
        pAPredictionRepository.delete(id);
    }
}

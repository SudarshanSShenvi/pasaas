package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PASchedulerIntervalService;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PASchedulerInterval;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.repository.PASchedulerIntervalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PASchedulerInterval.
 */
@Service
@Transactional
public class PASchedulerIntervalServiceImpl implements PASchedulerIntervalService{

    private final Logger log = LoggerFactory.getLogger(PASchedulerIntervalServiceImpl.class);
    
    @Inject
    private PASchedulerIntervalRepository pASchedulerIntervalRepository;

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pASchedulerInterval.
     *
     * @param pASchedulerInterval the entity to save
     * @return the persisted entity
     */
    public PASchedulerInterval save(PASchedulerInterval pASchedulerInterval) {
        log.debug("Request to save PASchedulerInterval : {}", pASchedulerInterval);
        PASchedulerInterval result = pASchedulerIntervalRepository.save(pASchedulerInterval);
        return result;
    }

    /**
     *  Get all the pASchedulerIntervals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PASchedulerInterval> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PASchedulerIntervals");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PASchedulerInterval> result = pASchedulerIntervalRepository.findByPaorgsciIn(organizationames, pageable);
        //Page<PASchedulerInterval> result = pASchedulerIntervalRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pASchedulerInterval by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PASchedulerInterval findOne(Long id) {
        log.debug("Request to get PASchedulerInterval : {}", id);
        PASchedulerInterval pASchedulerInterval = pASchedulerIntervalRepository.findOne(id);
        return pASchedulerInterval;
    }

    /**
     *  Delete the  pASchedulerInterval by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PASchedulerInterval : {}", id);
        pASchedulerIntervalRepository.delete(id);
    }
}

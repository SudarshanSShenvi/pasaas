package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PASchedulerService;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAScheduler;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.repository.PASchedulerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PAScheduler.
 */
@Service
@Transactional
public class PASchedulerServiceImpl implements PASchedulerService{

    private final Logger log = LoggerFactory.getLogger(PASchedulerServiceImpl.class);
    
    @Inject
    private PASchedulerRepository pASchedulerRepository;

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pAScheduler.
     *
     * @param pAScheduler the entity to save
     * @return the persisted entity
     */
    public PAScheduler save(PAScheduler pAScheduler) {
        log.debug("Request to save PAScheduler : {}", pAScheduler);
        PAScheduler result = pASchedulerRepository.save(pAScheduler);
        return result;
    }

    /**
     *  Get all the pASchedulers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAScheduler> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PASchedulers");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PAScheduler> result = pASchedulerRepository.findByPaorgschIn(organizationames, pageable);
        return result;
    }

    /**
     *  Get one pAScheduler by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAScheduler findOne(Long id) {
        log.debug("Request to get PAScheduler : {}", id);
        PAScheduler pAScheduler = pASchedulerRepository.findOne(id);
        return pAScheduler;
    }

    /**
     *  Delete the  pAScheduler by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAScheduler : {}", id);
        pASchedulerRepository.delete(id);
    }
}

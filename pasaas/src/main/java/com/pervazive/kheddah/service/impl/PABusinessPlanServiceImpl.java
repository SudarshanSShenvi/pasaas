package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PABusinessPlanService;
import com.pervazive.kheddah.domain.PABusinessPlan;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.repository.PABusinessPlanRepository;
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
 * Service Implementation for managing PABusinessPlan.
 */
@Service
@Transactional
public class PABusinessPlanServiceImpl implements PABusinessPlanService{

    private final Logger log = LoggerFactory.getLogger(PABusinessPlanServiceImpl.class);
    
    @Inject
    private PABusinessPlanRepository pABusinessPlanRepository;

    /**
     * Save a pABusinessPlan.
     *
     * @param pABusinessPlan the entity to save
     * @return the persisted entity
     */
    public PABusinessPlan save(PABusinessPlan pABusinessPlan) {
        log.debug("Request to save PABusinessPlan : {}", pABusinessPlan);
        PABusinessPlan result = pABusinessPlanRepository.save(pABusinessPlan);
        return result;
    }

    /**
     *  Get all the pABusinessPlans.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PABusinessPlan> findAll(Pageable pageable) {
        log.debug("Request to get all PABusinessPlans");
        Page<PABusinessPlan> result = pABusinessPlanRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pABusinessPlan by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PABusinessPlan findOne(Long id) {
        log.debug("Request to get PABusinessPlan : {}", id);
        PABusinessPlan pABusinessPlan = pABusinessPlanRepository.findOne(id);
        return pABusinessPlan;
    }

    /**
     *  Delete the  pABusinessPlan by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PABusinessPlan : {}", id);
        pABusinessPlanRepository.delete(id);
    }
}

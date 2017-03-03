package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAAlarmActualityService;
import com.pervazive.kheddah.domain.PAAlarmActuality;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.repository.PAAlarmActualityRepository;
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
 * Service Implementation for managing PAAlarmActuality.
 */
@Service
@Transactional
public class PAAlarmActualityServiceImpl implements PAAlarmActualityService{

    private final Logger log = LoggerFactory.getLogger(PAAlarmActualityServiceImpl.class);
    
    @Inject
    private PAAlarmActualityRepository pAAlarmActualityRepository;

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pAAlarmActuality.
     *
     * @param pAAlarmActuality the entity to save
     * @return the persisted entity
     */
    public PAAlarmActuality save(PAAlarmActuality pAAlarmActuality) {
        log.debug("Request to save PAAlarmActuality : {}", pAAlarmActuality);
        PAAlarmActuality result = pAAlarmActualityRepository.save(pAAlarmActuality);
        return result;
    }

    /**
     *  Get all the pAAlarmActualities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAAlarmActuality> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PAAlarmActualities");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PAAlarmActuality> result = pAAlarmActualityRepository.findByPaorgaaIn(organizationames, pageable);
        return result;
    }
    
    /**
     *  Get all the pAAlarmActualities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAAlarmActuality> findAll(Pageable pageable, PAOrganization paOrganization) {
        log.debug("Request to get all PAAlarmActualities");
        Page<PAAlarmActuality> result = pAAlarmActualityRepository.findByPaorgaa(paOrganization, pageable);
        return result;
    }

    /**
     *  Get one pAAlarmActuality by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAAlarmActuality findOne(Long id) {
        log.debug("Request to get PAAlarmActuality : {}", id);
        PAAlarmActuality pAAlarmActuality = pAAlarmActualityRepository.findOne(id);
        return pAAlarmActuality;
    }

    /**
     *  Delete the  pAAlarmActuality by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAAlarmActuality : {}", id);
        pAAlarmActualityRepository.delete(id);
    }
}

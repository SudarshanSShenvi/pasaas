package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAGeneralConfigService;
import com.pervazive.kheddah.domain.PAGeneralConfig;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.repository.PAGeneralConfigRepository;
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
 * Service Implementation for managing PAGeneralConfig.
 */
@Service
@Transactional
public class PAGeneralConfigServiceImpl implements PAGeneralConfigService{

    private final Logger log = LoggerFactory.getLogger(PAGeneralConfigServiceImpl.class);
    
    @Inject
    private PAGeneralConfigRepository pAGeneralConfigRepository;


    /**
     * Save a pAGeneralConfig.
     *
     * @param pAGeneralConfig the entity to save
     * @return the persisted entity
     */
    public PAGeneralConfig save(PAGeneralConfig pAGeneralConfig) {
        log.debug("Request to save PAGeneralConfig : {}", pAGeneralConfig);
        PAGeneralConfig result = pAGeneralConfigRepository.save(pAGeneralConfig);
        return result;
    }

    /**
     *  Get all the pAGeneralConfigs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAGeneralConfig> findAll(Pageable pageable) {
        log.debug("Request to get all PAGeneralConfigs");
        Page<PAGeneralConfig> result = pAGeneralConfigRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pAGeneralConfig by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAGeneralConfig findOne(Long id) {
        log.debug("Request to get PAGeneralConfig : {}", id);
        PAGeneralConfig pAGeneralConfig = pAGeneralConfigRepository.findOne(id);
        return pAGeneralConfig;
    }

    /**
     *  Delete the  pAGeneralConfig by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAGeneralConfig : {}", id);
        pAGeneralConfigRepository.delete(id);
    }
}

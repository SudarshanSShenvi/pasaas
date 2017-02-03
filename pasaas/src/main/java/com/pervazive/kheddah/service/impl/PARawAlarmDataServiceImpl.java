package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PARawAlarmDataService;
import com.pervazive.kheddah.domain.PARawAlarmData;
import com.pervazive.kheddah.repository.PARawAlarmDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PARawAlarmData.
 */
@Service
@Transactional
public class PARawAlarmDataServiceImpl implements PARawAlarmDataService{

    private final Logger log = LoggerFactory.getLogger(PARawAlarmDataServiceImpl.class);
    
    @Inject
    private PARawAlarmDataRepository pARawAlarmDataRepository;

    /**
     * Save a pARawAlarmData.
     *
     * @param pARawAlarmData the entity to save
     * @return the persisted entity
     */
    public PARawAlarmData save(PARawAlarmData pARawAlarmData) {
        log.debug("Request to save PARawAlarmData : {}", pARawAlarmData);
        PARawAlarmData result = pARawAlarmDataRepository.save(pARawAlarmData);
        return result;
    }

    /**
     *  Get all the pARawAlarmData.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PARawAlarmData> findAll(Pageable pageable) {
        log.debug("Request to get all PARawAlarmData");
        Page<PARawAlarmData> result = pARawAlarmDataRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pARawAlarmData by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PARawAlarmData findOne(Long id) {
        log.debug("Request to get PARawAlarmData : {}", id);
        PARawAlarmData pARawAlarmData = pARawAlarmDataRepository.findOne(id);
        return pARawAlarmData;
    }

    /**
     *  Delete the  pARawAlarmData by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PARawAlarmData : {}", id);
        pARawAlarmDataRepository.delete(id);
    }
}

package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAAlarmRCAService;
import com.pervazive.kheddah.domain.PAAlarmRCA;
import com.pervazive.kheddah.repository.PAAlarmRCARepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PAAlarmRCA.
 */
@Service
@Transactional
public class PAAlarmRCAServiceImpl implements PAAlarmRCAService{

    private final Logger log = LoggerFactory.getLogger(PAAlarmRCAServiceImpl.class);
    
    @Inject
    private PAAlarmRCARepository pAAlarmRCARepository;

    /**
     * Save a pAAlarmRCA.
     *
     * @param pAAlarmRCA the entity to save
     * @return the persisted entity
     */
    public PAAlarmRCA save(PAAlarmRCA pAAlarmRCA) {
        log.debug("Request to save PAAlarmRCA : {}", pAAlarmRCA);
        PAAlarmRCA result = pAAlarmRCARepository.save(pAAlarmRCA);
        return result;
    }

    /**
     *  Get all the pAAlarmRCAS.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAAlarmRCA> findAll(Pageable pageable) {
        log.debug("Request to get all PAAlarmRCAS");
        Page<PAAlarmRCA> result = pAAlarmRCARepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pAAlarmRCA by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAAlarmRCA findOne(Long id) {
        log.debug("Request to get PAAlarmRCA : {}", id);
        PAAlarmRCA pAAlarmRCA = pAAlarmRCARepository.findOne(id);
        return pAAlarmRCA;
    }

    /**
     *  Delete the  pAAlarmRCA by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAAlarmRCA : {}", id);
        pAAlarmRCARepository.delete(id);
    }
}

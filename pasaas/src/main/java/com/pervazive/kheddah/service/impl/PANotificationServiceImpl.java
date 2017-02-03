package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PANotificationService;
import com.pervazive.kheddah.domain.PANotification;
import com.pervazive.kheddah.repository.PANotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PANotification.
 */
@Service
@Transactional
public class PANotificationServiceImpl implements PANotificationService{

    private final Logger log = LoggerFactory.getLogger(PANotificationServiceImpl.class);
    
    @Inject
    private PANotificationRepository pANotificationRepository;

    /**
     * Save a pANotification.
     *
     * @param pANotification the entity to save
     * @return the persisted entity
     */
    public PANotification save(PANotification pANotification) {
        log.debug("Request to save PANotification : {}", pANotification);
        PANotification result = pANotificationRepository.save(pANotification);
        return result;
    }

    /**
     *  Get all the pANotifications.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PANotification> findAll(Pageable pageable) {
        log.debug("Request to get all PANotifications");
        Page<PANotification> result = pANotificationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pANotification by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PANotification findOne(Long id) {
        log.debug("Request to get PANotification : {}", id);
        PANotification pANotification = pANotificationRepository.findOne(id);
        return pANotification;
    }

    /**
     *  Delete the  pANotification by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PANotification : {}", id);
        pANotificationRepository.delete(id);
    }
}

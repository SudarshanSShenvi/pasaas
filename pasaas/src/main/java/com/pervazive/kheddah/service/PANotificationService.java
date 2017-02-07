package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PANotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PANotification.
 */
public interface PANotificationService {

    /**
     * Save a pANotification.
     *
     * @param pANotification the entity to save
     * @return the persisted entity
     */
    PANotification save(PANotification pANotification);

    /**
     *  Get all the pANotifications.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PANotification> findAll(Pageable pageable, String user);

    /**
     *  Get the "id" pANotification.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PANotification findOne(Long id);

    /**
     *  Delete the "id" pANotification.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

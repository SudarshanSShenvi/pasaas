package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAAlarmRCA;
import com.pervazive.kheddah.domain.PAOrganization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAAlarmRCA.
 */
public interface PAAlarmRCAService {

    /**
     * Save a pAAlarmRCA.
     *
     * @param pAAlarmRCA the entity to save
     * @return the persisted entity
     */
    PAAlarmRCA save(PAAlarmRCA pAAlarmRCA);

    /**
     *  Get all the pAAlarmRCAS.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAAlarmRCA> findAll(Pageable pageable, String user);
    
    Page<PAAlarmRCA> findAll(Pageable pageable, List<PAOrganization> organizationnames);

    /**
     *  Get the "id" pAAlarmRCA.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAAlarmRCA findOne(Long id);

    /**
     *  Delete the "id" pAAlarmRCA.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

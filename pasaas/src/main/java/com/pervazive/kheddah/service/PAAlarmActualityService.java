package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAAlarmActuality;
import com.pervazive.kheddah.domain.PAOrganization;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAAlarmActuality.
 */
public interface PAAlarmActualityService {

    /**
     * Save a pAAlarmActuality.
     *
     * @param pAAlarmActuality the entity to save
     * @return the persisted entity
     */
    PAAlarmActuality save(PAAlarmActuality pAAlarmActuality);

    /**
     *  Get all the pAAlarmActualities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAAlarmActuality> findAll(Pageable pageable, String user);

    Page<PAAlarmActuality> findAll(Pageable pageable, List<PAOrganization> organizationnames);
    /**
     *  Get the "id" pAAlarmActuality.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAAlarmActuality findOne(Long id);

    /**
     *  Delete the "id" pAAlarmActuality.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

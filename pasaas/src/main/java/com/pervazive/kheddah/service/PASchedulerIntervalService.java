package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PASchedulerInterval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PASchedulerInterval.
 */
public interface PASchedulerIntervalService {

    /**
     * Save a pASchedulerInterval.
     *
     * @param pASchedulerInterval the entity to save
     * @return the persisted entity
     */
    PASchedulerInterval save(PASchedulerInterval pASchedulerInterval);

    /**
     *  Get all the pASchedulerIntervals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PASchedulerInterval> findAll(Pageable pageable, String user);
    
    Page<PASchedulerInterval> findAll(Pageable pageable, List<PAOrganization> organizationnames);

    /**
     *  Get the "id" pASchedulerInterval.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PASchedulerInterval findOne(Long id);

    /**
     *  Delete the "id" pASchedulerInterval.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

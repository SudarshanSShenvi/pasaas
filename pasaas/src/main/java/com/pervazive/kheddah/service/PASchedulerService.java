package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAScheduler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAScheduler.
 */
public interface PASchedulerService {

    /**
     * Save a pAScheduler.
     *
     * @param pAScheduler the entity to save
     * @return the persisted entity
     */
    PAScheduler save(PAScheduler pAScheduler);

    /**
     *  Get all the pASchedulers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAScheduler> findAll(Pageable pageable, String user);

    /**
     *  Get the "id" pAScheduler.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAScheduler findOne(Long id);

    /**
     *  Delete the "id" pAScheduler.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

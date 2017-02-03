package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAAlarmActuality;
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
    Page<PAAlarmActuality> findAll(Pageable pageable);

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

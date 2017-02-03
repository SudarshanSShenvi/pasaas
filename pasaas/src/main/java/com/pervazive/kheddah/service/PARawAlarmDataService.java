package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PARawAlarmData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PARawAlarmData.
 */
public interface PARawAlarmDataService {

    /**
     * Save a pARawAlarmData.
     *
     * @param pARawAlarmData the entity to save
     * @return the persisted entity
     */
    PARawAlarmData save(PARawAlarmData pARawAlarmData);

    /**
     *  Get all the pARawAlarmData.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PARawAlarmData> findAll(Pageable pageable);

    /**
     *  Get the "id" pARawAlarmData.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PARawAlarmData findOne(Long id);

    /**
     *  Delete the "id" pARawAlarmData.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

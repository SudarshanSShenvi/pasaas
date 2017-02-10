package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAPrediction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAPrediction.
 */
public interface PAPredictionService {

    /**
     * Save a pAPrediction.
     *
     * @param pAPrediction the entity to save
     * @return the persisted entity
     */
    PAPrediction save(PAPrediction pAPrediction);

    /**
     *  Get all the pAPredictions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAPrediction> findAll(Pageable pageable, String user);
    
    Page<PAPrediction> findAll(Pageable pageable, List<PAOrganization> organizationnmaes);

    /**
     *  Get the "id" pAPrediction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAPrediction findOne(Long id);

    /**
     *  Delete the "id" pAPrediction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

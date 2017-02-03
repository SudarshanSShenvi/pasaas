package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAPredictionScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAPredictionScore.
 */
public interface PAPredictionScoreService {

    /**
     * Save a pAPredictionScore.
     *
     * @param pAPredictionScore the entity to save
     * @return the persisted entity
     */
    PAPredictionScore save(PAPredictionScore pAPredictionScore);

    /**
     *  Get all the pAPredictionScores.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAPredictionScore> findAll(Pageable pageable);

    /**
     *  Get the "id" pAPredictionScore.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAPredictionScore findOne(Long id);

    /**
     *  Delete the "id" pAPredictionScore.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

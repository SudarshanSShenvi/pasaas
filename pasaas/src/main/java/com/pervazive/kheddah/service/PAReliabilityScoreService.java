package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAReliabilityScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAReliabilityScore.
 */
public interface PAReliabilityScoreService {

    /**
     * Save a pAReliabilityScore.
     *
     * @param pAReliabilityScore the entity to save
     * @return the persisted entity
     */
    PAReliabilityScore save(PAReliabilityScore pAReliabilityScore);

    /**
     *  Get all the pAReliabilityScores.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAReliabilityScore> findAll(Pageable pageable, String user);
    
    Page<PAReliabilityScore> findAll(Pageable pageable, PAOrganization paOrganization);

    /**
     *  Get the "id" pAReliabilityScore.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAReliabilityScore findOne(Long id);

    /**
     *  Delete the "id" pAReliabilityScore.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAAccPrecision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAAccPrecision.
 */
public interface PAAccPrecisionService {

    /**
     * Save a pAAccPrecision.
     *
     * @param pAAccPrecision the entity to save
     * @return the persisted entity
     */
    PAAccPrecision save(PAAccPrecision pAAccPrecision);

    /**
     *  Get all the pAAccPrecisions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAAccPrecision> findAll(Pageable pageable);

    /**
     *  Get the "id" pAAccPrecision.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAAccPrecision findOne(Long id);

    /**
     *  Delete the "id" pAAccPrecision.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

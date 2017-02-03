package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PANEDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PANEDetails.
 */
public interface PANEDetailsService {

    /**
     * Save a pANEDetails.
     *
     * @param pANEDetails the entity to save
     * @return the persisted entity
     */
    PANEDetails save(PANEDetails pANEDetails);

    /**
     *  Get all the pANEDetails.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PANEDetails> findAll(Pageable pageable);

    /**
     *  Get the "id" pANEDetails.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PANEDetails findOne(Long id);

    /**
     *  Delete the "id" pANEDetails.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

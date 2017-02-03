package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAPMTRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAPMTRequest.
 */
public interface PAPMTRequestService {

    /**
     * Save a pAPMTRequest.
     *
     * @param pAPMTRequest the entity to save
     * @return the persisted entity
     */
    PAPMTRequest save(PAPMTRequest pAPMTRequest);

    /**
     *  Get all the pAPMTRequests.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAPMTRequest> findAll(Pageable pageable);

    /**
     *  Get the "id" pAPMTRequest.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAPMTRequest findOne(Long id);

    /**
     *  Delete the "id" pAPMTRequest.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAOrganization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAOrganization.
 */
public interface PAOrganizationService {

    /**
     * Save a pAOrganization.
     *
     * @param pAOrganization the entity to save
     * @return the persisted entity
     */
    PAOrganization save(PAOrganization pAOrganization);

    /**
     *  Get all the pAOrganizations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAOrganization> findAll(Pageable pageable);

    /**
     *  Get the "id" pAOrganization.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAOrganization findOne(Long id);

    /**
     *  Delete the "id" pAOrganization.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

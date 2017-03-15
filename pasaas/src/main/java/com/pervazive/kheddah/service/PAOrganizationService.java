package com.pervazive.kheddah.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pervazive.kheddah.domain.PABusinessPlan;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.enumeration.PAStatus;

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
    
    Page<PAOrganization> findAll(Pageable pageable, String user);
    
    Page<PAOrganization> findAll(Pageable pageable, List<PAOrganization> organizationnames);
    
    PAOrganization findOrganizationByName(String paOrganization);

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
    
    public void updateOrganizationwithUsers(Long id, String organizationname, ZonedDateTime validfrom, ZonedDateTime validto,
			PAStatus pastatus, PABusinessPlan pabporg, Set<String> pausers, Set<String> paprojects) ;
    
    public PAOrganization getOrganizationWithUser(Long id);
}

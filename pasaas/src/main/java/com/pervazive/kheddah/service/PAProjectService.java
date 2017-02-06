package com.pervazive.kheddah.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pervazive.kheddah.domain.PABusinessPlan;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.domain.enumeration.PAStatus;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Service Interface for managing PAProject.
 */
public interface PAProjectService {

    /**
     * Save a pAProject.
     *
     * @param pAProject the entity to save
     * @return the persisted entity
     */
    PAProject save(PAProject pAProject);

    /**
     *  Get all the pAProjects.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAProject> findAll(Pageable pageable);

    /**
     *  Get the "id" pAProject.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAProject findOne(Long id);

    /**
     *  Delete the "id" pAProject.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
    
    public void updateProjectwithUsers(Long id, String projectname, String description, PAOrganization paorgpro, Set<String> pausers) ;
    
    public PAProject getProjectWithUser(Long id);


}

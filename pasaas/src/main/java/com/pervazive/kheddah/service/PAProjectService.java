package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

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
}

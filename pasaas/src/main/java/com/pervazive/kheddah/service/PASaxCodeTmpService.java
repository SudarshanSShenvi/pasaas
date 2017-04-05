package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.PASaxCodeTmp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PASaxCodeTmp.
 */
public interface PASaxCodeTmpService {

    /**
     * Save a pASaxCodeTmp.
     *
     * @param pASaxCodeTmp the entity to save
     * @return the persisted entity
     */
    PASaxCodeTmp save(PASaxCodeTmp pASaxCodeTmp);

    /**
     *  Get all the pASaxCodeTmps.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PASaxCodeTmp> findAll(Pageable pageable, String user);

    Page<PASaxCodeTmp> findAll(Pageable pageable, PAOrganization paOrganization);
    
    Page<PASaxCodeTmp> findAllProject(Pageable pageable, PAProject paProject);
    /**
     *  Get the "id" pASaxCodeTmp.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PASaxCodeTmp findOne(Long id);

    /**
     *  Delete the "id" pASaxCodeTmp.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

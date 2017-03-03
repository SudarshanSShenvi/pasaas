package com.pervazive.kheddah.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pervazive.kheddah.domain.PAFileUpload;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.User;

/**
 * Service Interface for managing PAFileUpload.
 */
public interface PAFileUploadService {

    /**
     * Save a pAFileUpload.
     *
     * @param pAFileUpload the entity to save
     * @return the persisted entity
     */
    PAFileUpload save(PAFileUpload pAFileUpload);

    /**
     *  Get all the pAFileUploads.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    //Page<PAFileUpload> findAll(Pageable pageable);
    Page<PAFileUpload> findAll(Pageable pageable, String user);
    
    Page<PAFileUpload> findAll(Pageable pageable, PAOrganization paOrganization);

    /**
     *  Get the "id" pAFileUpload.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAFileUpload findOne(Long id);

    /**
     *  Delete the "id" pAFileUpload.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

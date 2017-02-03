package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAFileUpload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

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
    Page<PAFileUpload> findAll(Pageable pageable);

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

package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAErrorMessage.
 */
public interface PAErrorMessageService {

    /**
     * Save a pAErrorMessage.
     *
     * @param pAErrorMessage the entity to save
     * @return the persisted entity
     */
    PAErrorMessage save(PAErrorMessage pAErrorMessage);

    /**
     *  Get all the pAErrorMessages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAErrorMessage> findAll(Pageable pageable, String user);

    /**
     *  Get the "id" pAErrorMessage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAErrorMessage findOne(Long id);

    /**
     *  Delete the "id" pAErrorMessage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

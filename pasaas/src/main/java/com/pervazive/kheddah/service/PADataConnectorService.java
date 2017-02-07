package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PADataConnector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PADataConnector.
 */
public interface PADataConnectorService {

    /**
     * Save a pADataConnector.
     *
     * @param pADataConnector the entity to save
     * @return the persisted entity
     */
    PADataConnector save(PADataConnector pADataConnector);

    /**
     *  Get all the pADataConnectors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PADataConnector> findAll(Pageable pageable, String user);

    /**
     *  Get the "id" pADataConnector.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PADataConnector findOne(Long id);

    /**
     *  Delete the "id" pADataConnector.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

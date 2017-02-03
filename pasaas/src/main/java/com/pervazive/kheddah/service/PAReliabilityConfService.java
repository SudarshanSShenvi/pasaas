package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAReliabilityConf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAReliabilityConf.
 */
public interface PAReliabilityConfService {

    /**
     * Save a pAReliabilityConf.
     *
     * @param pAReliabilityConf the entity to save
     * @return the persisted entity
     */
    PAReliabilityConf save(PAReliabilityConf pAReliabilityConf);

    /**
     *  Get all the pAReliabilityConfs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAReliabilityConf> findAll(Pageable pageable);

    /**
     *  Get the "id" pAReliabilityConf.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAReliabilityConf findOne(Long id);

    /**
     *  Delete the "id" pAReliabilityConf.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

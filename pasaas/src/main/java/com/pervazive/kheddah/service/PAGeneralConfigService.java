package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAGeneralConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAGeneralConfig.
 */
public interface PAGeneralConfigService {

    /**
     * Save a pAGeneralConfig.
     *
     * @param pAGeneralConfig the entity to save
     * @return the persisted entity
     */
    PAGeneralConfig save(PAGeneralConfig pAGeneralConfig);

    /**
     *  Get all the pAGeneralConfigs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAGeneralConfig> findAll(Pageable pageable);

    /**
     *  Get the "id" pAGeneralConfig.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAGeneralConfig findOne(Long id);

    /**
     *  Delete the "id" pAGeneralConfig.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PASaxCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PASaxCode.
 */
public interface PASaxCodeService {

    /**
     * Save a pASaxCode.
     *
     * @param pASaxCode the entity to save
     * @return the persisted entity
     */
    PASaxCode save(PASaxCode pASaxCode);

    /**
     *  Get all the pASaxCodes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PASaxCode> findAll(Pageable pageable, String user);

    /**
     *  Get the "id" pASaxCode.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PASaxCode findOne(Long id);

    /**
     *  Delete the "id" pASaxCode.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

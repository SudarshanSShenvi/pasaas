package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PAReport.
 */
public interface PAReportService {

    /**
     * Save a pAReport.
     *
     * @param pAReport the entity to save
     * @return the persisted entity
     */
    PAReport save(PAReport pAReport);

    /**
     *  Get all the pAReports.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PAReport> findAll(Pageable pageable, String user);

    Page<PAReport> findAll(Pageable pageable, PAOrganization paOrganization);
    /**
     *  Get the "id" pAReport.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PAReport findOne(Long id);

    /**
     *  Delete the "id" pAReport.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

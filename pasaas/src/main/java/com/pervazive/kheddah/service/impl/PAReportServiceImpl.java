package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAReportService;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAReport;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.repository.PAReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PAReport.
 */
@Service
@Transactional
public class PAReportServiceImpl implements PAReportService{

    private final Logger log = LoggerFactory.getLogger(PAReportServiceImpl.class);
    
    @Inject
    private PAReportRepository pAReportRepository;
    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pAReport.
     *
     * @param pAReport the entity to save
     * @return the persisted entity
     */
    public PAReport save(PAReport pAReport) {
        log.debug("Request to save PAReport : {}", pAReport);
        PAReport result = pAReportRepository.save(pAReport);
        return result;
    }

    /**
     *  Get all the pAReports.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAReport> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PAReports");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PAReport> result = pAReportRepository.findByPaorgrepIn(organizationames, pageable);
        //Page<PAReport> result = pAReportRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pAReport by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAReport findOne(Long id) {
        log.debug("Request to get PAReport : {}", id);
        PAReport pAReport = pAReportRepository.findOne(id);
        return pAReport;
    }

    /**
     *  Delete the  pAReport by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAReport : {}", id);
        pAReportRepository.delete(id);
    }
}

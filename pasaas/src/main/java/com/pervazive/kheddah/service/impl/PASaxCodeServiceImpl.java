package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PASaxCodeService;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PASaxCode;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.repository.PASaxCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PASaxCode.
 */
@Service
@Transactional
public class PASaxCodeServiceImpl implements PASaxCodeService{

    private final Logger log = LoggerFactory.getLogger(PASaxCodeServiceImpl.class);
    
    @Inject
    private PASaxCodeRepository pASaxCodeRepository;

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pASaxCode.
     *
     * @param pASaxCode the entity to save
     * @return the persisted entity
     */
    public PASaxCode save(PASaxCode pASaxCode) {
        //log.debug("Request to save PASaxCode : {}", pASaxCode);
        PASaxCode result = pASaxCodeRepository.save(pASaxCode);
        return result;
    }

    /**
     *  Get all the pASaxCodes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PASaxCode> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PASaxCodes");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PASaxCode> result = pASaxCodeRepository.findByPaorgscIn(organizationames, pageable);
        //Page<PASaxCode> result = pASaxCodeRepository.findAll(pageable);
        return result;
    }
    
    /**
     *  Get all the pASaxCodes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PASaxCode> findAll(Pageable pageable, PAOrganization paOrganization) {
        log.debug("Request to get all PASaxCodes");
        Page<PASaxCode> result = pASaxCodeRepository.findByPaorgsc(paOrganization, pageable);
        //Page<PASaxCode> result = pASaxCodeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pASaxCode by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PASaxCode findOne(Long id) {
        log.debug("Request to get PASaxCode : {}", id);
        PASaxCode pASaxCode = pASaxCodeRepository.findOne(id);
        return pASaxCode;
    }

    /**
     *  Delete the  pASaxCode by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PASaxCode : {}", id);
        pASaxCodeRepository.delete(id);
    }
}

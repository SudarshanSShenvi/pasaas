package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PASaxCodeTmpService;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PASaxCodeTmp;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.repository.PASaxCodeTmpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PASaxCodeTmp.
 */
@Service
@Transactional
public class PASaxCodeTmpServiceImpl implements PASaxCodeTmpService{

    private final Logger log = LoggerFactory.getLogger(PASaxCodeTmpServiceImpl.class);
    
    @Inject
    private PASaxCodeTmpRepository pASaxCodeTmpRepository;

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pASaxCodeTmp.
     *
     * @param pASaxCodeTmp the entity to save
     * @return the persisted entity
     */
    public PASaxCodeTmp save(PASaxCodeTmp pASaxCodeTmp) {
        log.debug("Request to save PASaxCodeTmp : {}", pASaxCodeTmp);
        PASaxCodeTmp result = pASaxCodeTmpRepository.save(pASaxCodeTmp);
        return result;
    }

    /**
     *  Get all the pASaxCodeTmps.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PASaxCodeTmp> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PASaxCodeTmps");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PASaxCodeTmp> result = pASaxCodeTmpRepository.findByPaorgsctIn(organizationames, pageable);
        //Page<PASaxCodeTmp> result = pASaxCodeTmpRepository.findAll(pageable);
        return result;
    }
    
    /**
     *  Get all the pASaxCodeTmps.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PASaxCodeTmp> findAll(Pageable pageable, List<PAOrganization> organizationames) {
        log.debug("Request to get all PASaxCodeTmps");
        Page<PASaxCodeTmp> result = pASaxCodeTmpRepository.findByPaorgsctIn(organizationames, pageable);
        //Page<PASaxCodeTmp> result = pASaxCodeTmpRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pASaxCodeTmp by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PASaxCodeTmp findOne(Long id) {
        log.debug("Request to get PASaxCodeTmp : {}", id);
        PASaxCodeTmp pASaxCodeTmp = pASaxCodeTmpRepository.findOne(id);
        return pASaxCodeTmp;
    }

    /**
     *  Delete the  pASaxCodeTmp by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PASaxCodeTmp : {}", id);
        pASaxCodeTmpRepository.delete(id);
    }
}

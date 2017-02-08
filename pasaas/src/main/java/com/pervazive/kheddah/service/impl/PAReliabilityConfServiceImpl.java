package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAReliabilityConfService;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAReliabilityConf;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.repository.PAReliabilityConfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PAReliabilityConf.
 */
@Service
@Transactional
public class PAReliabilityConfServiceImpl implements PAReliabilityConfService{

    private final Logger log = LoggerFactory.getLogger(PAReliabilityConfServiceImpl.class);
    
    @Inject
    private PAReliabilityConfRepository pAReliabilityConfRepository;

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pAReliabilityConf.
     *
     * @param pAReliabilityConf the entity to save
     * @return the persisted entity
     */
    public PAReliabilityConf save(PAReliabilityConf pAReliabilityConf) {
        log.debug("Request to save PAReliabilityConf : {}", pAReliabilityConf);
        PAReliabilityConf result = pAReliabilityConfRepository.save(pAReliabilityConf);
        return result;
    }

    /**
     *  Get all the pAReliabilityConfs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAReliabilityConf> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PAReliabilityConfs");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PAReliabilityConf> result = pAReliabilityConfRepository.findByPaorgrcIn(organizationames, pageable);
        //Page<PAReliabilityConf> result = pAReliabilityConfRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pAReliabilityConf by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAReliabilityConf findOne(Long id) {
        log.debug("Request to get PAReliabilityConf : {}", id);
        PAReliabilityConf pAReliabilityConf = pAReliabilityConfRepository.findOne(id);
        return pAReliabilityConf;
    }

    /**
     *  Delete the  pAReliabilityConf by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAReliabilityConf : {}", id);
        pAReliabilityConfRepository.delete(id);
    }
}

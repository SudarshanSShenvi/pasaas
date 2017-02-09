package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PADataConnectorService;
import com.pervazive.kheddah.domain.PADataConnector;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.repository.PADataConnectorRepository;
import com.pervazive.kheddah.repository.PAOrganizationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PADataConnector.
 */
@Service
@Transactional
public class PADataConnectorServiceImpl implements PADataConnectorService{

    private final Logger log = LoggerFactory.getLogger(PADataConnectorServiceImpl.class);
    
    @Inject
    private PADataConnectorRepository pADataConnectorRepository;

    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pADataConnector.
     *
     * @param pADataConnector the entity to save
     * @return the persisted entity
     */
    public PADataConnector save(PADataConnector pADataConnector) {
        log.debug("Request to save PADataConnector : {}", pADataConnector);
        PADataConnector result = pADataConnectorRepository.save(pADataConnector);
        return result;
    }

    /**
     *  Get all the pADataConnectors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PADataConnector> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PADataConnectors");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PADataConnector> result = pADataConnectorRepository.findByPaorgdc(organizationames, pageable);
        //Page<PADataConnector> result = pADataConnectorRepository.findAll(pageable);
        return result;
    }
    
    /**
     *  Get all the pADataConnectors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PADataConnector> findAll(Pageable pageable, List<PAOrganization> organizationames ) {
        log.debug("Request to get all PADataConnectors");
        Page<PADataConnector> result = pADataConnectorRepository.findByPaorgdc(organizationames, pageable);
        //Page<PADataConnector> result = pADataConnectorRepository.findAll(pageable);
        return result;
    }


    /**
     *  Get one pADataConnector by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PADataConnector findOne(Long id) {
        log.debug("Request to get PADataConnector : {}", id);
        PADataConnector pADataConnector = pADataConnectorRepository.findOne(id);
        return pADataConnector;
    }

    /**
     *  Delete the  pADataConnector by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PADataConnector : {}", id);
        pADataConnectorRepository.delete(id);
    }
}

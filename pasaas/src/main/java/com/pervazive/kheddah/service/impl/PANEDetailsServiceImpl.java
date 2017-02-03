package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PANEDetailsService;
import com.pervazive.kheddah.domain.PANEDetails;
import com.pervazive.kheddah.repository.PANEDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PANEDetails.
 */
@Service
@Transactional
public class PANEDetailsServiceImpl implements PANEDetailsService{

    private final Logger log = LoggerFactory.getLogger(PANEDetailsServiceImpl.class);
    
    @Inject
    private PANEDetailsRepository pANEDetailsRepository;

    /**
     * Save a pANEDetails.
     *
     * @param pANEDetails the entity to save
     * @return the persisted entity
     */
    public PANEDetails save(PANEDetails pANEDetails) {
        log.debug("Request to save PANEDetails : {}", pANEDetails);
        PANEDetails result = pANEDetailsRepository.save(pANEDetails);
        return result;
    }

    /**
     *  Get all the pANEDetails.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PANEDetails> findAll(Pageable pageable) {
        log.debug("Request to get all PANEDetails");
        Page<PANEDetails> result = pANEDetailsRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pANEDetails by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PANEDetails findOne(Long id) {
        log.debug("Request to get PANEDetails : {}", id);
        PANEDetails pANEDetails = pANEDetailsRepository.findOne(id);
        return pANEDetails;
    }

    /**
     *  Delete the  pANEDetails by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PANEDetails : {}", id);
        pANEDetailsRepository.delete(id);
    }
}

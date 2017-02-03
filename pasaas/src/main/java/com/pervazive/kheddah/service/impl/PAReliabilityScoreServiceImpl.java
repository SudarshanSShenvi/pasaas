package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAReliabilityScoreService;
import com.pervazive.kheddah.domain.PAReliabilityScore;
import com.pervazive.kheddah.repository.PAReliabilityScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PAReliabilityScore.
 */
@Service
@Transactional
public class PAReliabilityScoreServiceImpl implements PAReliabilityScoreService{

    private final Logger log = LoggerFactory.getLogger(PAReliabilityScoreServiceImpl.class);
    
    @Inject
    private PAReliabilityScoreRepository pAReliabilityScoreRepository;

    /**
     * Save a pAReliabilityScore.
     *
     * @param pAReliabilityScore the entity to save
     * @return the persisted entity
     */
    public PAReliabilityScore save(PAReliabilityScore pAReliabilityScore) {
        log.debug("Request to save PAReliabilityScore : {}", pAReliabilityScore);
        PAReliabilityScore result = pAReliabilityScoreRepository.save(pAReliabilityScore);
        return result;
    }

    /**
     *  Get all the pAReliabilityScores.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAReliabilityScore> findAll(Pageable pageable) {
        log.debug("Request to get all PAReliabilityScores");
        Page<PAReliabilityScore> result = pAReliabilityScoreRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pAReliabilityScore by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAReliabilityScore findOne(Long id) {
        log.debug("Request to get PAReliabilityScore : {}", id);
        PAReliabilityScore pAReliabilityScore = pAReliabilityScoreRepository.findOne(id);
        return pAReliabilityScore;
    }

    /**
     *  Delete the  pAReliabilityScore by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAReliabilityScore : {}", id);
        pAReliabilityScoreRepository.delete(id);
    }
}

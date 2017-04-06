package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAPredictionScoreService;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAPredictionScore;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.repository.PAOrganizationRepository;
import com.pervazive.kheddah.repository.PAPredictionScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PAPredictionScore.
 */
@Service
@Transactional
public class PAPredictionScoreServiceImpl implements PAPredictionScoreService{

    private final Logger log = LoggerFactory.getLogger(PAPredictionScoreServiceImpl.class);
    
    @Inject
    private PAPredictionScoreRepository pAPredictionScoreRepository;
    @Inject
    private PAOrganizationRepository paOrganizationRepository;
    /**
     * Save a pAPredictionScore.
     *
     * @param pAPredictionScore the entity to save
     * @return the persisted entity
     */
    public PAPredictionScore save(PAPredictionScore pAPredictionScore) {
        log.debug("Request to save PAPredictionScore : {}", pAPredictionScore);
        PAPredictionScore result = pAPredictionScoreRepository.save(pAPredictionScore);
        return result;
    }

    /**
     *  Get all the pAPredictionScores.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAPredictionScore> findAll(Pageable pageable, String pausers) {
        log.debug("Request to get all PAPredictionScores");
        List<PAOrganization> organizationames = paOrganizationRepository.findOrgsByPAUser(pausers);
        Page<PAPredictionScore> result = pAPredictionScoreRepository.findByPaorgpsIn(organizationames, pageable);
        //Page<PAPredictionScore> result = pAPredictionScoreRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get all the pAPredictionScores.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAPredictionScore> findAll(Pageable pageable,  PAOrganization paOrganization) {
        log.debug("Request to get all PAPredictionScores");
        Page<PAPredictionScore> result = pAPredictionScoreRepository.findByPaorgps(paOrganization, pageable);
        //Page<PAPredictionScore> result = pAPredictionScoreRepository.findAll(pageable);
        return result;
    }
    
    @Transactional(readOnly = true) 
    public Page<PAPredictionScore> findAllProject(Pageable pageable,  PAProject paProject) {
        log.debug("Request to get all PAPredictionScores");
        Page<PAPredictionScore> result = pAPredictionScoreRepository.findByPaprops(paProject, pageable);
        //Page<PAPredictionScore> result = pAPredictionScoreRepository.findAll(pageable);
        return result;
    }
    
    @Transactional(readOnly = true) 
    public Page<PAPredictionScore> findAllPredictionsForChartC(Pageable pageable,  PAProject paProject) {
        log.debug("Request to get all PAPredictionScores");
        Page<PAPredictionScore> result = pAPredictionScoreRepository.findCscorePredictions(1F, paProject.getId(), pageable);
        //Page<PAPredictionScore> result = pAPredictionScoreRepository.findAll(pageable);
        return result;
    }
    
    @Transactional(readOnly = true) 
    public Page<PAPredictionScore> findAllPredictionsForChartB(Pageable pageable,  PAProject paProject) {
        log.debug("Request to get all PAPredictionScores");
        Page<PAPredictionScore> result = pAPredictionScoreRepository.findBscorePredictions(1F, paProject.getId(), pageable);
        //Page<PAPredictionScore> result = pAPredictionScoreRepository.findAll(pageable);
        return result;
    }
    
    
    /**
     *  Get all the pAPredictionScores.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAPredictionScore> findFailuresAbove(Pageable pageable,  Float probValStart, Float probValEnd ) {
        log.debug("Request to get all PAPredictionScores");
        Page<PAPredictionScore> result = pAPredictionScoreRepository.findByCscoreBetween(probValStart, probValEnd,  pageable);
        //Page<PAPredictionScore> result = pAPredictionScoreRepository.findAll(pageable);
        return result;
    }
    
    /**
     *  Get all the pAPredictionScores.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAPredictionScore> findStableAbove(Pageable pageable,  Float probValStart, Float probValEnd) {
        log.debug("Request to get all PAPredictionScores");
        Page<PAPredictionScore> result = pAPredictionScoreRepository.findByBscoreBetween(probValStart, probValEnd, pageable);
        //Page<PAPredictionScore> result = pAPredictionScoreRepository.findAll(pageable);
        return result;
    }
    
    /**
     *  Get one pAPredictionScore by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAPredictionScore findOne(Long id) {
        log.debug("Request to get PAPredictionScore : {}", id);
        PAPredictionScore pAPredictionScore = pAPredictionScoreRepository.findOne(id);
        return pAPredictionScore;
    }

    /**
     *  Delete the  pAPredictionScore by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAPredictionScore : {}", id);
        pAPredictionScoreRepository.delete(id);
    }
}

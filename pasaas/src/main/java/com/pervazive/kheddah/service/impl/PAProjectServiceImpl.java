package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAProjectService;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.repository.PAProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PAProject.
 */
@Service
@Transactional
public class PAProjectServiceImpl implements PAProjectService{

    private final Logger log = LoggerFactory.getLogger(PAProjectServiceImpl.class);
    
    @Inject
    private PAProjectRepository pAProjectRepository;

    /**
     * Save a pAProject.
     *
     * @param pAProject the entity to save
     * @return the persisted entity
     */
    public PAProject save(PAProject pAProject) {
        log.debug("Request to save PAProject : {}", pAProject);
        PAProject result = pAProjectRepository.save(pAProject);
        return result;
    }

    /**
     *  Get all the pAProjects.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAProject> findAll(Pageable pageable) {
        log.debug("Request to get all PAProjects");
        Page<PAProject> result = pAProjectRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pAProject by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAProject findOne(Long id) {
        log.debug("Request to get PAProject : {}", id);
        PAProject pAProject = pAProjectRepository.findOne(id);
        return pAProject;
    }

    /**
     *  Delete the  pAProject by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAProject : {}", id);
        pAProjectRepository.delete(id);
    }
}

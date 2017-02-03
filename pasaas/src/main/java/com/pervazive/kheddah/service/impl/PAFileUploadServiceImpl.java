package com.pervazive.kheddah.service.impl;

import com.pervazive.kheddah.service.PAFileUploadService;
import com.pervazive.kheddah.domain.PAFileUpload;
import com.pervazive.kheddah.repository.PAFileUploadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PAFileUpload.
 */
@Service
@Transactional
public class PAFileUploadServiceImpl implements PAFileUploadService{

    private final Logger log = LoggerFactory.getLogger(PAFileUploadServiceImpl.class);
    
    @Inject
    private PAFileUploadRepository pAFileUploadRepository;

    /**
     * Save a pAFileUpload.
     *
     * @param pAFileUpload the entity to save
     * @return the persisted entity
     */
    public PAFileUpload save(PAFileUpload pAFileUpload) {
        log.debug("Request to save PAFileUpload : {}", pAFileUpload);
        PAFileUpload result = pAFileUploadRepository.save(pAFileUpload);
        return result;
    }

    /**
     *  Get all the pAFileUploads.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PAFileUpload> findAll(Pageable pageable) {
        log.debug("Request to get all PAFileUploads");
        Page<PAFileUpload> result = pAFileUploadRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one pAFileUpload by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PAFileUpload findOne(Long id) {
        log.debug("Request to get PAFileUpload : {}", id);
        PAFileUpload pAFileUpload = pAFileUploadRepository.findOne(id);
        return pAFileUpload;
    }

    /**
     *  Delete the  pAFileUpload by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PAFileUpload : {}", id);
        pAFileUploadRepository.delete(id);
    }
}

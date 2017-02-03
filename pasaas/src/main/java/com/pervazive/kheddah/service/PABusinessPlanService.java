package com.pervazive.kheddah.service;

import com.pervazive.kheddah.domain.PABusinessPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PABusinessPlan.
 */
public interface PABusinessPlanService {

    /**
     * Save a pABusinessPlan.
     *
     * @param pABusinessPlan the entity to save
     * @return the persisted entity
     */
    PABusinessPlan save(PABusinessPlan pABusinessPlan);

    /**
     *  Get all the pABusinessPlans.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PABusinessPlan> findAll(Pageable pageable);

    /**
     *  Get the "id" pABusinessPlan.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PABusinessPlan findOne(Long id);

    /**
     *  Delete the "id" pABusinessPlan.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

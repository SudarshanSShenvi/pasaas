package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PABusinessPlan;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PABusinessPlan entity.
 */
@SuppressWarnings("unused")
public interface PABusinessPlanRepository extends JpaRepository<PABusinessPlan,Long> {

}

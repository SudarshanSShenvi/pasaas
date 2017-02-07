package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PABusinessPlan;
import com.pervazive.kheddah.domain.PAOrganization;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PABusinessPlan entity.
 */
@SuppressWarnings("unused")
public interface PABusinessPlanRepository extends JpaRepository<PABusinessPlan,Long> {
	
}

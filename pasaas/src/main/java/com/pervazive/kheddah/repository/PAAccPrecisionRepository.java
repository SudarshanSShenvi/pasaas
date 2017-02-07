package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAAccPrecision;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import com.pervazive.kheddah.domain.PAOrganization;

/**
 * Spring Data JPA repository for the PAAccPrecision entity.
 */
@SuppressWarnings("unused")
public interface PAAccPrecisionRepository extends JpaRepository<PAAccPrecision,Long> {

	
	List<PAAccPrecision> findByPaorgapIn(List<PAOrganization> paOrganization, Pageable pageable);
}

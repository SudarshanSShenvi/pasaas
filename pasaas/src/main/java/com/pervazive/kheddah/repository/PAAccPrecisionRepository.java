package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAAccPrecision;
import com.pervazive.kheddah.domain.PAOrganization;

/**
 * Spring Data JPA repository for the PAAccPrecision entity.
 */
@SuppressWarnings("unused")
public interface PAAccPrecisionRepository extends JpaRepository<PAAccPrecision,Long> {

	
	Page<PAAccPrecision> findByPaorgapIn(List<PAOrganization> paOrganization, Pageable pageable);
}

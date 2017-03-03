package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAReliabilityScore;

/**
 * Spring Data JPA repository for the PAReliabilityScore entity.
 */
@SuppressWarnings("unused")
public interface PAReliabilityScoreRepository extends JpaRepository<PAReliabilityScore,Long> {

	Page<PAReliabilityScore> findByPaorgrsIn(List<PAOrganization> paOrganization, Pageable pageable);
	
	Page<PAReliabilityScore> findByPaorgrs(PAOrganization paOrganization, Pageable pageable);
}

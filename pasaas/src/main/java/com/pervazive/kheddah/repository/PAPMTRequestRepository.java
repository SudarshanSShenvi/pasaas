package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAPMTRequest;

/**
 * Spring Data JPA repository for the PAPMTRequest entity.
 */
@SuppressWarnings("unused")
public interface PAPMTRequestRepository extends JpaRepository<PAPMTRequest,Long> {
	
	Page<PAPMTRequest> findByPaorgpmtIn(List<PAOrganization> paOrganization, Pageable pageable);
	
	Page<PAPMTRequest> findByPaorgpmt(PAOrganization paOrganization, Pageable pageable);
}

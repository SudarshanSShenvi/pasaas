package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PASchedulerInterval;

/**
 * Spring Data JPA repository for the PASchedulerInterval entity.
 */
@SuppressWarnings("unused")
public interface PASchedulerIntervalRepository extends JpaRepository<PASchedulerInterval,Long> {

	
	Page<PASchedulerInterval> findByPaorgsciIn(List<PAOrganization> paOrganization, Pageable pageable);
	
	Page<PASchedulerInterval> findByPaorgsci(PAOrganization paOrganization, Pageable pageable);
}

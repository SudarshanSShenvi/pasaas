package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAScheduler;

/**
 * Spring Data JPA repository for the PAScheduler entity.
 */
@SuppressWarnings("unused")
public interface PASchedulerRepository extends JpaRepository<PAScheduler,Long> {
	Page<PAScheduler> findByPaorgschIn(List<PAOrganization> paOrganization, Pageable pageable);
}

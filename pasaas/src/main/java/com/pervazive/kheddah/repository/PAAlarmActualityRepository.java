package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAAlarmActuality;
import com.pervazive.kheddah.domain.PAOrganization;

/**
 * Spring Data JPA repository for the PAAlarmActuality entity.
 */
@SuppressWarnings("unused")
public interface PAAlarmActualityRepository extends JpaRepository<PAAlarmActuality,Long> {
	Page<PAAlarmActuality> findByPaorgaaIn(List<PAOrganization> paOrganization, Pageable pageable);
	
	Page<PAAlarmActuality> findByPaorgaa(PAOrganization paorgaa, Pageable pageable);
}

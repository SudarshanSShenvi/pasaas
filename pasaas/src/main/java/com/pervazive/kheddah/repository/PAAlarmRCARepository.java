package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAAlarmRCA;
import com.pervazive.kheddah.domain.PAOrganization;

/**
 * Spring Data JPA repository for the PAAlarmRCA entity.
 */
@SuppressWarnings("unused")
public interface PAAlarmRCARepository extends JpaRepository<PAAlarmRCA,Long> {
	
	Page<PAAlarmRCA> findByPaorgarcIn(List<PAOrganization> paOrganization, Pageable pageable);
	
	Page<PAAlarmRCA> findByPaorgarc(PAOrganization paorgarc, Pageable pageable);
}

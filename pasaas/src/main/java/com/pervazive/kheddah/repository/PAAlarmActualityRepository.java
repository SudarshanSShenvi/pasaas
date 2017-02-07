package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAAlarmActuality;
import com.pervazive.kheddah.domain.PAOrganization;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAAlarmActuality entity.
 */
@SuppressWarnings("unused")
public interface PAAlarmActualityRepository extends JpaRepository<PAAlarmActuality,Long> {
	List<PAAlarmActuality> findByPaorgaaIn(List<PAOrganization> paOrganization, Pageable pageable);
}

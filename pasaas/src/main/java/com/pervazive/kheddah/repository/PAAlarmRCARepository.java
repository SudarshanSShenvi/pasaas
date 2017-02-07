package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAAlarmRCA;
import com.pervazive.kheddah.domain.PAOrganization;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAAlarmRCA entity.
 */
@SuppressWarnings("unused")
public interface PAAlarmRCARepository extends JpaRepository<PAAlarmRCA,Long> {
	
	List<PAAlarmRCA> findByPaorgarcIn(List<PAOrganization> paOrganization, Pageable pageable);
}

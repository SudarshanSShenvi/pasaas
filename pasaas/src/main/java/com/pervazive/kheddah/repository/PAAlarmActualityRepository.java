package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAAlarmActuality;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAAlarmActuality entity.
 */
@SuppressWarnings("unused")
public interface PAAlarmActualityRepository extends JpaRepository<PAAlarmActuality,Long> {

}

package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PARawAlarmData;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PARawAlarmData entity.
 */
@SuppressWarnings("unused")
public interface PARawAlarmDataRepository extends JpaRepository<PARawAlarmData,Long> {

}

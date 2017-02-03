package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PASchedulerInterval;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PASchedulerInterval entity.
 */
@SuppressWarnings("unused")
public interface PASchedulerIntervalRepository extends JpaRepository<PASchedulerInterval,Long> {

}

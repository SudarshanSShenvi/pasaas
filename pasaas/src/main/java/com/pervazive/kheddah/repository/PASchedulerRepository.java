package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAScheduler;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAScheduler entity.
 */
@SuppressWarnings("unused")
public interface PASchedulerRepository extends JpaRepository<PAScheduler,Long> {

}

package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAPrediction;

/**
 * Spring Data JPA repository for the PAPrediction entity.
 */
@SuppressWarnings("unused")
public interface PAPredictionRepository extends JpaRepository<PAPrediction,Long> {

	Page<PAPrediction> findByPaorgpreIn(List<PAOrganization> paOrganization, Pageable pageable);
}

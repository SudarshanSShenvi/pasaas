package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAPredictionScore;

/**
 * Spring Data JPA repository for the PAPredictionScore entity.
 */
@SuppressWarnings("unused")
public interface PAPredictionScoreRepository extends JpaRepository<PAPredictionScore,Long> {

	Page<PAPredictionScore> findByPaorgpsIn(List<PAOrganization> paOrganization, Pageable pageable);
}

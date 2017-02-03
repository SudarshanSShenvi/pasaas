package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAPredictionScore;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAPredictionScore entity.
 */
@SuppressWarnings("unused")
public interface PAPredictionScoreRepository extends JpaRepository<PAPredictionScore,Long> {

}

package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAPrediction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAPrediction entity.
 */
@SuppressWarnings("unused")
public interface PAPredictionRepository extends JpaRepository<PAPrediction,Long> {

}

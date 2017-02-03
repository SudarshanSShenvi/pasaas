package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAReliabilityScore;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAReliabilityScore entity.
 */
@SuppressWarnings("unused")
public interface PAReliabilityScoreRepository extends JpaRepository<PAReliabilityScore,Long> {

}

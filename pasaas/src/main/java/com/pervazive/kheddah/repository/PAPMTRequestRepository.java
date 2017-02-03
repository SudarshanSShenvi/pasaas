package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAPMTRequest;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAPMTRequest entity.
 */
@SuppressWarnings("unused")
public interface PAPMTRequestRepository extends JpaRepository<PAPMTRequest,Long> {

}

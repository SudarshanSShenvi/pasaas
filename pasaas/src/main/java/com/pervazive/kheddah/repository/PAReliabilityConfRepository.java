package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAReliabilityConf;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAReliabilityConf entity.
 */
@SuppressWarnings("unused")
public interface PAReliabilityConfRepository extends JpaRepository<PAReliabilityConf,Long> {

}

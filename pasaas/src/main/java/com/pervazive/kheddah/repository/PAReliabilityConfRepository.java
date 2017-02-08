package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAReliabilityConf;

/**
 * Spring Data JPA repository for the PAReliabilityConf entity.
 */
@SuppressWarnings("unused")
public interface PAReliabilityConfRepository extends JpaRepository<PAReliabilityConf,Long> {

	Page<PAReliabilityConf> findByPaorgrc(List<PAOrganization> paOrganization, Pageable pageable);
}

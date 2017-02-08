package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PADataConnector;
import com.pervazive.kheddah.domain.PAOrganization;

/**
 * Spring Data JPA repository for the PADataConnector entity.
 */
@SuppressWarnings("unused")
public interface PADataConnectorRepository extends JpaRepository<PADataConnector,Long> {
	Page<PADataConnector> findByPaorgdc(List<PAOrganization> paOrganization, Pageable pageable);
}

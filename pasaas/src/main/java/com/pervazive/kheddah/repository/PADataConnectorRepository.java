package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PADataConnector;
import com.pervazive.kheddah.domain.PAOrganization;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PADataConnector entity.
 */
@SuppressWarnings("unused")
public interface PADataConnectorRepository extends JpaRepository<PADataConnector,Long> {
	List<PADataConnector> findByPaorgdc(List<PAOrganization> paOrganization, Pageable pageable);
}

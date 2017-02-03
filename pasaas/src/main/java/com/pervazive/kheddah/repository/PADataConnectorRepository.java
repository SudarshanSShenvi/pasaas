package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PADataConnector;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PADataConnector entity.
 */
@SuppressWarnings("unused")
public interface PADataConnectorRepository extends JpaRepository<PADataConnector,Long> {

}

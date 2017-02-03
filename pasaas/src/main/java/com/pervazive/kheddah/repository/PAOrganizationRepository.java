package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAOrganization;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAOrganization entity.
 */
@SuppressWarnings("unused")
public interface PAOrganizationRepository extends JpaRepository<PAOrganization,Long> {

}

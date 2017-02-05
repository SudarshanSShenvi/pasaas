package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAOrganization entity.
 */
@SuppressWarnings("unused")
public interface PAOrganizationRepository extends JpaRepository<PAOrganization,Long> {
			@Query(value = "select distinct organization from PAOrganization organization left join fetch organization.pausers left join fetch organization.paproorgs ",
		    countQuery = "select count(organization) from PAOrganization organization")
		    Page<PAOrganization> findAllPAUsers(Pageable pageable);
}

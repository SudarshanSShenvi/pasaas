package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;
import java.lang.String;

/**
 * Spring Data JPA repository for the PAOrganization entity.
 */
@SuppressWarnings("unused")
public interface PAOrganizationRepository extends JpaRepository<PAOrganization,Long> {
			@Query(value = "select distinct organization from PAOrganization organization left join fetch organization.pausers left join fetch organization.paproorgs ",
		    countQuery = "select count(organization) from PAOrganization organization")
		    Page<PAOrganization> findAllPAUsers(Pageable pageable);
			
			@Query(value = "select distinct organization from PAOrganization organization left join fetch organization.pausers as pau where pau.login =?1")
			List<PAOrganization> findOrgsByPAUser(String pausers);
			
			Page<PAOrganization> findByOrganizationIn(List<PAOrganization> organization, Pageable page);
}

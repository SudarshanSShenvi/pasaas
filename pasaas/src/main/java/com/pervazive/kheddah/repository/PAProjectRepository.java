package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;

/**
 * Spring Data JPA repository for the PAProject entity.
 */
@SuppressWarnings("unused")
public interface PAProjectRepository extends JpaRepository<PAProject,Long> {
	@Query(value = "select distinct project from PAProject project left join fetch project.pausers",
		    countQuery = "select count(project) from PAProject project")
		    Page<PAProject> findAllPAUsers(Pageable pageable);

	@Query(value = "select distinct project from PAProject project where project.projectname = ?1")
	PAProject findByProjectName(String paproject);
	
	List<PAProject> findByPaorgproIn(List<PAOrganization> paOrganization, Pageable pageable);
}

package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAProject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAProject entity.
 */
@SuppressWarnings("unused")
public interface PAProjectRepository extends JpaRepository<PAProject,Long> {

}

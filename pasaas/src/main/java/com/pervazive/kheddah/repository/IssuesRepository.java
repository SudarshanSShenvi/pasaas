package com.pervazive.kheddah.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.Issues;

/**
 * Spring Data JPA repository for the Issues entity.
 */
@SuppressWarnings("unused")
public interface IssuesRepository extends JpaRepository<Issues,Long> {

}

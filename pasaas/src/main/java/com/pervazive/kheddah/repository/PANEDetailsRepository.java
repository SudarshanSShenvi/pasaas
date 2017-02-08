package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PANEDetails;
import com.pervazive.kheddah.domain.PAOrganization;

/**
 * Spring Data JPA repository for the PANEDetails entity.
 */
@SuppressWarnings("unused")
public interface PANEDetailsRepository extends JpaRepository<PANEDetails,Long> {

	Page<PANEDetails> findByPaorgnedIn(List<PAOrganization> paOrganization, Pageable pageable);
}

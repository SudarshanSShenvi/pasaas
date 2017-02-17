package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PASaxCode;

/**
 * Spring Data JPA repository for the PASaxCode entity.
 */
@SuppressWarnings("unused")
public interface PASaxCodeRepository extends JpaRepository<PASaxCode,Long> {

	Page<PASaxCode> findByPaorgscIn(List<PAOrganization> paOrganization, Pageable pageable);
	

}

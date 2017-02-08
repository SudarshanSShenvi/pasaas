package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PASaxCodeTmp;

/**
 * Spring Data JPA repository for the PASaxCodeTmp entity.
 */
@SuppressWarnings("unused")
public interface PASaxCodeTmpRepository extends JpaRepository<PASaxCodeTmp,Long> {

	Page<PASaxCodeTmp> findByPaorgsctIn(List<PAOrganization> paOrganization, Pageable pageable);
}

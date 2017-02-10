package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAReport;

/**
 * Spring Data JPA repository for the PAReport entity.
 */
@SuppressWarnings("unused")
public interface PAReportRepository extends JpaRepository<PAReport,Long> {

	Page<PAReport> findByPaorgrepIn(List<PAOrganization> paOrganization, Pageable pageable);
	
	//Page<PAReport> findByPaorgrep(PAOrganization paorgrep);
	
}

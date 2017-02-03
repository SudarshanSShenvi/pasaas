package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAReport;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAReport entity.
 */
@SuppressWarnings("unused")
public interface PAReportRepository extends JpaRepository<PAReport,Long> {

}

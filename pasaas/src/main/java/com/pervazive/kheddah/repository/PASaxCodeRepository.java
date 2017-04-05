package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.PASaxCode;
import java.lang.String;

/**
 * Spring Data JPA repository for the PASaxCode entity.
 */
@SuppressWarnings("unused")
public interface PASaxCodeRepository extends JpaRepository<PASaxCode,Long> {

	Page<PASaxCode> findByPaorgscIn(List<PAOrganization> paOrganization, Pageable pageable);
	
	Page<PASaxCode> findByPaorgsc(PAOrganization paOrganization, Pageable pageable);
	
	Page<PASaxCode> findByPaprosc(PAProject paProject, Pageable pageable);
	
	@Query(value="UPDATE pasaas.pa_saxcode a JOIN pasaas.pa_saxcode_tmp b ON a.distalarm = b.distalarm AND a.saxcode = b.saxcode SET a.total = a.total + b.total", nativeQuery = true) 
	List<PASaxCode> updateCustomResult();
	
	@Query(value="INSERT INTO pasaas.pa_saxcode (distalarm,saxcode,total) SELECT a.distalarm,a.saxcode,a.total FROM pasaas.pa_saxcode_tmp a WHERE (a.distalarm, a.saxcode) "
			+ "NOT IN (select distalarm, saxcode from pasaas.pa_saxcode)", nativeQuery = true)
	void addNewPatterns();
	

}

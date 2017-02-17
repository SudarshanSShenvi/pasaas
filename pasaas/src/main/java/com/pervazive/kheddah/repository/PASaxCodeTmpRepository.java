package com.pervazive.kheddah.repository;

import java.io.OutputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PASaxCodeTmp;

/**
 * Spring Data JPA repository for the PASaxCodeTmp entity.
 */
@SuppressWarnings("unused")
public interface PASaxCodeTmpRepository extends JpaRepository<PASaxCodeTmp,Long> {

	Page<PASaxCodeTmp> findByPaorgsctIn(List<PAOrganization> paOrganization, Pageable pageable);
	
	@Query(value = "load data local infile :filePath into table pasaas.pa_saxcode_tmp fields terminated by '\t' lines terminated by '\n' (distalarm, saxcode, total, painterval, @paorgsct_id, @paprosct_id, @pastatus)"
			+ " SET paorgsct_id = :orgId, paprosct_id = :projId, pastatus = 'Active'", nativeQuery = true)
	void saveCSV(@Param("filePath") String filePath, @Param("orgId") Long orgId, @Param("projId") Long projId);
}

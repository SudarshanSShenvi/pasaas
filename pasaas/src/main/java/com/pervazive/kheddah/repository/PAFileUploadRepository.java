package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pervazive.kheddah.domain.PAFileUpload;
import com.pervazive.kheddah.domain.PAOrganization;

/**
 * Spring Data JPA repository for the PAFileUpload entity.
 */
@SuppressWarnings("unused")
public interface PAFileUploadRepository extends JpaRepository<PAFileUpload,Long> {
	
	@Query(value = "select distinct fileupload from PAFileUpload fileupload Where fileupload.paorgfu.id = ?1")
	Page<PAFileUpload> findByPaorgfu(Pageable pageable, Long id);
	
	//@Query(value = "select distinct fileupload from PAFileUpload fileupload Where fileupload.paorgfu = ?1")
	Page<PAFileUpload> findByPaorgfuIn(List<PAOrganization> paOrganization, Pageable pageable);

}

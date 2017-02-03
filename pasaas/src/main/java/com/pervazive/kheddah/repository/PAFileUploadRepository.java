package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAFileUpload;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAFileUpload entity.
 */
@SuppressWarnings("unused")
public interface PAFileUploadRepository extends JpaRepository<PAFileUpload,Long> {

}

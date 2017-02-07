package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAErrorMessage;
import com.pervazive.kheddah.domain.PAOrganization;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAErrorMessage entity.
 */
@SuppressWarnings("unused")
public interface PAErrorMessageRepository extends JpaRepository<PAErrorMessage,Long> {
	List<PAErrorMessage> findByPaorgem(List<PAOrganization> paOrganization, Pageable pageable);
}

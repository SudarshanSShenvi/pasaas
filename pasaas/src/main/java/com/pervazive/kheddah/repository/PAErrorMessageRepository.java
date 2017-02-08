package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PAErrorMessage;
import com.pervazive.kheddah.domain.PAOrganization;

/**
 * Spring Data JPA repository for the PAErrorMessage entity.
 */
@SuppressWarnings("unused")
public interface PAErrorMessageRepository extends JpaRepository<PAErrorMessage,Long> {
	Page<PAErrorMessage> findByPaorgem(List<PAOrganization> paOrganization, Pageable pageable);
}

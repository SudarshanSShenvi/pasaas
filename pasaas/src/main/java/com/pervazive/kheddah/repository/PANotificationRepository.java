package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pervazive.kheddah.domain.PANotification;
import com.pervazive.kheddah.domain.PAOrganization;

/**
 * Spring Data JPA repository for the PANotification entity.
 */
@SuppressWarnings("unused")
public interface PANotificationRepository extends JpaRepository<PANotification,Long> {

	Page<PANotification> findByPaorgnotIn(List<PAOrganization> paOrganization, Pageable pageable);
}

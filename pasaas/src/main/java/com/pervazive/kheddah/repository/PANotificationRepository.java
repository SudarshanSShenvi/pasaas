package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PANotification;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PANotification entity.
 */
@SuppressWarnings("unused")
public interface PANotificationRepository extends JpaRepository<PANotification,Long> {

}

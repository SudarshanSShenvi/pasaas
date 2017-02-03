package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAErrorMessage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAErrorMessage entity.
 */
@SuppressWarnings("unused")
public interface PAErrorMessageRepository extends JpaRepository<PAErrorMessage,Long> {

}

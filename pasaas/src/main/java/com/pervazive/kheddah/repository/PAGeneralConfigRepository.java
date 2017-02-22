package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAGeneralConfig;
import com.pervazive.kheddah.domain.PAOrganization;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * Spring Data JPA repository for the PAGeneralConfig entity.
 */
@SuppressWarnings("unused")
public interface PAGeneralConfigRepository extends JpaRepository<PAGeneralConfig,Long> {

	PAGeneralConfig findByPastatus(PAStatus pastatus);
}

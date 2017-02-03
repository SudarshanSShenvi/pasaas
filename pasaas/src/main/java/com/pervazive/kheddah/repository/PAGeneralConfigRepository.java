package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAGeneralConfig;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAGeneralConfig entity.
 */
@SuppressWarnings("unused")
public interface PAGeneralConfigRepository extends JpaRepository<PAGeneralConfig,Long> {

}

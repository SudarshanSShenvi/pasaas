package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PAAccPrecision;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PAAccPrecision entity.
 */
@SuppressWarnings("unused")
public interface PAAccPrecisionRepository extends JpaRepository<PAAccPrecision,Long> {

}

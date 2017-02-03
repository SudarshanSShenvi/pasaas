package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PASaxCode;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PASaxCode entity.
 */
@SuppressWarnings("unused")
public interface PASaxCodeRepository extends JpaRepository<PASaxCode,Long> {

}

package com.pervazive.kheddah.repository;

import com.pervazive.kheddah.domain.PANEDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PANEDetails entity.
 */
@SuppressWarnings("unused")
public interface PANEDetailsRepository extends JpaRepository<PANEDetails,Long> {

}

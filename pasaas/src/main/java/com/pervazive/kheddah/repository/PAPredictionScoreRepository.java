package com.pervazive.kheddah.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAPredictionScore;
import com.pervazive.kheddah.domain.PAProject;

import java.lang.Float;

/**
 * Spring Data JPA repository for the PAPredictionScore entity.
 */
@SuppressWarnings("unused")
public interface PAPredictionScoreRepository extends JpaRepository<PAPredictionScore,Long> {

	Page<PAPredictionScore> findByPaorgpsIn(List<PAOrganization> paOrganization, Pageable pageable);
	
	Page<PAPredictionScore> findByPaorgps(PAOrganization paOrganization, Pageable pageable);
	
	Page<PAPredictionScore> findByPaprops(PAProject paProject, Pageable pageable);
	
	@Query(value = "SELECT paPredictionScore FROM PAPredictionScore paPredictionScore where paPredictionScore.cscore != ?1 AND paPredictionScore.paprops.id = ?2")
	Page<PAPredictionScore> findCscorePredictions(Float cVal, Long paProject, Pageable pageable);
	
	@Query(value = "SELECT paPredictionScore FROM PAPredictionScore paPredictionScore where paPredictionScore.bscore != ?1 AND paPredictionScore.paprops.id = ?2")
	Page<PAPredictionScore> findBscorePredictions(Float cVal, Long paProject, Pageable pageable);
	
	@Query(value = "INSERT INTO pasaas.pa_prediction_score (dist,alarmno,bcount,ccount,bscore,cscore,createdon,severity) "
			+ "SELECT SUBSTRING_INDEX(AA.distalarm,'_',1), SUBSTRING_INDEX(AA.distalarm,'_',-1), AA.b AS bCount, BB.c AS cCount, "
			+ "ROUND(IFNULL((AA.b / (AA.b + BB.c)), 0), 3) AS bScore, ROUND(IFNULL((BB.c / (AA.b + BB.c)), 0), 3) AS cScore, now(), AA.severity "
			+ "FROM "
			+ "( SELECT b.distalarm, b.saxcode, IFNULL(a.total,0) AS b, b.severity as severity FROM pasaas.pa_saxcode AS a "
			+ "RIGHT OUTER JOIN pasaas.pa_saxcode_tmp b ON a.distalarm = b.distalarm AND a.saxcode = CONCAT(b.saxcode, 'b')  ) "
			+ "AS AA JOIN "
			+ "( SELECT b.distalarm, b.saxcode, IFNULL(a.total,0) AS c FROM pasaas.pa_saxcode AS a "
			+ "RIGHT OUTER JOIN pasaas.pa_saxcode_tmp b ON a.distalarm = b.distalarm AND a.saxcode = CONCAT(b.saxcode, 'c'))"
			+ " AS BB where AA.distalarm = BB.distalarm and AA.saxcode = BB.saxcode", nativeQuery = true)
	void createPredictionsForDay();
	
	//@Query("select paPredictionScore from PAPredictionScore paPredictionScore where paPredictionScore.cscore>?1")
	Page<PAPredictionScore> findByCscoreBetween(Float cscorestart, Float cscoreend, Pageable pageable);
	
	//@Query("select paPredictionScore from PAPredictionScore paPredictionScore where paPredictionScore.bscore>?1")
	Page<PAPredictionScore> findByBscoreBetween(Float bscorestart, Float bscoreend, Pageable pageable);
}

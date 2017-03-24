package com.pervazive.kheddah.web.rest;

import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.spark.SparkConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.service.PAProjectService;
import com.pervazive.kheddah.service.SparkOperationsService;

/**
 * REST controller for managing PANotification.
 */
@RestController
@RequestMapping("/api")
public class SparkOpsResource {
	
	@Inject
	SparkOperationsService sparkOperationsService;
	
	@Inject
	private PAProjectService paProjectService;
	
    private final Logger log = LoggerFactory.getLogger(SparkOpsResource.class);
 
    @PostMapping("/triggerTrainingOps/{projectName}")
    @Timed
    public ResponseEntity<String> triggerTrainingOps(@PathVariable String projectName, HttpServletRequest request)
        throws Exception {
    	SparkConf sparkConf = sparkOperationsService.setSparkConfigurations();
    	PAProject paProject = paProjectService.findProjectByName(projectName);
    	sparkOperationsService.triggerTrainingOperation(1, sparkConf, sparkOperationsService.setHadoopConfigurations(), projectName, paProject.getPaorgpro().getOrganization());
    	return new ResponseEntity<>("DA Job submitted", HttpStatus.OK);
    }
    
    @PostMapping("/triggerPredictionOps/{projectName}")
    @Timed
    public ResponseEntity<String> triggerPredictionOps(@PathVariable String projectName, HttpServletRequest request)
        throws URISyntaxException {
    	SparkConf sparkConf = sparkOperationsService.setSparkConfigurations();
    	PAProject paProject = paProjectService.findProjectByName(projectName);
    	sparkOperationsService.triggerPredictionOperation(1,sparkConf, sparkOperationsService.setHadoopConfigurations(), projectName,  paProject.getPaorgpro().getOrganization());
    	return new ResponseEntity<>("DA Job submitted", HttpStatus.OK);
    }
 
}

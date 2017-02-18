package com.pervazive.kheddah.web.rest;

import java.net.URISyntaxException;

import javax.inject.Inject;

import org.apache.spark.SparkConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.pervazive.kheddah.service.SparkOperationsService;

/**
 * REST controller for managing PANotification.
 */
@RestController
@RequestMapping("/api")
public class SparkOpsResource {
	
	@Inject
	SparkOperationsService sparkOperationsService;
	
    private final Logger log = LoggerFactory.getLogger(SparkOpsResource.class);
 
    @PostMapping("/triggerTrainingOps")
    @Timed
    public ResponseEntity<String> triggerTrainingOps()
        throws URISyntaxException {
    	
    	SparkConf sparkConf = sparkOperationsService.setSparkConfigurations();
    	sparkOperationsService.triggerTrainingOperation(1, "hdfs://spark:8020/ppa-repo/fmdata", "13:6", "30", 
    			"CUSTOMDATE#yyyy-MM-dd HH:mm:ss", "UNIXTIME", "0,1,2,3,4,5,7,15,16,17,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55",
    			true, "hdfs://spark:8020/ppa-repo/conf/expression.txt", "2015-03-02 00:00:00", "2015-03-31 00:00:00", "2015-03-01 00:00:00", 
    			"CUSTOMDATE#yyyy-MM-dd HH:mm:ss", "CUSTOMDATE#yyyy-MM-dd HH:mm:ss", "hdfs://spark:8020/ppa-repo/fmdatafeed", "2", 
    			"hdfs://spark:8020/ppa-repo/temp/TD", "hdfs://spark:8020/ppa-repo/temp/5DW", "2", "5", "0", sparkConf, sparkOperationsService.setHadoopConfigurations());
    	return new ResponseEntity<>("DA Job submitted", HttpStatus.OK);
    }
    
    @PostMapping("/triggerPredictionOps")
    @Timed
    public ResponseEntity<String> triggerPredictionOps()
        throws URISyntaxException {
    	
    	SparkConf sparkConf = sparkOperationsService.setSparkConfigurations();
    	sparkOperationsService.triggerPredictionOperation(1, "hdfs://spark:8020/ppa-repo/temp/4DD", "13:6", "30", 
    			"CUSTOMDATE#yyyy-MM-dd HH:mm:ss", "UNIXTIME", "0,1,2,3,4,5,7,15,16,17,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55",
    			true, "hdfs://spark:8020/ppa-repo/conf/expression.txt", "2015-03-29 00:00:00", "2015-03-31 00:00:00", "2015-03-28 00:00:00", 
    			"CUSTOMDATE#yyyy-MM-dd HH:mm:ss", "CUSTOMDATE#yyyy-MM-dd HH:mm:ss", "hdfs://spark:8020/ppa-repo/temp/4DDfeed", "2", 
    			"hdfs://spark:8020/ppa-repo/temp/TD4", "hdfs://spark:8020/ppa-repo/temp/4DW", "2", "4", "0", sparkConf, sparkOperationsService.setHadoopConfigurations());
    	return new ResponseEntity<>("DA Job submitted", HttpStatus.OK);
    }
 
}

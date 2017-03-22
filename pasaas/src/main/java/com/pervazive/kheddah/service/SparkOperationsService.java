package com.pervazive.kheddah.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;

/**
 * Service Interface for managing PAScheduler.
 */
public interface SparkOperationsService {
	
	public void triggerTrainingOperation(long predictionId, SparkConf sparkConf, Configuration hadoopConf, String projectName, String organization  ) throws Exception ;
	
	public void triggerPredictionOperation(long predictionId,  SparkConf sparkConf, Configuration hadoopConf, String projectName, String organization  ) ;
	public Configuration setHadoopConfigurations();
	public SparkConf setSparkConfigurations();
	
	public void removeSparkConfigurations(SparkConf sparkConf);
}

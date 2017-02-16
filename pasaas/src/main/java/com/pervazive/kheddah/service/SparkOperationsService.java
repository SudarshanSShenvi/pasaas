package com.pervazive.kheddah.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;

/**
 * Service Interface for managing PAScheduler.
 */
public interface SparkOperationsService {
	
	public void triggerDataAggregation(String predictionId, String inputDir, String entityCol, String timeCol,
			  String sourceTimeFormat, String destTimeFormat, String skipindexes, Boolean isFirstRowHeader, SparkConf sparkConf, Configuration hadoopConf) ;
	
	public void triggerDataRollup(long predictionId, String exprFile, String seriesNext, String seriesEnd, String seriesStart,
			String outSeriesFormat, String inSeriesFormat, String inputFile, String requiredFlds,  SparkConf sparkConf, Configuration hadoopConf);
	
	 public void triggerPatterns(String inputFile, String outputFile, String saxcodeField, String subSeqInterval,
				String subSeqIntervalThreshold, long predictionId, SparkConf sparkConf, Configuration hadoopConf );
	 
	 public void triggerUpload(Configuration hadoopConf );
	
	public Configuration setHadoopConfigurations();
	public SparkConf setSparkConfigurations();
	
	public void removeSparkConfigurations(SparkConf sparkConf);
}

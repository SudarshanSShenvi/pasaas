package com.pervazive.kheddah.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;

/**
 * Service Interface for managing PAScheduler.
 */
public interface SparkOperationsService {
	
	public void triggerTrainingOperation(long predictionId, String inputDir, String entityCol, String timeCol,
			  String sourceTimeFormat, String destTimeFormat, String skipindexes, Boolean isFirstRowHeader, String exprFile, String seriesNext, String seriesEnd, String seriesStart,
				String outSeriesFormat, String inSeriesFormat, String inputFile, String requiredFlds, String patInputFile, String outputFile, String saxcodeField, String subSeqInterval,
				String subSeqIntervalThreshold, SparkConf sparkConf, Configuration hadoopConf) ;
	
	public void triggerPredictionOperation(long predictionId, String inputDir, String entityCol, String timeCol,
				  String sourceTimeFormat, String destTimeFormat, String skipindexes, Boolean isFirstRowHeader, String exprFile, String seriesNext, String seriesEnd, String seriesStart,
					String outSeriesFormat, String inSeriesFormat, String inputFile, String requiredFlds, String patInputFile, String outputFile, String saxcodeField, String subSeqInterval,
					String subSeqIntervalThreshold, SparkConf sparkConf, Configuration hadoopConf) ;
	public Configuration setHadoopConfigurations();
	public SparkConf setSparkConfigurations();
	
	public void removeSparkConfigurations(SparkConf sparkConf);
}

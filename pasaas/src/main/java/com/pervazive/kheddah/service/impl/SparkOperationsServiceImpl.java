package com.pervazive.kheddah.service.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pervazive.kheddah.paml.DataAggregator;
import com.pervazive.kheddah.paml.DataRollup;
import com.pervazive.kheddah.paml.SubSequenceGenerator;
import com.pervazive.kheddah.repository.PASaxCodeTmpRepository;
import com.pervazive.kheddah.service.HDFSFileOperationsService;
import com.pervazive.kheddah.service.PASaxCodeService;
import com.pervazive.kheddah.service.SparkOperationsService;

@Service
@Transactional
public class SparkOperationsServiceImpl implements SparkOperationsService {
	
    private final Logger log = LoggerFactory.getLogger(SparkOperationsServiceImpl.class);
	
	@Inject
	HDFSFileOperationsService hdfsFileOperationsService;
	
	@Inject
	PASaxCodeService paSaxCodeService;
	
	@Inject
	PASaxCodeTmpRepository paSaxCodeTmpRepository;
	
	public static Configuration hadoopConf = null;
	
	public SparkOperationsServiceImpl() {
	}
	
	public Configuration setHadoopConfigurations(){
		if(hadoopConf == null) {
			hadoopConf = new Configuration();
			hadoopConf.set("fs.defaultFS", "hdfs://spark:8020");
		}
		return hadoopConf;
	}
	
	public SparkConf setSparkConfigurations(){
		
			SparkConf sparkConf = new SparkConf();
			sparkConf.setMaster("spark://10.10.10.176:7077");
			sparkConf.set("spark.default.parallelism", "24");
			/*sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
			sparkConf.set("spark.kryoserializer.buffer.mb", "1024");
			sparkConf.set("spark.akka.frameSize", "2047");*/
			//sparkConf.set("spark.executor.cores", "2");
			sparkConf.set("spark.ui.enabled", "false");
			sparkConf.set("spark.executor.memory", "8G");
			sparkConf.set("spark.driver.memory", "8G");
			sparkConf.set("spark.driver.allowMultipleContexts", "true");
			String[] jar = {"/opt/pasaas-product/Apache-Spark/spark-2.1.0-bin-hadoop2.7/jars/paml-1.0.jar"
					,"/opt/pasaas-product/Apache-Spark/spark-2.1.0-bin-hadoop2.7/jars/commons-jexl-2.1.1.jar"
			};
			
			sparkConf.setJars(jar);
			return sparkConf;
	}
	
	public void removeSparkConfigurations(SparkConf sparkConf){
		sparkConf = null;
	}
	
	  public void triggerTrainingOperation(long predictionId, String inputDir, String entityCol, String timeCol,
			  String sourceTimeFormat, String destTimeFormat, String skipindexes, Boolean isFirstRowHeader, String exprFile, String seriesNext, String seriesEnd, String seriesStart,
				String outSeriesFormat, String inSeriesFormat, String inputFile, String requiredFlds, String patInputFile, String outputFile, String saxcodeField, String subSeqInterval,
				String subSeqIntervalThreshold, SparkConf sparkConf, Configuration hadoopConf ) {
		  
		  //Read Items from Configuration
		  // GET Organization and Project Name as parameters here
		   ExecutorService executorService = Executors.newFixedThreadPool(8);
		    // Start thread 1
		    Future<Long> future1 = executorService.submit(new Callable<Long>() {
		        @Override
		        public Long call() throws Exception {
		        	DataAggregator dataAggregator = new DataAggregator(predictionId, isFirstRowHeader, skipindexes, 
		  				sourceTimeFormat, destTimeFormat, timeCol, entityCol, inputDir);
		  		  		hdfsFileOperationsService.deleteFile("/ppa-repo/fmdatafeed", hadoopConf);
		  				dataAggregator.init(sparkConf, "Training - data aggregation");
		  				
		  				hdfsFileOperationsService.deleteFile("/ppa-repo/fmdatafeed/8.64E7", hadoopConf);
		  				DataRollup dataRollup = new DataRollup(predictionId, exprFile, seriesNext, seriesEnd, seriesStart, outSeriesFormat, inSeriesFormat, inputFile, requiredFlds);
		  		  		dataRollup.init(sparkConf, "Training - data rollup");
		  		  	hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/TD", hadoopConf);
					hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/5DW", hadoopConf);
					hdfsFileOperationsService.mkdir("hdfs://spark:8020/ppa-repo/temp/TD", hadoopConf);
					hdfsFileOperationsService.copyHdfsFile("hdfs://spark:8020/ppa-repo/fmdatafeed/8.64E7/", "hdfs://spark:8020/ppa-repo/temp/TD", hadoopConf);
					hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/TD/_SUCCESS", hadoopConf);
		        	SubSequenceGenerator subSequenceGenerator = new SubSequenceGenerator(patInputFile, outputFile, saxcodeField, subSeqInterval, subSeqIntervalThreshold, predictionId);
		        	subSequenceGenerator.run(sparkConf, "Training - Patterns");
		        	String path = hdfsFileOperationsService.passFileForUpload("hdfs://spark:8020/ppa-repo/temp/5DW/part-00000", hadoopConf).getAbsolutePath();
		        	log.debug("in here wiht "+path);
		        	paSaxCodeTmpRepository.deleteAllInBatch();
		        	log.debug("Done with Deletion "+path);
		        	paSaxCodeTmpRepository.saveCSV(path, 1L, 1L);
		  				return 0L;
		  		}
		    });
		    
		   
	  }
	  
	 
	  
	  public void triggerPredictionOperation(long predictionId, String inputDir, String entityCol, String timeCol,
			  String sourceTimeFormat, String destTimeFormat, String skipindexes, Boolean isFirstRowHeader, String exprFile, String seriesNext, String seriesEnd, String seriesStart,
				String outSeriesFormat, String inSeriesFormat, String inputFile, String requiredFlds, String patInputFile, String outputFile, String saxcodeField, String subSeqInterval,
				String subSeqIntervalThreshold, SparkConf sparkConf, Configuration hadoopConf ) {
		  
		  //Read Items from Configuration
		  // GET Organization and Project Name as parameters here
		  
		   ExecutorService executorService = Executors.newFixedThreadPool(8);
		    // Start thread 1
		    Future<Long> future1 = executorService.submit(new Callable<Long>() {
		        @Override
		        public Long call() throws Exception {
		        	DataAggregator dataAggregator = new DataAggregator(predictionId, isFirstRowHeader, skipindexes, 
				  				  sourceTimeFormat, destTimeFormat, timeCol, entityCol, inputDir);
				  		  	hdfsFileOperationsService.deleteFile("/ppa-repo/temp/4DDfeed", hadoopConf);
				  			dataAggregator.init(sparkConf, "Prediction - data aggregation");
				  			
				  			hdfsFileOperationsService.deleteFile("/ppa-repo/temp/4DDfeed/8.64E7", hadoopConf);
				  			DataRollup dataRollup = new DataRollup(predictionId, exprFile, seriesNext, seriesEnd, seriesStart, outSeriesFormat, inSeriesFormat, inputFile, requiredFlds);
				  		  	dataRollup.init(sparkConf, "Prediction - data rollup");
				  		  		
				  		  	hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/TD4", hadoopConf);
							hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/4DW", hadoopConf);
							hdfsFileOperationsService.mkdir("hdfs://spark:8020/ppa-repo/temp/TD4", hadoopConf);
							hdfsFileOperationsService.copyHdfsFile("hdfs://spark:8020/ppa-repo/temp/4DDfeed/8.64E7/", "hdfs://spark:8020/ppa-repo/temp/TD4", hadoopConf);
							hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/TD4/_SUCCESS", hadoopConf);
							SubSequenceGenerator subSequenceGenerator1 = new SubSequenceGenerator(patInputFile, outputFile, saxcodeField, 
									subSeqInterval, subSeqIntervalThreshold, predictionId);
				        	subSequenceGenerator1.run(sparkConf, "Prediction - patterns");
				        	String path = hdfsFileOperationsService.passFileForUpload("hdfs://spark:8020/ppa-repo/temp/4DW/part-00000", hadoopConf).getAbsolutePath();
				        	log.debug("in here wiht "+path);
				        	//paSaxCodeTmpRepository.saveCSV(path, 1L, 1L);
		  				return 0L;
		  		  
		        }
		    });
		    
		   
	  }
	  
	  

}

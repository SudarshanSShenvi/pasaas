package com.pervazive.kheddah.service.impl;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
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

import com.pervazive.kheddah.domain.PAGeneralConfig;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.paml.DataAggregator;
import com.pervazive.kheddah.paml.DataRollup;
import com.pervazive.kheddah.paml.SubSequenceGenerator;
import com.pervazive.kheddah.repository.PAPredictionScoreRepository;
import com.pervazive.kheddah.repository.PASaxCodeRepository;
import com.pervazive.kheddah.repository.PASaxCodeTmpRepository;
import com.pervazive.kheddah.service.HDFSFileOperationsService;
import com.pervazive.kheddah.service.PAGeneralConfigService;
import com.pervazive.kheddah.service.PAProjectService;
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
	
	@Inject
	PASaxCodeRepository paSaxCodeRepository;
	
	@Inject
	PAPredictionScoreRepository paPredictionScoreRepository;
	
	@Inject
	PAProjectService paProjectService;
	
	@Inject
	PAGeneralConfigService paGeneralConfigService;
	
	
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
	
	  public void triggerTrainingOperation(long predictionId, SparkConf sparkConf, Configuration hadoopConf, String projectName, String organization   ) {
		   ExecutorService executorService = Executors.newFixedThreadPool(8);
		   Future<Long> future1 = executorService.submit(new Callable<Long>() {
		        @Override
		        public Long call() throws Exception {
		        	PAProject paProject = paProjectService.findProjectByName(projectName);
		        	PAGeneralConfig  paGeneralConfig = paGeneralConfigService.findByStatus();
		        	String baseURL = paGeneralConfig.getHdfsurl()+"/"+paGeneralConfig.getHdfsbasedir()+"/"+organization+"/"+projectName;
		        	
		        	DataAggregator dataAggregator = new DataAggregator(1, paProject.getFeedfirstlineheader(), paProject.getFeedskipindexes(), 
		        			paProject.getFeedoutdateformat(), paProject.getFeedindateformat(),  paProject.getTimeseriesentity(), 
		        			paProject.getObjectentity(), baseURL+paGeneralConfig.getTrainingfeeddir(), baseURL + paGeneralConfig.getTrainingrollupinfile());
		        	hdfsFileOperationsService.deleteFile(baseURL+paGeneralConfig.getTrainingrollupinfile(), hadoopConf);
		  			dataAggregator.init(sparkConf, "Training - data aggregation");

		  			hdfsFileOperationsService.deleteFile(baseURL+paGeneralConfig.getTrainingrollupinfile()+"/8.64E7", hadoopConf);
		  			
		  			
		  			log.debug("===========> DATA ROLLUP PARMS ");
		  			log.debug("===========> "+baseURL+paGeneralConfig.getExpressionfilepath()+"/expression.txt");
		  			log.debug("===========> "+paProject.getRollseriesnxt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		  			log.debug("===========> "+paProject.getRollseriesstart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		  			log.debug("===========> "+paProject.getRollseriesend().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		  			log.debug("===========> "+paProject.getRollindateformat());
		  			log.debug("===========> "+baseURL+paGeneralConfig.getTrainingrollupinfile());
		  			log.debug("===========> "+paProject.getRollseriesgroupindex());
		  			
		  			
		  			
		  			
		  			
		  			DataRollup dataRollup = new DataRollup(1, baseURL+paGeneralConfig.getExpressionfilepath()+"/expression.txt", 
		  					paProject.getRollseriesnxt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				  			paProject.getRollseriesend().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), 
				  			paProject.getRollseriesstart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
		  					paProject.getRolloutdateformat(), paProject.getRollindateformat(), baseURL+paGeneralConfig.getTrainingrollupinfile(), paProject.getRollseriesgroupindex());
	  		  		dataRollup.init(sparkConf, "Training - data rollup");
		  		  		
		  		  	hdfsFileOperationsService.deleteFile(baseURL+paGeneralConfig.getTrainingpatternoutfile(), hadoopConf);
					hdfsFileOperationsService.deleteFile(baseURL+paGeneralConfig.getTrainingpatterntmpoutfile(), hadoopConf);
					hdfsFileOperationsService.mkdir(baseURL+paGeneralConfig.getTrainingpatternoutfile(), hadoopConf);
					hdfsFileOperationsService.copyHdfsFile(baseURL+paGeneralConfig.getTrainingrollupinfile()+"/8.64E7/", baseURL+paGeneralConfig.getTrainingpatternoutfile(), hadoopConf);
					hdfsFileOperationsService.deleteFile(baseURL+paGeneralConfig.getTrainingpatternoutfile()+"/_SUCCESS", hadoopConf);
		        	
					SubSequenceGenerator subSequenceGenerator = new SubSequenceGenerator(baseURL+paGeneralConfig.getTrainingpatternoutfile(), baseURL+paGeneralConfig.getTrainingpatterntmpoutfile(), 
							paProject.getPatternfldindex().toString(), paProject.getPatterntraininterval().toString(), paProject.getPatternintervalthreshhold().toString(), 1L);
					
		        	subSequenceGenerator.run(sparkConf, "Training - Patterns");
		        	String path = hdfsFileOperationsService.passFileForUpload(baseURL+paGeneralConfig.getTrainingpatterntmpoutfile()+"/part-00000", hadoopConf).getAbsolutePath();
		        	log.debug("Attempting Delete ");
		        	paSaxCodeTmpRepository.deleteAllInBatch();
		        	log.debug("Done with Deletion ");
		        	paSaxCodeTmpRepository.saveCSV(path, 1L, 1L);
		        	paSaxCodeRepository.updateCustomResult();
		        	paSaxCodeRepository.addNewPatterns();
		  			return 0L;
		  		}
		    });
		    
		   
	  }
	  
	 
	  
	  public void triggerPredictionOperation(long predictionId, SparkConf sparkConf, Configuration hadoopConf, String projectName, String organization  ) {
		  
		  ExecutorService executorService = Executors.newFixedThreadPool(8);
		    Future<Long> future1 = executorService.submit(new Callable<Long>() {
		        @Override
		        public Long call() throws Exception {
		        			        	
		        	PAProject paProject = paProjectService.findProjectByName(projectName);
		        	PAGeneralConfig  paGeneralConfig = paGeneralConfigService.findByStatus();
		        	String baseURL = paGeneralConfig.getHdfsurl()+"/"+paGeneralConfig.getHdfsbasedir()+"/"+organization+"/"+projectName;
		        	
		        	log.debug("===========> DATA ROLLUP PARMS ");
		  			log.debug("===========> "+baseURL+paGeneralConfig.getExpressionfilepath()+"/expression.txt");
		  			log.debug("===========> "+paProject.getRollseriesnxt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		  			log.debug("===========> "+paProject.getRollseriesstart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		  			log.debug("===========> "+paProject.getRollseriesend().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		  			log.debug("===========> "+paProject.getRollindateformat());
		  			log.debug("===========> "+baseURL+paGeneralConfig.getTrainingrollupinfile());
		  			log.debug("===========> "+paProject.getRollseriesgroupindex());
		        	
		        	DataAggregator dataAggregator = new DataAggregator(1, paProject.getFeedfirstlineheader(), paProject.getFeedskipindexes(), 
		        			paProject.getFeedoutdateformat(), paProject.getFeedindateformat(),  paProject.getTimeseriesentity(), 
		        			paProject.getObjectentity(), baseURL+paGeneralConfig.getPredictfeeddir(), baseURL + paGeneralConfig.getPredictrollupinfile());
		        	
				  		  	hdfsFileOperationsService.deleteFile(baseURL+paGeneralConfig.getPredictrollupinfile(), hadoopConf);
				  			dataAggregator.init(sparkConf, "Prediction - data aggregation");
				  			hdfsFileOperationsService.deleteFile(baseURL+paGeneralConfig.getPredictrollupinfile()+"/8.64E7", hadoopConf);
				  			
				  			DataRollup dataRollup = new DataRollup(1, baseURL+paGeneralConfig.getExpressionfilepath()+"/expression.txt", 
				  					paProject.getRollseriesnxt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						  			paProject.getRollseriesend().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), 
						  			paProject.getRollseriesstart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				  					paProject.getRolloutdateformat(), paProject.getRollindateformat(), baseURL+paGeneralConfig.getPredictrollupinfile(), paProject.getRollseriesgroupindex());
				  		  	dataRollup.init(sparkConf, "Prediction - data rollup");
				  		  		
				  		  	hdfsFileOperationsService.deleteFile(baseURL+paGeneralConfig.getPredictpatternoutfile(), hadoopConf);
							hdfsFileOperationsService.deleteFile(baseURL+paGeneralConfig.getPredictpatterntmpoutfile(), hadoopConf);
							hdfsFileOperationsService.mkdir(baseURL+paGeneralConfig.getPredictpatternoutfile(), hadoopConf);
							hdfsFileOperationsService.copyHdfsFile(baseURL+paGeneralConfig.getPredictrollupinfile()+"/8.64E7/", baseURL+paGeneralConfig.getPredictpatternoutfile(), hadoopConf);
							hdfsFileOperationsService.deleteFile(baseURL+paGeneralConfig.getPredictpatternoutfile()+"/_SUCCESS", hadoopConf);
							
							SubSequenceGenerator subSequenceGenerator = new SubSequenceGenerator(baseURL+paGeneralConfig.getPredictpatternoutfile(), baseURL+paGeneralConfig.getPredictpatterntmpoutfile(), 
									paProject.getPatternfldindex().toString(), paProject.getPatternpredictinterval().toString(), paProject.getPatternintervalthreshhold().toString(), 1L);
				        	subSequenceGenerator.run(sparkConf, "Prediction - patterns");
				        	
				        	String path = hdfsFileOperationsService.passFileForUpload(baseURL+paGeneralConfig.getPredictpatterntmpoutfile()+"/part-00000", hadoopConf).getAbsolutePath();
				        	paSaxCodeTmpRepository.deleteAllInBatch();
				        	paSaxCodeTmpRepository.saveCSV(path, 1L, 1L);
				        	log.debug("Attempting New Prediction creations ");
				        	paPredictionScoreRepository.createPredictionsForDay();
				        	return 0L;
		        }
		    });
	  }
}

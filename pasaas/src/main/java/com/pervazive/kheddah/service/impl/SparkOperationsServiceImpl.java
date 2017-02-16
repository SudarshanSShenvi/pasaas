package com.pervazive.kheddah.service.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.PASaxCode;
import com.pervazive.kheddah.domain.enumeration.PAStatus;
import com.pervazive.kheddah.paml.DataAggregator;
import com.pervazive.kheddah.paml.DataRollup;
import com.pervazive.kheddah.paml.SubSequenceGenerator;
import com.pervazive.kheddah.service.HDFSFileOperationsService;
import com.pervazive.kheddah.service.PASaxCodeService;
import com.pervazive.kheddah.service.SparkOperationsService;

@Service
@Transactional
public class SparkOperationsServiceImpl implements SparkOperationsService {
	
	@Inject
	HDFSFileOperationsService hdfsFileOperationsService;
	
	@Inject
	PASaxCodeService paSaxCodeService;
	
	public static Configuration hadoopConf = null;
	

	public SparkOperationsServiceImpl() {
		
	}
	
	/**
	 *SparkConf conf = new SparkConf();
		conf.setMaster("local");
		conf.set("spark.default.parallelism", "24");
		conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");
		conf.set("spark.kryoserializer.buffer.mb", "1024");
		conf.set("spark.akka.frameSize", "2047");
		conf.set("spark.driver.allowMultipleContexts", "true");
		
		
		SparkConf tmpConf = conf;
		SparkConf tmpConf1 = conf;
		SparkConf tmpConf2 = conf;
		
		
		DataAggregator dataAggregator = new DataAggregator();
		String parms[] = {"1", "hdfs://spark:8020/ppa-repo/fmdata","13:6", "30", "UNIXTIME", "CUSTOMDATE#yyyy-MM-dd HH:mm:ss", "0,1,2,3,4,5,7,15,16,17,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55", "true"};
		
		DataRollup dataRollup = new DataRollup();
		String parms2[] = {"1", "hdfs://spark:8020/ppa-repo/fmdatafeed","CUSTOMDATE#yyyy-MM-dd HH:mm:ss", "CUSTOMDATE#yyyy-MM-dd HH:mm:ss",
				"2015-03-01 00:00:00", "2015-03-31 23:59:59", "2015-03-02 23:59:59", "hdfs://spark:8020/ppa-repo/conf/expression.txt", "2"};
		
		SubSequenceGenerator subSequenceGenerator = new SubSequenceGenerator();
		
		String parms3[] = {"1", "hdfs://spark:8020/ppa-repo/temp/TD", "hdfs://spark:8020/ppa-repo/temp/5DW", "2", "5", "0"};
		
			try {
				hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/fmdatafeed", configuration);
				dataAggregator.init(parms, tmpConf);
				System.out.println("Waiting NOW ------------------------------");
				Thread.sleep(5000);
				System.out.println("Starting NOW ------------------------------");
				//dataRollup.init(parms2, tmpConf1);
				//hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/TD", configuration);
				//hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/5DW", configuration);
				//hdfsFileOperationsService.mkdir("hdfs://spark:8020/ppa-repo/temp/TD", configuration);
				//hdfsFileOperationsService.copyHdfsFile("hdfs://spark:8020/ppa-repo/fmdatafeed/1.72799E8/", "hdfs://spark:8020/ppa-repo/temp/TD", configuration);
				//hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/TD/_SUCCESS", configuration);
				System.out.println("Waiting AGAIN NOW ------------------------------");
				Thread.sleep(5000);
				System.out.println("Starting Again NOW ------------------------------");
				//subSequenceGenerator.run(parms3, tmpConf2);
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			*/
	
	/**
	   */
	// String parms[] = {"1", "hdfs://spark:8020/ppa-repo/fmdata","13:6", "30", "UNIXTIME", "CUSTOMDATE#yyyy-MM-dd HH:mm:ss", 
	// "0,1,2,3,4,5,7,15,16,17,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55", "true"};
	
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
			//sparkConf.set("spark.ui.enabled", "false");
			sparkConf.set("spark.executor.memory", "8G");
			sparkConf.set("spark.driver.memory", "8G");
			sparkConf.set("spark.driver.allowMultipleContexts", "true");
			String[] jar = {"/opt/pasaas-product/Apache-Spark/spark-2.1.0-bin-hadoop2.7/jars/pasaas.jar"
					,"/opt/pasaas-product/Apache-Spark/spark-2.1.0-bin-hadoop2.7/jars/commons-jexl-2.1.1.jar"
			};
			
			sparkConf.setJars(jar);
			return sparkConf;
	}
	
	public void removeSparkConfigurations(SparkConf sparkConf){
		sparkConf = null;
	}
	
	  public void triggerDataAggregation(String predictionId, String inputDir, String entityCol, String timeCol,
			  String sourceTimeFormat, String destTimeFormat, String skipindexes, Boolean isFirstRowHeader, SparkConf sparkConf, Configuration hadoopConf ) {
		  
		  //Read Items from Configuration
		  // GET Organization and Project Name as parameters here
		  
		   ExecutorService executorService = Executors.newFixedThreadPool(8);
		    // Start thread 1
		    Future<Long> future1 = executorService.submit(new Callable<Long>() {
		        @Override
		        public Long call() throws Exception {
		        	DataAggregator dataAggregator = new DataAggregator(Long.parseLong(predictionId), isFirstRowHeader, skipindexes, 
		  				  sourceTimeFormat, destTimeFormat, timeCol, entityCol, inputDir);
		  		  
		  				hdfsFileOperationsService.deleteFile("/ppa-repo/fmdatafeed", hadoopConf);
		  				dataAggregator.init(sparkConf);
		  				return 0L;
		  		  
		        }
		    });
		    
		   
	  }
	  
	  public void triggerDataRollup(long predictionId, String exprFile, String seriesNext, String seriesEnd, String seriesStart,
				String outSeriesFormat, String inSeriesFormat, String inputFile, String requiredFlds,  SparkConf sparkConf, Configuration hadoopConf ) {
		  
		  //Read Items from Configuration
		  // GET Organization and Project Name as parameters here
		  
		   ExecutorService executorService = Executors.newFixedThreadPool(8);
		    // Start thread 1
		    Future<Long> future1 = executorService.submit(new Callable<Long>() {
		        @Override
		        public Long call() throws Exception {
		        	DataRollup dataRollup = new DataRollup(predictionId, exprFile, seriesNext, seriesEnd, seriesStart, outSeriesFormat, inSeriesFormat, inputFile, requiredFlds);
		  		  		dataRollup.init(sparkConf);
		  				return 0L;
		  		  
		        }
		    });
	  }
	  
	  public void triggerPatterns(String inputFile, String outputFile, String saxcodeField, String subSeqInterval,
				String subSeqIntervalThreshold, long predictionId, SparkConf sparkConf, Configuration hadoopConf ) {
		  
		  //Read Items from Configuration
		  // GET Organization and Project Name as parameters here
		  
		   ExecutorService executorService = Executors.newFixedThreadPool(8);
		    // Start thread 1
		    Future<Long> future1 = executorService.submit(new Callable<Long>() {
		        @Override
		        public Long call() throws Exception {
		        	hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/TD", hadoopConf);
					hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/5DW", hadoopConf);
					hdfsFileOperationsService.mkdir("hdfs://spark:8020/ppa-repo/temp/TD", hadoopConf);
					hdfsFileOperationsService.copyHdfsFile("hdfs://spark:8020/ppa-repo/fmdatafeed/8.64E7/", "hdfs://spark:8020/ppa-repo/temp/TD", hadoopConf);
					hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/TD/_SUCCESS", hadoopConf);
		        	SubSequenceGenerator subSequenceGenerator = new SubSequenceGenerator(inputFile, outputFile, saxcodeField, subSeqInterval, subSeqIntervalThreshold, predictionId);
		        			subSequenceGenerator.run(sparkConf);
		        			hdfsFileOperationsService.readFile("hdfs://spark:8020/ppa-repo/temp/5DW/part-00000", hadoopConf);
		  				return 0L;
		  		  
		        }
		    });
	}
	  
	  public void triggerUpload(Configuration hadoopConf ) {
		  
		  //Read Items from Configuration
		  // GET Organization and Project Name as parameters here
		  PAOrganization pAOrganization = new PAOrganization();
		  pAOrganization.setId(1L);
		  
		  PAProject pAProject = new PAProject();
		  pAProject.setId(1L);
		  
		   ExecutorService executorService = Executors.newFixedThreadPool(8);
		    // Start thread 1
		    Future<Long> future1 = executorService.submit(new Callable<Long>() {
		        @Override
		        public Long call() throws Exception {
		        			List<String> tsvString = hdfsFileOperationsService.readFileO("hdfs://spark:8020/ppa-repo/temp/5DW/part-00000", hadoopConf);
		        			for (int i = 0; i < tsvString.size(); i++) {
		        				PASaxCode paSaxCode = new PASaxCode();
		        				
		        				String[] vals = tsvString.get(i).split("\t");
		        				paSaxCode.setDistalarm(vals[0]);
		        				paSaxCode.setSaxcode(vals[1]);
		        				paSaxCode.setTotal(vals[2]);
		        				paSaxCode.setPainterval(vals[3]);
		        				paSaxCode.setPaorgsc(pAOrganization);
		        				paSaxCode.setPaprosc(pAProject);
		        				paSaxCode.setPastatus(PAStatus.Active);
		        				System.out.println("Create NOW");
		        				paSaxCodeService.save(paSaxCode);
							}
		        			
		  				return 0L;
		  		  
		        }
		    });
	}

}

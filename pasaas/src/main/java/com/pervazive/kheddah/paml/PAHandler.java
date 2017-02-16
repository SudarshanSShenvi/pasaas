package com.pervazive.kheddah.paml;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;

import com.pervazive.kheddah.service.HDFSFileOperationsService;
import com.pervazive.kheddah.service.impl.HDFSFileOperationsServiceImpl;

public class PAHandler {
	
	public static void main(String[] args){
		
		HDFSFileOperationsService hdfsFileOperationsService = new HDFSFileOperationsServiceImpl();	
		
		Configuration configuration = new Configuration();
		configuration.set("fs.defaultFS", "hdfs://spark:8020");
		//PAHandler paHandler = new PAHandler();
		
		SparkConf sparkConf = new SparkConf();
		sparkConf.setMaster("local");
		sparkConf.set("spark.default.parallelism", "24");
		sparkConf.set("spark.driver.allowMultipleContexts", "true");
		
		DataAggregator dataAggregator = new DataAggregator(1, true, "0,1,2,3,4,5,7,15,16,17,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55", 
				"CUSTOMDATE#yyyy-MM-dd HH:mm:ss", "UNIXTIME", "30", "13:6", "hdfs://spark:8020/ppa-repo/fmdata");
			try {
				hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/fmdatafeed", configuration);
				dataAggregator.init(sparkConf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		/*DataRollup dataRollup = new DataRollup();
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
			}*/
	}
	
/*	private void check(HDFSFileOperationsService hdfsFileOperationsService, Configuration configuration) {
		try {
			hdfsFileOperationsService.copyHdfsFile("hdfs://spark:8020/ppa-repo/fmdatafeed/1.72799E8/", "hdfs://spark:8020/ppa-repo/temp/TD", configuration);
			hdfsFileOperationsService.deleteFile("hdfs://spark:8020/ppa-repo/temp/TD/_SUCCESS", configuration);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

}

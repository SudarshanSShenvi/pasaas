package com.pervazive.kheddah.paml;



import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;


public class WSCheck {
public static void main(String[] args) {
	
	String op = "hdfs://spark:8020/user/pervazive/counta4.txt";
	SparkConf conf = new SparkConf().setAppName("WC");
	conf.setMaster("local[2]");
	//conf.setMaster("spark://10.10.10.176:7077");
	//conf.setSparkHome("/opt/pasaas-product/Apache-Spark");
	//conf.set("spark.jars", "spark-2.1.0-bin-hadoop2.7");
	//conf.setMaster("yarn-client");
	//conf.set("spark.hadoop.yarn.resourcemanager.hostname", "10.10.10.124");
	//conf.set("spark.hadoop.yarn.resourcemanager.address", "10.10.10.124:8032");
	//conf.set("HADOOP_HOME", "/opt/hadoop/install/hadoop-2.5.1");
	JavaSparkContext sc = new JavaSparkContext(conf);
	
	JavaRDD<String> textFile = sc.textFile("hdfs://spark:8020/user/pervazive/BillingMapper.java");
	JavaRDD<String> words = textFile.flatMap(new FlatMapFunction<String, String>() {
	  public Iterator<String> call(String s) { return Arrays.asList(s.split(" ")).iterator(); }
	});
	JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
	  public Tuple2<String, Integer> call(String s) { return new Tuple2<String, Integer>(s, 1); }
	});
	JavaPairRDD<String, Integer> counts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
	  public Integer call(Integer a, Integer b) { return a + b; }
	});
	counts.saveAsTextFile(op);
}
}

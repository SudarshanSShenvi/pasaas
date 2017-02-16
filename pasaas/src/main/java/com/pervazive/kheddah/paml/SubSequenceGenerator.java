package com.pervazive.kheddah.paml;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;

import com.google.common.collect.Lists;
import com.pervazive.kheddah.paml.algo.HotSax;
import com.pervazive.kheddah.paml.algo.HotSax.Count;
import com.pervazive.kheddah.paml.util.DataFormatter;

import scala.Serializable;
import scala.Tuple2;


/**
 * Inputs SAXCode fld index Sub sequence interval (2/3/4 ....) Sub sequence
 * interval threshold ( Gives count >= threshold values)
 * 
 * 
 * Output Flat file (feed.subseq.interval) which has Entity, Sub sequence with
 * interval Save all values
 * 
 * @author Ravi
 *
 */
public class SubSequenceGenerator implements Serializable {
	public SubSequenceGenerator(String inputFile, String outputFile, String saxcodeField, String subSeqInterval,
			String subSeqIntervalThreshold, long predictionId) {
		super();
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.saxcodeField = saxcodeField;
		this.subSeqInterval = subSeqInterval;
		this.subSeqIntervalThreshold = subSeqIntervalThreshold;
		this.predictionId = predictionId;
	}

	private String IGNORE_CASE = "IgnoreCase";

	private String inputFile;
	private String outputFile;
	private String saxcodeField;
	private String subSeqInterval;
	private String subSeqIntervalThreshold;
	private long predictionId = 0;
	public static final char TAB_SEPARATOR = '\t';
	public static final char COMMA_SEPARATOR = ',';
	public static final char LINE_SEPARATOR = '\n';
	public static final String EMPTY = "";
	private static final String IGNORECASE = null;
	private int reducerPartition = 4;
	private int requiredColsNo = 3;

	public static class BucketedValues implements Comparator<BucketedValues>,
			Serializable {
		public long firstRecord;
		public String allRecords;

		// @Override
		public int compare(BucketedValues a, BucketedValues b) {
			if (a.firstRecord > b.firstRecord)
				return 1;
			if (a.firstRecord < b.firstRecord)
				return -1;
			return 0;
		}
	}

	/**
	 * Instead of integer make a count
	 * 
	 * @param saxCode
	 * @param seqI
	 * @return
	 */

	public static void main(String[] args) throws Exception {
		
		//String parms[] = {"1", "hdfs://spark:8020/ppa-repo/temp/TD", "hdfs://spark:8020/ppa-repo/temp/5DW", "2", "5", "0"};
		//new SubSequenceGenerator().run(args);
		//new SubSequenceGenerator().run(parms);
	}

	// @Override
	public int run(SparkConf sparkConf) throws Exception {

/*		if (args.length < 4) {
			System.err
					.println("Usage:"
							+ SubSequenceGenerator.class
							+ " <<inputfile>> <<outputfile>> <<SAXCode-field-index>> <<subsequnce-interval>> <<subsequnce-interval-threshold>>");
			System.exit(1);

		}
		predictionId = Long.parseLong(args[0].trim());
		inputFile = args[1].trim();
		outputFile = args[2].trim();
		saxcodeField = args[3].trim();
		subSeqInterval = args[4].trim();
		subSeqIntervalThreshold = args[5].trim();*/

		sparkConf.setAppName(predictionId
				+ " - SubSequenceGenerator");
		
		JavaSparkContext sc = new JavaSparkContext(sparkConf);
		JavaPairRDD<String, BucketedValues> mapToPair = mapPairFunction(sc);

		List<String> reduceSubSequenceCode = reduceByKeyFunc(mapToPair);

		JavaRDD<String> reduceSubSequenceList = sc
				.parallelize(reduceSubSequenceCode);

		reduceSubSequenceList.coalesce(1).saveAsTextFile(outputFile);
		sc.stop();
		return 0;

	}

	private JavaPairRDD<String, BucketedValues> mapPairFunction(
			JavaSparkContext sc) {

		JavaRDD<Tuple2<String, String>> file = sc.objectFile(inputFile);

		JavaPairRDD<String, BucketedValues> mapToPairValues = file
				.mapToPair(new PairFunction<Tuple2<String, String>, String, BucketedValues>() {
					List<String> cols = new ArrayList<String>();

					public Tuple2<String, BucketedValues> call(
							Tuple2<String, String> t) throws Exception {
						// TODO Auto-generated method stub
						BucketedValues b = new BucketedValues();
						b.allRecords = t._2;

						if (b.allRecords.length() == 0
								|| t._1.equalsIgnoreCase(IGNORE_CASE)) {
							new Tuple2<String, SubSequenceGenerator.BucketedValues>(
									IGNORE_CASE, b);
						} else {
							int firstRecordEnd = b.allRecords
									.indexOf(LINE_SEPARATOR);
							String firstRecord = (-1 == firstRecordEnd) ? b.allRecords
									: b.allRecords.substring(0, firstRecordEnd);
							DataFormatter.concatStringToArray(cols,
									firstRecord, TAB_SEPARATOR);
							try {
								// b.firstRecord = Long.parseLong(cols.get(0));
								b.firstRecord = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss").parse(
										cols.get(0).trim()).getTime();
							} catch (NumberFormatException e) {
								System.out
										.println("Error in parsing the datetime"
												+ cols.get(0));
								e.printStackTrace(System.out);
							}
							;
							cols.clear();
						}

						return new Tuple2<String, SubSequenceGenerator.BucketedValues>(
								t._1, b);
					}
				});

		return mapToPairValues;

	}

	private List<String> reduceByKeyFunc(
			JavaPairRDD<String, BucketedValues> mapToPairValues) {

		double[] ARGS_SLABS = new double[] { 0d, 1d };
		int subSequenceInterval;
		int subSequnceIntervalThreshold;
		int saxCodeFldIndex;

		List<String> cols = new ArrayList<String>();
		List<String> rows = new ArrayList<String>();

		subSequenceInterval = Integer.parseInt(subSeqInterval);
		subSequnceIntervalThreshold = Integer
				.parseInt(subSeqIntervalThreshold);
		saxCodeFldIndex = Integer.parseInt(saxcodeField);

		Map<String, Count> subSequenceMap = new HashMap<String, Count>();
		StringBuilder subSequnceSb = new StringBuilder(1024);

		JavaPairRDD<String, Iterable<BucketedValues>> reduceSubSequence = mapToPairValues
				.groupByKey(reducerPartition);

		List<String> reduceSubSequenceCode = new ArrayList();
		List<Double> measures = new ArrayList<Double>();
		List<BucketedValues> timeIntervalStartWithMeasureFields = new ArrayList<BucketedValues>();
		for (Tuple2<String, Iterable<BucketedValues>> bucketedValuesForKey : reduceSubSequence
				.collect()) {
			String requiredColumnStr = "";
			if (!bucketedValuesForKey._1.equalsIgnoreCase(IGNORE_CASE)) {
				timeIntervalStartWithMeasureFields.clear();
				timeIntervalStartWithMeasureFields = Lists
						.newArrayList(bucketedValuesForKey._2);

				Collections.sort(timeIntervalStartWithMeasureFields,
						new BucketedValues());
				measures.clear();
				for (BucketedValues bucketedValues : timeIntervalStartWithMeasureFields) {
					rows.clear();
					DataFormatter.concatStringToArray(rows,
							bucketedValues.allRecords, LINE_SEPARATOR);
					for (String aRow : rows) {
						cols.clear();
						DataFormatter.concatStringToArray(cols, aRow,
								TAB_SEPARATOR);
						measures.add(Double.parseDouble(cols
								.get(saxCodeFldIndex)));
						if (requiredColumnStr.equalsIgnoreCase("")) {
							for (int i = requiredColsNo; i < cols.size(); i++) {
								requiredColumnStr = requiredColumnStr
										+ TAB_SEPARATOR + cols.get(i);
							}

						}
					}
				}

				/**
				 * Create SAX codecs
				 */
				char[] saxA = HotSax.ts2String(measures, ARGS_SLABS);
				subSequenceMap.clear();
				HotSax.generateSubsequence(new String(saxA),
						subSequenceInterval, subSequenceMap);

				/**
				 * Cut Sub sequences
				 */
				subSequnceSb.setLength(0);
				boolean first = true;
				Count threshold;
				for (String subSeq : subSequenceMap.keySet()) {
					threshold = subSequenceMap.get(subSeq);
					if (threshold.count < subSequnceIntervalThreshold)
						continue;
					if (first)
						first = false;
					else
						subSequnceSb.append(LINE_SEPARATOR);

					subSequnceSb.append(bucketedValuesForKey._1)

					.append(TAB_SEPARATOR).append(subSeq).append(TAB_SEPARATOR)
							.append(threshold.count).append(TAB_SEPARATOR)
							.append(predictionId);
					if (!requiredColumnStr.equalsIgnoreCase("")) {
						subSequnceSb.append(requiredColumnStr);
					}
				}
				
				reduceSubSequenceCode.add(subSequnceSb.toString());
			}

		}
		return reduceSubSequenceCode;
	}
}

package com.pervazive.kheddah.paml;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import com.pervazive.kheddah.paml.util.DataFormatter;
import com.pervazive.kheddah.paml.util.TimeFormatter;

import scala.Serializable;
import scala.Tuple2;

/**
 * Inputs Series data field index Series data from type:
 * UNIXTIME/SINCE1971LONG/CUSTOMDATE#YYYY-MM-SS/INCH/CM/METER/.. Series data to
 * type: UNIXTIME/SINCE1971LONG/CUSTOMDATE#YYYY-MM-SS/INCH/CM/METER/.. Entity
 * field Unwanted Fields
 * 
 * Output Map file (name same as feed name) with entity as key and all rows
 * sorted by series column as value
 * 
 * Remove unwanted fields (Parameterized) Map by entity field Sort by
 * transformed series field Save all values Implementation for Hadoop 2.x
 * 
 * 
 */
public class DataAggregator implements Serializable {

	private String IGNORE_CASE = "IgnoreCase";
	private long PREDICTION_ID = 0;
	private String ARGS_ISHEADER;
	private String ARGS_SKIP_FIELD_INDEXES;
	private String ARGS_OUT_SERIES_FORMAT;
	private String ARGS_IN_SERIES_FORMAT;
	private String ARGS_SERIES_FIELD;
	private String ARGS_ENTITY_FIELD;
	private String ARGS_INPUT_FILE;

	public static final char TAB_SEPARATOR = '\t';
	public static final char COMMA_SEPARATOR = ',';
	public static final char LINE_SEPARATOR = '\n';
	public static final String EMPTY = "";

	public static final String FEED_OUTPUT_SUFFIX = "feed";

	public static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static boolean isFirstRowHeader = true;
	public static final String COMMA_BTW_DQOUTE_REGEX = ",(?!(([^\"]*\"){2})*[^\"]*$)"; // Move
																						// it
																						// to
																						// Data
																						// Cleaning
																						// job
																						// its
																						// specific
																						// to
																						// client
																						// data

	public static void main(String[] args) throws Exception {
		String parms[] = {"1", "hdfs://spark:8020/ppa-repo/fmdata","6:12", "2", "CUSTOMDATE#MM/dd/yyyy HH:mm", "CUSTOMDATE#yyyy-MM-dd HH:mm:ss", "0,1,2", "true"};
		//new DataAggregator().init(args);
		
		new DataAggregator().init(parms);
	}

	public int init(String[] args) throws Exception {
		if (args.length < 6) {
			System.err
					.println("Usage:"
							+ DataAggregator.class
							+ " <<"
							+ ARGS_INPUT_FILE
							+ ">> "
							// + " <<"
							// + ARGS_OUTPUT_FILE
							// + ">> "
							+ " <<"
							+ ARGS_ENTITY_FIELD
							+ ">> "
							+ " <<"
							+ ARGS_SERIES_FIELD
							+ ">> "
							+ " <<"
							+ ARGS_IN_SERIES_FORMAT
							+ " (UNIXTIME/SINCE1971LONG/CUSTOMDATE#YYYY-MM-SS/INCH/CM/METER)>> "
							+ " <<"
							+ ARGS_OUT_SERIES_FORMAT
							+ " (UNIXTIME/SINCE1971LONG/CUSTOMDATE#YYYY-MM-SS/INCH/CM/METER)>> "
							+ " <<" + ARGS_SKIP_FIELD_INDEXES
							+ "(comma separated)>> " + " <<" + ARGS_ISHEADER
							+ " >> ");

			return (Integer) null;
		}
		PREDICTION_ID = Long.parseLong(args[0].trim());
		ARGS_INPUT_FILE = args[1].trim();
		ARGS_ENTITY_FIELD = args[2].trim();
		ARGS_SERIES_FIELD = args[3].trim();
		ARGS_IN_SERIES_FORMAT = args[4].trim();
		ARGS_OUT_SERIES_FORMAT = args[5].trim();
		ARGS_SKIP_FIELD_INDEXES = args[6].trim();

		if (args.length == 8)
			isFirstRowHeader = Boolean.parseBoolean(args[7].trim());
		else
			isFirstRowHeader = false;

		JavaPairRDD<String, String> mapToPair = mapPairFuntion();

		JavaPairRDD<String, String> filteredData = reducedByKeyFuntion(mapToPair);

		JavaPairRDD<String, String> aggregatorData = filteredData
				.filter(new Function<Tuple2<String, String>, Boolean>() {
					public Boolean call(Tuple2<String, String> v1)
							throws Exception {
						// TODO Auto-generated method stub
						return v1._1.equalsIgnoreCase(IGNORE_CASE) ? false
								: true;
					}
				});

		aggregatorData.setName("text1").saveAsObjectFile(
				ARGS_INPUT_FILE + FEED_OUTPUT_SUFFIX);

		return 0;
	}

	/**
	 * TODO:// Move to standard converstion class.
	 * 
	 * @param format
	 * @return
	 */
	private static final int STD_UNIXTIME = 1;
	private static final String STD_UNIXTIME_NAME = "UNIXTIME";
	private static final int SINCE1971LONG = 2;
	private static final String SINCE1971LONG_NAME = "SINCE1971LONG";
	private static final int CUSTOMDATE = 3;
	private static final String CUSTOMDATE_NAME = "CUSTOMDATE";

	public static int getSeriesFormatValue(String format) {
		if (format.equalsIgnoreCase(STD_UNIXTIME_NAME))
			return STD_UNIXTIME;
		if (format.equalsIgnoreCase(SINCE1971LONG_NAME))
			return SINCE1971LONG;
		if (format.startsWith(CUSTOMDATE_NAME))
			return CUSTOMDATE;
		return -1;
	}

	/**
	 * TODO:// Move to standard converstion class.
	 * 
	 * @param type
	 * @param sourceVal
	 * @param customFormat
	 * @return
	 * @throws ParseException
	 */
	public static double convertSeriesUnit(int type, String sourceVal,
			String customFormat) throws ParseException {
		switch (type) {
		case STD_UNIXTIME:
			return TimeFormatter.convertUnixtimeToJavatime(Long
					.parseLong(sourceVal));
		case SINCE1971LONG:
			return Long.parseLong(sourceVal);
		case CUSTOMDATE:
			return TimeFormatter.convertDateformatToJavatime(sourceVal,
					customFormat);
		}
		return Long.MIN_VALUE;
	}

	public static String convertToOutSeriesFormat(int type,
			final Double sourceVal, String customFormat) throws ParseException {
		switch (type) {
		case STD_UNIXTIME:
			return TimeFormatter.convertJavatimeToUnixtime(
					sourceVal.longValue()).toString();
		case SINCE1971LONG:
			return sourceVal.toString();
		case CUSTOMDATE:
			return TimeFormatter.convertJavatimeToDateformat(
					sourceVal.longValue(), customFormat);
		}
		return sourceVal.toString();
	}

	public static class SeriesData {

		public double time;
		public String data;

		public SeriesData(double time, String data) {
			this.time = time;
			this.data = data;
		}

	}

	private JavaPairRDD<String, String> mapPairFuntion() throws IOException {
		SparkConf conf = new SparkConf().setAppName(PREDICTION_ID
				+ " - DataAggregator");
		//conf.setMaster("spark://spark:7077");
		conf.setMaster("yarn-client");
		conf.set("spark.hadoop.yarn.resourcemanager.hostname", "10.10.10.124");
		conf.set("spark.hadoop.yarn.resourcemanager.address", "10.10.10.124:8032");
		//conf.set("HADOOP_HOME", "/opt/hadoop/install/hadoop-2.5.1");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		
		JavaRDD<String> file = sc.textFile(ARGS_INPUT_FILE).cache();

		final int inSeriesFormatIndex;
		final int outSeriesFormatIndex;
		String seriesFldCustomFormatTmp = EMPTY;
		String seriesOutFldCustomFormatTemp = EMPTY;

		final List<String> entityIndexes = new ArrayList<String>();

		DataFormatter
				.concatStringToArray(entityIndexes, ARGS_ENTITY_FIELD, ':');

		final int seriesFldIndex = Integer.parseInt(ARGS_SERIES_FIELD);

		inSeriesFormatIndex = getSeriesFormatValue(ARGS_IN_SERIES_FORMAT);
		outSeriesFormatIndex = getSeriesFormatValue(ARGS_OUT_SERIES_FORMAT);

		if (inSeriesFormatIndex == -1 || outSeriesFormatIndex == -1)
			throw new IOException("Series format is not supported");
		if (inSeriesFormatIndex == CUSTOMDATE)
			seriesFldCustomFormatTmp = ARGS_IN_SERIES_FORMAT.split("#")[1]
					.trim();
		if (outSeriesFormatIndex == CUSTOMDATE)
			seriesOutFldCustomFormatTemp = ARGS_OUT_SERIES_FORMAT.split("#")[1]
					.trim();

		final String seriesOutFldCustomFormat = seriesOutFldCustomFormatTemp;
		final String seriesFldCustomFormat = seriesFldCustomFormatTmp;

		String removalFldStr = EMPTY;
		removalFldStr = ARGS_SKIP_FIELD_INDEXES;
		List<String> removalFlds = new ArrayList<String>();
		final List<Integer> removalFldsI = new ArrayList<Integer>();
		DataFormatter.concatStringToArray(removalFlds, removalFldStr,
				COMMA_SEPARATOR);
		for (String fld : removalFlds)
			removalFldsI.add(Integer.parseInt(fld));

		JavaPairRDD<String, String> pairs = file
				.mapToPair(new PairFunction<String, String, String>() {

					List<String> cols = new ArrayList<String>();
					StringBuilder valueSb = new StringBuilder(512);
					StringBuilder entityFldsb = new StringBuilder(128);
					boolean isFirstRowHeader = DataAggregator.isFirstRowHeader;

					public Tuple2<String, String> call(String line)
							throws IOException {

						if (isFirstRowHeader) {
							isFirstRowHeader = false;
							return new Tuple2<String, String>(IGNORE_CASE, null);
						}

						cols.clear();

						/**
						 * CSV Specific Formatting
						 */
						line = line.replaceAll("'", "");
						line = line.replaceAll("\\\\,", "");
						line = line.replaceAll(COMMA_BTW_DQOUTE_REGEX, " ");

						DataFormatter.concatStringToArray(cols, line,
								COMMA_SEPARATOR);
						valueSb.setLength(0);

						entityFldsb.setLength(0);
						boolean first = true;
						String aEntityCol = null;
						for (String entityIndex : entityIndexes) {
							if (first)
								first = false;
							else
								entityFldsb.append('_');
							aEntityCol = cols.get(Integer.parseInt(entityIndex));
							if (null == aEntityCol)
								return new Tuple2<String, String>(IGNORE_CASE,
										null);
							if (aEntityCol.trim().length() == 0)
								return new Tuple2<String, String>(IGNORE_CASE,
										null);
							entityFldsb.append(aEntityCol);
						}

						if (entityFldsb.toString().trim().length() == 0) {
							/**
							 * TODO:// Report Bad Data, Create another file for
							 * exception records
							 */
							return new Tuple2<String, String>(IGNORE_CASE, null);
						}

						double seriesFld = Long.MIN_VALUE;
						try {
							String seriesFldStr = cols.get(seriesFldIndex);
							if (null == seriesFldStr)
								return new Tuple2<String, String>(IGNORE_CASE,
										null);
							if (seriesFldStr.trim().length() == 0)
								return new Tuple2<String, String>(IGNORE_CASE,
										null);
							seriesFld = convertSeriesUnit(inSeriesFormatIndex,
									seriesFldStr, seriesFldCustomFormat);
						} catch (ParseException e) {
							System.out.println("Error in parsing the datetime:"
									+ seriesFld);
							e.printStackTrace(System.out);
							/**
							 * TODO:// Report Bad Data, Create another file for
							 * exception records
							 */
							return new Tuple2<String, String>(IGNORE_CASE, null);
						}

						int index = -1;

						valueSb.append(seriesFld);

						/**
						 * Skip unwanted columns
						 */
						for (String aCol : cols) {
							index++;
							if (index == seriesFldIndex)
								continue;
							if (removalFldsI.contains(index))
								continue;

							valueSb.append(TAB_SEPARATOR);
							valueSb.append(cols.get(index));
						}

						/**
						 * Continue reducing on the entity field
						 */
						return new Tuple2<String, String>(entityFldsb
								.toString(), DataAggregator
								.refineReduceByKeyValue(valueSb.toString(),
										outSeriesFormatIndex,
										seriesOutFldCustomFormat).data);
					}
				});
		return pairs;
	}

	private JavaPairRDD<String, String> reducedByKeyFuntion(
			JavaPairRDD<String, String> mapToPair) throws IOException {

		JavaPairRDD<String, String> reducedByKeyValues = mapToPair
				.reduceByKey(new Function2<String, String, String>() {

					public String call(String v1, String v2) throws Exception {
						// TODO Auto-generated method stub
						StringBuilder finalData = new StringBuilder(1024);

						finalData.append(v1);
						finalData.append(LINE_SEPARATOR);
						finalData.append(v2);

						return finalData.toString();
					}
				});
		return reducedByKeyValues;
	}

	public static SeriesData refineReduceByKeyValue(String text,
			int outSeriesFormatIndex, String seriesOutFldCustomFormat)
			throws IOException {
		List<String> cols = new ArrayList<String>(120);
		cols.clear();
		StringBuilder seriesDataValueSb = new StringBuilder(1024);
		seriesDataValueSb.setLength(0);
		DataFormatter.concatStringToArray(cols, text.toString(), TAB_SEPARATOR);

		boolean first = true;
		for (String aCol : cols) {
			if (first) {
				first = false;
				try {
					seriesDataValueSb.append(convertToOutSeriesFormat(
							outSeriesFormatIndex, Double.parseDouble(aCol),
							seriesOutFldCustomFormat));
				} catch (ParseException e) {
					throw new IOException(
							"Failed to convertToOutSeriesFormat : [" + aCol
									+ "]" + "\t" + outSeriesFormatIndex);
				}
			} else
				seriesDataValueSb.append(TAB_SEPARATOR).append(aCol);
		}
		return new SeriesData(Double.parseDouble(cols.get(0)),
				seriesDataValueSb.toString());
	}

}

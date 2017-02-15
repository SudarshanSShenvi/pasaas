package com.pervazive.kheddah.paml;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.storage.StorageLevel;

import com.pervazive.kheddah.paml.util.DataFormatter;
import com.pervazive.kheddah.paml.util.TimeFormatter;


import scala.Serializable;
import scala.Tuple2;

/**
 * Inputs Series argument format
 * UNIXTIME/SINCE1971LONG/CUSTOMDATE#YYYY-MM-SS/INCH/CM/METER/.. Series storage
 * format UNIXTIME/SINCE1971LONG/CUSTOMDATE#YYYY-MM-SS/INCH/CM/METER/.. Series
 * start Series end Series second point ( for interval calculation series second
 * point - Series start ) Apply Expressions FieldName Type Value f1 FILL 0,MEAN
 * f3 EXPR f1*f2 f1 AGGR COUNT,MIN,MAX,SUM,AVG,MEAN,SD
 * 
 * Output Map file (name same as feed.interval ) with entity as key Values -
 * Series,Aggregated columns
 * 
 * Map by entity field Sort by transformed series field Save all values
 * 
 * @author Ravi
 *
 */
public class DataRollup implements Serializable {
	private String IGNORE_CASE = "IgnoreCase";
	private long PREDICTION_ID = 0;
	private static String EXPRRESSION_FILE = "expr-file";
	private static String SERIES_NEXT = "series-next";
	private static String SERIES_END = "series-end";
	private static String SERIES_START = "series-start";
	private static String ARGS_OUT_SERIES_FORMAT = "out-series-format";
	private static String ARGS_IN_SERIES_FORMAT = "in-series-format";
	private static String ARGS_INPUT_FILE = "input-file";
	private String seriesgroupcol = "seriesgroupcol";
	public static final char TAB_SEPARATOR = '\t';
	public static final char COMMA_SEPARATOR = ',';
	public static final char LINE_SEPARATOR = '\n';
	public static final String EMPTY = "";

	public static List<Tuple2<String, String>> reduceDataRollupList = new ArrayList();
	public static int seriesArgFormatType;
	public static String customDateFormat = EMPTY;
	public static double seriesInterval = 0;
	public static double seriesStartVal = 0;
	public static double seriesEndVal = 0;

	public static List<Expr> applyExpressions = new ArrayList<Expr>();
	List<String> requiredColumns = new ArrayList<String>();

	public static JexlEngine exprEngine = new JexlEngine();
	public static JexlContext exprContext = null;

	public static int seriesStoreFormatType;

	// List<Tuple2<Text, Text>> reduceDataRollupList = new ArrayList();

	public static class Expr {
		String fldName;
		String fldType;
		String exprText;
		Expression expr;

		public Expr(String fldName, String fldType, String exprText,
				JexlEngine jexl) {
			this.fldName = fldName;
			this.fldType = fldType;
			this.exprText = exprText;

			if (fldType.equals("EXPR"))
				expr = jexl.createExpression(exprText);
		}
	}

	/**
	 * ========================================================================
	 * =============== =========================== MAP START
	 * ===========================================
	 * ==============================
	 * =========================================================
	 */

	private JavaPairRDD<String, String> mapPairFuntion(JavaSparkContext sc)
			throws IOException {

		/*
		 * JavaPairRDD<Text, Text> file = sc.sequenceFile(args[0].trim(),
		 * Text.class, Text.class);
		 */
		JavaRDD<Tuple2<String, String>> file = sc.objectFile(ARGS_INPUT_FILE);
		JavaPairRDD<String, String> mapToPair = file
				.mapToPair(new PairFunction<Tuple2<String, String>, String, String>() {
					public Tuple2<String, String> call(
							Tuple2<String, String> line) throws Exception {
						// TODO Auto-generated method stub
						return new Tuple2<String, String>(line._1, line._2);
					}
				});
		return mapToPair;
	}

	public static class BucketedValues implements Comparator<BucketedValues> {
		public double firstRecord;
		public String allRecords;

		public int compare(BucketedValues a, BucketedValues b) {
			if (a.firstRecord > b.firstRecord)
				return 1;
			if (a.firstRecord < b.firstRecord)
				return -1;
			return 0;
		}
	}

	/**
	 * ========================================================================
	 * =============== =========================== REDUCER START
	 * ===========================================
	 * ==============================
	 * =========================================================
	 */

	/*
	 * private JavaPairRDD<String, String> reducedByKeyFuntion(final String
	 * args[], JavaPairRDD<String, String> mapToPair, List<String>
	 * applyExpressionsL) throws IOException {
	 */

	private List<Tuple2<String, String>> reducedByKeyFuntion(
			JavaPairRDD<String, String> mapToPair,
			List<String> applyExpressionsL) throws IOException {

		BufferedReader reader = null;

		seriesArgFormatType = getSeriesFormatValue(ARGS_IN_SERIES_FORMAT);
		seriesStoreFormatType = getSeriesFormatValue(ARGS_OUT_SERIES_FORMAT);

		if (seriesArgFormatType == -1 || seriesStoreFormatType == -1)
			throw new IOException("Series format is not supported");

		if (seriesArgFormatType == 3)
			customDateFormat = ARGS_IN_SERIES_FORMAT.split("#")[1].trim();
		try {
			seriesInterval = new Double(getInterval(seriesArgFormatType,
					SERIES_START, SERIES_NEXT, customDateFormat));
			seriesStartVal = new Double(convertSeriesUnit(seriesArgFormatType,
					SERIES_START, customDateFormat));
			seriesEndVal = new Double(convertSeriesUnit(seriesArgFormatType,
					SERIES_END, customDateFormat));
		} catch (ParseException e) {
			System.out.println("Error in parsing the datetime" + SERIES_START
					+ " or " + SERIES_NEXT);
			e.printStackTrace(System.out);
		}

		if (applyExpressionsL.size() > 0) {

			exprEngine = new JexlEngine();
			exprEngine.setCache(512);
			exprEngine.setLenient(false);
			exprEngine.setSilent(false);

			List<String> exprFlds = new ArrayList<String>();
			for (String exprLine : applyExpressionsL) {
				exprFlds.clear();
				DataFormatter.concatStringToArray(exprFlds, exprLine,
						TAB_SEPARATOR);
				if (exprFlds.size() != 3)
					throw new IOException("Bad Expression Line:" + exprLine
							+ "\n" + EXPRRESSION_FILE);
				applyExpressions.add(new Expr(exprFlds.get(0), exprFlds.get(1),
						exprFlds.get(2), exprEngine));
			}
		}

		StorageLevel storageLevels = new StorageLevel();
		storageLevels.apply(true, false, false, false, 0);

		JavaPairRDD<String, Iterable<String>> reduceDataRollup = mapToPair
				.groupByKey().persist(storageLevels);

		List<String> cols = new ArrayList<String>();
		List<String> rows = new ArrayList<String>();
		List<BucketedValues> valuesSorted = new ArrayList<BucketedValues>();
		Map<Double, List<String>> intervalBuckerWithRowsMap = null;
		String requiredColumnsStr = "";

		for (Tuple2<String, Iterable<String>> dataRollupValuesForKey : reduceDataRollup
				.persist(storageLevels).collect()) {

			if (dataRollupValuesForKey._1.equalsIgnoreCase(IGNORE_CASE)) {
				continue;
			}

			valuesSorted.clear();

			for (String val : dataRollupValuesForKey._2) {
				BucketedValues b = new BucketedValues();
				b.allRecords = val.toString();
				if (b.allRecords.length() == 0)
					continue;
				int firstRecordEnd = b.allRecords.indexOf(LINE_SEPARATOR);
				String firstRecord = (-1 == firstRecordEnd) ? b.allRecords
						: b.allRecords.substring(0, firstRecordEnd);
				DataFormatter.concatStringToArray(cols, firstRecord,
						TAB_SEPARATOR);
				try {
					b.firstRecord = convertSeriesUnit(seriesArgFormatType,
							cols.get(0), customDateFormat);
				} catch (ParseException e) {
					System.out.println("Error in parsing the datetime"
							+ cols.get(0));
					e.printStackTrace(System.out);
				}

				cols.clear();
				valuesSorted.add(b);
			}

			Collections.sort(valuesSorted, new BucketedValues());

			intervalBuckerWithRowsMap = getRangePoints(seriesStartVal,
					seriesEndVal, seriesInterval);

			double currentVal;
			double currentRangeNumber;
			double lowerBoundRange;

			for (BucketedValues v : valuesSorted) {
				rows.clear();
				DataFormatter.concatStringToArray(rows,
						v.allRecords.toString(), LINE_SEPARATOR);
				for (String aRow : rows) {
					cols.clear();
					DataFormatter
							.concatStringToArray(cols, aRow, TAB_SEPARATOR);
					String seriesFld = cols.get(0);
					try {
						currentVal = new Double(convertSeriesUnit(
								seriesArgFormatType, seriesFld,
								customDateFormat));
						if (currentVal < seriesStartVal
								|| currentVal > seriesEndVal)
							continue;

						currentRangeNumber = Math
								.floor((currentVal - seriesStartVal)
										/ seriesInterval);
						lowerBoundRange = seriesStartVal + currentRangeNumber
								* seriesInterval;
						List<String> intervalBucket = intervalBuckerWithRowsMap
								.get(lowerBoundRange);
						if (null == intervalBucket) {
							System.err.println("Interval Bucket is absent: ["
									+ lowerBoundRange + "]");
							return null;
						}
						intervalBucket.add(aRow);
					} catch (ParseException e) {
						System.out.println("Error in parsing the datetime:"
								+ seriesFld);
						e.printStackTrace(System.out);
					}
				}
			}
			/*
			 * Continue aggregation
			 */

			List<String> fieldNames = new ArrayList<String>();
			List<String> fieldValues = new ArrayList<String>();

			List<String> aggregatedColNames = new ArrayList<String>();
			List<String> aggregatedColValues = new ArrayList<String>();
			List<String> requiredColumnsFromLine = new ArrayList<String>();

			StringBuilder rowBuilder = new StringBuilder(1024);

			boolean isFirstKey = true;

			for (double rangeKey : intervalBuckerWithRowsMap.keySet()) {
				cols.clear();
				cols.addAll(intervalBuckerWithRowsMap.get(rangeKey));

				fieldNames.clear();
				fieldValues.clear();

				aggregatedColNames.clear();
				aggregatedColValues.clear();

				requiredColumnsStr = "";

				int seq = 1;
				for (String col : cols) {
					String fldName = "f" + seq;
					seq++;
					fieldNames.add(fldName);
					fieldValues.add(col);
				}

				requiredColumnsFromLine.clear();
				if (cols.size() > 0) {
					DataFormatter.concatStringToArray(requiredColumnsFromLine,
							cols.get(cols.size() - 1), TAB_SEPARATOR);
					for (int i = 0; i < requiredColumns.size(); i++) {
						requiredColumnsStr = requiredColumnsStr
								+ TAB_SEPARATOR
								+ requiredColumnsFromLine.get(Integer
										.parseInt(requiredColumns.get(i)));
					}
				}

				for (Expr aExpr : applyExpressions) {
					if (aExpr.fldType.equals("AGGR")) {
						if (aExpr.exprText.equals("COUNT")) {

							fieldNames.add(aExpr.fldName);
							fieldValues
									.add(new Integer(cols.size()).toString());

							aggregatedColNames.add(aExpr.fldName);
							aggregatedColValues.add(new Integer(cols.size())
									.toString());
						} else if (aExpr.exprText.equals("SUM")) {

							fieldNames.add(aExpr.fldName);
							fieldValues.add(new Double(sum(cols)).toString());

							aggregatedColNames.add(aExpr.fldName);
							aggregatedColValues.add(new Double(sum(cols))
									.toString());
						} else if (aExpr.exprText.equals("MIN")) {

							fieldNames.add(aExpr.fldName);
							fieldValues.add(new Double(min(cols)).toString());

							aggregatedColNames.add(aExpr.fldName);
							aggregatedColValues.add(new Double(min(cols))
									.toString());
						} else if (aExpr.exprText.equals("MAX")) {

							fieldNames.add(aExpr.fldName);
							fieldValues.add(new Double(max(cols)).toString());

							aggregatedColNames.add(aExpr.fldName);
							aggregatedColValues.add(new Double(max(cols))
									.toString());

						} else if (aExpr.exprText.equals("STDDEV")) {
						} else if (aExpr.exprText.equals("MEAN")) {
						}
					} else if (aExpr.fldType.equals("EXPR")) {
						int fieldsT = fieldNames.size();
						exprContext = new MapContext();
						for (int i = 0; i < fieldsT; i++) {
							exprContext.set(fieldNames.get(i),
									fieldValues.get(i));
						}

						Object resObj = aExpr.expr.evaluate(exprContext);

						if (resObj instanceof Boolean) {
							Boolean b = (Boolean) resObj;
							String result = (b) ? "1" : "0";
							aggregatedColNames.add(aExpr.fldName);
							aggregatedColValues.add(result);
						} else {
							Float result = (Float) resObj;
							aggregatedColNames.add(aExpr.fldName);
							aggregatedColValues.add(new Float(result)
									.toString());
						}
					}
				}

				if (aggregatedColValues.size() > 0) {
					if (isFirstKey)
						isFirstKey = false;
					else
						rowBuilder.append(LINE_SEPARATOR);

				}

				boolean isFirst = true;
				for (String fldVal : aggregatedColValues) {

					try {
						if (isFirst) {
							isFirst = false;
							rowBuilder.append(convertToOutSeriesFormat(
									seriesStoreFormatType, rangeKey,
									customDateFormat)
									+ TAB_SEPARATOR + fldVal);
						} else {
							rowBuilder.append(TAB_SEPARATOR + fldVal);
						}
					} catch (ParseException e) {
						System.out
								.println("Error in pasring for outputformat :"
										+ rangeKey);
						e.printStackTrace(System.out);
					}
				}
				if (!requiredColumnsStr.equalsIgnoreCase("")) {
					rowBuilder.append(requiredColumnsStr);
				} else {
					rowBuilder.append(TAB_SEPARATOR + "NA");
				}
			}
			reduceDataRollupList.add(new Tuple2<String, String>(
					dataRollupValuesForKey._1, rowBuilder.toString()));
		}

		return reduceDataRollupList;
		// return reduceDataRollup1;
	}

	/**
	 * ========================================================================
	 * =============== =========================== REDUCER END
	 * ===========================================
	 * ==============================
	 * =========================================================
	 */

	public static Map<Double, List<String>> getRangePoints(double startPt,
			double endPt, double interval) {
		Map<Double, List<String>> rangePlotMap = new TreeMap<Double, List<String>>();
		for (double ptIndex = startPt; ptIndex <= endPt; ptIndex = ptIndex
				+ interval) {
			rangePlotMap.put(ptIndex, new ArrayList<String>());
		}
		// System.err.println(rangePlotMap.keySet().toString());
		return rangePlotMap;
	}

	public static void main(String[] args) throws Exception {
		//String parms[] = {"1", "hdfs://spark:8020/ppa-repo/fmdatafeed","CUSTOMDATE#yyyy-MM-dd HH:mm:ss", "CUSTOMDATE#yyyy-MM-dd HH:mm:ss",
			//	"2015-03-01 00:00:00", "2015-03-31 23:59:59", "2015-03-02 23:59:59", "hdfs://spark:8020/ppa-repo/conf/expression.txt", "2"};
		/*predictTrainingStartDate="2015-03-01 00:00:00"
				predictTrainingEndDate="2015-03-31 23:59:59"
				predictTrainingIntervalDate="2015-03-02 00:00:00"
				predictBaseStartDate="2015-03-28 00:00:00"
				predictBaseEndDate="2015-03-31 23:59:59"
				predictBaseIntervalDate="2015-03-29 00:00:00"
				predictDate="01/04/2015 00:00:00"*/

		//new DataRollup().init(args);
		//new DataRollup().init(parms);
	}

	public int init(String[] args, SparkConf sparkConf) throws Exception {
		if (args.length < 7) {
			System.err
					.println("Usage:"
							+ DataRollup.class
							+ " <<"
							+ ARGS_INPUT_FILE
							+ ">>"
							+ " <<"
							+ ARGS_IN_SERIES_FORMAT
							+ " (UNIXTIME/SINCE1971LONG/CUSTOMDATE#YYYY-MM-SS/INCH/CM/METER)>>"
							+ " <<"
							+ ARGS_OUT_SERIES_FORMAT
							+ " (UNIXTIME/SINCE1971LONG/CUSTOMDATE#YYYY-MM-SS/INCH/CM/METER)>>"
							+ " <<" + SERIES_START + ">>" + " <<" + SERIES_END
							+ ">>" + " <<" + SERIES_NEXT + ">>" + " <<"
							+ EXPRRESSION_FILE + ">>"
							// + " <<dummy-outputfile>> "
							+ "[series-group-column-index]");
			return -1;
		}

		PREDICTION_ID = Long.parseLong(args[0].trim());
		ARGS_INPUT_FILE = args[1].trim();
		ARGS_IN_SERIES_FORMAT = args[2].trim();
		ARGS_OUT_SERIES_FORMAT = args[3].trim();
		SERIES_START = args[4].trim();
		SERIES_END = args[5].trim();
		SERIES_NEXT = args[6].trim();
		EXPRRESSION_FILE = args[7].trim();
		String requiredFlds = args[8].trim();

		if (requiredFlds.contains(",")) {
			DataFormatter.concatStringToArray(requiredColumns, requiredFlds,
					COMMA_SEPARATOR);
		} else {
			requiredColumns.add(requiredFlds);
		}

		if (args.length == 9) {
			// seriesgroupcol = args[7].trim();
			seriesgroupcol = "hdfs://spark:8020/user/hadoop/dummy";
		} else
			seriesgroupcol = EMPTY;

		sparkConf.setAppName(PREDICTION_ID + " - DataRollup");

		JavaSparkContext sc = new JavaSparkContext(sparkConf);

		JavaRDD<String> expressionFile = sc.textFile(EXPRRESSION_FILE).cache();

		List<String> applyExpressionsL = new ArrayList<String>();
		for (String expr : expressionFile.collect()) {
			applyExpressionsL.add(expr);
		}

		JavaPairRDD<String, String> mapToPair = mapPairFuntion(sc);

		/*
		 * JavaPairRDD<String, String> reduceDataRollupList =
		 * reducedByKeyFuntion( args, mapToPair, applyExpressionsL);
		 */

		List<Tuple2<String, String>> reduceDataRollupList = reducedByKeyFuntion(
				mapToPair, applyExpressionsL);

		JavaPairRDD<String, String> reduceSubSequenceList = sc
				.parallelizePairs(reduceDataRollupList);

		double seriesInterval = 0;
		int seriesArgFormatType;
		String customDateFormat = EMPTY;

		seriesArgFormatType = getSeriesFormatValue(ARGS_IN_SERIES_FORMAT);
		if (seriesArgFormatType == 3)
			customDateFormat = ARGS_IN_SERIES_FORMAT.split("#")[1].trim();
		seriesInterval = new Double(getInterval(seriesArgFormatType,
				SERIES_START, SERIES_NEXT, customDateFormat));

		String url = ARGS_INPUT_FILE +"/"+ seriesInterval;

		reduceSubSequenceList.setName("result").saveAsObjectFile(url);

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

	public static double getInterval(int type, String start, String end,
			String customFormat) throws ParseException {
		switch (type) {
		case 1:
			return Long.parseLong(end) - Long.parseLong(start);
		case 2:
			return Long.parseLong(end) - Long.parseLong(start);
		case 3:
			return TimeFormatter.convertDateformatToJavatime(end, customFormat)
					- TimeFormatter.convertDateformatToJavatime(start,
							customFormat);
		}
		return Long.MIN_VALUE;
	}

	public static double sum(List<String> recordCols) {
		double val = 0.0;

		for (String col : recordCols) {
			val += Double.parseDouble(col);
		}
		return val;
	}

	public static double min(List<String> recordCols) {
		double val = Long.MAX_VALUE;

		for (String col : recordCols) {
			double temp = Double.parseDouble(col);
			if (temp < val)
				val = temp;
		}
		return val;
	}

	public static double max(List<String> recordCols) {
		double val = Long.MIN_VALUE;

		for (String col : recordCols) {
			double temp = Double.parseDouble(col);
			if (temp > val)
				val = temp;
		}
		return val;
	}

}

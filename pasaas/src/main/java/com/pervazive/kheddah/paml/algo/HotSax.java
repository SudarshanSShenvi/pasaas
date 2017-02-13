package com.pervazive.kheddah.paml.algo;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

/**
 * Implements HOT SAX algorithms
 * 
 */
public final class HotSax {

  /** Letters a-z. */
  static final char[] A_Z = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

  /**
   * Finds the maximal value in series data
   * 
   * @param series series values.
   * @return The max value
   */
  public static final double max(final double[] series) {
	  if (countInfinitedAndNaNs(series) == series.length) {
		  return Double.NaN;
	  }

	  double max = Long.MIN_VALUE;
	  for (int i = 0; i < series.length; i++) {
		  if (max < series[i]) max = series[i];
	  }
	  return max;
  }

  /**
   * Finds the minimal value in series.
   * 
   * @param series values.
   * @return min value
   */
  public static final double min(final double[] series) {
	  if (countInfinitedAndNaNs(series) == series.length) {
		  return Double.NaN;
	  }
	  double min = Long.MAX_VALUE;
	  for (int i = 0; i < series.length; i++) {
		  if (min > series[i]) min = series[i];
	  }
	  return min;
  }

  /**
   * Series mean
   * 
   * @param series The values
   * @return mean value
   */
  public static final double mean(final double[] series) {
	  double res = 0D;
	  int count = 0;
	  for (double reading : series) {
	      if (Double.isNaN(reading) || Double.isInfinite(reading)) continue;
	      res += reading;
	      count += 1;
	  }
	  
	  if (count > 0) return res / ((Integer) count).doubleValue();
	  return Double.NaN;
  }


  /**
   * Series Variance 
   * 
   * @param series values.
   * @return variance
   */
  public static final double variance(final double[] series) {
	  double res = 0D;
	  double mean = mean(series);
	  if (Double.isNaN(mean) || Double.isInfinite(mean)) return Double.NaN;
    
	  int count = 0;
	  for (double reading : series) {
		  if (Double.isNaN(reading) || Double.isInfinite(reading)) continue;
		  res += (reading - mean) * (reading - mean);
		  count += 1;
	  }
	  if (count > 0) return res / ((Integer) (count - 1)).doubleValue();
	  return Double.NaN;
  }

  /**
   * Computes the standard deviation of series.
   * 
   * @param series values.
   * @return standard deviation.
   */
  public static final double standardDeviation(final double[] series) {
	  double num0 = 0D;
	  double sum = 0D;
	  int count = 0;
	  for (double tp : series) {
		  if (Double.isNaN(tp) || Double.isInfinite(tp)) continue;
		  num0 = num0 + tp * tp;
		  sum = sum + tp;
		  count += 1;
	  }
	  
	  if (count > 0) {
		  double len = ((Integer) count).doubleValue();
		  return Math.sqrt((len * num0 - sum * sum) / (len * (len - 1)));
	  }
	  return Double.NaN;
  }

  /**
   * series Z-Normalize - mean zero and sd 1
   * 
   * @param series values.
   * @return Z-normalized series
   * @throws TimeseriesException if error occurs.
   */
  public static final double[] zNormalize(final double[] series) throws TimeseriesException {

    double mean = mean(series);
    double sd = standardDeviation(series);
    return zNormalize(series, mean, sd);
  }

  /**
   * Z-Normalize series to the mean 0 and SD 1.
   * 
   * @param series values.
   * @param mean Mean.
   * @param sd The standard deviation.
   * @return Z-normalized z-transformed series.
   * @throws TimeseriesException if error occurs.
   */
  public static double[] zNormalize(double[] series, double mean, double sd) throws TimeseriesException {

	double[] res = new double[series.length];
    if (Double.isInfinite(mean) || Double.isNaN(mean) || Double.isInfinite(sd) || Double.isNaN(sd)) {

    	int nanNum = countInfinitedAndNaNs(series);
    	if ((series.length - nanNum) == 1) {
    		for (int i = 0; i < res.length; i++) {
    			if (Double.isInfinite(series[i]) || Double.isNaN(series[i])) {
    				res[i] = Double.NaN;
    			} else {
    				res[i] = 1.0D;
    			}
    		}
    	}

    	else if (series.length == nanNum) {
    		for (int i = 0; i < res.length; i++) {
    			res[i] = Double.NaN;
    		}
    	}

    } else if (sd <= 0.001D) {

    	for (int i = 0; i < res.length; i++) {
    		if (Double.isInfinite(series[i]) || Double.isNaN(series[i])) {
    			res[i] = series[i];
    		} else {
    			res[i] = 0.1D;
    		}
    	}
    } else {
    	for (int i = 0; i < res.length; i++) {
    		res[i] = (series[i] - mean) / sd;
    	}
    }
    return res;
  }

  /**
   * Approximate the timeseries. 
   * A graph with 100 points in x-axis show a cosine curve.
   * After PAA
   * A new graph with 15 points in x-axis will show a cosine curve with same max/min Y-Range.
   * 
   * @param series values
   * @param totalXPoints The desired x-axis length of approximated series.
   * @return approximated series.
   * @throws TimeseriesException if error occurs.
   */
  public static double[] paa(double[] series, int totalXPoints) throws TimeseriesException {

	  if ( null == series) return null;
	  
	  int seriesT = series.length;
	  if ( 0 == seriesT) throw new TimeseriesException(
			"Paa not possible, There are no values in original series.");
	  
	  if ( seriesT == totalXPoints) {
		  double[] paaSeries = new double[seriesT];
		  System.arraycopy(series, 0, paaSeries, 0, seriesT);
		  return paaSeries;
	  }
	  
	  for (double val : series) {
		  if ( val == Double.NaN) throw new TimeseriesException(
				 "Paa not possible, Nan values in original series -" + series.length);
	  }
	  
      double[][] vals = toSingleRowMatrix(series);
      double[][] res;
      if (seriesT % totalXPoints == 0) {
    	  res = MatrixProcessor.reshape(vals, 0, totalXPoints);
      
      } else {
    	  
    	  double[][] tmp = new double[totalXPoints][seriesT];
    	  for (int row = 0; row < totalXPoints; row++) {
    		  for (int col = 0; col < seriesT; col++) {
    			  tmp[row][col] = vals[0][col];
    		  }
    	  }
    	  
    	  double[][] expandedSS = MatrixProcessor.reshape(tmp, 1, seriesT * totalXPoints);
    	  res = MatrixProcessor.reshape(expandedSS, seriesT, totalXPoints);
      }
      
      double[] newVals = MatrixProcessor.colMeans(res);
      return newVals;
  }


  /**
   * Converts the timeseries into string using given cuts intervals. Useful for not-normal
   * distribution cuts.
   * 
   * @param vals The timeseries.
   * @param cuts The cut intervals.
   * @return The timeseries SAX representation.
   */
  public static char[] ts2String(double[] vals, double[] cuts) {
    char[] res = new char[vals.length];
    for (int i = 0; i < vals.length; i++) {
      res[i] = num2char(vals[i], cuts);
    }
    return res;
  }
  
  public static char[] ts2String(List<Double> vals, double[] cuts) {
	  int size = vals.size();
	    char[] res = new char[size];
	    int i=-1;
	    for (Double val : vals) {
			i++;
			res[i] = num2char(val, cuts);
		}
	    return res;
  } 

  /**
   * Get mapping of a number to char.
   * 
   * @param value the value to map.
   * @param cuts the array of intervals.
   * @return character corresponding to numeric value.
   */
  public static char num2char(double value, double[] cuts) {
    int count = 0;
    while ((count < cuts.length) && (cuts[count] <= value)) {
      count++;
    }
    return A_Z[count];
  }

  /**
   * Converts index into char.
   * 
   * @param idx The index value.
   * @return The char by index.
   */
  public static char num2char(int idx) {
    return A_Z[idx];
  }


  /**
   * Get mapping of number to cut index.
   * 
   * @param value the value to map.
   * @param cuts the array of intervals.
   * @return character corresponding to numeric value.
   */
  public static int num2index(double value, double[] cuts) {
    int count = 0;
    while ((count < cuts.length) && (cuts[count] <= value)) {
      count++;
    }
    return count;
  }

  /**
   * Counts the number of NaNs' in the timeseries.
   * 
   * @param series The timeseries.
   * @return The count of NaN values.
   */
  public static final int countInfinitedAndNaNs(final double[] series) {
    int res = 0;
    for (double d : series) {
      if (Double.isInfinite(d) || Double.isNaN(d)) {
        res += 1;
      }
    }
    return res;
  }

  /**
   * Converts the vector into one-row matrix.
   * 
   * @param vector The vector.
   * @return The matrix.
   */
  public static double[][] toSingleRowMatrix(double[] vector) {
    double[][] res = new double[1][vector.length];
    for (int i = 0; i < vector.length; i++) {
      res[0][i] = vector[i];
    }
    return res;
  }

  /**
   * Extract subseries out of series.
   * 
   * @param series The series array.
   * @param start Start position
   * @param length Length of subseries to extract.
   * @return The subseries.
   * @throws IndexOutOfBoundsException If error occurs.
   */
  public static double[] subseries(double[] series, int start, int length)
      throws IndexOutOfBoundsException {
    if (start + length > series.length) {
      throw new IndexOutOfBoundsException("Unable to extract subseries, series length: "
          + series.length + ", start: " + start + ", subseries length: " + length);
    }
    double[] res = new double[length];
    for (int i = 0; i < length; i++) {
      res[i] = series[start + i];
    }
    return res;
  }

  /**
   * Extract subseries out of series.
   * 
   * @param series The series array.
   * @param start Start position
   * @param length Length of subseries to extract.
   * @return The subseries.
   * @throws IndexOutOfBoundsException If error occurs.
   */
  public static double[] subseries(Double[] series, int start, int length)
      throws IndexOutOfBoundsException {
    if (start + length > series.length) {
      throw new IndexOutOfBoundsException("Unable to extract subseries, series length: "
          + series.length + ", start: " + start + ", subseries length: " + length);
    }
    double[] res = new double[length];
    for (int i = 0; i < length; i++) {
      res[i] = series[start + i];
    }
    return res;
  }

  /**
   * Implements Gaussian smoothing.
   * 
   * @param series Data to process.
   * @param filterWidth The filter width.
   * @return smoothed series.
   * @throws TimeseriesException if error occurs.
   */
  public static double[] gaussFilter(double[] series, double filterWidth) throws TimeseriesException {

    double[] smoothedSignal = new double[series.length];
    double sigma = filterWidth / 2D;
    int maxShift = (int) Math.floor(4D * sigma); // Gaussian curve is reasonably > 0

    if (maxShift < 1) {
      throw new TimeseriesException("NOT smoothing: filter width too small - " + filterWidth);
    }
    for (int i = 0; i < smoothedSignal.length; i++) {
      smoothedSignal[i] = series[i];

      if (maxShift < 1) {
        continue;
      }
      for (int j = 1; j <= maxShift; j++) {

        double gaussFilter = Math.exp(-(j * j) / (2. * sigma * sigma));
        double leftAmpl, rightAmpl;

        // go left
        if ((i - j) >= 0) {
          leftAmpl = series[i - j];
        }
        else {
          leftAmpl = series[i];
        }

        // go right
        if ((i + j) <= smoothedSignal.length - 1) {
          rightAmpl = series[i + j];
        }
        else {
          rightAmpl = series[i];
        }

        smoothedSignal[i] += gaussFilter * (leftAmpl + rightAmpl);

      }

      double normalizingCoef = Math.sqrt(2. * Math.PI) * sigma;
      smoothedSignal[i] /= normalizingCoef;

    }
    return smoothedSignal;
  }

  public double gaussian(double x, double filterWidth) {
    double sigma = filterWidth / 2.;
    return Math.exp(-(x * x) / (2. * sigma * sigma));
  }

  public static String seriesToString(double[] series, NumberFormat df) {
    StringBuffer sb = new StringBuffer();
    sb.append('[');
    for (double d : series) {
      sb.append(df.format(d)).append(',');
    }
    sb.delete(sb.length() - 2, sb.length() - 1).append("]");
    return sb.toString();
  }
  
	public static void generateSubsequence(String saxCode, int seqI, Map<String, Count> subSeqPatterMap) {
		
		int index = 0;
		String subseq = null;
		int saxCodeT = saxCode.length();

		while(saxCodeT > index) {
			
			index++;
			if(index < seqI) continue;
			
			subseq = saxCode.substring(index-seqI, index);
			if(subSeqPatterMap.containsKey(subseq)) {
				subSeqPatterMap.get(subseq).count++;
			} else {
				subSeqPatterMap.put(subseq, new Count());
			}
		}
	}  
	
	public static class Count {
		public int count = 1; 
	}
	

}

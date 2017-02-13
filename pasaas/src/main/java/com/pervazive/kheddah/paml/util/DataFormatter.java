package com.pervazive.kheddah.paml.util;

import java.util.Collection;

public class DataFormatter {
	public static void concatStringToArray(Collection<String> stringCollection, String dataString, char separator) {

		if (dataString == null)
			return;
		if (dataString.length() == 0)
			return;

		int index1 = 0;
		int index2 = dataString.indexOf(separator);

		if (index2 >= 0) {
			String token = null;
			while (index2 >= 0) {
				token = dataString.substring(index1, index2);
				stringCollection.add(token);
				index1 = index2 + 1;
				index2 = dataString.indexOf(separator, index1);
				if (index2 < 0) index1--;
			}

			if (index1 <= dataString.length() - 1) {
				stringCollection.add(dataString.substring(index1 + 1));
			}

		} else {
			stringCollection.add(dataString);
		}
	}	
	
	public static void concatStringToArray(String[] stringArray, int[] positions, String dataString, char separator) {

		if (dataString == null)
			return;
		if (dataString.length() == 0)
			return;

		int index1 = 0;
		int index2 = dataString.indexOf(separator);

		int pos = -1;
		int resultSeq = 0;
		if (index2 >= 0) {
			String token = null;
			while (index2 >= 0) {
				pos++;
				for (int aPos : positions) {
					if (pos != aPos) continue;
					token = dataString.substring(index1, index2);
					stringArray[resultSeq++] = token;
					break;
				}
				index1 = index2 + 1;
				index2 = dataString.indexOf(separator, index1);
				if (index2 < 0) index1--;
			}

			if (index1 <= dataString.length() - 1) {
				pos++;
				for (int aPos : positions) {
					if (pos != aPos) continue;
					stringArray[resultSeq++] = dataString.substring(index1 + 1);
					break;
				}
			}

		} else {
			pos++;
			for (int aPos : positions) {
				if (pos != aPos) continue;
				stringArray[resultSeq++] = dataString;
				break;
			}
		}
	}

	public static void concatStringToArray(String[] stringArray, String dataString, char separator) {

		if (dataString == null)
			return;
		if (dataString.length() == 0)
			return;

		int index1 = 0;
		int index2 = dataString.indexOf(separator);

		int resultSeq = 0;
		if (index2 >= 0) {
			String token = null;
			while (index2 >= 0) {
				token = dataString.substring(index1, index2);
				stringArray[resultSeq++] = token;
				index1 = index2 + 1;
				index2 = dataString.indexOf(separator, index1);
				if (index2 < 0)
					index1--;
			}

			if (index1 <= dataString.length() - 1) {
				if ( stringArray.length > resultSeq)  
					stringArray[resultSeq++] = dataString.substring(index1 + 1);
			}

		} else {
			stringArray[resultSeq++] = dataString;
		}
	}

	private StringBuilder appender = new StringBuilder(65536);

	public String append1(String[] cols, char separator) {
		boolean isFirst = true;
		for (String col : cols) {
			if (isFirst)
				isFirst = false;
			else
				appender.append(separator);

			appender.append(col);
		}
		String value = appender.toString();
		appender.setLength(0);
		return value;

	}

}

package com.pervazive.kheddah.paml.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter {

	public static Long convertUnixtimeToJavatime(long sourceVal)
	{
		Date date = new Date(sourceVal * 1000L);
		return date.getTime();
	}

	public static String convertUnixtimeToDateformat(long sourceVal, String format)
	{
		Date date = new Date(sourceVal * 1000L);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static Long convertJavatimeToUnixtime(long sourceVal)
	{
		Date date = new Date(sourceVal);
		return (date.getTime() / 1000L);
	}

	public static String convertJavatimeToDateformat(long sourceVal, String format)
	{
		Date date = new Date(sourceVal);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static Long convertDateformatToUnixtime(String sourceVal, String format) throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = formatter.parse(sourceVal);
		return (date.getTime() / 1000L);
	}
	
	public static Long convertDateformatToJavatime(String sourceVal, String format) throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = formatter.parse(sourceVal);
		return date.getTime();
	}
	
	public static String convertDateformatToOtherFormat(String sourceVal, String srcFormat, String destFormat) throws ParseException
	{
		SimpleDateFormat srcFormatter = new SimpleDateFormat(srcFormat);
		SimpleDateFormat destFormatter = new SimpleDateFormat(destFormat);
		Date date = srcFormatter.parse(sourceVal);
		return destFormatter.format(date);
	}
}

package co.lateralview.interviewtest.ui.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils
{
	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'-3";
	public static final String UTC_TIME_ZONE = "UTC";

	public static String dateToTZString(Date date)
	{
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));
		return formatter.format(date);
	}
}

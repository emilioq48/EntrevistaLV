package co.lateralview.interviewtest.infrastrcuture.rest;

public class RestConstants
{
	public static final String API_DEV_URL = "http://10.0.1.8:3000/";

	public static String getUrl(String relativeUrl)
	{
		return API_DEV_URL + relativeUrl;
	}
}

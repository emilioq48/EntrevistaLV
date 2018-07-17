package co.lateralview.interviewtest.infrastrcuture.rest;

public class RestConstants
{
	private static final String API_DEV_URL = "http://192.168.0.158:3000/";

	public static String getUrl(String relativeUrl)
	{
		return API_DEV_URL + relativeUrl;
	}
}

package co.lateralview.interviewtest.infrastrcuture.rest.request;

import android.os.Bundle;

import com.android.volley.Request;

import net.lateralview.simplerestclienthandler.RestClientManager;
import net.lateralview.simplerestclienthandler.base.RequestHandler;

import co.lateralview.interviewtest.infrastrcuture.rest.RestConstants;

public class ThreadRequest
{
	protected static final String TAG = ThreadRequest.class.getSimpleName();

	public enum Url
	{
		GET_THREADS(RestConstants.getUrl("threads")),
		GET_THREAD_COMMENTS(RestConstants.getUrl("thread/{id}/comments?parent=none")),
		GET_NESTED_COMMENTS(RestConstants.getUrl("thread/{id}/comments?parent={parent_id}")),
		ADD_THREAD(RestConstants.getUrl("threads"));

		private String mUrl;

		Url(String url)
		{
			mUrl = url;
		}

		public String getUrl()
		{
			return mUrl;
		}
	}

	public static class Parameters
	{
		public static final String THREAD_ID = "thread_id";
		public static final String PARENT_ID = "parent_id";
	}

	private static String getThreadCommentsUrl(Bundle parameters)
	{
		return Url.GET_THREAD_COMMENTS.getUrl().replace("{id}", parameters.getString(Parameters.THREAD_ID));
	}

	private static String getNestedCommentsUrl(Bundle parameters)
	{
		return Url.GET_NESTED_COMMENTS.getUrl().replace("{id}", parameters.getString(Parameters.THREAD_ID))
				.replace("{parent_id}", parameters.getString(Parameters.PARENT_ID));
	}

//	public static void getThreads(RequestHandler requestHandler)
//	{
//		RestClientManager.getInstance().makeJsonArrayRequest(Request.Method.GET, Url.GET_THREADS.getUrl(), requestHandler);
//	}

	public static void getThreads(RequestHandler requestHandler)
	{
		RestClientManager.getInstance().makeJsonArrayRequest(Request.Method.GET, "http://192.168.0.156", requestHandler);
	}

	public static void getThreadComments(RequestHandler requestHandler, Bundle urlParameters)
	{
		RestClientManager.getInstance().makeJsonArrayRequest(Request.Method.GET, getThreadCommentsUrl(urlParameters), requestHandler);
	}

	public static void getNestedComments(RequestHandler requestHandler, Bundle urlParameters)
	{
		RestClientManager.getInstance().makeJsonArrayRequest(Request.Method.GET, getNestedCommentsUrl(urlParameters), requestHandler);
	}

	public static void addThread(RequestHandler requestHandler)
	{
		RestClientManager.getInstance().makeJsonRequest(Request.Method.POST, Url.ADD_THREAD.getUrl(), requestHandler);
	}

}

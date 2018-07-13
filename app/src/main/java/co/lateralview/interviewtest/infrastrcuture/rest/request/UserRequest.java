package co.lateralview.interviewtest.infrastrcuture.rest.request;

import com.android.volley.Request;

import net.lateralview.simplerestclienthandler.RestClientManager;
import net.lateralview.simplerestclienthandler.base.RequestHandler;

import co.lateralview.interviewtest.infrastrcuture.rest.RestConstants;

public class UserRequest
{
	protected static final String TAG = UserRequest.class.getSimpleName();

	public enum Url
	{
		GET_USERS(RestConstants.getUrl("users"));

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

	public static void getUsers(RequestHandler requestHandler)
	{
		RestClientManager.getInstance().makeJsonArrayRequest(Request.Method.GET, Url.GET_USERS.getUrl(), requestHandler);
	}
}

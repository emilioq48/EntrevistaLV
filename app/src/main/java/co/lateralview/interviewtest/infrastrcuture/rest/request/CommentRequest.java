package co.lateralview.interviewtest.infrastrcuture.rest.request;

import com.android.volley.Request;

import net.lateralview.simplerestclienthandler.RestClientManager;
import net.lateralview.simplerestclienthandler.base.RequestHandler;

import co.lateralview.interviewtest.infrastrcuture.rest.RestConstants;

public class CommentRequest
{
	protected static final String TAG = CommentRequest.class.getSimpleName();

	public enum Url
	{
		ADD_COMMENT(RestConstants.getUrl("comments"));

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

	public static void addComment(RequestHandler requestHandler)
	{
		RestClientManager.getInstance().makeJsonRequest(Request.Method.POST, Url.ADD_COMMENT.getUrl(), requestHandler);
	}
}

package co.lateralview.interviewtest.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Thread implements Serializable
{
	@SerializedName("id")
	private String mId;

	@SerializedName("title")
	private String mTitle;

	@SerializedName("url")
	private String mUrl;

	@SerializedName("created_at")
	private String mCreatedAt;

	@SerializedName("username")
	private String mUserName;

	public Thread(String mTitle, String mUrl, String mCreatedAt, String mUserName)
	{
		this.mTitle = mTitle;
		this.mUrl = mUrl;
		this.mCreatedAt = mCreatedAt;
		this.mUserName = mUserName;
	}

	public String getId()
	{
		return mId;
	}

	public String getUrl()
	{
		return mUrl;
	}

	public String getTitle()
	{
		return mTitle;
	}

	public String getCreatedAt()
	{
		return mCreatedAt;
	}

	public String getUserName()
	{
		return mUserName;
	}
}

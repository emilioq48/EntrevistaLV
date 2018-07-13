package co.lateralview.interviewtest.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Comment implements Serializable
{
	@SerializedName("id")
	private String mId;

	@SerializedName("threadId")
	private String mThreadId;

	@SerializedName("username")
	private String mUsername;

	@SerializedName("text")
	private String mText;

	@SerializedName("parent")
	private String mParent;

	@SerializedName("created_at")
	private String mCreatedAt;

	public Comment(String mThreadId, String mUsername, String mText, String mParent, String mCreatedAt)
	{
		this.mThreadId = mThreadId;
		this.mUsername = mUsername;
		this.mText = mText;
		this.mParent = mParent;
		this.mCreatedAt = mCreatedAt;
	}

	public String getId()
	{
		return mId;
	}

	public String getThreadId()
	{
		return mThreadId;
	}

	public String getUsername()
	{
		return mUsername;
	}

	public String getText()
	{
		return mText;
	}

	public String getParent()
	{
		return mParent;
	}

	public String getCreatedAt()
	{
		return mCreatedAt;
	}
}

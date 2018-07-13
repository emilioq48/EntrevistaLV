package co.lateralview.interviewtest.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable
{
	public static final String SHARED_PREFERENCE_KEY = "SharedPreferenceUser";

	@SerializedName("username")
	private String mUsername;

	@SerializedName("password_hash")
	private String mPasswordHash;

	@SerializedName("password_seed")
	private String mPasswordSeed;

	@SerializedName("created_at")
	private String mCreatedAt;

	public User(String mUsername)
	{
		this.mUsername = mUsername;
	}

	public String getUsername()
	{
		return mUsername;
	}

	public String getPasswordHash()
	{
		return mPasswordHash;
	}

	public String getPasswordSeed()
	{
		return mPasswordSeed;
	}

	public String getCreatedAt()
	{
		return mCreatedAt;
	}
}

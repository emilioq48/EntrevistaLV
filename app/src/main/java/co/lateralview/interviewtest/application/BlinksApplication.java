package co.lateralview.interviewtest.application;

import android.app.Application;

import net.lateralview.simplerestclienthandler.RestClientManager;

public class BlinksApplication extends Application
{
	protected static BlinksApplication sInstance;

	@Override
	public void onCreate()
	{
		super.onCreate();
		sInstance = this;

		initializeServices();
	}

	public static BlinksApplication getInstance()
	{
		return sInstance;
	}

	private void initializeServices()
	{
		RestClientManager.initialize(getInstance()).enableDebugLog(true);
	}

}
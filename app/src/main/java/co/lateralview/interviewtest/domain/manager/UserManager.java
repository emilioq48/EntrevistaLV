package co.lateralview.interviewtest.domain.manager;

import android.content.Context;

import co.lateralview.interviewtest.domain.model.User;
import co.lateralview.interviewtest.infrastrcuture.manager.SharedPreferencesManager;

public class UserManager
{
	public static boolean isUserLoggedIn(Context context)
	{
		User currentUser = SharedPreferencesManager.get(context, User.SHARED_PREFERENCE_KEY, User.class);
		return currentUser != null;
	}
}

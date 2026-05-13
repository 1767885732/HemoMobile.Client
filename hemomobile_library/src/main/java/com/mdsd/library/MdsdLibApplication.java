package com.mdsd.library;

import android.app.Application;
import android.content.Context;

public class MdsdLibApplication extends Application {
	private static Context applicationContext;

	public void onCreate() {
		super.onCreate();

		applicationContext = this.getApplicationContext();
	}

	public static Context getContext() {
		return applicationContext;
	}
}

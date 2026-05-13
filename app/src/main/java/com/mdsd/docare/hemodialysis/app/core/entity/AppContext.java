package com.mdsd.docare.hemodialysis.app.core.entity;

import com.mdsd.docare.hemodialysis.app.entity.server.MedUser;

/**
 * 应用程序上下文
 * 
 * <br>
 * 
 * @author peter.lv
 * @since 2015年4月25日
 */
public class AppContext {
	private static AppContext instance = new AppContext();
	public MedUser currentUser=null;
	
	public static AppContext getInstance()
	{
		return instance;
	}
}

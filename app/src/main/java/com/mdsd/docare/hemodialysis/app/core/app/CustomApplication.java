package com.mdsd.docare.hemodialysis.app.core.app;

import com.mdsd.library.manage.single.PrefrencesManage;
import com.mdsd.library.manage.single.VolleyNetCall;

import android.app.Application;

/**
 * 自定义的应用Application
 * 
 * <p>
 * 说明：应用配置等初始化
 * </p>
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月15日
 */
public class CustomApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		// 设置prefrence缓存
		PrefrencesManage.getInstance().init(this, "Hemodialysis");
		
		// 初始化volley的访问序列
		VolleyNetCall.getInstance().init(this);
	}
}

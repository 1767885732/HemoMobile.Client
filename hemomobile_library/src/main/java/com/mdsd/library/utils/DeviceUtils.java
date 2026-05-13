package com.mdsd.library.utils;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * DeviceUtils
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月24日
 */
public class DeviceUtils {
	/**
	 * 关闭软键盘
	 * 
	 * @param context
	 */
	public static void closeKeyboard(Activity context) {
		if(!isKeyboardOpen(context))
			return;
		
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	/**
	 * 关闭软键盘
	 * 
	 * @param context
	 * @param v
	 */
	public static void closeKeyboard(Context context,View v) {
		if(!isKeyboardOpen(context))
			return;
		
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	/**
	 * 判断键盘是否打开状态
	 *
	 * @param context
	 * @return
	 */
	public static boolean isKeyboardOpen(Context context){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE); 
		return imm.isActive();
	}

	/**
	 * 
	 * 是否存在sdcard
	 * 
	 * @return boolean
	 */
	public static boolean isSdCardEnable() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable();
	}

	/**
	 * 获取当前app可用的存储空间
	 * 
	 * @param context
	 * @param fileDir
	 *            文件目录
	 * @return 如果sdcard可用，则默认返回sdcard的存储路径，否则返回应用程序下的相关目录
	 */
	public static String getAppEnableDir(Context context, String fileDir) {
		if (isSdCardEnable()) {// sdcardb可用
			return Environment.getExternalStorageDirectory() + File.separator
					+ context.getPackageName() + File.separator + fileDir;
		} else {
			return context.getFilesDir().getAbsolutePath() + File.separator + fileDir;
		}
	}

	/**
	 * 获取当前应用的版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getAppVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 获取缓存文件
	 *
	 * @param context
	 * @param uniqueName
	 * @return
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {  
	    String cachePath;  
	    if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	            || !Environment.isExternalStorageRemovable()) && context.getExternalCacheDir() != null) {  
	        cachePath = context.getExternalCacheDir().getPath();  
	    } else {  
	        cachePath = context.getCacheDir().getPath();  
	    }  
	    return new File(cachePath + File.separator + uniqueName);  
	}  
}

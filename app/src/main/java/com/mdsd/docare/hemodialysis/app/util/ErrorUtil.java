package com.mdsd.docare.hemodialysis.app.util;

import com.android.volley.VolleyError;

public class ErrorUtil {

	/**
	 * 构建错误信息
	 *
	 * @param error
	 * @param tag
	 * @return
	 */
	public static String buildError(VolleyError error, String tag){
		try {
			if(error.networkResponse != null)
				return String.format("%s：statusCode:%s，errorMsg:%s", tag,error.networkResponse.statusCode,error.getMessage());
			else
				return String.format("%s：errorMsg:%s", tag,error.getMessage());
		} catch (Exception e) {
			return "buildError failed";
		}
		
	}
	
	/**
	 * 构建错误信息
	 *
	 * @param tag
	 * @param msg
	 * @return
	 */
	public static String buildError(String tag, String msg){
		return String.format("%s：%s，errorMsg:%s", tag,msg);
	}
}

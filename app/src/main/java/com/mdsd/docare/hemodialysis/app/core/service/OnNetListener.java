package com.mdsd.docare.hemodialysis.app.core.service;

/**
 * 网络访问监听
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月21日
 */
public interface OnNetListener<T> {

	/**
	 * 访问成功
	 * 
	 * @param t
	 */
	void onResponse(T object);

	/**
	 * 访问失败
	 * 
	 * @param errorMessage
	 */
	void onError(String errorMessage);
}

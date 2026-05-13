package com.mdsd.library.manage.single;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.toolbox.Volley;

/**
 * 运用google官方的 http访问框架
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年7月3日
 */
public class VolleyNetCall {
	private static String COOKIE = "Cookie";
	private static String TOKEN="Token";
	private RequestQueue mRequestQueue;
	private Map<String, String> headers = new HashMap<String, String>();
	/**
	 * 缓存是否可以控制，默认为false(即服务端headers控制)，true即客户端控制有效期等<br>
	 * 1.可设置request.header,如headers.put("Cache-Control", "max-age=60")<br>
	 * 2.add(request,60),前提isCacheControlEnable为true
	 * 
	 * @author lijianyu
	 * @since 2014-8-28 17:49:57
	 */
	boolean isCacheControlEnable = false;

	private DefaultRetryPolicy defaultRetryPolicy;

	
	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	private static class EasyNetCallHolder {
		static VolleyNetCall instance = new VolleyNetCall();
	}

	public static VolleyNetCall getInstance() {
		return EasyNetCallHolder.instance;
	}

	/**
	 * 初始化网路访问模块
	 *
	 * @param context
	 */
	public void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
	}

	/**
	 * 初始化网路访问模块
	 *
	 * @param context
	 * @param headers (访问的头部公共设置)
	 */
	public void init(Context context, Map<String, String> headers) {
		this.init(context, headers, false);
	}
	
	/**
	 * 初始化网路访问模块
	 *
	 * @param context
	 * @param cacheControlEnable (客户端是否可以控制缓存)
	 */
	public void init(Context context, boolean cacheControlEnable) {
		mRequestQueue = Volley.newRequestQueue(context);
		this.isCacheControlEnable = cacheControlEnable;
	}
	
	/**
	 * 初始化网路访问模块
	 *
	 * @param context
	 * @param headers (访问的头部公共设置)
	 * @param cacheControlEnable (客户端是否可以控制缓存)
	 */
	public void init(Context context, Map<String, String> headers,boolean cacheControlEnable) {
		mRequestQueue = Volley.newRequestQueue(context);
		this.isCacheControlEnable = cacheControlEnable;
		this.headers.putAll(headers);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
	
	/**
	 * 设置token
	 */
	public void setToken(String token){
		this.headers.put(TOKEN, token);
	}

	/**
	 * 设置访问http的cookie
	 * 
	 * @param cookie
	 */
	public void setHttpCookie(String cookie) {
		this.headers.put(COOKIE, cookie);
	}

	/**
	 * set call http hearders
	 * 
	 * @param headers
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * 进行网络访问
	 * 
	 * @param request
	 *            The request to service
	 * @return The passed-in request
	 */
	public Request<?> add(Request<?> request) {
		return add(request, 0);
	}
	
	/**
	 * 进行网络访问
	 *
	 * @param request
	 * @param cacheSecond (缓存时间,单位秒，前提设置setCacheControlEnable(true)，否则不起作用)
	 * @return
	 */
	public Request<?> add(Request<?> request,long cacheSecond) {
		// 请求header
		Map<String, String> reqHeaders = new HashMap<String, String>();
		
		if(this.headers != null)
			reqHeaders.putAll(this.headers);
		
		try {
			reqHeaders.putAll(request.getHeaders());
			
			if(cacheSecond > 0){// 如果需要控制缓存
				reqHeaders.put("Cache-Control", "max-age="+cacheSecond);
			}
			
			request.setCacheControlEnable(isCacheControlEnable);
			
			// 重新设置请求的header
			request.getHeaders().clear();
			request.getHeaders().putAll(reqHeaders);
			
			if(defaultRetryPolicy!=null)
				request.setRetryPolicy(defaultRetryPolicy);
		
			
		} catch (AuthFailureError e) {
			e.printStackTrace();
		}
		
		return mRequestQueue.add(request);
	}

	/**
	 * Cancels all requests in this queue with the given tag. Tag must be
	 * non-null and equality is by identity.
	 * 
	 * @param tag
	 */
	public void cancelAll(Object tag) {
		mRequestQueue.cancelAll(tag);
	}

	/**
	 * Cancels all requests in this queue for which the given filter applies.
	 * 
	 * 
	 * @param filter
	 *            The filtering function to use
	 */
	public void cancelAll(RequestFilter filter) {
		mRequestQueue.cancelAll(filter);
	}

	/**
	 * Starts the dispatchers in this queue.
	 */
	public void start() {
		mRequestQueue.start();
	}

	/**
	 * Starts the dispatchers in this queue.
	 */
	public void stop() {
		mRequestQueue.stop();
	}

	/**
	 * 缓存是否可以控制，默认为false(即服务端headers控制)，true即客户端控制有效期等<br>
	 * 1.可设置request.header,如headers.put("Cache-Control", "max-age=60")<br>
	 * 2.add(request,60),前提isCacheControlEnable为true<br>
	 * <br>
	 * <b>说明：无特殊需求，建议以服务端控制为主<b> <br>
	 * <b>使用方式：在application中进行初始化调用<b>
	 * 
	 * @return true, if is cache control enable
	 */
	public boolean isCacheControlEnable() {
		return isCacheControlEnable;
	}

	/**
	 * 缓存是否可以控制，默认为false(即服务端headers控制)，true即客户端控制有效期等<br>
	 * 1.可设置request.header,如headers.put("Cache-Control", "max-age=60")<br>
	 * 2.add(request,60),前提isCacheControlEnable为true<br>
	 * <br>
	 * <b>说明：无特殊需求，建议以服务端控制为主<b> <br>
	 * <b>使用方式：在application中进行初始化调用<b>
	 * 
	 * @param isCacheControlEnable
	 *            the new cache control enable
	 */
	public void setCacheControlEnable(boolean isCacheControlEnable) {
		this.isCacheControlEnable = isCacheControlEnable;
	}
	
	/**
	 * 超时设置
	 * @param timeOut
	 * @param retryCount
	 */
	public void setRetryPolicy(int timeOut,int retryCount){
		 defaultRetryPolicy = new DefaultRetryPolicy(timeOut, retryCount, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
	}
}

package com.mdsd.library.manage.single;

import java.util.List;
import java.util.UUID;

import com.mdsd.library.net.http.HttpClient;
import com.mdsd.library.net.http.HttpHeaders;
import com.mdsd.library.net.http.HttpParams;
import com.mdsd.library.net.http.HttpResponse;

/**
 * 
 * 默认的访问服务的httpclient单例
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月10日
 */
public class HttpDefClient {

	private static String COOKIE = "Cookie";

	private HttpClient httpClient = new HttpClient();
	private HttpHeaders httpHeaders = new HttpHeaders();

	private static class HttpDefClientHolder {
		static HttpDefClient instance = new HttpDefClient();
	}

	public static HttpDefClient getInstance() {
		return HttpDefClientHolder.instance;
	}

	/**
	 * 设置Token
	 *
	 * @param token
	 */
	public void setToken(String token) {
		httpHeaders.addHeader("Token", token);
	}

	/**
	 * 设置cookie
	 *
	 * @param cookie
	 */
	public void setCookie(String cookie){
		httpHeaders.addHeader(COOKIE, cookie);
	}

	/**
	 * get 方式访问服务
	 *
	 * @param url
	 * @param httpParams
	 * @param requestId
	 * @return
	 */
	public HttpResponse get(String url, HttpParams httpParams, String requestId) {

		return httpClient.get(url, this.httpHeaders,
				httpParams, requestId);
	}

	/**
	 * post的方式访问服务
	 *
	 * @param url
	 * @param httpParams
	 * @param requestId
	 * @return
	 */
	public HttpResponse post(String url, HttpParams httpParams, String requestId) {

		return httpClient.post(url, this.httpHeaders,
				httpParams, requestId);
	}
	
	/**
	 * 获取对应的cookie
	 *
	 * @param httpResponse
	 * @return
	 */
	public String getCookie(HttpResponse httpResponse) {
		
		if(httpResponse == null)
			return "";
		
		try {
			List<String> fields = httpResponse.headerFields.get("set-cookie");
			
			for (String cookie : fields) {
				return cookie.split(";", 2)[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 取消对应的网络访问请求
	 * 
	 * @param requestId
	 */
	public void cancel(String requestId) {
		httpClient.cancel(requestId);
	}

	/**
	 * 获取请求的requestId
	 * 
	 * @return
	 */
	public synchronized String getRequestId() {
		return UUID.randomUUID().toString();
	}
}

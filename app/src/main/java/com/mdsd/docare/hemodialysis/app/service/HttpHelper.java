package com.mdsd.docare.hemodialysis.app.service;

import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.mdsd.library.utils.StringUtils;

/**
 * @author mj.zhou
 * @version create��2014-2-21 ����10:03:36 http操作类
 */
public class HttpHelper {
	private static HttpClient mHttpClient;

	/**
	 * 获取httpClient实例
	 * 
	 * @return
	 */
	public static HttpClient getHttpClient() {
		if (mHttpClient == null) {
			SchemeRegistry supportedSchemes = new SchemeRegistry();
			PlainSocketFactory sf = PlainSocketFactory.getSocketFactory();
			supportedSchemes.register(new Scheme("http", sf, 80));
			// self added ---设置HttpClient支持HTTP和HTTPS两种模式
			supportedSchemes.register(new Scheme("https", sf, 443));
			HttpParams params = createHttpParams();
			HttpClientParams.setRedirecting(params, false);
			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, supportedSchemes);
			mHttpClient = new DefaultHttpClient(conMgr, params);
		}

		return mHttpClient;
	}

	private static HttpParams createHttpParams() {
		final HttpParams params = new BasicHttpParams();

		// Turn off stale checking. Our connections break all the time anyway,
		// and it's not worth it to pay the penalty of checking every time.
		HttpConnectionParams.setStaleCheckingEnabled(params, false);

		// self added --- 设置一些基本参数 HTTP请求执行参数
		org.apache.http.params.HttpProtocolParamBean paramsBean = new org.apache.http.params.HttpProtocolParamBean(
				params);
		paramsBean.setVersion(HttpVersion.HTTP_1_1);// 定义使用HTTP协议版本,
													// 如果不设置此参数将使用HTTP/1.1
		paramsBean.setContentCharset(HTTP.UTF_8);// 定义字符集用于每个内容体的默认编码
													// ,如果不设置此参数将使用ISO-8859-1
		ConnManagerParams.setTimeout(params, 5000); /* 从连接池中取连接的超时时间 */
		// self added ---

		// 连接管理
		HttpConnectionParams.setConnectionTimeout(params, 5000);// 建立连接的超时时间（以毫秒为单位）当值为0被解释成一个无限超时,如果此参数不设置，连接操作不会超时（无限超时）。
		HttpConnectionParams.setSoTimeout(params, 5000); // 以毫秒为单位定义套接字超时（SO_TIMEOUT）。当值为0被解释成一个无限的暂停,如果此参数不设置，读操作不会超时（无限暂停）。
		HttpConnectionParams.setSocketBufferSize(params, 8192);// 接收/传输HTTP消息时，确定socket内部缓冲区缓存数据的大小,如果此参数不设置，HttpClient将分配8192字节scoket缓冲区

		return params;
	}

	/**
	 * 执行http请求 根据状态码判断是否成功 200，204为成功，其他都为失败
	 * 
	 * @param httpRequest
	 * @return
	 * @throws Exception
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	protected static String execRequest(HttpRequestBase httpRequest)
			throws Exception {
		HttpResponse response = null;
		try {
			response = getHttpClient().execute(httpRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String msg = e.getMessage();
			if (StringUtils.isEmpty(msg)) {
				msg = e.toString();
				if (e.getClass() == java.net.SocketTimeoutException.class) {
					msg = "网络连接超时";
				}
			}
			throw new Exception("发送请求异常:" + msg);
		}

		int status = 200;
		try {
			status = response.getStatusLine().getStatusCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String msg = e.getMessage();
			if (StringUtils.isEmpty(msg)) {
				msg = e.toString();
				if (e.getClass() == java.net.SocketTimeoutException.class) {
					msg = "网络连接超时";
				}
			}
			throw new Exception("获取状态码异常:" + msg);
		}

		if (status == 200) {
			try {
				return EntityUtils.toString(response.getEntity());
			} catch (Exception e) {
				String msg = e.getMessage();
				if (StringUtils.isEmpty(msg)) {
					msg = e.toString();
					if (e.getClass() == java.net.SocketTimeoutException.class) {
						msg = "网络连接超时";
					}
				}
				throw new Exception("获取响应体异常:" + msg);
			}
		} else if (status == 204) {
			return "";
		} else if (status == 401) {
			response.getEntity().consumeContent();
			throw new HttpException("StatusCode:401 网络异常");
		} else if (status == 404) {
			response.getEntity().consumeContent();
			throw new HttpException("StatusCode:404 网络异常");
		} else {
			response.getEntity().consumeContent();
			throw new HttpException("StatusCode:null 网络异常");
		}
	}

	/**
	 * 执行 httpGet请求
	 * 
	 * @param url
	 *            请求地址
	 * @param nameValuePairs
	 *            附带参数
	 * @return 服务器返回数据
	 * @throws Exception
	 */
	public static String doHttpGet(String url,
			List<NameValuePair> nameValuePairs) throws Exception {
		String query = URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8);
		HttpGet httpGet = new HttpGet(url + "?" + query);
		return execRequest(httpGet);
	}

	/**
	 * 执行 httpGet请求
	 * 
	 * @param url
	 *            请求地址
	 * @return 服务器返回数据
	 * @throws Exception
	 */
	public static String doHttpGet(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		return execRequest(httpGet);
	}

	/**
	 * 执行 httpDelete请求
	 * 
	 * @param url
	 * @return 服务器返回数据
	 * @throws Exception
	 */
	public static String doHttpDelete(String url) throws Exception {
		HttpDelete httpDelete = new HttpDelete(url);
		return execRequest(httpDelete);
	}

	/**
	 * 执行httpDelete请求
	 * 
	 * @param url
	 *            请求URL
	 * @param nameValuePairs
	 *            附带参数
	 * @return 服务器返回数据
	 * @throws Exception
	 */
	public static String doHttpDelete(String url,
			List<NameValuePair> nameValuePairs) throws Exception {
		String query = URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8);
		HttpDelete httpDelete = new HttpDelete(url + "?" + query);
		return execRequest(httpDelete);
	}

	/**
	 * 执行httpPost请求
	 * 
	 * @param url
	 *            请求URL
	 * @param nameValuePairs
	 *            附带参数
	 * @return 服务器返回数据
	 * @throws Exception
	 */
	public static String doHttpPost(String url,
			List<NameValuePair> nameValuePairs) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(
				nameValuePairs, HTTP.UTF_8));
		return execRequest(httpPost);
	}

	/**
	 * 执行httpPost请求
	 * 
	 * @param url
	 *            请求URL
	 * @param json
	 *            提交的json数据
	 * @return 服务器返回数据
	 * @throws Exception
	 */
	public static String doHttpPost(String url, String json) throws Exception {

		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(new BasicHeader("Content-Type", "application/json"));
		httpPost.setEntity(new StringEntity(json, HTTP.UTF_8));
		return execRequest(httpPost);
	}

	/**
	 * 执行httpPut请求
	 * 
	 * @param url
	 *            请求URL
	 * @param json
	 *            提交的json数据
	 * @return 服务器返回数据
	 * @throws Exception
	 */
	public static String doHttpPut(String url, String json) throws Exception {

		HttpPut httpPut = new HttpPut(url);
		httpPut.addHeader(new BasicHeader("Content-Type", "application/json"));
		httpPut.setEntity(new StringEntity(json, HTTP.UTF_8));
		return execRequest(httpPut);
	}
}

package com.mdsd.library.net.service.task;

import com.mdsd.library.net.http.HttpClient.Method;
import com.mdsd.library.net.http.HttpEntityParam;
import com.mdsd.library.net.http.HttpResponse;

/**
 * 返回结果为HttpResponse的task，继承至{@link HttpTask}
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月12日
 */
public class HttpResponseTask extends HttpTask<HttpResponse> {

	public HttpResponseTask(
			TaskListener<HttpEntityParam, String, HttpResponse> taskListener,
			Method method) {
		super(taskListener, method);
		// TODO Auto-generated constructor stub
	}

	public HttpResponseTask(
			TaskListener<HttpEntityParam, String, HttpResponse> taskListener) {
		super(taskListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected HttpResponse doInBackground(HttpEntityParam... params) {
		HttpEntityParam httpEntityParam = params[0];

		if (this.method == Method.GET) {
			httpResponse = httpClient.get(httpEntityParam.getUrl(),
					httpEntityParam.getHttpParams(), getRequestId());
		}

		if (this.method == Method.POST) {
			httpResponse = httpClient.post(httpEntityParam.getUrl(),
					httpEntityParam.getHttpParams(), getRequestId());
		}

		return httpResponse;
	}

}

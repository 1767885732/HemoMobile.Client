package com.mdsd.library.net.service.task;

import com.mdsd.library.manage.single.HttpDefClient;
import com.mdsd.library.net.http.HttpClient.Method;
import com.mdsd.library.net.http.HttpEntityParam;
import com.mdsd.library.net.http.HttpResponse;

/**
 * http 访问task 默认为get方式
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月10日
 */
public class HttpTask<Result> extends BaseTask<HttpEntityParam, String, Result> {

	protected Method method;
	protected HttpDefClient httpClient;
	protected HttpResponse httpResponse = null;
	protected String requestId = "";

	public HttpTask(TaskListener<HttpEntityParam, String, Result> taskListener) {
		this(taskListener, Method.GET);
	}

	public HttpTask(TaskListener<HttpEntityParam, String, Result> taskListener,
			Method method) {
		super(taskListener);
		this.method = method;
		httpClient = HttpDefClient.getInstance();
	}

	@Override
	protected Result doInBackground(HttpEntityParam... params) {
		return null;
	}

	protected String getRequestId() {
		requestId = httpClient.getRequestId();
		return requestId;
	}
}

package com.mdsd.library.net.service.task;

import com.alibaba.fastjson.JSON;
import com.mdsd.library.manage.single.HttpDefClient;
import com.mdsd.library.net.http.HttpClient.Method;
import com.mdsd.library.net.http.HttpEntityParam;
import com.mdsd.library.utils.log.Log;

/**
 * 返回ServiceResponse的httpTask 继承至{@link HttpTask}
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月12日
 */
public class ServiceResponseTask extends HttpTask<ServiceResponse> {

	public ServiceResponseTask(
			TaskListener<HttpEntityParam, String, ServiceResponse> taskListener,
			Method method) {
		super(taskListener, method);
	}

	public ServiceResponseTask(
			TaskListener<HttpEntityParam, String, ServiceResponse> taskListener) {
		super(taskListener);
	}

	@Override
	protected ServiceResponse doInBackground(HttpEntityParam... params) {
		HttpEntityParam httpEntityParam = params[0];

		HttpDefClient httpClient = HttpDefClient.getInstance();

		if (this.method == Method.GET) {
			httpResponse = httpClient.get(httpEntityParam.getUrl(),
					httpEntityParam.getHttpParams(), getRequestId());
		}

		if (this.method == Method.POST) {
			httpResponse = httpClient.post(httpEntityParam.getUrl(),
					httpEntityParam.getHttpParams(), getRequestId());
		}

		try {
			if (httpResponse.responseCode < 400)
				return JSON.parseObject(httpResponse.responseBody,
						ServiceResponse.class);
			else {
				String err = String.format("errCode: %s; errMsg: %s", httpResponse.responseCode,httpResponse.responseMessage);
				Log.e(tag,err);
				ServiceResponse response = new ServiceResponse();
				response.setData("");
				response.setSuccess(false);
				response.setMessage(err);
				return response;
			}
		} catch (Exception e) {
			ServiceResponse response = new ServiceResponse();
			response.setData("");
			response.setSuccess(false);
			response.setMessage(e.getMessage());
			return response;
		}

	}
}

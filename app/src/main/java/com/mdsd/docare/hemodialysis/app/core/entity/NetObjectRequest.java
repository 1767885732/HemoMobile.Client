package com.mdsd.docare.hemodialysis.app.core.entity;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.mdsd.docare.hemodialysis.app.util.DataUtil;

/**
 * 针对服务端的返回的带//json的处理
 * 
 * <br>
 * @author jianyu.l
 * @since 2014年9月2日
 */
public class NetObjectRequest<T> extends JsonRequest<T> {

	int type = DataUtil.TYPE_JSONOBJECT;
	String dataTag = "";
	Class<?> classType;
	
	/**
	 * 
	 * @param method
	 * @param url
	 * @param requestBody
	 * @param listener
	 * @param errorListener
	 * @param type （DataUtil.TYPE_JSONOBJECT，DataUtil.TYPE_JSONARRAY）
	 * @param dataTag
	 */
	public NetObjectRequest(int method, String url, JSONObject jsonRequest,
			Listener<T> listener, ErrorListener errorListener,int type,String dataTag,Class<?> classType) {
		super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
		this.type = type;
		this.dataTag = dataTag;
		this.classType =classType;
		super.setRetryPolicy(new DefaultRetryPolicy(10000,0,1f));
	}
	
	/**
	 * 
	 * @param url
	 * @param jsonRequest
	 * @param listener
	 * @param errorListener
	 * @param type
	 * @param dataTag
	 * @param classType
	 */
	public NetObjectRequest(String url, JSONObject jsonRequest,
			Listener<T> listener, ErrorListener errorListener,int type,String dataTag,Class<?> classType) {
		super((jsonRequest == null)?Method.GET:Method.POST, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
		this.type = type;
		this.dataTag = dataTag;
		this.classType =classType;
		super.setRetryPolicy(new DefaultRetryPolicy(10000,0,1f));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			return (Response<T>) Response.success(
					DataUtil.getResult(jsonString, dataTag, type, classType),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}
}

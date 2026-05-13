package com.mdsd.docare.hemodialysis.app.core.service;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.mdsd.docare.hemodialysis.app.core.entity.AppSingleton;
import com.mdsd.docare.hemodialysis.app.core.entity.Constants;
import com.mdsd.docare.hemodialysis.app.core.entity.NetObjectRequest;
import com.mdsd.docare.hemodialysis.app.util.DataUtil;
import com.mdsd.docare.hemodialysis.app.util.ErrorUtil;
import com.mdsd.docare.hemodialysis.app.util.JSONStringHandler;
import com.mdsd.library.manage.single.VolleyNetCall;


/**
 * BaseService
 * 
 * <br>
 * @author jianyu.l
 * @since 2014年8月21日
 */
public class BaseService implements IService {
	protected static int TYPE_JSONOBJECT = 0;
	protected static int TYPE_JSONARRAY = 1;
	
	protected static VolleyNetCall volleyNetCall = VolleyNetCall.getInstance();
	
	/**
	 * 返回服务对应的URL地址
	 *
	 * @param tag
	 * @return
	 */
	protected static String getUrl(String tag){
		return String.format("http://%s%s", AppSingleton.getInstance().IP_ADDRESS,tag);
	};
	
	/**
	 * 获取封装过的geturl
	 *
	 * @param url
	 * @param nameValuePairs
	 * @return
	 * @throws Exception
	 */
	protected static String getGetUrl(String url,List<NameValuePair> nameValuePairs) throws Exception{
		String query = URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8);
		return url + "?" + query;
	}
	
	/**
	 * 获取封装过的geturl
	 *
	 * @param tag
	 * @param nameValuePairs
	 * @return
	 * @throws Exception
	 */
	protected static String getGetTagUrl(String tag,List<NameValuePair> nameValuePairs){
		String query = URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8);
		return getUrl(tag) + "?" + query;
	}
	
	
	/**
	 * 获取对应的实体类对象
	 *
	 * @param rJsonStr
	 * @param dataTag
	 * @param classType
	 * @return
	 * @deprecated (use DataUtil.getObject)
	 */
	protected static <T>  T getObject(String rJsonStr,String dataTag, Class<?> classType){
		return getResult(rJsonStr,dataTag,0,classType);
	}
	
	/**
	 * 获取对应的集合
	 *
	 * @param rJsonStr
	 * @param dataTag
	 * @param classType
	 * @return
	 * * @deprecated (use DataUtil.getList)
	 */
	protected static <T>  T getList(String rJsonStr,String dataTag, Class<?> classType){
		return getResult(rJsonStr,dataTag,1,classType);
	}
	
	/**
	 * 构建一个返回字符串的NetObjectRequest<br>
	 * 指针对于服务端的返回格式为{"SUCCESS":"true","DATA":"..."}
	 *
	 * @param url
	 * @param jsonRequest
	 * @param type
	 * @param dataTag
	 * @param onNetListener
	 * @param classType
	 * @return
	 */
	protected static NetObjectRequest<String>  bulidStringRequest(String url, JSONObject jsonRequest,final OnNetListener<String> onNetListener){
		
		return bulidObjectRequest(url, jsonRequest, DataUtil.TYPE_STRING_DATA, "DATA", onNetListener, String.class);
	}
	
	/**
	 * 构建一个返回字符串的NetObjectRequest<br>
	 * 指针对于服务端的返回格式为{"SUCCESS":"true","DATA":"..."}
	 * 
	 * @param method
	 * @param url
	 * @param jsonRequest
	 * @param type
	 * @param dataTag
	 * @param onNetListener
	 * @param classType
	 * @return
	 */
	protected static NetObjectRequest<String>  bulidStringRequest(int method,String url, JSONObject jsonRequest,final OnNetListener<String> onNetListener){
		
		return bulidObjectRequest(method,url, jsonRequest, DataUtil.TYPE_STRING_DATA, "DATA", onNetListener, String.class);
	}
	
	/**
	 * 构建一个返回对象的NetObjectRequest
	 *
	 * @param url
	 * @param jsonRequest
	 * @param type
	 * @param dataTag
	 * @param onNetListener
	 * @param classType
	 * @return
	 */
	protected static <T> NetObjectRequest<T>  bulidObjectRequest(String url, JSONObject jsonRequest,String dataTag,final OnNetListener<T> onNetListener,Class<T> classType){
		return bulidObjectRequest(url, jsonRequest,  DataUtil.TYPE_JSONOBJECT, dataTag, onNetListener, classType);
	}
	
	/**
	 * 构建一个返回对象的NetObjectRequest
	 *
	 * @param url
	 * @param jsonRequest
	 * @param type
	 * @param dataTag
	 * @param onNetListener
	 * @param classType
	 * @return
	 */
	private static <T> NetObjectRequest<T>  bulidObjectRequest(String url, JSONObject jsonRequest,int type,String dataTag,final OnNetListener<T> onNetListener,Class<T> classType){
		return bulidObjectRequest((jsonRequest == null)?Method.GET:Method.POST, url, jsonRequest, type, dataTag, onNetListener, classType);
	}
	
	/**
	 * 
	 *
	 * @param method
	 * @param url
	 * @param jsonRequest
	 * @param type
	 * @param dataTag
	 * @param onNetListener
	 * @param classType
	 * @return
	 */
	private static <T> NetObjectRequest<T>  bulidObjectRequest(int method, String url, JSONObject jsonRequest,int type,String dataTag,final OnNetListener<T> onNetListener,Class<T> classType){
		
		try {
			NetObjectRequest<T> netObjectRequest = new NetObjectRequest<T>(method,url, jsonRequest, new Listener<T>() {

				@Override
				public void onResponse(T response) {
					if(response == null)
					{
						onNetListener.onError(Constants.Error.SERVER_RETURN_NULL);
					}
					else
					{
						onNetListener.onResponse(response);
					}
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					onNetListener.onError(ErrorUtil.buildError(error, Constants.Error.SERVER_CALL_ERROR));
					
				}
			}, type, dataTag, classType);
			
			return netObjectRequest;
		} catch (Exception e) {
			onNetListener.onError(ErrorUtil.buildError(Constants.Error.APP_ERROR, e.getMessage()));
		}
		
		return null;
		
	}
	
	/**
	 * 构建一个返回集合的NetObjectRequest
	 *
	 * @param url
	 * @param jsonRequest
	 * @param dataTag
	 * @param onNetListener
	 * @param classType
	 * @return
	 */
	protected static <T> NetObjectRequest<List<T>>  bulidListRequest(String url, JSONObject jsonRequest,String dataTag,final OnNetListener<List<T>> onNetListener,Class<T> classType){
		try {
			NetObjectRequest<List<T>> netObjectRequest = new NetObjectRequest<List<T>>(url, jsonRequest, new Listener<List<T>>() {

				@Override
				public void onResponse(List<T> response) {
					if(response == null)
					{
						onNetListener.onError(Constants.Error.SERVER_RETURN_NULL);
					}
					else
					{
						onNetListener.onResponse(response);
					}
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					onNetListener.onError(ErrorUtil.buildError(error, Constants.Error.SERVER_CALL_ERROR));
					
				}
			}, DataUtil.TYPE_JSONARRAY, dataTag, classType);
			
			return netObjectRequest;
		} catch (Exception e) {
			onNetListener.onError(ErrorUtil.buildError(Constants.Error.APP_ERROR, e.getMessage()));
		}
		
		return null;
		
		
	}
	
	// -------------------
	// private method
	// -------------------
	/**
	 * 获取对应的类型结果
	 *
	 * @param rJsonStr
	 * @param dataTag
	 * @param type
	 * @param classType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T>  T getResult(String rJsonStr,String dataTag, int type,Class<?> classType){
		if(rJsonStr == null)
			return null;
		
		T t = null;
		try {
			if(rJsonStr != null){
				JSONObject obj = new JSONObject(JSONStringHandler.getHandledJsonString(rJsonStr));
				Boolean success = obj.getBoolean("SUCCESS");

				if (success) {
					if(type == 0){
						rJsonStr = obj.getJSONArray(dataTag).getJSONObject(0).toString().replace("\\", "");						
						t = (T) JSON.parseObject(rJsonStr,classType);
					}
					else{
						rJsonStr = obj.getJSONArray(dataTag).toString().replace("\\", "");
						t = (T) JSON.parseArray(rJsonStr, classType);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
}

package com.mdsd.docare.hemodialysis.app.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mdsd.docare.hemodialysis.app.core.app.Config;
import com.mdsd.docare.hemodialysis.app.core.service.BaseService;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.entity.server.MedDoctor;
import com.mdsd.docare.hemodialysis.app.entity.server.MedNurse;
import com.mdsd.docare.hemodialysis.app.entity.server.MedUser;

/**
 * 用户mdsuser业务
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月21日
 */
public class MedUserService extends BaseService {


	/**
	 * 登录
	 * 
	 * @param userName
	 * @param password
	 * @param listener
	 * @param errorListener
	 * @throws Exception
	 */
	public static void onLogin(String userName,String password,final OnNetListener<MedUser> onNetListener){
		
		// 构建参数
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("userName", userName));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		
		volleyNetCall.add(bulidObjectRequest(getGetTagUrl(Config.USER_API, nameValuePairs), null, "MED_USERS", onNetListener, MedUser.class));
	}
	
	/**
	 * 获取护士信息的集合
	 *
	 * @param onNetListener
	 */
	public static void getNurseList(OnNetListener<List<MedNurse>> onNetListener){
		volleyNetCall.add(bulidListRequest(getUrl(Config.GET_STAFF_DICT_LIST), null, "MED_STAFF_DICT", onNetListener, MedNurse.class));
	}
	
	/**
	 * 获取医生信息的集合
	 *
	 * @param onNetListener
	 */
	public static void getDoctorList(OnNetListener<List<MedDoctor>> onNetListener){
		volleyNetCall.add(bulidListRequest(getUrl(Config.GET_DOCTOR_LIST), null, "MED_STAFF_DICT", onNetListener, MedDoctor.class));
	}
}

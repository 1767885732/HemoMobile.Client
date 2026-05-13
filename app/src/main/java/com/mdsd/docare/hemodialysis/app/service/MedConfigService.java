package com.mdsd.docare.hemodialysis.app.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.mdsd.docare.hemodialysis.app.core.app.Config;
import com.mdsd.docare.hemodialysis.app.core.service.BaseService;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.entity.server.MedConfig;

/**
 * MedConfigService
 * 
 * <br>
 * @author jianyu.l
 * @since 2014年8月21日
 */
public class MedConfigService extends BaseService {
	
	
	
	/**
	 * 获取血透配置数据列表
	 *
	 * @param type
	 * @param status
	 * @param onNetListener
	 * @throws Exception
	 */
	public static void getConfigList(String type, String status,final OnNetListener<List<MedConfig>> onNetListener) {
		
		// 构建参数
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("type", type));
		listPair.add(new BasicNameValuePair("status", status));
		
		volleyNetCall.add(bulidListRequest(
				getGetTagUrl(Config.CONFIG_API, listPair),
				null, "MED_COMMON_ITEMLIST", onNetListener,
				MedConfig.class));
	}
	

}

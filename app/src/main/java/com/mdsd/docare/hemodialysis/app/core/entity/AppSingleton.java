package com.mdsd.docare.hemodialysis.app.core.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.entity.server.MedConfig;
import com.mdsd.docare.hemodialysis.app.entity.server.MedDoctor;
import com.mdsd.docare.hemodialysis.app.entity.server.MedNurse;
import com.mdsd.docare.hemodialysis.app.service.MedConfigService;
import com.mdsd.library.utils.log.Log;

/**
 * 应用程序的单例管理
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年7月30日
 */
public class AppSingleton {

	private static class AppSingletonHolder {
		static AppSingleton instance = new AppSingleton();
	}

	public static AppSingleton getInstance() {
		return AppSingletonHolder.instance;
	}

	private static final String[] typeArrays = new String[] {
		Constants.Dic.ROOM, Constants.Dic.CURE_MODE,Constants.Dic.CRRT_CURE_MODE,
		Constants.Dic.BLOOD_ACCESS, Constants.Dic.DIALYSIS_FILM,
		Constants.Dic.CLEANER_TYPE, Constants.Dic.CURE_WAY,
		Constants.Dic.SUMMARY_MODEL, Constants.Dic.DIALYSIS_TYPE,
		Constants.Dic.BLOOD_TYPE, Constants.Dic.NURSE_ORDER,
		Constants.Dic.DISPLACEMENT_MODE,Constants.Dic.DISPLACEMENT_RECIPE,
		Constants.Dic.PARAM_TIME_INTERVAL,Constants.Dic.DRUG_UNIT,Constants.Dic.FOCUS_LEVEL,
		Constants.Dic.SENSES,Constants.Dic.SUBJECTIVE_COMFORT,Constants.Dic.PERFUSION_DEVICE};
	
	// 需要管理的对象
	
	/**
	 * 服务是否可用
	 */
	public boolean isServerEnable = true;

	
	public List<MedConfig> medConfigs;
	
	public List<MedNurse> medNurses;
	
	public List<MedDoctor> medDoctors;
	
	/**
	 * 访问服务的ip地址
	 */
	public String IP_ADDRESS = "";
}

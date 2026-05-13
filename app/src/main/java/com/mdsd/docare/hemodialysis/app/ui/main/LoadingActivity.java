package com.mdsd.docare.hemodialysis.app.ui.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.entity.AppSingleton;
import com.mdsd.docare.hemodialysis.app.core.entity.Constants;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.entity.server.MedConfig;
import com.mdsd.docare.hemodialysis.app.entity.server.MedDoctor;
import com.mdsd.docare.hemodialysis.app.entity.server.MedNurse;
import com.mdsd.docare.hemodialysis.app.service.MedConfigService;
import com.mdsd.docare.hemodialysis.app.service.MedUserService;
import com.mdsd.library.manage.single.PrefrencesManage;
import com.mdsd.library.utils.StringUtils;
import com.mdsd.library.utils.log.Log;

/**
 * loading页面，程序启动的起始页
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年7月29日
 */
public class LoadingActivity extends Activity {

	private static final String KEY_MED_CONFIG = "key_med_config";
	private static final String KEY_MED_NURSE = "key_med_nurse";
	
	private int loadCount = 0;
	private int totalCount = 19;
	private TextView tvLoad;
	private static final String[] typeArrays = new String[] {
			Constants.Dic.ROOM, Constants.Dic.CURE_MODE,Constants.Dic.CRRT_CURE_MODE,
			Constants.Dic.BLOOD_ACCESS, Constants.Dic.DIALYSIS_FILM,
			Constants.Dic.CLEANER_TYPE, Constants.Dic.CURE_WAY,
			Constants.Dic.SUMMARY_MODEL, Constants.Dic.DIALYSIS_TYPE, 
			Constants.Dic.DISPLACEMENT_MODE,Constants.Dic.DISPLACEMENT_RECIPE,
			Constants.Dic.BLOOD_TYPE, Constants.Dic.NURSE_ORDER,Constants.Dic.PARAM_TIME_INTERVAL,Constants.Dic.DRUG_UNIT,
			Constants.Dic.FOCUS_LEVEL,Constants.Dic.SENSES,Constants.Dic.SUBJECTIVE_COMFORT,Constants.Dic.PERFUSION_DEVICE};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.loading_activity);

		tvLoad = (TextView) findViewById(R.id.tvLoad);
		tvLoad.setText(String
				.format("正在初始化数据...(%s/%s)", loadCount, typeArrays.length));
		
		
		// 判断是否有设置ip地址
		
		String ip = getSharedPreferences("config", MODE_PRIVATE).getString(ConfigActivity.KEY_IP, "");

		if (StringUtils.isEmpty(ip)) {// 如果ip地址为空，则跳转到ip配置界面
			startActivityForResult(new Intent(this, ConfigActivity.class),0);
		}else{
			AppSingleton.getInstance().IP_ADDRESS = ip;
			loadData();
		}
		
	}

	/**
	 * 第一次初始化数据
	 */
	private void loadData() {
		loadMedNurses();
		loadMedDoctors();
		loadMedConfigs();
	}

	/**
	 * 加载护士集合
	 */
	private void loadMedNurses(){
//		if((AppSingleton.getInstance().medNurses = loadLocalList(MedNurse.class, KEY_MED_NURSE)) == null){
//			MedUserService.getNurseList(new OnNetListener<List<MedNurse>>() {
//				
//				@Override
//				public void onResponse(List<MedNurse> object) {
//					AppSingleton.getInstance().medNurses = object;
//					start();
//				}
//				
//				@Override
//				public void onError(String errorMessage) {
//					start();
//					
//				}
//			});
//		}else
//			start();
		
		MedUserService.getNurseList(new OnNetListener<List<MedNurse>>() {
			
			@Override
			public void onResponse(List<MedNurse> object) {
				if(object!=null)
				{
					MedNurse tempMedNurse = new MedNurse();
					tempMedNurse.setEMP_NO("");
					tempMedNurse.setNAME("");
					object.add(0,tempMedNurse);
				}
				AppSingleton.getInstance().medNurses = object;
				start();
			}
			
			@Override
			public void onError(String errorMessage) {
				start();
				
			}
		});
	}
	
	/**
	 * 加载医生集合
	 */
	private void loadMedDoctors(){
		MedUserService.getDoctorList(new OnNetListener<List<MedDoctor>>() {
			
			@Override
			public void onResponse(List<MedDoctor> object) {
				if(object!=null)
				{
					MedDoctor tempMedDoctor = new MedDoctor();
					tempMedDoctor.setEMP_NO("");
					tempMedDoctor.setNAME("");
					object.add(0,tempMedDoctor);
				}
				AppSingleton.getInstance().medDoctors = object;
				start();
			}
			
			@Override
			public void onError(String errorMessage) {
				start();
				
			}
		});
	}
	
	/**
	 * 加载字典数据
	 */
	private void loadMedConfigs() {
		
//		if((AppSingleton.getInstance().medConfigs = loadLocalList(MedConfig.class, KEY_MED_CONFIG)) == null){
//			// 没有则从服务器获取
//			for (int i = 0; i < typeArrays.length; i++) {
//				getConfigListFromServer(typeArrays[i]);
//			}
//		}else{
//			for (int i = 0; i < 12; i++) {
//				start();
//			}
//		}
		AppSingleton.getInstance().medConfigs = new ArrayList<MedConfig>(); 
		for (int i = 0; i < typeArrays.length; i++) {
			getConfigListFromServer(typeArrays[i]);
		}
	}
	
	/**
	 * 本地加载数据
	 *
	 * @param type
	 * @param key
	 */
	private <T> List<T> loadLocalList(Class<T> type, String key){
		String str = PrefrencesManage.getInstance().getSharedPreferences().getString(key, "");
		if(!StringUtils.isEmpty(str) && !str.equals("null")){// 本地加载字典数据
			try {
				List<T> list =  JSON.parseArray(str, type);
				return list;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 从服务获取对应字典数据
	 *
	 * @param type
	 */
	void getConfigListFromServer(final String type){
		MedConfigService.getConfigList(type, "1", new OnNetListener<List<MedConfig>>() {
			@Override
			public void onResponse(List<MedConfig> object) {
				updateConfigList(object, type);
			}
			
			@Override
			public void onError(String errorMessage) {
				Log.d("get dic data failed type is :" + type+";errorMessage:"+errorMessage);
				start();
			}
		});
	}
	
	/**
	 * 更新程序字典数据
	 *
	 * @param list
	 */
	void updateConfigList(List<MedConfig> list, String type){
		if(AppSingleton.getInstance().medConfigs == null)
			AppSingleton.getInstance().medConfigs = new ArrayList<MedConfig>();
		
		MedConfig tempMedConfig = new MedConfig();
		tempMedConfig.setITEM_TYPE(type);
		tempMedConfig.setITEM_ID("");
		tempMedConfig.setITEM_NAME("");
		tempMedConfig.setITEM_VALUE("");
		AppSingleton.getInstance().medConfigs.add(tempMedConfig);
		AppSingleton.getInstance().medConfigs.addAll(list);
		
		start();
	}
	
	/**
	 * 进入主页面
	 */
	synchronized void start(){
		loadCount++;
		tvLoad.setText(String.format("正在初始化数据...(%s/%s)", loadCount, totalCount));
		
		if(loadCount == totalCount){
			
			Editor editor = PrefrencesManage.getInstance().getEditor();
			editor.putString(KEY_MED_CONFIG, JSON.toJSONString(AppSingleton.getInstance().medConfigs));
			editor.putString(KEY_MED_NURSE, JSON.toJSONString(AppSingleton.getInstance().medNurses));
			editor.commit();
			
			startActivity(new Intent(this, LoginActivity.class));
			finish();
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			loadData();
		}
	}

}

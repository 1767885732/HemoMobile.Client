package com.mdsd.docare.hemodialysis.app.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.mdsd.docare.hemodialysis.app.core.entity.AppSingleton;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.entity.server.MedConfig;
import com.mdsd.docare.hemodialysis.app.service.MedConfigService;
import com.mdsd.library.utils.StringUtils;

/**
 * 公共数据的处理工具类
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年9月1日
 */
public class DataUtil {
	
	public static int TYPE_JSONOBJECT = 0;
	public static int TYPE_JSONARRAY = 1;
	public static int TYPE_STRING_DATA = 2;

	/**
	 * 更具type获取对应的数据集合
	 *
	 * @param type (Constants.Dic.ROOM, Constants.Dic.CURE_MODE,
			Constants.Dic.BLOOD_ACCESS, Constants.Dic.DIALYSIS_FILM,
			Constants.Dic.CLEANER_TYPE, Constants.Dic.CURE_WAY)
	 * @return
	 */
	public static void updateMedConfigs(final String type){
		MedConfigService.getConfigList(type, "1", new OnNetListener<List<MedConfig>>() {
			@Override
			public void onResponse(List<MedConfig> object) {
				MedConfig tempMedConfig = new MedConfig();
				tempMedConfig.setITEM_TYPE(type);
				tempMedConfig.setITEM_ID("");
				tempMedConfig.setITEM_NAME("");
				tempMedConfig.setITEM_VALUE("");
				object.add(0, tempMedConfig);
				
				List<MedConfig> configs = new ArrayList<MedConfig>();
				for(MedConfig config : AppSingleton.getInstance().medConfigs)
				{
					if(config.getITEM_TYPE().equals(type))
					{
						configs.add(config);
					}
				}
				
				AppSingleton.getInstance().medConfigs.removeAll(configs);
				AppSingleton.getInstance().medConfigs.addAll(object);
			}
			
			@Override
			public void onError(String errorMessage) {
			}
		});
	}
	
	public static List<MedConfig> getMedConfigs(String type){
		List<MedConfig> configs = new ArrayList<MedConfig>();
		
		for(MedConfig config : AppSingleton.getInstance().medConfigs)
		{
			if(config.getITEM_TYPE().equals(type))
			{
				configs.add(config);
			}
		}
		
		return configs;
	}
	
	/**
	 * 获取对应的实体类对象
	 *
	 * @param rJsonStr
	 * @param dataTag
	 * @param classType
	 * @return
	 */
	public static <T>  T getObject(String rJsonStr,String dataTag, Class<?> classType){
		return getResult(rJsonStr,dataTag,0,classType);
	}
	
	/**
	 * 获取对应的集合
	 *
	 * @param rJsonStr
	 * @param dataTag
	 * @param classType
	 * @return
	 */
	public static <T>  T getList(String rJsonStr,String dataTag, Class<?> classType){
		return getResult(rJsonStr,dataTag,1,classType);
	}
	
	
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
	public static <T>  T getResult(String rJsonStr,String dataTag, int type,Class<?> classType){
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
					}else if(type == 1){
						rJsonStr = obj.getJSONArray(dataTag).toString().replace("\\", "");
						t = (T) JSON.parseArray(rJsonStr, classType);
					}else if(type == 2){
						rJsonStr = obj.getString(dataTag).replace("\\", "");
						t =(T) rJsonStr;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * 获取治疗状态
	 *
	 * @param scheduleStatus
	 * @return
	 */
	public static String getCureStatus(String scheduleStatus){
		if("0".equals(scheduleStatus))
			return "未开始";
		
		if("1".equals(scheduleStatus))
			return "治疗中";
		
		if("2".equals(scheduleStatus))
			return "治疗结束";
		
		return "";
	}
	
	/**
	 * 获取当前治疗状态
	 *
	 * @param startTime
	 * @param endTime
	 * @return (0：未开始；1：治疗中；2已结束)
	 */
	public static int getCureState(String startTime, String endTime){
		if (!StringUtils.isEmpty(endTime)) {// 治疗结束
			return 2;
		} else if (!StringUtils.isEmpty(startTime)) {// 治疗中
			return 1;
		}
		return 0;// 治疗未开始
	}
	
	/**
	 * 获取治疗时间段
	 *
	 * @param classes
	 * @return
	 */
	public static String getCureTime(String classes){
		
		if("1".equals(classes))
			return "上午";
		
		if("2".equals(classes))
			return "下午";
		
		if("3".equals(classes))
			return "晚班";
		
		if("4".equals(classes))
			return "急诊";
		
		return "";
	}
}

package com.mdsd.docare.hemodialysis.app.core.entity;

/**
 * 静态常量类
 * 
 * <br>
 * @author jianyu.l
 * @since 2014年9月1日
 */
public class Constants {

	public static class  ExtraKey{
		public static final String RECIPE_ID = "recipe_id";
		public static final String MED_PATIENT_SCHEDULE = "med_patient_schedule";
		public static final String MED_HEMO_PARAMETER = "med_hemo_parameter";
	}
	
	public static class Dic{
		public static final String ROOM = "区域";
		public static final String CURE_MODE = "净化方式";
		public static final String CRRT_CURE_MODE = "CRRT净化方式";
		public static final String BLOOD_ACCESS = "血管通路";
		public static final String DIALYSIS_FILM = "透析膜";
		public static final String CLEANER_TYPE = "净化器类型";
		public static final String CURE_WAY = "治疗方法";
		public static final String SUMMARY_MODEL = "透析小结模版";
		public static final String DIALYSIS_TYPE = "透析机";
		public static final String BLOOD_TYPE = "血型";
		public static final String NURSE_ORDER = "护士转抄医嘱";
		public static final String PARAM_TIME_INTERVAL = "透析参数间隔时间";
		public static final String DRUG_UNIT = "药品单位";
		public static final String DISPLACEMENT_MODE = "置换方式";
		public static final String DISPLACEMENT_RECIPE = "置换液配方";
		public static final String FOCUS_LEVEL = "病情";
		public static final String SENSES = "神志";
		public static final String SUBJECTIVE_COMFORT = "透析液品牌";
		public static final String PERFUSION_DEVICE = "灌流器";
	}
	
	public static class Error{
		public static final String APP_ERROR = "应用错误";
		public static final String SERVER_CALL_ERROR = "服务访问错误";
		
		public static final String SERVER_RETURN_NULL = "服务无返回数据";
		public static final String SERVER_SEARCH_FAILED = "查询失败";
		public static final String SERVER_SAVE_FAILED = "保存失败";
		public static final String SERVER_DEL_FAILED = "删除失败";
	}
	
	public static class Vascular_AccessType
	{
		public static final String  ZTNL = "2edd38f1-ce13-410d-a930-0b69133997fe";//自体内瘘
	}
}

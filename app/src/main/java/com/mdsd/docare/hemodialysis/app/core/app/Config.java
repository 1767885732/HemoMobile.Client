package com.mdsd.docare.hemodialysis.app.core.app;

/**
 * 服务访问相关地址配置
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月21日
 */
public class Config {

//	/**
//	 * 服务器地址 以后改成设置界面设置
//	 */
//	public static String SERVER_URL = "http://192.168.191.1";

	/**
	 * medUser API 节点
	 */
	public final static String USER_API = "/api/MedUser";
	
	public final static String GET_STAFF_DICT_LIST = "/Api/MedUser/GetStaffDictList";
	
	public final static String GET_DOCTOR_LIST = "/Api/MedUser/GetDoctorList";
	
	public final static String GET_NEW_VERSION="/Api/MedUser/GetNewVersion";

	/**
	 * medConfig API 节点
	 */
	public final static String CONFIG_API = "/api/Config/GetConfigList";

	/**
	 * medPatientSchedule API 节点
	 */
	public final static String PATIENTSCHEDULE_API = "/api/Schedule/GetPatientScheduleList";
	
	public final static String GETPATIENTSCHEDULEBYDATEANDHEMOID_API = "/api/Schedule/GetPatientScheduleByDateAndHemoId";
	
	public final static String START_CURE = "/api/Schedule/StartCure";

	/**
	 * medPatientSchedule API 节点
	 */
	public final static String SAVEPATIENTSCHEDULE_API = "/api/Schedule/SavePatientSchedule";

	/**
	 * medCure API 节点
	 */
	public final static String GETNEWCUREID_API = "/Api/Cure/GetNewCureID";

	/**
	 * medRecipe API 节点
	 */
	public final static String RECIPE_API = "/Api/Recipe/GetRecipeByHemodialysisIDAndDate";
	
	/**
	 * medRecipe API 节点
	 */
	public final static String GETRECIPEBYRECIPEID_API = "/Api/Recipe/GetRecipeByRecipeId";

	/**
	 * medCure API 节点
	 */
	public final static String SAVECUREMAIN_API = "/Api/Cure/SaveCureMain";

	/**
	 * medCure API 节点
	 */
	public final static String GETCLEANUPTIMES_API = "/Api/Cure/GetCleanUpTimes";
	
	/**
	 * medCure API 节点
	 */
	public final static String GETLASTTIMEDRYWEIGHTBYID_API = "/Api/Cure/GetLastTimeDryWeightById";

	/**
	 * medCure API 节点
	 */
	public final static String GETMAINCUREBYCUREID_API = "/Api/Cure/GetMainCureByCureId";

	/**
	 * medCure API 节点
	 */
	public final static String GETMAINCUREBYRECIPEID_API = "/Api/Cure/GetMainCureByRecipeId";

	/**
	 * medCure API 节点
	 */
	public final static String GETMAINCURECOUNT_API = "/Api/Cure/GetMainCureCount";
	
	/**
	 * medHemodialysisParameters API 节点
	 */
	public final static String GETHEMOPARAMETERSBYCUREID_API = "/Api/MedHemodialysisParameters/GetHemoParametersByCureID";
	
	public final static String DELETE_HEMO_PARAMETERS_BY_ID="/Api/MedHemodialysisParameters/DeleteHemoParametersById";
	
	public final static String SAVE_HEMO_PARAMETERS = "/Api/MedHemodialysisParameters/SaveHemoParameters";
	
	/**
	 * 临时医嘱
	 */
	public final static String GET_CURE_DRUG_BY_HEMOID = "/Api/CureDrug/GetValidCureDrugByHemoId";
	public final static String UPDATE_CURE_DRUG_STATE_BY_PARMA = "/Api/CureDrug/UpdateCureDrugStateByParma";
	public final static String SAVECUREDRUG_API = "/Api/CureDrug/SaveCureDrug";
	
	/**
	 * 长期医嘱
	 */
	public final static String GET_LONG_CURE_DRUG_BY_HEMOID = "/Api/CureDrug/GetLongCureDrugByHemoID";
	
	public final static String GETPATIENTVASULARACCESSBYHEMOID_API="/Api/VascularAccess/GetPatientVasularAccessByHemoId";

	public  final static String UPLOAD_PATIENT_PHOTO="/Api/MedPatient/UploadPatientPhoto";
}

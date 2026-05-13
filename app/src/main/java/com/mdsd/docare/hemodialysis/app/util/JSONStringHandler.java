package com.mdsd.docare.hemodialysis.app.util;

/**
 * json转换成对应的实体类的工具类
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月21日
 */
public class JSONStringHandler {
	public static String getHandledJsonString(String strJson) {
		strJson = strJson.substring(strJson.indexOf("\"") + 1,
				strJson.lastIndexOf("\""));
		strJson = strJson.replace("\\", "");

		return strJson;
	}

	public static String getHandledUserJsonString(String strJson) {
		strJson = strJson.replace("DEPT_ID", "deptId");
		strJson = strJson.replace("MEMO", "memo");
		strJson = strJson.replace("CREATED_DATE", "createdDate");
		strJson = strJson.replace("EMP_NO", "empNo");
		strJson = strJson.replace("USER_ID", "userId");
		strJson = strJson.replace("USER_NAME", "userName");
		strJson = strJson.replace("LOGIN_PWD", "loginPwd");
		strJson = strJson.replace("LOGIN_NAME", "loginName");
		strJson = strJson.replace("IS_VALID", "isValid");

		return strJson;
	}

	public static String getHandledConfigJsonString(String strJson) {
		strJson = strJson.replace("ITEM_ID", "itemId");
		strJson = strJson.replace("ITEM_VALUE", "itemValue");
		strJson = strJson.replace("ITEM_NAME", "itemName");
		strJson = strJson.replace("ITEM_TYPE", "itemType");
		strJson = strJson.replace("STATUS", "status");
		strJson = strJson.replace("ORDER_NUMBER", "orderNumber");

		return strJson;
	}

	public static String getHandledPatientScheduleJsonString(String strJson) {
		strJson = strJson.replace("BEDNAME", "bed_Name");
		strJson = strJson.replace("PATIENTNAME", "name");
		strJson = strJson.replace("SEX", "sex");
		strJson = strJson.replace("REMARK", "remark");
		strJson = strJson.replace("PURIFIER_MODEL_ID", "purifier_Model_Id");
		strJson = strJson.replace("STATUS", "status");
		strJson = strJson.replace("PATIENT_SCHEDULE_ID", "patient_Schedule_Id");
		strJson = strJson.replace("PATIENT_ID", "patient_Id");
		strJson = strJson.replace("MONITOR_LABEL", "monitor_Label");
		strJson = strJson.replace("DIALYSIS_DATE", "dialysis_Date");
		strJson = strJson.replace("BANCI_ID", "banci_Id");
		strJson = strJson.replace("DIALYSIS_ROOM_ID", "dialysis_Room_Id");
		strJson = strJson.replace("BED_NUMBER", "bed_Number");
		strJson = strJson.replace("START_TIME", "start_Time");
		strJson = strJson.replace("END_TIME", "end_Time");
		strJson = strJson.replace("HEMODIALYSIS_ID", "hemodialysis_Id");
		strJson = strJson.replace("RECIPE_ID", "recipe_Id");
		strJson = strJson.replace("USER_ID", "user_Id");
		strJson = strJson.replace("FOCUS_LEVEL", "focus_Level");
		strJson = strJson.replace("MACHINE_NAME", "machine_Name");
		strJson = strJson.replace("MODELNAME", "model_Name");
		strJson = strJson.replace("AREANAME", "area_Name");

		return strJson;
	}

	public static String getHandledCureJsonString(String strJson) {
		strJson = strJson.replace("CURE_ID", "cure_Id");
		strJson = strJson.replace("HEMODIALYSIS_ID", "hemodialysis_Id");
		strJson = strJson.replace("RECIPE_ID", "recipe_Id");
		strJson = strJson.replace("PURIFICATION_MODE", "purification_Mode");
		strJson = strJson.replace("FREQUENCY_HOURS", "frequency_Hours");
		strJson = strJson.replace("VASCULAR_ACCESS_ID", "vascular_Access_Id");
		strJson = strJson.replace("MACHINE_TYPE", "machine_Type");
		strJson = strJson.replace("PURIFIER_NAME", "purifier_Name");
		strJson = strJson.replace("PURIFIER_M2", "purifier_M2");
		strJson = strJson.replace("SODION", "sodion");
		strJson = strJson.replace("POTASSIUM_ION", "potassium_Ion");
		strJson = strJson.replace("CALCIUM_ION", "calcium_Ion");
		strJson = strJson.replace("BIRCARBONATE", "bircarbonate");
		strJson = strJson.replace("CURE_CREATE_DATE", "cure_Create_Date");
		strJson = strJson.replace("MACHINE_ID_TAG", "machine_Id_Tag");
		strJson = strJson.replace("MACHINE_ID", "machine_Id");
		strJson = strJson.replace("FIRST_HEPARIN", "first_Heparin");
		strJson = strJson.replace("BLOOW_FLOW", "bloow_Flow");
		strJson = strJson.replace("CURE_STATUS", "cure_Status");
		strJson = strJson.replace("BEFORE_DRY_WEIGHT_TAG",
				"before_Dry_Weight_Tag");
		strJson = strJson.replace("BEFORE_DRY_WEIGHT", "before_Dry_Weight");
		strJson = strJson.replace("PRIMARY_DOCTOR", "primary_Doctor");
		strJson = strJson.replace("DRY_WATER_VALUE", "dry_Water_Value");
		strJson = strJson.replace("HEPARIN_SPECIES", "heparin_Species");
		strJson = strJson.replace("DOSIS_SUSTENTATIVA", "dosis_Sustentativa");
		strJson = strJson.replace("FIRST_DRUG_UNIT", "first_Drug_Unit");
		strJson = strJson.replace("SECOND_DRUG_UNIT", "second_Drug_Unit");
		strJson = strJson.replace("FILTRATION_DISPLACEMENT_LIQUID",
				"filtration_Displacement_Liquid");
		strJson = strJson.replace("CLEAN_UP_TIMES", "clean_Up_Times");
		strJson = strJson.replace("AFTER_DIASTOLIC_PRESSURE",
				"after_Diastolic_Pressure");
		strJson = strJson.replace("AFTER_DRY_WEIGHT_TAG",
				"after_Dry_Weight_Tag");
		strJson = strJson.replace("AFTER_DRY_WEIGHT", "after_Dry_Weight");
		strJson = strJson.replace("AFTER_HEART_RATE", "after_Heart_Rate");
		strJson = strJson.replace("AFTER_SYSTOLIC_PRESSURE",
				"after_Systolic_Pressure");
		strJson = strJson.replace("AFTER_TEMPERATURE", "after_Temperature");
		strJson = strJson.replace("AMYLACEUM", "amylaceum");
		strJson = strJson.replace("BEFORE_DIASTOLIC_PRESSURE",
				"before_Diastolic_Pressure");
		strJson = strJson.replace("BEFORE_HEART_RATE", "before_Heart_Rate");
		strJson = strJson.replace("BEFORE_SYSTOLIC_PRESSURE",
				"before_Systolic_Pressure");
		strJson = strJson.replace("BEFORE_TEMPERATURE", "before_Temperature");
		strJson = strJson.replace("DIALYSATE_FLOW", "dialysate_Flow");
		strJson = strJson.replace("DIALYSATE_TEMPERATURE",
				"dialysate_Temperature");
		strJson = strJson.replace("DISPLACEMENT_LIQUID", "displacement_Liquid");
		strJson = strJson.replace("FILTRATION_PERCOLATE",
				"filtration_Percolate");
		strJson = strJson.replace("LAST_TIME_DRY_WEIGHT",
				"last_Time_Dry_Weight");
		strJson = strJson.replace("PERCOLATE", "percolate");
		strJson = strJson.replace("REUSE_TIMES_TAG", "reuse_Times_Tag");
		strJson = strJson.replace("REUSE_TIMES", "reuse_Times");
		strJson = strJson.replace("UFR", "ufr");
		strJson = strJson.replace("SUMMARY", "summary");
		strJson = strJson.replace("DOCTOR_ID", "doctor_Id");
		strJson = strJson.replace("DOCTOR_ADVICE", "doctor_Advice");
		strJson = strJson.replace("B_LIQUID", "b_Liquid");
		strJson = strJson.replace("USE_TYPE", "use_Type");
		strJson = strJson.replace("NURSE_ID", "nurse_Id");
		strJson = strJson.replace("VASCULAR_ACCESS_FIRM",
				"vascular_Access_Firm");
		strJson = strJson.replace("VEIN", "vein");
		strJson = strJson.replace("CHECK_NURSE", "check_Nurse");
		strJson = strJson.replace("RECIPE_TYPE", "recipe_Type");
		strJson = strJson.replace("PUNCTURE_NURSE", "puncture_Nurse");
		strJson = strJson.replace("SUMMARY2", "summary2");
		strJson = strJson.replace("VASCULAR_ACCESS_THROMBUS",
				"vascular_Access_Thrombus");
		strJson = strJson.replace("BEGIN_TIME", "begin_Time");
		strJson = strJson.replace("VASCULAR_ACCESS_ERRHYISIS",
				"vascular_Access_Errhyisis");
		strJson = strJson.replace("VASCULAR_ACCESS_GLIDE",
				"vascular_Access_Glide");
		strJson = strJson.replace("VASCULAR_ACCESS_SWELLING",
				"vascular_Access_Swelling");
		strJson = strJson.replace("A_LIQUID", "a_Liquid");
		strJson = strJson.replace("RECIPE_DATE", "recipe_Date");
		strJson = strJson.replace("VASCULAR_ACCESS_BLOOD",
				"vascular_Access_Blood");
		strJson = strJson.replace("PRIMARY_NURSE", "primary_Nurse");
		strJson = strJson.replace("END_TIME", "end_Time");
		strJson = strJson.replace("PERFORM_SCHEDULE", "perform_Schedule");

		return strJson;
	}
}

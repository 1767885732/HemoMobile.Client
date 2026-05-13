package com.mdsd.docare.hemodialysis.app.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import com.android.volley.Request.Method;
import com.mdsd.docare.hemodialysis.app.core.app.Config;
import com.mdsd.docare.hemodialysis.app.core.entity.Constants;
import com.mdsd.docare.hemodialysis.app.core.service.BaseService;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.entity.app.PatientSearchParam;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureDrug;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureInfo;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureLongDrug;
import com.mdsd.docare.hemodialysis.app.entity.server.MedHemoParameter;
import com.mdsd.docare.hemodialysis.app.entity.server.MedPatientSchedule;
import com.mdsd.docare.hemodialysis.app.entity.server.MedRecipeInfo;
import com.mdsd.docare.hemodialysis.app.entity.server.MedVasularAccess;
import com.mdsd.docare.hemodialysis.app.util.ErrorUtil;
import com.mdsd.library.utils.JsonParseUtil;
/**
 * MedPatientScheduleService
 * 
 * <br>
 * @author jianyu.l
 * @since 2014年8月21日
 */
public class MedPatientScheduleService extends BaseService {

	/**
	 * 获取患者列表
	 *
	 * @param searchParam
	 * @param onNetListener
	 */
	public static void getPatientScheduleList(PatientSearchParam searchParam,final OnNetListener<List<MedPatientSchedule>> onNetListener){
		// 构建参数
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("treamentDate", searchParam.getDate()));
		listPair.add(new BasicNameValuePair("userId", "5d744a87-ca60-40c3-bc7e-1f50a844aa5b"));
		listPair.add(new BasicNameValuePair("classes", searchParam.getTimeRangeType()));
		listPair.add(new BasicNameValuePair("area", searchParam.getRoomId()));
		
		volleyNetCall.add(bulidListRequest(
						getGetTagUrl(Config.PATIENTSCHEDULE_API, listPair),
						null, "MED_PATIENT_SCHEDULE", onNetListener,
						MedPatientSchedule.class));
	}
	
	/**
	 * 获取排班信息
	 *
	 * @param onNetListener
	 */
	public static void getPatientScheduleByDateAndHemoId(String date,String hemoId,final OnNetListener<MedPatientSchedule> onNetListener){
		
		// 构建参数
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("date", date));
		listPair.add(new BasicNameValuePair("hemoId", hemoId));
		
		volleyNetCall.add(bulidObjectRequest(
				getGetTagUrl(Config.GETPATIENTSCHEDULEBYDATEANDHEMOID_API, listPair), null,
				"MED_PATIENT_SCHEDULE", onNetListener, MedPatientSchedule.class));
	}
	
	/**
	 * 开始治疗
	 *
	 * @param hemoId
	 * @param date (透析日期)
	 * @param onNetListener
	 */
	public static void startCure(String hemoId, String date,OnNetListener<String> onNetListener){
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("hemoId", hemoId));
		listPair.add(new BasicNameValuePair("date", date));
		
		volleyNetCall.add(bulidStringRequest(getGetTagUrl(Config.START_CURE, listPair), null, onNetListener));
	}
	
	/**
	 * 结束治疗
	 *
	 * @param onNetListener
	 */
	public static void finishCure(MedPatientSchedule patientSchedule, OnNetListener<String> onNetListener){
		try {
			volleyNetCall.add(bulidStringRequest(getUrl(Config.SAVEPATIENTSCHEDULE_API), JsonParseUtil.toJsonObject(patientSchedule), onNetListener));
		} catch (JSONException e) {
			onNetListener.onError(ErrorUtil.buildError(Constants.Error.APP_ERROR, e.getMessage()));
		}
	}
	
	// -------------------------
	// 治疗信息
	// -------------------------
	/**
	 * 获取治疗信息初始值
	 *
	 * @param onNetListener
	 */
	public static void getMainCureByRecipeId(String recipeId,final OnNetListener<MedCureInfo> onNetListener){
		
		// 构建参数
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("recipeId", recipeId));
		
		volleyNetCall.add(bulidObjectRequest(
				getGetTagUrl(Config.GETMAINCUREBYRECIPEID_API, listPair), null,
				"MED_CURE_MAIN", onNetListener, MedCureInfo.class));
	}
	
	/**
	 * 保存治疗信息
	 *
	 * @param onNetListener
	 */
	public static void saveCureInfo(final MedCureInfo cureInfo, final OnNetListener<String> onNetListener)
	{
		
		try {
			volleyNetCall.add(bulidStringRequest(
					getUrl(Config.SAVECUREMAIN_API),
					JsonParseUtil.toJsonObject(cureInfo),onNetListener));
		} catch (JSONException e) {
			onNetListener.onError(ErrorUtil.buildError(Constants.Error.APP_ERROR, e.getMessage()));
		}
	}
	
	/**
	 * 获取处方信息初始值
	 *
	 * @param onNetListener
	 */
	public static void getRecipeByRecipeId(String recipeId,final OnNetListener<MedRecipeInfo> onNetListener){
		
		// 构建参数
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("recipeId", recipeId));
		
		volleyNetCall.add(bulidObjectRequest(
				getGetTagUrl(Config.GETRECIPEBYRECIPEID_API, listPair), null,
				"MED_HEMO_RECIPE", onNetListener, MedRecipeInfo.class));
	}
	
	/**
	 * 获取患者上次透析后体重
	 *
	 * @param onNetListener
	 */
	public static void getLastTimeDryWeightById(String hemoId, String createDate, final OnNetListener<String> onNetListener)
	{
		// 构建参数
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("hemoId", hemoId));
		listPair.add(new BasicNameValuePair("createDate", createDate));

		volleyNetCall.add(bulidStringRequest(
				getGetTagUrl(Config.GETLASTTIMEDRYWEIGHTBYID_API, listPair),
				null, onNetListener));
	}
	
	// -------------------------
	// 临时医嘱
	// -------------------------
	/**
	 * 获取临时医嘱列表集合
	 *
	 * @param hemoId
	 * @param onNetListener
	 */
	public static void getTempOrdersList(String hemoId,OnNetListener<List<MedCureDrug>> onNetListener){
		// 构建参数
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("hemoId", hemoId));
				
		volleyNetCall.add(bulidListRequest(
				getGetTagUrl(Config.GET_CURE_DRUG_BY_HEMOID, listPair), null,
				"MED_CURE_DRUG", onNetListener, MedCureDrug.class));
	}
	
	// -------------------------
	// 长期医嘱
	// -------------------------
	/**
	 * 获取长期医嘱
	 * 
	 * @param hemoId
	 * @param onNetListener
	 */
	public static void getLongOrder(String hemoId,
			OnNetListener<List<MedCureLongDrug>> onNetListener) {
		// 构建参数
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("hemoId", hemoId));

		volleyNetCall.add(bulidListRequest(
				getGetTagUrl(Config.GET_LONG_CURE_DRUG_BY_HEMOID, listPair), null,
				"MED_CURE_LONGDRUG", onNetListener, MedCureLongDrug.class));
	}
	
	/**
	 * 更新药品状态
	 *
	 * @param state	(1执行，2 退回)
	 * @param hemoId
	 * @param comNo
	 * @param createDate
	 * @param onNetListener
	 */
	public static void updataCureDrugStateByParma(String state, String hemoId, String comNo, String createDate,OnNetListener<String> onNetListener){
		// 构建参数
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("state", state));
		listPair.add(new BasicNameValuePair("hemoId", hemoId));
		listPair.add(new BasicNameValuePair("comNo", comNo));
		listPair.add(new BasicNameValuePair("createDate", createDate));
		
		volleyNetCall.add(bulidStringRequest(Method.POST, getGetTagUrl(Config.UPDATE_CURE_DRUG_STATE_BY_PARMA, listPair), null, onNetListener));
	}
	
	/**
	 * 保存临时医嘱
	 * 
	 * @param cureDrug
	 * @param cureId
	 * @param recipeId
	 * @param execNurse
	 * @param state
	 * @param onNetListener
	 */
	public static void saveCureDrug(MedCureDrug cureDrug, String cureId, String recipeId, String execNurse, String state,OnNetListener<String> onNetListener)
	{
		// 构建参数
				List<NameValuePair> listPair = new ArrayList<NameValuePair>();
				listPair.add(new BasicNameValuePair("cureId", cureId));
				listPair.add(new BasicNameValuePair("recipeId", recipeId));
				listPair.add(new BasicNameValuePair("execNurse", execNurse));
				listPair.add(new BasicNameValuePair("state", state));
				
				try {
					volleyNetCall.add(bulidStringRequest(Method.POST, getGetTagUrl(Config.SAVECUREDRUG_API, listPair), JsonParseUtil.toJsonObject(cureDrug), onNetListener));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					onNetListener.onError(ErrorUtil.buildError(Constants.Error.APP_ERROR, e.getMessage()));
				}
	}

	// -------------------------
	// 透析参数
	// -------------------------
	/**
	 * 获取透析参数LIST
	 *
	 * @param onNetListener
	 */
	public static void getHemoParametersByCureID(String cureId,final OnNetListener<List<MedHemoParameter>> onNetListener){
		
		// 构建参数
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("cureId", cureId));
		
		volleyNetCall.add(bulidListRequest(
				getGetTagUrl(Config.GETHEMOPARAMETERSBYCUREID_API, listPair),
				null, "MED_HEMODIALYSIS_PARAMETERS", onNetListener,
				MedHemoParameter.class));
	}
	
	/**
	 * 删除对应的透析记录
	 *
	 * @param hemoParameterId
	 * @param onNetListener
	 */
	public static void deleteHemoParameter(String hemoParameterId, final OnNetListener<String> onNetListener){
		// 构建参数
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("paramId", hemoParameterId));
		volleyNetCall.add(bulidStringRequest(Method.POST,getGetTagUrl(Config.DELETE_HEMO_PARAMETERS_BY_ID,listPair), null, onNetListener));
	}
	
	/**
	 * 保存透析参数数据
	 *
	 * @param hemoParameter
	 * @param onNetListener
	 */
	public static void saveHemoParameter(MedHemoParameter hemoParameter, final OnNetListener<String> onNetListener){
		
		try {
			volleyNetCall.add(bulidStringRequest(getUrl(Config.SAVE_HEMO_PARAMETERS), JsonParseUtil.toJsonObject(hemoParameter), onNetListener));
		} catch (JSONException e) {
			onNetListener.onError(ErrorUtil.buildError(Constants.Error.APP_ERROR, e.getMessage()));
		}
	}

	public static void getPatientVasularAccessByHemoId(String hemoId,final OnNetListener<List<MedVasularAccess>> onNetListener)
	{
		List<NameValuePair> listPair = new ArrayList<NameValuePair>();
		listPair.add(new BasicNameValuePair("hemoId", hemoId));
		
		volleyNetCall.add(bulidListRequest(
				getGetTagUrl(Config.GETPATIENTVASULARACCESSBYHEMOID_API, listPair),
				null, "MED_VASCULAR_ACCESS", onNetListener,
				MedVasularAccess.class));
	}
}

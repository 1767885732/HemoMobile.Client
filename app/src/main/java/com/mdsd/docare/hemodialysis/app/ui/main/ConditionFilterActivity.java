package com.mdsd.docare.hemodialysis.app.ui.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.entity.AppSingleton;
import com.mdsd.docare.hemodialysis.app.core.entity.Constants;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.core.ui.activity.BaseActivity;
import com.mdsd.docare.hemodialysis.app.entity.app.MedClass;
import com.mdsd.docare.hemodialysis.app.entity.app.PatientSearchParam;
import com.mdsd.docare.hemodialysis.app.entity.server.MedConfig;
import com.mdsd.docare.hemodialysis.app.service.MedConfigService;
import com.mdsd.docare.hemodialysis.app.ui.patient.PatientListActivity;
import com.mdsd.docare.hemodialysis.app.util.DataUtil;
import com.mdsd.docare.hemodialysis.app.util.ViewUtil;
import com.mdsd.library.manage.single.ActivityManageSingle;
import com.mdsd.library.ui.DateTextView;
import com.mdsd.library.utils.ToastUtil;
import com.mdsd.library.utils.log.Log;

/**
 * 条件搜索页面
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月18日
 */
public class ConditionFilterActivity extends BaseActivity {

	// view
	DateTextView dtvDate;
	Spinner spiTxs,spiTimeRange;
	
	// variable
	Intent patientListIntent;
	PatientSearchParam patientSearchParam;
	
	/**
	 * 第一点击返回按钮时间
	 */
	long firstBackPressedTime = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.condition_filter_activity);
	}
	
	@Override
	public void beforeInitView() {
		super.beforeInitView();
		
		setBarLogoCustomTitle();
	}
	
	@Override
	public void findViews() {
		super.findViews();
		
		dtvDate = (DateTextView) findViewById(R.id.dtvDate);
		
		spiTxs = (Spinner) findViewById(R.id.spiTxs);
		spiTimeRange = (Spinner) findViewById(R.id.spiTimeRange);
	}
	
	@Override
	public void setListeners() {
		super.setListeners();
		
		findViewById(R.id.btnConfirm).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toPatientListActivity();
			}
		});
	}
	
	
	@Override
	public void setViewData() {
		super.setViewData();
		
		// 时段
		ArrayAdapter<MedClass>  medClassAdapter= new ArrayAdapter<MedClass>(this,
				android.R.layout.simple_spinner_item,
				getClasses());
		medClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spiTimeRange.setAdapter(medClassAdapter);
		
		if(DataUtil.getMedConfigs(Constants.Dic.ROOM).size()==0)
		{
			MedConfigService.getConfigList(Constants.Dic.ROOM, "1", new OnNetListener<List<MedConfig>>() {
				@Override
				public void onResponse(List<MedConfig> list) {
					if(AppSingleton.getInstance().medConfigs == null)
						AppSingleton.getInstance().medConfigs = new ArrayList<MedConfig>();
					
					MedConfig tempMedConfig = new MedConfig();
					tempMedConfig.setITEM_TYPE(Constants.Dic.ROOM);
					tempMedConfig.setITEM_ID("");
					tempMedConfig.setITEM_NAME("");
					tempMedConfig.setITEM_VALUE("");
					AppSingleton.getInstance().medConfigs.add(tempMedConfig);
					AppSingleton.getInstance().medConfigs.addAll(list);
				}
				
				@Override
				public void onError(String errorMessage) {
					Log.d("get dic data failed type is :" + Constants.Dic.ROOM+";errorMessage:"+errorMessage);
				}
			});
		}
		
		// 透析室
		ViewUtil.buildSpiByMedConfig(this, spiTxs, Constants.Dic.ROOM);
	}
	// ----------------------
	// private method
	// ----------------------
	/**
	 * 跳转到患者列表页面
	 */
	void toPatientListActivity(){
		if(patientListIntent == null)
			patientListIntent = new Intent(this, PatientListActivity.class);
		
		// 构建传入参数
		if(patientSearchParam == null)
			patientSearchParam = new PatientSearchParam();
		patientSearchParam.setDate(dtvDate.getText().toString());
		patientSearchParam.setRoomId(((MedConfig) spiTxs.getSelectedItem()).getITEM_ID());
		patientSearchParam.setTimeRangeType(((MedClass) spiTimeRange.getSelectedItem()).getValue());
		
		patientListIntent.putExtra(PatientListActivity.EXTRA_PATIENT_SEARCH_PARAM, patientSearchParam);
		
		startActivity(patientListIntent);
	}
	
	
	/**
	 * 获取班次
	 */
	private List<MedClass> getClasses() {
		List<MedClass> listClass = new ArrayList<MedClass>();
		MedClass medClass = new MedClass("上午", "1");
		listClass.add(medClass);
		medClass = new MedClass("下午", "2");
		listClass.add(medClass);
		medClass = new MedClass("晚班", "3");
		listClass.add(medClass);
		medClass = new MedClass("急诊", "4");
		listClass.add(medClass);

		return listClass;
	}
	
	@Override
	public void onBackPressed() {
		// 退出程序
		
		if(System.currentTimeMillis() - firstBackPressedTime < 2000){
			ActivityManageSingle.getInstance().appExit();// 退出程序
		}else{
			ToastUtil.show(this, "再按一次退出应用");
			firstBackPressedTime = System.currentTimeMillis();
		}
	}
}

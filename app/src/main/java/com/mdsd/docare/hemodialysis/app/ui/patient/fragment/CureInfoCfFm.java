package com.mdsd.docare.hemodialysis.app.ui.patient.fragment;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.entity.AppContext;
import com.mdsd.docare.hemodialysis.app.core.entity.AppSingleton;
import com.mdsd.docare.hemodialysis.app.core.entity.Constants;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.core.ui.fragment.BaseChildFragment;
import com.mdsd.docare.hemodialysis.app.entity.server.MedConfig;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureInfo;
import com.mdsd.docare.hemodialysis.app.entity.server.MedDoctor;
import com.mdsd.docare.hemodialysis.app.entity.server.MedNurse;
import com.mdsd.docare.hemodialysis.app.entity.server.MedVasularAccess;
import com.mdsd.docare.hemodialysis.app.service.MedPatientScheduleService;
import com.mdsd.docare.hemodialysis.app.ui.patient.PatientTabActivity;
import com.mdsd.docare.hemodialysis.app.util.ViewUtil;
import com.mdsd.library.utils.StringUtils;
import com.mdsd.library.utils.ToastUtil;

/**
 * 治疗信息透析处方
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月19日
 */
public class CureInfoCfFm extends BaseChildFragment
{
	CureInfoPgFm _cureInfoPgFm;
	Spinner cure_spinner_zlfs,cure_spinner_xgtl,cure_spinner_jhqlx,cure_spinner_hzbq,cure_spinner_sz,cure_spinner_glq,
	cure_spinner_txm,cure_spinner_knj,cure_spinner_txjxh,cure_spinner_sjdw,cure_spinner_zjdw,cure_spinner_txypp;
	ProgressDialog progressDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.initView(inflater, container, R.layout.cure_info_cf_fm);
	}

	@Override
	public void findViews() {
		super.findViews();
		
		cure_spinner_zlfs = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_zlfs);
		cure_spinner_xgtl = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_xgtl);
		cure_spinner_jhqlx = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_jhqlx);
		cure_spinner_txm = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_txm);
		//cure_spinner_knj = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_knj);
		cure_spinner_txjxh= (Spinner)getCurrentView().findViewById(R.id.cure_spinner_txjxh);
		cure_spinner_sjdw = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_sjdw);
		cure_spinner_zjdw = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_zjdw);
		//cure_spinner_hzbq = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_hzbq);
		//cure_spinner_sz = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_sz);
		//cure_spinner_txypp = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_txypp);
		//cure_spinner_glq = (Spinner)getCurrentView().findViewById(R.id.cure_spinner_glq);
		//cure_radiobtn_jhqsy_sc=(CheckBox)getCurrentView().findViewById(R.id.cure_radiobtn_jhqsy_sc);
		//cure_radiobtn_jhqsy_cf=(CheckBox)getCurrentView().findViewById(R.id.cure_radiobtn_jhqsy_cf);
	}
	
	@Override
	public void setViewData() {
		super.setViewData();
		
		String area = PatientTabActivity.currMedPatientSchedule.getAREANAME();
		String mode = area.equals("CRRT") ? Constants.Dic.CRRT_CURE_MODE : Constants.Dic.CURE_MODE;
		// 绑定spinner数据源
		ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_zlfs, mode);
		ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_xgtl, Constants.Dic.BLOOD_ACCESS);
		ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_jhqlx, Constants.Dic.CLEANER_TYPE);
		ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_txm, Constants.Dic.DIALYSIS_FILM);
		//ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_knj, Constants.Dic.CURE_WAY);
		ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_txjxh, Constants.Dic.DIALYSIS_TYPE);
		ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_sjdw, Constants.Dic.DRUG_UNIT);
		ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_zjdw, Constants.Dic.DRUG_UNIT);
		//ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_hzbq, Constants.Dic.FOCUS_LEVEL);
		//ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_sz, Constants.Dic.SENSES);
		//ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_txypp, Constants.Dic.SUBJECTIVE_COMFORT);
		//ViewUtil.buildSpiByMedConfig(getActivity(), cure_spinner_glq, Constants.Dic.PERFUSION_DEVICE);
	}

	@Override
	public void setCommonObjectValue(Object t) {
		MedCureInfo cureInfo = (MedCureInfo) t;
		getPatientVasularAccessByHemoId(cureInfo);
	}

	@Override
	public void changeObjectByView(Object t) {
		MedCureInfo cureInfo = (MedCureInfo) t;
		cureInfo.setUse_Type("");
		ViewUtil.getValueFromView(getCurrentView().findViewById(R.id.rootView), cureInfo);
	}

	@Override
	public void setListeners() {
		cure_spinner_zlfs.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
//						String mode = arg0.getAdapter().getItem(arg2).toString();
//						if(mode.equals("HP")||mode.equals("HD+HP"))
//						{
//							cure_spinner_glq.setEnabled(true);
//						}
//						else
//						{
//							cure_spinner_glq.setSelection(0);
//							cure_spinner_glq.setEnabled(false);
//						}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
		
		cure_spinner_xgtl.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				//_cureInfoPgFm.onSpinnerXgtlChnage(parent.getAdapter().getItem(position).toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		cure_spinner_sjdw.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				cure_spinner_zjdw.setSelection(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
		
		/*cure_radiobtn_jhqsy_sc.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
				{
					if(cure_radiobtn_jhqsy_cf.isChecked())
					{
						cure_radiobtn_jhqsy_cf.setChecked(false);
					}
				}
			}});*/
		
		/*cure_radiobtn_jhqsy_cf.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1)
				{
					if(cure_radiobtn_jhqsy_sc.isChecked())
					{
						cure_radiobtn_jhqsy_sc.setChecked(false);
					}
				}
			}});*/
	}
	
	void getPatientVasularAccessByHemoId(final MedCureInfo cureInfo){
		if(progressDialog == null)
			progressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.dialog_data_commit), true);
		else
			progressDialog.show();
		
		
		MedPatientScheduleService.getPatientVasularAccessByHemoId(cureInfo.getHemodialysis_Id() ,new OnNetListener<List<MedVasularAccess>>() {
			@Override
			public void onResponse(List<MedVasularAccess> object) {
				progressDialog.cancel();
				ViewUtil.buildSpiData(getActivity(), cure_spinner_xgtl, MedVasularAccess.class, object);
				ViewUtil.setValueToView(getCurrentView().findViewById(R.id.rootView), cureInfo);
			}
			
			@Override
			public void onError(String errorMessage) {
				progressDialog.cancel();
				ToastUtil.show(getActivity(), errorMessage);
			}
		});
	}
}

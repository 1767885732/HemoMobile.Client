package com.mdsd.docare.hemodialysis.app.ui.patient.fragment;

import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.entity.Constants;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.core.ui.fragment.BaseFragment;
import com.mdsd.docare.hemodialysis.app.entity.server.MedConfig;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureInfo;
import com.mdsd.docare.hemodialysis.app.service.MedPatientScheduleService;
import com.mdsd.docare.hemodialysis.app.ui.patient.PatientTabActivity;
import com.mdsd.docare.hemodialysis.app.util.DataUtil;
import com.mdsd.library.utils.StringUtils;
import com.mdsd.library.utils.ToastUtil;
/**
 * 透析小结
 * 
 * <br>
 * @author jianyu.l
 * @since 2014年8月18日
 */
public class DialysisSummaryFm extends BaseFragment implements OnClickListener{
	// view
	AlertDialog modelListDialog = null;
	ProgressDialog progressDialog;
	EditText etInput;
	
	// variable
	MedCureInfo currMedCureInfo = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.initView(inflater, container, R.layout.dialysis_summary_fm);
	}
	
	@Override
	public void findViews() {
		super.findViews();
		
		etInput = (EditText) getView().findViewById(R.id.etInput);
	}
	
	@Override
	public void setListeners() {
		super.setListeners();
		
		getView().findViewById(R.id.btnUseModel).setOnClickListener(this);
		getView().findViewById(R.id.btnSave).setOnClickListener(this);
	}
	
	@Override
	public void setViewData() {
		super.setViewData();
		etInput.setSelection(etInput.length());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnUseModel:// 使用模板
			if(modelListDialog == null){
				List<MedConfig> list =  DataUtil.getMedConfigs(Constants.Dic.SUMMARY_MODEL);
				final String[] configs = new String[list.size()];
				
				for (int i = 0; i < list.size(); i++) {
					configs[i] = list.get(i).toString();
				}
				
				modelListDialog = new AlertDialog.Builder(getActivity()).setItems(configs, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						etInput.setText(configs[which]);
						etInput.setSelection(etInput.length());
					}
				}).create();
			}
			
			
			modelListDialog.show();			
			break;

		case R.id.btnSave:// 保存小结
			if(etInput.getText().length() == 0){
				ToastUtil.show(getActivity(), "小结不能为空");
				return;
			}
			
			
			if(progressDialog == null)
				progressDialog = ProgressDialog.show(getActivity(), "", "正在保存数据...", true);
			else
				progressDialog.show();
			
			// 根据服务接口说明，此保存方法，与保存治疗信息方法一样
			currMedCureInfo.setSummary(etInput.getText().toString());
			MedPatientScheduleService.saveCureInfo(currMedCureInfo, new OnNetListener<String>() {
				
				@Override
				public void onResponse(String object)
				{
					progressDialog.cancel();
					ToastUtil.show(getActivity().getApplicationContext(), "保存成功");
					PatientTabActivity.currMedCureInfo = currMedCureInfo;
				}
				
				@Override
				public void onError(String errorMessage) {
					progressDialog.cancel();
					ToastUtil.show(getActivity().getApplicationContext(), errorMessage);
					
				}
			});
			
			break;
		}
	}
	
	@Override
	public void onSelected() {
		// if(isFirstLoad){
		currMedCureInfo = PatientTabActivity.currMedCureInfo;

		if (!StringUtils.isEmpty(currMedCureInfo.getSummary()))
			etInput.setText(currMedCureInfo.getSummary());
		// }
		
		super.onSelected();
	}
}

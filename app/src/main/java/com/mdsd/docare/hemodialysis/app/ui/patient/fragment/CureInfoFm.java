package com.mdsd.docare.hemodialysis.app.ui.patient.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.core.ui.fragment.BaseChildFragment;
import com.mdsd.docare.hemodialysis.app.core.ui.fragment.BaseFragment;
import com.mdsd.docare.hemodialysis.app.core.ui.fragment.BaseFragmentAdapter;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureInfo;
import com.mdsd.docare.hemodialysis.app.service.MedPatientScheduleService;
import com.mdsd.docare.hemodialysis.app.ui.patient.PatientTabActivity;
import com.mdsd.library.ui.PagerSlidingTabStrip;
import com.mdsd.library.utils.ToastUtil;

/**
 * 治疗信息
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月18日
 */
public class CureInfoFm extends BaseFragment {
	// view
	PagerSlidingTabStrip tabStrip;
	ViewPager viewPager;
	private ProgressDialog progressDialog;
	private AlertDialog alertDialog;
	
	// variable
	BaseFragmentAdapter fragmentAdapter;
	List<BaseChildFragment> fragmentList;
	List<String> titleList = null;
	
	MedCureInfo currMedCureInfo = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view= super.initView(inflater, container, R.layout.cure_info_fm);
		//setHasOptionsMenu(true);
		return view;
	}
	
	@Override
	public void beforeInitView() {
		super.beforeInitView();
		
		fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager());
		
		fragmentList = new ArrayList<BaseChildFragment>();
		titleList = new ArrayList<String>();
	}
	
	@Override
	public void findViews() {
		super.findViews();
		
		tabStrip = (PagerSlidingTabStrip) getView().findViewById(R.id.tabs);
		viewPager = (ViewPager) getView().findViewById(R.id.pager);
		tabStrip.setTextSize(getResources().getDimensionPixelSize(
				R.dimen.pager_tab_text_size));
	}
	
	@Override
	public void setViewData() {
		super.setViewData();
		
		CureInfoCfFm cureInfoCfFm = new CureInfoCfFm();
		CureInfoPgFm cureInfoPgFm = new CureInfoPgFm();
		cureInfoCfFm._cureInfoPgFm = cureInfoPgFm;
		// 内容
		fragmentList.add(cureInfoCfFm);
		fragmentList.add(cureInfoPgFm);
		
		// 标题
		titleList.add(getResources().getString(R.string.page_tab_cure_info_tx));
		titleList.add(getResources().getString(R.string.page_tab_cure_info_pg));
		
		fragmentAdapter.addTitleList(titleList);
		fragmentAdapter.addFragmentList(fragmentList);
		
		viewPager.setAdapter(fragmentAdapter);
		tabStrip.setViewPager(viewPager);
	}
	
	@Override
	public void onSelected() {
		// if(isFirstLoad){
		currMedCureInfo = PatientTabActivity.currMedCureInfo;
		for (BaseChildFragment childFragment : fragmentList) {
			childFragment.setCommonObjectValue(currMedCureInfo);
		}
		// }
		
		super.onSelected();
		
		if(PatientTabActivity.currMedPatientSchedule.getINFECTIOUS_CHECK_RESULT().equals("待查"))
		{
			String name=PatientTabActivity.currMedPatientSchedule.getPATIENTNAME();
			if (alertDialog == null) {
				alertDialog = new AlertDialog.Builder(getActivity()).create();
				alertDialog.setTitle(R.string.comm_prompt);
				alertDialog.setMessage("患者"+name+"的传染病检验结果为待查，请多留意！");
				alertDialog.setButton(Dialog.BUTTON_POSITIVE, "确定", new OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}});
			}
		}
	}
	
	@Override
	public void onChildFragmentCall() {
		super.onChildFragmentCall();
		
		for (BaseChildFragment childFragment : fragmentList) {
			childFragment.changeObjectByView(currMedCureInfo);
		}
		
		saveCurrMedCureInfo();
	}
	
	/**
	 * 保存治疗信息
	 */
	private void saveCurrMedCureInfo(){
		if(progressDialog == null)
			progressDialog = ProgressDialog.show(getActivity(), "", "正在保存数据...", true);
		else
			progressDialog.show();
		
		// 保存数据
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
	}
	
	/*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        inflater.inflate(R.menu.patient_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
		if(item.getItemId() == R.id.menuRefresh){
			//loadPatientListFromServer();
		}
        return super.onOptionsItemSelected(item);
    }*/
}

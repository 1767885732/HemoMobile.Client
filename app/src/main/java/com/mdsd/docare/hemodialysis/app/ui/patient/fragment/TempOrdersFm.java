package com.mdsd.docare.hemodialysis.app.ui.patient.fragment;

import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.core.ui.fragment.BaseFragment;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureDrug;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureLongDrug;
import com.mdsd.docare.hemodialysis.app.service.MedPatientScheduleService;
import com.mdsd.docare.hemodialysis.app.ui.patient.PatientTabActivity;
import com.mdsd.docare.hemodialysis.app.ui.patient.TempOrdersListItemView;
import com.mdsd.library.ui.easyadapter.EasyAdapter;
import com.mdsd.library.utils.ToastUtil;

/**
 * 临时医嘱
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月18日
 */
/**
 * @author Peter.Lv
 *
 */
public class TempOrdersFm extends BaseFragment {
	// view
	ListView listView;
	ProgressDialog progressDialog;
	
	// variable
	EasyAdapter<MedCureDrug> easyAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.initView(inflater, container, R.layout.temp_orders_fm);
	}
	
	@Override
	public void beforeInitView() {
		super.beforeInitView();
		
		easyAdapter = new EasyAdapter<MedCureDrug>(getActivity(), TempOrdersListItemView.class);
	}
	
	@Override
	public void findViews() {
		super.findViews();
		
		listView = (ListView) getView().findViewById(R.id.listView);
	}

	@Override
	public void setViewData() {
		super.setViewData();
		
		listView.setAdapter(easyAdapter);
		listView.setEmptyView(getView().findViewById(R.id.empty));
	};
	
	
	@Override
	public void onSelected() {
		// if(isFirstLoad){
		getLongOrder();
		getTempOrdersList();
		// }
		super.onSelected();
	}
	// ----------------
	// private method
	// ----------------
	
	/**
	 * 获得临时医嘱集合
	 */
	private void getTempOrdersList() {
		if(progressDialog == null)
			progressDialog = ProgressDialog.show(getActivity(), "", "正在加载数据...", true);
		else
			progressDialog.show();
		
		MedPatientScheduleService.getTempOrdersList(PatientTabActivity.currMedCureInfo.getHemodialysis_Id(), new OnNetListener<List<MedCureDrug>>() {
			
			@Override
			public void onResponse(List<MedCureDrug> object) {
				progressDialog.cancel();
				easyAdapter.setItems(object);
			}
			
			@Override
			public void onError(String errorMessage) {
				progressDialog.cancel();
				ToastUtil.show(getActivity(), errorMessage);
			}
		});
	}
	
	/**
	 * 获得长期医嘱
	 */
	private void getLongOrder() {
		if(progressDialog == null)
			progressDialog = ProgressDialog.show(getActivity(), "", "正在加载数据...", true);
		else
			progressDialog.show();
		
		MedPatientScheduleService.getLongOrder(
				PatientTabActivity.currMedCureInfo.getHemodialysis_Id(),
				new OnNetListener<List<MedCureLongDrug>>() {

					@Override
					public void onResponse(List<MedCureLongDrug> object) {
						// TODO Auto-generated method stub
						progressDialog.cancel();
						if (object != null && object.size()>0) {
							
							String rate = object.get(0).getDrug_times();
							if (rate.equals("1")) {
								rate = "隔次";
							} else if (rate.equals("2")) {
								rate = "每天";
							} else if (rate.equals("3")) {
								rate = "每周" + object.get(0).getDrug_days();
							}
							((TextView) getView().findViewById(R.id.tvShow))
									.setText(String.format("%s %s %s%s %s %s %s\n备注：%s",
											object.get(0).getDrug_name(),
											rate,
											object.get(0).getDosage(),
											object.get(0).getUnit_name(),
											object.get(0).getDrug_mode_name(),
											object.get(0).getName(),
											object.get(0).getCreate_date(),
											object.get(0).getRemark()));
							
							if(object.size()>1)
							{
								rate = object.get(1).getDrug_times();
								if (rate.equals("1")) {
									rate = "隔次";
								} else if (rate.equals("2")) {
									rate = "每天";
								} else if (rate.equals("3")) {
									rate = "每周" + object.get(1).getDrug_days();
								}
								((TextView) getView().findViewById(R.id.tvShow2))
										.setText(String.format("%s %s %s%s %s %s %s\n备注：%s",
												object.get(1).getDrug_name(),
												rate,
												object.get(1).getDosage(),
												object.get(1).getUnit_name(),
												object.get(1).getDrug_mode_name(),
												object.get(1).getName(),
												object.get(1).getCreate_date(),
												object.get(1).getRemark()));
							}
						}
					}

					@Override
					public void onError(String errorMessage) {
						progressDialog.cancel();
						ToastUtil.show(getActivity(), errorMessage);
					}
				});
	}
}

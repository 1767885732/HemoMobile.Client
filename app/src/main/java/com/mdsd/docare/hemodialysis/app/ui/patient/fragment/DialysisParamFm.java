package com.mdsd.docare.hemodialysis.app.ui.patient.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.entity.Constants;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.core.ui.fragment.BaseFragment;
import com.mdsd.docare.hemodialysis.app.entity.app.DialysisParamListItem;
import com.mdsd.docare.hemodialysis.app.entity.server.MedHemoParameter;
import com.mdsd.docare.hemodialysis.app.service.MedPatientScheduleService;
import com.mdsd.docare.hemodialysis.app.ui.component.SinglePopupWindow;
import com.mdsd.docare.hemodialysis.app.ui.component.SinglePopupWindow.SingleChoiceItem;
import com.mdsd.docare.hemodialysis.app.ui.component.SinglePopupWindow.SingleItemClickListener;
import com.mdsd.docare.hemodialysis.app.ui.patient.DialysisParamEditActivity;
import com.mdsd.docare.hemodialysis.app.ui.patient.DialysisParamListItemView;
import com.mdsd.docare.hemodialysis.app.ui.patient.PatientTabActivity;
import com.mdsd.library.ui.easyadapter.EasyAdapter;
import com.mdsd.library.utils.StringUtils;
import com.mdsd.library.utils.TimeUtils;
import com.mdsd.library.utils.ToastUtil;

/**
 * 透析参数
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月18日
 */
public class DialysisParamFm extends BaseFragment implements OnClickListener
{

	// view
	ListView listView;
	SinglePopupWindow popupWindow;

	// variable
	EasyAdapter<DialysisParamListItem> easyAdapter;

	Intent dialysisParamEditIntent;
	
	private ProgressDialog progressDialog;
	AlertDialog deleteAlertDialog;
	
	/**
	 * 当前选择的透析参数对象
	 */
	MedHemoParameter currHemoParameter;
	
	/**
	 * 列表显示的数据源
	 */
	public static List<DialysisParamListItem> dialysisParamList;
	
	/**
	 * 获取的透析参数数据源
	 */
	private List<MedHemoParameter> medHemoParameters;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return super.initView(inflater, container, R.layout.dialysis_param_fm);
	}
	
	@Override
	public void beforeInitView()
	{
		super.beforeInitView();
		dialysisParamList = new ArrayList<DialysisParamListItem>();
		easyAdapter = new EasyAdapter<DialysisParamListItem>(getActivity(), DialysisParamListItemView.class,dialysisParamList);
	}

	@Override
	public void findViews()
	{
		super.findViews();

		listView = (ListView) getView().findViewById(R.id.listView);
	}

	@Override
	public void setViewData()
	{
		super.setViewData();
		
		listView.setAdapter(easyAdapter);
	};

	@Override
	public void setListeners()
	{
		super.setListeners();
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				currHemoParameter = medHemoParameters.get(position);
				popupSingleWindow();
			}
		});
		getView().findViewById(R.id.btnAdd).setOnClickListener(this);
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == Activity.RESULT_OK){// 如果保存成功返回，则重新刷新列表
			getParamList();
		}
		else
		{
			getParamList();
		}
	}

	// ----------------
	// private method
	// ----------------
	/**
	 * 弹出编辑选择窗口
	 */
	void popupSingleWindow()
	{
		if (popupWindow == null)
		{
			List<SingleChoiceItem> list = new ArrayList<SingleChoiceItem>();
			SingleChoiceItem choiceItem = new SingleChoiceItem("复制", R.color.app_green_color, R.color.white);
			list.add(choiceItem);

			choiceItem = new SingleChoiceItem("编辑", R.color.app_green_color, R.color.white);
			list.add(choiceItem);

			choiceItem = new SingleChoiceItem("删除", R.color.button_gray, R.color.white);
			list.add(choiceItem);

			popupWindow = SinglePopupWindow.build(getActivity(), list, new SingleItemClickListener()
					{
						@Override
						public void onItemClick(int position,View v)
						{
							popupWindow.dismiss();

							switch (position)
							{
								case 0:// 复制
									currHemoParameter.setHemodialysis_Parameters_Id("");
									currHemoParameter.setCardiotach(0);
									currHemoParameter.setSystolic_Pressure(0);
									currHemoParameter.setDiastolic_Pressure(0);
									currHemoParameter.setCreate_Date("");
									currHemoParameter.setClinical_Manifestation("");
									toDialysisParamEditActivity(currHemoParameter);
									break;
	
								case 1:// 编辑
									toDialysisParamEditActivity(currHemoParameter);
									break;
								case 2:// 删除
									// 弹出删除确认框
									if(deleteAlertDialog == null){
										android.content.DialogInterface.OnClickListener onClickListener = new android.content.DialogInterface.OnClickListener() {
											
											@Override
											public void onClick(DialogInterface dialog, int which) {
												switch (which) {
												case Dialog.BUTTON_POSITIVE:
													// 删除
													MedPatientScheduleService.deleteHemoParameter(currHemoParameter.getHemodialysis_Parameters_Id(), new OnNetListener<String>() {
														@Override
														public void onResponse(String object) {
															ToastUtil.show(getActivity(), "删除成功");
															
															int position = medHemoParameters.indexOf(currHemoParameter);
															medHemoParameters.remove(position);
															easyAdapter.remove(position);
														}
														
														@Override
														public void onError(String errorMessage) {
															ToastUtil.show(getActivity(), errorMessage);
															
														}
													});
													break;

												default:
													dialog.cancel();
													break;
												}
												
											}
										};
										
										
										deleteAlertDialog = new AlertDialog.Builder(getActivity()).create();
										deleteAlertDialog.setTitle(R.string.comm_prompt);
										deleteAlertDialog.setMessage("删除该记录无法还原，是否确定删除？");
										deleteAlertDialog.setButton(Dialog.BUTTON_POSITIVE, "是", onClickListener);
										deleteAlertDialog.setButton(Dialog.BUTTON_NEGATIVE, "否", onClickListener);
									}
									
									deleteAlertDialog.show();
									
									break;
							}
						}
					});
		}

		popupWindow.showFromBottom(getActivity().getWindow().getDecorView());
	}

	/**
	 * 跳转到条目编辑页面
	 */
	void toDialysisParamEditActivity(MedHemoParameter hemoParameter)
	{
		if (dialysisParamEditIntent == null)
		{
			dialysisParamEditIntent = new Intent(getActivity(), DialysisParamEditActivity.class);
		}

		dialysisParamEditIntent.putExtra(Constants.ExtraKey.MED_HEMO_PARAMETER, hemoParameter);
		startActivityForResult(dialysisParamEditIntent, 0);
	}

	/**
	 * 获取列表数据源，正式用
	 * @author zhoucl
	 * @return
	 */
	List<DialysisParamListItem> getParamList()
	{
		if(progressDialog == null)
			progressDialog = ProgressDialog.show(getActivity(), "", "正在加载数据...", true);
		else
			progressDialog.show();
		
		MedPatientScheduleService.getHemoParametersByCureID(PatientTabActivity.currMedCureInfo.getCure_Id(), new OnNetListener<List<MedHemoParameter>>()
				{
					@Override
					public void onResponse(List<MedHemoParameter> object)
					{
						dialysisParamList.clear();
						medHemoParameters = object;
						
						DialysisParamListItem dialysisParamListItem = null;
						String time = "";
						Calendar calendar = Calendar.getInstance();
						for (MedHemoParameter medHemoParameter : medHemoParameters) {
							if(!StringUtils.isEmpty(medHemoParameter.getCreate_Date())){
								calendar.setTime(TimeUtils.strToDate(medHemoParameter.getCreate_Date(),"yyyy/MM/dd HH:mm:ss"));
								time = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
							}
							
							dialysisParamListItem = new DialysisParamListItem(medHemoParameter.getCure_Id(), 
									time,
									medHemoParameter.getSystolic_Pressure()!=0.0?medHemoParameter.getSystolic_Pressure()+"":"",
									medHemoParameter.getDiastolic_Pressure()!=0.0?medHemoParameter.getDiastolic_Pressure()+"":"",
									medHemoParameter.getVenous_Pressure()!=0.0?medHemoParameter.getVenous_Pressure()+"":"",
									medHemoParameter.getTransmembrane_Pressure()!=0.0?medHemoParameter.getTransmembrane_Pressure()+"":"",
									medHemoParameter.getBreath() != 0.0 ? medHemoParameter.getBreath() + "" : "");
							
							dialysisParamList.add(dialysisParamListItem);
						}
						
						easyAdapter.notifyDataSetChanged();
						progressDialog.cancel();
					}
					
					@Override
					public void onError(String errorMessage)
					{
						progressDialog.cancel();
						ToastUtil.show(getActivity().getApplicationContext(), errorMessage);
					}
				});

		return dialysisParamList;
	}
	
	@Override
	public void onSelected() {
		// if(isFirstLoad){
		getParamList();
		// }
		
		super.onSelected();
	}

	@Override
	public void onClick(View v) {
		// 新增
		toDialysisParamEditActivity(null);
	};
}

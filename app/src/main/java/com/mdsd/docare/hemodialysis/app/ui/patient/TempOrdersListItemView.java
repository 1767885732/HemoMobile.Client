package com.mdsd.docare.hemodialysis.app.ui.patient;

import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.entity.AppContext;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureDrug;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureInfo;
import com.mdsd.docare.hemodialysis.app.service.MedPatientScheduleService;
import com.mdsd.library.ui.easyadapter.ItemViewHolder;
import com.mdsd.library.ui.easyadapter.PositionInfo;
import com.mdsd.library.ui.easyadapter.annotations.LayoutId;
import com.mdsd.library.ui.easyadapter.annotations.ViewId;
import com.mdsd.library.utils.ToastUtil;

import java.util.List;

/**
 * 临时医嘱listitem
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月19日
 */
@LayoutId(R.layout.temp_orders_list_item)
public class TempOrdersListItemView extends ItemViewHolder<MedCureDrug> {

	@ViewId(R.id.tvShowName)
	TextView tvShowName;
	
	@ViewId(R.id.btnExcute)
	Button btnExcute;
	
	ProgressDialog progressDialog = null;
	
	public TempOrdersListItemView(View view) {
		super(view);
		
		// 执行，或退回的操作
		btnExcute.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(progressDialog == null)
					progressDialog = ProgressDialog.show(getContext(), "", "处理中...");
				else
					progressDialog.show();
				
				final String state = getItem().getStatus();// 临时存储之前的状态
				
				if(btnExcute.getText().equals(getContext().getResources().getString(R.string.temp_orders_back))){
					getItem().setStatus("2");
				}else if(btnExcute.getText().equals(getContext().getResources().getString(R.string.temp_orders_excute))){
					getItem().setStatus("1");
				}
				
				/*MedPatientScheduleService.updataCureDrugStateByParma(getItem().getStatus(),
						getItem().getHemodialysis_id(), getItem().getCom_no(),
						getItem().getCreate_date(),
						new OnNetListener<String>() {

							@Override
							public void onResponse(String object) {
								progressDialog.dismiss();
								// 如果更新成功，则直接刷新界面
								getAdapter().notifyDataSetChanged();
							}

							@Override
							public void onError(String errorMessage) {
								progressDialog.dismiss();
								// 失败则还原状态
								getItem().setStatus(state);
								ToastUtil.show(getContext(), errorMessage);
							}
						});*/
				
				MedPatientScheduleService.saveCureDrug(getItem(),PatientTabActivity.currMedCureInfo.getCure_Id(),
						PatientTabActivity.currMedCureInfo.getRecipe_Id(),AppContext.getInstance().currentUser.getEMP_NO(),
						getItem().getStatus(),
						new OnNetListener<String>() {

							@Override
							public void onResponse(String object) {
								progressDialog.dismiss();
								// 如果更新成功，则直接刷新界面
								getAdapter().notifyDataSetChanged();
								getTempOrdersList();
								if(getItem().getStatus().equals("1"))
								{
									reloadMainCure();
								}
							}

							@Override
							public void onError(String errorMessage) {
								progressDialog.dismiss();
								// 失败则还原状态
								getItem().setStatus(state);
								ToastUtil.show(getContext(), errorMessage);
							}
						});
			}
		});
	}

	@Override
	public void onSetValues(MedCureDrug item, PositionInfo positionInfo) {
		tvShowName.setText(String.format("%s %s%s %s %s %s", item.getDrug_name(),item.getDosage(),item.getUnit_name(),item.getDrug_mode_name(),item.getName(),item.getCreate_date()));
		
		if("1".equals(item.getStatus())){
			btnExcute.setText(R.string.temp_orders_back);
			btnExcute.setBackgroundResource(R.color.app_enable);
		}else if("2".equals(item.getStatus())){
			tvShowName.setTextColor(getContext().getResources().getColor(R.color.app_enable));
			btnExcute.setEnabled(false);
			btnExcute.setText(R.string.temp_orders_back_already);
			btnExcute.setBackgroundResource(R.color.app_enable);
		}
	}

	private void getTempOrdersList() {
		if(progressDialog == null)
			progressDialog = ProgressDialog.show(getContext(), "", "正在加载数据...", true);
		else
			progressDialog.show();

		MedPatientScheduleService.getTempOrdersList(PatientTabActivity.currMedCureInfo.getHemodialysis_Id(), new OnNetListener<List<MedCureDrug>>() {

			@Override
			public void onResponse(List<MedCureDrug> object) {
				progressDialog.cancel();
				getAdapter().setItems(object);
			}

			@Override
			public void onError(String errorMessage) {
				progressDialog.cancel();
				ToastUtil.show(getContext(), errorMessage);
			}
		});
	}

	private void reloadMainCure()
	{
		String recipeId = PatientTabActivity.currMedPatientSchedule.getRECIPE_ID();
		if (progressDialog == null)
			progressDialog = ProgressDialog.show(getContext(), "", "正在加载数据...", true);
		else
			progressDialog.show();

		MedPatientScheduleService.getMainCureByRecipeId(recipeId, new OnNetListener<MedCureInfo>() {
			@Override
			public void onResponse(MedCureInfo cureInfo) {
				progressDialog.cancel();
				PatientTabActivity.currMedCureInfo = cureInfo;
			}

			@Override
			public void onError(String errorMessage) {
				progressDialog.cancel();
				ToastUtil.show(getContext(), errorMessage);
			}
		});
	}
}

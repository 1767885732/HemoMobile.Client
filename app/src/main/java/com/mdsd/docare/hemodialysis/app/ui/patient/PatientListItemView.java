package com.mdsd.docare.hemodialysis.app.ui.patient;

import java.io.UnsupportedEncodingException;

import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.entity.server.MedPatientSchedule;
import com.mdsd.docare.hemodialysis.app.util.BitmapUtil;
import com.mdsd.library.ui.easyadapter.ItemViewHolder;
import com.mdsd.library.ui.easyadapter.PositionInfo;
import com.mdsd.library.ui.easyadapter.annotations.LayoutId;
import com.mdsd.library.ui.easyadapter.annotations.ViewId;
import com.mdsd.library.utils.StringUtils;

@LayoutId(R.layout.patient_list_item)
public class PatientListItemView extends ItemViewHolder<MedPatientSchedule> {
	
	@ViewId(R.id.ivHead)
	ImageView ivHead;

	@ViewId(R.id.tvContent)
	TextView tvContent;
	
	@ViewId(R.id.ivStatus)
	ImageView ivStatus;
	
	private PatientListActivity patientList=PatientListActivity.getInstance();
	
	public PatientListItemView(View view) {
		super(view);
	}

	@Override
	public void onSetValues(MedPatientSchedule item, PositionInfo positionInfo) {
		if(isSelected()){
			getView().setBackgroundResource(R.color.list_item_select);
			tvContent.setTextColor(getContext().getResources().getColor(R.color.white));
		}else{
			getView().setBackgroundResource(R.color.white);
			tvContent.setTextColor(getContext().getResources().getColor(R.color.black));
		}
		
		
//		tvContent.setCompoundDrawablesWithIntrinsicBounds(
//				item.getSEX().equals("男") ? R.drawable.ic_man
//						: R.drawable.ic_woman, 0, 0, 0);
		String result="";
		if(item.getINFECTIOUS_CHECK_RESULT().equals("乙肝")||item.getINFECTIOUS_CHECK_RESULT().equals("丙肝")||item.getINFECTIOUS_CHECK_RESULT().equals("梅毒")||item.getINFECTIOUS_CHECK_RESULT().equals("艾滋病"))
		{
			result=item.getINFECTIOUS_CHECK_RESULT();
		}
		
		tvContent.setText(String.format("%s    %s\n透析号：%s\n治疗项目：%s\n传染病：%s\n净化器：%s\n预定时间：%s小时\n签到时间：%s\n签到顺序：%s", item.getPATIENTNAME(),
				item.getSEX(), item.getHEMODIALYSIS_ID(),item.getPURIFICATION_MODE(),result,item.getMODEL_NAME(),item.getFREQUENCY_HOURS(),item.getCHECK_DATE(),item.getCHECK_NUM()));
		
		if(item.getSTATUS() != null)
			ivStatus.setImageResource(getImgResource(item.getSTART_TIME(),item.getEND_TIME()));
		byte[] temp = Base64.decode(item.getPAT_PIC(), Base64.DEFAULT);
		Bitmap bitmap=BitmapUtil.getBitmapFromByte(temp);
		ivHead.setImageBitmap(bitmap);
  
	}
	
	/**
	 * 获取对应的治疗的状态图的资源ID
	 *
	 * @param scheduleStatus
	 * @return
	 * @deprecated use getImgResource(String startTime, String endTime)
	 */
	@SuppressWarnings("unused")
	private int getImgResource(int scheduleStatus) {
		
		if (scheduleStatus == 0) {
			return R.drawable.ic_zlb;
		} else if (scheduleStatus == 1) {
			return R.drawable.ic_zlz;
		} else if (scheduleStatus == 2) {
			return R.drawable.ic_zla;
		}

		return 0;
	}
	
	/**
	 * 获取对应的治疗的状态图的资源ID
	 *
	 * @param scheduleStatus
	 * @return
	 */
	private int getImgResource(String startTime, String endTime) {
		if (!StringUtils.isEmpty(endTime)) {// 治疗结束
			return R.drawable.ic_zla;
		} else if (!StringUtils.isEmpty(startTime)) {// 治疗中
			return R.drawable.ic_zlz;
		}
		return R.drawable.ic_zlb;// 治疗未开始
	}

}

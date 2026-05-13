package com.mdsd.docare.hemodialysis.app.ui.patient;

import android.view.View;
import android.widget.TextView;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.entity.app.DialysisParamListItem;
import com.mdsd.library.ui.easyadapter.ItemViewHolder;
import com.mdsd.library.ui.easyadapter.PositionInfo;
import com.mdsd.library.ui.easyadapter.annotations.LayoutId;
import com.mdsd.library.ui.easyadapter.annotations.ViewId;

/**
 * 透析参数列表item view
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月20日
 */
@LayoutId(R.layout.dialysis_param_list_item)
public class DialysisParamListItemView extends
		ItemViewHolder<DialysisParamListItem> {
	
	@ViewId(R.id.tvRecordTime)
	TextView tvRecordTime;
	
	@ViewId(R.id.tvSsy)
	TextView tvSsy;
	
	@ViewId(R.id.tvSzy)
	TextView tvSzy;
	
	@ViewId(R.id.tvJmy)
	TextView tvJmy;
	
	@ViewId(R.id.tvKmy)
	TextView tvKmy;
	
	@ViewId(R.id.tvMb)
	TextView tvMb;

	public DialysisParamListItemView(View view) {
		super(view);
	}

	@Override
	public void onSetValues(DialysisParamListItem item,
			PositionInfo positionInfo) {
		getView().setBackgroundResource(positionInfo.getPosition()%2 == 0?R.drawable.dialysi_param_row_1:R.drawable.dialysi_param_row_2);
		
		tvRecordTime.setText(item.getRecordTime());
		tvSsy.setText(item.getSsy());
		tvSzy.setText(item.getSzy());
		tvJmy.setText(item.getJmy());
		tvKmy.setText(item.getKmy());
		tvMb.setText(item.getMb());

	}

}

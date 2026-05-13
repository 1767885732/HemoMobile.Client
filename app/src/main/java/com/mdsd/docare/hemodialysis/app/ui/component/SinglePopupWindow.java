package com.mdsd.docare.hemodialysis.app.ui.component;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.library.ui.autoBgButton.AutoBgButton;
/**
 * 弹出的单选窗口
 * 
 * <br>
 * @author jianyu.l
 * @since 2014年8月22日
 */
public class SinglePopupWindow extends PopupWindow implements OnClickListener{

	/**
	 * 选择的数据源
	 */
	List<SingleChoiceItem> singleChoiceItems = null;
	
	/**
	 * 内容视图布局文件
	 */
	static LinearLayout contentLinearLayout;
	
	/**
	 * 对应的子view的布局参数
	 */
	LayoutParams textViewParams;
	
	SingleItemClickListener singleItemClickListener;
	
	Context mContext;
	
	
	private SinglePopupWindow(Context context, List<SingleChoiceItem> choiceItems,SingleItemClickListener singleItemClickListener){
		singleChoiceItems = choiceItems;
		mContext = context;
		this.singleItemClickListener = singleItemClickListener;
		
		if(singleChoiceItems == null)
			singleChoiceItems = new ArrayList<SingleChoiceItem>();
		
		// 初始化内容视图
		contentLinearLayout = new LinearLayout(context);
		
		contentLinearLayout.setOrientation(LinearLayout.VERTICAL);
		int padding = context.getResources().getDimensionPixelSize(R.dimen.margin_big);
		contentLinearLayout.setPadding(padding, padding, padding, padding);
		contentLinearLayout.setBackgroundResource(R.color.transparent_half);
		
		// 初始化子视图的布局参数
		textViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.editext_def_height));
		textViewParams.topMargin = context.getResources().getDimensionPixelSize(R.dimen.margin_normal);
		
		// 更新视图
		updateContentView();
	}
	
	/**
	 * 构建一个默认的单选弹出窗口
	 *
	 * @param choiceItems
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static SinglePopupWindow build(Context context,List<SingleChoiceItem> choiceItems,SingleItemClickListener singleItemClickListener){
		SinglePopupWindow popupWindow = new SinglePopupWindow(context,choiceItems,singleItemClickListener);
		
		popupWindow.setContentView(contentLinearLayout);
		// 为了响应点击window外部可以消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.PopwinAnimDef);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setWindowLayoutMode(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		return popupWindow;
	}
	
	/**
	 * 更新视图
	 *
	 * @return
	 */
	private void updateContentView(){
		if(this.singleChoiceItems.size() == 0 )
			return;
		
		contentLinearLayout.removeAllViews();
		AutoBgButton autoBgButton = null;
		SingleChoiceItem singleChoiceItem = null;
		for (int i = 0; i < singleChoiceItems.size(); i++) {
			singleChoiceItem = singleChoiceItems.get(i);
			
			autoBgButton = new AutoBgButton(mContext);
			autoBgButton.setLayoutParams(i>0?textViewParams:new LayoutParams(LayoutParams.MATCH_PARENT, mContext.getResources().getDimensionPixelSize(R.dimen.editext_def_height)));
			autoBgButton.setBackgroundResource(singleChoiceItem.getBackgroundId());
			autoBgButton.setText(singleChoiceItem.getText());
			autoBgButton.setTextColor(mContext.getResources().getColor(singleChoiceItem.getTextColorId()));
			autoBgButton.setOnClickListener(this);
			autoBgButton.setGravity(Gravity.CENTER);
			autoBgButton.setTag(i);
			
			contentLinearLayout.addView(autoBgButton);
		}
		
	}
	
	/**
	 * 更新视图
	 */
	public void notifyDataSetChanged(){
		updateContentView();
	}
	
	/**
	 * 从底部弹出单选窗口
	 *
	 * @param parentView
	 */
	public void showFromBottom(View parentView){
		showAtLocation(parentView , Gravity.BOTTOM, 0, 0);
	}
	

	@Override
	public void onClick(View v) {
		if(singleItemClickListener != null)
			singleItemClickListener.onItemClick((Integer)v.getTag(),v);
		
	}
	
	
	
	// --------------------
	// class interface
	// --------------------
	/**
	 * 子view单击监听
	 * 
	 * <br>
	 * 
	 * @author jianyu.l
	 * @since 2014年8月18日
	 */
	public interface SingleItemClickListener {
		/**
		 * item click
		 * 
		 * @param position
		 *            (所在位置)
		 */
		void onItemClick(int position,View v);
	}

	/**
	 * 选择的单项类
	 * 
	 * <br>
	 * .
	 * 
	 * @author jianyu.l
	 * @since 2014年8月18日
	 */
	public static class SingleChoiceItem {

		/** 显示的名称. */
		private String text;

		/** 背景资源id. */
		private int backgroundId;

		/** 字体颜色资源id. */
		private int textColorId;

		public SingleChoiceItem(String text, int backgroundId, int textColorId) {
			this.text = text;
			this.backgroundId = backgroundId;
			this.textColorId = textColorId;
		}

		/**
		 * Gets the 显示的名称.
		 * 
		 * @return the 显示的名称
		 */
		public String getText() {
			return text;
		}

		/**
		 * Sets the 显示的名称.
		 * 
		 * @param text
		 *            the new 显示的名称
		 */
		public void setText(String text) {
			this.text = text;
		}

		/**
		 * Gets the 背景资源id.
		 * 
		 * @return the 背景资源id
		 */
		public int getBackgroundId() {
			return backgroundId;
		}

		/**
		 * Sets the 背景资源id.
		 * 
		 * @param backgroundId
		 *            the new 背景资源id
		 */
		public void setBackgroundId(int backgroundId) {
			this.backgroundId = backgroundId;
		}

		/**
		 * Gets the 字体颜色资源id.
		 * 
		 * @return the 字体颜色资源id
		 */
		public int getTextColorId() {
			return textColorId;
		}

		/**
		 * Sets the 字体颜色资源id.
		 * 
		 * @param textColorId
		 *            the new 字体颜色资源id
		 */
		public void setTextColorId(int textColorId) {
			this.textColorId = textColorId;
		}

	}
}

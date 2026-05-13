package com.mdsd.library.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 单选可带分割线的bar<br>
 * 根据数据设置显示对应的单向<br>
 * 说明：样式为统一样式
 * 
 * <br>
 * 
 * @author lijianyu
 * @date 2014年4月1日
 * @time 下午3:08:27
 */
public class SingleBar extends RadioGroup {

	private static final String TAG = "SingleBar";

	/**
	 * 显示的名称
	 */
	private List<String> showWordList = null;
	private List<RadioButton> radioButtons = null;

	/**
	 * 对应的radiobutton的资源id 没有，将使用系统默认的radiobutton
	 */
	private int radioBtResId = 0;

	/**
	 * 分割线的资源id
	 */
	private int dividingLineId = 0;

	/**
	 * 是否使用分割线，默认为true
	 */
	private boolean isUserDividingLine = true;
	
	private int dividerColor = Color.BLACK;

	LayoutInflater inflater = null;
	LayoutParams itemLayoutParams = null;
	LayoutParams dividingLayoutParams = null;

	public SingleBar(Context context) {
		super(context);
		init();
	}

	public SingleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * @param dividerColor the dividerColor to set
	 */
	public void setDividerColor(int dividerColor) {
		this.dividerColor = dividerColor;
	}
	
	/**
	 * Gets the 显示的名称.
	 * 
	 * @return the 显示的名称
	 */
	public List<String> getShowWordList() {
		return showWordList;
	}

	/**
	 * Sets the 显示的名称.
	 * 
	 * @param showWordList
	 *            the new 显示的名称
	 */
	public void setShowWordList(List<String> showWordList) {
		this.showWordList = showWordList;
		List<RadioButton> buttons = new ArrayList<RadioButton>();
		RadioButton rbt = null;
		for (int i = 0; i < showWordList.size(); i++) {
			String str = showWordList.get(i);
			if (str == null)
				str = "";

			if (radioBtResId == 0) {// 没有设置资源文件
				Log.d(TAG, "radioBtResId is 0 ");
				rbt = new RadioButton(getContext());
			} else {
				try {
					rbt = (RadioButton) inflater.inflate(radioBtResId, null);
					rbt.setGravity(Gravity.CENTER);
				} catch (Exception e) {
					Log.d(TAG, "请使用对应的radiobutton资源文件");
				}
			}
			
			rbt.setText(showWordList.get(i));
			rbt.setId(i);
			buttons.add(rbt);
		}
		
		setRadioButtons(buttons);
	}

	public List<RadioButton> getRadioButtons() {
		return radioButtons;
	}

	public void setRadioButtons(List<RadioButton> radioButtons) {
		this.radioButtons = radioButtons;
		notifyDataSetChanged();
	}

	/**
	 * Sets the 对应的radiobutton的资源id 没有，将使用系统默认的radiobutton.
	 * 
	 * @param radioBtResId
	 *            the new 对应的radiobutton的资源id 没有，将使用系统默认的radiobutton
	 */
	public void setRadioBtResId(int radioBtResId) {
		this.radioBtResId = radioBtResId;
	}

	/**
	 * Sets the 分割线的资源id.
	 * 
	 * @param dividingLineId
	 *            the new 分割线的资源id
	 */
	public void setDividingLineId(int dividingLineId) {
		this.dividingLineId = dividingLineId;
	}

	/**
	 * Sets the user dividing line.
	 * 
	 * @param isUserDividingLine
	 *            the new user dividing line
	 */
	public void setUserDividingLine(boolean isUserDividingLine) {
		this.isUserDividingLine = isUserDividingLine;
	}

	// 初始化
	private void init() {
		inflater = LayoutInflater.from(getContext());
		itemLayoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		dividingLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		this.setOrientation(HORIZONTAL);
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);

			if (view instanceof RadioButton) {
				view.setOnClickListener(l);
			}
		}
	}

	/**
	 * Load item view.
	 */
	private void notifyDataSetChanged() {
		if (this.getChildCount() > 0)
			this.removeAllViews();

		if (showWordList == null || showWordList.size() == 0) {
			Log.d(TAG, "showWordList is null");
			return;
		}

		// 分割线
		View dividingLineView = null;

		RadioButton rbt = null;
		for (int i = 0; i < radioButtons.size(); i++) {
			rbt = radioButtons.get(i);
			if (rbt != null) {// 不为空加入到父容器
				this.addView(rbt, itemLayoutParams);
				if (i == 0)
					rbt.setChecked(true);

				if (isUserDividingLine && i != showWordList.size() - 1) {
					if (dividingLineId != 0) {
						dividingLineView = inflater.inflate(dividingLineId,
								null);
					} else {
						dividingLineView = new View(getContext());
						dividingLineView.setBackgroundColor(dividerColor);
						dividingLayoutParams.width = 1;
						dividingLayoutParams.height = LayoutParams.MATCH_PARENT;
					}
					this.addView(dividingLineView, dividingLayoutParams);
				}
			}
		}
	}
}

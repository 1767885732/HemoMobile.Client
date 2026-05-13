package com.mdsd.library.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mdsd.library.R;
/**
 * 
 * <p>com.mdsd.library.ui.FormItem</p>
 * <p>带标题的布局容器，继承RadioGroup</p>
 *
 */
public class FormItem extends RadioGroup {

	private TextView titleView = null;

	public FormItem(Context context) {
		super(context);
		setOrientation(LinearLayout.HORIZONTAL);
		titleView = new TextView(context);
		
		addView(titleView);
	}

	public FormItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setOrientation(LinearLayout.HORIZONTAL);
		titleView = new TextView(context);
		
		// 属性初始化值
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.FormItem);
		setTitle(a.getString(R.styleable.FormItem_itemTitle));
		setTitleSize(a.getDimensionPixelSize(R.styleable.FormItem_titleSize, 14));
		setTitleColor(a.getColor(R.styleable.FormItem_titleColor,
				getResources().getColor(R.color.black)));
		setTitleBackground(a.getResourceId(R.styleable.FormItem_titleBackground,
				R.color.transparent));
		setTitleWidth(a.getDimensionPixelSize(R.styleable.FormItem_titleWidth, 0));
		int space = a.getDimensionPixelSize(R.styleable.FormItem_space, 0);
		a.recycle();

		if(space == 0)
			addView(titleView);
		else{
			LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.rightMargin = space;
			addView(titleView, layoutParams);
		}
		
	}

//	public FormItem(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		
//		titleView = new TextView(context);
//		
//		// 属性初始化值
//		TypedArray a = getContext().obtainStyledAttributes(attrs,
//				R.styleable.FormItem, defStyle, 0);
//		setTitle(a.getString(R.styleable.FormItem_itemTitle));
//		setTitleSize(a.getDimensionPixelSize(R.styleable.FormItem_titleSize, 14));
//		setTitleColor(a.getColor(R.styleable.FormItem_titleColor,
//				getResources().getColor(R.color.black)));
//		setTitleBackground(a.getResourceId(R.styleable.FormItem_titleBackground,
//				R.color.transparent));
//		setTitleWidth(a.getDimensionPixelSize(R.styleable.FormItem_titleWidth, 0));
//		int space = a.getDimensionPixelSize(R.styleable.FormItem_space, 0);
//		a.recycle();
//
//		if(space == 0)
//			addView(titleView);
//		else{
//			LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			layoutParams.rightMargin = space;
//			addView(titleView, layoutParams);
//		}
//	}

	public void setTitle(CharSequence text) {
		titleView.setText(text);
	}

	public void setTitle(int resid) {
		titleView.setText(resid);
	}

	public void setTitleSize(float textSize) {
		titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
	}
	
	public void setTitleColor(int color) {
		titleView.setTextColor(color);
	}
	
	public void setTitleBackground(int resid){
		titleView.setBackgroundResource(resid);
	}
	
	public void setTitleWidth(int pixels){
		if(pixels == 0)
			return;
		
		titleView.setWidth(pixels);
	}

}

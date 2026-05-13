package com.mdsd.library.ui.pullRefreshLayout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PullHeaderView extends LinearLayout {

	private static final String REFRESHING = "加载中...";
	private static final String PULL_DOWN_REFRESH = "下拉刷新";

	private RefreshDrawable mRefreshDrawable;
	private ImageView mRefreshView;
	private PullRefreshLayout mPullLayout;

	/**
	 * 显示的textview
	 */
	TextView textView;

	public PullHeaderView(Context context) {
		super(context);
		init();
	}

	public PullHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public PullHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 设置PullLayout
	 * 
	 * @param pullLayout
	 */
	public void setPullLayout(PullRefreshLayout pullLayout) {
		mPullLayout = pullLayout;

		if (mRefreshDrawable == null) {
			mRefreshDrawable = new RingDrawable(getContext(), mPullLayout);
			mRefreshView.setImageDrawable(mRefreshDrawable);
			addView(mRefreshView, 0);
		}
	}
	
	public void setColorSchemeColors(int[] colorSchemeColors){
		mRefreshDrawable.setColorSchemeColors(colorSchemeColors);
	}
	

	private void init() {
		// 设置为居中布局
		setGravity(Gravity.CENTER_HORIZONTAL);
		setWillNotDraw(false);

		textView = new TextView(getContext());
		textView.setPadding(0, dp2px(20), 0, 0);
		textView.setLayoutParams(new LayoutParams(0,LayoutParams.WRAP_CONTENT,1));
		setRefreshText(PULL_DOWN_REFRESH);

		mRefreshView = new ImageView(getContext());
		mRefreshView.setLayoutParams(new LayoutParams(0,LayoutParams.MATCH_PARENT,1));

		addView(textView);
	}


	@Override
	public void offsetTopAndBottom(int offset) {
		mRefreshDrawable.offsetTopAndBottom(offset);
		bringChildToFront(mRefreshView);
		bringChildToFront(textView);
//		super.offsetTopAndBottom(offset);
	}

	public void setPercent(float percent) {
		mRefreshDrawable.setPercent(percent);
	}

	void start() {
		setRefreshText(REFRESHING);
		mRefreshDrawable.start();
	}

	void stop() {
		setRefreshText(PULL_DOWN_REFRESH);
		mRefreshDrawable.stop();
	}

	private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }
	
	/**
	 * 设置当前显示的内容
	 * 
	 * @param text
	 */
	private void setRefreshText(String text) {
		textView.setText(text);
	}

}

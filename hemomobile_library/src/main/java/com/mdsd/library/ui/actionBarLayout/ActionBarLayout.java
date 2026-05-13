package com.mdsd.library.ui.actionBarLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mdsd.library.ui.actionBar.ActionBar;
/**
 * 带有actionbar的线性布局容器
 * 
 * <br>
 * @author jianyu.l
 * @since 2014年6月24日
 */
public class ActionBarLayout extends LinearLayout {

	ActionBar actionBar;

	public ActionBarLayout(Context context) {
		super(context);
		actionBar = new ActionBar(context);
		init();
	}

	public ActionBarLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ActionBarLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		actionBar = new ActionBar(context,attrs);
		init();
	}

	void init() {
		setOrientation(VERTICAL);

		addView(actionBar);
	}

	public ActionBar getActionBar() {
		return actionBar;
	}
}

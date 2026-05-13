package com.mdsd.library.ui.actionBar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.mdsd.library.R;

/**
 * 简单的顶部actionbar,可设置左侧子按钮文字样式,点击事件和右侧多个按钮,事件;支持对应位置的menu弹出显示(样式为系统当前主题的样式);
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月12日
 */
public class ActionBar extends LinearLayout implements OnClickListener {

	/**
	 * 点击类型
	 * 
	 * @author jianyu.l
	 * @since 2014年5月30日
	 */
	public enum ActionBarClickItem {
		LEFT, TITLE
	}

	public static class BarTitlePosition {
		public static final int CENTER = 0;
		public static final int LEFT = 1;
	}

	TextView tvTitle, tvTitleLeft;
	LinearLayout lytRight;
	ActionBarButton actionLeft;

	private int itemBg, itemWidth, barTextColor;
	private float barTextSize;

	private ActionBarItemClickListener actionBarItemClickListener = null;

	public void setActionBarItemClickListener(
			ActionBarItemClickListener actionBarItemClickListener) {
		this.actionBarItemClickListener = actionBarItemClickListener;
	}

	public ActionBar(Context context) {
		this(context, null);
	}

	public ActionBar(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.ActionBarStyle);
	}

	public ActionBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		if (isInEditMode())
			return;

		// 初始化布局
		inflate(getContext(), R.layout.action_bar, this);

		// 获取对应的view
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitleLeft = (TextView) findViewById(R.id.tvTitleLeft);
		lytRight = (LinearLayout) findViewById(R.id.lytRight);
		actionLeft = (ActionBarButton) findViewById(R.id.actionLeft);

		actionLeft.setOnClickListener(this);
		tvTitleLeft.setOnClickListener(this);

		if (attrs == null)
			return;

		// 属性初始化值
		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.ActionBar, defStyle, 0);

		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				(int) a.getDimension(
						R.styleable.ActionBar_barHeight,
						getResources().getDimension(
								R.dimen.actionbar_default_height))));

		this.setBackgroundResource(a.getResourceId(
				R.styleable.ActionBar_barBackground, R.color.actionbar_bg));
		setTitle(a.getString(R.styleable.ActionBar_barTitle));
		setTitleColor(a.getColor(R.styleable.ActionBar_barTitleColor,
				getResources().getColor(R.color.white)));
		setTitleDisplayPosition(a
				.getInt(R.styleable.ActionBar_barTitlePosition,
						BarTitlePosition.CENTER));
		setTitleSize(a
				.getFloat(R.styleable.ActionBar_barTitleSize, getResources()
						.getDimension(R.dimen.action_bar_title_text_size)));

		setItemBg(a.getResourceId(R.styleable.ActionBar_barItemBackground,
				R.color.actionbar_item_bg_s));
		setActionTextColor(a.getColor(R.styleable.ActionBar_barTextColor,
				getResources().getColor(R.color.white)));
		setActionTextSize(a.getFloat(R.styleable.ActionBar_barTextSize,
				getResources().getDimension(R.dimen.action_bar_text_size)));

		setItemWidth((int) a.getDimension(R.styleable.ActionBar_barItemWidth,
				getResources().getDimension(R.dimen.action_button_min_width)));

		a.recycle();
	}

	/**
	 * 设置action button的字体颜色
	 * 
	 * @param color
	 */
	private void setActionTextColor(int color) {
		this.barTextColor = color;
		actionLeft.getTextView().setTextColor(color);
	}

	/**
	 * 设置action button的字体大小
	 * 
	 * @param color
	 */
	private void setActionTextSize(float textSize) {
		this.barTextSize = textSize;
		actionLeft.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_SP,
				textSize);
	}

	/**
	 * 设置标题显示位置 默认居中显示
	 * 
	 * @param position
	 */
	public void setTitleDisplayPosition(int position) {
		if (position == BarTitlePosition.LEFT) {
			findViewById(R.id.rltTitleLeft).setVisibility(VISIBLE);
			findViewById(R.id.lytTitle).setVisibility(GONE);
		}
	}

	/**
	 * 设置标题颜色
	 * 
	 * @param color
	 */
	public void setTitleColor(int color) {
		tvTitle.setTextColor(color);
		tvTitleLeft.setTextColor(color);
	}

	public void setItemWidth(int minWidth) {
		this.itemWidth = minWidth;

		if (actionLeft != null) {
			actionLeft.getLayoutParams().width = itemWidth;
		}

		for (int i = 0; i < lytRight.getChildCount(); i++) {
			lytRight.getChildAt(i).getLayoutParams().width = minWidth;
			lytRight.getChildAt(i).setLayoutParams(
					lytRight.getChildAt(i).getLayoutParams());
		}
	}

	/**
	 * 设置标题字体大小
	 * 
	 * @param resId
	 */
	public void setTitleSize(int resId) {
		setTitleSize(getResources().getDimension(resId));
	}

	public void setTitleSize(float size) {
		tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
		tvTitleLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
	}

	/**
	 * 设置左侧按钮图片文字
	 * 
	 * @param drawable
	 * @param text
	 * @param showAsImage
	 */
	public void setLeftAction(Drawable drawable, String text,
			boolean showAsImage) {
		actionLeft.setCenterImg(drawable);
		actionLeft.getTextView().setText(text);
		actionLeft.setShowImg(showAsImage);
		actionLeft.setVisibility(View.VISIBLE);
	}

	public void setLeftAction(int resId, String text, boolean showAsImage) {
		this.setLeftAction(getResources().getDrawable(resId), text, showAsImage);
	}

	/**
	 * 设置标题
	 * 
	 * @param sequence
	 */
	public void setTitle(CharSequence sequence) {
		tvTitle.setText(sequence);
		tvTitleLeft.setText(sequence);
	}

	/**
	 * 设置标题
	 * 
	 * @param resId
	 */
	public void setTitle(int resId) {
		setTitle(getResources().getString(resId));
	}

	/**
	 * 设置item的点击背景
	 * 
	 * @param resId
	 */
	public void setItemBg(int resId) {
		this.itemBg = resId;

		actionLeft.setBackgroundResource(resId);
		tvTitle.setBackgroundResource(resId);
		tvTitleLeft.setBackgroundResource(resId);

		for (int i = 0; i < lytRight.getChildCount(); i++) {
			lytRight.getChildAt(i).setBackgroundResource(resId);
		}
	}

	/**
	 * 设置标题右侧显示时的图标
	 * 
	 * @param resId
	 */
	public void setLeftLogo(int resId) {
		tvTitleLeft.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
	}

	/**
	 * 是否显示左侧按钮
	 * 
	 * @param enable
	 */
	public void displayLeftAction(boolean enable) {
		actionLeft.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		if (actionBarItemClickListener == null)
			return;

		if (v.getId() == R.id.actionLeft || v.getId() == R.id.tvTitleLeft) {// 左侧点击事件
			actionBarItemClickListener
					.onActionBarItemClick(ActionBarClickItem.LEFT);
		} else if (v.getId() == R.id.actionbarButton) {// 右侧点击事件
			PopupMenu popupMenu = (PopupMenu) v
					.getTag(R.id.actionbarTagPopMenu);

			if (popupMenu != null)// 如果有menu则弹出
				popupMenu.show();
			else if (v.getTag(R.id.actionbarTagPostion) != null)
				actionBarItemClickListener.onActionBarItemClick((Integer) v
						.getTag(R.id.actionbarTagPostion));
		} else if (v.getId() == R.id.tvTitle) {
			actionBarItemClickListener
					.onActionBarItemClick(ActionBarClickItem.TITLE);
		}
	}

	/**
	 * 设置右侧相关位置的text
	 * 
	 * @param text
	 * @param position
	 */
	public void setActionTitle(String text, int position) {
		ActionBarButton actionBarButton = (ActionBarButton) lytRight
				.getChildAt(position);
		if (actionBarButton != null) {
			actionBarButton.getTextView().setText(text);
		}
	}

	/**
	 * 设置右侧action的name
	 * 
	 * @param text
	 */
	public void setActionTitle(String text) {
		setActionTitle(text, 0);
	}

	/**
	 * 设置menu资源文件
	 * 
	 * @param position
	 *            对应的弹出位置
	 * @param resId
	 * @param menuItemClickListener
	 */
	public void setItemMenu(int position, int resId,
			OnMenuItemClickListener menuItemClickListener) {
		View v = lytRight.getChildAt(position);
		PopupMenu popupMenu = new PopupMenu(getContext(), v);

		try {
			Field[] fields = popupMenu.getClass().getDeclaredFields();
			for (Field field : fields) {
				if ("mPopup".equals(field.getName())) {
					field.setAccessible(true);
					Object menuPopupHelper = field.get(popupMenu);
					Class<?> classPopupHelper = Class.forName(menuPopupHelper
							.getClass().getName());
					Method setForceIcons = classPopupHelper.getMethod(
							"setForceShowIcon", boolean.class);
					setForceIcons.invoke(menuPopupHelper, true);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		popupMenu.setOnMenuItemClickListener(menuItemClickListener);
		popupMenu.getMenuInflater().inflate(resId, popupMenu.getMenu());
		v.setTag(R.id.actionbarTagPopMenu, popupMenu);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if(actionLeft == null || lytRight == null)
			return;
		
		if (actionLeft.getMeasuredWidth() > lytRight.getMeasuredWidth())
			tvTitle.setPadding(0, 0, actionLeft.getMeasuredWidth(), 0);
		else if (actionLeft.getMeasuredWidth() < lytRight.getMeasuredWidth()) {
			tvTitle.setPadding(
					lytRight.getMeasuredWidth() - actionLeft.getMeasuredWidth(),
					0, 0, 0);
		} else {
			tvTitle.setPadding(0, 0, 0, 0);
		}
	}

	/**
	 * 新增一个actionItem
	 * 
	 * @param drawable
	 * @param text
	 * @param showAsImage
	 */
	public void addActionItem(Drawable drawable, String text,
			boolean showAsImage) {
		ActionBarButton actionBarButton = new ActionBarButton(getContext());
		actionBarButton.setCenterImg(drawable);
		actionBarButton.setShowImg(showAsImage);
		actionBarButton.setBackgroundResource(itemBg);
		actionBarButton.getTextView().setText(text);
		actionBarButton.setOnClickListener(this);
		actionBarButton.getTextView().setTextColor(barTextColor);
		actionBarButton.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_SP,
				barTextSize);
		if (showAsImage)
			lytRight.addView(actionBarButton, new LayoutParams(itemWidth,
					LayoutParams.MATCH_PARENT));
		else
			lytRight.addView(actionBarButton, new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		actionBarButton.setTag(R.id.actionbarTagPostion,
				lytRight.getChildCount() - 1);
	}

	/**
	 * 新增一个actionItem
	 * 
	 * @param resId
	 * @param text
	 * @param showAsImage
	 */
	public void addActionItem(int resId, String text, boolean showAsImage) {
		this.addActionItem(getResources().getDrawable(resId), text, showAsImage);
	}

	/**
	 * 以文本的方式,显示一个action
	 * 
	 * @param text
	 */
	public void addActionItem(String text) {
		this.addActionItem(null, text, false);
	}

	/**
	 * 更新某一位置的action的text
	 * 
	 * @param text
	 * @param position
	 */
	public void updateActionItemText(String text, int position) {
		if (position >= lytRight.getChildCount())
			return;

		ActionBarButton actionBarButton = (ActionBarButton) lytRight
				.getChildAt(position);
		actionBarButton.getTextView().setText(text);
	}

	/**
	 * 获取对应的actionview
	 * 
	 * @param position
	 * @return
	 */
	public View getActionView(int position) {
		return lytRight.getChildAt(position);
	}

	/**
	 * 获取右侧action 数
	 * 
	 * @return
	 */
	public int getActionCount() {
		return lytRight.getChildCount();
	}

	/**
	 * 是否显示右侧actions
	 * 
	 * @param enabled
	 */
	public void displayActions(boolean enabled) {
		if (enabled)
			lytRight.setVisibility(View.VISIBLE);
		else
			lytRight.setVisibility(View.INVISIBLE);
	}

	/**
	 * 新增自定义的action view
	 * 
	 * @param v
	 */
	public void addActionView(View v, LayoutParams layoutParams) {
		if (v.getId() < 0)
			v.setId(R.id.actionbarButton);

		v.setOnClickListener(this);
		lytRight.addView(v, layoutParams);
		v.setTag(R.id.actionbarTagPostion, lytRight.getChildCount() - 1);
	}
}

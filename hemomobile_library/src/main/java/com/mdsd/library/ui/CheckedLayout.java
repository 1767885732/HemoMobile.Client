package com.mdsd.library.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
/**
 * 
 * <p>com.mdsd.library.ui.CheckedLayout</p>
 * <p>可以进行选择与否的layout</p>
 *
 */
public class CheckedLayout extends LinearLayout implements Checkable {
	private boolean mChecked;

	private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };

	public CheckedLayout(Context context) {
		this(context, null);
	}

	public CheckedLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CheckedLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void toggle() {
		setChecked(!mChecked);
	}

	@ViewDebug.ExportedProperty
	public boolean isChecked() {
		return mChecked;
	}

	/**
	 * <p>
	 * Changes the checked state of this text view.
	 * </p>
	 * 
	 * @param checked
	 *            true to check the text, false to uncheck it
	 */
	public void setChecked(boolean checked) {
		if (mChecked != checked) {
			mChecked = checked;
			refreshDrawableState();
		}
	}

	@Override
	public void onRtlPropertiesChanged(int layoutDirection) {
		super.onRtlPropertiesChanged(layoutDirection);
	}

	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
		final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
		if (isChecked()) {
			mergeDrawableStates(drawableState, CHECKED_STATE_SET);
		}
		return drawableState;
	}

	@Override
	public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
		super.onInitializeAccessibilityEvent(event);
		event.setClassName(CheckedTextView.class.getName());
		event.setChecked(mChecked);
	}

	@Override
	public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
		super.onInitializeAccessibilityNodeInfo(info);
		info.setClassName(CheckedTextView.class.getName());
		info.setCheckable(true);
		info.setChecked(mChecked);
	}

}

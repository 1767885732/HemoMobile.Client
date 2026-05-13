package com.mdsd.library.ui.autoBgButton;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Applies a pressed state color filter or disabled state alpha for the button's
 * background drawable.
 * 
 * @author lijianyu
 */
public class AutoBgButton extends Button {

	public AutoBgButton(Context context) {
		super(context);
	}

	public AutoBgButton(Context context, AttributeSet attrs) {
		super(context, attrs,android.R.attr.buttonStyle);
	}

	public AutoBgButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBackgroundDrawable(Drawable background) {
		if (background == null)
			return;

		super.setBackgroundDrawable(new AutoBgBackgroundDrawable(background));
	}
}

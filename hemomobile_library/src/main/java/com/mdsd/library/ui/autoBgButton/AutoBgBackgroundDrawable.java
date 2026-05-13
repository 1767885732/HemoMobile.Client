package com.mdsd.library.ui.autoBgButton;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 自动过滤点击背景
 * 
 * <br>
 * <br>
 * If you want to change it,<br>
 * please follow the "user updated in time" added to "version" behind and use
 * the "," to separate。<br>
 * such as "xx Created xx, xx Updated xx, ..."
 * 
 * @author lijianyu
 * @version lijianyu Created in 2013年12月23日 上午11:17:00
 */
public class AutoBgBackgroundDrawable extends StateListDrawable {

	// The color filter to apply when the button is pressed
	protected ColorFilter _pressedFilter = new LightingColorFilter(
			Color.LTGRAY, 1);

	// Alpha value when the button is disabled
	protected int _disabledAlpha = 100;
	// Alpha value when the button is enabled
	protected int _fullAlpha = 255;
	
	public AutoBgBackgroundDrawable(Drawable d) {
		super();
		
		
		if(d instanceof ColorDrawable){
			GradientDrawable gradientDrawable = new GradientDrawable();
			gradientDrawable.setColor(((ColorDrawable) d).getColor());
			addState(new int[]{}, gradientDrawable);
		}else if(!(d instanceof StateListDrawable))
			addState(new int[]{}, d);
		
	}

	
	
	@Override
	protected boolean onStateChange(int[] states) {
		boolean enabled = false;
		boolean pressed = false;

		for (int state : states) {
			if (state == android.R.attr.state_enabled)
				enabled = true;
			else if (state == android.R.attr.state_pressed)
				pressed = true;
		}

		mutate();
		if (enabled && pressed) {
			setColorFilter(_pressedFilter);
		} else if (!enabled) {
			setColorFilter(null);
			setAlpha(_disabledAlpha);
		} else {
			setColorFilter(null);
			setAlpha(_fullAlpha);
		}

		invalidateSelf();

		return super.onStateChange(states);
	}

	@Override
	public boolean isStateful() {
		return true;
	}

}

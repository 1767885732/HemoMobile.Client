/**
 * Copyright (C) 2014 Jianyu.L
 * 
 * @version:v1.0.0 
 * @author:Jianyu.L (lijianyu2012@gmail.com)
 * 
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2014年11月28日     Jianyu.L      v1.0.0        create
 *
 *
 */
package com.mdsd.library.ui.pullRefreshLayout;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

/**
 * <p>com.mdsd.library.ui.pullRefreshLayout.RefreshDrawable</p>
 * <p>TODO</p>
 * 
 */
public abstract class RefreshDrawable extends Drawable implements Drawable.Callback, Animatable{

	private PullRefreshLayout mRefreshLayout;

    public RefreshDrawable(Context context, PullRefreshLayout layout) {
        mRefreshLayout = layout;
    }

    public Context getContext(){
        return mRefreshLayout != null ? mRefreshLayout.getContext() : null;
    }

    public PullRefreshLayout getRefreshLayout(){
        return mRefreshLayout;
    }

    public abstract void setPercent(float percent);
    public abstract void setColorSchemeColors(int[] colorSchemeColors);

    public abstract void offsetTopAndBottom(int offset);

    @Override
    public void invalidateDrawable(Drawable who) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

}

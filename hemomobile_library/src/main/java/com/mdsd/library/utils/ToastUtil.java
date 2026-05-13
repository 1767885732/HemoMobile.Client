package com.mdsd.library.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * 短暂的信息提示工具类
 * 
 * @author jianyu.l
 * @since 2014年5月30日
 */
public class ToastUtil {

	public static void show(Context context, int resId) {
		show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
	}
	
	public static void showLong(Context context, CharSequence text) {
		show(context, text, Toast.LENGTH_LONG);
	}
	
	public static void showLong(Context context, int resId) {
		show(context, context.getResources().getText(resId), Toast.LENGTH_LONG);
	}

	public static void show(Context context, int resId, int duration) {
		show(context, context.getResources().getText(resId), duration);
	}

	public static void show(Context context, CharSequence text) {
		show(context, text, Toast.LENGTH_SHORT);
	}

	public static void show(Context context, CharSequence text, int duration) {
		if (judgeContext(context))
			Toast.makeText(context.getApplicationContext(), text, duration).show();
	}

	public static void show(Context context, int resId, Object... args) {
		show(context,
				String.format(context.getResources().getString(resId), args),
				Toast.LENGTH_SHORT);
	}

	public static void show(Context context, String format, Object... args) {
		show(context, String.format(format, args), Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int resId, int duration,
			Object... args) {
		show(context,
				String.format(context.getResources().getString(resId), args),
				duration);
	}

	public static void show(Context context, String format, int duration,
			Object... args) {
		show(context, String.format(format, args), duration);
	}

	private static boolean judgeContext(Context context) {
		if (context == null) {
			Log.d("ToastUtil", "context is null in judgeContext");
			return false;
		}

		if (context instanceof Activity && ((Activity) context).isFinishing()) {
			Log.d("ToastUtil", "Activity is isFinished in judgeContext");
			return false;
		}

		return true;
	}
}

/**
 * Copyright (C) 2014 Jianyu.L
 * 
 * @version:v1.0.0 
 * @author:Jianyu.L (lijianyu2012@gmail.com)
 * 
 * Modification History:
 *    Date          Author       Version     Description
 * -----------------------------------------------------------------
 * 2014年12月16日       Jianyu.L      v1.0.0        create
 *
 *
 */
package com.mdsd.library.utils;

import java.util.List;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * 
 */
public class ViewUtil {

	public static int dp2px(Context context,int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}
	
	/**
	 * 构造spi
	 * 
	 * @param context
	 * @param spinner
	 * @param type
	 * @param list
	 */
	public static <T> void buildSpi(Context context, Spinner spinner,
			List<T> list) {
		ArrayAdapter<T> arrayAdapter = new ArrayAdapter<T>(context,
				android.R.layout.simple_spinner_item, list);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(arrayAdapter);
	}

	/**
	 * 构造spi
	 * 
	 * @param context
	 * @param spinner
	 * @param type
	 * @param list
	 */
	public static <T> void buildSpi(Context context, Spinner spinner,
			String[] list) {
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, list);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(arrayAdapter);
	}
}

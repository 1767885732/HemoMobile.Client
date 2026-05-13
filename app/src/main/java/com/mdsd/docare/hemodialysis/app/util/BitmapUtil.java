package com.mdsd.docare.hemodialysis.app.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


/**
 * Android图片二进制与Bitmap、Drawable之间的转换
 * 
 * <br>
 * 
 * @author jun.w
 * @since 2016年7月19日
 */
public class BitmapUtil {
	
	/**
	 * Bitmap转化为二进制流
	 */
	public static byte[] getBitmapByte(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * 二进制流转化为Bitmap
	 */
	public static Bitmap getBitmapFromByte(byte[] temp) {
		if (temp != null) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
			return bitmap;
		} else {
			return null;
		}
	}

	/**
	 * Drawable转化为Bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;

	}

	/**
	 * Bitmap to Drawable
	 * 
	 * @param bitmap
	 * @param mcontext
	 * @return
	 */
	public static Drawable bitmapToDrawble(Bitmap bitmap, Context mcontext) {
		Drawable drawable = new BitmapDrawable(mcontext.getResources(), bitmap);
		return drawable;
	}

}

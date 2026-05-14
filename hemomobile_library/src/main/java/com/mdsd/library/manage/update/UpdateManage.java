package com.mdsd.library.manage.update;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

import com.alibaba.fastjson.JSONObject;

/**
 * apk更新管理
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月13日
 */
public class UpdateManage {

	/**
	 * 检查更新的url <br>
	 * 服务端应返回对应的json字符串({"apkUrl":"","title":"","content":"","type":"",
	 * "versionCode":""})
	 * 
	 * <pre>
	 * apkUrl:apk下载的地址；
	 * title:标题；
	 * content:更新内容；
	 * type:更新类型；
	 * versionCode:版本号;
	 * 
	 * <pre>
	 * @param url
	 * @param context
	 */
	public static void checkForUpdates(String url, final Context context,final String apkName) {
		CheckUpdateTask checkUpdateTask = new CheckUpdateTask() {
			@Override
			protected void onPostExecute(String result) {
				if (result == null || "".equals(result))
					return;

				try {
					// 转换json
					// json
					// 内容{"apkUrl":"","title":"","content":"","type":"","versionCode":""}
					result = result.substring(result.indexOf("\"") + 1,
							result.lastIndexOf("\""));
					result = result.replace("\\", "");
					JSONObject jsonObject = JSONObject.parseObject(result);
					int versionCode = context.getPackageManager()
							.getPackageInfo(context.getPackageName(), 0).versionCode;
					int newVersionCode = jsonObject.getIntValue("versionCode");

					if (newVersionCode > versionCode) {
						showDialog(context, jsonObject.getString("content"),
								jsonObject.getString("apkUrl"),
								jsonObject.getString("title"),apkName);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		checkUpdateTask.execute(url);
	}

	private static void showDialog(final Context context, String content,
			final String apkUrl, String title,final String apkName) {

		OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case AlertDialog.BUTTON_POSITIVE:// 更新
					downloadApk(context, apkUrl,apkName);
					break;
				default:
					dialog.dismiss();
					break;
				}

			}
		};

		AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.setTitle(title);
		dialog.setMessage(content);
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "更新", onClickListener);
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "以后再说", onClickListener);
		dialog.show();
	}

	private static void showConfirmDialog(final Context context,
			final String apkUrl,final String apkName) {
		OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case AlertDialog.BUTTON_POSITIVE:// 更新
					downloadApk(context, apkUrl,apkName);
					break;
				default:
					dialog.dismiss();
					break;
				}

			}
		};

		AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.setTitle("提示");
		dialog.setMessage("当前不是wifi环境，建议您wifi环境下进行更新。");
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "继续更新", onClickListener);
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "以后再说", onClickListener);
		dialog.show();
	}

	public static void downloadApk(Context context, String apkUrl, String apkName) {
		try {
			Log.d("UpdateManage", "downloadApk 被调用");
			Log.d("UpdateManage", "apkUrl: " + apkUrl);
			Log.d("UpdateManage", "apkName: " + apkName);
			
			Intent intent = new Intent(context.getApplicationContext(),
					DownloadApkService.class);
			intent.putExtra(DownloadApkService.EXTRA_KEY_APK_URL, apkUrl);
			intent.putExtra(DownloadApkService.EXTRA_KEY_APK_NAME, apkName);
			context.startService(intent);
			
			Log.d("UpdateManage", "DownloadApkService 已启动");
		} catch (Exception e) {
			Log.e("UpdateManage", "downloadApk 异常: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// 检查是否wifi连接
	private static boolean isWifiConnect(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();

		if (wifi == State.CONNECTED || wifi == State.CONNECTING)
			return true;

		return false;
	}
}

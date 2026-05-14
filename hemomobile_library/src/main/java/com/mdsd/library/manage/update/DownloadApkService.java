package com.mdsd.library.manage.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.BuildConfig;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.FileProvider;
import android.util.Log;

public class DownloadApkService extends IntentService {

	public static final String EXTRA_KEY_APK_URL = "extra_key_apk_url";
	public static final String EXTRA_KEY_APK_NAME = "extra_key_apk_name";

	public DownloadApkService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	private static final int BUFFER_SIZE = 10 * 1024; // 8k ~ 32K
	private static final String TAG = "DownloadService";
	private NotificationManager mNotifyManager;
	private Builder mBuilder;

	public DownloadApkService() {
		this("DownloadApkService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("DownloadService", "onHandleIntent 被调用");
		
		mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(this);

		String appName = getString(getApplicationInfo().labelRes);
		int icon = getApplicationInfo().icon;

		mBuilder.setContentTitle(appName).setSmallIcon(icon);
		String urlStr = intent.getStringExtra(EXTRA_KEY_APK_URL);
		String apkName = intent.getStringExtra(EXTRA_KEY_APK_NAME);
		
		Log.d("DownloadService", "urlStr: " + urlStr);
		Log.d("DownloadService", "apkName: " + apkName);

		InputStream in = null;
		FileOutputStream out = null;
		try {
			Log.d("DownloadService", "开始下载...");
			URL url = new URL(urlStr);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(false);
			urlConnection.setConnectTimeout(10 * 1000);
			urlConnection.setReadTimeout(10 * 1000);
			urlConnection.setRequestProperty("Connection", "Keep-Alive");
			urlConnection.setRequestProperty("Charset", "UTF-8");
			urlConnection
					.setRequestProperty("Accept-Encoding", "gzip, deflate");

			urlConnection.connect();
			long bytetotal = urlConnection.getContentLength();
			long bytesum = 0;
			int byteread = 0;
			in = urlConnection.getInputStream();
			
			File filesDir = getFilesDir();
			File apkDir = new File(filesDir, "apk");
			if(!apkDir.exists())
			{
				apkDir.mkdirs();
			}
			Log.d("DownloadService", "下载目录: " + apkDir.getAbsolutePath());
			
			File apkFile = new File(apkDir.getAbsolutePath(), apkName + ".apk");
			Log.d("DownloadService", "APK 文件路径: " + apkFile.getAbsolutePath());
			if(apkFile.exists())
			{
				apkFile.delete();
			}
			
			out = new FileOutputStream(apkFile);
			byte[] buffer = new byte[BUFFER_SIZE];

			int oldProgress = 0;

			while ((byteread = in.read(buffer)) != -1) {
				bytesum += byteread;
				out.write(buffer, 0, byteread);

				int progress = (int) (bytesum * 100L / bytetotal);
				// 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿
				if (progress != oldProgress) {
					updateProgress(progress);
				}
				oldProgress = progress;
			}
			// 下载完成
			Log.d("DownloadService", "下载完成，开始安装 APK");
			mNotifyManager.cancel(0);
			
			try {
				Intent installAPKIntent = new Intent(Intent.ACTION_VIEW);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
					Log.d("DownloadService", "Android 7.0+，使用 FileProvider");
					Uri contentUri = FileProvider.getUriForFile(this, "com.mdsd.docare.hemodialysis.app.fileprovider", apkFile);
					Log.d("DownloadService", "FileProvider URI: " + contentUri);
					installAPKIntent.setDataAndType(contentUri, "application/vnd.android.package-archive");
					installAPKIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					installAPKIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				} else {
					Log.d("DownloadService", "Android 6.0 以下，直接使用文件路径");
					installAPKIntent.setDataAndType(Uri.fromFile(apkFile),
							"application/vnd.android.package-archive");
					installAPKIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				}
				Log.d("DownloadService", "启动安装界面");
				Log.d("DownloadService", "resolveActivity: " + getPackageManager().resolveActivity(installAPKIntent, 0));
				startActivity(installAPKIntent);
				Log.d("DownloadService", "startActivity 已调用");
			} catch (Exception e) {
				Log.e("DownloadService", "启动安装界面失败: " + e.getMessage(), e);
			}

		} catch (Exception e) {
			Log.e(TAG, "download apk file error", e);
			mNotifyManager.cancel(0);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateProgress(int progress) {
		// "正在下载:" + progress + "%"
		mBuilder.setContentText(String.format("正在下载:%1$d%%", progress))
				.setProgress(100, progress, false);
		// setContentInent如果不设置在4.0+上没有问题，在4.0以下会报异常
		PendingIntent pendingintent = PendingIntent.getActivity(this, 0,
				new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
		mBuilder.setContentIntent(pendingintent);
		mNotifyManager.notify(0, mBuilder.build());

	}

}

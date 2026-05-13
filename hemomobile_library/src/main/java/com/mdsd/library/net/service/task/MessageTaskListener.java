package com.mdsd.library.net.service.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

/**
 * 弹出消息的tasklistener 继承至{@link TaskListener} <br>
 * 提示：默认情况，弹出框可以取消，取消弹出框则取消当前的任务
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月12日
 */
public class MessageTaskListener<Params, Progress, Result> extends
		TaskListener<Params, Progress, Result> {

	private static final String DEF_PROGRESS_MESSAGE = "数据处理中...";

	private ProgressDialog dialog;
	private boolean cancelEnable = true;

	public MessageTaskListener(Context context) {
		this(context, DEF_PROGRESS_MESSAGE);
	}

	public MessageTaskListener(Context context, String message) {
		this(context, message, true);
	}

	public MessageTaskListener(Context context, String message,
			boolean cancelEnable) {
		dialog = new ProgressDialog(context);
		dialog.setMessage(message);
		this.cancelEnable = cancelEnable;
	}

	public MessageTaskListener(Context context, boolean cancelEnable) {
		dialog = new ProgressDialog(context);
		dialog.setMessage(DEF_PROGRESS_MESSAGE);
		this.cancelEnable = cancelEnable;
	}

	@Override
	public void onPreExecute() {
		super.onPreExecute();

		if (dialog == null)
			return;

		dialog.show();
		dialog.setCancelable(cancelEnable);
		dialog.setCanceledOnTouchOutside(cancelEnable);
		dialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				if (getTask() instanceof HttpTask) {
					((HttpTask<?>) getTask()).httpClient
							.cancel(((HttpTask<?>) getTask()).requestId);
				}

				getTask().cancel(true);
			}
		});

	}

	@Override
	public void onPostExecute(Result result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if (dialog == null)
			return;

		dialog.dismiss();
		dialog = null;
	}
}

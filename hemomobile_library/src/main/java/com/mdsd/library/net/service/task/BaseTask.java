package com.mdsd.library.net.service.task;

import android.os.AsyncTask;

/**
 * 所有任务的基类
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月12日
 */
public abstract class BaseTask<Params, Progress, Result> extends
		AsyncTask<Params, Progress, Result> {
	protected String tag = getClass().getSimpleName();

	private TaskListener<Params, Progress, Result> taskListener = null;

	public BaseTask(TaskListener<Params, Progress, Result> taskListener) {
		this.taskListener = taskListener;
		this.taskListener.setTask(this);
	}

	@Override
	protected void onPreExecute() {
		this.taskListener.onPreExecute();
	}

	@Override
	protected void onPostExecute(Result result) {
		this.taskListener.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(
			@SuppressWarnings("unchecked") Progress... values) {
		this.taskListener.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		this.taskListener.onCancelled();
	}

}

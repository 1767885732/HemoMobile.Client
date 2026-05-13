package com.mdsd.library.net.service.task;

/**
 * task监听
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月12日
 */
public interface ITaskListener<Params, Progress, Result> {

	public void onPostExecute(Result result);

	public void onPreExecute();

	public void onProgressUpdate(
			@SuppressWarnings("unchecked") Progress... values);

	public void onCancelled();

	public void setTask(BaseTask<Params, Progress, Result> task);

	public BaseTask<Params, Progress, Result> getTask();
}
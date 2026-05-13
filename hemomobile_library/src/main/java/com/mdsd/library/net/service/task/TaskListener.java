package com.mdsd.library.net.service.task;

/**
 * @author jianyu.l
 * @since 2014年6月12日
 */
public abstract class TaskListener<Params, Progress, Result> implements
		ITaskListener<Params, Progress, Result> {

	BaseTask<Params, Progress, Result> task;

	@Override
	public void onPostExecute(Result result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProgressUpdate(
			@SuppressWarnings("unchecked") Progress... values) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCancelled() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTask(BaseTask<Params, Progress, Result> task) {
		this.task = task;

	}

	@Override
	public BaseTask<Params, Progress, Result> getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}

}

package com.mdsd.library.manage.single;

import java.util.Stack;

import android.app.Activity;
import android.os.Handler;

/**
 * activity管理类<br>
 * 请在activity的oncreate方法中调用ActivityManageSingle.getInstance().addActivity(this)
 * 方法;<br>
 * 每一个activity中的ondestroy方法中调用ActivityManageSingle.getInstance().finishActivity(
 * this);<br>
 * <br>
 * <b>（提示：建议建一个基类，操作可直接在基类执行）
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月9日
 */
public class ActivityManageSingle {

	private Stack<Activity> activityStack = null;

	private static class ActivityManageSingleHolder {
		static ActivityManageSingle instance = new ActivityManageSingle();
	}

	public static ActivityManageSingle getInstance() {
		return ActivityManageSingleHolder.instance;
	}

	/**
	 * 获取栈中大小
	 * 
	 * @return
	 */
	public int getStackSize() {
		return activityStack == null ? 0 : activityStack.size();
	}

	/**
	 * 添加activity到容器中
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}

		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void appExit() {
		try {
			finishAllActivity();

			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					System.exit(0);
				}
			}, 1000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

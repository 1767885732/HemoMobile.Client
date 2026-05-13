package com.mdsd.library.manage.single;

import android.content.Context;
import android.content.Intent;

/**
 * 
 * 用户登录管理
 * 
 * <br>
 * <br>
 * If you want to change it,<br>
 * please follow the "user updated in time" added to "version" behind and use
 * the "," to separate。<br>
 * such as "xx Created xx, xx Updated xx, ..."
 * 
 * @author lijianyu
 * @version lijianyu Created in 2013年12月12日 下午1:08:02
 */
public class LoginManage {

	OnLoginListener onLoginListener = null;
	private boolean isLogin = false;

	private static class LoginManageHolder {
		static LoginManage instance = new LoginManage();
	}

	public static LoginManage getInstance() {
		return LoginManageHolder.instance;
	}
	
	
	public enum LoginType {
		/**
		 * 登录/注销成功
		 */
		SUCCESS,

		/**
		 * 登录/注销取消
		 */
		CANCEL,

		/**
		 * 已经是登录状态/已是注销状态
		 */
		ALREADY,

	}
	
	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * Gets the on login listener.
	 * 
	 * @return the on login listener
	 */
	public OnLoginListener getOnLoginListener() {
		return onLoginListener;
	}

	/**
	 * Sets the on login listener.
	 * 
	 * @param onLoginListener
	 *            the new on login listener
	 */
	public void setOnLoginListener(OnLoginListener onLoginListener) {
		this.onLoginListener = onLoginListener;
	}


	/**
	 * 登陆请求
	 * 
	 * @param onLoginListener
	 * @param requestCode
	 */
	public void onLogin(Context context, OnLoginListener onLoginListener, Class<?> cls) {

		this.onLoginListener = onLoginListener;

		// 判断是否是登录状态
		if (!isLogin) {
			// 未登录，跳转到登录界面
			context.startActivity(new Intent(context, cls));

		} else if (this.onLoginListener != null) {
			this.onLoginListener.onLoginResult(LoginType.ALREADY);
			this.onLoginListener = null;
		}
	}

	/**
	 * 注销登录
	 * 
	 * @param context
	 * @param onLoginListener
	 * @param requestCode
	 */
	public void onLogOut(Context context, OnLoginListener onLoginListener) {
		this.onLoginListener = onLoginListener;

		// TODO

		if (this.onLoginListener != null) {
			this.onLoginListener.onLogOutResult(LoginType.SUCCESS);
			this.onLoginListener = null;
		}
	}

	// *****************
	// interface
	// *****************
	public interface OnLoginListener {

		/**
		 * 登录结果监听
		 * 
		 * @param loginType
		 * @param requestCode
		 *            自定义code
		 */
		void onLoginResult(LoginType loginType);

		/**
		 * 注销结果监听
		 * 
		 * @param loginType
		 * @param requestCode
		 *            自定义code
		 */
		void onLogOutResult(LoginType loginType);
	}

}

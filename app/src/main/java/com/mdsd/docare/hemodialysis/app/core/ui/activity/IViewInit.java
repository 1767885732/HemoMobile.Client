package com.mdsd.docare.hemodialysis.app.core.ui.activity;

public interface IViewInit {
	/**
	 * view加载之前
	 */
	void beforeInitView();

	/**
	 * 获取控件
	 */
	void findViews();

	/**
	 * 设置监听
	 */
	void setListeners();

	/**
	 * 为页面显示初始化数据
	 */
	void setViewData();
}

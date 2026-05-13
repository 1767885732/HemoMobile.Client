package com.mdsd.docare.hemodialysis.app.core.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.mdsd.library.manage.single.ActivityManageSingle;

public class BaseFragmentActivity extends FragmentActivity implements IViewInit {

	/**
	 * 当前类的名称，可供打印调试的tag
	 */
	protected final String TAG = getClass().getName();

	/**
	 * 是否显示返回按钮
	 */
	private boolean barBackVisible = false;

	/**
	 * On create.
	 * 
	 * @param savedInstanceState
	 *            the saved instance state
	 * @param layoutResID
	 *            the layout res id
	 */
	protected void onCreate(Bundle savedInstanceState, int layoutResID) {
		this.onCreate(savedInstanceState, layoutResID, false);
	}

	/**
	 * On create.
	 * 
	 * @param savedInstanceState
	 *            the saved instance state
	 * @param layoutResID
	 *            the layout res id
	 * @param backEnable
	 *            the back enable
	 */
	protected void onCreate(Bundle savedInstanceState, int layoutResID,
			boolean barBackVisible) {
		this.barBackVisible = barBackVisible;
		beforeInitView();
		super.onCreate(savedInstanceState);
		setContentView(layoutResID);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initView();
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);

	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initView();
	}

	@Override
	public void findViews() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setViewData() {
		// TODO Auto-generated method stub

	}

	/**
	 * 初始化view
	 */
	private void initView() {
		findViews();
		setListeners();
		setViewData();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		ActivityManageSingle.getInstance().finishActivity(this);
		super.onDestroy();
	}

	@Override
	public void beforeInitView() {
		// 不显示home图标
		if (getActionBar() != null)
			getActionBar().setDisplayShowHomeEnabled(false);

		// 设置默认的返回图标
		if (barBackVisible) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		// 加入当前的activity到ActivityManage管理栈内
		ActivityManageSingle.getInstance().addActivity(this);

	}
}

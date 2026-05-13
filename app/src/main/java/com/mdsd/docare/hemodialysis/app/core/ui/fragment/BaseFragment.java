package com.mdsd.docare.hemodialysis.app.core.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mdsd.docare.hemodialysis.app.core.ui.activity.IViewInit;

public abstract class BaseFragment extends Fragment implements FragmentSelectedListener,
		IViewInit {

	public String tag = this.getClass().getSimpleName();

	// view
	private View mView;
	protected boolean isFirstLoad = true;// 是否第一次加载

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return null;
	}

	/**
	 * 初始化视图
	 *
	 * @param view
	 * @return
	 */
	public View initView(View view) {
		if (mView == null) {
			beforeInitView();
			mView = view;
			findViews();
			setListeners();
			setViewData();
		} else if (mView.getParent() != null) {
			((ViewGroup) mView.getParent()).removeView(mView);
		}

		return mView;
	}

	/**
	 * 初始化视图
	 *
	 * @param inflater
	 * @param container
	 * @param resource
	 * @return
	 */
	public View initView(LayoutInflater inflater, ViewGroup container,
			int resource) {
		if (mView == null) {
			mView = inflater.inflate(resource, container, false);
			findViews();
			setListeners();
			setViewData();
		} else if (mView.getParent() != null) {
			((ViewGroup) mView.getParent()).removeView(mView);
		}

		return mView;
	}


	@Override
	public View getView() {
		return mView;
	}
	
	@Override
	public void onSelected() {
		if (isFirstLoad)
			isFirstLoad = false;// 第一次选择后设为false;

	}

	@Override
	public void beforeInitView() {
		// TODO Auto-generated method stub

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
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		beforeInitView();
	}
	
	/**
	 * 子childfragment的调用
	 */
	public void onChildFragmentCall(){};

}

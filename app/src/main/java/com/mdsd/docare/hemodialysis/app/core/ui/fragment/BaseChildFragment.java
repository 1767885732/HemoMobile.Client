package com.mdsd.docare.hemodialysis.app.core.ui.fragment;

import com.mdsd.docare.hemodialysis.app.core.ui.activity.IViewInit;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseChildFragment extends Fragment implements IViewInit{

	
	public String tag = this.getClass().getSimpleName();

	// view
	private View mView;
	
	
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
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		beforeInitView();
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
	
	public View getCurrentView(){
		return mView;
	}
	
	/**
	 * 为同一个对象进行赋值操作
	 *
	 * @param t
	 */
	public abstract void setCommonObjectValue(Object t);
	
	/**
	 * 根据view改变同一个对象
	 *
	 * @param t
	 */
	public abstract void changeObjectByView(Object t);

}

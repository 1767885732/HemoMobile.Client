package com.mdsd.docare.hemodialysis.app.core.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BaseFragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragmentList = null;
	private List<String> titleList = null;

	public BaseFragmentAdapter(FragmentManager fm) {
		super(fm);
		fragmentList = new ArrayList<Fragment>();
		titleList = new ArrayList<String>();
	}

	public BaseFragmentAdapter(FragmentManager fm,
			List<Fragment> fragmentList, List<String> titleArray) {
		super(fm);

		addTitleList(titleArray);
		addFragmentList(fragmentList);

	}

	public void addFragmentList(List<? extends Fragment> fragmentList) {
		
		if(this.fragmentList == null)
			this.fragmentList = new ArrayList<Fragment>();
		
		this.fragmentList.addAll(fragmentList);
		notifyDataSetChanged();
	}
	
	public void addTitleList(List<String> titleArray) {
		if(this.titleList == null)
			this.titleList = new ArrayList<String>();
		
		this.titleList.addAll(titleArray);
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return fragmentList.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragmentList.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		try {
			return titleList.get(position);
		} catch (Exception e) {
			return "";
		}
	}

	
	
}

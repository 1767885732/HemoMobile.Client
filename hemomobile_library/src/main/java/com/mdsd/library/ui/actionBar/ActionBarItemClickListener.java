package com.mdsd.library.ui.actionBar;

import com.mdsd.library.ui.actionBar.ActionBar.ActionBarClickItem;



public interface ActionBarItemClickListener {
	void onActionBarItemClick(ActionBarClickItem clickItem);
	void onActionBarItemClick(int position);
}

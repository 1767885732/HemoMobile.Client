package com.mdsd.library.ui.listView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mdsd.library.ui.easyadapter.EasyAdapter;
import com.mdsd.library.ui.easyadapter.EasyAdapter.OnChangeListener;

/**
 * 到达底部自动加载更多的Listview
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年6月25日
 */
public class LoadMoreListView extends ListView implements OnScrollListener,
		OnChangeListener {
	private static final String TAG = "LoadMoreListView";

	private LinearLayout footView = null;
	private ProgressBar mProgressBarLoadMore;

	private OnScrollListener mOnScrollListener = null;

	private boolean mIsLoadingMore = false;
	private int mCurrentScrollState;

	/**
	 * 当前页数
	 */
	private int page = 1;
	/**
	 * 每页的数据量
	 */
	private int pageSize = 10;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	private OnLoadListener onLoadListener;

	public void setOnLoadListener(OnLoadListener onLoadListener) {
		this.onLoadListener = onLoadListener;
	}

	public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadMoreListView(Context context) {
		super(context);
		init();
	}

	protected void init() {

		// 初始化footView
		footView = new LinearLayout(getContext());
		footView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		footView.setGravity(Gravity.CENTER);

		// 初始化progressbar
		mProgressBarLoadMore = new ProgressBar(getContext());
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				30, getResources().getDisplayMetrics());
		footView.addView(mProgressBarLoadMore, size, size);
		addFooterView(footView);
		// 设置滚动监听
		setOnScrollListener(this);
		if (getAdapter() instanceof EasyAdapter<?>) {
			((EasyAdapter<?>) getAdapter()).setOnChangeListener(this);
		}

	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		if (!l.getClass().equals(LoadMoreListView.class))
			this.mOnScrollListener = l;

		super.setOnScrollListener(l);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		// if ( scrollState == OnScrollListener.SCROLL_STATE_IDLE )
		// {
		// view.invalidateViews();
		// }

		mCurrentScrollState = scrollState;

		if (mOnScrollListener != null) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if (adapter instanceof EasyAdapter<?>) {
			((EasyAdapter<?>) adapter).setOnChangeListener(this);
		}

		super.setAdapter(adapter);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (mOnScrollListener != null) {
			mOnScrollListener.onScroll(view, firstVisibleItem,
					visibleItemCount, totalItemCount);
		}

		if (onLoadListener != null) {

			if (visibleItemCount == totalItemCount && !mIsLoadingMore) {
				mProgressBarLoadMore.setVisibility(View.GONE);
				return;
			}

			boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

			if (!mIsLoadingMore && loadMore
					&& mCurrentScrollState != SCROLL_STATE_IDLE) {
				mProgressBarLoadMore.setVisibility(View.VISIBLE);
				// mLabLoadMore.setVisibility(View.VISIBLE);
				mIsLoadingMore = true;
				onLoadMore();
			}

		}
	}

	/**
	 * 加载完成
	 */
	public void onLoadMoreComplete() {
		mIsLoadingMore = false;
		mProgressBarLoadMore.setVisibility(View.GONE);
	}

	/**
	 * 没有更多数据
	 */
	public void noMoreData() {
		Toast.makeText(getContext(), "无更多数据", Toast.LENGTH_SHORT).show();
		mProgressBarLoadMore.setVisibility(View.GONE);
	}

	/**
	 * 没有数据提示
	 */
	public void noMoreData(String msg) {
		Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
		mProgressBarLoadMore.setVisibility(View.GONE);
	}

	private void onLoadMore() {
		Log.d(TAG, "onLoadMore");
		if (onLoadListener != null) {
			onLoadListener.onLoadMore(++page, pageSize);
		}
	}

	public interface OnLoadListener {
		/**
		 * 加载更多 当前的页数，每页数据条数
		 */
		void onLoadMore(int page, int pageSize);
	}

	@Override
	public void onChange() {
		if (getCount() == 1) {// clear相当于刷新
			page = 1;
			mIsLoadingMore = true;
			mProgressBarLoadMore.setVisibility(View.VISIBLE);
		} else if ((getCount() - 1) < page * pageSize) {
			mIsLoadingMore = true;
			mProgressBarLoadMore.setVisibility(View.GONE);
		}
	}
}

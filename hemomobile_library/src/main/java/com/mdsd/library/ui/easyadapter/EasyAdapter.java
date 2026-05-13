/*
 * Copyright (C) 2014 Ribot Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mdsd.library.ui.easyadapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mdsd.library.ui.easyadapter.annotations.ClassAnnotationParser;

/**
 * Extension of BaseAdapter. Don't worry about implementing your own Adapter,
 * the EasyAdapter will <b>do the tedious work for you.</b> You just have to
 * implement an {@link uk.co.ribot.easyadapter.ItemViewHolder} and pass it into
 * the constructor of this class. </p> It implements the <a href=
 * "http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder"
 * >"view holder"</a> design pattern so your ListView will scroll smoothly.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class EasyAdapter<T> extends BaseAdapter {

	private List<T> mListItems;
	private Class mItemViewHolderClass;
	private LayoutInflater mInflater;
	private Integer mItemLayoutId;
	private int selectedPosition = -1;
	private OnChangeListener onChangeListener;
	private OnItemViewCallListener<T> onItemViewCallListener;
	

	/**
	 * Constructs and EasyAdapter with a Context, an
	 * {@link uk.co.ribot.easyadapter.ItemViewHolder} class, and list of items.
	 * 
	 * @param context
	 *            a valid Context
	 * @param itemViewHolderClass
	 *            your {@link uk.co.ribot.easyadapter.ItemViewHolder}
	 *            implementation class
	 * @param listItems
	 *            the list of items to load in the Adapter
	 */
	public EasyAdapter(Context context, Class itemViewHolderClass,
			List<T> listItems) {
		setItems(listItems);
		setItemViewHolderClass(itemViewHolderClass);
		setLayoutInflater(context);
		setItemLayoutId(itemViewHolderClass);
	}
	
	public void setSelectedPosition(int position) {
		if(selectedPosition != position){
			selectedPosition = position;
			notifyDataSetInvalidated();
		}
		
	}
	
	public OnChangeListener getOnChangeListener() {
		return onChangeListener;
	}

	public void setOnChangeListener(OnChangeListener onChangeListener) {
		this.onChangeListener = onChangeListener;
	}
	
	
	public OnItemViewCallListener<T> getOnItemViewCallListener() {
		return onItemViewCallListener;
	}

	public void setOnItemViewCallListener(
			OnItemViewCallListener<T> onItemViewCallListener) {
		this.onItemViewCallListener = onItemViewCallListener;
	}

	/**
	 * Constructs and EasyAdapter with a Context and an
	 * {@link uk.co.ribot.easyadapter.ItemViewHolder} class.
	 * 
	 * @param context
	 *            a valid Context
	 * @param itemViewHolderClass
	 *            your {@link uk.co.ribot.easyadapter.ItemViewHolder}
	 *            implementation class
	 */
	public EasyAdapter(Context context, Class itemViewHolderClass) {
		mListItems = new ArrayList<T>();
		setItemViewHolderClass(itemViewHolderClass);
		setLayoutInflater(context);
		setItemLayoutId(itemViewHolderClass);
	}

	/**
	 * Sets a new list of items into the Adapter
	 * 
	 * @param listItems
	 *            new list of items
	 */
	public void setItems(List<T> listItems) {
		mListItems = listItems;
		notifyDataSetChanged();
	}

	/**
	 * Adds a single item to the Adapter
	 * 
	 * @param item
	 *            item to add
	 */
	public void addItem(T item) {
		mListItems.add(item);
		notifyDataSetChanged();
	}

	/**
	 * Appends a list of items to the ones already in the Adapter
	 * 
	 * @param listItems
	 *            list of items to append
	 */
	public void addItems(List<T> listItems) {
		mListItems.addAll(listItems);
		notifyDataSetChanged();
	}

	/**
	 * clear data
	 */
	public void clear() {
		mListItems.clear();
		notifyDataSetChanged();
	}

	/**
	 * remove location data
	 * 
	 * @param location
	 */
	public void remove(int location) {
		mListItems.remove(location);
		notifyDataSetChanged();
	}

	/**
	 * remove object
	 * 
	 * @param object
	 */
	public void remove(T object) {
		mListItems.remove(object);
		notifyDataSetChanged();
	}

	/**
	 * replace item
	 * 
	 * @param location
	 * @param object
	 */
	public void replace(int location, T object) {
		mListItems.set(location, object);
		notifyDataSetChanged();
	}

	/**
	 * remove collection
	 * 
	 * @param collection
	 */
	public void remove(Collection<?> collection) {
		mListItems.removeAll(collection);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mListItems.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public T getItem(int position) {
		return mListItems.get(position);
	}

	public List<T> getListItems() {
		return mListItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemViewHolder<T> holder;
		if (convertView == null) {
			convertView = mInflater.inflate(mItemLayoutId, parent, false);
			// Create a new view holder using reflection
			holder = createViewHolder(convertView);
			if (convertView != null)
				convertView.setTag(holder);

			holder.setAdapter(this);
		} else {
			// Reuse the view holder
			holder = (ItemViewHolder) convertView.getTag();
		}

		T item = getItem(position);
		
		holder.setSelected(selectedPosition == position);
		holder.setItem(item);
		PositionInfo positionInfo = new PositionInfo(position,
				isFirst(position), isLast(position));
		holder.onSetValues(item, positionInfo);
		holder.onSetListeners(item, positionInfo);

		return convertView;
	}

	private void setLayoutInflater(Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// Checks that the itemViewHolderClass is a valid implementation of
	// ItemViewHolder
	// If not will throw InvalidViewHolderException.
	private void setItemViewHolderClass(Class itemViewHolderClass) {
		if (ItemViewHolder.class.isAssignableFrom(itemViewHolderClass)) {
			mItemViewHolderClass = itemViewHolderClass;
		} else {
			throw new InvalidViewHolderException();
		}
	}

	// Parses the layout ID annotation form the itemViewHolderClass
	private void setItemLayoutId(Class itemViewHolderClass) {
		mItemLayoutId = ClassAnnotationParser.getLayoutId(mItemViewHolderClass);
		if (mItemLayoutId == null) {
			throw new LayoutIdMissingException();
		}
	}

	// Create a new ItemViewHolder using Java reflection
	private ItemViewHolder createViewHolder(View view) {
		try {
			Constructor<ItemViewHolder> constructor = mItemViewHolderClass
					.getConstructor(View.class);
			return constructor.newInstance(view);
		} catch (IllegalAccessException e) {
			throw new InvalidViewHolderException();
		} catch (NoSuchMethodException e) {
			throw new InvalidViewHolderException();
		} catch (InvocationTargetException e) {
			throw new InvalidViewHolderException();
		} catch (InstantiationException e) {
			throw new InvalidViewHolderException();
		}
	}

	private boolean isLast(int position) {
		return position == mListItems.size() - 1;
	}

	private boolean isFirst(int position) {
		return position == 0;
	}
	
	public interface OnChangeListener {
		/**
		 * 当视图因为数据更新调用
		 */
		void onChange();
	}
	
	
	public interface OnItemViewCallListener<T>{
		/**
		 * 内部view的回调事件
		 * 
		 * @param v （执行的view）
		 * @param currItem （当前的item）
		 */
		void onItemViewCall(View v,T currItem);
	}
}

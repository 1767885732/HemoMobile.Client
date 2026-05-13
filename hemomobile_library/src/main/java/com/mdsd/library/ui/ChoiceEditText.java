package com.mdsd.library.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.mdsd.library.R;
import com.mdsd.library.utils.ToastUtil;

/**
 * 可以选择并可以编辑内容的edittext
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月4日
 */
public class ChoiceEditText extends LinearLayout {

	/**
	 * 选择的数据源
	 */
	private CharSequence[] mEntries;

	/**
	 * 是否多选，默认单选
	 */
	private boolean mMultipleChoice = false;

	/**
	 * 选择窗口
	 */
	AlertDialog choiceDialog = null;

	/**
	 * 多选状态下选择的数据
	 */
	boolean[] selected;

	OnConfirmListener onConfirmListener;

	/**
	 * 编辑框
	 */
	private EditText editText;

	/**
	 * 选择点击按钮
	 */
	private ImageView imgChoice;

	/**
	 * 获取控件的编辑对象
	 *
	 * @return
	 */
	public EditText getEditText() {
		return editText;
	}

	/**
	 * 获取输入项
	 *
	 * @return
	 */
	public Editable getText(){
		return editText.getText();
	}
	
	/**
	 * 设置输入项
	 *
	 * @param text
	 */
	public void setText(CharSequence text){
		editText.setText(text);
	}
	
	public ChoiceEditText(Context context) {
		this(context, null);
	}

	public ChoiceEditText(Context context, AttributeSet attrs) {
		// 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public ChoiceEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray type = context.obtainStyledAttributes(attrs,
				R.styleable.ChoiceEditText);
		editText = new EditText(getContext(),attrs,defStyle);
		// 设置数据源
		setChoiceData(type.getTextArray(R.styleable.ChoiceEditText_entries));
		editText.setEnabled(type.getBoolean(R.styleable.ChoiceEditText_enabled, true));
		this.mMultipleChoice = type.getBoolean(
				R.styleable.ChoiceEditText_multipleChoice, true);
		type.recycle();

		init();
	}

	/**
	 * 设置选择确认的监听
	 * 
	 * @param confirmListener
	 */
	public void setOnConfirmListener(OnConfirmListener confirmListener) {
		onConfirmListener = confirmListener;
	}

	/**
	 * 设置数据源
	 * 
	 * @param data
	 */
	public void setChoiceData(CharSequence[] data) {
		if (data != null) {
			this.mEntries = data;
			selected = new boolean[this.mEntries.length];
		}
	}

	private void init() {

		editText.setBackground(null);
		
		addView(editText, new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));

		imgChoice = new ImageView(getContext());
		imgChoice.setImageResource(R.drawable.input_ic_arrow_down);
		imgChoice.setScaleType(ScaleType.CENTER);
		imgChoice.setPadding(10, 0, 10, 0);
		imgChoice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();

			}
		});
		
		addView(imgChoice, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
	}

	/**
	 * 弹出选择窗口
	 */
	private void showDialog() {
		if(mEntries == null){
			ToastUtil.show(getContext(), "无可选项");
			return;
		}
		
		if (choiceDialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

			if (mMultipleChoice) {// 多选
				builder.setMultiChoiceItems(mEntries,
						new boolean[mEntries.length],
						new OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								selected[which] = isChecked;
							}
						}).setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String resultStr = "";
								for (int i = 0; i < selected.length; i++) {
									if (selected[i]) {
										resultStr += mEntries[i] + ",";
									}
								}
								if (resultStr.length() > 0) {
									resultStr = resultStr.substring(0,
											resultStr.length() - 1);
								}
								editText.setText(resultStr);
								editText.setSelection(editText.getText()
										.length());
								callListener();

							}
						});

			} else {// 单选
				builder.setSingleChoiceItems(mEntries, 0,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								editText.setText(mEntries[which]);
								editText.setSelection(editText.getText()
										.length());
								callListener();
								dialog.dismiss();
							}
						}).setNegativeButton("取消", null);
			}

			choiceDialog = builder.create();
		}

		choiceDialog.show();
	}

	void callListener() {
		if (onConfirmListener != null)
			onConfirmListener.onConfirm(editText.getText().toString());
	}

	public interface OnConfirmListener {
		void onConfirm(String text);
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		try {
			super.onRestoreInstanceState(state);
		} catch (Exception e) {
		}
		state = null;
	}
	
}

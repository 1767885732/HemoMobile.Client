package com.mdsd.library.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.mdsd.library.R;

public class MultipleChoiceView extends RelativeLayout {

	private EditText editText;
	private Button btnMore;
	private String[] itemArray;
	private Context context;
	boolean[] selected;// 一个存放Boolean值的数组
	private Boolean multipleChoice;
	private int singleSelected;

	public String getText() {
		return editText.getText().toString();
	}

	public void setText(String text) {
		editText.setText(text);
		if (multipleChoice) {
			String[] stringList = text.split(",");
			if (selected != null && selected.length > 0) {
				for (int i = 0; i < stringList.length; i++) {
					for (int j = 0; j < itemArray.length; j++) {
						if (itemArray[j].equals(stringList[i])) {
							selected[j] = true;
						}
					}
				}
			}
		} else {
			for (int i = 0; i < itemArray.length; i++) {
				if (itemArray[i].equals(text)) {
					singleSelected = i;
					return;
				}
			}
		}
	}

	public boolean[] getSelected() {
		return selected;
	}

	public void setSelected(boolean[] selected) {
		this.selected = selected;
	}

	public EditText getEditText() {
		return editText;
	}

	public void setEditText(EditText editText) {
		this.editText = editText;
	}

	public Button getBtnMore() {
		return btnMore;
	}

	public void setBtnMore(Button btnMore) {
		this.btnMore = btnMore;
	}

	public String[] getItemArray() {
		return itemArray;
	}

	public void setItemArray(String[] itemArray) {
		if (itemArray != null) {
			this.itemArray = itemArray;
			
			selected = new boolean[itemArray.length];
			for (int i = 0; i < itemArray.length; i++) {
				selected[i] = false;
			}
		}
	}

	public MultipleChoiceView(Context context) {
		super(context);
	}

	@SuppressLint("NewApi")
	public MultipleChoiceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.multiple_choice, this);

		editText = (EditText) findViewById(R.id.edit_multi_text);
		btnMore = (Button) findViewById(R.id.btn_multi_more);

		btnMore.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (itemArray != null && itemArray.length > 0) {
					initDialog();
				}
			}
		});

		// 获取焦点监听
		editText.setOnFocusChangeListener(new FocusChangeListener());

		TypedArray type = context.obtainStyledAttributes(attrs,
				R.styleable.MultipleChoice);
		String items = type.getString(R.styleable.MultipleChoice_itemArray);
		if (items != null && !items.isEmpty()) {
			setItemArray(items.split(","));
		}
		Boolean allowInput = type.getBoolean(
				R.styleable.MultipleChoice_allowInput, true);
		if (allowInput) {
			editText.setEnabled(true);
			editText.setTextColor(Color.BLACK);
		} else {
			editText.setEnabled(false);
			editText.setTextColor(Color.WHITE);
		}
		multipleChoice = type.getBoolean(
				R.styleable.MultipleChoice_multipleChoice, true);
		type.recycle();

	}

	private void initDialog() {
		AlertDialog.Builder builder = new Builder(
				MultipleChoiceView.this.context);
		builder.setTitle("请选择...");
		if (multipleChoice) {
			builder.setMultiChoiceItems(itemArray, selected,
					new DialogInterface.OnMultiChoiceClickListener() {	// 设置多选条目
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							if (isChecked) {
								selected[which] = true;
							} else {
								selected[which] = false;
							}
						}
					})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									String resultStr = "";
									for (int i = 0; i < selected.length; i++) {
										if (selected[i]) {
											resultStr += itemArray[i] + ",";
										}
									}
									if (resultStr.length() > 0
											&& resultStr.endsWith(",")) {
										resultStr = resultStr.substring(0,
												resultStr.length() - 1);
									}
									editText.setText(resultStr);
								}
							}).show();
		} else {
			builder.setSingleChoiceItems(itemArray, singleSelected,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							singleSelected = which;
							editText.setText(itemArray[which]);
							dialog.dismiss();
						}
					});
			builder.setNegativeButton("取消", null);
			builder.show();
		}
	}

	/**
	 * 获取焦点监听 
	 */
	private class FocusChangeListener implements OnFocusChangeListener {

		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				editText.setSelection(0);
			}
		}
	}

}


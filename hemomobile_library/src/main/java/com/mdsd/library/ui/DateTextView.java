package com.mdsd.library.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mdsd.library.R;

/**
 * 默认显示日期的textview控件，点击弹出日期选择
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月5日
 */
public class DateTextView extends TextView implements OnClickListener {

	DatePickerDialog datePickerDialog = null;
	TimePickerDialog timePickerDialog = null;
	AlertDialog bothAlertDialog = null;
	/**
	 * 显示类型还是日期<br>
	 * 默认为0日期1时间2日期和时间
	 */
	int timeType = 0;
	
	/**
	 * 设置时间后的回调
	 */
	private OnSetListener onSetListener;
	
	private Context mContext;

	public OnSetListener getOnSetListener() {
		return onSetListener;
	}

	public void setOnSetListener(OnSetListener onSetListener) {
		this.onSetListener = onSetListener;
	}

	public DateTextView(Context context) {
		this(context, null);
	}

	public DateTextView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.textViewStyle);
	}

	public DateTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		TypedArray a = mContext.obtainStyledAttributes(attrs,
				R.styleable.DateTextView, defStyle, 0);
		
		this.timeType = a.getInt(R.styleable.DateTextView_timeType, 0);
		
		a.recycle();
		
		setOnClickListener(this);
		
		if(this.timeType == 1)
			setText((new SimpleDateFormat("HH:mm")).format(new Date()));
		else if(this.timeType == 0)
			setText((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
		else
			setText((new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(new Date()));
	}

	@Override
	public void onClick(View v) {
		if(this.timeType == 1)
			showTime();
		else if(this.timeType == 0)
			showDate();
		else
			showBoth();

	}
	
	/**
	 * 显示日期
	 */
	private void showDate(){
		if (datePickerDialog == null)
			datePickerDialog = new DatePickerDialog(mContext,
					new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							setText(year, monthOfYear, dayOfMonth, 0, 0);

						}
					}, Calendar.getInstance().get(Calendar.YEAR), Calendar
							.getInstance().get(Calendar.MONTH), Calendar
							.getInstance().get(Calendar.DAY_OF_MONTH));

		datePickerDialog.show();
	}
	
	/**
	 * 显示时间
	 */
	private void showTime(){
		if(timePickerDialog == null)
			timePickerDialog = new TimePickerDialog(mContext, new OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							setText(0, 0, 0, hourOfDay, minute);
					
				}
			}, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
	
		timePickerDialog.show();
	}
	
	/**
	 * 显示日期和时间
	 */
	private void showBoth(){
		if(bothAlertDialog == null){
			LinearLayout layout = new LinearLayout(mContext);
			layout.setGravity(Gravity.CENTER);
			layout.setOrientation(LinearLayout.VERTICAL);
			final DatePicker datePicker = new DatePicker(mContext);
			final TimePicker timePicker = new TimePicker(mContext);
			
			datePicker.setCalendarViewShown(false);
			timePicker.setIs24HourView(true);
			
			layout.addView(datePicker);
			layout.addView(timePicker);
			
			DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which == DialogInterface.BUTTON_POSITIVE){
						
						setText(datePicker.getYear(),
								datePicker.getMonth(),
								datePicker.getDayOfMonth(),
								timePicker.getCurrentHour(),
								timePicker.getCurrentMinute());
					}
				}
			};
			
			bothAlertDialog = new AlertDialog.Builder(mContext).create();
			bothAlertDialog.setView(layout);
			bothAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "设定", onClickListener);
			bothAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",onClickListener);
		}
		
		bothAlertDialog.show();
	}
	
	/**
	 * 设置当前的显示内容
	 * 
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @param hour
	 * @param minute
	 */
	private void setText(int year,int month,int dayOfMonth,int hour,int minute){
		switch (timeType) {
		case 0:
			setText(String.format("%04d-%02d-%02d", year, month + 1,
					dayOfMonth));
			break;

		case 1:
			setText(String.format("%02d:%02d", hour, minute));
			break;
		case 2:
			setText(String.format("%04d-%02d-%02d %02d:%02d", year, month + 1,
					dayOfMonth, hour, minute));
			break;
		}
		
		if(onSetListener != null)
			onSetListener.onSet(getText().toString());
	}
	
	/**
	 * 
	 * <p>com.mdsd.library.ui.OnSetListener</p>
	 * <p>时间设置监听</p>
	 *
	 */
	public interface OnSetListener{
		void onSet(String text);
	}

}

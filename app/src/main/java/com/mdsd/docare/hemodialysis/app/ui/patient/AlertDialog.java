/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mdsd.docare.hemodialysis.app.ui.patient;

import java.util.List;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.service.MessageCallService;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class AlertDialog extends Activity {
	private TextView mTextView;
	private Button mButton;
	NotificationManager notificationManager;
	MessageCallService mcs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alert_dialog);
		findViews();

	}

	private void findViews(){
		mcs=MessageCallService.getInstance();
		mTextView = (TextView) findViewById(R.id.alert_message);
		mButton = (Button) findViewById(R.id.btn_cancel);
		notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
	}
	
//	public void ok(View view){
//		setResult(RESULT_OK,new Intent().putExtra("position", position).
//				putExtra("edittext", editText.getText().toString())
//				/*.putExtra("voicePath", voicePath)*/);
//		if(position != -1)
//			ChatActivity.resendPos = position;
//		finish();
//
//	}
	
	public void cancel(View view){
		finish();
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event){
//		finish();
//		return true;
//	}

	@Override
	public void onResume() {
		super.onResume();
		notificationManager.cancel(MessageCallService.MESSAGE_ID);
		getMsg();
	}

	@Override
	public void onPause() {
		super.onPause();
//		mcs.start(AlertDialog.this);
	}


	private void getMsg(){
		mTextView.setText("医嘱测试"+"\n");
//		msgAction.getAlertInfolList(new OnMessageListener<List<Message>>(AlertDialog.this, "正在查询中，请稍后...") {
//
//			@Override
//			public void onSuccess(List<Message> result) {
//				for (int i = 0; i < result.size(); ++i) {
//					Message msg=result.get(i);
//					mTextView.setText(msg.getTIME_POINT()+" "+msg.getNAME()+msg.getVITAL_SIGNS()+msg.getVITAL_SIGNS_VALUES()+msg.getDESCRB()+"\n");
//				}
//
//			}
//
//			@Override
//			public void onError(VolleyError error) {
//				super.onError(error);
//			}
//		});
	}


}

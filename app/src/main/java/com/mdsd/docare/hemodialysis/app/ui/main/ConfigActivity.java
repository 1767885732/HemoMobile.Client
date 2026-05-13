package com.mdsd.docare.hemodialysis.app.ui.main;

import android.app.AlertDialog;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.EditText;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.entity.AppSingleton;
import com.mdsd.docare.hemodialysis.app.core.ui.activity.BaseActivity;
import com.mdsd.library.utils.ToastUtil;

public class ConfigActivity extends BaseActivity {

	public static String KEY_IP = "key_ip";

	// view
	EditText etIp;
	AlertDialog alertDialog;

	// variable
	String ipAddress = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState,R.layout.config_activity,true);

		ipAddress = getSharedPreferences("config", MODE_PRIVATE).getString(
				KEY_IP, "");

		etIp = (EditText) findViewById(R.id.etIp);
		etIp.setText(ipAddress);
		etIp.setSelection(etIp.getText().length());
	}

	@Override
	public void onBackPressed() {
		finishedThis();
	}
	
	/**
	 * 测试当前ip地址是否可用
	 */
	void testConnection(){
		saveConfig();
	}

	/**
	 * 结束当前页面
	 */
	void finishedThis() {
		// 判断是否设置ip
		if (etIp.getText().length() == 0) {
			// TODO 还应该验证ip地址格式的正确性,此处暂时省略
			ToastUtil.show(getApplicationContext(), "请配置访问服务的地址");
			return;
		}

		AppSingleton.getInstance().IP_ADDRESS = etIp.getText().toString();
		testConnection();
	}

	/**
	 * 保存配置
	 */
	void saveConfig() {
		Editor editor = getSharedPreferences("config", MODE_PRIVATE).edit();

		editor.putString(KEY_IP, etIp.getText().toString());

		editor.commit();
		ToastUtil.show(getApplicationContext(), "设置成功");
		
		setResult(RESULT_OK);
		finish();
	}
}

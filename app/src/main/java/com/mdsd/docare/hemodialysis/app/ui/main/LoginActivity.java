package com.mdsd.docare.hemodialysis.app.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.app.Config;
import com.mdsd.docare.hemodialysis.app.core.entity.AppContext;
import com.mdsd.docare.hemodialysis.app.core.entity.AppSingleton;
import com.mdsd.docare.hemodialysis.app.core.service.BaseService;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.core.ui.activity.BaseActivity;
import com.mdsd.docare.hemodialysis.app.entity.server.MedUser;
import com.mdsd.docare.hemodialysis.app.service.MedUserService;
import com.mdsd.library.manage.single.ActivityManageSingle;
import com.mdsd.library.manage.update.UpdateManage;
import com.mdsd.library.utils.DigestUtils;
import com.mdsd.library.utils.ToastUtil;

/**
 * 用户登录页面
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月15日
 */
public class LoginActivity extends BaseActivity {

	// variable
	Handler uiHandler;

	// view
	EditText etAccount, etPassword;
	ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.login_activity);
	}

	@Override
	public void beforeInitView() {
		super.beforeInitView();
		setBarLogoCustomTitle();
		
		uiHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					getActionBar().show();
					uiHandler.sendEmptyMessageDelayed(1, 250);
				} else if (msg.what == 1) {
					findViewById(R.id.lytLogin).setVisibility(View.VISIBLE);
				}
			}
		};
	}

	@Override
	public void findViews() {
		super.findViews();

		etAccount = (EditText) findViewById(R.id.etAccount);
		etPassword = (EditText) findViewById(R.id.etPassword);
		
//		// 测试用
//		etAccount.setText("admin");
//		etPassword.setText("medhemo");

		//显示actionbar和输入框
		uiHandler.sendEmptyMessageDelayed(0, 2000);	
		String url=String.format("http://%s%s", AppSingleton.getInstance().IP_ADDRESS,Config.GET_NEW_VERSION);
		UpdateManage.checkForUpdates(url, this, "hemoMobile");
	}

	@Override
	public void setListeners() {
		super.setListeners();

		findViewById(R.id.btnLogin).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onLogin();
			}
		});
	}

	// ---------------------
	// private method
	// ---------------------
	/**
	 * 跳转到条件过滤页面
	 */
	void toConditionFilterActivity() {
		startActivity(new Intent(this, ConditionFilterActivity.class));
	}
	
	/**
	 * 登录处理
	 */
	void onLogin(){
		// 输入验证
		if(etAccount.getText().length() == 0 || etPassword.getText().length() == 0){
			ToastUtil.show(this, "请输入用户名或密码");
			return;
		}
		
		
		// 访问服务，进行用户名密码正确性验证
		// 登录
		if(progressDialog == null)
			progressDialog = ProgressDialog.show(this, "", "正在登录...", true);
		else
			progressDialog.show();
			
		// 登录
		MedUserService.onLogin(etAccount.getText().toString(),DigestUtils.md5(etPassword.getText().toString().toUpperCase()) ,new OnNetListener<MedUser>() {
			@Override
			public void onResponse(MedUser t) {
				progressDialog.cancel();
				AppContext.getInstance().currentUser=t;
				toConditionFilterActivity();
				finish();
			}
			
			@Override
			public void onError(String errorMessage) {
				progressDialog.cancel();
				ToastUtil.show(getApplicationContext(), errorMessage);
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		ActivityManageSingle.getInstance().appExit();// 退出程序
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_settings){
			startActivity(new Intent(this, ConfigActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}

}

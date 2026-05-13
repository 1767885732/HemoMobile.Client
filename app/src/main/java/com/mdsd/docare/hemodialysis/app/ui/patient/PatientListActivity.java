package com.mdsd.docare.hemodialysis.app.ui.patient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.jph.takephoto.model.TResult;
import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.app.Config;
import com.mdsd.docare.hemodialysis.app.core.entity.AppSingleton;
import com.mdsd.docare.hemodialysis.app.core.entity.Constants;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.core.ui.activity.BaseActivity;
import com.mdsd.docare.hemodialysis.app.core.ui.activity.BasePhotoActivity;
import com.mdsd.docare.hemodialysis.app.entity.app.PatientSearchParam;
import com.mdsd.docare.hemodialysis.app.entity.server.MedPatientSchedule;
import com.mdsd.docare.hemodialysis.app.service.MedPatientScheduleService;
import com.mdsd.docare.hemodialysis.app.service.MessageCallService;
import com.mdsd.docare.hemodialysis.app.ui.component.SinglePopupWindow;
import com.mdsd.docare.hemodialysis.app.ui.component.SinglePopupWindow.SingleChoiceItem;
import com.mdsd.docare.hemodialysis.app.ui.component.SinglePopupWindow.SingleItemClickListener;
import com.mdsd.docare.hemodialysis.app.util.DataUtil;
import com.mdsd.library.manage.upload.UploadFileTask;
import com.mdsd.library.ui.easyadapter.EasyAdapter;
import com.mdsd.library.utils.StringUtils;
import com.mdsd.library.utils.TimeUtils;
import com.mdsd.library.utils.ToastUtil;

/**
 * 患者列表页面
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月18日
 */
public class PatientListActivity extends BasePhotoActivity
{
	// static
	public static final String EXTRA_PATIENT_SEARCH_PARAM = "extra_patient_search_param";

	// view
	ListView listView;
	SinglePopupWindow popupWindow;
	ProgressDialog progressDialog;
	FrameLayout frameLayout;

	// variable
	EasyAdapter<MedPatientSchedule> listEasyAdapter;
	/**
	 * 当前选择的患者
	 */
	MedPatientSchedule currSelectPatientSchedule;

	/**
	 * 患者搜索参数<br>
	 * 初始化接受条件顾虑页面传入的值
	 */
	PatientSearchParam patientSearchParam;

	List<SingleChoiceItem> singleChoiceItems;
	
	Intent patientTabIntent = null;
	
	private static PatientListActivity patientList = null;

    public static PatientListActivity getInstance() {
        if (patientList != null) {
            return patientList;
        }
        return null;
    }
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState, R.layout.patient_list_activity, true);
		patientList=this;
	}

	@Override
	public void beforeInitView()
	{
		super.beforeInitView();

		if (getIntent() != null)
		{
			patientSearchParam = (PatientSearchParam) getIntent().getSerializableExtra(EXTRA_PATIENT_SEARCH_PARAM);
		}

		// initVariable
		listEasyAdapter = new EasyAdapter<MedPatientSchedule>(this, PatientListItemView.class);
	}

	@Override
	public void findViews()
	{
		super.findViews();

		listView = (ListView) findViewById(R.id.listView);
		listView.setEmptyView(findViewById(android.R.id.empty));
		listView.setAdapter(listEasyAdapter);
		frameLayout= (FrameLayout) findViewById(R.id.ll_root);
//		startService(new Intent(this,MessageCallService.class));
	}

	@Override
	public void setListeners()
	{
		super.setListeners();

		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				listEasyAdapter.setSelectedPosition(position);
				currSelectPatientSchedule = listEasyAdapter.getItem(position);
				popupSingleWindow();
			}
		});
	};

	@Override
	public void setViewData()
	{
		super.setViewData();

		// 访问服务获取患者列表数据
//		loadPatientListFromServer();
	}

	// ----------------
	// private method
	// ----------------
	/**
	 * 加载患者列表
	 */
	void loadPatientListFromServer()
	{
		if (progressDialog == null)
			progressDialog = ProgressDialog.show(this, "", "正在加载数据...", true);
		else
			progressDialog.show();

		MedPatientScheduleService.getPatientScheduleList(patientSearchParam,
				new OnNetListener<List<MedPatientSchedule>>()
				{

					@Override
					public void onResponse(List<MedPatientSchedule> object)
					{
						progressDialog.cancel();
						listEasyAdapter.clear();
						listEasyAdapter.addItems(object);
					}

					@Override
					public void onError(String errorMessage)
					{
						progressDialog.cancel();
						ToastUtil.show(getApplicationContext(), errorMessage);

					}
				});

	}

	/**
	 * 弹出编辑选择窗口
	 */
	void popupSingleWindow()
	{
		if (popupWindow == null)
		{
			singleChoiceItems = new ArrayList<SingleChoiceItem>();

			SingleChoiceItem choiceItem = new SingleChoiceItem("患者拍照", R.color.button_gray, R.color.white);
			singleChoiceItems.add(choiceItem);

			choiceItem = new SingleChoiceItem("开始治疗", R.color.app_green_color, R.color.white);
			singleChoiceItems.add(choiceItem);

			choiceItem = new SingleChoiceItem("结束治疗", R.color.button_gray, R.color.white);
			singleChoiceItems.add(choiceItem);

			popupWindow = SinglePopupWindow.build(this, singleChoiceItems,
					new SingleItemClickListener()
					{
						@Override
						public void onItemClick(int position,View v)
						{
							popupWindow.dismiss();

							if (position == 0) {
								//患者拍照
								baseHemoId = currSelectPatientSchedule.getHEMODIALYSIS_ID();
								baseUrl = String.format("http://%s%s", AppSingleton.getInstance().IP_ADDRESS, Config.UPLOAD_PATIENT_PHOTO);
								showImageDialog(frameLayout);
							} else if (position == 1) {
								v.setEnabled(false);
								startCure(v);
							} else{
								int state = DataUtil.getCureState(currSelectPatientSchedule.getSTART_TIME(), currSelectPatientSchedule.getEND_TIME());
								if (state == 0)// 未开始
									ToastUtil.show(getApplicationContext(), "患者还未开始治疗！");
								else if(state == 2)
									ToastUtil.show(getApplicationContext(), "患者已经结束治疗！");
								else
									new AlertDialog.Builder(PatientListActivity.this).setTitle("确认结束治疗吗？").setIcon(android.R.drawable.ic_dialog_info)
									.setPositiveButton("确定", new OnClickListener(){
										
										@Override
										public void onClick(
												DialogInterface arg0, int arg1) {
											// TODO Auto-generated method stub
											finishCure();
										}}).setNegativeButton("取消", new OnClickListener(){

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												// TODO Auto-generated method stub
												
											}}).show();
							}

						}
					});
		}

		if (DataUtil.getCureState(currSelectPatientSchedule.getSTART_TIME(), currSelectPatientSchedule.getEND_TIME()) == 0)
		{// 还未开始治疗
			singleChoiceItems.get(1).setText("开始治疗");
		} else
			singleChoiceItems.get(1).setText("编辑治疗");

		popupWindow.notifyDataSetChanged();// 更新视图
		popupWindow.showFromBottom(getWindow().getDecorView());// 弹出窗口
	}

	/**
	 * 开始治疗	
	 */
	void startCure(final View v){
		if(StringUtils.isEmpty(currSelectPatientSchedule.getRECIPE_ID()))
		{
			ToastUtil.show(getApplicationContext(), "患者还未确认处方！");
			return;
		}
		
		if(DataUtil.getCureState(currSelectPatientSchedule.getSTART_TIME(), currSelectPatientSchedule.getEND_TIME()) != 0){
			v.setEnabled(true);
			toPatientTabActivity();
			return;
		}
		
		progressDialog = ProgressDialog.show(this, "", "数据处理中...", true);
		
		MedPatientScheduleService.startCure(currSelectPatientSchedule.getHEMODIALYSIS_ID(), currSelectPatientSchedule.getDIALYSIS_DATE(), new OnNetListener<String>() {
			
			@Override
			public void onResponse(String object) {
				progressDialog.dismiss();
				if(object.equals("")){
					//重新加载患者列表
					MedPatientScheduleService.getPatientScheduleList(patientSearchParam,
							new OnNetListener<List<MedPatientSchedule>>()
							{
								@Override
								public void onResponse(List<MedPatientSchedule> object)
								{
									listEasyAdapter.clear();
									listEasyAdapter.addItems(object);
									v.setEnabled(true);
									toPatientTabActivity();
								}

								@Override
								public void onError(String errorMessage)
								{
									ToastUtil.show(getApplicationContext(), errorMessage);
									loadPatientListFromServer();
								}
							});
				}else
					ToastUtil.show(getApplicationContext(), object);
			}
			
			@Override
			public void onError(String errorMessage) {
				progressDialog.dismiss();
				v.setEnabled(true);
				ToastUtil.show(getApplicationContext(), errorMessage);
				loadPatientListFromServer();
			}
		});
	}
	
	/**
	 * 结束治疗
	 */
	void finishCure(){
		progressDialog = ProgressDialog.show(this, "", "数据处理中...", true);
		
		MedPatientSchedule updateSchedule = currSelectPatientSchedule;
		updateSchedule.setSTATUS("2");
		updateSchedule.setEND_TIME(TimeUtils.getCurrentTimeInString(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")));
		MedPatientScheduleService.finishCure(updateSchedule, new OnNetListener<String>() {
			
			@Override
			public void onResponse(String object) {
				progressDialog.dismiss();
				int position = listEasyAdapter.getListItems().indexOf(currSelectPatientSchedule);
				listEasyAdapter.getItem(position).setSTATUS("2");
				listEasyAdapter.notifyDataSetChanged();
				ToastUtil.show(getApplicationContext(), "操作成功");
			}
			
			@Override
			public void onError(String errorMessage) {
				progressDialog.dismiss();
				ToastUtil.show(getApplicationContext(), errorMessage);
				
			}
		});
	}
	
	/**
	 * 跳转到病人功能主页面
	 */
	void toPatientTabActivity()
	{
		if(patientTabIntent == null)
			patientTabIntent = new Intent(this, PatientTabActivity.class);

		currSelectPatientSchedule.setPAT_PIC("");
		patientTabIntent.putExtra(Constants.ExtraKey.MED_PATIENT_SCHEDULE, currSelectPatientSchedule);
		startActivity(patientTabIntent);
}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			if (popupWindow != null)
			{
				if (popupWindow.isShowing())
				{
					popupWindow.dismiss();
					return false;
				}
			}
			finish();
			return false;
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.patient_list_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menuRefresh){
			loadPatientListFromServer();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		loadPatientListFromServer();
		super.onResume();
	}

	@Override
	public void takeSuccess(TResult result) {
		super.takeSuccess(result);
		if (result == null || result.getImage() == null || result.getImage().getOriginalPath() == null) {
			return;
		}
		onUploadCompleted(result, "");
	}

	private void onUploadCompleted(TResult result, String path) {
		if (result != null) {
			String hemoId = currSelectPatientSchedule.getHEMODIALYSIS_ID();
			String filepath = result.getImage().getOriginalPath();
			filepath = Environment.getExternalStorageDirectory().toString() + filepath.substring(filepath.indexOf("/", 1));
			File file = new File(filepath);
			if(!file.exists())
			{
				ToastUtil.show(getApplicationContext(), "照片不存在！");
				return;
			}

			if (progressDialog == null) {
				progressDialog = ProgressDialog.show(this, "", "数据处理中...", true);
			}
			else
			{
				progressDialog.show();
			}

			UploadFileTask uploadFileTask = new UploadFileTask(file, baseUrl, hemoId)
			{
				@Override
				protected void onPostExecute(String s) {
					super.onPostExecute(s);
					if(s.equals(""))
					{
						progressDialog.cancel();
						//重新加载患者列表
						loadPatientListFromServer();
					}
					else
					{
						progressDialog.cancel();
						ToastUtil.show(getApplicationContext(), "患者拍照失败！\r\n" + s);
					}
				}
			};
			uploadFileTask.execute("");
		}
	}
}

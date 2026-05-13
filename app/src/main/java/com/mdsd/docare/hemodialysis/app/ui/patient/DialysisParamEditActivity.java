package com.mdsd.docare.hemodialysis.app.ui.patient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.mdsd.docare.hemodialysis.app.R;
import com.mdsd.docare.hemodialysis.app.core.entity.AppContext;
import com.mdsd.docare.hemodialysis.app.core.entity.AppSingleton;
import com.mdsd.docare.hemodialysis.app.core.entity.Constants;
import com.mdsd.docare.hemodialysis.app.core.service.OnNetListener;
import com.mdsd.docare.hemodialysis.app.core.ui.activity.BaseActivity;
import com.mdsd.docare.hemodialysis.app.core.ui.activity.CheckPermissionsActivity;
import com.mdsd.docare.hemodialysis.app.entity.app.MedClass;
import com.mdsd.docare.hemodialysis.app.entity.server.MedConfig;
import com.mdsd.docare.hemodialysis.app.entity.server.MedHemoParameter;
import com.mdsd.docare.hemodialysis.app.entity.server.MedNurse;
import com.mdsd.docare.hemodialysis.app.service.MedPatientScheduleService;
import com.mdsd.docare.hemodialysis.app.ui.patient.fragment.DialysisParamFm;
import com.mdsd.docare.hemodialysis.app.util.DataUtil;
import com.mdsd.docare.hemodialysis.app.util.Utils;
import com.mdsd.docare.hemodialysis.app.util.UuidLib;
import com.mdsd.docare.hemodialysis.app.util.ViewUtil;
import com.mdsd.library.callback.ScanListener;
import com.mdsd.library.core.bluetooth.Ble;
import com.mdsd.library.core.bluetooth.BleLogger;
import com.mdsd.library.core.bluetooth.Connection;
import com.mdsd.library.core.bluetooth.Device;
import com.mdsd.library.event.Events;
import com.mdsd.library.ui.ClearEditText;
import com.mdsd.library.utils.BleUtils;
import com.mdsd.library.utils.StringUtils;
import com.mdsd.library.utils.TimeUtils;
import com.mdsd.library.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 透析参数编辑页面
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月20日
 */
public class DialysisParamEditActivity extends BaseActivity implements OnClickListener,ScanListener {
	// view
	ProgressDialog progressDialog = null;
	Spinner spiNurse,spi_clinical_Manifestation;
	ClearEditText TextView_clinical_Manifestation,cure_ed_ddd,cure_ed_txyll,cure_ed_knj,txtCure_Mode;
	Spinner cure_spinner_jmydw,cure_spinner_dmydw,cure_spinner_knjdw;
	CheckBox cure_chk_sx_s,cure_chk_sx_f,cure_chk_ht_s,cure_chk_ht_f;
	
	// variable
	/**
	 * 接收到的MedHemoParameter对象<br>
	 * 如果为null,默认为新增状态, 否则为编辑状态
	 */
	MedHemoParameter extraMedHemoParameter;

	private List<Device> data = new ArrayList<>();

	private static int[] PROPERTIES = new int[]{BluetoothGattCharacteristic.PROPERTY_WRITE, BluetoothGattCharacteristic.PROPERTY_INDICATE,
			BluetoothGattCharacteristic.PROPERTY_NOTIFY, BluetoothGattCharacteristic.PROPERTY_READ,
			BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE, BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE};
	private static String[] PROPERTIES_STR = new String[]{"WRITE", "INDICATE", "NOTIFY", "READ", "SIGNED_WRITE", "WRITE_NO_RESPONSE"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState, R.layout.dialysis_param_edit_activity, true);
	}
	
	@Override
	public void beforeInitView() {
		super.beforeInitView();
		extraMedHemoParameter = (MedHemoParameter) getIntent().getSerializableExtra(Constants.ExtraKey.MED_HEMO_PARAMETER);
		initBluetoothConfig();
		Ble.getInstance().initialize(this, null);
	}
	
	@Override
	public void findViews() {
		super.findViews();
		spiNurse = (Spinner) findViewById(R.id.spiNurse);
		spi_clinical_Manifestation = (Spinner) findViewById(R.id.spi_clinical_Manifestation);
		TextView_clinical_Manifestation = (ClearEditText) findViewById(R.id.TextView_clinical_Manifestation);
		txtCure_Mode = (ClearEditText) findViewById(R.id.txtCure_Mode);
		cure_spinner_jmydw=(Spinner) findViewById(R.id.cure_spinner_jmydw);
		//cure_spinner_dmydw=(Spinner) findViewById(R.id.cure_spinner_dmydw);
		cure_spinner_knjdw=(Spinner) findViewById(R.id.cure_spinner_knjdw);
		//cure_chk_sx_s=(CheckBox) findViewById(R.id.cure_chk_sx_s);
		//cure_chk_sx_f=(CheckBox) findViewById(R.id.cure_chk_sx_f);
		//cure_chk_ht_s=(CheckBox) findViewById(R.id.cure_chk_ht_s);
		//cure_chk_ht_f=(CheckBox) findViewById(R.id.cure_chk_ht_f);
		cure_ed_ddd = (ClearEditText) findViewById(R.id.cure_ed_ddd);
		cure_ed_txyll = (ClearEditText) findViewById(R.id.cure_ed_txyll);
		cure_ed_knj = (ClearEditText) findViewById(R.id.cure_ed_knj);
	}
	
	@Override
	public void setListeners() {
		super.setListeners();
		
		findViewById(R.id.btnSave).setOnClickListener(this);
		findViewById(R.id.btnCancel).setOnClickListener(this);
		findViewById(R.id.btnRead).setOnClickListener(this);
		spi_clinical_Manifestation.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (position > 0) {
							String text=txtCure_Mode.getText().toString();
							if(StringUtils.isEmpty(text))
							{
								txtCure_Mode.setText(parent.getAdapter().getItem(position).toString());
							}
							else
							{
								if(text.substring(text.length()-1).equals("；"))
								{
									txtCure_Mode.setText(text + parent.getAdapter().getItem(position).toString());
								}
								else
								{
									txtCure_Mode.setText(text + "；" + parent.getAdapter().getItem(position).toString());
								}
							}
						}
					}				

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
//		cure_chk_sx_s.setOnCheckedChangeListener(new OnCheckedChangeListener(){
//
//			@Override
//			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				if(arg1)
//				{
//					if(cure_chk_sx_f.isChecked())
//					{
//						cure_chk_sx_f.setChecked(false);
//					}
//				}
//			}});
		
//		cure_chk_sx_f.setOnCheckedChangeListener(new OnCheckedChangeListener(){
//
//			@Override
//			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				if(arg1)
//				{
//					if(cure_chk_sx_s.isChecked())
//					{
//						cure_chk_sx_s.setChecked(false);
//					}
//				}
//			}});
		
//		cure_chk_ht_s.setOnCheckedChangeListener(new OnCheckedChangeListener(){
//
//			@Override
//			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				if(arg1)
//				{
//					if(cure_chk_ht_f.isChecked())
//					{
//						cure_chk_ht_f.setChecked(false);
//					}
//				}
//			}});
		
//		cure_chk_ht_f.setOnCheckedChangeListener(new OnCheckedChangeListener(){
//
//			@Override
//			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				if(arg1)
//				{
//					if(cure_chk_ht_s.isChecked())
//					{
//						cure_chk_ht_s.setChecked(false);
//					}
//				}
//			}});
	}
	
	@Override
	public void setViewData() {
		super.setViewData();
		
		ViewUtil.buildSpiData(this, spiNurse, MedNurse.class, AppSingleton.getInstance().medNurses);
		ViewUtil.buildSpiByMedConfig(this, spi_clinical_Manifestation, Constants.Dic.NURSE_ORDER);
		ViewUtil.buildSpiByMedConfig(this, cure_spinner_knjdw, Constants.Dic.DRUG_UNIT);
		
		ArrayAdapter<MedConfig>  medConfigAdapter= new ArrayAdapter<MedConfig>(this, android.R.layout.simple_spinner_item, getVenousPressureUnit());
		medConfigAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cure_spinner_jmydw.setAdapter(medConfigAdapter);
		//cure_spinner_dmydw.setAdapter(medConfigAdapter);
		
		updateView();
	}
	
	// ------------------
	// private method
	// ------------------
	/**
	 * 更新视图根据MedHemoParameter
	 */
	void updateView(){
		if(extraMedHemoParameter != null){
			ViewUtil.setValueToView(findViewById(R.id.rootView), extraMedHemoParameter);
			
			// 单独为时间设置值
			String dateStr = extraMedHemoParameter.getCreate_Date();
			if(!StringUtils.isEmpty(dateStr)){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(TimeUtils.strToDate(dateStr,"yyyy/MM/dd HH:mm:ss"));
				((TextView)findViewById(R.id.dtvRecordTime)).setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)));
			}
			else
			{
				Date dt=TimeUtils.strToDate(DialysisParamFm.dialysisParamList.get(0).getRecordTime(),"HH:mm");
				//复制记录时，时间在上一次的基础上加上60分钟
				for(int i=0;i<DialysisParamFm.dialysisParamList.size();i++)
				{
					if(TimeUtils.strToDate(DialysisParamFm.dialysisParamList.get(i).getRecordTime(),"HH:mm").compareTo(dt)>0)
					{
						dt=TimeUtils.strToDate(DialysisParamFm.dialysisParamList.get(i).getRecordTime(),"HH:mm");
					}
				}
				List<MedConfig> list = DataUtil.getMedConfigs(Constants.Dic.PARAM_TIME_INTERVAL);
				int interval = list.size() > 0 ? Integer.parseInt(list.get(1).getITEM_VALUE()) : 0;
				dt = new Date(dt.getTime() + interval * 60 * 1000);
				((TextView)findViewById(R.id.dtvRecordTime)).setText(String.format("%02d:%02d", dt.getHours(),dt.getMinutes()));
			}
		}
		else
		{
			if (DialysisParamFm.dialysisParamList == null || (DialysisParamFm.dialysisParamList != null && DialysisParamFm.dialysisParamList.size() == 0))
			{
				//上午班次新增第一条透析参数，时间默认是8点
				Calendar calendar = Calendar.getInstance();
				if(calendar.get(Calendar.AM_PM)==Calendar.AM)
				{
					((TextView) findViewById(R.id.dtvRecordTime)).setText("08:00");
				}
				
//				String ssy="";
//				String szy="";
//				String mb="";
				
//				if (PatientTabActivity.currMedCureInfo != null) {
//					ssy = !(PatientTabActivity.currMedCureInfo
//							.getBefore_Systolic_Pressure().toString()
//							.equals("0.0")) ? PatientTabActivity.currMedCureInfo
//							.getBefore_Systolic_Pressure().toString() : "";
//					szy = !(PatientTabActivity.currMedCureInfo
//							.getBefore_Diastolic_Pressure().toString()
//							.equals("0.0")) ? PatientTabActivity.currMedCureInfo
//							.getBefore_Diastolic_Pressure().toString() : "";
//					mb = !(PatientTabActivity.currMedCureInfo.getBefore_BP()
//							.toString().equals("0.0")) ? PatientTabActivity.currMedCureInfo
//							.getBefore_BP().toString() : "";
//				}
				
//				if(PatientTabActivity.currMedRecipeInfo!=null)
//				{
//					if (ssy == "") {
//						ssy = !(PatientTabActivity.currMedRecipeInfo
//								.getToday_Blooda().toString().equals("0.0")) ? PatientTabActivity.currMedRecipeInfo
//								.getToday_Blooda().toString() : "";
//					}
//					if (szy == "") {
//						szy = !(PatientTabActivity.currMedRecipeInfo
//								.getToday_Bloodb().toString().equals("0.0")) ? PatientTabActivity.currMedRecipeInfo
//								.getToday_Bloodb().toString() : "";
//					}
//					if (mb == "") {
//						mb = !(PatientTabActivity.currMedRecipeInfo
//								.getToday_Bloodp().toString().equals("0.0")) ? PatientTabActivity.currMedRecipeInfo
//								.getToday_Bloodp().toString() : "";
//					}
//				}
				
//				if(ssy.indexOf(".")>0)
//				{
//					ssy=ssy.replaceAll("0+?$", "");
//					ssy=ssy.replaceAll("[.]$", "");
//				}
				
//				if(szy.indexOf(".")>0)
//				{
//					szy=szy.replaceAll("0+?$", "");
//					szy=szy.replaceAll("[.]$", "");
//				}
				
//				if(mb.indexOf(".")>0)
//				{
//					mb=mb.replaceAll("0+?$", "");
//					mb=mb.replaceAll("[.]$", "");
//				}
				
				//((ClearEditText) findViewById(R.id.cure_ed_ssy)).setText(ssy);
				//((ClearEditText) findViewById(R.id.cure_ed_szy)).setText(szy);
				//((ClearEditText) findViewById(R.id.cure_ed_mb)).setText(mb);
				
				//cure_ed_knj.setText(PatientTabActivity.currMedRecipeInfo.getFirst_Drug_Dosage());

				for (int i = 0; i < spiNurse.getCount(); i++) {
					if(spiNurse.getItemAtPosition(i) instanceof MedNurse && ((MedNurse) spiNurse.getItemAtPosition(i)).getEMP_NO().equals(AppContext.getInstance().currentUser.getEMP_NO())){
						spiNurse.setSelection(i);
					}
				}

				cure_spinner_jmydw.setSelection(1);
				//cure_spinner_dmydw.setSelection(1);
				if (PatientTabActivity.currMedCureInfo.getMachine_Id().equals("DBB-27") || PatientTabActivity.currMedCureInfo.getMachine_Id().equals("DBB-27C")) {
					cure_spinner_jmydw.setSelection(2);
					//cure_spinner_dmydw.setSelection(2);
				}

				Double min = (double) 0;
				if (PatientTabActivity.currMedCureInfo.getFrequency_Minute() != 0) {
					java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.0");
					min = Double.valueOf(myformat.format(PatientTabActivity.currMedCureInfo.getFrequency_Minute() / 60));
				}
				Double hour = PatientTabActivity.currMedCureInfo.getFrequency_Hours() + min;
				String urf = ((Double) ((PatientTabActivity.currMedCureInfo.getUfr() * 1000 + 300) / hour)).toString();
				urf = !urf.equals("0.0") ? urf : "";
				if(urf.indexOf(".")>0)
				{
					urf = String.valueOf(Math.floor(Double.parseDouble(urf)));
					urf = urf.replaceAll("0+?$", "");
					urf = urf.replaceAll("[.]$", "");
				}
				((ClearEditText) findViewById(R.id.cure_ed_cll)).setText(urf);

				cure_ed_ddd.setText("14.0");
				cure_ed_txyll.setText("500");
				//cure_ed_knj.setText("4000");
			}
//			else
//			{
//				cure_ed_knj.setText("0");
//			}
		}
	}
	
	// --------------------
	// Override method
	// --------------------
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSave:
			//检查记录是否提前录入，若提前，给予提示，不予保存
			Date cureDate=TimeUtils.strToDate(PatientTabActivity.currMedCureInfo.getCure_Create_Date(),"yyyy/MM/dd");
			Date nowDate=TimeUtils.strToDate(TimeUtils.dateToStr(new Date(), "yyyy/MM/dd"), "yyyy/MM/dd");
			if(cureDate.compareTo(nowDate)==0)
			{
				Date recordTime = TimeUtils.strToDate(((TextView) findViewById(R.id.dtvRecordTime)).getText().toString(), "HH:mm");
				Date nowTime=TimeUtils.strToDate(TimeUtils.dateToStr(new Date(), "HH:mm"), "HH:mm");
				if(recordTime.compareTo(nowTime)>0)
				{
					ToastUtil.show(getApplicationContext(), "透析参数不允许提前录入");
					return;
				}
			}
			
			if(extraMedHemoParameter == null){
				extraMedHemoParameter = new MedHemoParameter();
				extraMedHemoParameter.setCure_Id(PatientTabActivity.currMedCureInfo.getCure_Id());
				extraMedHemoParameter.setRecipe_Id(PatientTabActivity.currMedCureInfo.getRecipe_Id());
			}
			
			extraMedHemoParameter.setVascular_Access_Glide("");
			extraMedHemoParameter.setVascular_Access_Errhyisis("");
			// 赋值
			ViewUtil.getValueFromView(findViewById(R.id.rootView), extraMedHemoParameter);
			
			// 调用服务进行保存操作
			save();
			break;

		case R.id.btnCancel:
			finish();
			break;
		case R.id.btnRead:
			if (Ble.getInstance().isInitialized()) {
				if (Ble.getInstance().isBluetoothAdapterEnabled()) {
					requestPermissions();
					Ble.getInstance().startScan(this);
				} else {
					startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),0);
				}
			}
			break;
		}
	}
	
	/**
	 * 保存透析参数数据
	 */
	void save(){
		if(progressDialog == null)
			progressDialog = ProgressDialog.show(this, "", "正在保存数据...", true);
		else
			progressDialog.show();
		
		// 保存数据
		MedPatientScheduleService.saveHemoParameter(extraMedHemoParameter, new OnNetListener<String>() {
			
			@Override
			public void onResponse(String object)
			{
				progressDialog.cancel();
				ToastUtil.show(getApplicationContext(), "保存成功");
				setResult(RESULT_OK);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK)
		{
			requestPermissions();
			Ble.getInstance().startScan(this);
		}
	}

	@Override
	public void onScanStart() {

	}

	@Override
	public void onScanStop() {

	}

	@Override
	public void onScanResult(@NonNull Device device) {
		boolean isHasDevice = false;
		for (Device datum : data) {
			if (datum.addr.equals(device.addr)) {
				isHasDevice = true;
			}
		}
		if (!isHasDevice) {
			if(device.name.toUpperCase().startsWith("AES-")) {
				data.add(device);
			}
		}

		if(device.name.toUpperCase().startsWith("AES-")) {
			Ble.getInstance().registerSubscriber(this);
			Ble.getInstance().getConnection(device);
			Ble.getInstance().connect(this, device, true, null);
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onConnectionStateChange(Events.ConnectionStateChanged e) {
		switch (e.state) {
			case Connection.STATE_SERVICE_DISCOVERED:
				Connection connection = Ble.getInstance().getConnection(data.get(0));
				if (connection != null) {
					for (BluetoothGattService bluetoothGattService : connection.getGattServices()) {
						UUID blueToothUUid = bluetoothGattService.getUuid();
						String name = UuidLib.getServiceName(blueToothUUid);
						if ("Glucose".equals(name)) {
							for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService.getCharacteristics()) {
								int characteristicProperties = bluetoothGattCharacteristic.getProperties();
//                                List<String> access = new ArrayList<>();
								int accessCount = 0;
								for (int i = 0; i < PROPERTIES.length; i++) {
									int property = PROPERTIES[i];
									if ((characteristicProperties & property) != 0) {
										String accessStr = PROPERTIES_STR[i];
										if ("WRITE".equals(accessStr)
												|| "NOTIFY".equals(accessStr)
												|| "READ".equals(accessStr)) {
											accessCount++;
										}
									}
								}
								if (accessCount==3) {
//                                    ParcelUuid extra_write_service = new ParcelUuid(bluetoothGattService.getUuid());
//                                    ParcelUuid extra_write_characteristic = new ParcelUuid(bluetoothGattCharacteristic.getUuid());
									ParcelUuid extra_notify_service =  new ParcelUuid(bluetoothGattService.getUuid());
									ParcelUuid extra_notify_characteristi = new ParcelUuid(bluetoothGattCharacteristic.getUuid());
									Ble.getInstance().getConnection(data.get(0)).toggleNotification("1", extra_notify_service.getUuid(),
											extra_notify_characteristi.getUuid(), true);
									return;
								}
							}
						}
					}
				}
				break;
		}
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onCharacteristicChanged(Events.CharacteristicChanged e) {
		String hexString = BleUtils.bytesToHexString(e.characteristic.getValue());
		Log.e("hexString==s",hexString+"");
		byte[] value =  e.characteristic.getValue();
		String headerStr = BleUtils.bytesToHexString(new byte[]{value[0],value[1],value[2]}).trim();
		Log.e("hexString==h",headerStr+"");
		if (("FD FD 5A").equals(headerStr)&&(value.length==13)) {
			String endStr = BleUtils.bytesToHexString(new byte[]{value[11],value[12]}).trim();
			if ("0D 0A".equals(endStr)) {
				int sbpData = Utils.bytesToInteger(value[4],value[3]);
				int dbpData = Utils.bytesToInteger(value[6],value[5]);
				int rrData = Utils.bytesToInteger(value[8],value[7]);
				String rrStatusData = "检测失败";
				String rrStatusCode = BleUtils.bytesToHexString(new byte[]{value[9]}).trim();
				if ("55".equals(rrStatusCode))
				{
					rrStatusData = "正常";
				}else if ("AA".equals(rrStatusCode)){
					rrStatusData = "心率不齐";
				}
				((ClearEditText) findViewById(R.id.cure_ed_ssy)).setText(sbpData+"");
				((ClearEditText) findViewById(R.id.cure_ed_szy)).setText(dbpData+"");
				((ClearEditText) findViewById(R.id.cure_ed_mb)).setText(rrData+"");
			}
		}
	}

	/**
	 * 初始化蓝牙配置
	 */
	private void initBluetoothConfig()
	{
		Ble.getInstance().addScanListener(this);
		Ble.getInstance().getConfiguration().setDiscoverServicesDelayMillis(500);
		Ble.getInstance().getConfiguration().setScanPeriodMillis(30000);
		Ble.getInstance().setLogPrintLevel(BleLogger.ALL);//输出日志
		Ble.getInstance().getConfiguration().setWaitWriteResult(true);
		Ble.getInstance().getConfiguration().setUseBluetoothLeScanner(false);
	}

	private void requestPermissions()
	{
		int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
		if(result!=0) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
		}
	}

	private List<MedConfig> getVenousPressureUnit()
	{
		List<MedConfig> listConfig = new ArrayList<MedConfig>();
		MedConfig config=new MedConfig();
		config.setITEM_ID("0");
		config.setITEM_VALUE("");
		config.setITEM_NAME("");
		config.setITEM_TYPE("单位");
		config.setORDER_NUMBER(0);
		listConfig.add(config);

		config=new MedConfig();
		config.setITEM_ID("1");
		config.setITEM_VALUE("1");
		config.setITEM_NAME("mmHg");
		config.setITEM_TYPE("单位");
		config.setORDER_NUMBER(1);
		listConfig.add(config);

		config=new MedConfig();
		config.setITEM_ID("2");
		config.setITEM_VALUE("2");
		config.setITEM_NAME("KPa");
		config.setITEM_TYPE("单位");
		config.setORDER_NUMBER(2);
		listConfig.add(config);

		return listConfig;
	}
}

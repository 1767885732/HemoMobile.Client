package com.mdsd.docare.hemodialysis.app.util;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.mdsd.docare.hemodialysis.app.entity.server.MedConfig;
import com.mdsd.docare.hemodialysis.app.entity.server.MedCureInfo;
import com.mdsd.docare.hemodialysis.app.entity.server.MedDoctor;
import com.mdsd.docare.hemodialysis.app.entity.server.MedNurse;
import com.mdsd.docare.hemodialysis.app.entity.server.MedVasularAccess;
import com.mdsd.library.utils.ObjectUtils;


/**
 * 视图的工具类
 * 
 * <br>
 * @author jianyu.l
 * @since 2014年8月18日
 */
public class ViewUtil {
	
	/**
	 * 构建对应的spinner
	 *
	 * @param context
	 * @param spinner
	 * @param type (Constants.Dic.ROOM, Constants.Dic.CURE_MODE,
			Constants.Dic.BLOOD_ACCESS, Constants.Dic.DIALYSIS_FILM,
			Constants.Dic.CLEANER_TYPE, Constants.Dic.CURE_WAY)
	 */
	public static void buildSpiByMedConfig(Context context,Spinner spinner,String type){
		ArrayAdapter<MedConfig> medConfigAdapter = new ArrayAdapter<MedConfig>(context, android.R.layout.simple_spinner_item, DataUtil.getMedConfigs(type));
		medConfigAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(medConfigAdapter);
		
		DataUtil.updateMedConfigs(type);
	}
	
	/**
	 * 构造spi
	 *
	 * @param context
	 * @param spinner
	 * @param type
	 * @param list
	 */
	public static <T> void buildSpiData(Context context,Spinner spinner,Class<T> type ,List<T> list){
		ArrayAdapter<T> arrayAdapter = new ArrayAdapter<T>(context, android.R.layout.simple_spinner_item, list);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(arrayAdapter);
	}

	
	/**
	 * 设置对应的值到view上<br>
	 * 说明：view的tag的设置(例如:TextView.fileName|RadioButton.fileName.1)
	 * 
	 * @param rootView
	 *            （视图root）
	 * @param object
	 *            (对应的对象)
	 */
	public static void setValueToView(View rootView, Object object) {
		if(object == null)
			throw new NullPointerException("object is null");
		
		if(rootView == null)
			return;
		
		if(rootView.getTag() == null){// 如果没有tag
			if (rootView instanceof ViewGroup && ((ViewGroup) rootView).getChildCount() > 0) {
				
				if(rootView instanceof RadioGroup){
					((RadioGroup) rootView).clearCheck();
				}
				
				ViewGroup vgp = (ViewGroup) rootView;
				// 如果为布局容器，则循环遍历子控件
				for (int i = 0; i < vgp.getChildCount(); i++) {
					setValueToView(vgp.getChildAt(i),object);
				}
			}
		}else{// 有tag
			String[] tagStrings = rootView.getTag().toString().split("\\.");
			Object value = ObjectUtils.getValueFromObject(tagStrings[1], object);
			
			if(tagStrings[0].equals("Spinner")){
				Spinner spi = (Spinner) rootView;
				// 因为目前app的spinner的数据源都是medconfig的集合，所以写在此处
				try {
					int count = spi.getCount();
					for (int i = 0; i < count; i++) {
						
						if(spi.getItemAtPosition(i) instanceof MedConfig && ((MedConfig) spi.getItemAtPosition(i)).getITEM_TYPE().equals("护士转抄医嘱") && ((MedConfig) spi.getItemAtPosition(i)).getITEM_NAME().equals(value))
						{
							spi.setSelection(i);// 设置选择项
							break;
						}
						else if(spi.getItemAtPosition(i) instanceof MedConfig && ((MedConfig) spi.getItemAtPosition(i)).getITEM_TYPE().equals("透析机") && ((MedConfig) spi.getItemAtPosition(i)).getITEM_NAME().equals(value))
						{
							spi.setSelection(i);// 设置选择项
							break;
						}
						else if(spi.getItemAtPosition(i) instanceof MedConfig && ((MedConfig) spi.getItemAtPosition(i)).getITEM_TYPE().equals("单位") && ((MedConfig) spi.getItemAtPosition(i)).getITEM_NAME().equals(value))
						{
							spi.setSelection(i);// 设置选择项
							break;
						}
						else if(spi.getItemAtPosition(i) instanceof MedConfig && ((MedConfig) spi.getItemAtPosition(i)).getITEM_TYPE().equals("灌流器") && ((MedConfig) spi.getItemAtPosition(i)).getITEM_NAME().equals(value))
						{
							spi.setSelection(i);// 设置选择项
							break;
						}
						else if(spi.getItemAtPosition(i) instanceof MedConfig && ((MedConfig) spi.getItemAtPosition(i)).getITEM_ID().equals(value)){
							spi.setSelection(i);// 设置选择项
							break;
						}
						
						if(spi.getItemAtPosition(i) instanceof MedNurse && ((MedNurse) spi.getItemAtPosition(i)).getEMP_NO().equals(value)){
							spi.setSelection(i);// 设置选择项
							break;
						}
						
						if(spi.getItemAtPosition(i) instanceof MedDoctor && ((MedDoctor) spi.getItemAtPosition(i)).getEMP_NO().equals(value)){
							spi.setSelection(i);// 设置选择项
							break;
						}
						
						if(spi.getItemAtPosition(i) instanceof MedVasularAccess && ((MedVasularAccess) spi.getItemAtPosition(i)).getVascular_Access_Id().equals(value)){
							spi.setSelection(i);// 设置选择项
							break;
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if (tagStrings[0].equals("RadioButton")) {
				RadioButton rbt = (RadioButton) rootView;
				rbt.setChecked(tagStrings[2].equals(value));
//				if(tagStrings[2].equals(value))
//				{
//					((RadioGroup)rbt.getParent()).check(rbt.getId());
//				}
			}else if (tagStrings[0].equals("TextView")) {
				TextView tv = (TextView) rootView;
				String result = (value == null||value.toString().equals("0.0")) ? "" : value.toString();
				if(result.indexOf(".")>0)
				{
					result = result.replaceAll("0+?$", "");
					result = result.replaceAll("[.]$", "");
				}
				tv.setText(result);
				if (tagStrings[1].equals("ufr") && value.toString().equals("0.0")) {
					tv.setText("0");
				}
				if (tagStrings[1].equals("dry_Water_Value") && (value.toString().equals("0.0")||value == null)) {
					tv.setText("0");
				}
			}
			else if (tagStrings[0].equals("CheckBox")) {
				CheckBox cb = (CheckBox) rootView;
				cb.setChecked(tagStrings[2].equals(value));
			}
		}

	}
	
	/**
	 * 从视图获取值，写入到对象中与setValueToView方法对应
	 * 
	 * @param rootView
	 * @param object
	 *            （不为null）
	 */
	public static void getValueFromView(View rootView, Object object){
		if(object == null)
			throw new NullPointerException("object is null");
		
		if(rootView == null)
			return;
		
		if(rootView.getTag() == null){// 如果没有tag
			if (rootView instanceof ViewGroup && ((ViewGroup) rootView).getChildCount() > 0) {
				ViewGroup vgp = (ViewGroup) rootView;
				// 如果为布局容器，则循环遍历子控件
				for (int i = 0; i < vgp.getChildCount(); i++) {
					getValueFromView(vgp.getChildAt(i),object);
				}
			}
		}else{// 有tag
			String[] tagStrings = rootView.getTag().toString().split("\\.");
			
			if(tagStrings[0].equals("Spinner")){
				Spinner spi = (Spinner) rootView;
				// 因为目前app的spinner的数据源都是medconfig的集合，所以写在此处
				try {
					if(spi.getSelectedItem() instanceof MedConfig && (((MedConfig)spi.getSelectedItem()).getITEM_TYPE().equals("护士转抄医嘱") || ((MedConfig)spi.getSelectedItem()).getITEM_TYPE().equals("透析机")|| ((MedConfig)spi.getSelectedItem()).getITEM_TYPE().equals("单位")|| ((MedConfig)spi.getSelectedItem()).getITEM_TYPE().equals("灌流器"))){
						MedConfig medConfig =  (MedConfig) spi.getSelectedItem();
						ObjectUtils.setValueToObject(object, medConfig.getITEM_NAME(), tagStrings[1]);
					}
					else if(spi.getSelectedItem() instanceof MedConfig){
						MedConfig medConfig =  (MedConfig) spi.getSelectedItem();
						ObjectUtils.setValueToObject(object, medConfig.getITEM_ID(), tagStrings[1]);
					}
					
					if(spi.getSelectedItem() instanceof MedNurse){
						MedNurse medNurse =  (MedNurse) spi.getSelectedItem();
						ObjectUtils.setValueToObject(object, medNurse.getEMP_NO(), tagStrings[1]);
					}
					
					if(spi.getSelectedItem() instanceof MedDoctor){
						MedDoctor medDoctor =  (MedDoctor) spi.getSelectedItem();
						ObjectUtils.setValueToObject(object, medDoctor.getEMP_NO(), tagStrings[1]);
					}
					
					if(spi.getSelectedItem() instanceof MedVasularAccess){
						MedVasularAccess medAccess =  (MedVasularAccess) spi.getSelectedItem();
						ObjectUtils.setValueToObject(object, medAccess.getVascular_Access_Id(), tagStrings[1]);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if (tagStrings[0].equals("RadioButton")) {
				RadioButton rbt = (RadioButton) rootView;
				if(rbt.isChecked())
					ObjectUtils.setValueToObject(object, tagStrings[2], tagStrings[1]);
			}else if (tagStrings[0].equals("TextView")) {
				TextView tv = (TextView) rootView;
				ObjectUtils.setValueToObject(object, tv.getText().toString(), tagStrings[1]);
			}
			else if (tagStrings[0].equals("CheckBox")) {
				CheckBox cb = (CheckBox) rootView;
				/*String value = "0";
				if(cb.isChecked())
				{
					value="1";
				}*/
				if(cb.isChecked()) {
					ObjectUtils.setValueToObject(object, tagStrings[2], tagStrings[1]);
				}
			}
		}
	}
}

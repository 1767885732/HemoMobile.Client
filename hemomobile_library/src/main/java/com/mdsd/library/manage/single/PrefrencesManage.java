package com.mdsd.library.manage.single;

import com.alibaba.fastjson.JSON;
import com.mdsd.library.utils.log.Log;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences 管理单例<br>
 * 使用前请在application中初始化
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年7月10日
 */
public class PrefrencesManage {

	private Context context;
	private String name = "mdsd_prefrences";
	private int mode = Context.MODE_PRIVATE;

	private static class PrefrencesManageHolder {
		static PrefrencesManage instance = new PrefrencesManage();
	}

	public static PrefrencesManage getInstance() {
		return PrefrencesManageHolder.instance;
	}

	public void init(Context context) {
		this.context = context;
	}

	public void init(Context context, String name) {
		this.context = context;
		this.name = name;
	}

	public void init(Context context, String name, int mode) {
		this.context = context;
		this.name = name;
		this.mode = mode;
	}

	public SharedPreferences getSharedPreferences(String name, int mode) {
		return context.getSharedPreferences(name, mode);
	}

	public SharedPreferences getSharedPreferences(String name) {
		return context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	public SharedPreferences getSharedPreferences() {
		return context.getSharedPreferences(this.name, this.mode);
	}
	
	
	public Editor getEditor(String name, int mode) {
		return context.getSharedPreferences(name, mode).edit();
	}
	
	public Editor getEditor(String name) {
		return context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
	}
	
	public Editor getEditor() {
		return context.getSharedPreferences(name, mode).edit();
	}
	
	// ----------------------
	// 提供给外部调用的快捷方法
	// ----------------------
	
	/**
	 * 保存key对应的值
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean storeValue(String key , String value){
		Editor editor = getEditor();	
		editor.putString(key,value);		
		return editor.commit();
	}
	
	/**
	 * 保存key对应的布尔值
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean storeValue(String key , boolean value){
		Editor editor = getEditor();	
		editor.putBoolean(key,value);		
		return editor.commit();
	}
	
	/**
	 * 保存key对应的长整形
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean storeValue(String key , long value){
		Editor editor = getEditor();	
		editor.putLong(key,value);		
		return editor.commit();
	}
	
	/**
	 * 保存对应的浮点型
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean storeValue(String key , float value){
		Editor editor = getEditor();	
		editor.putFloat(key,value);		
		return editor.commit();
	}
	
	/**
	 * 保存对应的整形
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean storeValue(String key , int value){
		Editor editor = getEditor();	
		editor.putInt(key,value);		
		return editor.commit();
	}
	
	/**
	 * 保存一个实体对象值
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean  storeValue(String key, Object value){
		String jsonStr = JSON.toJSONString(value);
		return storeValue(key, jsonStr);
	}
	
	/**
	 * 获取key对应的布尔值
	 * @param context
	 * @param key          键值
	 * @param defValue     默认值 
	 * @return
	 */
	public boolean getBooleanValue(String key, boolean defValue){
		return getSharedPreferences().getBoolean(key, defValue);
	}
	
	/**
	 * 获取key对应的字符串值
	 * @param context
	 * @param key       键值
	 * @param defValue  默认值 
	 * @return 
	 */
	public String getStringValue(String key, String defValue){
		return getSharedPreferences().getString(key, defValue);
	}
	
	/**
	 * 获取key对应的长整形
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public long getLongValue(String key, long defValue){
		return getSharedPreferences().getLong(key, defValue);
	}
	
	/**
	 * 获取key对应的浮点型
	 *
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public float getFloatValue(Context context,String key, float defValue){
		return getSharedPreferences().getFloat(key, defValue);
	}
	
	/**
	 * 获取key对应的整形
	 *
	 * @param key
	 * @param defValue
	 * @return
	 */
	public int getIntValue(String key, int defValue){
		return getSharedPreferences().getInt(key, defValue);
	} 
	
	/**
	 * 获取对应的key对象的值
	 *
	 * @param key
	 * @param type
	 * @return
	 */
	public <T> T getObjValue(String key,Class<T> type){ 
		String jsonStr = getStringValue(key, "");
		try {
			return JSON.parseObject(jsonStr, type);
		} catch (Exception e) {
			Log.e("PrefrencesManage", e.getMessage());
		}
		
		return null;
	}
}

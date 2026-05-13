package com.mdsd.library.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.net.ParseException;

import com.mdsd.library.utils.log.Log;

/**
 * org.json 的转换
 * 
 * <br>
 * @author jianyu.l
 * @since 2014年8月28日
 */
@SuppressLint("UseValueOf")
@SuppressWarnings({"unchecked", "rawtypes" })
public class JsonParseUtil {
	
	/**
	 * 转换object to map
	 *
	 * @param o
	 * @return
	 * @throws JSONException
	 */
	public static Map<String, String> toMap(Object o) throws JSONException 
    { 
        return toMap(toJsonObject(o)); 
    } 
	
	/**
	 * 转换JSONObject 为map
	 *
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	public static Map<String, String> toMap(JSONObject jsonObject) throws JSONException{
		Map<String, String> result = new HashMap<String, String>(); 
        Iterator<String> iterator = jsonObject.keys(); 
        String key = null; 
        String value = null; 
        while (iterator.hasNext()) 
        { 
            key = iterator.next(); 
            value = jsonObject.getString(key); 
            result.put(key, value); 
        } 
        return result; 
	}
	
	
	/**
	 * 转换list为jsonArray
	 *
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static JSONArray toJSONArray(List list) throws Exception{  
        JSONArray array = null;  
        if(list.isEmpty()){  
            return array;  
        }  
        array = new JSONArray();  
        for(Object o : list){  
            array.put(toJsonObject(o));  
        }  
        return array;  
    }  
	
	
	/** 
     * JSONObject对象转JavaBean 
     * @param JSONObject 
     * @param JavaBean的class 
     * @return 转换结果（异常情况下返回null） 
     */  
    public static Object toObjectFromJsonObject(JSONObject json, Class cls)  
    {  
        Object obj = null;  
          
        try  
        {  
            obj = cls.newInstance();  
              
            // 取出Bean里面的所有方法  
            Method[] methods = cls.getMethods();  
            for(int i=0; i < methods.length; i++)  
            {  
                // 取出方法名  
                String methodName = methods[i].getName();  
                // 取出方法的类型  
                Class[] clss = methods[i].getParameterTypes();  
                if(clss.length != 1)  
                {  
                    continue;  
                }  
                  
                // 若是方法名不是以set开始的则退出本次循环  
                if(methodName.indexOf("set") < 0)  
                {  
                    continue;  
                }  
                  
                // 类型  
                String type = clss[0].getSimpleName();  
                  
                String key = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);  
                  
                // 如果map里有该key  
                if(json.has(key) && json.get(key) != null)  
                {  
                    setValue(type, json.get(key), methods[i], obj);  
                }  
            }  
        } catch (Exception ex)  
        {  
        	Log.e("JSONObject转JavaBean失败");  
        }  
          
        return obj;  
    }  
	

	/**
	 * javabean 转换成jsonobject
	 *
	 * @param o
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject toJsonObject(Object o) throws JSONException{
		JSONObject json = new JSONObject();
		Class clazz = o.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			json.put(f.getName(), invokeMethod(clazz, f.getName(), o));
		}
		return json;
	}
	
	
	// ------------------------
	// private method
	// ------------------------
	
	/** 
     * 给JavaBean的每个属性设值 
     * @param type 
     * @param value 
     * @param method 
     * @param bean 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws InvocationTargetException 
     * @throws ParseException 
     */  
    private static void setValue(String type, Object value, Method method, Object bean)  
    {  
        if(value != null && !"".equals(value))  
        {  
            try  
            {  
                if("String".equals(type))  
                {  
                    method.invoke(bean, new Object[] {value});  
                } else if("int".equals(type) || "Integer".equals(type))  
                {  
                    method.invoke(bean, new Object[] { new Integer(""+value) });  
                } else if("double".equals(type) || "Double".equals(type))  
                {  
                    method.invoke(bean, new Object[] { new Double(""+value) });  
                } else if("float".equals(type) || "Float".equals(type))  
                {  
                    method.invoke(bean, new Object[] { new Float(""+value) });  
                } else if("long".equals(type) || "Long".equals(type))  
                {  
                    method.invoke(bean, new Object[] { new Long(""+value) });  
                } else if("int".equals(type) || "Integer".equals(type))  
                {  
                    method.invoke(bean, new Object[] { new Integer(""+value) });  
                } else if("boolean".equals(type) || "Boolean".equals(type))  
                {  
                    method.invoke(bean, new Object[] { Boolean.valueOf(""+value) });  
                } else if("BigDecimal".equals(type))  
                {  
                    method.invoke(bean, new Object[] { new BigDecimal(""+value) });  
                } else if("Date".equals(type))  
                {  
                    Class dateType = method.getParameterTypes()[0];  
                    if("java.util.Date".equals(dateType.getName()))  
                    {  
                        java.util.Date date = null;  
                        if("String".equals(value.getClass().getSimpleName()))  
                        {  
                            String time = String.valueOf(value);  
                            String format = null;  
                            if(time.indexOf(":") > 0)  
                            {  
                                if(time.indexOf(":") == time.lastIndexOf(":"))  
                                {  
                                    format = "yyyy-MM-dd H:mm";  
                                }  
                                else  
                                {  
                                    format = "yyyy-MM-dd H:mm:ss";  
                                }  
                            }  
                            else  
                            {  
                                format = "yyyy-MM-dd";  
                            }  
                            SimpleDateFormat sf = new SimpleDateFormat();  
                            sf.applyPattern(format);  
                            date = sf.parse(time);  
                        }  
                        else  
                        {  
                            date = (java.util.Date) value;  
                        }  
                          
                        if(date != null)  
                        {  
                            method.invoke(bean, new Object[] { date });  
                        }  
                    }  
                    else if("java.sql.Date".equals(dateType.getName()))  
                    {  
                        Date date = null;  
                        if("String".equals(value.getClass().getSimpleName()))  
                        {  
                            String time = String.valueOf(value);  
                            String format = null;  
                            if(time.indexOf(":") > 0)  
                            {  
                                if(time.indexOf(":") == time.lastIndexOf(":"))  
                                {  
                                    format = "yyyy-MM-dd H:mm";  
                                }  
                                else  
                                {  
                                    format = "yyyy-MM-dd H:mm:ss";  
                                }  
                            }  
                            else  
                            {  
                                format = "yyyy-MM-dd";  
                            }  
                            SimpleDateFormat sf = new SimpleDateFormat();  
                            sf.applyPattern(format);  
                            date = new Date(sf.parse(time).getTime());  
                        }  
                        else  
                        {  
                            date = (Date) value;  
                        }  
                          
                        if(date != null)  
                        {  
                            method.invoke(bean, new Object[] { date });  
                        }  
                    }  
                } else if("Timestamp".equals(type))  
                {  
                    Timestamp timestamp = null;  
                    if("String".equals(value.getClass().getSimpleName()))  
                    {  
                        String time = String.valueOf(value);  
                        String format = null;  
                        if(time.indexOf(":") > 0)  
                        {  
                            if(time.indexOf(":") == time.lastIndexOf(":"))  
                            {  
                                format = "yyyy-MM-dd H:mm";  
                            }  
                            else  
                            {  
                                format = "yyyy-MM-dd H:mm:ss";  
                            }  
                        }  
                        else  
                        {  
                            format = "yyyy-MM-dd";  
                        }  
                        SimpleDateFormat sf = new SimpleDateFormat();  
                        sf.applyPattern(format);  
                        timestamp = new Timestamp(sf.parse(time).getTime());  
                    }  
                    else  
                    {  
                        timestamp = (Timestamp) value;  
                    }  
                      
                    if(timestamp != null)  
                    {  
                        method.invoke(bean, new Object[] { timestamp });  
                    }  
                } else if("byte[]".equals(type))  
                {  
                    method.invoke(bean, new Object[] { new String(""+value).getBytes() });  
                }  
            } catch(Exception ex)  
            {  
            	Log.e("JSONObject赋值给JavaBean失败");  
            }  
        }  
    }  
	
	/**
	 * 获取对应方法的值
	 *
	 * @param c
	 * @param fieldName
	 * @param o
	 * @return
	 */
	private static Object invokeMethod(Class c, String fieldName, Object o) {
		String methodName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Method method = null;
		try {
			method = c.getMethod("get" + methodName);
			return method.invoke(o);
		}catch (NoSuchMethodException e) {
			Log.d(e.getMessage());
		} catch (Exception e) {
			Log.e(e.getMessage());
		}
		
		return "";
	}
}

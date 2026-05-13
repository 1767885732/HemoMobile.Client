package com.mdsd.library.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mdsd.library.utils.log.Log;

/**
 * Object Utils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-10-24
 */
public class ObjectUtils {

    /**
     * compare two object
     * 
     * @param actual
     * @param expected
     * @return <ul>
     *         <li>if both are null, return true</li>
     *         <li>return actual.{@link Object#equals(Object)}</li>
     *         </ul>
     */
    public static boolean isEquals(Object actual, Object expected) {
        return actual == expected || (actual == null ? expected == null : actual.equals(expected));
    }

    /**
     * convert long array to Long array
     * 
     * @param source
     * @return
     */
    public static Long[] transformLongArray(long[] source) {
        Long[] destin = new Long[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert Long array to long array
     * 
     * @param source
     * @return
     */
    public static long[] transformLongArray(Long[] source) {
        long[] destin = new long[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert int array to Integer array
     * 
     * @param source
     * @return
     */
    public static Integer[] transformIntArray(int[] source) {
        Integer[] destin = new Integer[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * convert Integer array to int array
     * 
     * @param source
     * @return
     */
    public static int[] transformIntArray(Integer[] source) {
        int[] destin = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            destin[i] = source[i];
        }
        return destin;
    }

    /**
     * compare two object
     * <ul>
     * <strong>About result</strong>
     * <li>if v1 > v2, return 1</li>
     * <li>if v1 = v2, return 0</li>
     * <li>if v1 < v2, return -1</li>
     * </ul>
     * <ul>
     * <strong>About rule</strong>
     * <li>if v1 is null, v2 is null, then return 0</li>
     * <li>if v1 is null, v2 is not null, then return -1</li>
     * <li>if v1 is not null, v2 is null, then return 1</li>
     * <li>return v1.{@link Comparable#compareTo(Object)}</li>
     * </ul>
     * 
     * @param v1
     * @param v2
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <V> int compare(V v1, V v2) {
        return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1 : ((Comparable)v1).compareTo(v2));
    }
    
    
    
	/**
	 * 获取对象中获取get+field方法的值
	 * 
	 * 
	 * @param c
	 * @param fieldName
	 * @param o
	 * @return
	 */
	public static Object getValueFromObject(String fieldName, Object o) {
		String methodName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Method method = null;
		try {
			method = o.getClass().getMethod("get" + methodName);
			return method.invoke(o);
		} catch (NoSuchMethodException e) {
			Log.d(e.getMessage());
		} catch (Exception e) {
			Log.e(e.getMessage());
		}

		return "";
	}
	
	/**
	 * 为对象的setfield设置值
	 *
	 * @param obj
	 * @param value
	 * @param field
	 */
	@SuppressWarnings("rawtypes")
	public static void setValueToObject(Object obj, Object value, String field) {
		if (obj == null || value == null)
			return;

		// 取出Bean里面的所有方法
		Method[] methods = obj.getClass().getMethods();
		String methodName = "set" + field.substring(0, 1).toUpperCase()
				+ field.substring(1);
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(methodName)) {
				try {
					// 获取参数类型
					Method method = methods[i];
					String type = method.getParameterTypes()[0].getSimpleName();
					if ("String".equals(type)) {
						method.invoke(obj, value.toString());
					} else if ("int".equals(type) || "Integer".equals(type)) {
						method.invoke(obj, Integer.parseInt(value.toString().equals("")?"0":value.toString()));
					} else if ("double".equals(type) || "Double".equals(type)) {
						method.invoke(obj, Double.parseDouble(value.toString().equals("")?"0":value.toString()));
					} else if ("float".equals(type) || "Float".equals(type)) {
						method.invoke(obj, Float.parseFloat(value.toString().equals("")?"0":value.toString()));
					} else if ("long".equals(type) || "Long".equals(type)) {
						method.invoke(obj, Long.parseLong(value.toString().equals("")?"0":value.toString()));
					} else if ("boolean".equals(type) || "Boolean".equals(type)) {
						method.invoke(obj,
								Boolean.parseBoolean(value.toString().equals("")?"false":value.toString()));
					} else if ("BigDecimal".equals(type)) {
						method.invoke(obj, BigDecimal.valueOf(Double
								.parseDouble(value.toString().equals("")?"0":value.toString())));
					} else if ("Date".equals(type)) {
						Class dateType = method.getParameterTypes()[0];
						if ("java.util.Date".equals(dateType.getName())) {
							java.util.Date date = null;
							if ("String".equals(value.getClass()
									.getSimpleName())) {
								String time = String.valueOf(value);
								String format = null;
								if (time.indexOf(":") > 0) {
									if (time.indexOf(":") == time
											.lastIndexOf(":")) {
										format = "yyyy-MM-dd H:mm";
									} else {
										format = "yyyy-MM-dd H:mm:ss";
									}
								} else {
									format = "yyyy-MM-dd";
								}
								SimpleDateFormat sf = new SimpleDateFormat();
								sf.applyPattern(format);
								date = sf.parse(time);
							} else {
								date = (java.util.Date) value;
							}

							if (date != null) {
								method.invoke(obj, new Object[] { date });
							}
						} else if ("java.sql.Date".equals(dateType.getName())) {
							Date date = null;
							if ("String".equals(value.getClass()
									.getSimpleName())) {
								String time = String.valueOf(value);
								String format = null;
								if (time.indexOf(":") > 0) {
									if (time.indexOf(":") == time
											.lastIndexOf(":")) {
										format = "yyyy-MM-dd H:mm";
									} else {
										format = "yyyy-MM-dd H:mm:ss";
									}
								} else {
									format = "yyyy-MM-dd";
								}
								SimpleDateFormat sf = new SimpleDateFormat();
								sf.applyPattern(format);
								date = new Date(sf.parse(time).getTime());
							} else {
								date = (Date) value;
							}

							if (date != null) {
								method.invoke(obj, date);
							}
						}
					} else if ("Timestamp".equals(type)) {
						Timestamp timestamp = null;
						if ("String".equals(value.getClass().getSimpleName())) {
							String time = String.valueOf(value);
							String format = null;
							if (time.indexOf(":") > 0) {
								if (time.indexOf(":") == time.lastIndexOf(":")) {
									format = "yyyy-MM-dd H:mm";
								} else {
									format = "yyyy-MM-dd H:mm:ss";
								}
							} else {
								format = "yyyy-MM-dd";
							}
							SimpleDateFormat sf = new SimpleDateFormat();
							sf.applyPattern(format);
							timestamp = new Timestamp(sf.parse(time).getTime());
						} else {
							timestamp = (Timestamp) value;
						}

						if (timestamp != null) {
							method.invoke(obj, timestamp);
						}
					} else if ("byte[]".equals(type)) {
						method.invoke(obj,
								new Object[] { new String("" + value)
										.getBytes() });
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				break;
			}
		}
	}
}

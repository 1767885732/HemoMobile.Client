/**
 * Copyright (C) 2014 Jianyu.L
 * 
 * @version:v1.0.0 
 * @author:Jianyu.L (lijianyu2012@gmail.com)
 * 
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2014年10月23日     Jianyu.L      v1.0.0        create
 *
 *
 */
package com.mdsd.library.utils.log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.TextUtils;

/**
 * <p>
 * com.mdsd.library.utils.log.AppLog
 * </p>
 * <p>
 * 应用打印日志所需，支持打印日志到控制台和文件，可以清除文件目录，日志文件命名已日期命名
 * </p>
 * <p>使用说明：
 * <li>AppLog.isLog设置是否打印日志</li>
 * <li>AppLog.isLogToFile设置是否打印日志到文件</li>
 * <li>AppLog.logToFileLevel设置是打印什么级别的日志到文件（默认为error级别）</li>
 * <li>AppLog.setLogDirPath设置日志文件目录，设置目录之后如果成功则直接默认打印日志到文件</li>
 * </p>
 * 
 */
public class AppLog {
	
	/**
	 * 是否打印日志<br>
	 * 默认为true
	 */
	public static boolean isLog = true;
	
	
	/**
	 * 打印日志到文件<br>
	 * 默认为false
	 */
	private static boolean isLogToFile = false;
	
	
	/**
	 * 打印日志到文件的级别<br>
	 * 默认为{@link Level.ERROR}}
	 */
	public static Level logToFileLevel = Level.ERROR;
	
	/**
	 * 日期保存天数，默认为3天
	 */
	public static int saveDays = 3;
	

	/**
	 * 
	 * <p>com.mdsd.library.utils.log.TYPE</p>
	 * <p>日志的级别</p>
	 *
	 */
	public enum Level {
		INFO(1), DEBUG(2), VERBOSE(0), WARN(3), ERROR(4);
		
		public int lev = 0;
		
		Level(int lev){
			this.lev = lev;
		}
		
	}
	
	/**
	 * 日志文件目录
	 */
	private static String logDirPath = "";
	
	/**
	 * 当前日志文件路劲
	 */
	private static String logFilePath = "";
	
	/**
	 * 日期格式化（"yyyy-MM-dd"）
	 */
	private static SimpleDateFormat dtDf = new SimpleDateFormat("yyyy-MM-dd");   
	private static SimpleDateFormat timeDf = new SimpleDateFormat("HH:mm:ss");   
	
	
	/**
	 * Gets the 日志文件路径.
	 *
	 * @return the 日志文件路径
	 */
	public static String getLogDirPath() {
		return logDirPath;
	}

	/**
	 * Sets the 日志文件路径.
	 *
	 * @param logFilePath the new 日志文件路径
	 */
	public static void setLogDirPath(String logDirPath) {
		AppLog.logDirPath = logDirPath;
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(logDirPath);
		if (!logDirPath.endsWith(File.separator)) {
			buffer.append(File.separator);
		}
		buffer.append(getCurrDate());
		buffer.append(".log");
		
		isLogToFile = createLogDir(logFilePath = buffer.toString());
		
	}


	private static String TAG = "AppLog";
	
	
	 /** 
     * Log输出所在类 
     */  
    private static String className;  
    /** 
     * Log输出所在方法 
     */  
    private static String methodName;  
    /** 
     * Log输出所行号 
     */  
    private static int lineNumber;  
	
	
	/**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String msg) {
		if (isLog) {
			String logMsg = buildMessage(msg);
			android.util.Log.v(buildTag(), logMsg);
		}
		
		logToFile(Level.VERBOSE, buildMessage(msg), null);
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void v(String msg, Throwable thr) {
		if (isLog) {
			String logMsg = buildMessage(msg);
			android.util.Log.v(buildTag(), logMsg, thr);
		}
		logToFile(Level.VERBOSE, buildMessage(msg), thr);
    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    public static void d(String msg) {
		if (isLog) {
			String logMsg = buildMessage(msg);
			android.util.Log.d(buildTag(), logMsg);
		}
		logToFile(Level.DEBUG, buildMessage(msg), null);
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void d(String msg, Throwable thr) {
		if (isLog) {
			String logMsg = buildMessage(msg);
			android.util.Log.d(buildTag(), logMsg, thr);
		}
		logToFile(Level.DEBUG, buildMessage(msg), thr);
    }

    /**
     * Send an INFO log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String msg) {
		if (isLog) {
			String logMsg = buildMessage(msg);
			android.util.Log.i(buildTag(), logMsg);
		}
		logToFile(Level.INFO, buildMessage(msg), null);
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void i(String msg, Throwable thr) {
		if (isLog) {
			String logMsg = buildMessage(msg);
			android.util.Log.i(buildTag(), logMsg, thr);
		}
		logToFile(Level.INFO, buildMessage(msg), thr);
    }


    /**
     * Send a WARN log message
     *
     * @param msg The message you would like logged.
     */
    public static void w(String msg) {
		if (isLog) {
			String logMsg = buildMessage(msg);
			android.util.Log.w(buildTag(), logMsg);
		}
		logToFile(Level.WARN, buildMessage(msg), null);
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void w(String msg, Throwable thr) {
		if (isLog) {
			String logMsg = buildMessage(msg);
			android.util.Log.w(buildTag(), logMsg, thr);
		}
		logToFile(Level.WARN, buildMessage(msg), thr);
    }

    /**
     * Send an empty WARN log message and log the exception.
     *
     * @param thr An exception to log
     */
    public static void w(Throwable thr) {
		if (isLog) {
			android.util.Log.w(buildTag(), thr);
		}
		logToFile(Level.WARN, "",thr);
    }
    
    /**
     * Send an ERROR log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String msg) {
		if (isLog) {
			String logMsg = buildMessage(msg);
			android.util.Log.e(buildTag(), logMsg);
		}
		logToFile(Level.ERROR, buildMessage(msg), null);
    }
    
    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void e(String msg, Throwable thr) {
		if (isLog) {
			String logMsg = buildMessage(msg);
			android.util.Log.e(buildTag(), logMsg, thr);
		}
		logToFile(Level.ERROR, buildMessage(msg), thr);
    }
	
    /**
     * 清除日志文件
     */
    public static boolean clearLogs(){
    	 if (TextUtils.isEmpty(logDirPath)) {
    		 android.util.Log.e("Error", "The path of Log dir is not exist.");
             return false;
         }
    	 
    	 File dir = new File(logDirPath);
    	 
		if (!dir.exists()) {// 不存在
			return false;
		}
		if (!dir.isDirectory()) {// 不是目录
			return false;
		}
		
		// 删除目下的所有文件
		for (File fileChildren : dir.listFiles()) {
			if(fileChildren != null)
				fileChildren.delete();
		}
		
		return true;
    }
	
	// ------------------
	// private method
	// ------------------
    
    /**
     * 构建tag
     * @return
     */
    private static String buildTag(){
    	StringBuffer buffer = new StringBuffer(TAG);
    	buffer.append(" [");
    	buffer.append(className);
    	buffer.append("]");
    	
    	return buffer.toString();
    }
    
    /**
     * 打印日志到指定文件
     * 
     * @param lev 级别
     * @param msg 错误信息
     * @param thr
     */
    private static void logToFile(Level lev,String msg, Throwable thr){
    	if(!isLogToFile)
    		return;
    	
    	if(lev.lev >= logToFileLevel.lev){// 如果打印级别高于当前设置的级别则打印
    		
            StringBuffer bufferlog = new StringBuffer();
            bufferlog.append(getCurrTime());
            bufferlog.append("  ");
            bufferlog.append(lev.name());
            bufferlog.append(":");
            bufferlog.append(className);
            bufferlog.append("  ");
            bufferlog.append(msg);
			
            if (thr != null) {
                bufferlog.append(File.separator);
                bufferlog.append(android.util.Log.getStackTraceString(thr));
            }

            // 写入日志到指定文件
            Log2File.log2file(logFilePath, bufferlog.toString());
    	}
    }
    
    
	/**
     * Building Message
     *
     * @param msg The message you would like logged.
     * @return Message String
     */
    private static String buildMessage(String msg) {
//		StackTraceElement caller = new Throwable().fillInStackTrace()
//				.getStackTrace()[2];

		getMethodNames(new Throwable().fillInStackTrace().getStackTrace());
    	
		StringBuffer buffer = new StringBuffer();  
        buffer.append("[");  
        buffer.append(methodName);  
        buffer.append("()");  
        buffer.append(" line:");  
        buffer.append(lineNumber);  
        buffer.append("] ");  
        buffer.append(msg);  
		
//		return new StringBuilder().append("行号:").append(caller.getLineNumber())
//				.append("  ").append(caller.getClassName()).append(".")
//				.append(caller.getMethodName()).append("(): ").append(msg)
//				.toString();
        
        return buffer.toString();
	}
    
    
    /**
     * create the Directory from the path
     *
     * @param path
     */
    private static boolean createLogDir(String path) {
        if (TextUtils.isEmpty(path)) {
            android.util.Log.e(TAG, "The path is not valid.");
            return false;
        }

        File file = new File(path);

        boolean ret;
        boolean exist;

        // 父目录
        exist = file.getParentFile().exists();
        if (!exist) {
            ret = file.getParentFile().mkdirs();

            if (!ret) {
                android.util.Log.e(TAG, "The Log Dir can not be created!");
                return false;
            }
            android.util.Log.i(TAG, "The Log Dir was successfully created! -" + file.getParent());
        }
        
        // 删除旧文件
        removeOldLog(file.getParentFile());
        
        return true;
    }
    
    /**
     * 获取当前日期
     * @return
     */
    private static String getCurrDate(){   
        Date dt = new Date();   
        return dtDf.format(dt);   
    } 
    
    /**
     * 获取当前时间
     * @return
     */
    private static String getCurrTime(){   
        Date dt = new Date();   
        return timeDf.format(dt);   
    } 
    
    /**
     * 删除旧的日志文件
     */
    private static void removeOldLog(File dir){
    	for (File fl : dir.listFiles()) {
    		String flName = fl.getName();
    		
    		try {
				if(diffDate(new Date(), dtDf.parse(flName.split("\\.")[0])) >= saveDays){
					fl.delete();
				}
			} catch (Exception e) {
				  android.util.Log.e(TAG, e.getMessage());
			}
		}
    }
	
    /**
	 * 日期相减
	 * 
	 * @param date
	 *            Date 日期
	 * @param date1
	 *            Date 日期
	 * @return 返回相减后的日期
	 */
    private static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}
    
    /**
	 * 返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
    private static long getMillis(Date date) {
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}
    
    
    /** 
     * 取得输出所在位置的信息 className methodName lineNumber 
     *  
     * @param sElements 
     */  
    private static void getMethodNames(StackTraceElement[] sElements)  
    {  
        className = sElements[2].getFileName().split("\\.")[0];  
        methodName = sElements[2].getMethodName();  
        lineNumber = sElements[2].getLineNumber();  
    } 
    
    
}

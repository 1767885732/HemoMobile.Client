package com.mdsd.docare.hemodialysis.app.entity.app;

import java.io.Serializable;

/**
 * 患者列表搜索类
 * 
 * <br>
 * .
 * 
 * @author jianyu.l
 * @since 2014年8月18日
 */
public class PatientSearchParam implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 时间段的类型. */
	private String timeRangeType;

	/** 日期. */
	private String date;

	/** 透析室对应的id. */
	private String roomId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * Gets the 时间段的类型.
	 * 
	 * @return the 时间段的类型
	 */
	public String getTimeRangeType() {
		return timeRangeType;
	}

	/**
	 * Sets the 时间段的类型.
	 * 
	 * @param timeRangeType
	 *            the new 时间段的类型
	 */
	public void setTimeRangeType(String timeRangeType) {
		this.timeRangeType = timeRangeType;
	}

	/**
	 * Gets the 日期.
	 * 
	 * @return the 日期
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the 日期.
	 * 
	 * @param date
	 *            the new 日期
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets the 透析室对应的id.
	 * 
	 * @return the 透析室对应的id
	 */
	public String getRoomId() {
		return roomId;
	}

	/**
	 * Sets the 透析室对应的id.
	 * 
	 * @param roomId
	 *            the new 透析室对应的id
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	/**
	 * Gets the 用户id.
	 * 
	 * @return the 用户id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the 用户id.
	 * 
	 * @param userId
	 *            the new 用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}

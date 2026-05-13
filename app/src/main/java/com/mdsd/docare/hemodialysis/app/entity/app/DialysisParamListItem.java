package com.mdsd.docare.hemodialysis.app.entity.app;

/**
 * 透析参数列表item类
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月20日
 */
public class DialysisParamListItem {

	public DialysisParamListItem() {

	}

	public DialysisParamListItem(String cureId, String recordTime, String ssy,
			String szy, String jmy, String kmy, String mb) {
		this.cureId = cureId;
		this.recordTime = recordTime;
		this.ssy = ssy;
		this.szy = szy;
		this.jmy = jmy;
		this.kmy = kmy;
		this.mb = mb;
	}

	private String cureId;

	/** 记录时间. */
	private String recordTime;

	/** 收缩压. */
	private String ssy;

	/** 舒张压. */
	private String szy;
	
	/** 静脉压. */
	private String jmy;
	
	/** 跨膜压. */
	private String kmy;

	/** 脉搏. */
	private String mb;

	/**
	 * Gets the 记录时间.
	 * 
	 * @return the 记录时间
	 */
	public String getRecordTime() {
		return recordTime;
	}

	/**
	 * Sets the 记录时间.
	 * 
	 * @param recordTime
	 *            the new 记录时间
	 */
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	/**
	 * Gets the 收缩压.
	 * 
	 * @return the 收缩压
	 */
	public String getSsy() {
		return ssy;
	}

	/**
	 * Sets the 收缩压.
	 * 
	 * @param ssy
	 *            the new 收缩压
	 */
	public void setSsy(String ssy) {
		this.ssy = ssy;
	}

	/**
	 * Gets the 舒张压.
	 * 
	 * @return the 舒张压
	 */
	public String getSzy() {
		return szy;
	}

	/**
	 * Sets the 舒张压.
	 * 
	 * @param szy
	 *            the new 舒张压
	 */
	public void setSzy(String szy) {
		this.szy = szy;
	}
	
	

	public String getJmy() {
		return jmy;
	}

	public void setJmy(String jmy) {
		this.jmy = jmy;
	}

	public String getKmy() {
		return kmy;
	}

	public void setKmy(String kmy) {
		this.kmy = kmy;
	}

	/**
	 * Gets the 脉搏.
	 * 
	 * @return the 脉搏
	 */
	public String getMb() {
		return mb;
	}

	/**
	 * Sets the 脉搏.
	 * 
	 * @param mb
	 *            the new 脉搏
	 */
	public void setMb(String mb) {
		this.mb = mb;
	}

	public String getCureId() {
		return cureId;
	}

	public void setCureId(String cureId) {
		this.cureId = cureId;
	}

}

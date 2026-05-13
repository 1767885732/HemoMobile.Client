package com.mdsd.docare.hemodialysis.app.entity.server;

/**
 * MedUser 实体
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月21日
 */
public class MedUser {

	private String USER_ID;
	private String LOGIN_NAME;
	private String LOGIN_PWD;
	private String USER_NAME;
	private String IS_VALID;
	private String CREATED_DATE;
	private String DEPT_ID;
	private String MEMO;
	private String EMP_NO;

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getLOGIN_NAME() {
		return LOGIN_NAME;
	}

	public void setLOGIN_NAME(String lOGIN_NAME) {
		LOGIN_NAME = lOGIN_NAME;
	}

	public String getLOGIN_PWD() {
		return LOGIN_PWD;
	}

	public void setLOGIN_PWD(String lOGIN_PWD) {
		LOGIN_PWD = lOGIN_PWD;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public String getIS_VALID() {
		return IS_VALID;
	}

	public void setIS_VALID(String iS_VALID) {
		IS_VALID = iS_VALID;
	}

	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public String getDEPT_ID() {
		return DEPT_ID;
	}

	public void setDEPT_ID(String dEPT_ID) {
		DEPT_ID = dEPT_ID;
	}

	public String getMEMO() {
		return MEMO;
	}

	public void setMEMO(String mEMO) {
		MEMO = mEMO;
	}

	public String getEMP_NO() {
		return EMP_NO;
	}

	public void setEMP_NO(String eMP_NO) {
		EMP_NO = eMP_NO;
	}

}

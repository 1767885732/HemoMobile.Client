package com.mdsd.docare.hemodialysis.app.entity.server;

import java.io.Serializable;

public class MedPatientSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	private String PATIENT_SCHEDULE_ID;

	private String PATIENT_ID;
	
	private String PAT_PIC;//头像

	private String PATIENTNAME;

	private String SEX;

	private String MONITOR_LABEL;

	private String DIALYSIS_DATE;

	private String BANCI_ID;

	private String DIALYSIS_ROOM_ID;

	private String BED_NUMBER;

	private String BEDNAME;

	private String START_TIME;

	private String END_TIME;

	private String STATUS;

	private String HEMODIALYSIS_ID;

	private String REMARK;

	private String RECIPE_ID;

	private String PURIFIER_MODEL_ID;

	private String USER_ID;

	private String FOCUS_LEVEL;

	private String MACHINE_NAME;

	private String MODELNAME;

	private String AREANAME;
	
	private String INFECTIOUS_CHECK_RESULT;
	
	private String IS_CRRT;

	/**
	 * 治疗项目
	 */
	private String PURIFICATION_MODE;
	/**
	 * 净化器
	 */
	private String MODEL_NAME;
	/**
	 * 预定时间
	 */
	private String FREQUENCY_HOURS;
	
	private String CHECK_NUM;
	
	private String CHECK_DATE;

	public String getPATIENT_SCHEDULE_ID() {
		return PATIENT_SCHEDULE_ID;
	}

	public void setPATIENT_SCHEDULE_ID(String pATIENT_SCHEDULE_ID) {
		PATIENT_SCHEDULE_ID = pATIENT_SCHEDULE_ID;
	}

	public String getPATIENT_ID() {
		return PATIENT_ID;
	}

	public void setPATIENT_ID(String pATIENT_ID) {
		PATIENT_ID = pATIENT_ID;
	}
	

	public String getPAT_PIC() {
		return PAT_PIC;
	}

	public void setPAT_PIC(String pAT_PIC) {
		PAT_PIC = pAT_PIC;
	}

	public String getPATIENTNAME() {
		return PATIENTNAME;
	}

	public void setPATIENTNAME(String pATIENTNAME) {
		PATIENTNAME = pATIENTNAME;
	}

	public String getSEX() {
		return SEX;
	}

	public void setSEX(String sEX) {
		SEX = sEX;
	}

	public String getMONITOR_LABEL() {
		return MONITOR_LABEL;
	}

	public void setMONITOR_LABEL(String mONITOR_LABEL) {
		MONITOR_LABEL = mONITOR_LABEL;
	}

	public String getDIALYSIS_DATE() {
		return DIALYSIS_DATE;
	}

	public void setDIALYSIS_DATE(String dIALYSIS_DATE) {
		DIALYSIS_DATE = dIALYSIS_DATE;
	}

	public String getBANCI_ID() {
		return BANCI_ID;
	}

	public void setBANCI_ID(String bANCI_ID) {
		BANCI_ID = bANCI_ID;
	}

	public String getDIALYSIS_ROOM_ID() {
		return DIALYSIS_ROOM_ID;
	}

	public void setDIALYSIS_ROOM_ID(String dIALYSIS_ROOM_ID) {
		DIALYSIS_ROOM_ID = dIALYSIS_ROOM_ID;
	}

	public String getBED_NUMBER() {
		return BED_NUMBER;
	}

	public void setBED_NUMBER(String bED_NUMBER) {
		BED_NUMBER = bED_NUMBER;
	}

	public String getBEDNAME() {
		return BEDNAME;
	}

	public void setBEDNAME(String bEDNAME) {
		BEDNAME = bEDNAME;
	}

	public String getSTART_TIME() {
		return START_TIME;
	}

	public void setSTART_TIME(String sTART_TIME) {
		START_TIME = sTART_TIME;
	}

	public String getEND_TIME() {
		return END_TIME;
	}

	public void setEND_TIME(String eND_TIME) {
		END_TIME = eND_TIME;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getHEMODIALYSIS_ID() {
		return HEMODIALYSIS_ID;
	}

	public void setHEMODIALYSIS_ID(String hEMODIALYSIS_ID) {
		HEMODIALYSIS_ID = hEMODIALYSIS_ID;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}

	public String getRECIPE_ID() {
		return RECIPE_ID;
	}

	public void setRECIPE_ID(String rECIPE_ID) {
		RECIPE_ID = rECIPE_ID;
	}

	public String getPURIFIER_MODEL_ID() {
		return PURIFIER_MODEL_ID;
	}

	public void setPURIFIER_MODEL_ID(String pURIFIER_MODEL_ID) {
		PURIFIER_MODEL_ID = pURIFIER_MODEL_ID;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getFOCUS_LEVEL() {
		return FOCUS_LEVEL;
	}

	public void setFOCUS_LEVEL(String fOCUS_LEVEL) {
		FOCUS_LEVEL = fOCUS_LEVEL;
	}

	public String getMACHINE_NAME() {
		return MACHINE_NAME;
	}

	public void setMACHINE_NAME(String mACHINE_NAME) {
		MACHINE_NAME = mACHINE_NAME;
	}

	public String getMODELNAME() {
		return MODELNAME;
	}

	public void setMODELNAME(String mODELNAME) {
		MODELNAME = mODELNAME;
	}

	public String getAREANAME() {
		return AREANAME;
	}

	public void setAREANAME(String aREANAME) {
		AREANAME = aREANAME;
	}

	public String getPURIFICATION_MODE() {
		return PURIFICATION_MODE;
	}

	public void setPURIFICATION_MODE(String pURIFICATION_MODE) {
		PURIFICATION_MODE = pURIFICATION_MODE;
	}

	public String getMODEL_NAME() {
		if(MODEL_NAME==null)
			return "";
		return MODEL_NAME;
	}

	public void setMODEL_NAME(String mODEL_NAME) {
		MODEL_NAME = mODEL_NAME;
	}

	public String getFREQUENCY_HOURS() {
		if(FREQUENCY_HOURS==null)
			return "";
		return FREQUENCY_HOURS;
	}

	public void setFREQUENCY_HOURS(String fREQUENCY_HOURS) {
		FREQUENCY_HOURS = fREQUENCY_HOURS;
	}

	public String getCHECK_NUM() {
		return CHECK_NUM;
	}

	public void setCHECK_NUM(String cHECK_NUM) {
		CHECK_NUM = cHECK_NUM;
	}

	public String getCHECK_DATE() {
		return CHECK_DATE;
	}

	public void setCHECK_DATE(String cHECK_DATE) {
		CHECK_DATE = cHECK_DATE;
	}

	public String getINFECTIOUS_CHECK_RESULT() {
		return INFECTIOUS_CHECK_RESULT;
	}

	public void setINFECTIOUS_CHECK_RESULT(String iNFECTIOUS_CHECK_RESULT) {
		INFECTIOUS_CHECK_RESULT = iNFECTIOUS_CHECK_RESULT;
	}

	public String getIS_CRRT() {
		return IS_CRRT;
	}

	public void setIS_CRRT(String iS_CRRT) {
		IS_CRRT = iS_CRRT;
	}
}

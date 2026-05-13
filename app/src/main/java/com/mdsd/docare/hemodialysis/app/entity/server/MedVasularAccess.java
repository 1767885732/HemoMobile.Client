package com.mdsd.docare.hemodialysis.app.entity.server;

import java.util.Date;

import com.mdsd.library.utils.TimeUtils;

public class MedVasularAccess {

private String vascular_Access_Id;

private String patient_Id;

private String vascular_Access_Type;

//private String access_Materia;

private String lateral_Position;

private String vascular_Postion;

//private String blood_Vessel;

//private String modus_Operandi;

private String access_Class;

private String create_Date;

//private String hospital;

//private String access_Status;

//private String first_Date;

//private String is_Success;

//private String first_Lose_Date;

//private String tube_Drawing_Date;

//private String lose_Reason;

private String hemodialysis_Id;

public String getVascular_Access_Id() {
	return vascular_Access_Id;
}

public void setVascular_Access_Id(String vascular_Access_Id) {
	this.vascular_Access_Id = vascular_Access_Id;
}

public String getPatient_Id() {
	return patient_Id;
}

public void setPatient_Id(String patient_Id) {
	this.patient_Id = patient_Id;
}

public String getVascular_Access_Type() {
	return vascular_Access_Type;
}

public void setVascular_Access_Type(String vascular_Access_Type) {
	this.vascular_Access_Type = vascular_Access_Type;
}

public String getLateral_Position() {
	return lateral_Position;
}

public void setLateral_Position(String lateral_Position) {
	this.lateral_Position = lateral_Position;
}

public String getVascular_Postion() {
	return vascular_Postion;
}

public void setVascular_Postion(String vascular_Postion) {
	this.vascular_Postion = vascular_Postion;
}

public String getAccess_Class() {
	return access_Class;
}

public void setAccess_Class(String access_Class) {
	this.access_Class = access_Class;
}

public String getCreate_Date() {
	return create_Date;
}

public void setCreate_Date(String create_Date) {
	this.create_Date = create_Date;
}

public String getHemodialysis_Id() {
	return hemodialysis_Id;
}

public void setHemodialysis_Id(String hemodialysis_Id) {
	this.hemodialysis_Id = hemodialysis_Id;
}

@Override
	public String toString() {
		String strDate = "";
		Date date = TimeUtils.strToDate(create_Date, "yyyy/MM/dd");
		if (date != null) {
			strDate = TimeUtils.dateToStr(date, "yyyy/MM/dd");
		}
		return patient_Id + strDate;
	}
}

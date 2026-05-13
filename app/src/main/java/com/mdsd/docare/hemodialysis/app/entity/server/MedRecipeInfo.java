package com.mdsd.docare.hemodialysis.app.entity.server;

import java.io.Serializable;

public class MedRecipeInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String recipe_Id;

	private String patient_Id;
	
	private String recipe_Date;
	
	private String purification_Mode;
	
	private Double dry_Weight;
	
	private Double frequency_Week;

	private Double frequency_Times;

	private Double frequency_Hours;

	private Double spkt_V;

	private Double urr;

	private String first_Purifier_Model;
	
	private String first_Purifier_Name;
	
	private Double first_Purifier_M2;

	private Double first_Purifier_Koa;

	private Double first_Purifier_Kuf;

	private String second_Purifier_Model;
	
	private String second_Purifier_Name;
	
	private Double second_Purifier_M2;

	private Double second_Purifier_Koa;
	
	private Double second_Purifier_Kuf;
	
	private Double sodion;

	private Double potassium_Ion;

	private Double calcium_Ion;
	
	private Double bicarbonate_Radical;

	private Double bloow_Flow;

	private Double dialysate_Flow;

	private Double dialysate_Temperature;	

	private Double displacement_Liquid;

	private Double blood_Displacement;
	
	private String therapeutic_Method;

	private String first_Drug_Name;

	private String first_Drug_Dosage;

	private String first_Drug_Unit;

	private String first_Drug_Mode;
	
	private String second_Drug_Name;
	
	private String second_Drug_Dosage;

	private String second_Drug_Unit;

	private String second_Drug_Mode;

	private String user_Id;

	private String hemodialysis_Id;

	private String status;

	private String vascular_Access_Id;

	private String recipe_Type;

	private Double ufr;

	private Double today_Weight;

	private Double today_Blooda;

	private Double today_Bloodb;

	private String remark;

	private Double today_Bloodp;

	private String dry_Weight_Remark;
	
	private Double frequency_Minute;
	
	private String displacement_Mode;
	
	private String displacement_Recipe;
	
	private String displacement_Special_Adjust;
	
	private String anticoagulant_Use;
	
	private String special_Matter;
	
	private Double ufr2;
	
	private Double displacement_Flow;

	public String getRecipe_Id() {
		return recipe_Id;
	}

	public void setRecipe_Id(String recipe_Id) {
		this.recipe_Id = recipe_Id;
	}

	public String getPatient_Id() {
		return patient_Id;
	}

	public void setPatient_Id(String patient_Id) {
		this.patient_Id = patient_Id;
	}

	public String getRecipe_Date() {
		return recipe_Date;
	}

	public void setRecipe_Date(String recipe_Date) {
		this.recipe_Date = recipe_Date;
	}

	public String getPurification_Mode() {
		return purification_Mode;
	}

	public void setPurification_Mode(String purification_Mode) {
		this.purification_Mode = purification_Mode;
	}

	public Double getDry_Weight() {
		return dry_Weight;
	}

	public void setDry_Weight(Double dry_Weight) {
		this.dry_Weight = dry_Weight;
	}

	public Double getFrequency_Week() {
		return frequency_Week;
	}

	public void setFrequency_Week(Double frequency_Week) {
		this.frequency_Week = frequency_Week;
	}

	public Double getFrequency_Times() {
		return frequency_Times;
	}

	public void setFrequency_Times(Double frequency_Times) {
		this.frequency_Times = frequency_Times;
	}

	public Double getFrequency_Hours() {
		return frequency_Hours;
	}

	public void setFrequency_Hours(Double frequency_Hours) {
		this.frequency_Hours = frequency_Hours;
	}

	public Double getSpkt_V() {
		return spkt_V;
	}

	public void setSpkt_V(Double spkt_V) {
		this.spkt_V = spkt_V;
	}

	public Double getUrr() {
		return urr;
	}

	public void setUrr(Double urr) {
		this.urr = urr;
	}

	public String getFirst_Purifier_Model() {
		return first_Purifier_Model;
	}

	public void setFirst_Purifier_Model(String first_Purifier_Model) {
		this.first_Purifier_Model = first_Purifier_Model;
	}

	public String getFirst_Purifier_Name() {
		return first_Purifier_Name;
	}

	public void setFirst_Purifier_Name(String first_Purifier_Name) {
		this.first_Purifier_Name = first_Purifier_Name;
	}

	public Double getFirst_Purifier_M2() {
		return first_Purifier_M2;
	}

	public void setFirst_Purifier_M2(Double first_Purifier_M2) {
		this.first_Purifier_M2 = first_Purifier_M2;
	}

	public Double getFirst_Purifier_Koa() {
		return first_Purifier_Koa;
	}

	public void setFirst_Purifier_Koa(Double first_Purifier_Koa) {
		this.first_Purifier_Koa = first_Purifier_Koa;
	}

	public Double getFirst_Purifier_Kuf() {
		return first_Purifier_Kuf;
	}

	public void setFirst_Purifier_Kuf(Double first_Purifier_Kuf) {
		this.first_Purifier_Kuf = first_Purifier_Kuf;
	}

	public String getSecond_Purifier_Model() {
		return second_Purifier_Model;
	}

	public void setSecond_Purifier_Model(String second_Purifier_Model) {
		this.second_Purifier_Model = second_Purifier_Model;
	}

	public String getSecond_Purifier_Name() {
		return second_Purifier_Name;
	}

	public void setSecond_Purifier_Name(String second_Purifier_Name) {
		this.second_Purifier_Name = second_Purifier_Name;
	}

	public Double getSecond_Purifier_M2() {
		return second_Purifier_M2;
	}

	public void setSecond_Purifier_M2(Double second_Purifier_M2) {
		this.second_Purifier_M2 = second_Purifier_M2;
	}

	public Double getSecond_Purifier_Koa() {
		return second_Purifier_Koa;
	}

	public void setSecond_Purifier_Koa(Double second_Purifier_Koa) {
		this.second_Purifier_Koa = second_Purifier_Koa;
	}

	public Double getSecond_Purifier_Kuf() {
		return second_Purifier_Kuf;
	}

	public void setSecond_Purifier_Kuf(Double second_Purifier_Kuf) {
		this.second_Purifier_Kuf = second_Purifier_Kuf;
	}

	public Double getSodion() {
		return sodion;
	}

	public void setSodion(Double sodion) {
		this.sodion = sodion;
	}

	public Double getPotassium_Ion() {
		return potassium_Ion;
	}

	public void setPotassium_Ion(Double potassium_Ion) {
		this.potassium_Ion = potassium_Ion;
	}

	public Double getCalcium_Ion() {
		return calcium_Ion;
	}

	public void setCalcium_Ion(Double calcium_Ion) {
		this.calcium_Ion = calcium_Ion;
	}

	public Double getBicarbonate_Radical() {
		return bicarbonate_Radical;
	}

	public void setBicarbonate_Radical(Double bicarbonate_Radical) {
		this.bicarbonate_Radical = bicarbonate_Radical;
	}

	public Double getBloow_Flow() {
		return bloow_Flow;
	}

	public void setBloow_Flow(Double bloow_Flow) {
		this.bloow_Flow = bloow_Flow;
	}

	public Double getDialysate_Flow() {
		return dialysate_Flow;
	}

	public void setDialysate_Flow(Double dialysate_Flow) {
		this.dialysate_Flow = dialysate_Flow;
	}

	public Double getDialysate_Temperature() {
		return dialysate_Temperature;
	}

	public void setDialysate_Temperature(Double dialysate_Temperature) {
		this.dialysate_Temperature = dialysate_Temperature;
	}

	public Double getDisplacement_Liquid() {
		return displacement_Liquid;
	}

	public void setDisplacement_Liquid(Double displacement_Liquid) {
		this.displacement_Liquid = displacement_Liquid;
	}

	public Double getBlood_Displacement() {
		return blood_Displacement;
	}

	public void setBlood_Displacement(Double blood_Displacement) {
		this.blood_Displacement = blood_Displacement;
	}

	public String getTherapeutic_Method() {
		return therapeutic_Method;
	}

	public void setTherapeutic_Method(String therapeutic_Method) {
		this.therapeutic_Method = therapeutic_Method;
	}

	public String getFirst_Drug_Name() {
		return first_Drug_Name;
	}

	public void setFirst_Drug_Name(String first_Drug_Name) {
		this.first_Drug_Name = first_Drug_Name;
	}

	public String getFirst_Drug_Dosage() {
		return first_Drug_Dosage;
	}

	public void setFirst_Drug_Dosage(String first_Drug_Dosage) {
		this.first_Drug_Dosage = first_Drug_Dosage;
	}

	public String getFirst_Drug_Unit() {
		return first_Drug_Unit;
	}

	public void setFirst_Drug_Unit(String first_Drug_Unit) {
		this.first_Drug_Unit = first_Drug_Unit;
	}

	public String getFirst_Drug_Mode() {
		return first_Drug_Mode;
	}

	public void setFirst_Drug_Mode(String first_Drug_Mode) {
		this.first_Drug_Mode = first_Drug_Mode;
	}

	public String getSecond_Drug_Name() {
		return second_Drug_Name;
	}

	public void setSecond_Drug_Name(String second_Drug_Name) {
		this.second_Drug_Name = second_Drug_Name;
	}

	public String getSecond_Drug_Dosage() {
		return second_Drug_Dosage;
	}

	public void setSecond_Drug_Dosage(String second_Drug_Dosage) {
		this.second_Drug_Dosage = second_Drug_Dosage;
	}

	public String getSecond_Drug_Unit() {
		return second_Drug_Unit;
	}

	public void setSecond_Drug_Unit(String second_Drug_Unit) {
		this.second_Drug_Unit = second_Drug_Unit;
	}

	public String getSecond_Drug_Mode() {
		return second_Drug_Mode;
	}

	public void setSecond_Drug_Mode(String second_Drug_Mode) {
		this.second_Drug_Mode = second_Drug_Mode;
	}

	public String getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}

	public String getHemodialysis_Id() {
		return hemodialysis_Id;
	}

	public void setHemodialysis_Id(String hemodialysis_Id) {
		this.hemodialysis_Id = hemodialysis_Id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVascular_Access_Id() {
		return vascular_Access_Id;
	}

	public void setVascular_Access_Id(String vascular_Access_Id) {
		this.vascular_Access_Id = vascular_Access_Id;
	}

	public String getRecipe_Type() {
		return recipe_Type;
	}

	public void setRecipe_Type(String recipe_Type) {
		this.recipe_Type = recipe_Type;
	}

	public Double getUfr() {
		return ufr;
	}

	public void setUfr(Double ufr) {
		this.ufr = ufr;
	}

	public Double getToday_Weight() {
		return today_Weight;
	}

	public void setToday_Weight(Double today_Weight) {
		this.today_Weight = today_Weight;
	}

	public Double getToday_Blooda() {
		return today_Blooda;
	}

	public void setToday_Blooda(Double today_Blooda) {
		this.today_Blooda = today_Blooda;
	}

	public Double getToday_Bloodb() {
		return today_Bloodb;
	}

	public void setToday_Bloodb(Double today_Bloodb) {
		this.today_Bloodb = today_Bloodb;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getToday_Bloodp() {
		return today_Bloodp;
	}

	public void setToday_Bloodp(Double today_Bloodp) {
		this.today_Bloodp = today_Bloodp;
	}

	public String getDry_Weight_Remark() {
		return dry_Weight_Remark;
	}

	public void setDry_Weight_Remark(String dry_Weight_Remark) {
		this.dry_Weight_Remark = dry_Weight_Remark;
	}

	public Double getFrequency_Minute() {
		return frequency_Minute;
	}

	public void setFrequency_Minute(Double frequency_Minute) {
		this.frequency_Minute = frequency_Minute;
	}

	public String getDisplacement_Mode() {
		return displacement_Mode;
	}

	public void setDisplacement_Mode(String displacement_Mode) {
		this.displacement_Mode = displacement_Mode;
	}

	public String getDisplacement_Recipe() {
		return displacement_Recipe;
	}

	public void setDisplacement_Recipe(String displacement_Recipe) {
		this.displacement_Recipe = displacement_Recipe;
	}

	public String getDisplacement_Special_Adjust() {
		return displacement_Special_Adjust;
	}

	public void setDisplacement_Special_Adjust(String displacement_Special_Adjust) {
		this.displacement_Special_Adjust = displacement_Special_Adjust;
	}

	public String getAnticoagulant_Use() {
		return anticoagulant_Use;
	}

	public void setAnticoagulant_Use(String anticoagulant_Use) {
		this.anticoagulant_Use = anticoagulant_Use;
	}

	public String getSpecial_Matter() {
		return special_Matter;
	}

	public void setSpecial_Matter(String special_Matter) {
		this.special_Matter = special_Matter;
	}

	public Double getUfr2() {
		return ufr2;
	}

	public void setUfr2(Double ufr2) {
		this.ufr2 = ufr2;
	}

	public Double getDisplacement_Flow() {
		return displacement_Flow;
	}

	public void setDisplacement_Flow(Double displacement_Flow) {
		this.displacement_Flow = displacement_Flow;
	}
}

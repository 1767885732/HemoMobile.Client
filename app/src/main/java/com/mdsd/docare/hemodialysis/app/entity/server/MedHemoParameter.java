package com.mdsd.docare.hemodialysis.app.entity.server;

import java.io.Serializable;

public class MedHemoParameter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String hemodialysis_Parameters_Id;

	private String cure_Id;

	private String recipe_Id;

	private String create_Date;//记录时间

	private double venous_Pressure;//静脉压

	private double transmembrane_Pressure;// 跨膜压

	private double temperature;// 体温

	private double systolic_Pressure;//收缩压

	private double diastolic_Pressure;//舒张压

	private double cardiotach;//脉搏

	private double breath;// 呼吸

	private String kt_v;// kt/v

	private String cure_Mode;

	private String clinical_Manifestation;

	private double blood_Flow;// 血流量

	private double sodium_Ion;// 钠离子浓度

	private double dialysate_Rate;// 透析液流量

	private double urf;// 超滤率

	private double online_Clearance_Rate;// 在线清除率

	private double conductivity;// 电导度

	private String nurse_Id;// 护士签字

	private double displacement;// 置换量

	private String vascular_Access_Errhyisis;// 渗血

	private String vascular_Access_Glide;// 滑脱

	private String extended_Field_1;

	private String extended_Field_2;

	private String extended_Field_3;

	private String extended_Field_4;

	private String extended_Field_5;

	private double anticoagulant;// 抗凝剂

	private String anticoagulantunit;// 抗凝剂单位
	
	private String venous_Pressure_Unit;
	
	private Double artery_Pressure;
	
	private String crrt_Class;

	public String getHemodialysis_Parameters_Id() {
		return hemodialysis_Parameters_Id;
	}

	public void setHemodialysis_Parameters_Id(String hemodialysis_Parameters_Id) {
		this.hemodialysis_Parameters_Id = hemodialysis_Parameters_Id;
	}

	public String getCure_Id() {
		return cure_Id;
	}

	public void setCure_Id(String cure_Id) {
		this.cure_Id = cure_Id;
	}

	public String getRecipe_Id() {
		return recipe_Id;
	}

	public void setRecipe_Id(String recipe_Id) {
		this.recipe_Id = recipe_Id;
	}

	public String getCreate_Date() {
		return create_Date;
	}

	public void setCreate_Date(String create_Date) {
		this.create_Date = create_Date;
	}

	public double getVenous_Pressure() {
		return venous_Pressure;
	}

	public void setVenous_Pressure(double venous_Pressure) {
		this.venous_Pressure = venous_Pressure;
	}

	public double getTransmembrane_Pressure() {
		return transmembrane_Pressure;
	}

	public void setTransmembrane_Pressure(double transmembrane_Pressure) {
		this.transmembrane_Pressure = transmembrane_Pressure;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getSystolic_Pressure() {
		return systolic_Pressure;
	}

	public void setSystolic_Pressure(double systolic_Pressure) {
		this.systolic_Pressure = systolic_Pressure;
	}

	public double getDiastolic_Pressure() {
		return diastolic_Pressure;
	}

	public void setDiastolic_Pressure(double diastolic_Pressure) {
		this.diastolic_Pressure = diastolic_Pressure;
	}

	public double getCardiotach() {
		return cardiotach;
	}

	public void setCardiotach(double cardiotach) {
		this.cardiotach = cardiotach;
	}

	public double getBreath() {
		return breath;
	}

	public void setBreath(double breath) {
		this.breath = breath;
	}

	public String getKt_v() {
		return kt_v;
	}

	public void setKt_v(String kt_v) {
		this.kt_v = kt_v;
	}

	public String getCure_Mode() {
		return cure_Mode;
	}

	public void setCure_Mode(String cure_Mode) {
		this.cure_Mode = cure_Mode;
	}

	public String getClinical_Manifestation() {
		return clinical_Manifestation;
	}

	public void setClinical_Manifestation(String clinical_Manifestation) {
		this.clinical_Manifestation = clinical_Manifestation;
	}

	public double getBlood_Flow() {
		return blood_Flow;
	}

	public void setBlood_Flow(double blood_Flow) {
		this.blood_Flow = blood_Flow;
	}

	public double getSodium_Ion() {
		return sodium_Ion;
	}

	public void setSodium_Ion(double sodium_Ion) {
		this.sodium_Ion = sodium_Ion;
	}

	public double getDialysate_Rate() {
		return dialysate_Rate;
	}

	public void setDialysate_Rate(double dialysate_Rate) {
		this.dialysate_Rate = dialysate_Rate;
	}

	public double getUrf() {
		return urf;
	}

	public void setUrf(double urf) {
		this.urf = urf;
	}

	public double getOnline_Clearance_Rate() {
		return online_Clearance_Rate;
	}

	public void setOnline_Clearance_Rate(double online_Clearance_Rate) {
		this.online_Clearance_Rate = online_Clearance_Rate;
	}

	public double getConductivity() {
		return conductivity;
	}

	public void setConductivity(double conductivity) {
		this.conductivity = conductivity;
	}

	public String getNurse_Id() {
		return nurse_Id;
	}

	public void setNurse_Id(String nurse_Id) {
		this.nurse_Id = nurse_Id;
	}

	public double getDisplacement() {
		return displacement;
	}

	public void setDisplacement(double displacement) {
		this.displacement = displacement;
	}

	public String getVascular_Access_Errhyisis() {
		return vascular_Access_Errhyisis;
	}

	public void setVascular_Access_Errhyisis(String vascular_Access_Errhyisis) {
		this.vascular_Access_Errhyisis = vascular_Access_Errhyisis;
	}

	public String getVascular_Access_Glide() {
		return vascular_Access_Glide;
	}

	public void setVascular_Access_Glide(String vascular_Access_Glide) {
		this.vascular_Access_Glide = vascular_Access_Glide;
	}

	public String getExtended_Field_1() {
		return extended_Field_1;
	}

	public void setExtended_Field_1(String extended_Field_1) {
		this.extended_Field_1 = extended_Field_1;
	}

	public String getExtended_Field_2() {
		return extended_Field_2;
	}

	public void setExtended_Field_2(String extended_Field_2) {
		this.extended_Field_2 = extended_Field_2;
	}

	public String getExtended_Field_3() {
		return extended_Field_3;
	}

	public void setExtended_Field_3(String extended_Field_3) {
		this.extended_Field_3 = extended_Field_3;
	}

	public String getExtended_Field_4() {
		return extended_Field_4;
	}

	public void setExtended_Field_4(String extended_Field_4) {
		this.extended_Field_4 = extended_Field_4;
	}

	public String getExtended_Field_5() {
		return extended_Field_5;
	}

	public void setExtended_Field_5(String extended_Field_5) {
		this.extended_Field_5 = extended_Field_5;
	}

	public double getAnticoagulant() {
		return anticoagulant;
	}

	public void setAnticoagulant(double anticoagulant) {
		this.anticoagulant = anticoagulant;
	}

	public String getVenous_Pressure_Unit() {
		return venous_Pressure_Unit;
	}

	public void setVenous_Pressure_Unit(String venous_Pressure_Unit) {
		this.venous_Pressure_Unit = venous_Pressure_Unit;
	}

	public Double getArtery_Pressure() {
		return artery_Pressure;
	}

	public void setArtery_Pressure(Double artery_Pressure) {
		this.artery_Pressure = artery_Pressure;
	}

	public String getCrrt_Class() {
		return crrt_Class;
	}

	public void setCrrt_Class(String crrt_Class) {
		this.crrt_Class = crrt_Class;
	}

	public String getAnticoagulantunit() {
		return anticoagulantunit;
	}

	public void setAnticoagulantunit(String anticoagulantunit) {
		this.anticoagulantunit = anticoagulantunit;
	}
}

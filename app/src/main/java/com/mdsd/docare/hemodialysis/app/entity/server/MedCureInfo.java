package com.mdsd.docare.hemodialysis.app.entity.server;

import java.io.Serializable;

public class MedCureInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private String cure_Id;

	private String recipe_Id;

	private String hemodialysis_Id;

	private String recipe_Type;

	private Double calcium_Ion;

	private String cure_Status;

	private String doctor_Id;

	private String recipe_Date;

	private Double bloow_Flow;

	private Double dialysate_Flow;

	private Double dialysate_Temperature;

	private Double ufr;

	private Double sodion;

	private Double potassium_Ion;

	private String perform_Schedule;

	private String nurse_Id;

	private String purification_Mode;

	private int clean_Up_Times;

	private Double frequency_Hours;

	private String begin_Time;

	private String end_Time;

	private Double last_Time_Dry_Weight;
	
	private Double dry_Weight;

	private Double before_Dry_Weight;

	private Double after_Dry_Weight;

	private Double before_Systolic_Pressure;

	private Double before_Diastolic_Pressure;

	private Double after_Systolic_Pressure;

	private Double after_Diastolic_Pressure;

	private Double dry_Water_Value;

	private Double before_Temperature;

	private Double after_Temperature;

	private Double before_Heart_Rate;

	private Double after_Heart_Rate;

	private String primary_Nurse;

	private String primary_Doctor;

	private String puncture_Nurse;

	private String machine_Id;

	private String vascular_Access_Id;

	private String heparin_Species;

	private Double first_Heparin;

	private Double dosis_Sustentativa;

	private String machine_Type;

	private String purifier_Name;

	private Double purifier_M2;

	private String use_Type;

	private Double reuse_Times;

	private String a_Liquid;

	private String b_Liquid;

	private Double bircarbonate;

	private Double amylaceum;

	private String summary;

	private String cure_Create_Date;

	private String vascular_Access_Firm;

	private String vascular_Access_Glide;

	private String vascular_Access_Swelling;

	private String vascular_Access_Errhyisis;

	private String vascular_Access_Thrombus;

	private String vascular_Access_Blood;
	
	private String vascular_Access_Blood_Infect;

	private Double filtration_Displacement_Liquid;

	private Double filtration_Percolate;

	private Double displacement_Liquid;

	private Double percolate;

	private String doctor_Advice;

	private String summary2;
	
	private String summary3;

	private String check_Nurse;

	private String first_Drug_Unit;

	private String second_Drug_Unit;

	private String vein;
	
	private String dry_Weight_Tag;

	private String before_Dry_Weight_Tag;

	private String after_Dry_Weight_Tag;

	private String reuse_Times_Tag;

	private String machine_Id_Tag;
	
    private String blood_Up;
	
	private String blood_Type;
	
	private String blood_Transfusion;
	
	private String coagulation_In_Dialyser;
	
	private String in_Basket_Clean;
	
	private String in_Basket_Red_Hot;
	
	private String in_Basket_Ecchymosis;
	
	private String in_Basket_Tremor;
	
	private String in_Basket_Noise;
	
	private String in_Basket_Vascular_Elasticity;
	
	private String in_Basket_Vascular_Other;
	
	private String in_Basket_Wound_Allergy;
	
	private String in_Basket_Plaster_Allergy;
	
	private String vascular_Access_Type;
	
	private String subjective_Comfort;
	
	private Double before_BP;
	
	private Double after_BP;
	
	private Double frequency_Minute;
	
	private String displacement_Mode;
	
	private String displacement_Recipe;
	
	private String displacement_Special_Adjust;
	
	private String anticoagulant_Use;
	
	private String special_Matter;
	
	private Double ufr2;
	
	private Double displacement_Flow;
	
	private Double uf;
	
	private Double sum_Uf;
	
	private String focus_Level;
	
	private String senses;
	
	private String allergic;
	
	private String bt;
	
	private String bp;
	
	private String br;
	
	private String afterbt;
	
	private String afterbp;
	
	private String afterbr;

	private String in_Bed;

	private Double actual_Cleanup_Hour;

	private Double actual_Cleanup_Minute;

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

	public String getHemodialysis_Id() {
		return hemodialysis_Id;
	}

	public void setHemodialysis_Id(String hemodialysis_Id) {
		this.hemodialysis_Id = hemodialysis_Id;
	}

	public String getRecipe_Type() {
		return recipe_Type;
	}

	public void setRecipe_Type(String recipe_Type) {
		this.recipe_Type = recipe_Type;
	}

	public Double getCalcium_Ion() {
		return calcium_Ion;
	}

	public void setCalcium_Ion(Double calcium_Ion) {
		this.calcium_Ion = calcium_Ion;
	}

	public String getCure_Status() {
		return cure_Status;
	}

	public void setCure_Status(String cure_Status) {
		this.cure_Status = cure_Status;
	}

	public String getDoctor_Id() {
		return doctor_Id;
	}

	public void setDoctor_Id(String doctor_Id) {
		this.doctor_Id = doctor_Id;
	}

	public String getRecipe_Date() {
		return recipe_Date;
	}

	public void setRecipe_Date(String recipe_Date) {
		this.recipe_Date = recipe_Date;
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

	public Double getUfr() {
		return ufr;
	}

	public void setUfr(Double ufr) {
		this.ufr = ufr;
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

	public String getPerform_Schedule() {
		return perform_Schedule;
	}

	public void setPerform_Schedule(String perform_Schedule) {
		this.perform_Schedule = perform_Schedule;
	}

	public String getNurse_Id() {
		return nurse_Id;
	}

	public void setNurse_Id(String nurse_Id) {
		this.nurse_Id = nurse_Id;
	}

	public String getPurification_Mode() {
		return purification_Mode;
	}

	public void setPurification_Mode(String purification_Mode) {
		this.purification_Mode = purification_Mode;
	}

	public int getClean_Up_Times() {
		return clean_Up_Times;
	}

	public void setClean_Up_Times(int clean_Up_Times) {
		this.clean_Up_Times = clean_Up_Times;
	}

	public Double getFrequency_Hours() {
		return frequency_Hours;
	}

	public void setFrequency_Hours(Double frequency_Hours) {
		this.frequency_Hours = frequency_Hours;
	}

	public String getBegin_Time() {
		return begin_Time;
	}

	public void setBegin_Time(String begin_Time) {
		this.begin_Time = begin_Time;
	}

	public String getEnd_Time() {
		return end_Time;
	}

	public void setEnd_Time(String end_Time) {
		this.end_Time = end_Time;
	}

	public Double getLast_Time_Dry_Weight() {
		return last_Time_Dry_Weight;
	}

	public void setLast_Time_Dry_Weight(Double last_Time_Dry_Weight) {
		this.last_Time_Dry_Weight = last_Time_Dry_Weight;
	}

	public Double getDry_Weight() {
		return dry_Weight;
	}

	public void setDry_Weight(Double dry_Weight) {
		this.dry_Weight = dry_Weight;
	}

	public String getDry_Weight_Tag() {
		return dry_Weight_Tag;
	}

	public void setDry_Weight_Tag(String dry_Weight_Tag) {
		this.dry_Weight_Tag = dry_Weight_Tag;
	}

	public Double getBefore_Dry_Weight() {
		return before_Dry_Weight;
	}

	public void setBefore_Dry_Weight(Double before_Dry_Weight) {
		this.before_Dry_Weight = before_Dry_Weight;
	}

	public Double getAfter_Dry_Weight() {
		return after_Dry_Weight;
	}

	public void setAfter_Dry_Weight(Double after_Dry_Weight) {
		this.after_Dry_Weight = after_Dry_Weight;
	}

	public Double getBefore_Systolic_Pressure() {
		return before_Systolic_Pressure;
	}

	public void setBefore_Systolic_Pressure(Double before_Systolic_Pressure) {
		this.before_Systolic_Pressure = before_Systolic_Pressure;
	}

	public Double getBefore_Diastolic_Pressure() {
		return before_Diastolic_Pressure;
	}

	public void setBefore_Diastolic_Pressure(Double before_Diastolic_Pressure) {
		this.before_Diastolic_Pressure = before_Diastolic_Pressure;
	}

	public Double getAfter_Systolic_Pressure() {
		return after_Systolic_Pressure;
	}

	public void setAfter_Systolic_Pressure(Double after_Systolic_Pressure) {
		this.after_Systolic_Pressure = after_Systolic_Pressure;
	}

	public Double getAfter_Diastolic_Pressure() {
		return after_Diastolic_Pressure;
	}

	public void setAfter_Diastolic_Pressure(Double after_Diastolic_Pressure) {
		this.after_Diastolic_Pressure = after_Diastolic_Pressure;
	}

	public Double getDry_Water_Value() {
		return dry_Water_Value;
	}

	public void setDry_Water_Value(Double dry_Water_Value) {
		this.dry_Water_Value = dry_Water_Value;
	}

	public Double getBefore_Temperature() {
		return before_Temperature;
	}

	public void setBefore_Temperature(Double before_Temperature) {
		this.before_Temperature = before_Temperature;
	}

	public Double getAfter_Temperature() {
		return after_Temperature;
	}

	public void setAfter_Temperature(Double after_Temperature) {
		this.after_Temperature = after_Temperature;
	}

	public Double getBefore_Heart_Rate() {
		return before_Heart_Rate;
	}

	public void setBefore_Heart_Rate(Double before_Heart_Rate) {
		this.before_Heart_Rate = before_Heart_Rate;
	}

	public Double getAfter_Heart_Rate() {
		return after_Heart_Rate;
	}

	public void setAfter_Heart_Rate(Double after_Heart_Rate) {
		this.after_Heart_Rate = after_Heart_Rate;
	}

	public String getPrimary_Nurse() {
		return primary_Nurse;
	}

	public void setPrimary_Nurse(String primary_Nurse) {
		this.primary_Nurse = primary_Nurse;
	}

	public String getPrimary_Doctor() {
		return primary_Doctor;
	}

	public void setPrimary_Doctor(String primary_Doctor) {
		this.primary_Doctor = primary_Doctor;
	}

	public String getPuncture_Nurse() {
		return puncture_Nurse;
	}

	public void setPuncture_Nurse(String puncture_Nurse) {
		this.puncture_Nurse = puncture_Nurse;
	}

	public String getMachine_Id() {
		return machine_Id;
	}

	public void setMachine_Id(String machine_Id) {
		this.machine_Id = machine_Id;
	}

	public String getVascular_Access_Id() {
		return vascular_Access_Id;
	}

	public void setVascular_Access_Id(String vascular_Access_Id) {
		this.vascular_Access_Id = vascular_Access_Id;
	}

	public String getHeparin_Species() {
		return heparin_Species;
	}

	public void setHeparin_Species(String heparin_Species) {
		this.heparin_Species = heparin_Species;
	}

	public Double getFirst_Heparin() {
		return first_Heparin;
	}

	public void setFirst_Heparin(Double first_Heparin) {
		this.first_Heparin = first_Heparin;
	}

	public Double getDosis_Sustentativa() {
		return dosis_Sustentativa;
	}

	public void setDosis_Sustentativa(Double dosis_Sustentativa) {
		this.dosis_Sustentativa = dosis_Sustentativa;
	}

	public String getMachine_Type() {
		return machine_Type;
	}

	public void setMachine_Type(String machine_Type) {
		this.machine_Type = machine_Type;
	}

	public String getPurifier_Name() {
		return purifier_Name;
	}

	public void setPurifier_Name(String purifier_Name) {
		this.purifier_Name = purifier_Name;
	}

	public Double getPurifier_M2() {
		return purifier_M2;
	}

	public void setPurifier_M2(Double purifier_M2) {
		this.purifier_M2 = purifier_M2;
	}

	public String getUse_Type() {
		return use_Type;
	}

	public void setUse_Type(String use_Type) {
		this.use_Type = use_Type;
	}

	public Double getReuse_Times() {
		return reuse_Times;
	}

	public void setReuse_Times(Double reuse_Times) {
		this.reuse_Times = reuse_Times;
	}

	public String getA_Liquid() {
		return a_Liquid;
	}

	public void setA_Liquid(String a_Liquid) {
		this.a_Liquid = a_Liquid;
	}

	public String getB_Liquid() {
		return b_Liquid;
	}

	public void setB_Liquid(String b_Liquid) {
		this.b_Liquid = b_Liquid;
	}

	public Double getBircarbonate() {
		return bircarbonate;
	}

	public void setBircarbonate(Double bircarbonate) {
		this.bircarbonate = bircarbonate;
	}

	public Double getAmylaceum() {
		return amylaceum;
	}

	public void setAmylaceum(Double amylaceum) {
		this.amylaceum = amylaceum;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCure_Create_Date() {
		return cure_Create_Date;
	}

	public void setCure_Create_Date(String cure_Create_Date) {
		this.cure_Create_Date = cure_Create_Date;
	}

	public String getVascular_Access_Firm() {
		return vascular_Access_Firm;
	}

	public void setVascular_Access_Firm(String vascular_Access_Firm) {
		this.vascular_Access_Firm = vascular_Access_Firm;
	}

	public String getVascular_Access_Glide() {
		return vascular_Access_Glide;
	}

	public void setVascular_Access_Glide(String vascular_Access_Glide) {
		this.vascular_Access_Glide = vascular_Access_Glide;
	}

	public String getVascular_Access_Swelling() {
		return vascular_Access_Swelling;
	}

	public void setVascular_Access_Swelling(String vascular_Access_Swelling) {
		this.vascular_Access_Swelling = vascular_Access_Swelling;
	}

	public String getVascular_Access_Errhyisis() {
		return vascular_Access_Errhyisis;
	}

	public void setVascular_Access_Errhyisis(String vascular_Access_Errhyisis) {
		this.vascular_Access_Errhyisis = vascular_Access_Errhyisis;
	}

	public String getVascular_Access_Thrombus() {
		return vascular_Access_Thrombus;
	}

	public void setVascular_Access_Thrombus(String vascular_Access_Thrombus) {
		this.vascular_Access_Thrombus = vascular_Access_Thrombus;
	}

	public String getVascular_Access_Blood() {
		return vascular_Access_Blood;
	}

	public void setVascular_Access_Blood(String vascular_Access_Blood) {
		this.vascular_Access_Blood = vascular_Access_Blood;
	}

	public String getVascular_Access_Blood_Infect() {
		return vascular_Access_Blood_Infect;
	}

	public void setVascular_Access_Blood_Infect(String vascular_Access_Blood_Infect) {
		this.vascular_Access_Blood_Infect = vascular_Access_Blood_Infect;
	}

	public Double getFiltration_Displacement_Liquid() {
		return filtration_Displacement_Liquid;
	}

	public void setFiltration_Displacement_Liquid(
			Double filtration_Displacement_Liquid) {
		this.filtration_Displacement_Liquid = filtration_Displacement_Liquid;
	}

	public Double getFiltration_Percolate() {
		return filtration_Percolate;
	}

	public void setFiltration_Percolate(Double filtration_Percolate) {
		this.filtration_Percolate = filtration_Percolate;
	}

	public Double getDisplacement_Liquid() {
		return displacement_Liquid;
	}

	public void setDisplacement_Liquid(Double displacement_Liquid) {
		this.displacement_Liquid = displacement_Liquid;
	}

	public Double getPercolate() {
		return percolate;
	}

	public void setPercolate(Double percolate) {
		this.percolate = percolate;
	}

	public String getDoctor_Advice() {
		return doctor_Advice;
	}

	public void setDoctor_Advice(String doctor_Advice) {
		this.doctor_Advice = doctor_Advice;
	}

	public String getSummary2() {
		return summary2;
	}

	public void setSummary2(String summary2) {
		this.summary2 = summary2;
	}

	public String getCheck_Nurse() {
		return check_Nurse;
	}

	public void setCheck_Nurse(String check_Nurse) {
		this.check_Nurse = check_Nurse;
	}

	public String getFirst_Drug_Unit() {
		return first_Drug_Unit;
	}

	public void setFirst_Drug_Unit(String first_Drug_Unit) {
		this.first_Drug_Unit = first_Drug_Unit;
	}

	public String getSecond_Drug_Unit() {
		return second_Drug_Unit;
	}

	public void setSecond_Drug_Unit(String second_Drug_Unit) {
		this.second_Drug_Unit = second_Drug_Unit;
	}

	public String getVein() {
		return vein;
	}

	public void setVein(String vein) {
		this.vein = vein;
	}

	public String getBefore_Dry_Weight_Tag() {
		return before_Dry_Weight_Tag;
	}

	public void setBefore_Dry_Weight_Tag(String before_Dry_Weight_Tag) {
		this.before_Dry_Weight_Tag = before_Dry_Weight_Tag;
	}

	public String getAfter_Dry_Weight_Tag() {
		return after_Dry_Weight_Tag;
	}

	public void setAfter_Dry_Weight_Tag(String after_Dry_Weight_Tag) {
		this.after_Dry_Weight_Tag = after_Dry_Weight_Tag;
	}

	public String getReuse_Times_Tag() {
		return reuse_Times_Tag;
	}

	public void setReuse_Times_Tag(String reuse_Times_Tag) {
		this.reuse_Times_Tag = reuse_Times_Tag;
	}

	public String getMachine_Id_Tag() {
		return machine_Id_Tag;
	}

	public void setMachine_Id_Tag(String machine_Id_Tag) {
		this.machine_Id_Tag = machine_Id_Tag;
	}
	
	public String getBlood_Up() {
		return blood_Up;
	}

	public void setBlood_Up(String blood_up) {
		this.blood_Up = blood_up;
	}
	
	public String getBlood_Type() {
		return blood_Type;
	}

	public void setBlood_Type(String blood_type) {
		this.blood_Type = blood_type;
	}
	
	public String getBlood_Transfusion() {
		return blood_Transfusion;
	}

	public void setBlood_Transfusion(String blood_transfusion) {
		this.blood_Transfusion = blood_transfusion;
	}
	
	public String getCoagulation_In_Dialyser() {
		return coagulation_In_Dialyser;
	}

	public void setCoagulation_In_Dialyser(String coagulation_in_dialyser) {
		this.coagulation_In_Dialyser = coagulation_in_dialyser;
	}
	
	public String getIn_Basket_Clean() {
		return in_Basket_Clean;
	}

	public void setIn_Basket_Clean(String in_basket_clean) {
		this.in_Basket_Clean = in_basket_clean;
	}
	
	public String getIn_Basket_Red_Hot() {
		return in_Basket_Red_Hot;
	}

	public void setIn_Basket_Red_Hot(String in_basket_red_hot) {
		this.in_Basket_Red_Hot = in_basket_red_hot;
	}
	
	public String getIn_Basket_Ecchymosis() {
		return in_Basket_Ecchymosis;
	}

	public void setIn_Basket_Ecchymosis(String in_basket_ecchymosis) {
		this.in_Basket_Ecchymosis = in_basket_ecchymosis;
	}
	
	public String getIn_Basket_Tremor() {
		return in_Basket_Tremor;
	}

	public void setIn_Basket_Tremor(String in_basket_tremor) {
		this.in_Basket_Tremor = in_basket_tremor;
	}
	
	public String getIn_Basket_Noise() {
		return in_Basket_Noise;
	}

	public void setIn_Basket_Noise(String in_basket_noise) {
		this.in_Basket_Noise = in_basket_noise;
	}
	
	public String getIn_Basket_Vascular_Elasticity() {
		return in_Basket_Vascular_Elasticity;
	}

	public void setIn_Basket_Vascular_Elasticity(String in_basket_vascular_elasticity) {
		this.in_Basket_Vascular_Elasticity = in_basket_vascular_elasticity;
		
//		if(!in_basket_vascular_elasticity.equals(""))
//		{
//			this.setIn_Basket_Vascular_Other("");
//		}
	}
	
	public String getIn_Basket_Vascular_Other() {
		return in_Basket_Vascular_Other;
	}

	public void setIn_Basket_Vascular_Other(String in_basket_vascular_other) {
		this.in_Basket_Vascular_Other = in_basket_vascular_other;
		
//		if(!in_basket_vascular_other.equals(""))
//	    {
//			this.setIn_Basket_Vascular_Elasticity("2");
//	    }
	}
	
	public String getIn_Basket_Wound_Allergy() {
		return in_Basket_Wound_Allergy;
	}

	public void setIn_Basket_Wound_Allergy(String in_basket_wound_allergy) {
		this.in_Basket_Wound_Allergy = in_basket_wound_allergy;
	}
	
	public String getIn_Basket_Plaster_Allergy() {
		return in_Basket_Plaster_Allergy;
	}

	public void setIn_Basket_Plaster_Allergy(String in_basket_plaster_allergy) {
		this.in_Basket_Plaster_Allergy = in_basket_plaster_allergy;
	}
	
	public String getSubjective_Comfort() {
		return subjective_Comfort;
	}

	public void setSubjective_Comfort(String subjective_comfort) {
		this.subjective_Comfort = subjective_comfort;
	}

	public Double getBefore_BP() {
		return before_BP;
	}

	public void setBefore_BP(Double before_BP) {
		this.before_BP = before_BP;
	}

	public String getVascular_Access_Type() {
		return vascular_Access_Type;
	}

	public void setVascular_Access_Type(String vascular_Access_Type) {
		this.vascular_Access_Type = vascular_Access_Type;
	}

	public String getSummary3() {
		return summary3;
	}

	public void setSummary3(String summary3) {
		this.summary3 = summary3;
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

	public Double getUf() {
		return uf;
	}

	public void setUf(Double uf) {
		this.uf = uf;
	}

	public Double getSum_Uf() {
		return sum_Uf;
	}

	public void setSum_Uf(Double sum_Uf) {
		this.sum_Uf = sum_Uf;
	}

	public String getFocus_Level() {
		return focus_Level;
	}

	public void setFocus_Level(String focus_Level) {
		this.focus_Level = focus_Level;
	}

	public String getSenses() {
		return senses;
	}

	public void setSenses(String senses) {
		this.senses = senses;
	}

	public String getAllergic() {
		return allergic;
	}

	public void setAllergic(String allergic) {
		this.allergic = allergic;
	}

	public String getBt() {
		return bt;
	}

	public void setBt(String bt) {
		this.bt = bt;
	}

	public String getBp() {
		return bp;
	}

	public void setBp(String bp) {
		this.bp = bp;
	}

	public String getBr() {
		return br;
	}

	public void setBr(String br) {
		this.br = br;
	}

	public String getAfterbt() {
		return afterbt;
	}

	public void setAfterbt(String afterbt) {
		this.afterbt = afterbt;
	}

	public String getAfterbp() {
		return afterbp;
	}

	public void setAfterbp(String afterbp) {
		this.afterbp = afterbp;
	}

	public String getAfterbr() {
		return afterbr;
	}

	public void setAfterbr(String afterbr) {
		this.afterbr = afterbr;
	}

	public Double getAfter_BP() {
		return after_BP;
	}

	public void setAfter_BP(Double after_BP) {
		this.after_BP = after_BP;
	}

	public String getIn_Bed() {
		return in_Bed;
	}

	public void setIn_Bed(String in_Bed) {
		this.in_Bed = in_Bed;
	}

	public Double getActual_Cleanup_Hour() {
		return actual_Cleanup_Hour;
	}

	public void setActual_Cleanup_Hour(Double actual_Cleanup_Hour) {
		this.actual_Cleanup_Hour = actual_Cleanup_Hour;
	}

	public Double getActual_Cleanup_Minute() {
		return actual_Cleanup_Minute;
	}

	public void setActual_Cleanup_Minute(Double actual_Cleanup_Minute) {
		this.actual_Cleanup_Minute = actual_Cleanup_Minute;
	}
}

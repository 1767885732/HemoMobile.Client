package com.mdsd.docare.hemodialysis.app.entity.server;

import java.io.Serializable;

public class MedCureLongDrug implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String cure_drug_id;
	
	private String hemodialysis_id;
	
	private String cure_id;
	
	private String recipe_id;
	
	private String drug_code;
	
	private String drug_name;
	
	private String drug_times;
	
	private String drug_days;
	
	private String dosage;
	
	private String dosage_units;
	
	private String status;
	
	private String create_date;
	
	private String drug_mode;
	
	private String com_no;
	
	private String unit_name;
	
	private String drug_mode_name;
	
	private String name;
	
	private String drug_rate;
	
	private String remark;

	public String getCure_drug_id() {
		return cure_drug_id;
	}

	public void setCure_drug_id(String cure_drug_id) {
		this.cure_drug_id = cure_drug_id;
	}

	public String getHemodialysis_id() {
		return hemodialysis_id;
	}

	public void setHemodialysis_id(String hemodialysis_id) {
		this.hemodialysis_id = hemodialysis_id;
	}

	public String getCure_id() {
		return cure_id;
	}

	public void setCure_id(String cure_id) {
		this.cure_id = cure_id;
	}

	public String getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(String recipe_id) {
		this.recipe_id = recipe_id;
	}

	public String getDrug_code() {
		return drug_code;
	}

	public void setDrug_code(String drug_code) {
		this.drug_code = drug_code;
	}

	public String getDrug_name() {
		return drug_name;
	}

	public void setDrug_name(String drug_name) {
		this.drug_name = drug_name;
	}

	public String getDrug_times() {
		return drug_times;
	}

	public void setDrug_times(String drug_times) {
		this.drug_times = drug_times;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getDosage_units() {
		return dosage_units;
	}

	public void setDosage_units(String dosage_units) {
		this.dosage_units = dosage_units;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getDrug_mode() {
		return drug_mode;
	}

	public void setDrug_mode(String drug_mode) {
		this.drug_mode = drug_mode;
	}

	public String getCom_no() {
		return com_no;
	}

	public void setCom_no(String com_no) {
		this.com_no = com_no;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getDrug_mode_name() {
		return drug_mode_name;
	}

	public void setDrug_mode_name(String drug_mode_name) {
		this.drug_mode_name = drug_mode_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDrug_rate() {
		return drug_rate;
	}

	public void setDrug_rate(String drug_rate) {
		this.drug_rate = drug_rate;
	}

	public String getDrug_days() {
		return drug_days;
	}

	public void setDrug_days(String drug_days) {
		this.drug_days = drug_days;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}

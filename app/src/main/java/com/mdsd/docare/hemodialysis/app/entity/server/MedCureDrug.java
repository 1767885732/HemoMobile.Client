package com.mdsd.docare.hemodialysis.app.entity.server;

import java.io.Serializable;

/**
 * 临时医嘱
 * 
 * <br>
 * .
 * 
 * @author jianyu.l
 * @since 2014年9月4日
 */
public class MedCureDrug implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cure_drug_id. */
	private String cure_drug_id;

	/** The cure_id. */
	private String cure_id;

	/** The recipe_id. */
	private String recipe_id;

	/** The drug_code. */
	private String drug_code;

	/** The drug_name. */
	private String drug_name;
	// private String drug_times;// 日期间隔
	// private String administration;// 用药途径
	private String dosage;// 单次用量
	private String dosage_units;// 用量单位
	// private String remark;
	/** 药品执行状态. */
	private String status;
	// private String execute_status;// 处方状态
	// private String doctor_id;
	/** 给药时间. */
	private String create_date;

	/** 药品使用方式. */
	private String drug_mode;
	// private String drug_timetype;// 透析前，中，后
	// private String drug_days;
	// private String exec_date;//执行时间
	// private String drug_nurse_id;
	// private String patient_id;
	/** The hemodialysis_id. */
	private String hemodialysis_id;

	/** 医嘱组合号. */
	private String com_no;

	/**
	 * 医嘱子医嘱序号
	 */
	private String com_sub_no;

	/**
	 * 规格
	 */
	private String unit_name;

	/**
	 * 用法
	 */
	private String drug_mode_name;

	/**
	 * 医生姓名
	 */
	private String name;

	/**
	 * Gets the cure_drug_id.
	 * 
	 * @return the cure_drug_id
	 */
	public String getCure_drug_id() {
		return cure_drug_id;
	}

	/**
	 * Sets the cure_drug_id.
	 * 
	 * @param cure_drug_id
	 *            the new cure_drug_id
	 */
	public void setCure_drug_id(String cure_drug_id) {
		this.cure_drug_id = cure_drug_id;
	}

	/**
	 * Gets the cure_id.
	 * 
	 * @return the cure_id
	 */
	public String getCure_id() {
		return cure_id;
	}

	/**
	 * Sets the cure_id.
	 * 
	 * @param cure_id
	 *            the new cure_id
	 */
	public void setCure_id(String cure_id) {
		this.cure_id = cure_id;
	}

	/**
	 * Gets the recipe_id.
	 * 
	 * @return the recipe_id
	 */
	public String getRecipe_id() {
		return recipe_id;
	}

	/**
	 * Sets the recipe_id.
	 * 
	 * @param recipe_id
	 *            the new recipe_id
	 */
	public void setRecipe_id(String recipe_id) {
		this.recipe_id = recipe_id;
	}

	/**
	 * Gets the drug_code.
	 * 
	 * @return the drug_code
	 */
	public String getDrug_code() {
		return drug_code;
	}

	/**
	 * Sets the drug_code.
	 * 
	 * @param drug_code
	 *            the new drug_code
	 */
	public void setDrug_code(String drug_code) {
		this.drug_code = drug_code;
	}

	/**
	 * Gets the drug_name.
	 * 
	 * @return the drug_name
	 */
	public String getDrug_name() {
		return drug_name;
	}

	/**
	 * Sets the drug_name.
	 * 
	 * @param drug_name
	 *            the new drug_name
	 */
	public void setDrug_name(String drug_name) {
		this.drug_name = drug_name;
	}

	/**
	 * Gets the 药品执行状态.
	 * 
	 * @return the 药品执行状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the 药品执行状态.
	 * 
	 * @param status
	 *            the new 药品执行状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the 给药时间.
	 * 
	 * @return the 给药时间
	 */
	public String getCreate_date() {
		return create_date;
	}

	/**
	 * Sets the 给药时间.
	 * 
	 * @param create_date
	 *            the new 给药时间
	 */
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	/**
	 * Gets the 药品使用方式.
	 * 
	 * @return the 药品使用方式
	 */
	public String getDrug_mode() {
		return drug_mode;
	}

	/**
	 * Sets the 药品使用方式.
	 * 
	 * @param drug_mode
	 *            the new 药品使用方式
	 */
	public void setDrug_mode(String drug_mode) {
		this.drug_mode = drug_mode;
	}

	/**
	 * Gets the hemodialysis_id.
	 * 
	 * @return the hemodialysis_id
	 */
	public String getHemodialysis_id() {
		return hemodialysis_id;
	}

	/**
	 * Sets the hemodialysis_id.
	 * 
	 * @param hemodialysis_id
	 *            the new hemodialysis_id
	 */
	public void setHemodialysis_id(String hemodialysis_id) {
		this.hemodialysis_id = hemodialysis_id;
	}

	/**
	 * Gets the 医嘱组合号.
	 * 
	 * @return the 医嘱组合号
	 */
	public String getCom_no() {
		return com_no;
	}

	/**
	 * Sets the 医嘱组合号.
	 * 
	 * @param com_no
	 *            the new 医嘱组合号
	 */
	public void setCom_no(String com_no) {
		this.com_no = com_no;
	}

	/**
	 * Gets the dosage.
	 * 
	 * @return the dosage
	 */
	public String getDosage() {
		return dosage;
	}

	/**
	 * Sets the dosage.
	 * 
	 * @param dosage
	 *            the new dosage
	 */
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	/**
	 * Gets the dosage_units.
	 * 
	 * @return the dosage_units
	 */
	public String getDosage_units() {
		return dosage_units;
	}

	/**
	 * Sets the dosage_units.
	 * 
	 * @param dosage_units
	 *            the new dosage_units
	 */
	public void setDosage_units(String dosage_units) {
		this.dosage_units = dosage_units;
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

	public String getCom_sub_no() {
		return com_sub_no;
	}

	public void setCom_sub_no(String com_sub_no) {
		this.com_sub_no = com_sub_no;
	}

}

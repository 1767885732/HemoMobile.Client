package com.mdsd.docare.hemodialysis.app.entity.server;

import java.io.Serializable;

/**
 * 护士相关类
 * 
 * <br>
 * .
 * 
 * @author jianyu.l
 * @since 2014年9月10日
 */
public class MedNurse implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 编号. */
	private String EMP_NO;

	/** 护士姓名. */
	private String NAME;

	/**
	 * Gets the emp no.
	 * 
	 * @return the emp no
	 */
	public String getEMP_NO() {
		return EMP_NO;
	}

	/**
	 * Sets the emp no.
	 * 
	 * @param eMP_NO
	 *            the new emp no
	 */
	public void setEMP_NO(String eMP_NO) {
		EMP_NO = eMP_NO;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getNAME() {
		return NAME;
	}

	/**
	 * Sets the name.
	 * 
	 * @param nAME
	 *            the new name
	 */
	public void setNAME(String nAME) {
		NAME = nAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return NAME;
	}

}

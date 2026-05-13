package com.mdsd.docare.hemodialysis.app.entity.server;

public class MedConfig {
	private String ITEM_ID;

	private String ITEM_VALUE;

	private String ITEM_NAME;

	private String ITEM_TYPE;

	private String STATUS;

	private int ORDER_NUMBER;

	public String getITEM_ID() {
		return ITEM_ID;
	}

	public void setITEM_ID(String iTEM_ID) {
		ITEM_ID = iTEM_ID;
	}

	public String getITEM_VALUE() {
		return ITEM_VALUE;
	}

	public void setITEM_VALUE(String iTEM_VALUE) {
		ITEM_VALUE = iTEM_VALUE;
	}

	public String getITEM_NAME() {
		return ITEM_NAME;
	}

	public void setITEM_NAME(String iTEM_NAME) {
		ITEM_NAME = iTEM_NAME;
	}

	public String getITEM_TYPE() {
		return ITEM_TYPE;
	}

	public void setITEM_TYPE(String iTEM_TYPE) {
		ITEM_TYPE = iTEM_TYPE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public int getORDER_NUMBER() {
		return ORDER_NUMBER;
	}

	public void setORDER_NUMBER(int oRDER_NUMBER) {
		ORDER_NUMBER = oRDER_NUMBER;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ITEM_NAME;
	}
	
}

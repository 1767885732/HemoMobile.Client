package com.mdsd.docare.hemodialysis.app.entity.app;

/**
 * 临时医嘱的listitem对应类
 * 
 * <br>
 * 
 * @author jianyu.l
 * @since 2014年8月19日
 */
public class TempOrdersListItem {

	/** 显示内容. */
	private String showName;

	/** 执行状态 0未执行，1已执行. */
	private int state;

	/**
	 * Instantiates a new temp orders list item.
	 */
	public TempOrdersListItem() {

	}

	/**
	 * Instantiates a new temp orders list item.
	 * 
	 * @param showName
	 *            the show name
	 * @param state
	 *            the state
	 */
	public TempOrdersListItem(String showName, int state){
		this.showName = showName;
		this.state = state;
	}

	/**
	 * Gets the 显示内容.
	 * 
	 * @return the 显示内容
	 */
	public String getShowName() {
		return showName;
	}

	/**
	 * Sets the 显示内容.
	 * 
	 * @param showName
	 *            the new 显示内容
	 */
	public void setShowName(String showName) {
		this.showName = showName;
	}

	/**
	 * Gets the 执行状态 0未执行，1已执行.
	 * 
	 * @return the 执行状态 0未执行，1已执行
	 */
	public int getState() {
		return state;
	}

	/**
	 * Sets the 执行状态 0未执行，1已执行.
	 * 
	 * @param state
	 *            the new 执行状态 0未执行，1已执行
	 */
	public void setState(int state) {
		this.state = state;
	}

}

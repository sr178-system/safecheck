package com.sr178.safecheck.app.bean;

public class CheckOptionsBean {

	private int optionId;
	private String optionTitle;
	public int getOptionId() {
		return optionId;
	}
	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}
	public String getOptionTitle() {
		return optionTitle;
	}
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	public CheckOptionsBean(int optionId, String optionTitle) {
		super();
		this.optionId = optionId;
		this.optionTitle = optionTitle;
	}
	public CheckOptionsBean() {
		super();
	}
}

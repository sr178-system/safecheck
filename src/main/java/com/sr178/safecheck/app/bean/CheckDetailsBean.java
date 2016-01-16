package com.sr178.safecheck.app.bean;

import java.util.List;

import com.google.common.collect.Lists;

public class CheckDetailsBean {

	private int checkBigId;
	private String checkBigTitle;
	private int checkSmallId;
	private String checkSmallTitle;
	
	private List<CheckOptionsBean> checkOptions = Lists.newArrayList();

	public int getCheckBigId() {
		return checkBigId;
	}

	public void setCheckBigId(int checkBigId) {
		this.checkBigId = checkBigId;
	}

	public String getCheckBigTitle() {
		return checkBigTitle;
	}

	public void setCheckBigTitle(String checkBigTitle) {
		this.checkBigTitle = checkBigTitle;
	}

	public int getCheckSmallId() {
		return checkSmallId;
	}

	public void setCheckSmallId(int checkSmallId) {
		this.checkSmallId = checkSmallId;
	}

	public String getCheckSmallTitle() {
		return checkSmallTitle;
	}

	public void setCheckSmallTitle(String checkSmallTitle) {
		this.checkSmallTitle = checkSmallTitle;
	}

	public List<CheckOptionsBean> getCheckOptions() {
		return checkOptions;
	}

	public void setCheckOptions(List<CheckOptionsBean> checkOptions) {
		this.checkOptions = checkOptions;
	}
	public void addOptions(int optionId, String optionTitle){
		checkOptions.add(new CheckOptionsBean(optionId, optionTitle));
	}
}

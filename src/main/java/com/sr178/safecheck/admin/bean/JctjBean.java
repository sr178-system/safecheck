package com.sr178.safecheck.admin.bean;


import java.util.List;

import com.google.common.collect.Lists;
import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.admin.bo.EnforceRecord;

public class JctjBean {
	private String cpName;
	private CheckRecord  checkRecord;
	private EnforceRecord enforceRecord;
	private List<String> itemsNames;
	
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public CheckRecord getCheckRecord() {
		return checkRecord;
	}
	public void setCheckRecord(CheckRecord checkRecord) {
		this.checkRecord = checkRecord;
	}
	public EnforceRecord getEnforceRecord() {
		return enforceRecord;
	}
	public void setEnforceRecord(EnforceRecord enforceRecord) {
		this.enforceRecord = enforceRecord;
	}
	public List<String> getItemsNames() {
		return itemsNames;
	}
	public void setItemsNames(List<String> itemsNames) {
		this.itemsNames = itemsNames;
	}
	
	public void addItemsName(String names){
		if(itemsNames==null){
			itemsNames = Lists.newArrayList();
		}
		itemsNames.add(names);
	}
}

package com.sr178.safecheck.admin.bean;

import java.util.List;

import com.sr178.safecheck.admin.bo.CheckItems;

public class CheckResultBean {

	private CheckItems firstItem;
	private CheckItems secondItem;
	private List<CheckItems> resultList;
	private String description;
	private List<String> resource;
	public CheckItems getFirstItem() {
		return firstItem;
	}
	public void setFirstItem(CheckItems firstItem) {
		this.firstItem = firstItem;
	}
	public CheckItems getSecondItem() {
		return secondItem;
	}
	public void setSecondItem(CheckItems secondItem) {
		this.secondItem = secondItem;
	}
	public List<CheckItems> getResultList() {
		return resultList;
	}
	public void setResultList(List<CheckItems> resultList) {
		this.resultList = resultList;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getResource() {
		return resource;
	}
	public void setResource(List<String> resource) {
		this.resource = resource;
	}
	public CheckResultBean() {
		super();
	}
}

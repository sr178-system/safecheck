package com.sr178.safecheck.app.bean;

import com.sr178.safecheck.admin.bo.CheckItems;

public class CheckItemBean {
	private int id;
	private String itemTitle;
	
	
	
	
	public CheckItemBean(int id, String itemTitle) {
		super();
		this.id = id;
		this.itemTitle = itemTitle;
	}
	public CheckItemBean(CheckItems checkItems){
		this.id = checkItems.getId();
		this.itemTitle = checkItems.getItemTitle();
	}
	
	public CheckItemBean() {
		super();
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
}

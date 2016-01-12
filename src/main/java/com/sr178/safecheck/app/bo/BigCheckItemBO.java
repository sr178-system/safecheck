package com.sr178.safecheck.app.bo;

import com.sr178.safecheck.admin.bo.CheckItems;

public class BigCheckItemBO {

	private Integer id;
	private String itemTitle;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
	public BigCheckItemBO(Integer id, String itemTitle) {
		super();
		this.id = id;
		this.itemTitle = itemTitle;
	}

	public BigCheckItemBO(CheckItems check) {
		super();
		this.id = check.getId();
		this.itemTitle = check.getItemTitle();
	}
	public BigCheckItemBO() {
		super();
	}
	
}

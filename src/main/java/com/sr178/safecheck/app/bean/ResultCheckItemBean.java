package com.sr178.safecheck.app.bean;

import com.sr178.safecheck.admin.bo.CheckItems;

public class ResultCheckItemBean extends CheckItemBean{

	public ResultCheckItemBean() {
		super();
	}

	public ResultCheckItemBean(int id, String itemTitle) {
		super(id, itemTitle);
	}

	public ResultCheckItemBean(CheckItems checkItems) {
		super(checkItems);
	}
	
	
}

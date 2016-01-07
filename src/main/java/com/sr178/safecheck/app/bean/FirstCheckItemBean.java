package com.sr178.safecheck.app.bean;

import java.util.List;

public class FirstCheckItemBean extends CheckItemBean{


	private List<SecondCheckItemBean> downList;

	public List<SecondCheckItemBean> getDownList() {
		return downList;
	}

	public void setDownList(List<SecondCheckItemBean> downList) {
		this.downList = downList;
	}

	public FirstCheckItemBean() {
		super();
	}

	public FirstCheckItemBean(int id, String itemTitle) {
		super(id, itemTitle);
	}
	
	
}

package com.sr178.safecheck.app.bean;

import java.util.List;

import com.sr178.safecheck.admin.bo.CheckItems;

public class SecondCheckItemBean extends CheckItemBean{
	private List<ResultCheckItemBean> resultList;
	public List<ResultCheckItemBean> getResultList() {
		return resultList;
	}
	public void setResultList(List<ResultCheckItemBean> resultList) {
		this.resultList = resultList;
	}
	public SecondCheckItemBean() {
		super();
	}
	public SecondCheckItemBean(int id, String itemTitle) {
		super(id, itemTitle);
	}
	public SecondCheckItemBean(CheckItems checkItems) {
		super(checkItems);
	}
	
}

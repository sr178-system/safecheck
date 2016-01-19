package com.sr178.safecheck.app.bean;


import com.sr178.safecheck.admin.bean.CheckResultBean;

public class CheckDetailsForAdminBean extends CheckDetailsBean{
   private CheckResultBean result;
   private int secondSize;
   

	public CheckResultBean getResult() {
		return result;
	}
	
	public void setResult(CheckResultBean result) {
		this.result = result;
	}

	public int getSecondSize() {
		return secondSize;
	}

	public void setSecondSize(int secondSize) {
		this.secondSize = secondSize;
	}
}

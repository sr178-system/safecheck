package com.sr178.safecheck.admin.bean;

import java.util.List;

import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.admin.bo.EnforceRecord;
import com.sr178.safecheck.app.bean.CheckDetailsForAdminBean;

public class CheckRecordDetailsBean {

	private CheckRecord checkRecord;
	
	private List<CheckDetailsForAdminBean> checkDetails;
	
	
	private List<EnforceRecord> enforceList;

	public CheckRecord getCheckRecord() {
		return checkRecord;
	}
	public void setCheckRecord(CheckRecord checkRecord) {
		this.checkRecord = checkRecord;
	}
	public List<CheckDetailsForAdminBean> getCheckDetails() {
		return checkDetails;
	}
	public void setCheckDetails(List<CheckDetailsForAdminBean> checkDetails) {
		this.checkDetails = checkDetails;
	}

	public List<EnforceRecord> getEnforceList() {
		return enforceList;
	}

	public void setEnforceList(List<EnforceRecord> enforceList) {
		this.enforceList = enforceList;
	}
}

package com.sr178.safecheck.admin.bean;

import java.util.List;
import java.util.Map;

import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.admin.bo.EnforceRecord;
import com.sr178.safecheck.app.bean.ZeroCheckItemBean;

public class CheckRecordDetailsBean {

	private CheckRecord checkRecord;
	
	private ZeroCheckItemBean checkModelBean;
	
	private Map<String,CheckResultBean> resultMap;
	
	private List<EnforceRecord> enforceList;

	public CheckRecord getCheckRecord() {
		return checkRecord;
	}

	public void setCheckRecord(CheckRecord checkRecord) {
		this.checkRecord = checkRecord;
	}

	public ZeroCheckItemBean getCheckModelBean() {
		return checkModelBean;
	}

	public void setCheckModelBean(ZeroCheckItemBean checkModelBean) {
		this.checkModelBean = checkModelBean;
	}

	public Map<String, CheckResultBean> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, CheckResultBean> resultMap) {
		this.resultMap = resultMap;
	}

	public List<EnforceRecord> getEnforceList() {
		return enforceList;
	}

	public void setEnforceList(List<EnforceRecord> enforceList) {
		this.enforceList = enforceList;
	}
}

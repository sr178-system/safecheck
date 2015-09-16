package com.sr178.safecheck.admin.bean;

import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.admin.bo.EnforceRecord;

/**
 * 检查督查
 * @author ThinkPad User
 *
 */
public class JcdcBean {
	private String userName;
	private EnforceRecord enforceRecord;
	private CheckRecord checkRecord;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public EnforceRecord getEnforceRecord() {
		return enforceRecord;
	}
	public void setEnforceRecord(EnforceRecord enforceRecord) {
		this.enforceRecord = enforceRecord;
	}
	public CheckRecord getCheckRecord() {
		return checkRecord;
	}
	public void setCheckRecord(CheckRecord checkRecord) {
		this.checkRecord = checkRecord;
	}
}

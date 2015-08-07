package com.sr178.safecheck.common.action;


import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
/**
 * @author hzy
 * 2012-7-20
 */
public class ALDAdminActionSupport extends ActionSupport {
	/**  */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	/** 错误码 */
	private int erroCodeNum;
	/** 错误描述 */
	private String erroDescrip;
	

	public int getErroCodeNum() {
		return erroCodeNum;
	}

	public void setErroCodeNum(int erroCodeNum) {
		this.erroCodeNum = erroCodeNum;
	}

	public String getErroDescrip() {
		return erroDescrip;
	}

	public void setErroDescrip(String erroDescrip) {
		this.erroDescrip = erroDescrip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String ip(){
		return ServletActionContext.getRequest().getRemoteAddr();
	}
}

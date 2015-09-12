package com.sr178.safecheck.common.action;


import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
/**
 * @author dd
 * 2012-7-20
 */
public class ALDAdminActionSupport extends ActionSupport {
	/**  */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	/** 错误码 */
	private int code;
	/** 错误描述 */
	private String erroDescrip;
	


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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public String getSessionId(){
		HttpSession sessionhttp = ServletActionContext.getRequest()
				.getSession();
		return sessionhttp.getId();
	}
	
}

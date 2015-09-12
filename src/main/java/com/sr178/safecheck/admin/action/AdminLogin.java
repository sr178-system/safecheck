package com.sr178.safecheck.admin.action;


import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.common.action.ALDAdminActionSupport;

public class AdminLogin extends ALDAdminActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String passWord;

	public String execute() {
	   AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
	   adminService.login(userName, passWord, super.getSessionId());
	   return SUCCESS;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public int getCode() {
		return super.getCode();
	}
}

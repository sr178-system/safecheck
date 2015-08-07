package com.sr178.safecheck.user.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.sr178.safecheck.common.action.ALDAdminActionSupport;

public class AdminLogin extends ALDAdminActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String adminName;
	private String adminPassword;
	private String randString;
	private Integer version;
	public String getRandString() {
		return randString;
	}

	public void setRandString(String randString) {
		this.randString = randString;
	}

	public String execute() {
		if (adminName == null || adminPassword == null)
			return SUCCESS;

//		UserService aus = ServiceCacheFactory.getServiceCache()
//				.getService(UserService.class);
		HttpSession sessionhttp = ServletActionContext.getRequest()
				.getSession();
		String rand = (String) sessionhttp.getAttribute("rand");
		if (rand == null) {
			super.setErroCodeNum(1);
			super.setErroDescrip("验证码过期！");
			return SUCCESS;
		}
		if (!rand.equals(randString)) {
			super.setErroCodeNum(2);
			super.setErroDescrip("验证码不正确！");
			return SUCCESS;
		}
//		//Gcuser au = aus.login(sessionhttp.getId(), adminName, adminPassword,ServletActionContext.getRequest().getRemoteAddr());
//		if (au == null) {
//			super.setErroCodeNum(3);
//			super.setErroDescrip("登录失败，用户名或密码错误");
//			return SUCCESS;
//		} else {
////			if(au.getGanew()!=0){
////				super.setErroCodeNum(4);//重定向去修改用户信息
////				return SUCCESS;
////			}
//			return SUCCESS;
//		}
		return SUCCESS;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getErroDescrip() {
		return super.getErroDescrip();
	}
	public int getErroCodeNum() {
		return super.getErroCodeNum();
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}

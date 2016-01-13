package com.sr178.safecheck.admin.bean;

public class UserInfo {
    private String userName;
    private int roleType;//0 普通用户  1管理员  2超级管理员
    private String departMent;
    private String name;
    
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getRoleType() {
		return roleType;
	}
	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}
	public String getDepartMent() {
		return departMent;
	}
	public void setDepartMent(String departMent) {
		this.departMent = departMent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UserInfo(String userName, int roleType, String departMent) {
		super();
		this.userName = userName;
		this.roleType = roleType;
		this.departMent = departMent;
	}
	public UserInfo() {
		super();
	}
	
	public String getCurrentDepartMent(){
		if(roleType==1){
			return departMent;
		}
		return null;
	}
    
}

package com.sr178.safecheck.admin.bo;

import java.util.Date;
   /**
    * admin_user 实体类
    */ 


public class AdminUser{
	private String userName;
	private String passWord;
	private String name;
	private int sex;
	private Date birthday;
	private String call;
	private String remark;
	private String upUser="";
	private int status;
	private String departMent;
	public void setUserName(String userName){
	this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}
	public void setPassWord(String passWord){
	this.passWord=passWord;
	}
	public String getPassWord(){
		return passWord;
	}
	public void setName(String name){
	this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setSex(int sex){
	this.sex=sex;
	}
	public int getSex(){
		return sex;
	}
	public void setBirthday(Date birthday){
	this.birthday=birthday;
	}
	public Date getBirthday(){
		return birthday;
	}
	public void setCall(String call){
	this.call=call;
	}
	public String getCall(){
		return call;
	}
	public void setRemark(String remark){
	this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}
	public void setUpUser(String upUser){
	this.upUser=upUser;
	}
	public String getUpUser(){
		return upUser;
	}
	public void setStatus(int status){
	this.status=status;
	}
	public int getStatus(){
		return status;
	}
	public AdminUser(String userName, String passWord, String name, int sex, Date birthday, String call, String remark,
			String upUser) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.call = call;
		this.remark = remark;
		this.upUser = upUser;
	}
	public AdminUser() {
	}
	public String getDepartMent() {
		return departMent;
	}
	public void setDepartMent(String departMent) {
		this.departMent = departMent;
	}
}


package com.sr178.safecheck.admin.bo;

   /**
    * user 实体类
    */ 


public class User{
	private String userName;
	private String passWord;
	private String name;
	private int sex;
	private String call;
	private String remark;
	private int status;
	private String departMent;
	private String lastEditName;
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
	public void setStatus(int status){
	this.status=status;
	}
	public int getStatus(){
		return status;
	}
	public User(String userName, String passWord, String name, int sex, String call, String remark, String departMent) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.name = name;
		this.sex = sex;
		this.call = call;
		this.remark = remark;
		this.departMent = departMent;
	}
	public User() {
	}
	public String getDepartMent() {
		return departMent;
	}
	public void setDepartMent(String departMent) {
		this.departMent = departMent;
	}
	public String getLastEditName() {
		return lastEditName;
	}
	public void setLastEditName(String lastEditName) {
		this.lastEditName = lastEditName;
	}
}


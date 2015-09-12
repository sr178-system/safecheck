package com.sr178.safecheck.admin.bo;

import java.util.Date;
   /**
    * check_record 实体类
    */ 


public class CheckRecord{
	private Integer id;
	private String cpName;
	private String checkItems;
	private String checkUsername;
	private String checkerName;
	private int resourceId;
	private String resource1Names;
	private String resource2Names;
	private String resource3Names;
	private String position;
	private Date checkTime;
	private Date checkServerTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setCpName(String cpName){
	this.cpName=cpName;
	}
	public String getCpName(){
		return cpName;
	}
	public void setCheckItems(String checkItems){
	this.checkItems=checkItems;
	}
	public String getCheckItems(){
		return checkItems;
	}
	public void setCheckUsername(String checkUsername){
	this.checkUsername=checkUsername;
	}
	public String getCheckUsername(){
		return checkUsername;
	}
	public void setCheckerName(String checkerName){
	this.checkerName=checkerName;
	}
	public String getCheckerName(){
		return checkerName;
	}
	public void setResourceId(int resourceId){
	this.resourceId=resourceId;
	}
	public int getResourceId(){
		return resourceId;
	}
	public void setCheckTime(Date checkTime){
	this.checkTime=checkTime;
	}
	public Date getCheckTime(){
		return checkTime;
	}
	public Date getCheckServerTime() {
		return checkServerTime;
	}
	public void setCheckServerTime(Date checkServerTime) {
		this.checkServerTime = checkServerTime;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getResource1Names() {
		return resource1Names;
	}
	public void setResource1Names(String resource1Names) {
		this.resource1Names = resource1Names;
	}
	public String getResource2Names() {
		return resource2Names;
	}
	public void setResource2Names(String resource2Names) {
		this.resource2Names = resource2Names;
	}
	public String getResource3Names() {
		return resource3Names;
	}
	public void setResource3Names(String resource3Names) {
		this.resource3Names = resource3Names;
	}
}


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
	private Date checkTime;
	
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
}


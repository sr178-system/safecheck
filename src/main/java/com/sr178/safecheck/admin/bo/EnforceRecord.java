package com.sr178.safecheck.admin.bo;

import java.util.Date;
   /**
    * enforce_record 实体类
    */ 


public class EnforceRecord{
	private Integer id;
	private String cpName;
	private String enforceUsername;
	private String enforceName;
	private int resourceId;
	private Date enforceTime;
	private Date enforceServerTime;
	
	
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
	public void setEnforceUsername(String enforceUsername){
	this.enforceUsername=enforceUsername;
	}
	public String getEnforceUsername(){
		return enforceUsername;
	}
	public void setEnforceName(String enforceName){
	this.enforceName=enforceName;
	}
	public String getEnforceName(){
		return enforceName;
	}
	public void setResourceId(int resourceId){
	this.resourceId=resourceId;
	}
	public int getResourceId(){
		return resourceId;
	}
	public void setEnforceTime(Date enforceTime){
	this.enforceTime=enforceTime;
	}
	public Date getEnforceTime(){
		return enforceTime;
	}
	public Date getEnforceServerTime() {
		return enforceServerTime;
	}
	public void setEnforceServerTime(Date enforceServerTime) {
		this.enforceServerTime = enforceServerTime;
	}
}


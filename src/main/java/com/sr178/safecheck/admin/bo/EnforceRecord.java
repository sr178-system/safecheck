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
	
	private String resource1Names;
	private String resource2Names;
	private String resource3Names;
	
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


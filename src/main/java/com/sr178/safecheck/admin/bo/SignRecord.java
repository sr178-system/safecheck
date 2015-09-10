package com.sr178.safecheck.admin.bo;

import java.util.Date;
   /**
    * sign_record 实体类
    */ 


public class SignRecord{
	private Integer id;
	private String cpName;
	private String signerUsername;
	private String signerName;
	private int resourceId;
	private String position;
	private Date signTime;
	
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
	public void setSignerUsername(String signerUsername){
	this.signerUsername=signerUsername;
	}
	public String getSignerUsername(){
		return signerUsername;
	}
	public void setSignerName(String signerName){
	this.signerName=signerName;
	}
	public String getSignerName(){
		return signerName;
	}
	public void setResourceId(int resourceId){
	this.resourceId=resourceId;
	}
	public int getResourceId(){
		return resourceId;
	}
	public void setPosition(String position){
	this.position=position;
	}
	public String getPosition(){
		return position;
	}
	public void setSignTime(Date signTime){
	this.signTime=signTime;
	}
	public Date getSignTime(){
		return signTime;
	}
}


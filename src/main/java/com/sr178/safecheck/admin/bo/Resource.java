package com.sr178.safecheck.admin.bo;

import java.util.Date;
   /**
    * resource 实体类
    */ 


public class Resource{
	private Integer resourceId;
	private String resource1Names;
	private String resource2Names;
	private String resource3Names;
	private String resource4Names;
	private Date updatedTime;
	private Date createdTime;
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public void setResource1Names(String resource1Names){
	this.resource1Names=resource1Names;
	}
	public String getResource1Names(){
		return resource1Names;
	}
	public void setResource2Names(String resource2Names){
	this.resource2Names=resource2Names;
	}
	public String getResource2Names(){
		return resource2Names;
	}
	public void setUpdatedTime(Date updatedTime){
	this.updatedTime=updatedTime;
	}
	public Date getUpdatedTime(){
		return updatedTime;
	}
	public void setCreatedTime(Date createdTime){
	this.createdTime=createdTime;
	}
	public Date getCreatedTime(){
		return createdTime;
	}
	public String getResource3Names() {
		return resource3Names;
	}
	public void setResource3Names(String resource3Names) {
		this.resource3Names = resource3Names;
	}
	public String getResource4Names() {
		return resource4Names;
	}
	public void setResource4Names(String resource4Names) {
		this.resource4Names = resource4Names;
	}
}


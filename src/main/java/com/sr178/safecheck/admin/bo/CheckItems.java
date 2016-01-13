package com.sr178.safecheck.admin.bo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
   /**
    * check_items 实体类
    */ 


public class CheckItems{
	private Integer id;
	private String itemTitle;
	private Date addTime;
	private int parentId;
	private String departMent;
	private int layer;
	private String lastEditName;
	private int successOrFail;
	private Date editTime;
	private int status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setItemTitle(String itemTitle){
	this.itemTitle=itemTitle;
	}
	public String getItemTitle(){
		return itemTitle;
	}
	public void setAddTime(Date addTime){
	this.addTime=addTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddTime(){
		return addTime;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getDepartMent() {
		return departMent;
	}
	public void setDepartMent(String departMent) {
		this.departMent = departMent;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public String getLastEditName() {
		return lastEditName;
	}
	public void setLastEditName(String lastEditName) {
		this.lastEditName = lastEditName;
	}
	public int getSuccessOrFail() {
		return successOrFail;
	}
	public void setSuccessOrFail(int successOrFail) {
		this.successOrFail = successOrFail;
	}
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}


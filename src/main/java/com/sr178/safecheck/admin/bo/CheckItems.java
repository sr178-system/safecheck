package com.sr178.safecheck.admin.bo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
   /**
    * check_items 实体类
    */ 


public class CheckItems{
	private Integer id;
	private String itemTitle;
	private String itemContent;
	private Date addTime;
	
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
	public void setItemContent(String itemContent){
	this.itemContent=itemContent;
	}
	public String getItemContent(){
		return itemContent;
	}
	public void setAddTime(Date addTime){
	this.addTime=addTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddTime(){
		return addTime;
	}
}


package com.sr178.safecheck.admin.bo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
   /**
    * notice 实体类
    */ 


public class Notice{
	private Integer id;
	private String noticeTitle;
	private String noticeContent;
	private int status;
	private Date addTime;
	private String attachMent;
	private String departMent;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setNoticeTitle(String noticeTitle){
	this.noticeTitle=noticeTitle;
	}
	public String getNoticeTitle(){
		return noticeTitle;
	}
	public void setNoticeContent(String noticeContent){
	this.noticeContent=noticeContent;
	}
	public String getNoticeContent(){
		return noticeContent;
	}
	public void setAddTime(Date addTime){
	this.addTime=addTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getAddTime(){
		return addTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAttachMent() {
		return attachMent;
	}
	public void setAttachMent(String attachMent) {
		this.attachMent = attachMent;
	}
	public String getDepartMent() {
		return departMent;
	}
	public void setDepartMent(String departMent) {
		this.departMent = departMent;
	}
}


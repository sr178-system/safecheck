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
	private int read;
	private Date addTime;
	private String attachMent;
	private String departMent;
	private String lastEditName;
	private Date editTime;
	
	
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
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
	public String getLastEditName() {
		return lastEditName;
	}
	public void setLastEditName(String lastEditName) {
		this.lastEditName = lastEditName;
	}
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
}


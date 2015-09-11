package com.sr178.safecheck.admin.bo;

import java.util.Date;
   /**
    * notice 实体类
    */ 


public class Notice{
	private Integer id;
	private String noticeTitle;
	private String noticeContent;
	private int status;
	private Date addTime;
	
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
	public Date getAddTime(){
		return addTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}


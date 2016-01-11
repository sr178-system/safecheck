package com.sr178.safecheck.admin.bo;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
   /**
    * check_record 实体类
    */ 


public class CheckRecord{
	private Integer id;
	private String cpName;
	private String cn;
	private int checkItemId;
	private String checkItemName;
	private String checkResult;
	private String checkUsername;
	private String checkerName;
	private Integer resourceId;
	private String resource1Names;
	private String resource2Names;
	private String resource3Names;
	private String resource4Names;
	private String position;
	private Date checkTime;
	private Date checkServerTime;
	private String resPersonName;
	private String resPersonCall;
	private int passStatus;//1全部通过  0 未全部通过
	
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
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public void setCheckTime(Date checkTime){
	this.checkTime=checkTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCheckTime(){
		return checkTime;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCheckServerTime() {
		return checkServerTime;
	}
	public void setCheckServerTime(Date checkServerTime) {
		this.checkServerTime = checkServerTime;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public int getCheckItemId() {
		return checkItemId;
	}
	public void setCheckItemId(int checkItemId) {
		this.checkItemId = checkItemId;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getResource4Names() {
		return resource4Names;
	}
	public void setResource4Names(String resource4Names) {
		this.resource4Names = resource4Names;
	}
	public String getResPersonName() {
		return resPersonName;
	}
	public void setResPersonName(String resPersonName) {
		this.resPersonName = resPersonName;
	}
	public String getResPersonCall() {
		return resPersonCall;
	}
	public void setResPersonCall(String resPersonCall) {
		this.resPersonCall = resPersonCall;
	}
	public String getCheckItemName() {
		return checkItemName;
	}
	public void setCheckItemName(String checkItemName) {
		this.checkItemName = checkItemName;
	}
	public int getPassStatus() {
		return passStatus;
	}
	public void setPassStatus(int passStatus) {
		this.passStatus = passStatus;
	}
}


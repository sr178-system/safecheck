package com.sr178.safecheck.admin.bean;

import java.util.Date;

public class MixCheckAndEnforceBean {
	
	private int id;
	private String cpName;
	private String checkItems;
	private String checkItemNames;
	private String username;
	private String name;
	private int resourceId;
	private String position;
	private Date times;
	private int type;//1检查对象   2执法对象
	private String resource1Names;
	private String resource2Names;
	private String resource3Names;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public String getCheckItems() {
		return checkItems;
	}
	public void setCheckItems(String checkItems) {
		this.checkItems = checkItems;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Date getTimes() {
		return times;
	}
	public long getTimesc() {
		return times.getTime();
	}
	public void setTimes(Date times) {
		this.times = times;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public String getCheckItemNames() {
		return checkItemNames;
	}
	public void setCheckItemNames(String checkItemNames) {
		this.checkItemNames = checkItemNames;
	}
}

package com.sr178.safecheck.app.bean;

public class TrainRecordSimple {
	private String name;
	private String certNum;
	private String canWorkType ;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getCanWorkType() {
		return canWorkType;
	}
	public void setCanWorkType(String canWorkType) {
		this.canWorkType = canWorkType;
	}
	@Override
	public String toString() {
		return "TrainRecordSimple [name=" + name + ", certNum=" + certNum + ", canWorkType=" + canWorkType + "]";
	}
}

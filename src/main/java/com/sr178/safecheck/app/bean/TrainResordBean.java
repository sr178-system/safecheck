package com.sr178.safecheck.app.bean;

public class TrainResordBean {

	private String name;
	private String certNum;
	private String sex ;
	private String idCard ;
	private String workType ;
	private String canWorkType ;
	private String workCp ;
	private String trainCp ;
	private String certCp ;
	private String  certTime;
	private String  effectTimeStart;
	private String  effectTimeEnd;
	private String workScore;
	private String theoryScore;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getCanWorkType() {
		return canWorkType;
	}
	public void setCanWorkType(String canWorkType) {
		this.canWorkType = canWorkType;
	}
	public String getWorkCp() {
		return workCp;
	}
	public void setWorkCp(String workCp) {
		this.workCp = workCp;
	}
	public String getTrainCp() {
		return trainCp;
	}
	public void setTrainCp(String trainCp) {
		this.trainCp = trainCp;
	}
	public String getCertCp() {
		return certCp;
	}
	public void setCertCp(String certCp) {
		this.certCp = certCp;
	}
	public String getCertTime() {
		return certTime;
	}
	public void setCertTime(String certTime) {
		this.certTime = certTime;
	}
	public String getEffectTimeStart() {
		return effectTimeStart;
	}
	public void setEffectTimeStart(String effectTimeStart) {
		this.effectTimeStart = effectTimeStart;
	}
	public String getEffectTimeEnd() {
		return effectTimeEnd;
	}
	public void setEffectTimeEnd(String effectTimeEnd) {
		this.effectTimeEnd = effectTimeEnd;
	}
	public String getWorkScore() {
		return workScore;
	}
	public void setWorkScore(String workScore) {
		this.workScore = workScore;
	}
	public String getTheoryScore() {
		return theoryScore;
	}
	public void setTheoryScore(String theoryScore) {
		this.theoryScore = theoryScore;
	}
	@Override
	public String toString() {
		return "TrainResordBean [name=" + name + ", certNum=" + certNum + ", sex=" + sex + ", idCard=" + idCard
				+ ", workType=" + workType + ", canWorkType=" + canWorkType + ", workCp=" + workCp + ", trainCp="
				+ trainCp + ", certCp=" + certCp + ", certTime=" + certTime + ", effectTimeStart=" + effectTimeStart
				+ ", effectTimeEnd=" + effectTimeEnd + ", workScore=" + workScore + ", theoryScore=" + theoryScore
				+ "]";
	}
}

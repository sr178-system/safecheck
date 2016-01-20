package com.sr178.safecheck.admin.action;

import java.util.List;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.bean.CheckRecordDetailsBean;
import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.app.bo.BigCheckItemBO;
import com.sr178.safecheck.common.action.ALDAdminPageActionSupport;

public class CheckRecordAction extends ALDAdminPageActionSupport<CheckRecord> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	private String searchCp;
	private String checkName;
	private Integer checkId;
	private Integer checkResult;
	private List<BigCheckItemBO> bigCheckList;
	/**
	 * 检查列表
	 * @return
	 */
	public String recordList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		bigCheckList = adminService.getAllParentCheckItems(super.getSessionId());
		super.initPage(adminService.getCheckRecordPage(super.getSessionId(), startDate, endDate, searchCp, checkName, checkId, checkResult, super.getToPage(), 30)); 
		return SUCCESS;
	}
 
	/**
	 * 检查详情
	 */
	private int recordId;
	private CheckRecordDetailsBean bean;
	public String recordDetails(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		bean = adminService.getCheckRecordDetailsBean(recordId);
		return SUCCESS;
	}

	public String getSearchCp() {
		return searchCp;
	}
	public void setSearchCp(String searchCp) {
		this.searchCp = searchCp;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public Integer getCheckId() {
		return checkId;
	}
	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}
	public Integer getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}
	public List<BigCheckItemBO> getBigCheckList() {
		return bigCheckList;
	}
	public void setBigCheckList(List<BigCheckItemBO> bigCheckList) {
		this.bigCheckList = bigCheckList;
	}
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public CheckRecordDetailsBean getBean() {
		return bean;
	}
	public void setBean(CheckRecordDetailsBean bean) {
		this.bean = bean;
	}

}

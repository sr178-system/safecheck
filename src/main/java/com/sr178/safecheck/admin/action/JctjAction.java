package com.sr178.safecheck.admin.action;

import java.util.Collection;

import com.sr178.common.jdbc.bean.IPage;
import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.bean.JctjBean;
import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.common.action.ALDAdminPageActionSupport;

public class JctjAction extends ALDAdminPageActionSupport<JctjBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String searchCp;
	
	public String cpList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		super.initPage(adminService.getJctjBeanPageList(searchCp,super.getToPage(), 30)); 
		return SUCCESS;
	}
	
    private int indexPage;
    private int pageSize;
	private String cpName;
	private int total;
	private Collection<CheckRecord> list;
	public String checkList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		IPage<CheckRecord> page = adminService.getCheckRecordPage(cpName, indexPage, pageSize);
		if(page!=null&&page.getData()!=null&&page.getData().size()>0){
			list = page.getData();
			total = (int)page.getTotalSize();
		}
		return SUCCESS;
	}
	

	public String getSearchCp() {
		return searchCp;
	}

	public void setSearchCp(String searchCp) {
		this.searchCp = searchCp;
	}

	public int getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public Collection<CheckRecord> getList() {
		return list;
	}

	public void setList(Collection<CheckRecord> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}

package com.sr178.safecheck.admin.action;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.bean.JctjBean;
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

	public String getSearchCp() {
		return searchCp;
	}

	public void setSearchCp(String searchCp) {
		this.searchCp = searchCp;
	}
}

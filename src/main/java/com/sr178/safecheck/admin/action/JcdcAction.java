package com.sr178.safecheck.admin.action;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.bean.JcdcBean;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.common.action.ALDAdminPageActionSupport;

public class JcdcAction extends ALDAdminPageActionSupport<JcdcBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String searchUn;
	
	public String unList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		super.initPage(adminService.getJcdcBeanPageList(super.getUserName(), searchUn, super.getToPage(), 10)); 
		return SUCCESS;
	}
	
	

	public String getSearchUn() {
		return searchUn;
	}

	public void setSearchUn(String searchUn) {
		this.searchUn = searchUn;
	}
}

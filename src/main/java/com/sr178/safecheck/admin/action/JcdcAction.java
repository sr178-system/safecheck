package com.sr178.safecheck.admin.action;

import java.util.Collection;

import com.sr178.common.jdbc.bean.IPage;
import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.bean.JcdcBean;
import com.sr178.safecheck.admin.bean.MixCheckAndEnforceBean;
import com.sr178.safecheck.admin.bo.User;
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
//		super.initPage(adminService.getJcdcBeanPageList(super.getUserName(), searchUn, super.getToPage(), 10)); 
		return SUCCESS;
	}
	
	private String efUserName;
	private User enforceUser;
    private int indexPage;
    private int pageSize;
	private int total;
	private Collection<MixCheckAndEnforceBean> list;
	public String enforceList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		enforceUser = adminService.getByUserName(efUserName);
		IPage<MixCheckAndEnforceBean> ipage = adminService.getJcdcDetailsPageList(efUserName, indexPage, pageSize);
		if(ipage!=null&&ipage.getData()!=null){
			list = ipage.getData();
		}
		return SUCCESS;
	}
	

	public String getSearchUn() {
		return searchUn;
	}

	public void setSearchUn(String searchUn) {
		this.searchUn = searchUn;
	}



	public String getEfUserName() {
		return efUserName;
	}


	public void setEfUserName(String efUserName) {
		this.efUserName = efUserName;
	}


	public User getEnforceUser() {
		return enforceUser;
	}


	public void setEnforceUser(User enforceUser) {
		this.enforceUser = enforceUser;
	}


	public int getIndexPage() {
		return indexPage;
	}


	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
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
	public Collection<MixCheckAndEnforceBean> getList() {
		return list;
	}
	public void setList(Collection<MixCheckAndEnforceBean> list) {
		this.list = list;
	}
}

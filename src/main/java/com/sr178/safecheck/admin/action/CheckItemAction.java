package com.sr178.safecheck.admin.action;

import java.util.List;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.bo.CheckItems;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.common.action.ALDAdminPageActionSupport;

public class CheckItemAction extends ALDAdminPageActionSupport<CheckItems> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<CheckItems> list;
	public String showList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
        list = adminService.getCheckItemsPageList();
		return SUCCESS;
	}
	private Integer id;
	private String title;
	private String content1;
	public String add(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		if(id==null){
			adminService.addCheckItems(title, content1);
		}else{
			adminService.editCheckItems(id, title, content1);
		}
		return SUCCESS;
	}
	
	private int[] ids;
	public String delete(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.deleteCheckItems(ids);
		return SUCCESS;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent1() {
		return content1;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
	}

	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<CheckItems> getList() {
		return list;
	}

	public void setList(List<CheckItems> list) {
		this.list = list;
	}
}

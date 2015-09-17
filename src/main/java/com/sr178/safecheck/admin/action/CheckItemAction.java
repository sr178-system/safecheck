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
	
	private String title;
	private String content;
	public String add(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.addCheckItems(title, content);
		return SUCCESS;
	}
	
	private int[] ids;
	public String delete(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.deleteCheckItems(ids);
		return SUCCESS;
	}
	
	private int id;
	public String edit(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.editCheckItems(id, title, content);
		return SUCCESS;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<CheckItems> getList() {
		return list;
	}

	public void setList(List<CheckItems> list) {
		this.list = list;
	}
}

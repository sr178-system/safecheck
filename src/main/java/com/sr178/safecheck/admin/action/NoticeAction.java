package com.sr178.safecheck.admin.action;

import java.util.List;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.bo.Notice;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.common.action.ALDAdminPageActionSupport;

public class NoticeAction extends ALDAdminPageActionSupport<Notice> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Notice> list;
	public String showList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
        list = adminService.getNoticeList();
		return SUCCESS;
	}
	
	
	
	
	private Integer id;
	private String title;
	private String content1;
	public String add(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		if(id==null){
			adminService.addNotice(title, content1);
		}else{
			adminService.editNotice(id, title, content1);
		}
		return SUCCESS;
	}
	
	private int[] ids;
	public String delete(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.deleteNotice(ids);
		return SUCCESS;
	}
	
	
//	public String edit(){
//		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
//		adminService.editNotice(id, title, content);
//		return SUCCESS;
//	}
	
	private int status;
	public String editNoticeStatus(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.editNoticeStatus(ids, status); 
		return SUCCESS;
	}
	private Notice notice;
	public String getOne(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		notice = adminService.getOne(id);
		return SUCCESS;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int[] getIds() {
		return ids;
	}

	public String getContent1() {
		return content1;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
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
	public List<Notice> getList() {
		return list;
	}

	public void setList(List<Notice> list) {
		this.list = list;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}
}

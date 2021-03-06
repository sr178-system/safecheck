package com.sr178.safecheck.admin.action;

import java.io.File;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.bo.Notice;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.common.action.ALDAdminPageActionSupport;

public class NoticeAction extends ALDAdminPageActionSupport<Notice> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String showList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
        super.initPage(adminService.getNoticeList(super.getSessionId(),super.getToPage(),10));
		return SUCCESS;
	}
	
	
	private Integer id;
	private String title;
	private String content1;
	private String attachMent;
	private int st;
	public String addOrEdit(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		
		if(st==0){
			if(id==null){
				return SUCCESS;
			}else{
				Notice notice = adminService.getOne(id);
				if(notice!=null){
					title = notice.getNoticeTitle();
					content1 = notice.getNoticeContent();
					attachMent = notice.getAttachMent();
				}
				return SUCCESS;
			}
		}
		
		
		if(id==null){
			id = adminService.addNotice(super.getSessionId(), title, content1, attachMent);
			super.setCode(2000);
		}else{
			adminService.editNotice(super.getSessionId(), id, title, content1, attachMent);
			super.setCode(2001);
		}
		return "redirect";
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
	
	
	
	
	/**
	 * 图片上传
	 */
	private List<File> attach;
	private List<String> attachFileName;
	private List<String> attachContentType;
	private String serverFileName;
	private String clientFileName;
	private String size;
	public String uploadAttach() {
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		String path = ServletActionContext.getServletContext().getRealPath("/");
		String descDirectoryPath = path + "/uploads/attach/";
		clientFileName = attachFileName.get(0);
		String[] result = adminService.saveAttach(attach.get(0), attachFileName.get(0), descDirectoryPath);
		serverFileName = result[0];
		size = result[1];
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

	public String getAttachMent() {
		return attachMent;
	}

	public void setAttachMent(String attachMent) {
		this.attachMent = attachMent;
	}
	public List<File> getAttach() {
		return attach;
	}
	public void setAttach(List<File> attach) {
		this.attach = attach;
	}
	public List<String> getAttachFileName() {
		return attachFileName;
	}
	public void setAttachFileName(List<String> attachFileName) {
		this.attachFileName = attachFileName;
	}
	public List<String> getAttachContentType() {
		return attachContentType;
	}
	public void setAttachContentType(List<String> attachContentType) {
		this.attachContentType = attachContentType;
	}
	public String getServerFileName() {
		return serverFileName;
	}
	public void setServerFileName(String serverFileName) {
		this.serverFileName = serverFileName;
	}
	public String getClientFileName() {
		return clientFileName;
	}
	public void setClientFileName(String clientFileName) {
		this.clientFileName = clientFileName;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getSt() {
		return st;
	}
	public void setSt(int st) {
		this.st = st;
	}
}

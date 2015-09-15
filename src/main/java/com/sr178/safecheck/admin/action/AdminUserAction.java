package com.sr178.safecheck.admin.action;

import java.util.Date;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.bo.AdminUser;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.common.action.ALDAdminPageActionSupport;
import com.sr178.safecheck.common.utils.DateUtils;

public class AdminUserAction extends ALDAdminPageActionSupport<AdminUser> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 获取管理员列表
	 * @return
	 */
	public String showList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		super.initPage(adminService.getAdminUserPageList(super.getToPage(), 20));
		return SUCCESS;
	}
	
	/**
	 * 登出
	 * @return
	 */
	public String logout(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.loginout(super.getSessionId());
		return SUCCESS;
	}
	/**
	 * 删除管理员
	 */
	private String[] adminUserNames;
	public String delete(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.deleteAdmins(adminUserNames);
		return SUCCESS;
	}
	/**
	 * 启用或禁用管理员   0 开启  1禁用
	 */
	private String adminUserName;
	private int status;
	public String editStatus(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.editAdminStatus(adminUserName, status);
		return SUCCESS;
	}
	/**
	 * 编辑管理员
	 */
	private String passWord;
	private int sex;
	private String name;
	private String call;
	private String remark;
	private String upUser;
	private String birthday;
	public String editAdmin(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		Date birth = DateUtils.StringToDate(birthday);
		adminService.editAdmins(adminUserName, passWord, sex, name, call, remark, upUser, birth);
		return SUCCESS;
	}
	/**
	 * 添加管理员
	 * @return
	 */
	public String addAdmin(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		Date birth = DateUtils.StringToDate(birthday);
		adminService.addAdmins(adminUserName, passWord, sex, name, call, remark, upUser, birth);
		return SUCCESS;
	}
	/**
	 * 修改密码
	 */
	private String oldPasswd;
	private String newPasswd;
	public String editPwd(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.editAdminPwd(adminUserName, oldPasswd, newPasswd);
		return SUCCESS;
	}
}

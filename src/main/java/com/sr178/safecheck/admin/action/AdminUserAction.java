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
	
	private int st;
	/**
	 * 获取管理员列表
	 * @return
	 */
	public String showList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		super.initPage(adminService.getAdminUserPageList(super.getToPage(), 10));
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
	private int status;
	public String editStatus(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		if(adminUserNames!=null&&adminUserNames.length>0){
			for(String adminUserName:adminUserNames){
				adminService.editAdminStatus(adminUserName, status);
			}
		}
		return SUCCESS;
	}
	/**
	 * 编辑管理员
	 */
	private String adminUserName;
	private String passWord;
	private int sex;
	private String name;
	private String call;
	private String remark;
	private String upUser;
	private String birthday;
	
	private AdminUser adminUser;
	public String editAdmin(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		if(st==0){
			adminUser = adminService.getAdminByUserName(adminUserName);
			return INPUT;
		}
		Date birth = DateUtils.StringToDate(birthday);
		adminService.editAdmins(adminUserName, passWord, sex, name, call, remark, upUser, birth);
		super.setCode(2001);
		return SUCCESS;
	}
	/**
	 * 添加管理员
	 * @return
	 */
	public String addAdmin(){
		if(st==0){
			return INPUT;
		}
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		Date birth = DateUtils.StringToDate(birthday);
		adminService.addAdmins(adminUserName, passWord, sex, name, call, remark, super.getUserName(), birth);
		super.setCode(2000);
		return SUCCESS;
	}
	/**
	 * 修改密码
	 */
	private String oldPasswd;
	private String newPasswd;
	public String editPwd(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.editAdminPwd(super.getUserName(), oldPasswd, newPasswd);
		return SUCCESS;
	}

	public int getSt() {
		return st;
	}

	public void setSt(int st) {
		this.st = st;
	}

	public String[] getAdminUserNames() {
		return adminUserNames;
	}

	public void setAdminUserNames(String[] adminUserNames) {
		this.adminUserNames = adminUserNames;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpUser() {
		return upUser;
	}

	public void setUpUser(String upUser) {
		this.upUser = upUser;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getOldPasswd() {
		return oldPasswd;
	}

	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}

	public String getNewPasswd() {
		return newPasswd;
	}

	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
	
}

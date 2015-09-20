package com.sr178.safecheck.admin.action;

import java.util.Date;
import java.util.List;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.bo.AdminUser;
import com.sr178.safecheck.admin.bo.User;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.common.action.ALDAdminPageActionSupport;
import com.sr178.safecheck.common.utils.DateUtils;

public class UserAction extends ALDAdminPageActionSupport<User> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int st;
	/**
	 * 获取当前管理员下的用户列表
	 * @return
	 */
	public String showListu(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		super.initPage(adminService.getUserPageList(super.getUserName(),super.getToPage(), 10));
		return SUCCESS;
	}
	/**
	 * 删除管理员
	 */
	private String[] userNames;
	public String deleteu(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.deleteUsers(userNames);
		return SUCCESS;
	}
	/**
	 * 启用或禁用管理员   0 开启  1禁用
	 */
	private int status;
	public String editStatusu(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		if(userNames!=null&&userNames.length>0){
			for(String adminUserName:userNames){
				adminService.editUserStatus(adminUserName, status);
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
	private List<AdminUser> admins;
	private User user;
	public String editUser(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		if(st==0){
			admins = adminService.getAllAdminUser();
			user = adminService.getByUserName(adminUserName);
			return INPUT;
		}
		
		Date birth = DateUtils.StringToDate(birthday);
		adminService.editUsers(adminUserName, passWord, sex, name, call, remark, upUser, birth);
		super.setCode(2001);
		return SUCCESS;
	}
	/**
	 * 添加管理员
	 * @return
	 */
	
	public String addUser(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		if(st==0){
			admins = adminService.getAllAdminUser();
			return INPUT;
		}
		Date birth = DateUtils.StringToDate(birthday);
		adminService.addUsers(adminUserName, passWord, sex, name, call, remark, upUser, birth);
		super.setCode(2000);
		return SUCCESS;
	}

	public int getSt() {
		return st;
	}

	public void setSt(int st) {
		this.st = st;
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
	public String[] getUserNames() {
		return userNames;
	}
	public void setUserNames(String[] userNames) {
		this.userNames = userNames;
	}
	public List<AdminUser> getAdmins() {
		return admins;
	}
	public void setAdmins(List<AdminUser> admins) {
		this.admins = admins;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}

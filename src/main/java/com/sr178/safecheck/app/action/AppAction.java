package com.sr178.safecheck.app.action;

import java.util.HashMap;
import java.util.Map;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.app.service.AppService;

public class AppAction extends AppBaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 修改密码
	 */
	private String oldPwd;
	private String newPwd;
	public String editpwd(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		appService.editpwd(super.getLoginUser(), oldPwd, newPwd);
		return renderSuccessResult();
	}
	/**
	 * 查询检查项列表
	 * @return
	 */
	public String checkList(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		return renderListResult(appService.checkList());
	}
	
	/**
	 * 签到
	 */
	private String cpName;
	private Long signTime;
	private String position;
	public String sign(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		int taskId = appService.sign(super.getLoginUser(), cpName, signTime, position);
		Map<String,Integer> result = new HashMap<String,Integer>();
		result.put("taskId", taskId);
		return renderObjectResult(result);
	}
	
	
	
	
	
	
	
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public Long getSignTime() {
		return signTime;
	}
	public void setSignTime(Long signTime) {
		this.signTime = signTime;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}

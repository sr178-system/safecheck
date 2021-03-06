package com.sr178.safecheck.app.action;

import java.util.HashMap;
import java.util.Map;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.app.bo.Version;
import com.sr178.safecheck.app.service.AppService;


public class AppNoAuthAction extends AppBaseActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 登录接口
	 */
	private String userName;
	private String passWord;
	public String login(){
		Map<String,String> result = new HashMap<String,String>();
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		result.put("tokenId", appService.login(userName, passWord));
        return renderObjectResult(result);
	}
	
	
	private int versionNum;
	public String checkVersion(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		Version version = appService.checkVersion(versionNum);
		return renderKeyValueResult("url",version==null?"":version.getUrl());
	}
	
	
	
	
	
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}





	public int getVersionNum() {
		return versionNum;
	}





	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}
	
}

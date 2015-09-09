package com.sr178.safecheck.app.action;

import java.util.HashMap;
import java.util.Map;


public class AppNoAuthAction extends AppBaseActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 登录接口
	 */
	private String passWord;
	public String login(){
		
		Map<String,String> result = new HashMap<String,String>();
		result.put("taskId", "1");
		
        return renderObjectResult(result);
	}
	
	
	
	
	
	
	
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}

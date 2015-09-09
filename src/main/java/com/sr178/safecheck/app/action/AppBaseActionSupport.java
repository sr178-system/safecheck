package com.sr178.safecheck.app.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class AppBaseActionSupport extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String JSON = "json";
	
	private String tokenId;
	
	private String userName;
	
	private Map<String,Object> dataMap = new HashMap<String,Object>();


	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	/**
	 * 返回成功的对象消息
	 * @param o
	 */
	public <T> String renderObjectResult(T o){
		dataMap.put("flag", 1);
		dataMap.put("msg", "success");
		dataMap.put("rc",o);
		return JSON;
	}
	/**
	 * 返回成功的列表消息
	 * @param o
	 */
	public <T> String renderListResult(List<T> o){
		dataMap.put("flag", 1);
		dataMap.put("msg", "success");
		dataMap.put("rc",o);
		return JSON;
	}
	/**
	 * 错误消息
	 * @param msg
	 */
	public String renderErrorResult(String msg){
		dataMap.put("flag", 0);
		dataMap.put("msg",msg);
		return JSON;
	}
    /**
     * 没有返回参数的成功消息
     */
	public String renderSuccessResult(){
		dataMap.put("flag", 1);
		dataMap.put("msg","sucess");
		return JSON;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

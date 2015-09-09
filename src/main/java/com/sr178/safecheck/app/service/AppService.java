package com.sr178.safecheck.app.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppService {
	   Map<String,String> userToken = new ConcurrentHashMap<String,String>();
	    
	    /**
	     * 查看是否登录了
	     * @param sessionId
	     * @return
	     */
	    public String isLogin(String tokenId){
	    	if(tokenId==null||"".equals(tokenId)){
	    		return null;
	    	}
	    	return userToken.get(tokenId);
	    }
}

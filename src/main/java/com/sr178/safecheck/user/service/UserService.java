package com.sr178.safecheck.user.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class UserService {
	
    
    
    Map<String,String> userSession = new ConcurrentHashMap<String,String>();
    
    /**
     * 查看是否登录了
     * @param sessionId
     * @return
     */
    public String isLogin(String sessionId){
    	return userSession.get(sessionId);
    }
    
    
    
    
    public void login(){
    	
    }
	
}

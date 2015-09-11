package com.sr178.safecheck.admin.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.sr178.safecheck.admin.dao.AdminUserDao;
import com.sr178.safecheck.app.dao.CheckItemsDao;
import com.sr178.safecheck.app.dao.CheckRecordDao;
import com.sr178.safecheck.app.dao.EnforceRecordDao;
import com.sr178.safecheck.app.dao.NoticeDao;
import com.sr178.safecheck.app.dao.ResourceDao;
import com.sr178.safecheck.app.dao.UserDao;




public class AdminService {
	
    
    
       Map<String,String> userSession = new ConcurrentHashMap<String,String>();
	   @Autowired
	   private UserDao userDao;
	   @Autowired
	   private CheckItemsDao checkItemsDao;
	   @Autowired
	   private CheckRecordDao checkRecordDao;
	   @Autowired
	   private EnforceRecordDao enforceRecordDao;
	   @Autowired
	   private NoticeDao noticeDao;
	   @Autowired
	   private ResourceDao resourceDao;
	   @Autowired
	   private AdminUserDao adminUserDao;
    
    
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

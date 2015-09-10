package com.sr178.safecheck.admin.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

import com.sr178.game.framework.log.LogSystem;
import com.sr178.safecheck.admin.dao.AdminUserDao;
import com.sr178.safecheck.app.dao.CheckItemsDao;
import com.sr178.safecheck.app.dao.CheckRecordDao;
import com.sr178.safecheck.app.dao.EnforceRecordDao;
import com.sr178.safecheck.app.dao.NoticeDao;
import com.sr178.safecheck.app.dao.ResourceDao;
import com.sr178.safecheck.app.dao.SignRecordDao;
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
	   private SignRecordDao signRecordDao;    
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
	
    
    public static void main(String[] args) throws ClientProtocolException, IOException {
    	String url="http://localhost:8083/uploadFile";
        HttpClient httpclient= new DefaultHttpClient();
        HttpPost httpPost= new HttpPost(url);
        MultipartEntity mulentity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
           mulentity.addPart("foodname", new StringBody("nice boy foodname"));
           mulentity.addPart("foodstyle", new StringBody("foodstyle"));
           mulentity.addPart("price", new StringBody("price"));  
           File image1 = new File("E:\\图片\\58-110R31U14786.jpg");
           File image2 = new File("E:\\图片\\11.jpg");
          //添加图片表单数据       
           FileBody filebody1 = new FileBody(image1);
           FileBody filebody2 = new FileBody(image2);
           mulentity.addPart("foodimg",filebody1);    
           mulentity.addPart("listFiles",filebody1);
           mulentity.addPart("listFiles",filebody2);
           mulentity.addPart("foodtab", new StringBody("foodtab"));
           mulentity.addPart("state", new StringBody("1"));        
           httpPost.setEntity(mulentity);
           HttpResponse response =    httpclient.execute(httpPost);
           
           if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
           {
               LogSystem.info("上传成功！！~~");
           }
           else
           {
        	   LogSystem.info("上传失败");
           }
	}
}

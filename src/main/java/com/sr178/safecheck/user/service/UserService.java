package com.sr178.safecheck.user.service;

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

import com.sr178.game.framework.log.LogSystem;




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
	
    
    public static void main(String[] args) throws ClientProtocolException, IOException {
    	String url="http://localhost:8082/uploadFile";
        HttpClient httpclient= new DefaultHttpClient();
        HttpPost httpPost= new HttpPost(url);
        MultipartEntity mulentity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
           mulentity.addPart("foodname", new StringBody("nice boy foodname"));
           mulentity.addPart("foodstyle", new StringBody("foodstyle"));
           mulentity.addPart("price", new StringBody("price"));  
           File image = new File("E:\\图片\\58-110R31U14786.jpg");
          //添加图片表单数据       
           FileBody filebody = new FileBody(image);
           mulentity.addPart("foodimg",filebody);    
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

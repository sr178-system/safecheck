package com.sr178.safecheck.admin.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sr178.game.framework.log.LogSystem;

public class ImageUploadAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String foodname;
	private String foodstyle;
	private String price;

	// 接收上传文件
	private File foodimg;
	private String foodimgFileName;
	private String foodimgContentType;

	private String foodtab;
	
	private List<File> listFiles;
	
	private List<String> listFilesFileName;

	private String state;
	
	private static String descDirectoryPath = null;
	
	public String execute(){
		
		if(descDirectoryPath==null){
			  String path= ServletActionContext.getServletContext().getRealPath("/");
			  descDirectoryPath = path+"/uploads/";
		}
		
        
        LogSystem.info("foodname="+foodname+",foodstyle="+foodstyle+",price="+price+",foodimgFileName="+foodimgFileName+",foodtab="+foodtab+",state="+state+",foodimgContentType="+foodimgContentType);
       //保存上传文件
        try {
			FileUtils.copyFile(foodimg, new File(descDirectoryPath+foodimgFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public String getFoodstyle() {
		return foodstyle;
	}

	public void setFoodstyle(String foodstyle) {
		this.foodstyle = foodstyle;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public File getFoodimg() {
		return foodimg;
	}

	public void setFoodimg(File foodimg) {
		this.foodimg = foodimg;
	}

	public String getFoodimgFileName() {
		return foodimgFileName;
	}

	public void setFoodimgFileName(String foodimgFileName) {
		this.foodimgFileName = foodimgFileName;
	}

	public String getFoodimgContentType() {
		return foodimgContentType;
	}

	public void setFoodimgContentType(String foodimgContentType) {
		this.foodimgContentType = foodimgContentType;
	}

	public String getFoodtab() {
		return foodtab;
	}

	public void setFoodtab(String foodtab) {
		this.foodtab = foodtab;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<File> getListFiles() {
		return listFiles;
	}

	public void setListFiles(List<File> listFiles) {
		this.listFiles = listFiles;
	}

	public List<String> getListFilesFileName() {
		return listFilesFileName;
	}

	public void setListFilesFileName(List<String> listFilesFileName) {
		this.listFilesFileName = listFilesFileName;
	}
	
}

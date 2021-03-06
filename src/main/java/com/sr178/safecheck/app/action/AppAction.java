package com.sr178.safecheck.app.action;


import java.io.File;
import java.util.List;

import org.apache.struts2.ServletActionContext;

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
	 * 查询检查大项项列表
	 * @return
	 */
	public String bigCheckList(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		return renderListResult(appService.checkList(super.getLoginUser()));
	}
	/**
	 * 查询检查大类详情
	 */
	private int id;
	public String checkDetails(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		return renderObjectResult(appService.checkDetailsNew(id));
	}
	/**
	 * 模糊查询企业列表
	 * @return
	 */
	public String companyList(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		return renderListResult(appService.companyList(cpName));
	}
	/**
	 * 获取系统时间
	 * @return
	 */
	public String systemTime(){
		return renderKeyValueResult("systemTime", System.currentTimeMillis());
	}
	
	/**
	 * 保存检查结果
	 */
	private String cpName;
	private String position;
	private int checkId;
	private Long checkTime;
	private String checkResult;
	private String resPersonName;
	private String resPersonCall;
	public String saveCheck(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		return renderKeyValueResult("taskId", appService.saveCheck(super.getLoginUser(), cpName, checkId, position, checkTime, resPersonName, resPersonCall, checkResult));
	}
	
	/**
	 * 执法
	 */
	private String inCpName;
	private Long inTime;
	public String intendance(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		return renderKeyValueResult("taskId", appService.intendance(super.getLoginUser(), inCpName, inTime));
	}
	
	/**
	 * 查询培训记录
	 */
	private String idCard;
	private String certNum;
	public String trainRecord(){
		return renderObjectResult(AppService.trainRecod(certNum));
	}
	/**
	 * 简易培训记录列表
	 * @return
	 */
	public String trainSimpleRecord(){
		return renderListResult(AppService.trainSimpleByIdCard(idCard,certNum));
	}
	/**
	 * 分页查询公告列表
	 */
	private int pageNo;
	private int pageSize;
	private String searchStr;
	public String newsList(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		return renderPageResult(appService.getPageNotice(searchStr,super.getLoginUser(),pageNo, pageSize));
	}
	/**
	 * 查询新闻内容
	 */
	private int newsId;
	public String newsContent(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		return renderObjectResult(appService.getNotice(super.getLoginUser(),newsId));
	}
	/**
	 * 获取用户未读消息条数
	 * @return
	 */
	public String noReadNum(){
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		return renderObjectResult(appService.noReadNum(super.getLoginUser()));
	}
	/**
	 * 图片上传
	 */
	private List<File> images;
	private List<String> imagesFileName;
	private int taskId;
	private int type;
	public String uploadFiles() {
		AppService appService = ServiceCacheFactory.getService(AppService.class);
		String path = ServletActionContext.getServletContext().getRealPath("/");
		String descDirectoryPath = path + "/uploads/";
		appService.saveFiles(images,imagesFileName,taskId, type, descDirectoryPath);
		return renderSuccessResult();
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getInCpName() {
		return inCpName;
	}
	public void setInCpName(String inCpName) {
		this.inCpName = inCpName;
	}
	public Long getInTime() {
		return inTime;
	}
	public void setInTime(Long inTime) {
		this.inTime = inTime;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getCheckId() {
		return checkId;
	}
	public void setCheckId(int checkId) {
		this.checkId = checkId;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getResPersonName() {
		return resPersonName;
	}
	public void setResPersonName(String resPersonName) {
		this.resPersonName = resPersonName;
	}
	public String getResPersonCall() {
		return resPersonCall;
	}
	public void setResPersonCall(String resPersonCall) {
		this.resPersonCall = resPersonCall;
	}
	public Long getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Long checkTime) {
		this.checkTime = checkTime;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getCertNum() {
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public List<File> getImages() {
		return images;
	}
	public void setImages(List<File> images) {
		this.images = images;
	}
	public List<String> getImagesFileName() {
		return imagesFileName;
	}
	public void setImagesFileName(List<String> imagesFileName) {
		this.imagesFileName = imagesFileName;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getSearchStr() {
		return searchStr;
	}
	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

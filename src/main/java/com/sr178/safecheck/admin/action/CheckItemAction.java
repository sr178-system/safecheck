package com.sr178.safecheck.admin.action;

import java.util.List;

import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.safecheck.admin.bean.UserInfo;
import com.sr178.safecheck.admin.bo.CheckItems;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.common.action.ALDAdminPageActionSupport;

public class CheckItemAction extends ALDAdminPageActionSupport<CheckItems> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 大类列表
	 * @return
	 */
	public String showList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
        super.initPage(adminService.getZeroCheckItemsPageList(super.getSessionId(), super.getToPage(), 10));
		return SUCCESS;
	}
	/**
	 * 删大类
	 * @return
	 */
	public String deleteBigCheckItems(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.deleteBigCheckItems(ids);
		return SUCCESS;
	}
	
	private int status;
	public String updateStatus(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.updateBigCheckItemsStatus(ids, status);
		return SUCCESS;
	}
	/**
	 * 查询大类详情
	 */
	private CheckItems bigCheck;
	private int roleType;
	public String requestBigCheckDetails(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		UserInfo userInfo = adminService.isLogin(super.getSessionId());
		roleType = userInfo.getRoleType();
		bigCheck = adminService.getBigCheck(id);
		return SUCCESS;
	}
	/**
	 * 获取下级列表
	 */
	private int parentId;
	private List<CheckItems> downList;
	public String requestDownList(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		downList = adminService.getDownCheckItems(parentId);
		return SUCCESS;
	}
	
	/**
	 * 删小类
	 */
	private int[] ids;
	public String deleteSmallCheckItems(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		adminService.deleteSmallCheckItems(ids);
		return SUCCESS;
	}
	
	private Integer id;   //大类id
	private String title;//大类名称
	private String firstItemName;//大项名称
	private String secondItemName;//子项名称
	private String resultItemName;//结果名称
	private Integer successOrFail;//0 失败 标识 1成功标识
	private String departMent;//部门
	private int st;
	private List<String> dps;
	public String addOrEdit(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		if (st == 0) {
			dps = adminService.getMyDepartMent(super.getSessionId());
			if (id == null) {// 添加
				return SUCCESS;
			} else {// 修改 查询大类信息 子类信息通过ajax异步请求接口
				bigCheck = adminService.getBigCheck(id);
				downList = adminService.getDownCheckItems(id);
				return SUCCESS;
			}
		}
		
		if(successOrFail==null){
			successOrFail = 1;
		}else{
			successOrFail = 0;
		}
		
		if(id==null){
			id = adminService.addCheckItems(super.getSessionId(), title, firstItemName, secondItemName, resultItemName, departMent, successOrFail);
			super.setCode(2000);
			return "redirect";
		}else{
			adminService.editCheckItems(super.getSessionId(),id, title, firstItemName, secondItemName, resultItemName, departMent, successOrFail);
			super.setCode(2001);
			return "redirect";
		}
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public List<CheckItems> getDownList() {
		return downList;
	}

	public void setDownList(List<CheckItems> downList) {
		this.downList = downList;
	}


	public CheckItems getBigCheck() {
		return bigCheck;
	}

	public void setBigCheck(CheckItems bigCheck) {
		this.bigCheck = bigCheck;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSt() {
		return st;
	}
	public void setSt(int st) {
		this.st = st;
	}
	public String getFirstItemName() {
		return firstItemName;
	}
	public void setFirstItemName(String firstItemName) {
		this.firstItemName = firstItemName;
	}
	public String getSecondItemName() {
		return secondItemName;
	}
	public void setSecondItemName(String secondItemName) {
		this.secondItemName = secondItemName;
	}
	public String getResultItemName() {
		return resultItemName;
	}
	public void setResultItemName(String resultItemName) {
		this.resultItemName = resultItemName;
	}
	public int getSuccessOrFail() {
		return successOrFail;
	}
	public void setSuccessOrFail(int successOrFail) {
		this.successOrFail = successOrFail;
	}
	public String getDepartMent() {
		return departMent;
	}
	public void setDepartMent(String departMent) {
		this.departMent = departMent;
	}
	public List<String> getDps() {
		return dps;
	}
	public void setDps(List<String> dps) {
		this.dps = dps;
	}
}

package com.sr178.safecheck.admin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.common.jdbc.bean.Page;
import com.sr178.common.jdbc.bean.SqlParamBean;
import com.sr178.safecheck.admin.bean.JctjBean;
import com.sr178.safecheck.admin.bo.AdminUser;
import com.sr178.safecheck.admin.bo.CheckItems;
import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.admin.bo.EnforceRecord;
import com.sr178.safecheck.admin.bo.User;
import com.sr178.safecheck.admin.dao.AdminUserDao;
import com.sr178.safecheck.app.dao.CheckItemsDao;
import com.sr178.safecheck.app.dao.CheckRecordDao;
import com.sr178.safecheck.app.dao.EnforceRecordDao;
import com.sr178.safecheck.app.dao.NoticeDao;
import com.sr178.safecheck.app.dao.ResourceDao;
import com.sr178.safecheck.app.dao.UserDao;
import com.sr178.safecheck.common.exception.ServiceException;




public class AdminService {
	public static final String AND = "and";
	public static final String OR = "or";

	Map<String, String> userSession = new ConcurrentHashMap<String, String>();
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
	 * 
	 * @param sessionId
	 * @return
	 */
	public String isLogin(String sessionId) {
		return userSession.get(sessionId);
	}
    /**
     * 登录
     * @param userName
     * @param passWord
     * @param sessionId
     */
	public void login(String userName, String passWord,String sessionId) {
		AdminUser adminUser = adminUserDao.get(new SqlParamBean("user_name", userName), new SqlParamBean(AND, "pass_word", passWord));
		if(adminUser==null){
			throw new ServiceException(1, "用户名或密码错误！");
		}
		if(adminUser.getStatus()==1){
			throw new ServiceException(2, "该用户已被禁用，请及时联系管理员！");
		}
		userSession.put(sessionId, adminUser.getUserName());
	}
	/**
	 * 获取检查项Map
	 * @return
	 */
	public Map<Integer,CheckItems> getCheckItemsMap(){
		List<CheckItems> list = checkItemsDao.getAll();
		Map<Integer,CheckItems> result = Maps.newHashMap();
		for(CheckItems items:list){
			result.put(items.getId(), items);
		}
		return result;
	}
	/**
	 * 检查项ids转成检查项名称列表
	 * @param ids
	 * @param checkItemsMap
	 * @return
	 */
	public List<String> idsToNames(String ids,Map<Integer,CheckItems> checkItemsMap){
		List<String> result = Lists.newArrayList();
		if(!Strings.isNullOrEmpty(ids)){
			String[] checkItemIds = ids.split(",");
			for(String id:checkItemIds){
				CheckItems items = checkItemsMap.get(Integer.valueOf(id));
				if(items!=null){
					result.add(items.getItemTitle());
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取企业最近的执法记录
	 * @param cpNames
	 * @return
	 */
	private Map<String,EnforceRecord> getRecentCpEnforceRecord(List<String> cpNames){
		List<EnforceRecord> list = enforceRecordDao.getEnforceRecordGroupByCpName(cpNames);
		Map<String,EnforceRecord> result = Maps.newHashMap();
		for(EnforceRecord enforceRecord:list){
			result.put(enforceRecord.getCpName(), enforceRecord);
		}
		return result;
	}
	/**
	 * 查询检查统计列表
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<JctjBean> getJctjBeanPageList(String searchCp,int pageIndex,int pageSize){
		IPage<CheckRecord> page = checkRecordDao.getCheckRecordPageListGroupByCpName(searchCp,pageIndex, pageSize);
		Collection<JctjBean> trasferDate = new ArrayList<JctjBean>();
		IPage<JctjBean> result = new Page<JctjBean>(trasferDate, page.getTotalSize(), pageSize, pageIndex);
		if(page.getData()!=null&&page.getData().size()>0){
			Map<Integer,CheckItems> checkItemsMap = getCheckItemsMap();
			List<String> cpNames = Lists.newArrayList();
			for(CheckRecord checkRecord:page.getData()){
				cpNames.add(checkRecord.getCpName());
			}
			Map<String,EnforceRecord> recentEnforceMap = getRecentCpEnforceRecord(cpNames);
			for(CheckRecord checkRecord:page.getData()){
				JctjBean bean = new JctjBean();
				bean.setCpName(checkRecord.getCpName());
				bean.setCheckRecord(checkRecord);
				bean.setEnforceRecord(recentEnforceMap.get(checkRecord.getCpName()));
				bean.setItemsNames(idsToNames(checkRecord.getCheckItems(),checkItemsMap));
				trasferDate.add(bean);
			}
		}
		return result;
	}

}

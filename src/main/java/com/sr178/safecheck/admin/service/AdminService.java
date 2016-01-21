package com.sr178.safecheck.admin.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.common.jdbc.bean.SqlParamBean;
import com.sr178.game.framework.log.LogSystem;
import com.sr178.safecheck.admin.bean.CheckRecordDetailsBean;
import com.sr178.safecheck.admin.bean.CheckResultBean;
import com.sr178.safecheck.admin.bean.MixCheckAndEnforceBean;
import com.sr178.safecheck.admin.bean.UserInfo;
import com.sr178.safecheck.admin.bo.AdminUser;
import com.sr178.safecheck.admin.bo.CheckItems;
import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.admin.bo.EnforceRecord;
import com.sr178.safecheck.admin.bo.Notice;
import com.sr178.safecheck.admin.bo.User;
import com.sr178.safecheck.admin.dao.AdminUserDao;
import com.sr178.safecheck.app.bean.CheckDetailsForAdminBean;
import com.sr178.safecheck.app.bo.BigCheckItemBO;
import com.sr178.safecheck.app.dao.CheckItemsDao;
import com.sr178.safecheck.app.dao.CheckRecordDao;
import com.sr178.safecheck.app.dao.EnforceRecordDao;
import com.sr178.safecheck.app.dao.NoticeDao;
import com.sr178.safecheck.app.dao.UserDao;
import com.sr178.safecheck.common.exception.ServiceException;
import com.sr178.safecheck.common.utils.MD5Security;
import com.sr178.safecheck.common.utils.MacShaUtils;
import com.sr178.safecheck.common.utils.ParamCheck;




public class AdminService {
	public static final String AND = "and";
	public static final String OR = "or";

  	private Cache<String,UserInfo> userSession = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).maximumSize(2000).build();

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
	private AdminUserDao adminUserDao;
	
	private static final String SHA_SECRET = "!@#asDFA55214644";
	/**
	 * 获取所有管理原用户
	 * @return
	 */
//	public List<AdminUser> getAllAdminUser(){
//		return adminUserDao.getAll();
//	}

	public List<String> getMyDepartMent(String sessionId){
		UserInfo userInfo = this.isLogin(sessionId);
		if(userInfo.getRoleType()==1){
			List<String> result = Lists.newArrayList();
			result.add(userInfo.getDepartMent());
			return result;
		}else{
			return adminUserDao.getAllDepartMent();
		}
	}
	/**
	 * 模糊搜索部门列表
	 * @param str
	 * @return
	 */
    public List<String> searchDepartMent(String str){
		return adminUserDao.searchDepartMent(str);
	}
	/**
	 * 查看是否登录了
	 * 
	 * @param sessionId
	 * @return
	 */
	public UserInfo isLogin(String sessionId) {
		return userSession.getIfPresent(sessionId);
	}
    /**
     * 登录
     * @param userName
     * @param passWord
     * @param sessionId
     */
	public void login(String userName, String passWord,String sessionId) {
		passWord = MacShaUtils.doEncryptBase64(passWord, SHA_SECRET);
		AdminUser adminUser = adminUserDao.get(new SqlParamBean("user_name", userName), new SqlParamBean(AND, "pass_word", passWord));
		UserInfo userInfo = new UserInfo();
		if(adminUser==null){
			User user = userDao.get(new SqlParamBean("user_name", userName), new SqlParamBean(AND, "pass_word", passWord));
			if(user==null){
				throw new ServiceException(1, "用户名或密码错误！");
			}else if(user.getStatus()==1){
				throw new ServiceException(2, "该用户已被禁用，请及时联系管理员！");
			}
			userInfo.setUserName(userName);
			userInfo.setRoleType(0);
			userInfo.setName(user.getName());
			userInfo.setDepartMent(user.getDepartMent());
		}else{
			if(adminUser.getStatus()==1){
				throw new ServiceException(2, "该用户已被禁用，请及时联系管理员！");
			}
			userInfo.setUserName(userName);
			if(!Strings.isNullOrEmpty(adminUser.getDepartMent())){
				userInfo.setRoleType(1);
				userInfo.setDepartMent(adminUser.getDepartMent());
				userInfo.setName(adminUser.getName());
			}else{
				userInfo.setRoleType(2);
				userInfo.setName(adminUser.getName());
			}
		}
		userSession.put(sessionId, userInfo);
	}
	/**
	 * 登出
	 * @param sessionId
	 */
	public void loginout(String sessionId){
		userSession.invalidate(sessionId);
	}
	/**
	 * 查询检查记录
	 * @param sessionId
	 * @param startDate
	 * @param endDate
	 * @param cpName
	 * @param checkName
	 * @param checkId
	 * @param checkResult
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<CheckRecord> getCheckRecordPage(String sessionId,String startDate,String endDate,String cpName,String checkName,Integer checkId,Integer checkResult,int pageIndex,int pageSize){
		UserInfo userInfo = this.isLogin(sessionId);
		String departMent = null;
		if(userInfo.getRoleType()==0){
			//普通用户只能看到自己的记录
			checkName = userInfo.getName();
		}else if(userInfo.getRoleType()==1){//部门管理员只能看到自己部门的检查记录
			departMent = userInfo.getDepartMent();
		}
		return checkRecordDao.getCheckRecordPage(departMent, startDate, endDate, cpName, checkName, checkId, checkResult, pageIndex, pageSize);
	}
	/**
	 * 获取当前登录用户的所有大类型
	 * @param sessionId
	 * @return
	 */
	public List<BigCheckItemBO> getAllParentCheckItems(String sessionId){
		UserInfo userInfo = this.isLogin(sessionId);
		String departMent = null;
		if(userInfo.getRoleType()==0||userInfo.getRoleType()==1){
			departMent = userInfo.getDepartMent();
		}
		return checkItemsDao.getBigCheckItemBOList(departMent);
	}
	
	/**
	 * 检查记录详情
	 * @param recordId
	 * @return
	 */
	public CheckRecordDetailsBean getCheckRecordDetailsBean(int recordId){
		CheckRecordDetailsBean result = new CheckRecordDetailsBean();
		CheckRecord record = checkRecordDao.getCheckRecord(recordId);
		if(record==null){
			throw new ServiceException(1, "检查记录不存在");
		}
//		ZeroCheckItemBean checkModelBean = appService.checkDetails(recordId);
		String checkResult = record.getCheckResult();
		List<CheckDetailsForAdminBean> checkDetails = transferToCheckResultBeanMap(checkResult, record);
		result.setCheckRecord(record);
//		result.setCheckModelBean(checkModelBean);
//		result.setResultMap(map);
		result.setCheckDetails(checkDetails);
		result.setEnforceList(getEnforceRecordList(record.getCheckTime(), record.getCpName()));
		return result;
	}
	
	/**
	 * 获取检查记录相关的执法记录
	 * @param checkTime
	 * @param cpName
	 * @return
	 */
	private List<EnforceRecord> getEnforceRecordList(Date checkTime,String cpName){
		Date nextTime = checkRecordDao.getNextCheckTime(checkTime,cpName);
		List<EnforceRecord> enList = Lists.newArrayList();
		if(nextTime!=null){
			enList = enforceRecordDao.getEnforceRecordByDate(checkTime, nextTime, cpName);
		}
		return enList;
	}
	/**
	 * 
	 * @param checkResult
	 * @param checkRecord
	 * @return  key为:[大项id+"#"+小项id],value为[检查的结果情况，包括结果选项，情况说明及资源地址]
	 */
	private List<CheckDetailsForAdminBean> transferToCheckResultBeanMap(String checkResult,CheckRecord checkRecord){
		List<CheckDetailsForAdminBean> result = Lists.newArrayList();
		if(Strings.isNullOrEmpty(checkResult)){
			return result;
		}
		String[] strArray =  checkResult.split(":");
		Map<String,List<String>> photoMap = getItemPhoto(checkRecord.getResource2Names());
		
		Map<Integer,Integer> bigIdToSecondSize = Maps.newHashMap();
		
		for(String str:strArray){
			CheckDetailsForAdminBean tempBean = new CheckDetailsForAdminBean();
			CheckResultBean temp = trasferToCheckResultBean(str);
			if(temp==null){
				continue;
			}
			tempBean.setCheckBigId(temp.getFirstItem().getId());
			tempBean.setCheckBigTitle(temp.getFirstItem().getItemTitle());
			tempBean.setCheckSmallId(temp.getSecondItem().getId());
			tempBean.setCheckSmallTitle(temp.getSecondItem().getItemTitle());
			String key = temp.getFirstItem().getId().intValue()+"#"+temp.getSecondItem().getId().intValue();
			temp.setResource(photoMap.get(key));
			tempBean.setResult(temp);
			result.add(tempBean);
			if(bigIdToSecondSize.containsKey(temp.getFirstItem().getId())){
				Integer currentSize = bigIdToSecondSize.get(temp.getFirstItem().getId());
				currentSize = currentSize +1;
				bigIdToSecondSize.put(temp.getFirstItem().getId(), currentSize);
			}else{
				bigIdToSecondSize.put(temp.getFirstItem().getId(), 1);
			}
		}
		for(CheckDetailsForAdminBean bean:result){
			bean.setSecondSize(bigIdToSecondSize.get(bean.getCheckBigId()).intValue());
		}
		return result;
	}
	/**
	 * 解析图片资源对应的项map
	 * @param resourceStr
	 * @return
	 */
	private Map<String,List<String>> getItemPhoto(String resourceStr){
		if(Strings.isNullOrEmpty(resourceStr)){
			return new HashMap<String,List<String>>();
		}
		String[] resources = resourceStr.split(",");
		Map<String,List<String>> result = Maps.newHashMap();
		for(String resource:resources){
			 String[] array = resource.split("_");
			  if(array.length<2||Strings.isNullOrEmpty(array[0])){
				  LogSystem.warn("不合法的文件名"+resource);
				  continue;
			  }
			  String bigAndSmallId = array[0];
			  String[] bas = bigAndSmallId.split("#");
			  if(bas.length!=2){
				  LogSystem.warn("不合法的文件名"+resource);
				  continue;
			  }
			  try {
				  Integer.valueOf(bas[0]);
				  Integer.valueOf(bas[1]);
			} catch (Exception e) {
				 LogSystem.warn("不合法的文件名"+resource);
				 continue;
			}
			resource = resource.replace("#", "%23");
			if(result.containsKey(bigAndSmallId)){
				List<String> list = result.get(bigAndSmallId);
				
				list.add(resource);
			}else{
				List<String> list = Lists.newArrayList();
				list.add(resource);
				result.put(bigAndSmallId, list);
			}
		}
		return result;
	}
	
	/**
	 * 填充CheckResultBean的大项及小项名称及结果 及说明情况
	 * @param resultItems
	 * @return
	 */
	private CheckResultBean trasferToCheckResultBean(String resultItems){
		CheckResultBean result = new CheckResultBean();
		String[] items = resultItems.split(",");
		if(items.length!=4){
			 return null;
		}
		try {
			int bigid = Integer.valueOf(items[0]);
			int smallId = Integer.valueOf(items[1]);
			CheckItems resultItemBig = checkItemsDao.get(new SqlParamBean("id", bigid));
			CheckItems resultItemSmall = checkItemsDao.get(new SqlParamBean("id", smallId));
			
			if(resultItemBig==null){
				return null; 
			}
			result.setFirstItem(resultItemBig);
			if(resultItemSmall==null){
				return null; 
			}
			result.setSecondItem(resultItemSmall);
		} catch (Exception e) {
			LogSystem.error(e, "");
			return null;
		}
		String[] resultIds = items[2].split("\\|");
		String resultList = "";
//		List<CheckItems> resultList = Lists.newArrayList();
		for(String idStr:resultIds){
			try {
				int id = Integer.valueOf(idStr);
				CheckItems resultItem = checkItemsDao.get(new SqlParamBean("id", id));
				if(resultItem==null){
					LogSystem.warn("无法找到结果id=["+idStr+"]");
					continue;
				}
				String title = "";
				if(resultItem.getSuccessOrFail()==0){
					title = "<font class='red'>"+resultItem.getItemTitle()+"</font>";
				}else{
					title = resultItem.getItemTitle();
				}
				
				if(resultList.equals("")){
					resultList = title;
				}else{
					resultList = resultList+","+title;
				}
			} catch (Exception e) {
				LogSystem.warn("结果id错误,id应该为数字类型，但现在不是，id="+idStr);
				continue;
			}
		}
		result.setResultList(resultList);
		result.setDescription(items[3]);
		return result;
	}
	
	/**
	 * 获取单个大类检查项
	 * @param checkId
	 * @return
	 */
	public CheckItems getBigCheck(int checkId){
		return checkItemsDao.get(new SqlParamBean("id", checkId));
	}
	/**
	 * 获取节点的下级列表
	 * @param parentId
	 * @return
	 */
	public List<CheckItems> getDownCheckItems(int parentId){
		return checkItemsDao.getList("order by id desc", new SqlParamBean("parent_id", parentId));
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
				try {
					CheckItems items = checkItemsMap.get(Integer.valueOf(id));
					if(items!=null){
						result.add(items.getItemTitle());
					}
				} catch (Exception e) {
				}
			}
		}
		return result;
	}
	
	/**
	 * 检查项ids转成检查项名称列表
	 * @param ids
	 * @param checkItemsMap
	 * @return
	 */
	public String idsToNameStr(String ids,Map<Integer,CheckItems> checkItemsMap){
		StringBuffer result = null;
		if(!Strings.isNullOrEmpty(ids)){
			String[] checkItemIds = ids.split(",");
			for(String id:checkItemIds){
				try {
				CheckItems items = checkItemsMap.get(Integer.valueOf(id));
				if(items!=null){
					if(result==null){
						result = new StringBuffer();
						result.append(items.getItemTitle());
					}else{
						result.append("，"+items.getItemTitle());
					}
				}
				} catch (Exception e) {
				}
			}
		}
		if(result==null){
			return null;
		}
		return result.toString();
	}
	
	/**
	 * 获取企业最近的执法记录
	 * @param cpNames
	 * @return
	 */
//	private Map<String,EnforceRecord> getRecentCpEnforceRecord(List<String> cpNames){
//		List<EnforceRecord> list = enforceRecordDao.getEnforceRecordGroupByCpName(cpNames);
//		Map<String,EnforceRecord> result = Maps.newHashMap();
//		for(EnforceRecord enforceRecord:list){
//			result.put(enforceRecord.getCpName(), enforceRecord);
//		}
//		return result;
//	}
	
	
	
	/**
	 * 查询检查统计列表
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
//	public IPage<JctjBean> getJctjBeanPageList(String searchCp,int pageIndex,int pageSize){
//		IPage<CheckRecord> page = checkRecordDao.getCheckRecordPageListGroupByCpName(searchCp,pageIndex, pageSize);
//		Collection<JctjBean> trasferDate = new ArrayList<JctjBean>();
//		IPage<JctjBean> result = new Page<JctjBean>(trasferDate, page.getTotalSize(), pageSize, pageIndex);
//		if(page.getData()!=null&&page.getData().size()>0){
//			Map<Integer,CheckItems> checkItemsMap = getCheckItemsMap();
//			List<String> cpNames = Lists.newArrayList();
//			for(CheckRecord checkRecord:page.getData()){
//				cpNames.add(checkRecord.getCn());
//			}
//			Map<String,EnforceRecord> recentEnforceMap = getRecentCpEnforceRecord(cpNames);
//			for(CheckRecord checkRecord:page.getData()){
//				checkRecord.setCpName(checkRecord.getCn());
//				JctjBean bean = new JctjBean();
//				bean.setCpName(checkRecord.getCn());
//				bean.setCheckRecord(checkRecord);
//				bean.setEnforceRecord(recentEnforceMap.get(checkRecord.getCn()));
////				bean.setItemsNames(idsToNames(checkRecord.getCheckItems(),checkItemsMap));
//				trasferDate.add(bean);
//			}
//		}
//		return result;
//	}
	/**
	 * 检查统计详情页
	 * @param cpName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<MixCheckAndEnforceBean> getJctjDetailsPageList(String cpName,int pageIndex,int pageSize){
		IPage<MixCheckAndEnforceBean> page = checkRecordDao.getMixPageByCpName(cpName, pageIndex, pageSize);
		if(page.getData()!=null&&page.getData().size()>0){
			Map<Integer,CheckItems> checkItemsMap = getCheckItemsMap();
			for(MixCheckAndEnforceBean meb:page.getData()){
				if(meb.getType()==1&&!Strings.isNullOrEmpty(meb.getCheckItems())){
					meb.setCheckItemNames(idsToNameStr(meb.getCheckItems(), checkItemsMap));
				}
			}
		}
		return page;
	}
	
	/**
	 * 获取检查详情
	 * @param cpName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
//	public IPage<CheckRecord> getCheckRecordPage(String cpName,int pageIndex,int pageSize){
//		IPage<CheckRecord> page =  checkRecordDao.getCheckRecordByCpName(cpName, pageIndex, pageSize);
//		if(page!=null&&page.getData()!=null){
//			//设置检查项
//			Map<Integer,CheckItems> checkItemsMap = getCheckItemsMap();
//			for(CheckRecord record:page.getData()){
////				record.setCheckItemNames(idsToNames(record.getCheckItems(), checkItemsMap));
//			}
//		}
//		return page;
//	}
	
	
	/**
	 * 检查督查
	 * @param searchUn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
//	public IPage<JcdcBean> getJcdcBeanPageList(String adminUserName,String searchUn,int pageIndex,int pageSize){
//		AdminUser adminUser = adminUserDao.get(new SqlParamBean("user_name", adminUserName));
//		List<String> findUser = null;//如果是null 则是超级管理员  需要查询出全部  如果不是null  则需要查询指定数量的用户记录出来
//		long totalSize = 0;
//		
//		Collection<JcdcBean> trasferDate = new ArrayList<JcdcBean>();
//		
//		if(!Strings.isNullOrEmpty(adminUser.getUpUser())){//非超级管理员  
//			findUser = Lists.newArrayList();
//			//c查询出他的下属
//			List<User> listUser = userDao.getList(new SqlParamBean("up_user", adminUserName));
//			if(listUser!=null&&listUser.size()>0){
//				 for(User user:listUser){
//					 findUser.add(user.getUserName());
//				 }
//			}
//		}
//		IPage<EnforceRecord> recordList = null;
//		if(findUser==null||findUser.size()!=0){//是管理员 或有下属的才进行查询
//			recordList = enforceRecordDao.getEnforceRecordGroupByEnforceUserName(searchUn,findUser,pageIndex,pageSize);
//		}
//		if(recordList!=null&&recordList.getTotalSize()>0){
//			totalSize = recordList.getTotalSize();
//			List<String> haveUserNames = Lists.newArrayList();
//			for(EnforceRecord enforceRecord:recordList.getData()){
//				haveUserNames.add(enforceRecord.getUn());
//			}
//			Map<String,CheckRecord> map =  getRecentUserCheckRecord(haveUserNames);
//			JcdcBean bean = null; 
//			for(EnforceRecord enforceRecord:recordList.getData()){
//				bean = new JcdcBean();
//				bean.setEnforceRecord(enforceRecord);
//				CheckRecord recentCheckRecord = map.get(enforceRecord.getUn());
//				if(recentCheckRecord!=null){
//					bean.setCheckRecord(recentCheckRecord);
//					enforceRecord.setEnforceName(recentCheckRecord.getCheckerName());
//				}
//				bean.setUserName(enforceRecord.getUn());
//				trasferDate.add(bean);
//			}
//			
//		}
//		IPage<JcdcBean> result = new Page<JcdcBean>(trasferDate, totalSize, pageSize, pageIndex);
//		return result;
//	}
	
	/**
	 * 检查督查详情页
	 * @param cpName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<MixCheckAndEnforceBean> getJcdcDetailsPageList(String userName,int pageIndex,int pageSize){
		IPage<MixCheckAndEnforceBean> page = checkRecordDao.getMixPageByUserName(userName, pageIndex, pageSize);
		if(page.getData()!=null&&page.getData().size()>0){
			Map<Integer,CheckItems> checkItemsMap = getCheckItemsMap();
			for(MixCheckAndEnforceBean meb:page.getData()){
				if(meb.getType()==1&&!Strings.isNullOrEmpty(meb.getCheckItems())){
					meb.setCheckItemNames(idsToNameStr(meb.getCheckItems(), checkItemsMap));
				}
			}
		}
		return page;
	}
	/**
	 * 执法详情页面
	 * @param efName
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<EnforceRecord> getEnforceRecordPage(String efUserName,int pageIndex,int pageSize){
		IPage<EnforceRecord> page =  enforceRecordDao.getEnforceRecordByEnforceName(efUserName, pageIndex, pageSize);
		return page;
	} 
	/**
	 * 获取执法人员信息
	 * @param userName
	 * @return
	 */
	public User getByUserName(String userName){
		return userDao.get(new SqlParamBean("user_name", userName));
	}
	/**
	 * 获取执法人员信息
	 * @param userName
	 * @return
	 */
	public AdminUser getAdminByUserName(String userName){
		return adminUserDao.get(new SqlParamBean("user_name", userName));
	}
	/**
	 * 获取用户最近的检查记录
	 * @param cpNames
	 * @return
	 */
//	private Map<String,CheckRecord> getRecentUserCheckRecord(List<String> userNames){
//		List<CheckRecord> list = checkRecordDao.getCheckRecordGroupCheckUserName(userNames);
//		Map<String,CheckRecord> result = Maps.newHashMap();
//		for(CheckRecord checkRecord:list){
//			result.put(checkRecord.getCheckUsername(), checkRecord);
//		}
//		return result;
//	}

	/**
	 * 查询所有管理员列表
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<AdminUser> getAdminUserPageList(int pageIndex,int pageSize){
		return adminUserDao.getPageList(pageIndex, pageSize, "");
	}
	
	/**
	 * 查询所有执法人员列表
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<User> getUserPageList(String sessionId,String uname,String name,String departMent,int pageIndex,int pageSize){
		UserInfo userInfo = this.isLogin(sessionId);
		if(userInfo.getRoleType()==1){
			departMent = userInfo.getDepartMent();
		}
		return userDao.getUserPageList(uname, name, departMent, pageIndex, pageSize);
	}
	/**
	 * 获取一个管理员
	 * @param userName
	 * @return
	 */
	public AdminUser getAdminUser(String userName){
		return adminUserDao.get(new SqlParamBean("user_name", userName));
	}
	/**
	 * 修改管理员密码
	 * @param adminUserName
	 * @param oldPasswd
	 * @param newPasswd
	 */
	public void editAdminPwd(String adminUserName,String oldPasswd,String newPasswd){
		String oldPasswdSha = MacShaUtils.doEncryptBase64(oldPasswd, SHA_SECRET);
		AdminUser adminUser = adminUserDao.get(new SqlParamBean("user_name", adminUserName),new SqlParamBean("and","pass_word", oldPasswdSha));
		if(adminUser==null){
			throw new ServiceException(1, "旧密码不正确");
		}
		String newPasswdShA = MacShaUtils.doEncryptBase64(newPasswd, SHA_SECRET);
		if(!adminUserDao.updatePassword(adminUserName, newPasswdShA)){
			throw new ServiceException(2, "更新密码失败");
		}
	}
	/**
	 * 删除管理员
	 * @param adminUserNames
	 */
	public void deleteAdmins(String[] adminUserNames){
		List<String> lists = Arrays.asList(adminUserNames) ;
		adminUserDao.deleteAdmins(lists);
	}
	/**
	 * 删除管理员
	 * @param adminUserNames
	 */
	public void deleteUsers(String[] userNames){
		List<String> lists = Arrays.asList(userNames) ;
		userDao.deleteUsers(lists);
	}	
	/**
	 * 天假用户
	 * @param userName
	 * @param passWord
	 * @param sex
	 * @param name
	 * @param call
	 * @param remark
	 * @param upUser
	 * @param birthday
	 */
	public void addAdmins(String userName,String passWord,int sex,String name,String call,String remark,String departMent){
		ParamCheck.checkString(userName, 1, "用户名不能为空");
		ParamCheck.checkString(passWord, 2, "密码不能为空");
		ParamCheck.checkString(name, 3, "名字不能为空");
//		ParamCheck.checkString(call, 4, "电话不能为空");
//		ParamCheck.checkString(upUser, 5, "上级用户不能为空");
		ParamCheck.checkObject(departMent, 6, "部门不能为空");
		AdminUser adminUser = adminUserDao.get(new SqlParamBean("user_name", userName));
		if(adminUser!=null){
			throw new ServiceException(7, "用户名已被注册了");
		}
		passWord = MacShaUtils.doEncryptBase64(passWord, SHA_SECRET);
		adminUser = new AdminUser(userName, passWord, name, sex, call, remark, departMent);
		if(!adminUserDao.add(adminUser)){
			throw new ServiceException(8, "添加失败！");
		}
	}
	/**
	 * 天假用户
	 * @param userName
	 * @param passWord
	 * @param sex
	 * @param name
	 * @param call
	 * @param remark
	 * @param upUser
	 * @param birthday
	 */
	public void addUsers(String userName,String passWord,int sex,String name,String call,String remark,String departMent,String lastEditName){
		ParamCheck.checkString(userName, 1, "用户名不能为空");
		ParamCheck.checkString(passWord, 2, "密码不能为空");
		ParamCheck.checkString(name, 3, "名字不能为空");
//		ParamCheck.checkString(call, 4, "电话不能为空");
		ParamCheck.checkString(departMent, 5, "部门不能为空");
//		ParamCheck.checkObject(birthday, 6, "生日不能为空");
		User adminUser = userDao.get(new SqlParamBean("user_name", userName));
		if(adminUser!=null){
			throw new ServiceException(7, "用户名已被注册了");
		}
		passWord = MacShaUtils.doEncryptBase64(passWord, SHA_SECRET);
		adminUser = new User(userName, passWord, name, sex, call, remark, departMent);
		adminUser.setLastEditName(lastEditName);
		if(!userDao.add(adminUser)){
			throw new ServiceException(8, "添加失败！");
		}
	}
	/**
	 * 修改用户
	 * @param userName
	 * @param passWord
	 * @param sex
	 * @param name
	 * @param call
	 * @param remark
	 * @param upUser
	 * @param birthday
	 */
	public void editAdmins(String userName,String passWord,int sex,String name,String call,String remark,String departMent){
		ParamCheck.checkString(userName, 1, "用户名不能为空");
		ParamCheck.checkString(name, 3, "名字不能为空");
//		ParamCheck.checkString(call, 4, "电话不能为空");
//		ParamCheck.checkObject(birthday, 6, "生日不能为空");
		AdminUser adminUser = adminUserDao.get(new SqlParamBean("user_name", userName));
		if(adminUser==null){
			throw new ServiceException(7, "用户不存在");
		}
		if(!Strings.isNullOrEmpty(passWord)){
			passWord = MacShaUtils.doEncryptBase64(passWord, SHA_SECRET);
		}
		
		AdminUser newAdminUser = new AdminUser(userName, passWord, name, sex, call, remark, departMent);
		newAdminUser.setStatus(adminUser.getStatus());
		if(Strings.isNullOrEmpty(passWord)){
			newAdminUser.setPassWord(adminUser.getPassWord());
		}
		if(!adminUserDao.updateAll(newAdminUser)){
			throw new ServiceException(8, "更新失败！");
		}
	}
	
	/**
	 * 修改用户
	 * @param userName
	 * @param passWord
	 * @param sex
	 * @param name
	 * @param call
	 * @param remark
	 * @param upUser
	 * @param birthday
	 */
	public void editUsers(String userName,String passWord,int sex,String name,String call,String remark,String departMent,String lastEditName){
		ParamCheck.checkString(userName, 1, "用户名不能为空");
		ParamCheck.checkString(name, 3, "名字不能为空");
//		ParamCheck.checkString(call, 4, "电话不能为空");
		ParamCheck.checkString(departMent, 5, "部门不能为空");
//		ParamCheck.checkObject(birthday, 6, "生日不能为空");
		User adminUser = userDao.get(new SqlParamBean("user_name", userName));
		if(adminUser==null){
			throw new ServiceException(7, "用户不存在");
		}
		if(!Strings.isNullOrEmpty(passWord)){
			passWord = MacShaUtils.doEncryptBase64(passWord, SHA_SECRET);
		}
		User newAdminUser = new User(userName, passWord, name, sex, call, remark, departMent);
		newAdminUser.setStatus(adminUser.getStatus());
		if(Strings.isNullOrEmpty(passWord)){
			newAdminUser.setPassWord(adminUser.getPassWord());
		}
		newAdminUser.setLastEditName(lastEditName);
		if(!userDao.updateAll(newAdminUser)){
			throw new ServiceException(8, "更新失败！");
		}
	}
	/**
	 * 修改用户状态
	 * @param userName
	 * @param status  0启用  1禁用
	 */
	public void editAdminStatus(String userName,int status){
		AdminUser adminUser = adminUserDao.get(new SqlParamBean("user_name", userName));
		if(adminUser==null){
			throw new ServiceException(1, "用户不存在");
		}
		adminUser.setStatus(status);
		if(!adminUserDao.updateAll(adminUser)){
			throw new ServiceException(2, "更新失败！");
		}
	}
	
	/**
	 * 修改用户状态
	 * @param userName
	 * @param status  0启用  1禁用
	 */
	public void editUserStatus(String userName,int status){
		User adminUser = userDao.get(new SqlParamBean("user_name", userName));
		if(adminUser==null){
			throw new ServiceException(1, "用户不存在");
		}
		adminUser.setStatus(status);
		if(!userDao.updateAll(adminUser)){
			throw new ServiceException(2, "更新失败！");
		}
	}
	/**
	 * 查询所有大类检查项
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<CheckItems> getZeroCheckItemsPageList(String sessionId,int pageIndex,int pageSize){
		UserInfo userInfo = this.isLogin(sessionId);
		String departMent = null;
		if(userInfo.getRoleType()==1){
			departMent = userInfo.getDepartMent();
		}
		return checkItemsDao.getBigCheckItemsList(departMent, pageIndex, pageSize);
	}
	/**
	 * 添加检查项
	 * @param title
	 * @param content
	 */
	public int addCheckItems(String sessionId,String title,String firstItemName,String secondItemName,String resultItemName,String departMent,int successOrFail){
		ParamCheck.checkString(title, 1, "大类名称不能为空");
		ParamCheck.checkString(firstItemName, 2, "大项名称不能为空");
		ParamCheck.checkString(secondItemName, 3, "小项名称不能为空");
		ParamCheck.checkString(resultItemName, 4, "小项名称不能为空");
		CheckItems temp = checkItemsDao.get(new SqlParamBean("item_title", title),new SqlParamBean(AND,"parent_id", 0));
		if(temp!=null){
			throw new ServiceException(5, "已存在的大类名称"+title);
		}
		//添加大类
		CheckItems t = new CheckItems();
		t.setItemTitle(title);
		t.setAddTime(new Date());
		t.setEditTime(new Date());
		t.setStatus(1);
		t.setParentId(0);
		t.setDepartMent(departMent);
		t.setLastEditName(isLogin(sessionId).getName());
		int id = checkItemsDao.addBackKey(t);
		
		
		CheckItems first = new CheckItems();
		first.setItemTitle(firstItemName);
		first.setAddTime(new Date());
		first.setParentId(id);
		int firstId=checkItemsDao.addBackKey(first);
		
		CheckItems second = new CheckItems();
		second.setItemTitle(secondItemName);
		second.setAddTime(new Date());
		second.setParentId(firstId);
		int secondId=checkItemsDao.addBackKey(second);
		
		CheckItems result = new CheckItems();
		result.setItemTitle(resultItemName);
		result.setAddTime(new Date());
		result.setParentId(secondId);
		result.setSuccessOrFail(successOrFail);
		checkItemsDao.addBackKey(result);
		
		return id;
	}
	/**
	 * 修改检查项
	 * @param id
	 * @param title
	 * @param content
	 */
	public void editCheckItems(String sessionId,int id,String title,String firstItemName,String secondItemName,String resultItemName,String departMent,int successOrFail){
		ParamCheck.checkString(title, 1, "大类名称不能为空");
		ParamCheck.checkString(firstItemName, 2, "大项名称不能为空");
		ParamCheck.checkString(secondItemName, 3, "小项名称不能为空");
		ParamCheck.checkString(resultItemName, 4, "小项名称不能为空");
		UserInfo userInfo = isLogin(sessionId);
		CheckItems temp = checkItemsDao.get(new SqlParamBean("id", id),new SqlParamBean(AND,"parent_id", 0));
		if(temp==null){
			throw new ServiceException(6, "修改的大类不存在，id="+id);
		}
		temp.setItemTitle(title);
		temp.setDepartMent(departMent);
		temp.setLastEditName(userInfo.getName());
		checkItemsDao.update(temp);
		
		CheckItems first = checkItemsDao.get(new SqlParamBean("item_title", firstItemName),new SqlParamBean(AND,"parent_id", id));
		if(first!=null){
			CheckItems second = checkItemsDao.get(new SqlParamBean("item_title", secondItemName),new SqlParamBean(AND,"parent_id", first.getId()));
			if(second!=null){
				CheckItems result = checkItemsDao.get(new SqlParamBean("item_title", resultItemName),new SqlParamBean(AND,"parent_id", second.getId()));
				if(result!=null){
					if(result.getSuccessOrFail()!=successOrFail){
						result.setSuccessOrFail(successOrFail);
						checkItemsDao.update(result);
					}
				}else{
					result = new CheckItems();
					result.setItemTitle(resultItemName);
					result.setAddTime(new Date());
					result.setParentId(second.getId());
					result.setSuccessOrFail(successOrFail);
					checkItemsDao.addBackKey(result);
				}
			}else{//添加2层
				second = new CheckItems();
				second.setItemTitle(secondItemName);
				second.setAddTime(new Date());
				second.setParentId(first.getId());
				int secondId=checkItemsDao.addBackKey(second);
				
				CheckItems result = new CheckItems();
				result.setItemTitle(resultItemName);
				result.setAddTime(new Date());
				result.setParentId(secondId);
				result.setSuccessOrFail(successOrFail);
				checkItemsDao.addBackKey(result);
			}
		}else{//后面的全部重新添加
			first = new CheckItems();
			first.setItemTitle(firstItemName);
			first.setAddTime(new Date());
			first.setParentId(id);
			int firstId=checkItemsDao.addBackKey(first);
			
			CheckItems second = new CheckItems();
			second.setItemTitle(secondItemName);
			second.setAddTime(new Date());
			second.setParentId(firstId);
			int secondId=checkItemsDao.addBackKey(second);
			
			CheckItems result = new CheckItems();
			result.setItemTitle(resultItemName);
			result.setAddTime(new Date());
			result.setParentId(secondId);
			result.setSuccessOrFail(successOrFail);
			checkItemsDao.addBackKey(result);
		}
		
	}
	/**
	 * 删除检小查项
	 * @param ids
	 */
	public void deleteSmallCheckItems(int[] ids){
		for(int id:ids){
			deleteCheckItemsTree(id);
		}
	}
	
	/**
	 * 删除大检查项
	 * @param ids
	 */
	public void deleteBigCheckItems(int[] ids){
		for(int id:ids){
			checkItemsDao.delete(new SqlParamBean("id", id));//删自己
			List<CheckItems> firstCheck = this.getDownCheckItems(id);
			if(firstCheck!=null&&firstCheck.size()>0){
				for(CheckItems first:firstCheck){
					deleteCheckItemsTree(first.getId());
				}
			}
		}
	}
	/**
	 * 更新大类的状态
	 * @param ids
	 * @param status
	 */
	public void updateBigCheckItemsStatus(int[] ids,int status){
		for(int id:ids){
			checkItemsDao.updateStatus(id, status);//更新状态
		}
	}
	
	/**
	 * 删除前三级
	 * @param id
	 */
	private void deleteCheckItemsTree(int id){
		CheckItems checkItem = checkItemsDao.get(new SqlParamBean("id", id));
		if(checkItem!=null){
			checkItemsDao.delete(new SqlParamBean("id", id));//删自己
			List<CheckItems> downList = this.getDownCheckItems(id);
			if(downList!=null&&downList.size()>0){
				checkItemsDao.delete(new SqlParamBean("parent_id", id));//删第二级
				for(CheckItems items:downList){
					checkItemsDao.delete(new SqlParamBean("parent_id", items.getId()));//删第三级
				}
			}
		}
	}
	
	/**
	 * 查询所有检查项
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<Notice> getNoticeList(String sessionId,int pageIndex,int pageSize){
		UserInfo userInfo = this.isLogin(sessionId);
		String departMent = null;
		String sql = "";
		if(userInfo.getRoleType()==1){
			departMent = userInfo.getDepartMent();
			sql = sql + " where depart_ment='"+departMent+"'";
		}
		sql = sql + " order by status desc,id desc";
		return noticeDao.getPageList(pageIndex, pageSize, sql);
	}
	/**
	 * 添加检查项
	 * @param title
	 * @param content
	 */
	public int addNotice(String sessionId,String title,String content,String attachMent){
		ParamCheck.checkString(title, 1, "标题不能为空");
		ParamCheck.checkString(content, 2, "内容不能为空");
		UserInfo userInfo = this.isLogin(sessionId);
		Notice t = new Notice();
		t.setNoticeTitle(title);
		t.setNoticeContent(content);
		t.setAddTime(new Date());
		t.setEditTime(new Date());
		t.setLastEditName(userInfo.getName());
		t.setDepartMent(userInfo.getCurrentDepartMent());
		t.setAttachMent(attachMent);
		t.setStatus(1);
		return noticeDao.addBackKey(t);
	}
	/**
	 * 修改检查项
	 * @param id
	 * @param title
	 * @param content
	 */
	public void editNotice(String sessionId,int id,String title,String content,String attachMent){
		ParamCheck.checkString(title, 1, "标题不能为空");
		ParamCheck.checkString(content, 2, "内容不能为空");
		UserInfo userInfo = this.isLogin(sessionId);
		noticeDao.update(id, title, content, userInfo.getName(), attachMent, userInfo.getCurrentDepartMent());
	}
	/**
	 * 删除检查项
	 * @param ids
	 */
	public void deleteNotice(int[] ids){
		for(int id:ids){
			noticeDao.delete(new SqlParamBean("id", id));
		}
	}
	/**
	 * 更新状态
	 * @param ids
	 * @param status 0 停用   1启用   2顶置
	 */
	public void editNoticeStatus(int[] ids,int status){
		for(int id:ids){
			noticeDao.updateStatus(id, status);
		}
	}
	
	
	
	public Notice getOne(int id){
		return noticeDao.get(new SqlParamBean("id", id));
	}
	
	/**
	 * 保存图片
	 * @param files
	 * @param fileNames
	 * @param taskId
	 * @param type
	 */
	public String[] saveAttach(File file, String names,String descPath) {
	        String[] result = new String[2];
			String oldFileName = names;
			String[] prefixArray = oldFileName.split("\\.");
			String prefix = prefixArray[prefixArray.length-1];
			String fileName = MD5Security.md5_16(UUID.randomUUID().toString()) + "." + prefix;
			try {
				
				FileUtils.copyFile(file, new File(descPath + fileName));
			} catch (IOException e) {
				LogSystem.error(e, "上传附件失败");
				throw new ServiceException(3, "上传附件失败！请重新上传！");
			}
			String size = getFormatSize(file.length());
			result[0] = fileName;
			result[1] = size ;
			return result;
	}
	public static void main(String[] args) {
		System.out.println( MacShaUtils.doEncryptBase64("xx", SHA_SECRET));
		AdminService service = new AdminService();
		service.getItemPhoto("1#2_adsfasfsafsaf.jpg");
	}
	
	
    public static String getFormatSize(double size) {  
        double kiloByte = size/1024;  
        if(kiloByte < 1) {  
            return size + "Byte(s)";  
        }  
          
        double megaByte = kiloByte/1024;  
        if(megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";  
        }  
          
        double gigaByte = megaByte/1024;  
        if(gigaByte < 1) {  
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
        }  
          
        double teraBytes = gigaByte/1024;  
        if(teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";  
        }  
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";  
    }  
	
}

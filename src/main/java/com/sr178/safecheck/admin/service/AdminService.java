package com.sr178.safecheck.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.common.jdbc.bean.Page;
import com.sr178.common.jdbc.bean.SqlParamBean;
import com.sr178.safecheck.admin.bean.JcdcBean;
import com.sr178.safecheck.admin.bean.JctjBean;
import com.sr178.safecheck.admin.bean.MixCheckAndEnforceBean;
import com.sr178.safecheck.admin.bo.AdminUser;
import com.sr178.safecheck.admin.bo.CheckItems;
import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.admin.bo.EnforceRecord;
import com.sr178.safecheck.admin.bo.Notice;
import com.sr178.safecheck.admin.bo.User;
import com.sr178.safecheck.admin.dao.AdminUserDao;
import com.sr178.safecheck.app.dao.CheckItemsDao;
import com.sr178.safecheck.app.dao.CheckRecordDao;
import com.sr178.safecheck.app.dao.EnforceRecordDao;
import com.sr178.safecheck.app.dao.NoticeDao;
import com.sr178.safecheck.app.dao.UserDao;
import com.sr178.safecheck.common.exception.ServiceException;
import com.sr178.safecheck.common.utils.MacShaUtils;
import com.sr178.safecheck.common.utils.ParamCheck;




public class AdminService {
	public static final String AND = "and";
	public static final String OR = "or";

  	private Cache<String,String> userSession = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).maximumSize(2000).build();

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
	public List<AdminUser> getAllAdminUser(){
		return adminUserDao.getAll();
	}

	/**
	 * 查看是否登录了
	 * 
	 * @param sessionId
	 * @return
	 */
	public String isLogin(String sessionId) {
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
		if(adminUser==null){
			throw new ServiceException(1, "用户名或密码错误！");
		}
		if(adminUser.getStatus()==1){
			throw new ServiceException(2, "该用户已被禁用，请及时联系管理员！");
		}
		userSession.put(sessionId, adminUser.getUserName());
	}
	/**
	 * 登出
	 * @param sessionId
	 */
	public void loginout(String sessionId){
		userSession.invalidate(sessionId);
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
				cpNames.add(checkRecord.getCn());
			}
			Map<String,EnforceRecord> recentEnforceMap = getRecentCpEnforceRecord(cpNames);
			for(CheckRecord checkRecord:page.getData()){
				checkRecord.setCpName(checkRecord.getCn());
				JctjBean bean = new JctjBean();
				bean.setCpName(checkRecord.getCn());
				bean.setCheckRecord(checkRecord);
				bean.setEnforceRecord(recentEnforceMap.get(checkRecord.getCn()));
				bean.setItemsNames(idsToNames(checkRecord.getCheckItems(),checkItemsMap));
				trasferDate.add(bean);
			}
		}
		return result;
	}
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
	public IPage<CheckRecord> getCheckRecordPage(String cpName,int pageIndex,int pageSize){
		IPage<CheckRecord> page =  checkRecordDao.getCheckRecordByCpName(cpName, pageIndex, pageSize);
		if(page!=null&&page.getData()!=null){
			//设置检查项
			Map<Integer,CheckItems> checkItemsMap = getCheckItemsMap();
			for(CheckRecord record:page.getData()){
				record.setCheckItemNames(idsToNames(record.getCheckItems(), checkItemsMap));
			}
		}
		return page;
	}
	
	
	/**
	 * 检查督查
	 * @param searchUn
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<JcdcBean> getJcdcBeanPageList(String adminUserName,String searchUn,int pageIndex,int pageSize){
		AdminUser adminUser = adminUserDao.get(new SqlParamBean("user_name", adminUserName));
		List<String> findUser = null;//如果是null 则是超级管理员  需要查询出全部  如果不是null  则需要查询指定数量的用户记录出来
		long totalSize = 0;
		
		Collection<JcdcBean> trasferDate = new ArrayList<JcdcBean>();
		
		if(!Strings.isNullOrEmpty(adminUser.getUpUser())){//非超级管理员  
			findUser = Lists.newArrayList();
			//c查询出他的下属
			List<User> listUser = userDao.getList(new SqlParamBean("up_user", adminUserName));
			if(listUser!=null&&listUser.size()>0){
				 for(User user:listUser){
					 findUser.add(user.getUserName());
				 }
			}
		}
		IPage<EnforceRecord> recordList = null;
		if(findUser==null||findUser.size()!=0){//是管理员 或有下属的才进行查询
			recordList = enforceRecordDao.getEnforceRecordGroupByEnforceUserName(searchUn,findUser,pageIndex,pageSize);
		}
		if(recordList!=null&&recordList.getTotalSize()>0){
			totalSize = recordList.getTotalSize();
			List<String> haveUserNames = Lists.newArrayList();
			for(EnforceRecord enforceRecord:recordList.getData()){
				haveUserNames.add(enforceRecord.getUn());
			}
			Map<String,CheckRecord> map =  getRecentUserCheckRecord(haveUserNames);
			JcdcBean bean = null; 
			for(EnforceRecord enforceRecord:recordList.getData()){
				bean = new JcdcBean();
				bean.setEnforceRecord(enforceRecord);
				CheckRecord recentCheckRecord = map.get(enforceRecord.getUn());
				if(recentCheckRecord!=null){
					bean.setCheckRecord(recentCheckRecord);
					enforceRecord.setEnforceName(recentCheckRecord.getCheckerName());
				}
				bean.setUserName(enforceRecord.getUn());
				trasferDate.add(bean);
			}
			
		}
		IPage<JcdcBean> result = new Page<JcdcBean>(trasferDate, totalSize, pageSize, pageIndex);
		return result;
	}
	
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
	private Map<String,CheckRecord> getRecentUserCheckRecord(List<String> userNames){
		List<CheckRecord> list = checkRecordDao.getCheckRecordGroupCheckUserName(userNames);
		Map<String,CheckRecord> result = Maps.newHashMap();
		for(CheckRecord checkRecord:list){
			result.put(checkRecord.getCheckUsername(), checkRecord);
		}
		return result;
	}

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
	 * 查询所有管理员列表
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<User> getUserPageList(String adminUser,int pageIndex,int pageSize){
		AdminUser adminUserBean = adminUserDao.get(new SqlParamBean("user_name", adminUser));
		if(Strings.isNullOrEmpty(adminUserBean.getUpUser())){//如果是超级管理员  则返回全部
			return userDao.getPageList(pageIndex, pageSize, "");
		}
		return userDao.getPageList(pageIndex, pageSize, new SqlParamBean("up_user", adminUser));
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
	public void addAdmins(String userName,String passWord,int sex,String name,String call,String remark,String upUser,Date birthday){
		ParamCheck.checkString(userName, 1, "用户名不能为空");
		ParamCheck.checkString(passWord, 2, "密码不能为空");
		ParamCheck.checkString(name, 3, "名字不能为空");
//		ParamCheck.checkString(call, 4, "电话不能为空");
		ParamCheck.checkString(upUser, 5, "上级用户不能为空");
//		ParamCheck.checkObject(birthday, 6, "生日不能为空");
		AdminUser adminUser = adminUserDao.get(new SqlParamBean("user_name", userName));
		if(adminUser!=null){
			throw new ServiceException(7, "用户名已被注册了");
		}
		passWord = MacShaUtils.doEncryptBase64(passWord, SHA_SECRET);
		adminUser = new AdminUser(userName, passWord, name, sex, birthday, call, remark, upUser);
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
	public void addUsers(String userName,String passWord,int sex,String name,String call,String remark,String upUser,Date birthday){
		ParamCheck.checkString(userName, 1, "用户名不能为空");
		ParamCheck.checkString(passWord, 2, "密码不能为空");
		ParamCheck.checkString(name, 3, "名字不能为空");
//		ParamCheck.checkString(call, 4, "电话不能为空");
		ParamCheck.checkString(upUser, 5, "上级用户不能为空");
//		ParamCheck.checkObject(birthday, 6, "生日不能为空");
		User adminUser = userDao.get(new SqlParamBean("user_name", userName));
		if(adminUser!=null){
			throw new ServiceException(7, "用户名已被注册了");
		}
		passWord = MacShaUtils.doEncryptBase64(passWord, SHA_SECRET);
		adminUser = new User(userName, passWord, name, sex, birthday, call, remark, upUser);
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
	public void editAdmins(String userName,String passWord,int sex,String name,String call,String remark,String upUser,Date birthday){
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
		
		AdminUser newAdminUser = new AdminUser(userName, passWord, name, sex, birthday, call, remark, upUser);
		newAdminUser.setStatus(adminUser.getStatus());
		newAdminUser.setUpUser(adminUser.getUpUser());
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
	public void editUsers(String userName,String passWord,int sex,String name,String call,String remark,String upUser,Date birthday){
		ParamCheck.checkString(userName, 1, "用户名不能为空");
		ParamCheck.checkString(name, 3, "名字不能为空");
//		ParamCheck.checkString(call, 4, "电话不能为空");
		ParamCheck.checkString(upUser, 5, "上级用户不能为空");
//		ParamCheck.checkObject(birthday, 6, "生日不能为空");
		User adminUser = userDao.get(new SqlParamBean("user_name", userName));
		if(adminUser==null){
			throw new ServiceException(7, "用户不存在");
		}
		if(!Strings.isNullOrEmpty(passWord)){
			passWord = MacShaUtils.doEncryptBase64(passWord, SHA_SECRET);
		}
		User newAdminUser = new User(userName, passWord, name, sex, birthday, call, remark, upUser);
		newAdminUser.setStatus(adminUser.getStatus());
		if(Strings.isNullOrEmpty(passWord)){
			newAdminUser.setPassWord(adminUser.getPassWord());
		}
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
	 * 查询所有检查项
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<CheckItems> getCheckItemsPageList(){
		return checkItemsDao.getAllOrder("order by id desc");
	}
	/**
	 * 添加检查项
	 * @param title
	 * @param content
	 */
	public void addCheckItems(String title,String content){
		ParamCheck.checkString(title, 1, "标题不能为空");
		ParamCheck.checkString(content, 2, "内容不能为空");
		CheckItems t = new CheckItems();
		t.setItemTitle(title);
		t.setItemContent(content);
		t.setAddTime(new Date());
		checkItemsDao.add(t);
	}
	/**
	 * 修改检查项
	 * @param id
	 * @param title
	 * @param content
	 */
	public void editCheckItems(int id,String title,String content){
		ParamCheck.checkString(title, 1, "标题不能为空");
		ParamCheck.checkString(content, 2, "内容不能为空");
		checkItemsDao.update(id, title, content);
	}
	/**
	 * 删除检查项
	 * @param ids
	 */
	public void deleteCheckItems(int[] ids){
		for(int id:ids){
			checkItemsDao.delete(new SqlParamBean("id", id));
		}
	}
	
	/**
	 * 查询所有检查项
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Notice> getNoticeList(){
		return noticeDao.getAllOrder("order by status desc,id desc");
	}
	/**
	 * 添加检查项
	 * @param title
	 * @param content
	 */
	public void addNotice(String title,String content){
		ParamCheck.checkString(title, 1, "标题不能为空");
		ParamCheck.checkString(content, 2, "内容不能为空");
		Notice t = new Notice();
		t.setNoticeTitle(title);
		t.setNoticeContent(content);
		t.setAddTime(new Date());
		t.setStatus(1);
		noticeDao.add(t);
	}
	/**
	 * 修改检查项
	 * @param id
	 * @param title
	 * @param content
	 */
	public void editNotice(int id,String title,String content){
		ParamCheck.checkString(title, 1, "标题不能为空");
		ParamCheck.checkString(content, 2, "内容不能为空");
		noticeDao.update(id, title, content);
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
	public static void main(String[] args) {
		System.out.println( MacShaUtils.doEncryptBase64("xx", SHA_SECRET));
	}
	
}

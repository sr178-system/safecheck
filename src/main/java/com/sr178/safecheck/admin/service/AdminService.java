package com.sr178.safecheck.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
import com.sr178.safecheck.admin.dao.AdminUserDao;
import com.sr178.safecheck.app.dao.CheckItemsDao;
import com.sr178.safecheck.app.dao.CheckRecordDao;
import com.sr178.safecheck.app.dao.EnforceRecordDao;
import com.sr178.safecheck.app.dao.NoticeDao;
import com.sr178.safecheck.app.dao.ResourceDao;
import com.sr178.safecheck.app.dao.UserDao;
import com.sr178.safecheck.common.exception.ServiceException;
import com.sr178.safecheck.common.utils.MacShaUtils;
import com.sr178.safecheck.common.utils.ParamCheck;




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
	
	private static final String SHA_SECRET = "!@#asDFA55214644";

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
		userSession.remove(sessionId);
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
		ParamCheck.checkString(call, 4, "电话不能为空");
		ParamCheck.checkString(upUser, 5, "上级用户不能为空");
		ParamCheck.checkObject(birthday, 6, "生日不能为空");
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
		ParamCheck.checkString(passWord, 2, "密码不能为空");
		ParamCheck.checkString(name, 3, "名字不能为空");
		ParamCheck.checkString(call, 4, "电话不能为空");
		ParamCheck.checkString(upUser, 5, "上级用户不能为空");
		ParamCheck.checkObject(birthday, 6, "生日不能为空");
		AdminUser adminUser = adminUserDao.get(new SqlParamBean("user_name", userName));
		if(adminUser==null){
			throw new ServiceException(7, "用户不存在");
		}
		passWord = MacShaUtils.doEncryptBase64(passWord, SHA_SECRET);
		AdminUser newAdminUser = new AdminUser(userName, passWord, name, sex, birthday, call, remark, upUser);
		newAdminUser.setStatus(adminUser.getStatus());
		if(!adminUserDao.updateAll(newAdminUser)){
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
	
	public static void main(String[] args) {
		System.out.println( MacShaUtils.doEncryptBase64("xx", SHA_SECRET));
	}
	
}

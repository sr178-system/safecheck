package com.sr178.safecheck.app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.sr178.common.jdbc.bean.SqlParamBean;
import com.sr178.safecheck.admin.bo.CheckItems;
import com.sr178.safecheck.admin.bo.Resource;
import com.sr178.safecheck.admin.bo.SignRecord;
import com.sr178.safecheck.admin.bo.User;
import com.sr178.safecheck.app.dao.CheckItemsDao;
import com.sr178.safecheck.app.dao.CheckRecordDao;
import com.sr178.safecheck.app.dao.EnforceRecordDao;
import com.sr178.safecheck.app.dao.NoticeDao;
import com.sr178.safecheck.app.dao.ResourceDao;
import com.sr178.safecheck.app.dao.SignRecordDao;
import com.sr178.safecheck.app.dao.UserDao;
import com.sr178.safecheck.common.exception.ServiceException;
import com.sr178.safecheck.common.utils.ParamCheck;

public class AppService {
	public static final String AND = "and";
	public static final String OR = "or";

	Map<String, String> userToken = new ConcurrentHashMap<String, String>();
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

	/**
	 * 查看是否登录了
	 * 
	 * @param sessionId
	 * @return
	 */
	public String isLogin(String tokenId) {
		if (tokenId == null || "".equals(tokenId)) {
			return null;
		}
		return userToken.get(tokenId);
	}

	/**
	 * 登录
	 * 
	 * @param userName
	 * @param passWord
	 */
	public String login(String userName, String passWord) {
		ParamCheck.checkString(userName, 1, "用户名不能为空");
		ParamCheck.checkString(userName, 2, "密码不能为空");

		User user = userDao.get(new SqlParamBean("user_name", userName), new SqlParamBean(AND, "pass_word", passWord));
		if (user == null) {
			throw new ServiceException(1, "错误的用户名或密码");
		}
		String tokenId = UUID.randomUUID().toString();
		userToken.put(tokenId, user.getUserName());
		return tokenId;
	}

	/**
	 * 修改密码
	 * 
	 * @param userName
	 * @param oldPwd
	 * @param newPwd
	 */
	public void editpwd(String userName, String oldPwd, String newPwd) {
		ParamCheck.checkString(userName, 1, "用户名不能为空");
		ParamCheck.checkString(oldPwd, 2, "旧密码不能为空");
		ParamCheck.checkString(oldPwd, 3, "新密码不能为空");
		User user = userDao.get(new SqlParamBean("user_name", userName), new SqlParamBean(AND, "pass_word", oldPwd));
		if (user == null) {
			throw new ServiceException(4, "原始密码错误");
		}
		if (!userDao.updatePwd(userName, newPwd)) {
			throw new ServiceException(5, "更新异常");
		}
	}

	/**
	 * 获取所有检查项
	 * 
	 * @return
	 */
	public List<CheckItems> checkList() {
		return checkItemsDao.getAll();
	}
    /**
     * 签到
     * @param userName
     * @param cpName
     * @param signTime
     * @param position
     * @return
     */
	public int sign(String userName, String cpName, Long signTime, String position) {
		User user = userDao.get(new SqlParamBean("user_name", userName));
		ParamCheck.checkString(cpName, 1, "企业名称不能为空");
		ParamCheck.checkObject(signTime, 2, "签到时间不能为空");
		ParamCheck.checkString(position, 3, "签到位置不能为空");
		int resourceId = addResource();
		SignRecord signRecord = new SignRecord();
		signRecord.setCpName(cpName);
		signRecord.setPosition(position);
		signRecord.setResourceId(resourceId);
		signRecord.setSignerName(user.getName());
		signRecord.setSignerUsername(userName);
		signRecord.setSignTime(new Date(signTime.longValue()));
		signRecordDao.add(signRecord);
		return resourceId;
	}
	
	

	/**
	 * 添加资源
	 * 
	 * @return
	 */
	public int addResource() {
		Resource resource = new Resource();
		resource.setCreatedTime(new Date());
		resource.setUpdatedTime(new Date());
		resourceDao.add(resource);
		return resourceDao.getLastInsertId();
	}

}

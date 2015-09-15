package com.sr178.safecheck.admin.dao;

import java.util.List;

import com.sr178.common.jdbc.SqlParameter;
import com.sr178.common.jdbc.util.SqlUtil;
import com.sr178.safecheck.admin.bo.AdminUser;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class AdminUserDao extends SfDaoBase<AdminUser> {

	public boolean updatePassword(String userName,String password){
		String sql = "update "+super.getTable()+" set pass_word=? where user_name=? limit 1";
		return super.getJdbc().update(sql, SqlParameter.Instance().withString(password).withString(userName))>0;
	}
	/**
	 * 删除管理员
	 * @param admins
	 * @return
	 */
	public boolean deleteAdmins(List<String> admins){
		String sql = "delete from "+super.getTable()+" where user_name in("+SqlUtil.joinStr(admins)+") and up_user<>''";
		return super.getJdbc().update(sql, null)>0;
	}
	/**
	 * 更新用户信息
	 * @param adminUser
	 * @return
	 */
	public boolean updateAll(AdminUser adminUser){
		String sql = "update "+super.getTable()+" set pass_word=?,name=?,sex=?,birthday=?,call=?,remark=?,up_user=?,status=? where user_name=? limit 1";
		SqlParameter parameter = SqlParameter.Instance();
		parameter.setString(adminUser.getPassWord());
		parameter.setString(adminUser.getName());
		parameter.setInt(adminUser.getSex());
		parameter.setObject(adminUser.getBirthday());
		parameter.setString(adminUser.getCall());
		parameter.setString(adminUser.getRemark());
		parameter.setString(adminUser.getUpUser());
		parameter.setInt(adminUser.getStatus());
		parameter.setString(adminUser.getUserName());
		return super.getJdbc().update(sql, parameter)>0;
	}
}

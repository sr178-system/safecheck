package com.sr178.safecheck.app.dao;


import java.util.List;

import com.google.common.base.Strings;
import com.sr178.common.jdbc.SqlParameter;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.common.jdbc.util.SqlUtil;
import com.sr178.safecheck.admin.bo.User;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class UserDao extends SfDaoBase<User>{
    
	
	public boolean updatePwd(String userName,String newPwd){
		String sql = "update "+super.getTable()+" set pass_word=? where user_name=? limit 1";
		return super.getJdbc().update(sql, SqlParameter.Instance().withString(newPwd).withString(userName))>0;
	}
	
	
	public boolean updatePassword(String userName,String password){
		String sql = "update "+super.getTable()+" set pass_word=? where user_name=? limit 1";
		return super.getJdbc().update(sql, SqlParameter.Instance().withString(password).withString(userName))>0;
	}
	/**
	 * 删除用户
	 * @param admins
	 * @return
	 */
	public boolean deleteUsers(List<String> admins){
		String sql = "delete from "+super.getTable()+" where user_name in("+SqlUtil.joinStr(admins)+")";
		return super.getJdbc().update(sql, null)>0;
	}
	/**
	 * 更新用户信息
	 * @param adminUser
	 * @return
	 */
	public boolean updateAll(User adminUser){
		String sql = "update "+super.getTable()+" set pass_word=?,name=?,sex=?,`call`=?,remark=?,status=?,depart_ment=?,last_edit_name=? where user_name=? limit 1";
		SqlParameter parameter = SqlParameter.Instance();
		parameter.setString(adminUser.getPassWord());
		parameter.setString(adminUser.getName());
		parameter.setInt(adminUser.getSex());
		parameter.setString(adminUser.getCall());
		parameter.setString(adminUser.getRemark());
		parameter.setInt(adminUser.getStatus());
		parameter.setString(adminUser.getDepartMent());
		parameter.setString(adminUser.getLastEditName());
		parameter.setString(adminUser.getUserName());
		return super.getJdbc().update(sql, parameter)>0;
	}
	
	/**
	 * 获取用户列表
	 * @param uname
	 * @param name
	 * @param departMent
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<User> getUserPageList(String uname,String name,String departMent,int pageIndex,int pageSize){
		String sql = "select * from "+super.getTable()+" where 1=1";
		SqlParameter parameter = SqlParameter.Instance();
		if(!Strings.isNullOrEmpty(uname)){
			sql = sql + " and user_name=?";
			parameter.setString(uname);
		}
		if(!Strings.isNullOrEmpty(name)){
			sql = sql + " and name=?";
			parameter.setString(name);
		}
		if(!Strings.isNullOrEmpty(departMent)){
			sql = sql + " and depart_ment=?";
			parameter.setString(departMent);
		}
		return jdbc.getListPage(sql, User.class, parameter, pageSize, pageIndex);
	}
}

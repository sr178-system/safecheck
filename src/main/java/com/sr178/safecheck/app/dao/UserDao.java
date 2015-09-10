package com.sr178.safecheck.app.dao;


import com.sr178.common.jdbc.SqlParameter;
import com.sr178.safecheck.admin.bo.User;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class UserDao extends SfDaoBase<User>{
    
	
	public boolean updatePwd(String userName,String newPwd){
		String sql = "update "+super.getTable()+" set pass_word=? where user_name=? limit 1";
		return super.getJdbc().update(sql, SqlParameter.Instance().withString(newPwd).withString(userName))>0;
	}
}

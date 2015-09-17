package com.sr178.safecheck.app.dao;

import com.sr178.common.jdbc.SqlParameter;
import com.sr178.safecheck.admin.bo.CheckItems;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class CheckItemsDao extends SfDaoBase<CheckItems> {

	public boolean update(int id,String title,String content){
		String sql = "update "+super.getTable()+" set item_title=?,item_content=? where id=? limit 1";
		SqlParameter parameter = SqlParameter.Instance();
		parameter.withString(title);
		parameter.withString(content);
		parameter.withInt(id);
		return super.getJdbc().update(sql, parameter)>0;
	}
}

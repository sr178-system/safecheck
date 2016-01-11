package com.sr178.safecheck.app.dao;

import java.util.List;

import com.google.common.base.Strings;
import com.sr178.common.jdbc.SqlParameter;
import com.sr178.safecheck.admin.bo.CheckItems;
import com.sr178.safecheck.app.bo.BigCheckItemBO;
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
	
	public List<BigCheckItemBO> getBigCheckItemBOList(String departMent){
		String sql = "select id,item_title from "+super.getTable()+" where parent_id=0";
		SqlParameter parameter = SqlParameter.Instance();
		if(!Strings.isNullOrEmpty(departMent)){
			sql = sql +" and departMent=?";
			parameter.withString(departMent);
		}
		return super.getJdbc().getList(sql, BigCheckItemBO.class, parameter);
	}
	
}

package com.sr178.safecheck.app.dao;

import java.util.List;

import com.google.common.base.Strings;
import com.sr178.common.jdbc.SqlParameter;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.safecheck.admin.bo.CheckItems;
import com.sr178.safecheck.app.bo.BigCheckItemBO;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class CheckItemsDao extends SfDaoBase<CheckItems> {

	public boolean update(CheckItems checkItem){
		String sql = "update "+super.getTable()+" set item_title=?,last_edit_name=?,edit_time=now(),success_or_fail=?,depart_ment=? where id=? limit 1";
		SqlParameter parameter = SqlParameter.Instance();
		parameter.withString(checkItem.getItemTitle());
		parameter.withString(checkItem.getLastEditName());
		parameter.withInt(checkItem.getSuccessOrFail());
		parameter.withString(checkItem.getDepartMent());
		parameter.withInt(checkItem.getId());
		return super.getJdbc().update(sql, parameter)>0;
	}
	
	public List<BigCheckItemBO> getBigCheckItemBOList(String departMent){
		String sql = "select id,item_title from "+super.getTable()+" where parent_id=0 and status=1";
		SqlParameter parameter = SqlParameter.Instance();
		if(!Strings.isNullOrEmpty(departMent)){
			sql = sql +" and depart_ment=?";
			parameter.withString(departMent);
		}
		return super.getJdbc().getList(sql, BigCheckItemBO.class, parameter);
	}
	
	public IPage<CheckItems> getBigCheckItemsList(String departMent,int pageIndex,int pageSize){
		String sql = "select * from "+super.getTable()+" where parent_id=0 ";
		SqlParameter parameter = SqlParameter.Instance();
		if(!Strings.isNullOrEmpty(departMent)){
			sql = sql +" and depart_ment=?";
			parameter.withString(departMent);
		}
		sql = sql + " order by id desc";
		return super.getJdbc().getListPage(sql, CheckItems.class, parameter, pageSize, pageIndex);
	}
	
	public boolean updateStatus(int id,int status){
		String sql = "update "+super.getTable()+" set status=?,edit_time=now() where id=? limit 1";
		SqlParameter parameter = SqlParameter.Instance();
		parameter.withInt(status);
		parameter.withInt(id);
		return super.getJdbc().update(sql, parameter)>0;
	}
	
}

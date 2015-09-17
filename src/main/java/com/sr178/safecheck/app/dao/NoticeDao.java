package com.sr178.safecheck.app.dao;

import com.google.common.base.Strings;
import com.sr178.common.jdbc.SqlParameter;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.safecheck.admin.bo.Notice;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class NoticeDao extends SfDaoBase<Notice> {

	
	public IPage<Notice> getNoticePage(String searchStr,int pageIndex,int pageSize){
		
		String sql = "select id,notice_title,status,add_time from "+super.getTable();
		SqlParameter parameter = SqlParameter.Instance();
	    if(!Strings.isNullOrEmpty(searchStr)){
	    	sql = sql + " where notice_title like '%"+searchStr+"%'";
	    }
		 sql = sql +  " order by status desc,add_time desc";
		return super.getJdbc().getListPage(sql, Notice.class, parameter, pageSize, pageIndex);
	}
	
	
	public boolean update(int id,String title,String content){
		String sql = "update "+super.getTable()+" set notice_title=?,notice_content=? where id=? limit 1";
		SqlParameter parameter = SqlParameter.Instance();
		parameter.withString(title);
		parameter.withString(content);
		parameter.withInt(id);
		return super.getJdbc().update(sql, parameter)>0;
	}
	
	public boolean updateStatus(int id,int status){
		String sql = "update "+super.getTable()+" set status=? where id=? limit 1";
		SqlParameter parameter = SqlParameter.Instance();
		parameter.withInt(status).withInt(id);
		return super.getJdbc().update(sql, parameter)>0;
	}
}

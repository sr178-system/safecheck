package com.sr178.safecheck.app.dao;

import java.util.List;

import com.google.common.base.Strings;
import com.sr178.common.jdbc.SqlParameter;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.safecheck.admin.bo.Notice;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class NoticeDao extends SfDaoBase<Notice> {

	
	public IPage<Notice> getNoticePage(String searchStr,String userName,String departMent,int pageIndex,int pageSize){
		String sql = "select no.id,no.notice_title,no.status,no.add_time,IF(un.notice_id is null,0,1) as `read` from "+super.getTable();
		SqlParameter parameter = SqlParameter.Instance();
		sql = sql+" no left join user_notice_readlog un on no.id=un.notice_id and un.user_name=? where no.status<>0";
		parameter.withString(userName);
		if(!Strings.isNullOrEmpty(departMent)){
			sql = sql +" and (no.depart_ment='' or no.depart_ment is null or no.depart_ment=?)";
			parameter.withString(departMent);
		}
	    if(!Strings.isNullOrEmpty(searchStr)){
	    	sql = sql + " and no.notice_title like '%"+searchStr+"%'";
	    }
		sql = sql +  " order by no.status desc,no.add_time desc";
		return super.getJdbc().getListPage(sql, Notice.class, parameter, pageSize, pageIndex);
	}
	
	
	public boolean update(int id,String title,String content,String lastEditName,String attachMent,String departMent){
		String sql = "update "+super.getTable()+" set notice_title=?,notice_content=?,last_edit_name=?,edit_time=now(),attach_ment=?,depart_ment=? where id=? limit 1";
		SqlParameter parameter = SqlParameter.Instance();
		parameter.withString(title);
		parameter.withString(content);
		parameter.withString(lastEditName);
		parameter.withString(attachMent);
		parameter.withString(departMent);
		parameter.withInt(id);
		return super.getJdbc().update(sql, parameter)>0;
	}
	
	public boolean updateStatus(int id,int status){
		String sql = "update "+super.getTable()+" set status=? where id=? limit 1";
		SqlParameter parameter = SqlParameter.Instance();
		parameter.withInt(status).withInt(id);
		return super.getJdbc().update(sql, parameter)>0;
	}
	
	public void setIsRead(String userName,int noticeId){
		String sql = "INSERT IGNORE INTO user_notice_readlog (user_name,notice_id,created_time) VALUES ('"+userName+"',"+noticeId+",now())";
		super.getJdbc().update(sql, null);
	}
	
	public List<Notice> getNoReadList(String userName,String departMent){
		String sql = "select no.id,no.notice_title,no.status,no.add_time,IF(un.notice_id is null,0,1) as `read` from "+super.getTable();
		SqlParameter parameter = SqlParameter.Instance();
		sql = sql+" no left join user_notice_readlog un on no.id=un.notice_id and un.user_name=? where un.notice_id is null and no.status<>0";
		parameter.withString(userName);
		if(!Strings.isNullOrEmpty(departMent)){
			sql = sql +" and (no.depart_ment='' or no.depart_ment is null or no.depart_ment=?)";
			parameter.withString(departMent);
		}
		sql = sql +  " order by no.status desc, no.add_time desc";
		return super.getJdbc().getList(sql, Notice.class,parameter);
	}
}

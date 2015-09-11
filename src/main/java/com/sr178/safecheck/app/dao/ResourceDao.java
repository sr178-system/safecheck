package com.sr178.safecheck.app.dao;

import java.util.Date;

import com.sr178.common.jdbc.SqlParameter;
import com.sr178.safecheck.admin.bo.Resource;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class ResourceDao extends SfDaoBase<Resource> {

	public boolean updateResource(int resourceId,String resource,int type){
		String sql = "update "+ super.getTable()+" set resource_?_names=?,updated_time=? where resource_id=? limit 1";
		return super.getJdbc().update(sql, SqlParameter.Instance().withInt(type).withString(resource).withObject(new Date()).withInt(resourceId))>0;
	}
}

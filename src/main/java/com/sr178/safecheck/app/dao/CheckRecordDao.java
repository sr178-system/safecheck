package com.sr178.safecheck.app.dao;

import java.util.List;

import com.google.common.base.Strings;
import com.sr178.common.jdbc.SqlParameter;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.common.jdbc.util.SqlUtil;
import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class CheckRecordDao extends SfDaoBase<CheckRecord> {

	
	public IPage<CheckRecord> getCheckRecordPageListGroupByCpName(String searchCp,int pageIndex,int pageSize){
		String sql = "select * from (select cr.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from "+super.getTable()+" as cr left join resource as r on cr.resource_id=r.resource_id";
		if(!Strings.isNullOrEmpty(searchCp)){
			sql = sql + " where cr.cp_name like '%"+searchCp+"%'";
		}
		sql = sql+" group by cr.cp_name order by cr.check_time desc)cc order by cc.check_time desc";
		return super.getJdbc().getListPage(sql, CheckRecord.class, null, pageSize, pageIndex);
	}
	
	
	
	public List<CheckRecord> getCheckRecordGroupCheckUserName(List<String> userNames){
		String sql = "select er.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from "+super.getTable()+" as er left join resource as r on er.resource_id=r.resource_id where er.check_username in("+SqlUtil.joinStr(userNames)+") group by er.check_username order by er.check_time desc";
		return super.getJdbc().getList(sql, CheckRecord.class);
	}
	public IPage<CheckRecord> getCheckRecordByCpName(String cpName,int pageIndex,int pageSize){
		String sql = "select * from (select er.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from "+super.getTable()+" as er left join resource as r on er.resource_id=r.resource_id where er.cp_name=? order by er.check_time desc)cc";
		return super.getJdbc().getListPage(sql, CheckRecord.class, SqlParameter.Instance().withString(cpName), pageSize, pageIndex);
	}
}

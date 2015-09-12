package com.sr178.safecheck.app.dao;

import com.google.common.base.Strings;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class CheckRecordDao extends SfDaoBase<CheckRecord> {

	
	public IPage<CheckRecord> getCheckRecordPageListGroupByCpName(String searchCp,int pageIndex,int pageSize){
		String sql = "select * from (select cr.*,,r.resource_1_names,r.resource_2_names,r.resource_3_names from "+super.getTable()+" as cr left join resource as r on cr.resource_id=r.resource_id";
		if(!Strings.isNullOrEmpty(searchCp)){
			sql = sql + " where cr.cp_name like '%"+searchCp+"%'";
		}
		sql = sql+" group by cr.cp_name order by cr.check_time desc)cc order by cc.check_time desc";
		return super.getJdbc().getListPage(sql, CheckRecord.class, null, pageSize, pageIndex);
	}
}

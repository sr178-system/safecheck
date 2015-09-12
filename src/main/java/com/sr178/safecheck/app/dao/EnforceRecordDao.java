package com.sr178.safecheck.app.dao;

import java.util.List;


import com.sr178.common.jdbc.util.SqlUtil;
import com.sr178.safecheck.admin.bo.EnforceRecord;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class EnforceRecordDao extends SfDaoBase<EnforceRecord> {

	public List<EnforceRecord> getEnforceRecordGroupByCpName(List<String> cpNames){
		String sql = "select er.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from "+super.getTable()+" as er left join resource as r on er.resource_id=r.recource_id where er.cp_name in("+SqlUtil.joinStr(cpNames)+") group by er.cp_name order by er.enforce_time desc";
		return super.getJdbc().getList(sql, EnforceRecord.class);
	}
}

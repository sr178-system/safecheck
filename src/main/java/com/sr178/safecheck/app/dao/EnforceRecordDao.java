package com.sr178.safecheck.app.dao;

import java.util.List;

import com.google.common.base.Strings;
import com.sr178.common.jdbc.SqlParameter;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.common.jdbc.util.SqlUtil;
import com.sr178.safecheck.admin.bo.EnforceRecord;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class EnforceRecordDao extends SfDaoBase<EnforceRecord> {

	public List<EnforceRecord> getEnforceRecordGroupByCpName(List<String> cpNames){
		String sql = "select * from (select er.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from "+super.getTable()+" as er left join resource as r on er.resource_id=r.resource_id where er.cp_name in("+SqlUtil.joinStr(cpNames)+") order by er.enforce_time desc,er.id desc)cc group by cc.cp_name";
		return super.getJdbc().getList(sql, EnforceRecord.class);
	}
	
	
	public IPage<EnforceRecord> getEnforceRecordGroupByEnforceUserName(String searchUn,List<String> enforcesNames,int pageIndex,int pageSize){
//		String sql = "select * from (select er.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from "+super.getTable()+" as er left join resource as r on er.resource_id=r.resource_id ";
		
		String sql = "select * from (select kk.un,u.name as nm from (select * from (select check_username as un from check_record group by check_username union select enforce_username as un from enforce_record group by enforce_username)aa group by aa.un)kk left join user u on kk.un=u.user_name)bb "
				+ "left join (select * from (select er.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from enforce_record as er left join resource as r on er.resource_id=r.resource_id order by er.enforce_time desc,er.id desc)cc "
				+ "group by cc.enforce_username)dd "
				+ "on bb.un=dd.enforce_username";
		
		String where = "";
		if(enforcesNames!=null){
			where = " where bb.nm in("+SqlUtil.joinStr(enforcesNames)+")";
		}
		if(!Strings.isNullOrEmpty(searchUn)){
			if(Strings.isNullOrEmpty(where)){
				where = " where bb.nm like '%"+searchUn+"%'";
			}else{
				where = where +" and bb.nm like '%"+searchUn+"%'";
			}
		}
		sql = sql + where;
//		sql = sql +" order by er.enforce_time desc,er.id desc)cc group by cc.enforce_username";
		return super.getJdbc().getListPage(sql, EnforceRecord.class, null, pageSize, pageIndex);
	}
	
	
	public IPage<EnforceRecord> getEnforceRecordByEnforceName(String efUserName,int pageIndex,int pageSize){
		String sql = "select * from (select er.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from "+super.getTable()+" as er left join resource as r on er.resource_id=r.resource_id where er.enforce_username=? order by er.enforce_time desc)cc";
		return super.getJdbc().getListPage(sql, EnforceRecord.class, SqlParameter.Instance().withString(efUserName), pageSize, pageIndex);
	}
	
	
	public IPage<EnforceRecord> getEnforceRecordByCpName(String cpName,int pageIndex,int pageSize){
		String sql = "select * from (select er.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from "+super.getTable()+" as er left join resource as r on er.resource_id=r.resource_id where er.cp_name=? order by er.enforce_time desc)cc";
		return super.getJdbc().getListPage(sql, EnforceRecord.class, SqlParameter.Instance().withString(cpName), pageSize, pageIndex);
	}
	
}

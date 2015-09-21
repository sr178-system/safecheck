package com.sr178.safecheck.app.dao;

import java.util.List;

import com.google.common.base.Strings;
import com.sr178.common.jdbc.SqlParameter;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.common.jdbc.util.SqlUtil;
import com.sr178.safecheck.admin.bean.MixCheckAndEnforceBean;
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
	
	public IPage<MixCheckAndEnforceBean> getMixPageByCpName(String cpName,int pageIndex,int pageSize){
		String sql = "select * from (select cr.id as id,cr.cp_name,cr.check_items,"
				+ "cr.check_username as username,cr.checker_name as name,cr.resource_id,"
				+ "cr.position,cr.check_time as times,1 as type,r.resource_1_names,r.resource_2_names,r.resource_3_names "
				+ "from check_record cr left join resource as r on cr.resource_id=r.resource_id "
				+ "where cr.cp_name=? union select er.id as id,er.cp_name,'' as check_items, er.enforce_username as username,"
				+ "er.enforce_name as name,er.resource_id,'' as position,er.enforce_time as times,2 as type,"
				+ "r1.resource_1_names,r1.resource_2_names,r1.resource_3_names from enforce_record as er "
				+ "left join resource as r1 on er.resource_id=r1.resource_id where er.cp_name=?)cc "
				+ "order by cc.times desc";
		return super.getJdbc().getListPage(sql, MixCheckAndEnforceBean.class, SqlParameter.Instance().withString(cpName).withString(cpName), pageSize, pageIndex);
	}
	
	public IPage<MixCheckAndEnforceBean> getMixPageByUserName(String userName,int pageIndex,int pageSize){
		String sql = "select * from (select cr.id as id,cr.cp_name,cr.check_items,"
				+ "cr.check_username as username,cr.checker_name as name,cr.resource_id,"
				+ "cr.position,cr.check_time as times,1 as type,r.resource_1_names,r.resource_2_names,r.resource_3_names "
				+ "from check_record cr left join resource as r on cr.resource_id=r.resource_id "
				+ "where cr.check_username=? union select er.id as id,er.cp_name,'' as check_items, er.enforce_username as username,"
				+ "er.enforce_name as name,er.resource_id,'' as position,er.enforce_time as times,2 as type,"
				+ "r1.resource_1_names,r1.resource_2_names,r1.resource_3_names from enforce_record as er "
				+ "left join resource as r1 on er.resource_id=r1.resource_id where er.enforce_username=?)cc "
				+ "order by cc.times desc";
		return super.getJdbc().getListPage(sql, MixCheckAndEnforceBean.class, SqlParameter.Instance().withString(userName).withString(userName), pageSize, pageIndex);
	}
}

package com.sr178.safecheck.app.dao;

import java.util.Date;
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
//		String sql = "select * from (select cr.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from "+super.getTable()+" as cr left join resource as r on cr.resource_id=r.resource_id";
//		if(!Strings.isNullOrEmpty(searchCp)){
//			sql = sql + " where cr.cp_name like '%"+searchCp+"%'";
//		}
		
		String sql = "select * from (select bb.cp_name as cn from (select cp_name from check_record group by cp_name union select cp_name from enforce_record group by cp_name)bb group by bb.cp_name)aa "
				+ "left join (select * from (select cr.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from check_record as cr left join resource as r on cr.resource_id=r.resource_id order by cr.check_time desc,cr.id desc)cc group by cc.cp_name)dd "
				+ "on dd.cp_name=aa.cn";
		if(!Strings.isNullOrEmpty(searchCp)){
			sql = sql + " where dd.cp_name like '%"+searchCp+"%'";
		}
//		sql = sql+" order by cr.check_time desc,cr.id desc)cc group by cc.cp_name";
		return super.getJdbc().getListPage(sql, CheckRecord.class, null, pageSize, pageIndex);
	}
	
	public List<CheckRecord> getCheckRecordGroupCheckUserName(List<String> userNames){
		String sql = "select * from (select er.*,r.resource_1_names,r.resource_2_names,r.resource_3_names from "+super.getTable()+" as er left join resource as r on er.resource_id=r.resource_id where er.check_username in("+SqlUtil.joinStr(userNames)+") order by er.check_time desc,er.id desc)cc group by cc.check_username";
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
				+ "order by cc.times desc,cc.id desc";
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
				+ "order by cc.times desc,cc.id desc";
		return super.getJdbc().getListPage(sql, MixCheckAndEnforceBean.class, SqlParameter.Instance().withString(userName).withString(userName), pageSize, pageIndex);
	}
	
	public List<String> searchCompay(String searchStr,int limit){
		String sql = "select distinct cp_name from "+super.getTable()+" where cp_name like '%"+searchStr+"%' limit "+limit;
		SqlParameter parameter = SqlParameter.Instance();
		return jdbc.queryForList(sql, String.class, parameter);
	}
	
	
	public Date getNextCheckTime(Date checkTime,String cpName){
		String sql = "select er.* from "
				+ super.getTable() + " as er "
				+ " where er.check_time>? and er.cp_name=?"
				+ " order by er.check_time asc limit 1"
				+ "";
		SqlParameter parameter = SqlParameter.Instance();
		parameter.withObject(checkTime);
		parameter.withString(cpName);
		CheckRecord result = jdbc.get(sql, CheckRecord.class, parameter);
		if(result!=null){
			return result.getCheckTime();
		}else{
			return null;
		}
	}
	
	
	public CheckRecord getCheckRecord(int recordId){
		String sql = "select er.*,r.resource_1_names,r.resource_2_names,r.resource_3_names,r.resource_4_names,ci.item_title as check_item_name from "
				+ super.getTable() + " as er " + "left join resource as r on er.resource_id=r.resource_id"
				+ " left join user as u on er.check_username=u.user_name "
				+ " left join check_items as ci on er.check_item_id=ci.id" 
				+ " where er.id=?";
		SqlParameter parameter = SqlParameter.Instance();
		parameter.withInt(recordId);
		return jdbc.get(sql, CheckRecord.class, parameter);
	}
	
	public IPage<CheckRecord> getCheckRecordPage(String departMent, String startDate, String endDate, String cpName,
			String checkName, Integer checkId, Integer checkResult, int pageIndex, int pageSize) {
		String sql = "select * from (select er.*,r.resource_1_names,r.resource_2_names,r.resource_3_names,r.resource_4_names,ci.item_title as check_item_name from "
				+ super.getTable() + " as er " + "left join resource as r on er.resource_id=r.resource_id"
				+ " left join user as u on er.check_username=u.user_name "
				+ " left join check_items as ci on er.check_item_id=ci.id" 
				+ " where 1=1";
		SqlParameter parameter = SqlParameter.Instance();
		if (!Strings.isNullOrEmpty(departMent)) {
			sql = sql + " and u.depart_ment=?";
			parameter.withString(departMent);
		}
		if(!Strings.isNullOrEmpty(startDate)){
			sql = sql +" and er.check_time>=?";
			parameter.withString(startDate+" 00:00:00");
		}
		if(!Strings.isNullOrEmpty(endDate)){
			sql = sql +" and er.check_time<=?";
			parameter.withString(endDate+" 23:59:59");
		}
		if(!Strings.isNullOrEmpty(cpName)){
			sql = sql +" and er.cp_name like '%"+cpName+"%'";
		}
		if(!Strings.isNullOrEmpty(checkName)){
			sql = sql +" and u.name like '%"+checkName+"%'";
		}
		if(checkId!=null){
			sql = sql +" and er.check_item_id=?";
			parameter.withInt(checkId);
		}
		if(checkResult!=null){
			sql = sql +" and er.pass_status=?";
			parameter.withInt(checkResult);
		}

		sql = sql + " order by er.check_time desc";

		sql = sql + ")cc";

		return jdbc.getListPage(sql, CheckRecord.class, parameter, pageSize, pageIndex);
	}
}

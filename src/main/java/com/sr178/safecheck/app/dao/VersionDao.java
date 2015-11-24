package com.sr178.safecheck.app.dao;

import com.sr178.safecheck.app.bo.Version;
import com.sr178.safecheck.common.dao.SfDaoBase;

public class VersionDao extends SfDaoBase<Version> {

	public Version getLastVersion(){
		String sql = "select * from "+super.getTable()+" order by version_num desc limit 1";
		return super.getJdbc().get(sql, Version.class, null);
	}
}

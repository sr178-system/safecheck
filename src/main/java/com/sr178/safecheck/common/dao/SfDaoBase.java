package com.sr178.safecheck.common.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.sr178.common.jdbc.Jdbc;
import com.sr178.common.jdbc.dao.BaseDao;

public class SfDaoBase<T> extends BaseDao<T> {
    @Autowired
	protected Jdbc jdbc;
	
	@Override
	public Jdbc getJdbc() {
		return jdbc;
	}

}

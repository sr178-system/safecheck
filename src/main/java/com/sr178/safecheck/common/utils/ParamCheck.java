package com.sr178.safecheck.common.utils;

import com.google.common.base.Strings;
import com.sr178.safecheck.common.exception.ServiceException;

public class ParamCheck {

	public static void checkString(String param,int code,String message){
		if(Strings.isNullOrEmpty(param)){
			throw new ServiceException(code, message);
		}
	}
	
	public static void checkObject(Object param,int code,String message){
		if(param==null){
			throw new ServiceException(code, message);
		}
	}
}

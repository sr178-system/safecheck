package com.sr178.safecheck.common.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * struts异常拦截器
 * 
 * @author mc
 * 
 */
public class StrutsExceptionInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		
		
		String result = null;
		try {
			result = invocation.invoke();
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
}

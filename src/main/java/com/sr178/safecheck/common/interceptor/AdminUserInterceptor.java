package com.sr178.safecheck.common.interceptor;


import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.game.framework.log.LogSystem;
import com.sr178.safecheck.common.action.ALDAdminActionSupport;
import com.sr178.safecheck.common.exception.ServiceException;
import com.sr178.safecheck.user.service.UserService;

public class AdminUserInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		// TODO Auto-generated method stub
		//HttpSession session = ServletActionContext.getRequest().getSession();
        //String sessionID = session.getId();
		UserService aus = ServiceCacheFactory.getServiceCache()
				.getService(UserService.class);
		HttpSession sessionhttp = ServletActionContext.getRequest()
				.getSession();
		String userName = aus.isLogin(sessionhttp.getId());
		if (userName==null) {
			return "nologin";
		} else {
//			Gcuser user = aus.getUserByUserName(userName);
			ALDAdminActionSupport aldAction = null;
			Object obj = actionInvocation.getAction();
			if (obj instanceof ALDAdminActionSupport) {
				aldAction = (ALDAdminActionSupport) obj;
				aldAction.setUserName(userName);
			} else {
				String className = obj.getClass().getCanonicalName();
				throw new RuntimeException("ACTION继承的类非ALDAdminActionSupport"+className);
			}
			// 异常处理
			try {
				String result = actionInvocation.invoke();
				return result;
			} catch (Exception e) {
				if(e instanceof ServiceException){
					ServiceException exception = (ServiceException)e;
					aldAction.setErroCodeNum(exception.getCode());
					LogSystem.info(exception.getMessage());
					return "success";
				}else{
					LogSystem.error(e, "");
					aldAction.setErroCodeNum(8888);
					return "glober_error";
				}
			}
		}
	}
}

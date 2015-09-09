package com.sr178.safecheck.app.interceptor;




import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.game.framework.log.LogSystem;
import com.sr178.safecheck.app.action.AppBaseActionSupport;
import com.sr178.safecheck.app.service.AppService;
import com.sr178.safecheck.common.exception.ServiceException;

public class AppUserInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		AppBaseActionSupport appAction = null;
		Object obj = actionInvocation.getAction();
		String className = obj.getClass().getCanonicalName();
		if (obj instanceof AppBaseActionSupport) {
			appAction = (AppBaseActionSupport) obj;
		} else {
			throw new RuntimeException("ACTION继承的类非AppBaseActionSupport"+className);
		}
		
		AppService aus = ServiceCacheFactory.getServiceCache()
				.getService(AppService.class);
		
		String userName = aus.isLogin(appAction.getTokenId());
		if (userName==null&&!className.equals("com.sr178.safecheck.app.action.AppNoAuthAction")) {
			appAction.renderErrorResult("token失效或没有登录");
			return "json";
		} else {
			appAction.setUserName(userName);
			// 异常处理
			try {
				String result = actionInvocation.invoke();
				return result;
			} catch (Exception e) {
				if(e instanceof ServiceException){
					ServiceException exception = (ServiceException)e;
					appAction.renderErrorResult(exception.getMessage());
					LogSystem.info(exception.getMessage());
					return "json";
				}else{
					LogSystem.error(e, "");
					appAction.renderErrorResult("未知错误！");
					return "json";
				}
			}
		}
	}
}

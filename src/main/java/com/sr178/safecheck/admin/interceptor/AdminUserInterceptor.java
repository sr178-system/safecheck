package com.sr178.safecheck.admin.interceptor;


import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.game.framework.log.LogSystem;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.common.action.ALDAdminActionSupport;
import com.sr178.safecheck.common.exception.ServiceException;

public class AdminUserInterceptor extends AbstractInterceptor {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        AdminService aus = ServiceCacheFactory.getServiceCache()
                .getService(AdminService.class);
        HttpSession sessionhttp = ServletActionContext.getRequest()
                .getSession();
        String userName = aus.isLogin(sessionhttp.getId());
        Object obj = actionInvocation.getAction();
        String className = obj.getClass().getCanonicalName();
        if (userName==null&&!className.equals("com.sr178.safecheck.admin.action.AdminLogin")) {
            return "nologin";
        } else {
//            Gcuser user = aus.getUserByUserName(userName);
            ALDAdminActionSupport aldAction = null;
            
            if (obj instanceof ALDAdminActionSupport) {
                aldAction = (ALDAdminActionSupport) obj;
                aldAction.setUserName(userName);
            } else {
                throw new RuntimeException("ACTION继承的类非ALDAdminActionSupport"+className);
            }
            // 异常处理
            try {
                String result = actionInvocation.invoke();
                return result;
            } catch (Exception e) {
                if(e instanceof ServiceException){
                    ServiceException exception = (ServiceException)e;
                    aldAction.setCode(exception.getCode());;
                    LogSystem.info(exception.getMessage());
                    return "success";
                }else{
                    LogSystem.error(e, "");
                     aldAction.setCode(8888);
                    return "glober_error";
                }
            }
        }
    }
}

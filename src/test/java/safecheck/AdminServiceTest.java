package safecheck;

import com.sr178.common.jdbc.bean.IPage;
import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.game.framework.testcore.DaoTest;
import com.sr178.safecheck.admin.bean.JcdcBean;
import com.sr178.safecheck.admin.service.AdminService;

public class AdminServiceTest extends DaoTest {

	public void testMethod(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		IPage<JcdcBean> rsult = adminService.getJcdcBeanPageList("xx", "", 0, 50);
	}
}

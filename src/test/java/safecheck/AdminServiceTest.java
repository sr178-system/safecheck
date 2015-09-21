package safecheck;

import com.sr178.common.jdbc.bean.IPage;
import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.game.framework.testcore.DaoTest;
import com.sr178.safecheck.admin.bean.JcdcBean;
import com.sr178.safecheck.admin.bean.MixCheckAndEnforceBean;
import com.sr178.safecheck.admin.service.AdminService;

public class AdminServiceTest extends DaoTest {

	public void testMethod(){
		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
		IPage<MixCheckAndEnforceBean> result1 = adminService.getJcdcDetailsPageList("xx", 0, 2);
		
		IPage<MixCheckAndEnforceBean> result2 = adminService.getJctjDetailsPageList("俗人工作室", 0, 2);
		
		System.out.println("ok");
	}
}

package safecheck;

import java.util.Date;

import com.sr178.common.jdbc.bean.IPage;
import com.sr178.game.framework.context.ServiceCacheFactory;
import com.sr178.game.framework.testcore.DaoTest;
import com.sr178.safecheck.admin.bean.JcdcBean;
import com.sr178.safecheck.admin.bean.MixCheckAndEnforceBean;
import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.admin.bo.Notice;
import com.sr178.safecheck.admin.service.AdminService;
import com.sr178.safecheck.app.bean.NoReadBean;
import com.sr178.safecheck.app.dao.CheckRecordDao;
import com.sr178.safecheck.app.service.AppService;

public class AdminServiceTest extends DaoTest {

	public void testMethod(){
//		AdminService adminService = ServiceCacheFactory.getService(AdminService.class);
//		IPage<MixCheckAndEnforceBean> result1 = adminService.getJcdcDetailsPageList("xx", 0, 2);
//		
//		IPage<MixCheckAndEnforceBean> result2 = adminService.getJctjDetailsPageList("俗人工作室", 0, 2);
//		
//		System.out.println("ok");
       
		AppService appService = ServiceCacheFactory.getService(AppService.class);
//		appService.saveCheck("yy_down", "good", "1,3,8", "pp", new Date().getTime());
//		appService.companyList("我");
//		IPage<Notice> ipage = appService.getPageNotice(null, "xx_down", 0, 10);
		appService.getNotice("xx_down", 4);
		NoReadBean noReadBean = appService.noReadNum("xx_down");
		System.out.println("");
	}
	
	
}

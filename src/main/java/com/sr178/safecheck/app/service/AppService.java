package com.sr178.safecheck.app.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.common.jdbc.bean.SqlParamBean;
import com.sr178.game.framework.log.LogSystem;
import com.sr178.safecheck.admin.bo.CheckItems;
import com.sr178.safecheck.admin.bo.CheckRecord;
import com.sr178.safecheck.admin.bo.EnforceRecord;
import com.sr178.safecheck.admin.bo.Notice;
import com.sr178.safecheck.admin.bo.Resource;
import com.sr178.safecheck.admin.bo.User;
import com.sr178.safecheck.app.bean.CheckDetailsBean;
import com.sr178.safecheck.app.bean.FirstCheckItemBean;
import com.sr178.safecheck.app.bean.NoReadBean;
import com.sr178.safecheck.app.bean.ResultCheckItemBean;
import com.sr178.safecheck.app.bean.SecondCheckItemBean;
import com.sr178.safecheck.app.bean.TrainRecordSimple;
import com.sr178.safecheck.app.bean.TrainResordBean;
import com.sr178.safecheck.app.bean.ZeroCheckItemBean;
import com.sr178.safecheck.app.bo.BigCheckItemBO;
import com.sr178.safecheck.app.bo.Version;
import com.sr178.safecheck.app.dao.CheckItemsDao;
import com.sr178.safecheck.app.dao.CheckRecordDao;
import com.sr178.safecheck.app.dao.EnforceRecordDao;
import com.sr178.safecheck.app.dao.NoticeDao;
import com.sr178.safecheck.app.dao.ResourceDao;
import com.sr178.safecheck.app.dao.UserDao;
import com.sr178.safecheck.app.dao.VersionDao;
import com.sr178.safecheck.common.exception.ServiceException;
import com.sr178.safecheck.common.utils.MD5Security;
import com.sr178.safecheck.common.utils.ParamCheck;
import com.sr178.safecheck.common.utils.UrlRequestUtils;

public class AppService {
	public static final String AND = "and";
	public static final String OR = "or";

  	private Cache<String,String> userToken = CacheBuilder.newBuilder().expireAfterAccess(24, TimeUnit.HOURS).maximumSize(2000).build();

	@Autowired
	private UserDao userDao;
	@Autowired
	private CheckItemsDao checkItemsDao;
	@Autowired
	private CheckRecordDao checkRecordDao;
	@Autowired
	private EnforceRecordDao enforceRecordDao;
	@Autowired
	private NoticeDao noticeDao;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private VersionDao versionDao;

	/**
	 * 查看是否登录了
	 * 
	 * @param sessionId
	 * @return
	 */
	public String isLogin(String tokenId) {
		if (tokenId == null || "".equals(tokenId)) {
			return null;
		}
		return userToken.getIfPresent(tokenId);
	}

	/**
	 * 登录
	 * 
	 * @param userName
	 * @param passWord
	 */
	public String login(String userName, String passWord) {
		ParamCheck.checkString(userName, 1, "用户名不能为空");
		ParamCheck.checkString(userName, 2, "密码不能为空");
		User user = userDao.get(new SqlParamBean("user_name", userName), new SqlParamBean(AND, "pass_word", passWord));
		if (user == null) {
			throw new ServiceException(3, "错误的用户名或密码");
		}
		if(user.getStatus()!=0){
			throw new ServiceException(4, "该账户已被禁用，请及时联系管理员！");
		}
		
		String tokenId = UUID.randomUUID().toString();
		userToken.put(tokenId, user.getUserName());
		return tokenId;
	}

	/**
	 * 修改密码
	 * 
	 * @param userName
	 * @param oldPwd
	 * @param newPwd
	 */
	public void editpwd(String userName, String oldPwd, String newPwd) {
		ParamCheck.checkString(userName, 1, "用户名不能为空");
		ParamCheck.checkString(oldPwd, 2, "旧密码不能为空");
		ParamCheck.checkString(oldPwd, 3, "新密码不能为空");
		User user = userDao.get(new SqlParamBean("user_name", userName), new SqlParamBean(AND, "pass_word", oldPwd));
		if (user == null) {
			throw new ServiceException(4, "原始密码错误");
		}
		if (!userDao.updatePwd(userName, newPwd)) {
			throw new ServiceException(5, "更新异常");
		}
	}

	
	/**
	 * 获取所有检查类型
	 * 
	 * @return
	 */
	public List<BigCheckItemBO> checkList(String userName) {
		 User user = userDao.get(new SqlParamBean("user_name", userName));
		return checkItemsDao.getBigCheckItemBOList(user.getDepartMent());
	}
	/**
	 * 查询大类详情
	 * @param id
	 * @return
	 */
	public ZeroCheckItemBean checkDetails(int id){
		CheckItems checkItem = checkItemsDao.get(new SqlParamBean("id", id));
		if(checkItem==null){
			return null;
		}
		ZeroCheckItemBean zero = new ZeroCheckItemBean(checkItem);
		List<CheckItems> firstCheckItems =  checkItemsDao.getList(new SqlParamBean("parent_id", id));
		List<FirstCheckItemBean>  firstList = Lists.newArrayList();
		for(CheckItems first:firstCheckItems){
			FirstCheckItemBean firstBO = new FirstCheckItemBean(first);
			List<CheckItems> secondCheckItems =  checkItemsDao.getList(new SqlParamBean("parent_id", first.getId()));
			List<SecondCheckItemBean> secondList = Lists.newArrayList();
			for(CheckItems second:secondCheckItems){
				SecondCheckItemBean secondBO = new SecondCheckItemBean(second);
				List<CheckItems> resultCheckItems =  checkItemsDao.getList(new SqlParamBean("parent_id", second.getId()));
				List<ResultCheckItemBean> resultList = Lists.newArrayList();
				for(CheckItems result:resultCheckItems){
					resultList.add(new ResultCheckItemBean(result));
				}
				secondBO.setResultList(resultList);
				secondList.add(secondBO);
			}
			firstBO.setDownList(secondList);
			firstList.add(firstBO);
		}
		
		zero.setDownList(firstList);
		return zero;
	}
	
	/**
	 * 查询大类详情(客户端要求的格式)
	 * @param id
	 * @return
	 */
	public List<CheckDetailsBean> checkDetailsNew(int id){
		List<CheckDetailsBean> result = Lists.newArrayList();
		CheckItems checkItem = checkItemsDao.get(new SqlParamBean("id", id));
		if(checkItem==null){
			throw new ServiceException(1, "不存在的大类id");
		}
		List<CheckItems> firstCheckItems =  checkItemsDao.getList("order by id asc",new SqlParamBean("parent_id", id));
		for(CheckItems first:firstCheckItems){
			List<CheckItems> secondCheckItems =  checkItemsDao.getList("order by id asc",new SqlParamBean("parent_id", first.getId()));
			for(CheckItems second:secondCheckItems){
				CheckDetailsBean temp = new CheckDetailsBean();
				temp.setCheckBigId(first.getId());
				temp.setCheckBigTitle(first.getItemTitle());
				temp.setCheckSmallId(second.getId());
				temp.setCheckSmallTitle(second.getItemTitle());
				List<CheckItems> resultCheckItems =  checkItemsDao.getList("order by id asc",new SqlParamBean("parent_id", second.getId()));
				for(CheckItems options:resultCheckItems){
					temp.addOptions(options.getId(), options.getItemTitle());
				}
				result.add(temp);
			}
		}
		return result;
	}
	/**
	 * 模糊查询企业列表
	 * @param searchStr
	 * @return
	 */
	public List<String> companyList(String searchStr) {
		return  checkRecordDao.searchCompay(searchStr, 5);
	}
	/**
	 * 签到及检查结果保存
	 * @param userName
	 * @param cpName
	 * @param checkIds
	 * @param checkTime
	 * @return
	 */
	public int saveCheck(String userName,String cpName,int checkId,String position,Long checkTime,String resPersonName,String resPersonCall,String checkResult){
		ParamCheck.checkString(cpName, 1, "企业名称不能为空");
		ParamCheck.checkObject(checkTime, 2, "检查时间不能为空");
		ParamCheck.checkString(checkResult, 3, "检查结果不能为空");
		ParamCheck.checkString(position, 4, "位置不能为空");
		ParamCheck.checkString(resPersonName, 5, "现场负责人不能为空");
		ParamCheck.checkString(resPersonCall, 6, "现场负责人电话不能为空");
		
		boolean result = checkResultAvalidate(checkResult);
		
		User user = userDao.get(new SqlParamBean("user_name", userName));
		CheckRecord checkRecord = new CheckRecord();
		checkRecord.setCheckUsername(userName);
		checkRecord.setCheckerName(user.getName());
		checkRecord.setCheckItemId(checkId);
		checkRecord.setCheckTime(new Date(checkTime));
		checkRecord.setPosition(position);
		checkRecord.setCpName(cpName);
		checkRecord.setCheckResult(checkResult);
		checkRecord.setResPersonName(resPersonName);
		checkRecord.setResPersonCall(resPersonCall);
		int resourceId = addResource();
		checkRecord.setResourceId(resourceId);
		checkRecord.setCheckServerTime(new Date());
		checkRecord.setPassStatus(result?1:0);
		checkRecordDao.add(checkRecord);
		return resourceId;
	}
	
	/**
	 * 是否全部通过
	 * @param checkResult
	 * @return
	 */
	private boolean checkResultAvalidate(String checkResult){
		String[] strArray =  checkResult.split(":");
		boolean result = true;
		for(String str:strArray){
			String[] items = str.split(",");
			if(items.length!=4){
				LogSystem.info("错误的检查结果，checkResult="+checkResult);
				throw new ServiceException(100, "检查结果格式不对！条目间以英文冒号隔开，小项间用英文逗号隔开，【"+str+"】的拼接不符合规范，逗号隔开后，必须要有4个元素。现在只有["+items.length+"]");
			}
			try {
				int bigid = Integer.valueOf(items[0]);
				int smallId = Integer.valueOf(items[1]);
				CheckItems resultItemBig = checkItemsDao.get(new SqlParamBean("id", bigid));
				CheckItems resultItemSmall = checkItemsDao.get(new SqlParamBean("id", smallId));
				if(resultItemBig==null){
					throw new ServiceException(101, "大项id不存在，bigid="+items[0]); 
				}
				if(resultItemSmall==null){
					throw new ServiceException(101, "小项id不存在，bigid="+items[1]); 
				}
			} catch (Exception e) {
				throw new ServiceException(101, "结果对应的大项或小项id错误,id应该为数字类型，但现在不是，bigid="+items[0]+",or smallid="+items[1]); 
			}
			
			String[] resultIds = items[2].split("\\|");
			for(String idStr:resultIds){
				try {
					int id = Integer.valueOf(idStr);
					CheckItems resultItem = checkItemsDao.get(new SqlParamBean("id", id));
					if(resultItem==null){
						throw new ServiceException(101, "结果id不存在,id="+id);
					}
					if(resultItem.getSuccessOrFail()==0){
						result = false;
					}
				} catch (Exception e) {
					throw new ServiceException(101, "结果id错误,id应该为数字类型，但现在不是，id="+idStr);
				}
			}
		}
		return result;
	}
	
	/**
	 * 执法
	 * @param userName
	 * @param inCpName
	 * @param inTime
	 * @return
	 */
	public int intendance(String userName,String inCpName,Long inTime){
		ParamCheck.checkString(inCpName, 1, "企业名称不能为空");
		ParamCheck.checkObject(inTime, 2, "检查时间不能为空");
		User user = userDao.get(new SqlParamBean("user_name", userName));
		EnforceRecord enforceRecord = new EnforceRecord();
		enforceRecord.setCpName(inCpName);
		enforceRecord.setEnforceName(user.getName());
		enforceRecord.setEnforceServerTime(new Date());
		enforceRecord.setEnforceTime(new Date(inTime));
		enforceRecord.setEnforceUsername(userName);
		int resourceId = addResource();
		enforceRecord.setResourceId(resourceId);
		enforceRecordDao.add(enforceRecord);
		return resourceId;
	}
	/**
	 * 查询公告列表
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public IPage<Notice> getPageNotice(String searchStr, String userName, int pageIndex, int pageSize) {
		User user = userDao.get(new SqlParamBean("user_name", userName));
		IPage<Notice> ipage = noticeDao.getNoticePage(searchStr, userName, user.getDepartMent(), pageIndex, pageSize);
		if (ipage != null && ipage.getData().size() > 0) {
			Collection<Notice> c = ipage.getData();
			for (Notice notice : c) {
				notice.setStatus(notice.getStatus() - 1);
			}
		}
		return ipage;
	}
	/**
	 * 查询新闻内容
	 * @param id
	 * @return
	 */
	public Notice getNotice(String userName,int id){
		Notice notice =  noticeDao.get(new SqlParamBean("id",id));
		if(notice!=null){
			noticeDao.setIsRead(userName, id);
		}
		return notice;
	}
	/**
	 * 查询未读消息数量及最后一条未读消息标题及id
	 * @param userName
	 * @return
	 */
	public NoReadBean noReadNum(String userName){
		User user = userDao.get(new SqlParamBean("user_name", userName));
		List<Notice> list = noticeDao.getNoReadList(userName, user.getDepartMent());
		NoReadBean result = new NoReadBean();
		if(list!=null&&list.size()>0){
			result.setNum(list.size());
			result.setNoticeId(list.get(0).getId());
			result.setTitle(list.get(0).getNoticeTitle());
		}
		return result;
	}
	
	/**
	 * 保存图片
	 * @param files
	 * @param fileNames
	 * @param taskId
	 * @param type
	 */
	public void saveFiles(List<File> files, List<String> names, int taskId, int type, String descPath) {
		Resource resource = resourceDao.get(new SqlParamBean("resource_id", taskId));
		if (resource == null) {
			throw new ServiceException(1, "任务不存在！");
		}
		List<String> fileNames = Lists.newArrayList();
		for (int i = 0; i < files.size(); i++) {
			File file = files.get(i);
			String oldFileName = names.get(i);
			String prefix = oldFileName.split("\\.")[1];
			String fileName = "";
			if (type == 2) {
				fileName = checkFileName(oldFileName) + "_" + MD5Security.md5_16(UUID.randomUUID().toString()) + "."
						+ prefix;
			} else {
				fileName = MD5Security.md5_16(UUID.randomUUID().toString()) + "." + prefix;
			}
			try {
				FileUtils.copyFile(file, new File(descPath + fileName));
				fileNames.add(fileName);
			} catch (IOException e) {
				LogSystem.error(e, "上传图片失败");
				throw new ServiceException(3, "上传图片失败！请重新上传！");
			}
		}
		String source = StringUtils.join(fileNames.iterator(), ",");
		if (type == 1) {
			source = joinStr(resource.getResource1Names(), source);
		} else if (type == 2) {
			source = joinStr(resource.getResource2Names(), source);
		} else if (type == 3) {
			source = joinStr(resource.getResource3Names(), source);
		} else if (type == 4) {
			source = joinStr(resource.getResource4Names(), source);
		}
		resourceDao.updateResource(taskId, source, type);
	}
	/**
	 * 检查文件名的合法性
	 * @param oldFileName
	 * @return
	 */
	private String checkFileName(String oldFileName){
		 String[] array = oldFileName.split("_");
		  if(array.length<2||Strings.isNullOrEmpty(array[0])){
			  LogSystem.info("不合法的文件名"+oldFileName);
			  throw new ServiceException(2, "上传图片失败！请重新上传！图片格式不正确，type=2的时候要按照规则执行，bigId#smallid_本地文件名.jpg,当前文件名为:"+oldFileName);
		  }
		  String bigAndSmallId = array[0];
		  String[] bas = bigAndSmallId.split("#");
		  if(bas.length!=2){
			  LogSystem.info("不合法的文件名"+oldFileName);
			  throw new ServiceException(2, "上传图片失败！请重新上传！图片格式不正确，type=2的时候要按照规则执行，bigId#smallid_本地文件名.jpg,当前文件名为:"+oldFileName);
		  }
		  try {
			  Integer.valueOf(bas[0]);
			  Integer.valueOf(bas[1]);
		} catch (Exception e) {
			 LogSystem.info("不合法的文件名"+oldFileName);
			 throw new ServiceException(2, "上传图片失败！请重新上传！图片格式不正确，type=2的时候要按照规则执行，bigId#smallid_本地文件名.jpg,当前文件名为:"+oldFileName);
		}
		return bigAndSmallId;
	}
	
	private String joinStr(String befor,String add){
		if(Strings.isNullOrEmpty(befor)){
			return add;
		}else{
			return befor+","+add;
		}
	}
	
	/**
	 * 添加资源
	 * 
	 * @return
	 */
	public int addResource() {
		Resource resource = new Resource();
		resource.setCreatedTime(new Date());
		resource.setUpdatedTime(new Date());
		return resourceDao.addBackKey(resource);
	}
	
	/**
	 * 从网页获取训练记录
	 * @param idCard
	 * @param certNum
	 */
	private static final String URL = "http://118.122.114.164:8069/QuerySys/Query.aspx";
	private static final String IMAGE_URL_PRE = "http://118.122.114.164:8069/Exam/ShowSafeWorkerPhoto.aspx";
	public static TrainResordBean trainRecod(String certNum){
		ParamCheck.checkString(certNum, 1, "证书标识号不能为空");
//		String __VIEWSTATE = getTag(content,"<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"","\"");
//		String __EVENTVALIDATION = getTag(content,"<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"","\"");
		HashMap<String,String> paraMap = Maps.newHashMap();
//		paraMap.put("txtName", "");
//		paraMap.put("id", certNum);
//		paraMap.put("__VIEWSTATE",__VIEWSTATE);
//		paraMap.put("__VIEWSTATEENCRYPTED","");
//		paraMap.put("__EVENTVALIDATION",__EVENTVALIDATION);
//		paraMap.put("btnQuery.x", "63");
//		paraMap.put("btnQuery.y", "16");
//		String result = UrlRequestUtils.execute(URL, paraMap, UrlRequestUtils.Mode.POST);
		paraMap.put("id", certNum);
		String result = UrlRequestUtils.execute(URL, paraMap, UrlRequestUtils.Mode.GET);
		if(result==null){
			throw new ServiceException(1, "网络请求失败！");
		}
		String erroDesc = getTag(result, "<span id=\"lblInfo\"><font color=\"Red\">", "</font></span>");
		if(erroDesc==null){
			erroDesc = getTag(result, "<span id=\"lblInfo\" style=\"color:Red;\">", "</span>");
		}
		if(erroDesc==null){
			LogSystem.warn("收到的网页结果为："+result);
			throw new ServiceException(1, "网络请求失败！");
		}
		if(!erroDesc.equals("身份证号不得少于15位，证书标识号不得少于15位。")){
			throw new ServiceException(1, erroDesc);
		}
		TrainResordBean bean = new TrainResordBean();
		bean.setCanWorkType(getTag(result, "<span id=\"lblOperationItem\">", "</span>"));
		bean.setCertCp(getTag(result, "<span id=\"lblPubUnit\">", "</span>"));
		bean.setCertNum(getTag(result, "<span id=\"lblCertificate\"><font color=\"Red\">", "</font></span>"));
		bean.setCertTime(getTag(result, "<span id=\"lblPublic\">", "</span>"));
		bean.setEffectTimeEnd(getTag(result, "<span id=\"lblTo\">", "</span>"));
		bean.setEffectTimeStart(getTag(result, "<span id=\"lblFrom\">", "</span>"));
		bean.setIdCard(getTag(result, "<span id=\"lblIDCard\">", "</span>"));
		bean.setName(getTag(result, "<span id=\"lblName\">", "</span>"));
		bean.setSex(getTag(result, "<span id=\"lblSex\">", "</span>"));
		bean.setTheoryScore(getTag(result, "<span id=\"lblScore\">", "</span>"));
		bean.setTrainCp(getTag(result, "<span id=\"lblTrinUnit\">", "</span>"));
		bean.setWorkCp(getTag(result, "<span id=\"lblWorkUnit\">", "</span>"));
		bean.setWorkScore(getTag(result, "<span id=\"lblOptScore\">", "</span>"));
		bean.setWorkType(getTag(result, "<span id=\"lblJobType\">", "</span>"));
		bean.setImage(IMAGE_URL_PRE+getTag(result, "../Exam/ShowSafeWorkerPhoto.aspx", "\""));
		if(Strings.isNullOrEmpty(bean.getCertNum())){//客户端需要特殊处理的错误
			throw new ServiceException(3000, "无相关证书编号["+certNum+"]对应信息");
		}
		return bean;
		
	}
	
	public static List<TrainRecordSimple> trainSimpleByIdCard(String idCard,String certNum){
		if(Strings.isNullOrEmpty(idCard)&&Strings.isNullOrEmpty(certNum)){
			throw new ServiceException(1, "身份证号码和证件号码不能同时为空");
		}
		String content = UrlRequestUtils.execute(URL, new HashMap<String,String>(), UrlRequestUtils.Mode.POST);
		if(content==null){
			throw new ServiceException(1, "网络请求失败！");
		}
		String __VIEWSTATE = getTag(content,"<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"","\"");
		String __EVENTVALIDATION = getTag(content,"<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"","\"");
		HashMap<String,String> paraMap = Maps.newHashMap();
		paraMap.put("txtName", idCard);
		paraMap.put("txtCode", certNum);
		paraMap.put("__VIEWSTATE",__VIEWSTATE);
		paraMap.put("__VIEWSTATEENCRYPTED","");
		paraMap.put("__EVENTVALIDATION",__EVENTVALIDATION);
		paraMap.put("btnQuery.x", "63");
		paraMap.put("btnQuery.y", "16");
		
		String result = UrlRequestUtils.execute(URL, paraMap, UrlRequestUtils.Mode.POST);
		if(result==null){
			throw new ServiceException(1, "网络请求失败！");
		}
		String erroDesc = getTag(result, "<span id=\"lblInfo\"><font color=\"Red\">", "</font></span>");
		if(erroDesc==null){
			erroDesc = getTag(result, "<span id=\"lblInfo\" style=\"color:Red;\">", "</span>");
		}
		
		if(erroDesc==null){
			LogSystem.warn("收到的网页结果为："+result);
			throw new ServiceException(1, "网络请求失败！");
		}
		if(!erroDesc.equals("身份证号不得少于15位，证书标识号不得少于15位。")){
			throw new ServiceException(1, erroDesc);
		}
		List<TrainRecordSimple> resultT = Lists.newArrayList();
		String trListStr = getTag(result,"<table cellspacing=\"2\" cellpadding=\"4\" border=\"0\" id=\"gvClasses\" width=\"100%\">","</table>");
		Document doc = Jsoup.parse("<html><body><table>"+trListStr+"</table></body></html>");
        Elements trs = doc.select("tr");
        for(int i = 0;i<trs.size();i++){
            Elements tds = trs.get(i).select("td");
            if(tds.size()>=5){
            	TrainRecordSimple simple = new TrainRecordSimple();
            	simple.setCertNum(tds.get(0).text());
            	simple.setName(tds.get(1).text());
            	simple.setCanWorkType(tds.get(4).text());
            	resultT.add(simple);
            }
        }
		return resultT;
	}
	/**
	 * 版本检查
	 **/
	public Version checkVersion(int currentVersionNum){
		Version version = versionDao.getLastVersion();
		if(version==null){
			return null;
		}
		if(version.getVersionNum()>currentVersionNum){
			return version;
		}
		return null;
	}
	
//	public static String trainRecodTest(String idCard,String certNum){
//		HttpUriRequest request = new HttpPost(URL);
//		request.addHeader("ContentType", "application/x-www-form-urlencoded");
//		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");
//		
//		HttpResponse response = UrlRequestUtils.executeWithRequest(request, new HashMap<String,String>());
//		String content="";
//		try {
//			content = EntityUtils.toString(response.getEntity(), "utf-8");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		request.abort();
//		
//		Header[] heads =response.getHeaders("Set-Cookie");
//		request = new HttpPost(URL);
////		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");
////		request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
////		request.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
////		request.addHeader("Cookie",heads[0].getValue().split(";")[0]);
////		request.addHeader("Accept-Encoding", "gzip, deflate");
////		request.addHeader("Referer", "http://118.122.114.164:8069/QuerySys/Query.aspx");
////		request.addHeader("Connection", "keep-alive");
//		
//		String __VIEWSTATE = getTag(content,"<input type=\"hidden\" name=\"__VIEWSTATE\" id=\"__VIEWSTATE\" value=\"","\"");
//		String __EVENTVALIDATION = getTag(content,"<input type=\"hidden\" name=\"__EVENTVALIDATION\" id=\"__EVENTVALIDATION\" value=\"","\"");
//		
//		HashMap<String,String> paraMap = Maps.newHashMap();
//		paraMap.put("txtName", idCard);
//		paraMap.put("txtCode", certNum);
//		paraMap.put("__VIEWSTATE",__VIEWSTATE);
//		paraMap.put("__VIEWSTATEENCRYPTED","");
//		paraMap.put("__EVENTVALIDATION",__EVENTVALIDATION);
//		paraMap.put("btnQuery.x", "63");
//		paraMap.put("btnQuery.y", "16");
//		
//		
//		HttpResponse response2 = UrlRequestUtils.executeWithRequest(request, paraMap);
//		try {
//			return EntityUtils.toString(response2.getEntity(), "utf-8");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	

	private static String getTag(String content,String firstTag,String endTag){
		String[] array = content.split(firstTag);
		if(array.length==1){
			return null;
		}
		String[] sencondArray = array[1].split(endTag);
		if(sencondArray.length==1){
			return null;
		}
		return sencondArray[0];
	}

	
	public static void main(String[] args) {
//		TrainResordBean bean = trainRecod("T510502198803067812");
//		System.out.println(bean.toString());
//		List<TrainRecordSimple> list = trainSimpleByIdCard("510502198803067812","T510502198803067812");
//
//		for(TrainRecordSimple trainRecordSimple:list){
//			System.out.println(trainRecordSimple.toString());
//		}
		String sst = "1#2#3";
		String[] str = sst.split("#");
		System.out.println(str.length);
	}

}

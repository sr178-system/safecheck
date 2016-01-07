package com.sr178.safecheck.app.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.google.common.collect.Lists;

public class ZeroCheckItemBean extends CheckItemBean {
	
	private List<FirstCheckItemBean> downList;
	
	public List<FirstCheckItemBean> getDownList() {
		return downList;
	}
	public void setDownList(List<FirstCheckItemBean> downList) {
		this.downList = downList;
	}
	public ZeroCheckItemBean() {
		super();
	}
	public ZeroCheckItemBean(int id, String itemTitle) {
		super(id, itemTitle);
	}
	
	public static void main(String[] args) throws JSONException {
		ResultCheckItemBean bean1 = new ResultCheckItemBean(1,"有");
		ResultCheckItemBean bean2 = new ResultCheckItemBean(2,"无");
		ResultCheckItemBean bean3 = new ResultCheckItemBean(3,"不涉及");
		List<ResultCheckItemBean> resultList1 = Lists.newArrayList(bean1, bean2, bean3);
		ResultCheckItemBean bean4 = new ResultCheckItemBean(4,"有");
		ResultCheckItemBean bean5 = new ResultCheckItemBean(5,"无");
		ResultCheckItemBean bean6 = new ResultCheckItemBean(6,"不涉及");
		List<ResultCheckItemBean> resultList2 = Lists.newArrayList(bean4, bean5, bean6);	
		SecondCheckItemBean sbean1 = new SecondCheckItemBean(7, "营业执照7");
		sbean1.setResultList(resultList1);
		SecondCheckItemBean sbean2 = new SecondCheckItemBean(8, "卫生许可8");
		sbean2.setResultList(resultList2);
		List<SecondCheckItemBean> secondList1 = Lists.newArrayList(sbean1,sbean2);
		SecondCheckItemBean sbean3 = new SecondCheckItemBean(9, "营业执照9");
		sbean3.setResultList(resultList1);
		SecondCheckItemBean sbean4 = new SecondCheckItemBean(10, "卫生许可10");
		sbean4.setResultList(resultList2);
		List<SecondCheckItemBean> secondList2 = Lists.newArrayList(sbean3,sbean4);
		
		
		FirstCheckItemBean firstCheckItemBean1 = new FirstCheckItemBean(11, "证照");
		firstCheckItemBean1.setDownList(secondList1);
		
		FirstCheckItemBean firstCheckItemBean2 = new FirstCheckItemBean(12, "制度建设");
		firstCheckItemBean2.setDownList(secondList2);
		
		List<FirstCheckItemBean>  firstList = Lists.newArrayList(firstCheckItemBean1,firstCheckItemBean2);
		
		ZeroCheckItemBean zeroCheckItemBean = new ZeroCheckItemBean(13, "生产安全检查");
		zeroCheckItemBean.setDownList(firstList);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rs", zeroCheckItemBean);
		System.out.println(JSONUtil.serialize(map));

		
	}
}

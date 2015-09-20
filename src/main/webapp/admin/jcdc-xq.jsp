<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 头部 -->
<%@ include file="/admin/head.jsp"%>
<div class="easyui-layout" data-options="fit:true">

<!-- 导航 -->
<jsp:include page="/admin/nav.jsp" flush="true"><jsp:param name="current" value="2"/></jsp:include>
    <div id="content" region="center">
    		<div class="crumb">
    			<p>您当前的位置：<a href="/admin/adminindex">首页</a>><a href="/admin/jcdc">督查检查</a>><span>详情</span></p>
    		</div>
    		<div class="detilym" oncontextmenu='return false' ondragstart='return false'>
    			
    			<div class="dtitle">
    				执法人员：${enforceUser.name}
    			</div>
    			<div class="dtecnt">
    				<p><b>登录名：</b>${efUserName}</p>
    				<p><b>性别：</b><c:if test="${efUserName.sex==1}">男</c:if><c:if test="${efUserName.sex==2}">女</c:if></p>
    				<p><b>出生日期：</b><fmt:formatDate value="${efUserName.birthday}" type="both" pattern="yyyy.MM.dd"/></p>
    				<p><b>电话号码：</b>${efUserName.call}</p>
    			</div>
    			<div class="dlink">
    				<ul>
    				    <s:iterator var="data" value="dataList">
    					<li>
    						<div>
    							<p><b>检查日期：</b><fmt:formatDate value="${data.birthday}" type="both" pattern="yyyy.MM.dd"/></p>
			    				<p><b>被检查企业：</b>泸州市XX厂</p>
			    				<p><b>签到地点：</b><a href="#"><点击查看地图></a></p>
			    				<p><b>签到照片：</b></p>
								<p>
                                    <a href="images/demopage/image-1.jpg" data-lightbox="example-set" title="签到照片">
                                        <img src="images/demopage/image-1.jpg" />
                                    </a>
                                </p>
								<p><b>检查项：</b>检查项目1、检查项目2、检查项目3、检查项目4</p>
								<p><b>取证照片：</b></p>
								<p><img src="#"><img src="#"><img src="#"></p>
								<p><b>企业负责人签名照片：</b></p>
								<p><img src="#"></p>
    						</div>
    					</li>
    					</s:iterator>
    				</ul>
    				<p><a name="pos" class="mores" href="#">点击加载更多▼</a></p>
    			</div>
 
    		</div>
    </div>
 </div>
<script type="text/javascript">
	$(".mores").click(function(){
		//$.get('xxx',function(e){
			alert("加载功能");
		//})
	})
</script>
</body>
</html>
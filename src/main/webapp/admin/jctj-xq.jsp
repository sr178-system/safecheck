<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 头部 -->
<%@ include file="/admin/head.jsp"%>
<div class="easyui-layout" data-options="fit:true">

<!-- 导航 -->
<jsp:include page="/admin/nav.jsp" flush="true"><jsp:param name="current" value="1"/></jsp:include>
    <div id="content" region="center">
    		<div class="crumb">
    			<p>您当前的位置：<a href="/admin/adminindex">首页</a>><a href="/admin/adminindex">检查统计</a>><span>详情</span></p>
    		</div>
    		<div class="detilym" oncontextmenu='return false' ondragstart='return false'>
    			<div class="dtitle">
    				企业名称：${cpName}
    			</div>
    			<div class="dlink">
    				<ul>
    				    <s:iterator var="data" value="list">
    					<li>
    						<div>
    							<p><b>检查日期：</b><fmt:formatDate value="${data.checkTime}" type="both" pattern="yyyy.MM.dd"/></p>
                                <p><b>检查项目：</b>
                                    <c:set value="1" var="i" />
	                                <c:forEach items="${data.checkItemNames}" var="it"><c:if test="${i==1}">${it}</c:if>
	                                <c:if test="${i>1}">,${it}</c:if>
								    <c:set value="${i+1}" var="i" />
								    </c:forEach>
							    </p>
								<p><b>取证照片：</b></p>
								<p>
									<c:if test="${not empty data.resource2Names}">
										<c:set
											value="${fn:split(data.resource2Names, ',')}"
											var="pics" />
										<c:forEach items="${pics}" var="s">
											<a href="/uploads/${s}" data-lightbox="example-set"
												title="执法图片"><img src="/uploads/${s}" /></a>
										</c:forEach>
									</c:if>
								</p>
								<p><b>企业负责人签名照片：</b></p>
								<p>
								<c:if test="${not empty data.resource3Names}">
										<c:set
											value="${fn:split(data.resource3Names, ',')}"
											var="pics" />
										<c:forEach items="${pics}" var="s">
											<a href="/uploads/${s}" data-lightbox="example-set"
												title="签字图片"><img src="/uploads/${s}" /></a>
										</c:forEach>
									</c:if>
								</p>
                                <p><b>检查人员：</b>${data.checkerName}</p>
    						</div>
    					</li>
    					</s:iterator>
    				</ul>
    				<p>
    				<c:if test="${total<=pageSize}"><a class="mores" name="pos" href="#pos" onclick="alert('已全部加载完！')">点击加载更多▼</a></c:if>
    				<c:if test="${total>pageSize}"><a class="mores" name="pos" href="checkRecordList?cpName=${cpName}&indexPage=0&pageSize=${pageSize+pageSize}#pos">点击加载更多▼</a></c:if>
    				</p>
    			</div>
 
    		</div>
    </div>
 </div>
<script type="text/javascript">
	$(".mores").click(function(){
		//$.get('xxx',function(e){
			alert("加载功能");
		//})
		    var
			$(".dlink ul").html($(".dlink ul").html()+"<li>sb????????????</li>");
	})
	
	
	
</script>
</body>
</html>
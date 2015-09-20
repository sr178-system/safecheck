<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 头部 -->
<%@ include file="/admin/head.jsp"%>
<c:if test="${code==7}"><script type="text/javascript">alert("该用户名[${adminUserName}]已被占用！请重新注册");history.go(-1);</script></c:if>
<c:if test="${code==2001}"><script type="text/javascript">alert("修改用户成功！");location.href='/admin/adminList';</script></c:if>
<link rel="stylesheet" type="text/css" href="/scripts/jquery.datetimepicker.css"/>
<script src="/scripts/jquery.datetimepicker.js"></script>
<div class="easyui-layout" data-options="fit:true">

<!-- 导航 -->
<jsp:include page="/admin/nav.jsp" flush="true"><jsp:param name="current" value="3"/></jsp:include>
    <div id="content" region="center">
    		<div class="crumb">
    			<p>您当前的位置：<a href="/admin/adminindex">首页</a>><span><a href="/admin/adminList">管理员管理</a></span>>编辑</p>
    		</div>
    		<div>
    			<form class="form form1" method="POST" action="editAdmin?st=1" name="FormAddAdmin" id="FormAddAdmin">
    				<p><label>登录名：</label><input type="text" name="adminUserName" id="adminUserName" value="${adminUser.userName}" readOnly></p>
					<p><label>密　码：</label><input type="password"  name="passWord" id="passWord">如不修改密码,请留空</p>
					<p><label>确认密码：</label><input type="password"  name="passWord2" id="passWord2">如不修改密码,请留空</p>
					<p><label>姓　名：</label><input type="text" name="name" id="name" value="${adminUser.name}"></p>
					<p class="sexs"><label>性　别：</label><input type="radio" <c:if test="${adminUser.sex==1}">checked="checked"</c:if> name="sex" id="sex" value="1">男　　<input type="radio" <c:if test="${adminUser.sex==2}">checked="checked"</c:if> name="sex" id="sex" value="2">女</p>
					<p><label>出生日期：</label><input type="text" name="birthday" id="birthday" value="<fmt:formatDate value="${adminUser.birthday}" type="both" pattern="yyyy/MM/dd"/>"></p>
					<p><label>电话号码：</label><input type="text" name="call" id="call" value="${adminUser.call}"></p>
					<p><label>备　注：</label></p>
					<p><textarea name="remark" id="remark">${adminUser.remark}</textarea></p>
					<p class="fbnt"><a href="#" onClick="checkParam()">保存</a> <a href="/admin/adminList">取消</a></p>
    			</form>
    		</div>
    </div>
 </div>
</body>
</html>
 <script type="text/javascript">

 function checkParam(){
   if($('#adminUserName').val()==''){
	   alert('用户名不能为空！');
	   $('#adminUserName').focus().select();
	   return;
	}
    if($('#name').val()==''){
	   alert('名字不能为空！');
	   $('#name').focus().select();
	   return;
	}
    if($('#birthday').val()==''){
 	   alert('出生日期不能为空！');
	   $('#birthday').focus().select();
	   return;
 	}
    if($('#call').val()==''){
  	   alert('电话不能为空！');
	   $('#call').focus().select();
	   return;
  	}
    if($('#remark').val()==''){
   	   alert('备注不能为空！');
	   $('#remark').focus().select();
	   return;
   	}
    $('#FormAddAdmin').submit();
 }
 
 $('#birthday').datetimepicker({
		yearOffset:0,
		lang:'ch',
		timepicker:false,
		format:'Y/m/d',
		formatDate:'Y/m/d',
//		minDate:'1970/01/01', // yesterday is minimum date
//		maxDate:'+1970/01/02' // and tommorow is maximum date calendar
	});
</script>
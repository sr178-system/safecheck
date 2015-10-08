<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/admin/head.jsp"%>

<c:if test="${code==7}"><script type="text/javascript">alert("该用户名[${adminUserName}]已被占用！请重新添加",function(){history.go(-1);});</script></c:if>
<c:if test="${code==2000}"><script type="text/javascript">alert("添加用户成功！",function(){location.href='/admin/userList';});</script></c:if>
<link rel="stylesheet" type="text/css" href="/scripts/jquery.datetimepicker.css"/>
<script src="/scripts/jquery.datetimepicker.js"></script>
<div class="easyui-layout" data-options="fit:true">

<jsp:include page="/admin/nav.jsp" flush="true"><jsp:param name="current" value="4"/></jsp:include>
    <div id="content" region="center">
    		<div class="crumb">
    			<p>您当前的位置：<a href="/admin/adminindex">首页</a>><span><a href="/admin/userList">执法人员管理</a></span>>添加</p>
    		</div>
    		<div>
    			<form class="form form1" method="POST" action="addUser?st=1" name="FormAddAdmin" id="FormAddAdmin">
    				<p><label>登录名：</label><input type="text" name="adminUserName" id="adminUserName"></p>
					<p><label>密　码：</label><input type="password"  name="passWord" id="passWord"></p>
					<p><label>确认密码：</label><input type="password"  name="passWord2" id="passWord2"></p>
					<p><label>姓　名：</label><input type="text" name="name" id="name"></p>
					<p class="sexs"><label>性　别：</label><input type="radio" checked="checked" name="sex" id="sex" value="1">男　　<input type="radio" name="sex" value="2">女</p>
					<p><label>出生日期：</label><input type="text" name="birthday" id="birthday"></p>
					<p><label>电话号码：</label><input type="text" name="call" id="call"></p>
					<p><label>备　注：</label></p>
					<p><textarea name="remark" id="remark"></textarea></p>
					<p><label>管理员：</label><select name="upUser" id="upUser">
					        <option value="">=请选择=</option>
					        <c:set var="cun" value="${userName}"></c:set>
					       <s:iterator var="data" value="admins">
					          <option value="${data.userName}" <c:if test="${cun==data.userName}">selected</c:if>>${data.name}</option>
					       </s:iterator>
					</select></p>
					<p class="fbnt"><a href="#" onClick="checkParam()">保存</a> <a href="/admin/adminList">取消</a></p>
    			</form>
    		</div>
    </div>
 </div>
</body>
</html>
 <script type="text/javascript">

 function checkParam(){
		if ($('#adminUserName').val() == '') {
				alert('用户名不能为空！');
				$('#adminUserName').focus().select();
				return;
			}
			if (checkParamLenght($('#adminUserName').val(), 32)) {
				alert('用户名不能超过32个字符！');
				$('#adminUserName').focus().select();
				return;
			}
			if ($('#passWord').val() == '') {
				alert('密码不能为空！');
				$('#passWord').focus().select();
				return;
			}
			if (checkParamLenght($('#passWord').val(), 32)) {
				alert('密码不能超过32个字符！');
				$('#passWord').focus().select();
				return;
			}
			if ($('#passWord').val() != $('#passWord2').val()) {
				alert('密码和确认密码不一致！');
				$('#passWord').focus().select();
				return;
			}
			if ($('#name').val() == '') {
				alert('名字不能为空！');
				$('#name').focus().select();
				return;
			}
			if (checkParamLenght($('#name').val(), 16)) {
				alert('名字不能超过16个字符！');
				$('#name').focus().select();
				return;
			}
			if ($('#call').val() != ''
					&& checkParamLenght($('#call').val(), 32)) {
				alert('电话号码不能超过32个字符！');
				$('#call').focus().select();
				return;
			}
			if ($('#remark').val() != ''
					&& checkParamLenght($('#remark').val(), 500)) {
				alert('备注不能超过500个字符！');
				$('#remark').focus().select();
				return;
			}
			if ($('#upUser').val() == '') {
				alert('请选择该用户的上级管理员！');
				$('#upUser').focus().select();
				return;
			}
			$('#FormAddAdmin').submit();
		}

		$('#birthday').datetimepicker({
			yearOffset : 0,
			lang : 'ch',
			timepicker : false,
			format : 'Y/m/d',
			formatDate : 'Y/m/d',
		//		minDate:'1970/01/01', // yesterday is minimum date
		//		maxDate:'+1970/01/02' // and tommorow is maximum date calendar
		});
	</script>
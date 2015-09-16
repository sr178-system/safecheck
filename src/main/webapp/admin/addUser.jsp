<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 头部 -->
<%@ include file="/admin/head.jsp"%>
<div class="easyui-layout" data-options="fit:true">

<!-- 导航 -->
<jsp:include page="/admin/nav.jsp" flush="true"><jsp:param name="current" value="4"/></jsp:include>
    <div id="content" region="center">
    		<div class="crumb">
    			<p>您当前的位置：<a href="/admin/adminindex">首页</a>><span><a href="/admin/userList">执法人员管理</a></span>>添加</p>
    		</div>
    		<div class="form form1" method="POST" action="addUser?st=1" name="FORM">
    			<form>
    				<p><label>登录名：</label><input type="text"  name="adminUserName"></p>
					<p><label>密　码：</label><input type="password"  name="passWord"></p>
					<p><label>确认密码：</label><input type="password"  name="passWord2"></p>
					<p><label>姓　名：</label><input type="text" name="name"></p>
					<p class="sexs"><label>性　别：</label><input type="radio" checked="checked" name="sex" value="1">男　　<input type="radio" name="sex" value="2">女</p>
					<p><label>出生日期：</label><input type="text" name="birthday"></p>
					<p><label>电话号码：</label><input type="text" name="call"></p>
					<p><label>备　注：</label></p>
					<p><textarea></textarea></p>
					<p><label>管理员：</label><select><option>管理员1</option></select></p>
					<p class="fbnt"><a href="#">保存</a> <a href="#">取消</a></p>
    			</form>
    		</div>
    </div>
 </div>
</body>
</html>
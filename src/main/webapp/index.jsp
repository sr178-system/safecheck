<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>泸州市阳江区安全生产监督管理局 - 移动执法系统管理后台</title>
	<link rel="stylesheet" type="text/css" href="/css/easyui.css">
	<script type="text/javascript" src="/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="/scripts/jquery.easyui.min.js"></script>
	
    <script language="javascript" src="/js/ajax.js"></script>
		<SCRIPT type=text/javascript>
		    function logins(){
		    	if (Form.userName.value=="") {      alert("请写入您的用户名!");      return;    } 
		    	if (Form.passWord.value=="") {      alert("请写入登录密码!");      return;    }
			    var ajaxobj = new Ajax();
			    ajaxobj.url="/admin/adminlogin?userName="+Form.userName.value+"&passWord="+Form.passWord.value;
			    ajaxobj.callback=function(){
				    var responseMsg = eval('(' + ajaxobj.gettext() + ')');
				    if(responseMsg.code!=0){
				    	if(responseMsg.code==1){
				    		alert("用户名或密码错误");
				    	}else if(responseMsg.code==2){
				    		alert("该用户已被禁用，请及时联系管理员！");
				    	}
				    }else{
				    	location.href='/admin/adminindex';
				    }
			    }
			    ajaxobj.send();
		    }
    </SCRIPT>	
</head>
<body>

<div class="easyui-layout login" data-options="fit:true">
	<div class="easyui-window loginbox" title="">
		<form class="cb" name="Form" onSubmit="return false" method="POST">
			<p class="lxq">备案号:蜀ICP备14018479号</p> 
			<div>
				<p><label>用户名：</label><input type="text" name="userName"></p>
				<p><label>密　码：</label><input type="password" name="passWord"></p>
				<p><label></label><a href="javascript:logins()"><img src="images/login.jpg" /></a></p>
			</div>
		</form>
	</div>
</div>

 <script type="text/javascript">

 </script>
</body>
</html>
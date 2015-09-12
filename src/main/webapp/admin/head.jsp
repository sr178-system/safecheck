<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>泸州市阳江区安全生产监督管理局 - 移动执法系统管理后台</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" content="ie=edge"/>
	<link rel="stylesheet" type="text/css" href="/css/easyui.css">
	<script type="text/javascript" src="/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="/scripts/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/scripts/lightbox.js"></script>
	<script type="text/javascript">
		$().ready(function(){
			$(".pawex").click(function(){
				$(".form2").toggle();
			});
			$(".form2 .clesed").click(function(){ $(".form2").hide(); });
		});
	</script>
	<!--[if lte IE 6]> 
		<script type="text/javascript" src="../scripts/png.js"></script>
		<script type="text/javascript"> 
	DD_belatedPNG.fix('.crumb p, .table1 .t1top span, .form2 .jt, .lefter ul li a, .loginbox'); 
		</script>
	<![endif]--> 
</head> 
<body>

<div class="header cb">
	<div class="l"><a href="#"><img src="/images/logo.png" /></a></div>
	<div class="r headtent"><strong>当前用户：<b>${userName}</b></strong><a class="pawex" href="#">修改密码</a><span>|</span><a href="#">退出</a></div>
	<form class="form form2">
		<span class="jt"></span>
		<p><label>旧密码：</label><input type="password" value=""></p>
		<p><label>新密码：</label><input type="password" value=""></p>
		<p><label>确认密码：</label><input type="password" value=""></p>
		<p class="fbnt"><a href="#" class="st">保存</a> <a href="#" class="clesed">取消</a></p>
	</form>
</div>
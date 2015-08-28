<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.text.*" %>
<%@ include file="taglibs.jsp" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<title>首页 - 80视觉</title>
<style type="text/css">
<!--
*{font-family:"Microsoft Yahei","冬青黑体简体中文 w3","宋体";text-decoration:none;border:0;margin:0;padding:0;}
body {font-size: 14px;background:url(images/login/bg_body.png) repeat;}
.login{position:absolute;  top:50%;  height:230px;margin:0 auto; margin-top:-115px; width:900px; left:50%; margin-left:-450px;}
  .login .logo{width:450px; height:60px; margin:0 auto 100px auto;background:url(images/login/logo.png) no-repeat;}
  	.login .logo #errorMessage{color: red; padding: 100px 0 0 13px; font-size: 15px; letter-spacing: 1px;}
  .login .box{width:570px; height:34px; padding:0 165px 35px 165px;background:url(images/login/bg_login.png) no-repeat 0 34px;}
    .login .box .uid,.login .box .pwd{ float:left;width:190px; height:34px;margin:0 20px 0 0;}
     .login .box .uid input,.login .box .pwd input{width:150px; height:26px; line-height:26px;padding:4px 2px 4px 38px;font-size:14px;color:#58595B;}
     .login .box .uid input{background:url(images/login/bg_uid.png) no-repeat;}
     .login .box .pwd input{background:url(images/login/bg_pwd.png) no-repeat;}
    .login .box .bt{float:left;width:150px; height:34px;}
      .login .box .bt input{float:left;width:80px; height:34px; background:url(images/login/bg_bt.png) no-repeat;font-size:14px;color:#fff;margin:0 20px 0 0; cursor: pointer;}
      .login .box .bt a{float:left; line-height:34px; color:#D1D3D4;display:block}
      .login .box .bt a:hover{color:#F15A29;}
-->
</style>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript">
function do_reset() {
	$('#password').val("");
	$('#loginUserName').val("");
}
</script>
</head>
<body>
<form name="loginform" action="${ctxPath}/j_spring_security_check" method="post">
<div class="login">
 	<div class="logo">
 		<div id="errorMessage">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</div>
 	</div>
 	<div class="box">
  		<div class="uid"><input name="j_username" id="loginUserName" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" type="text"></div>
  		<div class="pwd"><input name="j_password" id="password" type="password" maxlength="20"></div>
  		<div class="bt"><input name="login" type="submit" value="登录"> <a href="javascript:void(0);" onclick="do_reset()">重 置</a></div>
 	</div>
</div>
</form>
</body>
</html>

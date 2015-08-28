<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>用户登录已超时</title>
<style type="text/css">
<!--
*{font-family:"Microsoft Yahei","冬青黑体简体中文 w3","宋体";text-decoration:none;border:0;margin:0;padding:0;}
body {font-size: 14px;background:url(images/bg_body.png) repeat;}
.cue{position:absolute;  top:50%;  height:70px;margin:0 auto; margin-top:-50px; width:480px; left:50%; margin-left:-300px; font-size:14px; line-height:25px; padding:15px 0 15px 120px; color:#D1D3D4;background:url(images/icon_cue.png) no-repeat 0 0;}
.cue a{ color:#F15A29; padding:0 5px}
-->
</style>
</head>
<body>
<div class="cue" id ="sessionOut">您长时间未操作系统，为确保您的资料及数据信息的安全，系统自动超时退出，请重新<a href="<%=basePath%>login.action">登录</a>系统！ </div>
</body>
</html>
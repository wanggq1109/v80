<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>80视觉团队 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/studio.css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/jqthumb/jqthumb.js"></script>
<script>$(document).ready(function(){$('.nav .seven').addClass('cur');});</script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script>
<script type="text/javascript">$(function(){var fix = $("#Header");fix.scrollFix();})</script>
</head>
<body>
<!--header start-->
<jsp:include page="top.jsp"></jsp:include>
<!--header end-->
<!--main start-->
<div id="main">
  <div class="container">
   <div id="team">
    <div class="header"></div>
    <div class="teamlist">
     <ul>
     <script>$(function(){$('.teamlist li img').jqthumb({width:245,height:325,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
     <s:iterator value="collectionEmployeePhoto" var="employee" status="t">
       <s:if test="#t.count%4 != 0"><li></s:if><s:if test="#t.count%4 == 0"><li class="f"></s:if><a href="studioemployeePhotoshow.action?id=${employee.id}"><img src="<%=basePath%>${employee.photoUrl}"/></a><div class="camerist"></div><div class="title">${employee.position}</div></li>
     </s:iterator>
     </ul>
    </div>
   </div>
  </div>
</div>
<!--main end-->
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
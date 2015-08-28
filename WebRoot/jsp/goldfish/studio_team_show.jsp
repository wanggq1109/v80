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
<title>工作室简介 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/studio.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/jqthumb/jqthumb.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script><!--photo-->
<script>
	$(document).ready(function(){
		$('.nav .seven').addClass('cur');
	});
</script>
<script type="text/javascript">
		$(function(){
			var fix = $("#Header");
			fix.scrollFix();
		})
</script>
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
    <div class="show">
     <script>$(function(){$('.photo img').jqthumb({width:245,height:325,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
     <div class="photo"><img src="<%=basePath%>${employee.photoUrl}"/><div class="camerist"></div><div class="title">${employee.position }</div></div>
     <div class="cbox">
      <h2>${employee.position }---${employee.name }</h2>
      <div class="intro">${employee.intro}</div>

    <script type="text/javascript">$(document).ready(function(){$("#pl .ex div").hover(function() {$(this).animate({"top": "-200px"}, 150, "swing");},function() {$(this).stop(true,false).animate({"top": "0px"}, 400, "swing");});});</script>
    <script>$(function(){$('.workslist ul li img').jqthumb({width:200,height:200,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
      <div class="workslist"><%--
       <h3>作品</h3>
       --%><ul id="pl">
<script>
$(function(){
	$(".workslist li .rsp").hide();	
	$(".workslist li").hover(function(){
		$(this).find(".rsp").stop().fadeTo(500,0.5)
		$(this).find(".text").stop().animate({left:'0'}, {duration: 500})
	},
	function(){
		$(this).find(".rsp").stop().fadeTo(500,0)
		$(this).find(".text").stop().animate({left:'200'}, {duration: "fast"})
		$(this).find(".text").animate({left:'-200'}, {duration: 0})
	});
});
</script>
       </ul>
      </div>
     </div>
    </div>
   </div>
  </div>
</div>
<!--main end-->
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
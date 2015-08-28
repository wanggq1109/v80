<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>在线订购 - 活动团购 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/enroll.css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script>
<script type="text/javascript">
		$(function(){
			var fix = $("#Header"), fixbottom = $(".pricebox");
			fix.scrollFix();
			fixbottom.scrollFix({distanceTop:90,endPos:425});
		})
</script>
</head>
<body>
<!--header start-->
<jsp:include page="top.jsp"></jsp:include>
<!--header end-->
<!--slide start-->

<!--slide end-->
<!--main start-->
<div id="main">
  <div class="container">
   <div id="enroll">
    <div class="header">您现在的位置：<a href="#">首页</a> > <a href="#">团购活动</a> > <a href="#">在线订购</a></div>
    <div class="show">
      <div class="title"><h1>《浪漫情人岛》</h1></div>
      <div class="cbox">
      <ul>
       <li><span>团购价格：</span><span class="price">¥ 9999 </span></li>
       <li><span>真实姓名：</span><input type="text" name="name" id="name" class="input" value=""> * </li>
       <li><span>手机号码：</span><input type="text" name="phone" id="name" class="input" value="">* </li>
       <li><span>QQ 号码：</span><input type="text" name="qq" id="qq" class="input" value="">* </li>
       <li><span>备　　注：</span><textarea name="beizhu" id="name" class="intext" ></textarea></li>    
       <li><span>验 证 码：</span><input name="validate" type="text" id="validate" size="6" class="code" /><img src="<%=basePath%>goldfish/images/yzm.jpg" onClick="location.reload();"/><span class="other">看不清？点击更换</span></li>
       <li><div class="bt"><a href="#">提交订单</a></div></li>
      </ul>
      </div>
      
      <div class="rightbar">
      <div class="list grouplist mb30"><h3><a class="more" href="#">more</a></h3>
       <ul>
       <li><a href="#"><img src="<%=basePath%>goldfish/images/3-1.jpg"/></a></li>
       <li><a href="#"><img src="<%=basePath%>goldfish/images/3-2.jpg"/></a></li>
       </ul>
      </div>
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

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>80视觉服务 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/studio.css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script>
	$(document).ready(function(){
		$('.nav .eight').addClass('cur');
	});
</script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script>
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
   <div id="service">
    <div class="header"></div>
    <div class="procedure"><img src="<%=basePath%>goldfish/images/bg_procedure.png"/></div>
    <div class="procedurelist">
     <ul>
      <li><div class="one"></div><div class="c">选购您的摄影套系； 您也可以在线预约，我们的专业贴心的摄影顾问会在24小时内为您回复电话，约见具体咨询时间，您到达客服中心后，可与摄影顾问沟通，我们也会针对您的喜好为您推荐适合您的摄影套系，摄影尊崇的是为您量身定制，为您高端服务，您在鉴选之前，可以与摄影顾问进行深入的沟通。预约好摄影套系，我们将为您启动5星服务企划书正式成为摄影尊贵的顾客。</div></li>
      <li><div class="two"></div><div class="c">选购您的摄影套系； 您也可以在线预约，我们的专业贴心的摄影顾问会在24小时内为您回复电话，约见具体咨询时间，您到达客服中心后，可与摄影顾问沟通，我们也会针对您的喜好为您推荐适合您的摄影套系，摄影尊崇的是为您量身定制，为您高端服务，您在鉴选之前，可以与摄影顾问进行深入的沟通。预约好摄影套系，我们将为您启动5星服务企划书正式成为摄影尊贵的顾客。</div></li>
      <li><div class="three"></div><div class="c">选购您的摄影套系； 您也可以在线预约，我们的专业贴心的摄影顾问会在24小时内为您回复电话，约见具体咨询时间，您到达客服中心后，可与摄影顾问沟通，我们也会针对您的喜好为您推荐适合您的摄影套系，摄影尊崇的是为您量身定制，为您高端服务，您在鉴选之前，可以与摄影顾问进行深入的沟通。预约好摄影套系，我们将为您启动5星服务企划书正式成为摄影尊贵的顾客。</div></li>
      <li><div class="four"></div><div class="c">选购您的摄影套系； 您也可以在线预约，我们的专业贴心的摄影顾问会在24小时内为您回复电话，约见具体咨询时间，您到达客服中心后，可与摄影顾问沟通，我们也会针对您的喜好为您推荐适合您的摄影套系，摄影尊崇的是为您量身定制，为您高端服务，您在鉴选之前，可以与摄影顾问进行深入的沟通。预约好摄影套系，我们将为您启动5星服务企划书正式成为摄影尊贵的顾客。</div></li>
      <li><div class="five"></div><div class="c">选购您的摄影套系； 您也可以在线预约，我们的专业贴心的摄影顾问会在24小时内为您回复电话，约见具体咨询时间，您到达客服中心后，可与摄影顾问沟通，我们也会针对您的喜好为您推荐适合您的摄影套系，摄影尊崇的是为您量身定制，为您高端服务，您在鉴选之前，可以与摄影顾问进行深入的沟通。预约好摄影套系，我们将为您启动5星服务企划书正式成为摄影尊贵的顾客。</div></li>
      <li><div class="six"></div><div class="c">选购您的摄影套系； 您也可以在线预约，我们的专业贴心的摄影顾问会在24小时内为您回复电话，约见具体咨询时间，您到达客服中心后，可与摄影顾问沟通，我们也会针对您的喜好为您推荐适合您的摄影套系，摄影尊崇的是为您量身定制，为您高端服务，您在鉴选之前，可以与摄影顾问进行深入的沟通。预约好摄影套系，我们将为您启动5星服务企划书正式成为摄影尊贵的顾客。</div></li>
      <li><div class="seven"></div><div class="c">选购您的摄影套系； 您也可以在线预约，我们的专业贴心的摄影顾问会在24小时内为您回复电话，约见具体咨询时间，您到达客服中心后，可与摄影顾问沟通，我们也会针对您的喜好为您推荐适合您的摄影套系，摄影尊崇的是为您量身定制，为您高端服务，您在鉴选之前，可以与摄影顾问进行深入的沟通。预约好摄影套系，我们将为您启动5星服务企划书正式成为摄影尊贵的顾客。</div></li>
      <li class="n"><div class="eight"></div><div class="c">选购您的摄影套系； 您也可以在线预约，我们的专业贴心的摄影顾问会在24小时内为您回复电话，约见具体咨询时间，您到达客服中心后，可与摄影顾问沟通，我们也会针对您的喜好为您推荐适合您的摄影套系，摄影尊崇的是为您量身定制，为您高端服务，您在鉴选之前，可以与摄影顾问进行深入的沟通。预约好摄影套系，我们将为您启动5星服务企划书正式成为摄影尊贵的顾客。</div></li>
     </ul>
    </div>
   </div>
   
  </div>
</div>
<!--main end-->

<jsp:include page="foot.jsp"></jsp:include>

</body>
</html>
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
<title>顾客评价 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/list.css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script>
<script type="text/javascript">
		$(function(){
			var fix = $("#Header"), fixbottom = $(".pricebox");
			fix.scrollFix();
			fixbottom.scrollFix({distanceTop:90,endPos:425});
		})
</script>
<script>
	$(document).ready(function(){
		$('.nav .six').addClass('cur');
	});
</script>
</head>
<body>
<!--header start-->
<jsp:include page="top.jsp"></jsp:include>
<!--header end-->
<!--main start-->
<div id="main">
  <div class="container">
   <div id="Canonicals">
    <div class="CanonicalsList"><ul id="pl">
       <div class="classifybox mr25">
        <div class="cheader"><h1>全部</h1><p>112组</p></div>
        <div class="classify"><a title="" href="#">最新</a>/<a title="" href="#">外景</a>/<a title="" href="#">内景</a><a title="" href="#">古典</a>/<a title="" href="#">现代</a></div>
       </div>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-4.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-2.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-3.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-4.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-4.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-2.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-3.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-4.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-2.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-3.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-2.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-3.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-4.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-4.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-2.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-3.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-4.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mr25 mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-2.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
       <li class="mb25"><a href="#"></a><img src="<%=basePath%>goldfish/images/4-3.jpg"><div class="overLayer"></div><div class="infoLayer"><h2>《浪漫情人岛》</h2><p>26张</p></div></li>
      </ul>
      <div class="pages"><span><</span><a href="#">1</a><a href="#">2</a><a href="#">3</a><a href="#" class="current">4</a><a href="#">5</a><a href="#">6</a><a href="#">7</a><a href="#">8</a><a href="#">></a></div>
     </div>
   </div>
  </div>
</div>
<!--main end-->

<jsp:include page="foot.jsp"></jsp:include>

</body>
</html>
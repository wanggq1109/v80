<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<title>八零视觉婚纱摄影工作室成立 - 新闻 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/content.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>jsp/goldfish/video-js/video-js.css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="<%=basePath%>jsp/goldfish/video-js/video.js" type="text/javascript"></script>
<script src="<%=basePath%>js/jqthumb/jqthumb.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script>
<script type="text/javascript">
	$(function(){
		var fix = $("#Header");
		fix.scrollFix();
		//$('.nav .six').addClass('cur');
	});
</script>
</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
<!--main start-->
<div id="main">
  	<div class="container">
   		<div id="news">
    		<div class="header">您现在的位置：<a href="#">首页</a> > <a href="news_list.html">新闻</a></div>
    		<div class="show">
     			<div class="cbox">
      				<div class="title"><h1>${newsinfo.title}</h1></div>
      				<div class="other">来源：${newsinfo.source}　发布日期：${newsinfo.releaseTime }　 阅读次数：1317<div class="share"><!-- JiaThis Button BEGIN -->
	      				<div class="jiathis_style">
	      					<a class="jiathis_button_qzone"></a>
	      					<a class="jiathis_button_tsina"></a>
	      					<a class="jiathis_button_tqq"></a>
	      					<a class="jiathis_button_weixin"></a>
	      					<a class="jiathis_button_renren"></a>
	      					<a class="jiathis_button_xiaoyou"></a>
	      					<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
	      					<a class="jiathis_counter_style"></a>
	      				</div>
	      				<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1393983951615117" charset="utf-8"></script><!-- JiaThis Button END --></div>
      				</div>
      				<div class="intro">${newsinfo.content }</div>
                    <div class="comment">
                      <!-- 多说评论框 start -->
	<div class="ds-thread" data-thread-key="${newsinfo.id}" data-title="${newsinfo.title }" data-url="<%=basePath%>shownews_content.action?id=${newsinfo.id}"></div>
<!-- 多说评论框 end -->
<!-- 多说公共JS代码 start (一个网页只需插入一次) -->
<script type="text/javascript">
var duoshuoQuery = {short_name:"80shijue"};
	(function() {
		var ds = document.createElement('script');
		ds.type = 'text/javascript';ds.async = true;
		ds.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') + '//static.duoshuo.com/embed.js';
		ds.charset = 'UTF-8';
		(document.getElementsByTagName('head')[0] 
		 || document.getElementsByTagName('body')[0]).appendChild(ds);
	})();
	</script>
                    <!-- 多说公共JS代码 end -->
                    </div>
     			</div>
     			<div class="rightbar">
      				<div class="list newslist mb30">
      					<h3></h3>
       					<ul>
       						<s:iterator value="newsList" var="news" status="t">
      							<li><a href="shownews_content.action?id=${news.id}">${news.title}</a></li>
      						</s:iterator>
       					</ul>
      				</div>
      				<div class="list grouplist mb30">
      				  <script>$(function(){$('.grouplist li a img').jqthumb({width:320,height:218,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
      					<h3></h3>
       					<ul>
       						<s:iterator value="photoListweekRecommend" var="photo" status="t">
		 						<li><a href="photoListforTopicweek.action?id=${photo.phototdesc.id}"><img src="<%=basePath%>${photo.breviaryUrl}"/></a></li>
	   						</s:iterator>
       					</ul>
      				</div>
      				<div class="list microfilmlist">
      					<h3><a class="more" href="#">more</a></h3>
       					<ul>
       						<s:iterator value="videoListnew" var="film" status="t">
		    				<li>
			   					<a title="" href="javascript:void(0)">
				   					<video id="video_list" class="video-js vjs-default-skin" controls preload="none" width="320" height="180" poster="<%=basePath%>${film.suolueurl}" data-setup="{}">
									    <source src="<%=basePath%>${film.url}" type='video/mp4' />
									    <source src="<%=basePath%>${film.url}" type='video/webm' />
									    <source src="<%=basePath%>${film.url}" type='video/ogg' />
									    <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
									    <track kind="subtitles" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
								  	</video>
			   					</a>
			   				</li>
	   						</s:iterator>
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
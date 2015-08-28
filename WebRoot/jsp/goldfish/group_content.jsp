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
<title>团购活动详情 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/content.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>jsp/goldfish/video-js/video-js.css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="<%=basePath%>jsp/goldfish/video-js/video.js" type="text/javascript"></script>
<script src="<%=basePath%>goldfish/js/slide/jquery-finger-v0.1.0.min.js" type="text/javascript"></script>
<script src="<%=basePath%>goldfish/js/slide/modernizr-custom-v2.7.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script>
<script type="text/javascript">
	$(function(){
		var fix = $("#Header"), fixbottom = $(".pricebox");
		fix.scrollFix();
		fixbottom.scrollFix({distanceTop:90,endPos:425});
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
   		<div id="group">
    		<div class="header">您现在的位置：<a href="#">首页</a> > <a href="<%=basePath%>group_list.action">活动团购</a></div>
    		<div class="show">
      			<div class="title">
      				<div class="share">
	      				<!-- JiaThis Button BEGIN -->
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
	      				<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1393983951615117" charset="utf-8"></script>
	      				<!-- JiaThis Button END -->
	      			</div>
	      			<h1>《${groupitem.title }》</h1>
	      		</div>
      			<div class="cbox">
       				${groupitem.content }
                    <div class="comment">
                      <!-- 多说评论框 start -->
	<div class="ds-thread" data-thread-key="${groupitem.id}" data-title="${topic.title }" data-url="<%=basePath%>group_content.action?id=${groupitem.id}"></div>
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
      				<div class="pricebox pt5 pb20 mb10">
       					<div class="price">
       						<span class="y fl">¥ </span>
       						<span class="n fl">${groupitem.price }</span>
       						<span class="o fl">${groupitem.old_price }</span>
       					</div>
       					<div class="bt">
       						<a href="http://lj80.taobao.com/shop/view_shop.htm?tracelog=twddp&user_number_id=26841448&qq-pf-to=pcqq.c2c">我要团购</a>
       					</div>
      				</div>
      				<div class="list newslist mb30">
      					<h3><a class="more" href="#">more</a></h3>
       					<ul>
       						<s:iterator value="newsList2" var="news" status="t">
      							<li><a href="shownews_content.action?id=${news.id}">${news.title}</a></li>
      						</s:iterator>
       					</ul>
      				</div>
      				<div class="list grouplist mb30">
      					<h3><a class="more" href="#">more</a></h3>
       					<ul>
       						<s:iterator value="photoListweekRecommendsub" var="photo" status="t">
		 						<li><a href="photoListforTopicweek.action?id=${photo.phototdesc.id}"><img src="<%=basePath%>${photo.breviaryUrl}" width="320"/></a></li>
	   						</s:iterator>
       					</ul>
      				</div>
      				<div class="list microfilmlist">
      					<h3></h3>
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
			   				    <div class="intro"><h5><a title="" href="#">《${huaxufilmInfo.name}》</a></h5><p>拍摄时间：<s:date name="huaxufilmInfo.shootingDate" format="yyyy-MM-dd"/></p></div>
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
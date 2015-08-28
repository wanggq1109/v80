<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String code = (String)request.getAttribute("code");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>《${topic.title }》 - 礼服赏析 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/content.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=basePath%>goldfish/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/jqthumb/jqthumb.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script>
<script type="text/javascript">$(document).ready(function(){var fix = $("#Header");fix.scrollFix();$('.nav .three').addClass('cur');});</script>
<script>$(function(){$('.photolist img').jqthumb({width:156,height:234,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>

</head>
<body>
<jsp:include page="top.jsp"></jsp:include>
<!--slide end-->
<!--main start-->
<div id="main">
  	<div class="container">
   		<div id="canonicals">
    		<div class="header">您现在的位置：<a href="#">首页</a> > <a href="<%=basePath%>dressshowmenu.action">礼服赏析</a></div>
    		<div class="show">
     			<div class="title">
     				<div class="share"><!-- JiaThis Button BEGIN -->
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
     					<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1393983951615117" charset="utf-8"></script><!-- JiaThis Button END -->
     				</div>
     				<h1>《${topic.title}》</h1>
     			</div>
     			<div class="cbox">
        			<div class="intro"><p>描      述：${topic.photTopicDesc}</p></div>
     				<div class="photolist">
      					<ul class="gallery">
     					<s:iterator value="dressListInfo" var="photo" status="t">
     						<s:if test="#t.count%7==0"><li class="ex"></s:if>
    						<s:else><li class="ex mr18"></s:else>
      					   	<a href="<%=basePath%>${photo.photoUrl}"><img src="<%=basePath%>${photo.breviaryUrl}"/><div class='overLayer'></div></a></li>
     					</s:iterator>
     					</ul>
      				</div>
      				<script type="text/javascript" src="<%=basePath%>goldfish/js/box/zoom.min.js"></script>
      				
                    <div class="comment">
                      <!-- 多说评论框 start -->
	<div class="ds-thread" data-thread-key="${topic.id}" data-title="${topic.title }" data-url="<%=basePath%>dressphotoListforTopic.action?id=${topic.id}"></div>
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
    		</div>
    	</div>
   	</div>
</div>
<!--main end-->
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
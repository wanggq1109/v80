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
<title>首页 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link type="image/x-icon" href="favicon.ico" rel="icon" >
<link type="image/x-icon" href="http://121.41.92.58:80/favicon.ico" rel="shortcut icon">
<link rel="Bookmark" href="favicon.ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/index.css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="<%=basePath%>goldfish/js/jquery.SuperSlide2.js" type="text/javascript"></script><!--客片-->
<script src="<%=basePath%>goldfish/js/MSClass.js" type="text/javascript"></script>
<script src="<%=basePath%>goldfish/js/jqthumb/jqthumb.js" type="text/javascript"></script>
<script src="<%=basePath%>goldfish/js/scrollfix.min.js" type="text/javascript"></script><!--photo-->
<script type="text/javascript">$(function(){var fix = $("#Header");fix.scrollFix();$('.nav .one').addClass('cur');})</script>
</head>
<body>
<!--header start-->
<jsp:include page="top.jsp"></jsp:include>
<!--header end-->
<!--main start-->
<div id="main">
<!--一周推荐开始-->
<div class="container oh mb20">
  <div class="cheader"><h4 class="one"></h4><span class="one"><a href="<%=basePath%>photoListforTopicweek.action?id=${weekrecommend.phototdesc.id}" target="_blank">更多+</a></span></div>
	<div id="Recommend">
 	<script>$(function(){$('.RecommendList li.first a img').jqthumb({width:470,height:335,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
	<script>$(function(){$('.RecommendList li.o a img').jqthumb({width:228,height:156,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
	<div class="RecommendList">
	 <ul id="pl">
      <li class="first"><a href="photoListforTopicweek.action?id=${weekrecommend.phototdesc.id}" title="${weekrecommend.phototdesc.title }" target="_blank"><div class="photo"><img src="<%=basePath%>${weekrecommend.photoUrl}"/></div><div class="overLayer"></div><div class="intro"><h3>${weekrecommend.phototdesc.title}</h3><p>${weekrecommend.phototdesc.photTopicDesc }</p></div></a></li>
	  <s:iterator value="photoListweekRecommend" var="photo" status="t">
      <s:if test="#t.count==1"><li class="o mb23"></s:if>
      <s:if test="#t.count==2"><li class="o mb23"></s:if>
      <s:if test="#t.count==3"><li class="o mb23"></s:if>
      <s:if test="#t.count==4"><li class="o"></s:if>
      <s:if test="#t.count==5"><li class="o"></s:if>
      <s:if test="#t.count==6"><li class="o"></s:if>
        <a href="<%=basePath%>photoListforTopicweek.action?id=${photo.phototdesc.id}" title="${photo.phototdesc.title}" target="_blank">
          <div class="photo"><s:if test="%{#photo.breviaryUrl==''||#photo.breviaryUrl==null}"><img src="<%=basePath%>goldfish/images/imgbg_05.png"/></s:if><s:else><img src="<%=basePath%>${photo.breviaryUrl}" /></s:else></div>
          <div class="overLayer"></div>
          <div class="intro"><h6>${photo.phototdesc.title }</h6><p>摄影师：${photo.phototdesc.cameraman}</p></div>
        </a>
      </li>
	  </s:iterator>
	 </ul>
    </div>
  </div>
</div>
<!--一周推荐结束-->  

<!--客片开始-->
<script type="text/javascript">
$(document).ready(function () {
	/* 图片滚动效果 */
	$(".mr_frbox").slide({
		titCell: "",
		mainCell: ".mr_frUl ul",
		autoPage: true,
		effect: "leftLoop",
		autoPlay: true,
		vis: 5
	});
});
</script>
<script>$(function(){$('#mr_fu li a img').jqthumb({width:160,height:250,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
<div class="container mb15">
  <div class="cheader"><h4 class="three"></h4><span class="three"><a href="customerPhotoShare.action" target="_blank">更多+</a></span></div>
  <div class="showcase">
    <div class="mr_frbox">
      <img class="mr_frBtnL prev" src="goldfish/images/mfrL.jpg" />
      <div class="mr_frUl">
        <ul id="mr_fu">
        <s:iterator value="customerphotosList" var="photo" status="t">
        	<s:if test="#t.count%2 != 0">
        		<li><a href="<%=basePath%>showcase_content_init.action?id=${photo.id}" title="${customerName}" target="_blank"><img src="<%=basePath%>${photo.url}" /><div class="intro"><div class="time"><span class="d">${photo.cDate}</span><span class="m">${photo.cMonth}</span></div><div class="title"><h6>${customerName}</h6><span></span></div></div></a>
        	</s:if>
        	<s:if test="#t.count%2 == 0">
        		<a href="<%=basePath%>showcase_content_init.action?id=${photo.id}" title="${customerName}" target="_blank"><img src="<%=basePath%>${photo.url}" /><div class="intro"><div class="time"><span class="d">${photo.cDate}</span><span class="m">${photo.cMonth}</span></div><div class="title"><h6>${customerName}</h6><span></span></div></div></a></li>
        	</s:if>
        </s:iterator>
        </ul>
      </div>
      <img class="mr_frBtnR next" src="goldfish/images/mfrR.jpg" />
    </div>
  </div>
</div>
<!--客片结束-->  

<!--摄影作品开始-->
<script type="text/javascript">$(document).ready(function() {$(".PhotographyList").hide();$("ul.Pcolumn li:first").addClass("active").show();$(".PhotographyList:first").show();$("ul.Pcolumn li").click(function() {$("ul.Pcolumn li").removeClass("active");$(this).addClass("active");$(".PhotographyList").hide();var activeTab = $(this).find("a").attr("href");$(activeTab).fadeIn(); return false;});});</script>
<script>$(function(){$('.PhotographyList li a img').jqthumb({width:184,height:184,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
<div class="container oh mb30">
			<div class="cheader">
				<h4 class="two"></h4>
				<span class="two">
					<ul class="Pcolumn column">
						<s:iterator value="photoworksTypeTagList" var="photo" status="t">
						<li>
							<s:if test="#t.count==1"><a href="#one"></s:if>
							<s:if test="#t.count==2"><a href="#two"></s:if>
							<s:if test="#t.count==3"><a href="#three"></s:if>
							<s:if test="#t.count==4"><a href="#four"></s:if>
							${photo.text}</a>
						</li>
						</s:iterator>
					</ul>
					<a href="photography_list.action" target="_blank">更多+</a>
				</span>
			</div>
			<div id="one" class="PhotographyList">
    <ul class="clearfix"><s:iterator value="photoListwaijing" var="photo" status="t">
      <s:if test="#t.count==1"><li class="o"></s:if>
      <s:if test="#t.count==2"><li class="lld"></s:if>
      <s:if test="#t.count==3"><li class="lld"></s:if>
      <s:if test="#t.count==4"><li class="lld"></s:if>
      <s:if test="#t.count==5"><li class="lld"></s:if>
      <s:if test="#t.count==6"><li class="lld l"></s:if>
      <s:if test="#t.count==7"><li class="f"></s:if>
      <s:if test="#t.count==8"><li class="lld ltd"></s:if>
      <s:if test="#t.count==9"><li class="lld ltd"></s:if>
      <s:if test="#t.count==10"><li class="lld ltd"></s:if>
      <s:if test="#t.count==11"><li class="lld ltd"></s:if>
      <s:if test="#t.count==12"><li class="lld t"></s:if>
        <a href="<%=basePath%>photoListforTopic.action?id=${photo.id}" title="${photo.name}" target="_blank">
          <div class="photo"><s:if test="%{#photo.url==''||#photo.url==null}"><img src="<%=basePath%>goldfish/images/imgbg_02.png"/></s:if><s:else><img src="<%=basePath%>${photo.url}"/></s:else></div>
          <div class="overLayer"></div><div class="intro"><h6>${photo.name}</h6></div>
        </a>
      </li>
      </s:iterator>
	</ul>
    <div class="clear"></div>
  </div>
  <div id="two" class="PhotographyList">
    <ul class="clearfix"><s:iterator value="photoListneijing" var="photo" status="t">
      <s:if test="#t.count==1"><li class="o"></s:if>
      <s:if test="#t.count==2"><li class="lld"></s:if>
      <s:if test="#t.count==3"><li class="lld"></s:if>
      <s:if test="#t.count==4"><li class="lld"></s:if>
      <s:if test="#t.count==5"><li class="lld"></s:if>
      <s:if test="#t.count==6"><li class="lld l"></s:if>
      <s:if test="#t.count==7"><li class="f"></s:if>
      <s:if test="#t.count==8"><li class="lld ltd"></s:if>
      <s:if test="#t.count==9"><li class="lld ltd"></s:if>
      <s:if test="#t.count==10"><li class="lld ltd"></s:if>
      <s:if test="#t.count==11"><li class="lld ltd"></s:if>
      <s:if test="#t.count==12"><li class="lld t"></s:if>
        <a href="<%=basePath%>photoListforTopic.action?id=${photo.id}" title="${photo.name}" target="_blank">
          <div class="photo"><s:if test="%{#photo.url==''||#photo.url==null}"><img src="<%=basePath%>goldfish/images/imgbg_02.png"/></s:if><s:else><img src="<%=basePath%>${photo.url}"/></s:else></div>
          <div class="overLayer"></div><div class="intro"><h6>${photo.name}</h6></div>
        </a>
      </li>
      </s:iterator>
	</ul>
    <div class="clear"></div>
  </div>
  <div id="three" class="PhotographyList">
    <ul class="clearfix"><s:iterator value="photoListgudian" var="photo" status="t">
      <s:if test="#t.count==1"><li class="o"></s:if>
      <s:if test="#t.count==2"><li class="lld"></s:if>
      <s:if test="#t.count==3"><li class="lld"></s:if>
      <s:if test="#t.count==4"><li class="lld"></s:if>
      <s:if test="#t.count==5"><li class="lld"></s:if>
      <s:if test="#t.count==6"><li class="lld l"></s:if>
      <s:if test="#t.count==7"><li class="f"></s:if>
      <s:if test="#t.count==8"><li class="lld ltd"></s:if>
      <s:if test="#t.count==9"><li class="lld ltd"></s:if>
      <s:if test="#t.count==10"><li class="lld ltd"></s:if>
      <s:if test="#t.count==11"><li class="lld ltd"></s:if>
      <s:if test="#t.count==12"><li class="lld t"></s:if>
        <a href="<%=basePath%>photoListforTopic.action?id=${photo.id}" title="${photo.name}" target="_blank">
          <div class="photo"><s:if test="%{#photo.url==''||#photo.url==null}"><img src="<%=basePath%>goldfish/images/imgbg_02.png"/></s:if><s:else><img src="<%=basePath%>${photo.url}"/></s:else></div>
          <div class="overLayer"></div><div class="intro"><h6>${photo.name}</h6></div>
        </a>
      </li>
      </s:iterator>
	</ul>
    <div class="clear"></div>
  </div>
  <div id="four" class="PhotographyList">
    <ul class="clearfix"><s:iterator value="photoListxiandai" var="photo" status="t">
      <s:if test="#t.count==1"><li class="o"></s:if>
      <s:if test="#t.count==2"><li class="lld"></s:if>
      <s:if test="#t.count==3"><li class="lld"></s:if>
      <s:if test="#t.count==4"><li class="lld"></s:if>
      <s:if test="#t.count==5"><li class="lld"></s:if>
      <s:if test="#t.count==6"><li class="lld l"></s:if>
      <s:if test="#t.count==7"><li class="f"></s:if>
      <s:if test="#t.count==8"><li class="lld ltd"></s:if>
      <s:if test="#t.count==9"><li class="lld ltd"></s:if>
      <s:if test="#t.count==10"><li class="lld ltd"></s:if>
      <s:if test="#t.count==11"><li class="lld ltd"></s:if>
      <s:if test="#t.count==12"><li class="lld t"></s:if>
        <a href="<%=basePath%>photoListforTopic.action?id=${photo.id}" title="${photo.name}" target="_blank">
          <div class="photo"><s:if test="%{#photo.url==''||#photo.url==null}"><img src="<%=basePath%>goldfish/images/imgbg_02.png"/></s:if><s:else><img src="<%=basePath%>${photo.url}"/></s:else></div>
          <div class="overLayer"></div><div class="intro"><h6>${photo.name}</h6></div>
        </a>
      </li>
      </s:iterator>
	</ul>
    <div class="clear"></div>
  </div>
</div>
<!--摄影作品结束-->  



<!--热点景区开始-->
<script type="text/javascript">$(document).ready(function() {$(".hotspots").hide();$("ul.Hcolumn li:first").addClass("active").show();$(".hotspots:first").show();$("ul.Hcolumn li").click(function() {$("ul.Hcolumn li").removeClass("active");$(this).addClass("active");$(".hotspots").hide();var activeTab = $(this).find("a").attr("href");$(activeTab).fadeIn(); return false;});});</script>
<script>$(function(){$('.hotspots li a img').jqthumb({width:184,height:184,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
	<div class="container oh mb30">
		<div class="cheader">
			<h4 class="four"></h4>
			<span class="four">
				<ul class="Hcolumn column">
					<s:iterator value="hotspotsType" var="photo" status="t">
						<li>
							<s:if test="#t.count==1"><a href="#five"></s:if>
							<s:if test="#t.count==2"><a href="#six"></s:if>
							<s:if test="#t.count==3"><a href="#seven"></s:if>
							<s:if test="#t.count==4"><a href="#eight"></s:if>
							${photo.text}</a>
						</li>
					</s:iterator>
				</ul> <a href="hotspotsshow_init.action" target="_blank">更多+</a>
			</span>
		</div>
		<div id="five" class="hotspots">
		    <ul class="clearfix"><s:iterator value="hotspots1" var="photo" status="t">
		      <s:if test="#t.count==1"><li class="o"></s:if>
		      <s:if test="#t.count==2"><li class="lld"></s:if>
		      <s:if test="#t.count==3"><li class="lld"></s:if>
		      <s:if test="#t.count==4"><li class="lld"></s:if>
		      <s:if test="#t.count==5"><li class="lld"></s:if>
		      <s:if test="#t.count==6"><li class="lld l"></s:if>
		      <s:if test="#t.count==7"><li class="f"></s:if>
		      <s:if test="#t.count==8"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==9"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==10"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==11"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==12"><li class="lld t"></s:if>
		        <a href="<%=basePath%>hotspotscontent_init.action?id=${photo.id}" title="${photo.name}" target="_blank">
		          <div class="photo"><s:if test="%{#photo.url==''||#photo.url==null}"><img src="<%=basePath%>goldfish/images/imgbg_02.png"/></s:if><s:else><img src="<%=basePath%>${photo.url}"/></s:else></div>
		          <div class="overLayer"></div><div class="intro"><h6>${photo.name}</h6></div>
		        </a>
		      </li>
		      </s:iterator>
			</ul>
    		<div class="clear"></div>
  		</div>
  		<div id="six" class="hotspots">
    		<ul class="clearfix"><s:iterator value="hotspots2" var="photo" status="t">
		      <s:if test="#t.count==1"><li class="o"></s:if>
		      <s:if test="#t.count==2"><li class="lld"></s:if>
		      <s:if test="#t.count==3"><li class="lld"></s:if>
		      <s:if test="#t.count==4"><li class="lld"></s:if>
		      <s:if test="#t.count==5"><li class="lld"></s:if>
		      <s:if test="#t.count==6"><li class="lld l"></s:if>
		      <s:if test="#t.count==7"><li class="f"></s:if>
		      <s:if test="#t.count==8"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==9"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==10"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==11"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==12"><li class="lld t"></s:if>
        		<a href="<%=basePath%>hotspotscontent_init.action?id=${photo.id}" title="${photo.name}" target="_blank">
		          <div class="photo"><s:if test="%{#photo.url==''||#photo.url==null}"><img src="<%=basePath%>goldfish/images/imgbg_02.png"/></s:if><s:else><img src="<%=basePath%>${photo.url}"/></s:else></div>
		          <div class="overLayer"></div><div class="intro"><h6>${photo.name}</h6></div>
        		</a>
      		  </li>
      		  </s:iterator>
			</ul>
    		<div class="clear"></div>
  		</div>
  		<div id="seven" class="hotspots">
		    <ul class="clearfix"><s:iterator value="hotspots3" var="photo" status="t">
		      <s:if test="#t.count==1"><li class="o"></s:if>
		      <s:if test="#t.count==2"><li class="lld"></s:if>
		      <s:if test="#t.count==3"><li class="lld"></s:if>
		      <s:if test="#t.count==4"><li class="lld"></s:if>
		      <s:if test="#t.count==5"><li class="lld"></s:if>
		      <s:if test="#t.count==6"><li class="lld l"></s:if>
		      <s:if test="#t.count==7"><li class="f"></s:if>
		      <s:if test="#t.count==8"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==9"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==10"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==11"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==12"><li class="lld t"></s:if>
		        <a href="<%=basePath%>hotspotscontent_init.action?id=${photo.id}" title="${photo.name}" target="_blank">
		          <div class="photo"><s:if test="%{#photo.url==''||#photo.url==null}"><img src="<%=basePath%>goldfish/images/imgbg_02.png"/></s:if><s:else><img src="<%=basePath%>${photo.url}"/></s:else></div>
		          <div class="overLayer"></div><div class="intro"><h6>${photo.name}</h6></div>
		        </a>
		      </li>
		      </s:iterator>
			</ul>
    		<div class="clear"></div>
  		</div>
  		<div id="eight" class="hotspots">
		    <ul class="clearfix"><s:iterator value="hotspots4" var="photo" status="t">
		      <s:if test="#t.count==1"><li class="o"></s:if>
		      <s:if test="#t.count==2"><li class="lld"></s:if>
		      <s:if test="#t.count==3"><li class="lld"></s:if>
		      <s:if test="#t.count==4"><li class="lld"></s:if>
		      <s:if test="#t.count==5"><li class="lld"></s:if>
		      <s:if test="#t.count==6"><li class="lld l"></s:if>
		      <s:if test="#t.count==7"><li class="f"></s:if>
		      <s:if test="#t.count==8"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==9"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==10"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==11"><li class="lld ltd"></s:if>
		      <s:if test="#t.count==12"><li class="lld t"></s:if>
		        <a href="<%=basePath%>hotspotscontent_init.action?id=${photo.id}" title="${photo.name}" target="_blank">
		          <div class="photo"><s:if test="%{#photo.url==''||#photo.url==null}"><img src="<%=basePath%>goldfish/images/imgbg_02.png"/></s:if><s:else><img src="<%=basePath%>${photo.url}"/></s:else></div>
		          <div class="overLayer"></div><div class="intro"><h6>${photo.name}</h6></div>
		        </a>
		      </li>
		      </s:iterator>
			</ul>
		    <div class="clear"></div>
  		</div>
	</div>
<!--热点景区结束-->  


<!--微电影开始-->
<script type="text/javascript">$(document).ready(function() {$(".MicrofilmList").hide();$("ul.Mcolumn li:first").addClass("active").show();$(".MicrofilmList:first").show();$("ul.Mcolumn li").click(function() {$("ul.Mcolumn li").removeClass("active");$(this).addClass("active");$(".MicrofilmList").hide();var activeTab = $(this).find("a").attr("href");$(activeTab).fadeIn(); return false;});});</script>
<script>$(function(){$('.MicrofilmList .hot img').jqthumb({width:468,height:287,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
<script>$(function(){$('.MicrofilmList ul li img').jqthumb({width:340,height:210,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
<div class="container oh mb30">
  <div class="cheader"><h4 class="five"></h4><span class="five"><ul class="Mcolumn column"><li><a href="#nine">最新</a></li><li><a href="#ten">热门</a></li><li><a href="#eleven">花絮</a></li></ul><a href="microfilm_init.action" target="_blank">更多+</a></span></div>
    <div id="nine" class="MicrofilmList">
     <div class="hot">
       <div class="video">
         <video id="video_main" class="video-js vjs-big-skin" controls preload="none" width="468" height="287" poster="<%=basePath%>${newfilmInfo.suolueurl}" data-setup="">
		   <source src="<%=basePath%>${newfilmInfo.url}" type='video/mp4' />
		   <source src="<%=basePath%>${newfilmInfo.url}" type='video/webm' />
	       <source src="<%=basePath%>${newfilmInfo.url}" type='video/ogg' />
		   <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
		   <track kind="subtitles" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
	  	 </video>
       </div>
       <div class="intro">
         <h3><a title="《${newfilmInfo.name}》" href="#">《${newfilmInfo.name}》</a></h3>
         <p>拍摄时间：<s:date name="newfilmInfo.shootingDate" format="yyyy-MM-dd"/></p><p>${newfilmInfo.intro}</p>
       </div>
     </div>
  	 <ul>
  	   <s:iterator value="videoListnew" var="film" status="t">
       <s:if test="#t.count==4"><li class="b"></s:if>
  	   <s:else><li></s:else>
		 <video id="video_list" class="video-js vjs-default-skin" controls preload="none" width="340" height="210" poster="<%=basePath%>${film.suolueurl}" data-setup="{}">
	     <source src="<%=basePath%>${film.url}" type='video/mp4' />
		 <source src="<%=basePath%>${film.url}" type='video/webm' />
	     <source src="<%=basePath%>${film.url}" type='video/ogg' />
	     <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
		 <track kind="subtitles" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
	     </video>
	   </li>
	   </s:iterator>
   	 </ul>
   </div>
    <div id="ten" class="MicrofilmList">
     <div class="hot">
       <div class="video">
         <video id="video_main" class="video-js vjs-big-skin" controls preload="none" width="468" height="287" poster="<%=basePath%>${microfilmInfo.suolueurl}" data-setup="">
		   <source src="<%=basePath%>${microfilmInfo.url}" type='video/mp4' />
		   <source src="<%=basePath%>${microfilmInfo.url}" type='video/webm' />
	       <source src="<%=basePath%>${microfilmInfo.url}" type='video/ogg' />
		   <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
		   <track kind="subtitles" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
	  	 </video>
       </div>
       <div class="intro">
         <h3><a title="《${microfilmInfo.name}》" href="#">《${microfilmInfo.name}》</a></h3>
         <p>拍摄时间：<s:date name="microfilmInfo.shootingDate" format="yyyy-MM-dd"/></p><p>${microfilmInfo.intro}</p>
       </div>
     </div>
  	 <ul>
  	   <s:iterator value="videoListMicroFilm" var="film" status="t">
       <s:if test="#t.count==4"><li class="b"></s:if>
  	   <s:else><li></s:else>
		 <video id="video_list" class="video-js vjs-default-skin" controls preload="none" width="340" height="210" poster="<%=basePath%>${film.suolueurl}" data-setup="{}">
	     <source src="<%=basePath%>${film.url}" type='video/mp4' />
		 <source src="<%=basePath%>${film.url}" type='video/webm' />
	     <source src="<%=basePath%>${film.url}" type='video/ogg' />
	     <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
		 <track kind="subtitles" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
	     </video>
	   </li>
	   </s:iterator>
   	 </ul>
   </div>
    <div id="eleven" class="MicrofilmList">
     <div class="hot">
       <div class="video">
         <video id="video_main" class="video-js vjs-big-skin" controls preload="none" width="468" height="287" poster="<%=basePath%>${huaxufilmInfo.suolueurl}" data-setup="">
		   <source src="<%=basePath%>${huaxufilmInfo.url}" type='video/mp4' />
		   <source src="<%=basePath%>${huaxufilmInfo.url}" type='video/webm' />
	       <source src="<%=basePath%>${huaxufilmInfo.url}" type='video/ogg' />
		   <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
		   <track kind="subtitles" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
	  	 </video>
       </div>
       <div class="intro">
         <h3><a title="《${huaxufilmInfo.name}》" href="#">《${huaxufilmInfo.name}》</a></h3>
         <p>拍摄时间：<s:date name="huaxufilmInfo.shootingDate" format="yyyy-MM-dd"/></p><p>${huaxufilmInfo.intro}</p>
       </div>
     </div>
  	 <ul>
  	   <s:iterator value="videoListMicroFilm" var="film" status="t">
       <s:if test="#t.count==4"><li class="b"></s:if>
  	   <s:else><li></s:else>
		 <video id="video_list" class="video-js vjs-default-skin" controls preload="none" width="340" height="210" poster="<%=basePath%>${film.suolueurl}" data-setup="{}">
	     <source src="<%=basePath%>${film.url}" type='video/mp4' />
		 <source src="<%=basePath%>${film.url}" type='video/webm' />
	     <source src="<%=basePath%>${film.url}" type='video/ogg' />
	     <track kind="captions" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
		 <track kind="subtitles" src="demo.captions.vtt" srclang="en" label="English"></track><!-- Tracks need an ending tag thanks to IE9 -->
	     </video>
	   </li>
	   </s:iterator>
   	 </ul>
   </div>
</div>
<!--微电影结束-->  

<!--礼服欣赏开始-->
<script type="text/javascript">$(document).ready(function() {$(".CanonicalsList").hide();$("ul.Ccolumn li:first").addClass("active").show();$(".CanonicalsList:first").show();$("ul.Ccolumn li").click(function() {$("ul.Ccolumn li").removeClass("active");$(this).addClass("active");$(".CanonicalsList").hide();var activeTab = $(this).find("a").attr("href");$(activeTab).fadeIn(); return false;});});</script>
<script>$(function(){$('.CanonicalsList ul li img').jqthumb({width:224,height:333,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
<div class="container oh mb30">
  <div class="cheader"><h4 class="six"></h4><span class="six">
  <ul class="Ccolumn column">
		<s:iterator value="dressTypeTagList" var="photo" status="t">
			<li><s:if test="#t.count==1">
					<a href="#twelve">
						</s:if> <s:if test="#t.count==2">
						  <a href="#thirteen">
							</s:if> <s:if test="#t.count==3">
								<a href="#fourteen">
							</s:if> ${photo.text}</a>
							</li>
						</s:iterator>
	</ul>					
  <a href="dressshowmenu.action" target="_blank">更多+</a></span></div>
  <div id="twelve" class="CanonicalsList">
    <ul>
      <s:iterator value="photoListdresschuantong" var="photo" status="t">
      <s:if test="#t.count==5"><li class="f"></s:if>
      <s:else><li></s:else>
        <a href="dressphotoListforTopic.action?id=${photo.id}" title="《${photo.name}》" target="_blank"><div class="photo"><s:if test="%{#photo.url==''||#photo.url==null}"><img src="<%=basePath%>goldfish/images/imgbg_01.png"/></s:if><s:else><img src="<%=basePath%>${photo.url}"/></s:else></div><div class="overLayer"></div></a>
        <div class="intro"><h4><a href="group_content.html">《${photo.name}》</a></h4><p>${photo.photosize}张</p></div>
      </li>
	  </s:iterator>
    </ul>
  </div>

  <div id="thirteen" class="CanonicalsList">
    <ul>
      <s:iterator value="photoListdressxiandai" var="photo" status="t">
      <s:if test="#t.count==5"><li class="f"></s:if>
      <s:else><li></s:else>
        <a href="dressphotoListforTopic.action?id=${photo.id}" title="《${photo.name}》" target="_blank"><div class="photo"><s:if test="%{#photo.url==''||#photo.url==null}"><img src="<%=basePath%>goldfish/images/imgbg_01.png"/></s:if><s:else><img src="<%=basePath%>${photo.url}"/></s:else></div><div class="overLayer"></div></a>
        <div class="intro"><h4><a href="dressphotoListforTopic.action?id=${photo.id}">《${photo.name}》</a></h4><p>${photo.photosize}张</p></div>
      </li>
	  </s:iterator>
    </ul>
  </div>

  <div id="fourteen" class="CanonicalsList">
    <ul>
      <s:iterator value="photoListdresshanxi" var="photo" status="t">
      <s:if test="#t.count==5"><li class="f"></s:if>
      <s:else><li></s:else>
        <a href="dressphotoListforTopic.action?id=${photo.id}" title="《${photo.name}》" target="_blank"><div class="photo"><s:if test="%{#photo.url==''||#photo.url==null}"><img src="<%=basePath%>goldfish/images/imgbg_01.png"/></s:if><s:else><img src="<%=basePath%>${photo.url}"/></s:else></div><div class="overLayer"></div></a>
        <div class="intro"><h4><a href="dressphotoListforTopic.action?id=${photo.id}">《${photo.name}》</a></h4><p>${photo.photosize}张</p></div>
      </li>
	  </s:iterator>
    </ul>
  </div>
</div>
<!--礼服欣赏结束-->  

<!--团购活动开始-->
<script type="text/javascript">$(document).ready(function() {$(".GrouppurchaseList").hide();$("ul.Gcolumn li:first").addClass("active").show();$(".GrouppurchaseList:first").show();$("ul.Gcolumn li").click(function() {$("ul.Gcolumn li").removeClass("active");$(this).addClass("active");$(".GrouppurchaseList").hide();var activeTab = $(this).find("a").attr("href");$(activeTab).fadeIn(); return false;});});</script>
<script>$(function(){$('.GrouppurchaseList ul li img').jqthumb({width:282,height:192,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
<div class="container oh mb30">
  <div class="cheader"><h4 class="seven"></h4><span class="seven"><ul class="Gcolumn column"><li><a href="#fifteen">最新</a></li><li><a href="#sixteen">热门</a></li></ul><a href="group_list.action" target="_blank">更多+</a></span></div>
  <div id="fifteen" class="GrouppurchaseList">
    <ul>
	  <s:iterator value="groupList" var="tuan" status="t">
      <s:if test="#t.count==4"><li class="l"></s:if>
 	  <s:else><li></s:else>
        <s:if test="%{#tuan.url==''||#tuan.url==null}"><a href="group_content.action?id=${tuan.id}" title="《${tuan.title}》" target="_blank"><img src="<%=basePath%>goldfish/images/imgbg_03.png"/><div class="overLayer"></div></a></s:if><s:else><a href="group_content.action?id=${tuan.id}" target="_blank"><img src="<%=basePath%>${tuan.url}"/><div class="overLayer"></div></a></s:else>
        <div class="intro"><h3><a href="group_content.action?id=${tuan.id}" title="《${tuan.title}》" target="_blank">《${tuan.title}》</a></h3><p>${tuan.descr}</p></div>
        <div class="other"><div class="price"><span class="y fl">¥ </span><span class="n fl">${tuan.price}</span><span class="o fl">${tuan.old_price}</span></div><div class="bt"><a href="group_content.action?id=${tuan.id}" target="_blank">我要团购</a></div></div>
      </li>
      </s:iterator> 
	</ul>
  </div>
  <div id="sixteen" class="GrouppurchaseList">
    <ul>
	  <s:iterator value="groupList" var="tuan" status="t">
      <s:if test="#t.count==4"><li class="l"></s:if>
 	  <s:else><li></s:else>
        <s:if test="%{#tuan.url==''||#tuan.url==null}"><a href="group_content.action?id=${tuan.id}" title="《${tuan.title}》" target="_blank"><img src="<%=basePath%>goldfish/images/imgbg_03.png"/><div class="overLayer"></div></a></s:if><s:else><a href="group_content.action?id=${tuan.id}" target="_blank"><img src="<%=basePath%>${tuan.url}"/><div class="overLayer"></div></a></s:else>
        <div class="intro"><h3><a href="group_content.action?id=${tuan.id}" title="《${tuan.title}》" target="_blank">《${tuan.title}》</a></h3><p>${tuan.descr}</p></div>
        <div class="other"><div class="price"><span class="y fl">¥ </span><span class="n fl">${tuan.price}</span><span class="o fl">${tuan.old_price}</span></div><div class="bt"><a href="group_content.action?id=${tuan.id}" target="_blank">我要团购</a></div></div>
      </li>
      </s:iterator> 
	</ul>
  </div>
</div>
<!--团购活动结束--> 

<div class="t_container oh">
<!--资讯开始-->
  <div class="container_half">
    <div class="cheader"><h4 class="eight"></h4><span class="eight"><a href="newsinfolistshow.action" target="_blank">更多+</a></span></div>
    <div class="NewsList">
      <ul>
        <s:iterator value="newsList" var="news" status="t">
      	<li><span>${news.releaseTime}</span><a href="shownews_content.action?id=${news.id}" target="_blank">${news.title}</a></li>
        </s:iterator>
      </ul>
    </div>
  </div>
<!--资讯结束-->
<!--评价开始-->  
  <div class="container_half oh ml24">
    <div class="cheader"><h4 class="nine"></h4></div>
    <!-- 多说最新评论 start -->
    <div class="ds-recent-comments" data-num-items="10" data-show-avatars="1" data-show-time="1" data-show-title="1" data-show-admin="1" data-excerpt-length="500"></div>
    <!-- 多说最新评论 end -->
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
<!--评价结束-->
</div>

  	
</div>
<!--main end-->
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ include file="../../taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = (String)request.getAttribute("id");
String pageIndex = request.getParameter("pageIndex");
String code = (String)request.getAttribute("code");
System.out.println("--------------code----"+code);
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>摄影作品 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/list.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/jqthumb/jqthumb.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script><!--photo-->
<script type="text/javascript">$(function(){var fix = $("#Header");fix.scrollFix();$('.nav .two').addClass('cur');});</script><!—header—>
<script type="text/javascript">
	$(document).ready(function(){
		list_init(1);
		page_init();
	});
	function getCode(_code){
		list_init(1, _code);
		page_init(_code);
	}
	function page_init(_code){
		$("#paging").pagination({
	        items: $("#count_id").val(),
	        itemsOnPage: 1,
	        onPageClick: function(pageNumber, event) {
	        	if(_code)
	        		list_init(pageNumber, _code);
	        	else
					list_init(pageNumber);
			}
	    });
	}
	function list_init(page, _code){
		var _url = "";
		if(_code){
			_url = "ajaxPhotoTopicShowbyCode.action";
		} else {
			_code = "<%=code%>";
			_url = "ajaxPhotosTopicnewShow.action";
		}
		$.ajax({
			url : _url,
			data : {"code": _code, "pageIndex":page}, //以键/值对的形式
			async : false,
			dataType : "json",
			success : function(data) {
				if(data.pageCount < 1) $("#show_page").hide();
				$("#count_id").val(data.pageCount);
				$("#PList").empty();
				for (i in data.rows){
					var list_div = "";
					var j = parseInt(i) + 1;
					if(j % 5 == 0){
						list_div += "<li class='respl-item'><div class='unit'>";
					} else {
						list_div += "<li class='respl-item mr15'><div class='unit'>";
					}
					list_div += "<div class='unit'><a class='images' href='photoListforTopic.action?id="+data.rows[i].id+"' target='_blank'><img class='lazy' src='" + data.rows[i].url + "' /><div class='overLayer'></div></a><h5><a href='photoListforTopic.action?id="+data.rows[i].id+"' target='_blank'>"+data.rows[i].name+"</a></h5>";
                	list_div += "<dl><dd>摄影师："+data.rows[i].cameraman+"</dd><dd>造型师："+data.rows[i].stylists+"</dd><dd>拍摄时间："+data.rows[i].shotTime+"</dd></dl><div class='view'><a href='photoListforTopic.action?id="+data.rows[i].id+"'>查看</a></div></div>";
					$("#PList").append(list_div);
				}
			}
		});
		$(".PList li").hover(function(){
		     $(this).find(".hoverline").fadeIn().end()
			 .find(".images").addClass("mousehover").stop().animate({
			     "height":"180px"
		     },500).find("img").stop().animate({
     			"margin-top":"-30px"
	     	 },500);
		     $(this).find(".overLayer").stop().animate({
		     	"margin-top":"-30px"
     		 },580);
		     $(this).find(".view").stop().animate({
		     	"bottom":"0"	
     		 },900);
			 
	     },function(){
		     $(this).find(".line").fadeOut().end()
			 .find(".images").removeClass("mousehover").stop().animate({
	     		"height":"228px"
	     	 },500).find("img").stop().animate({
	     		"margin-top":"0"
	     	 },500);
	     	$(this).find(".overLayer").stop().animate({
	     		"margin-top":"0"
	     	 },580);
	     	$(this).find(".view").stop().animate({
	     		"bottom":"-50px"
	     	 },700);
	     });
	     
	     
		$("#PList li img").jqthumb({
			width:228,
			height:228,
			after: function(imgObj){
				imgObj.css('opacity', 0).animate({opacity: 1}, 2000);
			}
		});
		$("#PList li .rsp").hide();	
		$("#PList li").hover(function(){
			$(this).find(".rsp").stop().fadeTo(500,0.5)
			$(this).find(".text").stop().animate({left:'0'}, {duration: 500})
		},
		function(){
			$(this).find(".rsp").stop().fadeTo(500,0)
			$(this).find(".text").stop().animate({left:'228'}, {duration: "fast"})
			$(this).find(".text").animate({left:'-228'}, {duration: 0})
		});
	}
</script>
</head>
<body>
<!--header start-->
<jsp:include page="top.jsp"></jsp:include>
<!--header end-->

<div id="main">
  <div class="container">
   <div id="Photography">
     <script type="text/javascript">$(document).ready(function() {$(".plist").hide();$("ul.Ccolumn li:first").addClass("active").show();$(".plist:first").show();$("ul.Ccolumn li").click(function() {$("ul.Ccolumn li").removeClass("active");$(this).addClass("active");$(".plist").hide();var activeTab = $(this).find("a").attr("href");$(activeTab).fadeIn(); return false;});});</script>
     <div class="cheader"><h4 class="two"></h4>
     <ul class="Ccolumn column"><%--
     <li><a href="javascript:void(0);" onclick="getCode()">最新</a></li>--%>
     	<s:iterator value="photoworksTypeTagList" var="photo" status="t">
     		<li>
     		<a href="javascript:void(0);" onclick="getCode('${photo.code}')">${photo.text }</a>
     		</li>
     	</s:iterator>
     </ul>
     </div>
     <!--list start-->
     <div class="PhotographyList">
         <ul class="PList respl-items">
            <div id="PList"></div>
           	<input type="hidden" id="count_id" />
	     </ul>
	     <div id="show_page"><jsp:include page="page.jsp"></jsp:include></div>
	 </div>
    </div>
  </div>
</div>
<!--main end-->
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>
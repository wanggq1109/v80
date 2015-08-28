<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = (String)request.getAttribute("id");
String pageIndex = request.getParameter("pageIndex");
String code = (String)request.getAttribute("code");
System.out.println("--------------code----"+code);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>热门景点 - 首页</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/list.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=basePath%>goldfish/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/jqthumb/jqthumb.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script><!--photo-->
<script type="text/javascript">$(function(){var fix = $("#Header");fix.scrollFix();$('.nav .six').addClass('cur');});</script><!--header-->
<script type="text/javascript">$(function(){var fixbottom = $(".Hcolumn");fixbottom.scrollFix({distanceTop:100,startTop:"#startTop",endPos:480});});</script>
<script type="text/javascript">
	$(document).ready(function(){
		getCode('0901');
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
		$.ajax({
			url : "ajaxPhotoTopicShowbyCode.action",
			data : {"code": _code, "pageIndex":page}, //以键/值对的形式
			async : false,
			dataType : "json",
			success : function(data) {
				if(data.pageCount < 1) $("#show_page").hide();
				$("#count_id").val(data.pageCount);
				$("#HList").empty();
				for (i in data.rows){
					var list_div = "";
					var j = parseInt(i) + 1;
					if(j % 4 == 0){
						list_div += "<li class='respl-item'>";
					} else {
						list_div += "<li class='respl-item mr15'>";
					}
					list_div += "<div class='unit'><a class='images' href='hotspotscontent_init.action?id="+data.rows[i].id+"' title='"+data.rows[i].name+"' target='_blank'><img class='lazy' src='" + data.rows[i].url + "' /><div class='overLayer'></div></a><h5><a href='hotspotscontent_init.action?id="+data.rows[i].id+"' title='"+data.rows[i].name+"' target='_blank'>"+data.rows[i].name+"</a></h5>";
                	list_div += "<dl><dd>摄影师："+data.rows[i].cameraman+"</dd><dd>造型师："+data.rows[i].stylists+"</dd><dd>拍摄时间："+data.rows[i].shotTime+"</dd></dl><div class='view'><a href='hotspotscontent_init.action?id="+data.rows[i].id+"'>查看</a></div></div>";
					$("#HList").append(list_div);
				}
			}
		});
	$('#HList img').jqthumb({
		width:228,
		height:228,
		after: function(imgObj){
			imgObj.css('opacity', 0).animate({opacity: 1}, 2000);
		}
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
     		<div class="cheader"><h4 class="four"></h4></div>
    		<!--list start-->
    		<div id="HotspotsList">
     		<script type="text/javascript">$(document).ready(function() {$(".hlist").hide();$("ul.Hcolumn li:first").addClass("active").show();$(".hlist:first").show();$("ul.Hcolumn li").click(function() {$("ul.Hcolumn li").removeClass("active");$(this).addClass("active");$(".hlist").hide();var activeTab = $(this).find("a").attr("href");$(activeTab).fadeIn(); return false;});});</script>
			<script>
		       jQuery(document).ready(function($){
			     $(".HList li").hover(function(){
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
		     });
			 </script>
      			<div class="leftbar">
      				<ul class="Hcolumn">
      					<s:iterator value="hotspotsTagList" var="tags" status="t">
      					<li><a href="javascript:void(0);" onclick="getCode('${tags.code}')">${tags.text}</a></li>
      					</s:iterator>
      				</ul>
      			</div>
      			<div class="rightbar">
          			<div class="allProduct respl-header">
            			<ul class="HList respl-items" id="HList">
           					<input type="hidden" id="count_id" />
           				</ul>
          			</div>
          			<div id="show_page"><jsp:include page="page.jsp"></jsp:include></div>
      			</div>
    		</div>
    		<!--list end-->
   		</div>
  	</div>   
</div>
<!--main end-->

<jsp:include page="foot.jsp"></jsp:include>

</body>
</html>
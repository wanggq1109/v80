<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = (String)request.getAttribute("id");
String code = (String)request.getAttribute("code");
System.out.println("--------------code----"+code);
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>礼服欣赏 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/list.css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="<%=basePath%>goldfish/js/jqthumb/jqthumb.js" type="text/javascript"></script>
<script src="<%=basePath%>goldfish/js/scrollfix.min.js" type="text/javascript"></script><!--photo-->
<script type="text/javascript">$(function(){var fix = $("#Header");fix.scrollFix();$('.nav .three').addClass('cur');});</script><!--header-->
<script>
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
				$("#pl").empty();
				for (i in data.rows){
					var list_div = '';
					var j = parseInt(i) + 1;
					if(j % 4 == 0){
						list_div += "<li class='mb50'>";
					} else {
						list_div += "<li class='mr16 mb50'>";
					}
					list_div += "<a href='dressphotoListforTopic.action?id="+data.rows[i].id+"' title='"+data.rows[i].name+"' target='_blank'><img src='" + data.rows[i].url + "'/><div class='overLayer'></div><div class='infoLayer'><h3>《"+data.rows[i].name+"》</h3><p>"+data.rows[i].photosize+"张</p></div></a></li>";
					$("#pl").append(list_div);
				}
			}
		});
		$(".CanonicalsList li img").jqthumb({
			width:288,
			height:432,
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
<!--main start-->
<div id="main">
  <div class="container">
    <div id="Canonicals">
    <!--list start-->
	  <script type="text/javascript">$(document).ready(function() {$(".clist").hide();$("ul.Ccolumn li:first").addClass("active").show();$(".clist:first").show();$("ul.Ccolumn li").click(function() {$("ul.Ccolumn li").removeClass("active");$(this).addClass("active");$(".clist").hide();var activeTab = $(this).find("a").attr("href");$(activeTab).fadeIn(); return false;});});</script>
      <div class="cheader"><h4 class="six"></h4><ul class="Ccolumn column">
      
      <li><a href="javascript:void(0);" onclick="getCode()">最新</a></li>
      	<s:iterator value="dressTypeTagList" var="photo" status="t">
      		<li><a href="javascript:void(0);" onclick="getCode('${photo.code}')">${photo.text }</a></li>
      	</s:iterator>
      </ul>
      </div>
      <div class="CanonicalsList">
        <div class="Clist">
          	<ul id="pl"></ul>
      		<input type="hidden" id="count_id" />
     	  <div id="show_page"><jsp:include page="page.jsp"></jsp:include></div>
        </div>
        
          <!--
   		<div id="Canonicals">
    		<div class="CanonicalsList">
    			<ul id="pl">
	       			<div class="classifybox mr16">
		        		<div class="cheader"><h1>全部</h1><p>${count}组</p></div>
		        		<div class="classify">
		        			<a href="javascript:void(0);" onclick="getCode()">最新</a>/
		        			<a href="javascript:void(0);" onclick="getCode('0201')">传统</a>/
		        			<a href="javascript:void(0);" onclick="getCode('0202')">现代</a>
		        		</div>
	       			</div>
	       			<div id="_list"></div>
      			</ul>
      			<input type="hidden" id="count_id" />
     		</div>
     		<div id="show_page"><jsp:include page="page.jsp"></jsp:include></div>
   		</div>
   		-->
  	  </div>
    </div>
  </div>
</div>
<!--main end-->

<jsp:include page="foot.jsp"></jsp:include>

</body>
</html>
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
<title>80新闻 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/list.css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="<%=basePath%>goldfish/js/scrollfix.min.js" type="text/javascript"></script>
<script type="text/javascript">$(function(){var fix = $("#Header");fix.scrollFix();})</script>
<script>
	$(document).ready(function(){
		//$('.nav .six').addClass('cur');
        list_init(1);
		$("#paging").pagination({
	        items: $("#count_id").val(),
	        itemsOnPage: 1,
	        onPageClick: function(pageNumber, event) {
				list_init(pageNumber);
			}
	    });
	});
	function list_init(page){
		var data_url = "";
		var data_title = "";
		var data_content = "";
		$.ajax({
			url:"ajaxnewsInfopage.action",
			data:{"pageIndex":page}, //以键/值对的形式
			async : false,
			dataType : "json",
			success : function(data) {
				if(data.pageCount < 1) $("#show_page").hide();
				$("#count_id").val(data.pageCount);
				$(".NewsList ul").empty();
				for (i in data.rows){
					if(data.rows[i].url == "")
						data_url = "goldfish/images/imgbg_03.png";
					else 
						data_url = data.rows[i].url;
					var list_div = "";
					var j = parseInt(i) + 1;
					if(j % 2 == 0) 
						list_div += "<li>";
					else
						list_div += "<li class='mr16'>";
						
					if(data.rows[i].title.length > 16){
						data_title =  data.rows[i].title.substr(0, 16) + "...";
					} else {
						data_title =  data.rows[i].title;
					}	
							
					if(data.rows[i].content.length > 16){
						data_content =  data.rows[i].content.substr(0, 16) + "...";
					} else {
						data_content =  data.rows[i].content;
					}	
					list_div += "<a href='shownews_content.action?id=" + data.rows[i].id + "' target='_blank'><img src='" + data_url + "' /><div class='overLayer'></div>";
					list_div += "<div class='title'>" + data_title + "</div><div class='other'>发布时间：" + data.rows[i].releaseTime + "<br>" + data.rows[i].descr + "</div></a></li>";
					$(".NewsList ul").append(list_div);
				}
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
    <div class="cheader"><h4 class="eight"></h4></div>
    <div id="News">
      <div class="NewsList">
	    <ul></ul>
	    <input type="hidden" id="count_id" />
	    <div id="show_page"><jsp:include page="page.jsp"></jsp:include></div>
	  </div>
    </div>
  </div>
</div>
<!--main end-->

<jsp:include page="foot.jsp"></jsp:include>

</body>
</html>
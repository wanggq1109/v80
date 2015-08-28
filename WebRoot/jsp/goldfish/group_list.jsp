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
<title>团购活动 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/list.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/jqthumb/jqthumb.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script><!--photo-->
<script type="text/javascript">$(function(){var fix = $("#Header");fix.scrollFix();})</script>
<script>
	$(document).ready(function(){
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
		$.ajax({
			url:"ajaxGroupItemInfopage.action",
			data:{"pageIndex":page}, //以键/值对的形式
			async : false,
			dataType : "json",
			success : function(data) {
				if(data.pageCount < 1) $("#show_page").hide();
				var data_url = "";
				$("#count_id").val(data.pageCount);
				$("#tuan_list").empty();
				for (i in data.rows){
					if(data.rows[i].url == "")
						data_url = "goldfish/images/imgbg_03.png";
					else 
						data_url = data.rows[i].url;
					var list_div = "";
					var j = parseInt(i) + 2;
					if(j % 5 == 0){
						list_div += "<li class='l'>";
					} else {
						list_div += "<li>";
					}
					list_div += "<a href='group_content.action?id=" + data.rows[i].id + "'><img src='" + data_url + "' /><div class='intro'><h3><a href='group_content.action?id=" + data.rows[i].id + "'>《" + data.rows[i].title + "》</a></h3><p>" + data.rows[i].descr + "</p></div>";
					list_div += "<div class='other'><div class='price'><span class='y fl'>¥ </span><span class='n fl'>" + data.rows[i].price + "</span><span class='o fl'>" + data.rows[i].old_price + "</span></div><div class='bt'><a href='group_content.action?id="+data.rows[i].id+"'>我要团购</a></div></div></li>";
					$("#tuan_list").append(list_div);
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
    <div id="Group">
      <div class="cheader"><h4 class="seven"></h4></div>
      <div class="GroupList">
        <ul>
	      <div id="tuan_list"></div>
	    </ul>	
        <input type="hidden" id="count_id" />
      </div>
      <div id="show_page"><jsp:include page="page.jsp"></jsp:include></div>
      </div>
   	</div>
  </div>
</div>
<!--main end-->

<jsp:include page="foot.jsp"></jsp:include>

</body>
</html>
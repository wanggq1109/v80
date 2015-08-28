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
<title>微电影 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/list.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/jqthumb/jqthumb.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script><!--photo-->
<script type="text/javascript">$(function(){var fix = $("#Header");fix.scrollFix();$('.nav .four').addClass('cur');});</script><!--header-->
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
			url:"ajaxforMicroFilmPage.action",
			data:{"pageIndex":page}, //以键/值对的形式
			async : false,
			dataType : "json",
			success : function(data) {
				if(data.pageCount < 1) $("#show_page").hide();
				$("#count_id").val(data.pageCount);
				$("#film_list").empty();
				for (i in data.rows){
					var list_div = "";
					var j = parseInt(i) + 1;
					if(j % 3 == 0){
						list_div += "<li>";
					} else {
						list_div += "<li class='mr27'>";
					}
					list_div += "<video id='' class='video-js vjs-list-skin' controls preload='none' width='380' height='235' poster='" + data.rows[i].suolueurl + "' data-setup='{}'>";
					list_div += "<source src='" + data.rows[i].url + "' type='video/mp4' /><source src='" + data.rows[i].url + "' type='video/webm' /><source src='" + data.rows[i].url + "' type='video/ogg' />";
					list_div += "<track kind='captions' src='demo.captions.vtt' srclang='en' label='English'><track kind='subtitles' src='demo.captions.vtt' srclang='en' label='English'></track></video></div>";
					list_div += "<div class='intro'><h3><a href='javascript:void(0)'>《" + data.rows[i].name + "》</a></h3><p>" + data.rows[i].shootingDate + "</p><p>" + data.rows[i].intro + "</p></div>";
					$("#film_list").append(list_div);
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
   <div id="Microfilm">
    <!--list start-->
    <script>$(function(){$('.CanonicalsList li img').jqthumb({width:380,height:235,after: function(imgObj){imgObj.css('opacity', 0).animate({opacity: 1}, 2000);}});});</script>
    <div class="cheader"><h4 class="five"></h4></div>
    <!--
    <div class="MicrofilmList">
      <ul id="pl">
        <li class="mr25"><img src="images/3-1.jpg"/><div class="intro"><h3><a title="" href="#">《浪漫情人岛》</a></h3><p>拍摄时间：2014.04.10</p><p>祝福来自厦门的林先生夫妇，本站所有作品均来自真实顾客，严禁转载使用。祝福来自厦门的林先生夫妇，本站所有作品均</p></div></li>
        <li class="mr25"><img src="images/3-1.jpg"/><div class="intro"><h3><a title="" href="#">《浪漫情人岛》</a></h3><p>拍摄时间：2014.04.10</p><p>祝福来自厦门的林先生夫妇，本站所有作品均来自真实顾客，严禁转载使用。祝福来自厦门的林先生夫妇，本站所有作品均</p></div></li>
        <li><img src="images/3-1.jpg"/><div class="intro"><h3><a title="" href="#">《浪漫情人岛》</a></h3><p>拍摄时间：2014.04.10</p><p>祝福来自厦门的林先生夫妇，本站所有作品均来自真实顾客，严禁转载使用。祝福来自厦门的林先生夫妇，本站所有作品均</p></div></li>
        <li class="mr25"><img src="images/3-1.jpg"/><div class="intro"><h3><a title="" href="#">《浪漫情人岛》</a></h3><p>拍摄时间：2014.04.10</p><p>祝福来自厦门的林先生夫妇，本站所有作品均来自真实顾客，严禁转载使用。祝福来自厦门的林先生夫妇，本站所有作品均</p></div></li>
        <li class="mr25"><img src="images/3-1.jpg"/><div class="intro"><h3><a title="" href="#">《浪漫情人岛》</a></h3><p>拍摄时间：2014.04.10</p><p>祝福来自厦门的林先生夫妇，本站所有作品均来自真实顾客，严禁转载使用。祝福来自厦门的林先生夫妇，本站所有作品均</p></div></li>
        <li><img src="images/3-1.jpg"/><div class="intro"><h3><a title="" href="#">《浪漫情人岛》</a></h3><p>拍摄时间：2014.04.10</p><p>祝福来自厦门的林先生夫妇，本站所有作品均来自真实顾客，严禁转载使用。祝福来自厦门的林先生夫妇，本站所有作品均</p></div></li>
      </ul>
    </div>
   	 -->
    		<div class="MicrofilmList">
	    		<ul id="pl">
	       			<div id="film_list"></div>
         		</ul>
         		<input type="hidden" id="count_id" />
     		</div>
     		<div id="show_page"><jsp:include page="page.jsp"></jsp:include></div>
  	</div>
  </div>
</div>
<!--main end-->

<jsp:include page="foot.jsp"></jsp:include>

</body>
</html>
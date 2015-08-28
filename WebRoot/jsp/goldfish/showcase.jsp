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
<title>客片 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/list.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/jqthumb/jqthumb.js"></script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("ul.Ccolumn li:first").addClass("active").show();
		$("ul.Ccolumn li").click(function() {
			$("ul.Ccolumn li").removeClass("active");
			$(this).addClass("active");
			var activeTab = $(this).find("a").attr("href");
			$(activeTab).fadeIn(); 
			return false;
		});

		$(".mone ul li").click(function() {
			$(".mone ul li").removeClass("selected");
			$(this).addClass("selected");
			var activeTab = $(this).find("a").attr("href");
			$(activeTab).fadeIn(); 
			return false;
		});

		var ym;
		var fix = $("#Header");
		fix.scrollFix();
		$('.nav .five').addClass('cur');
		page_init();
		
		var dt = new Date();
		y = dt.getFullYear();//获取年(四位)
		m = dt.getMonth()+1;//获得月份
		if(m<10) m = "0" + m;
		var year = $(".column li:nth-of-type(1) a");
		var last_year = $(".column li:nth-of-type(2) a");
		year.html(y);
		last_year.html(y-1);
		year.click(function(){
			$(".month li:nth-of-type(1)").trigger("click");
			ym = year.html() + "-01";
			ajax_data(ym,1);
		});
		last_year.click(function(){
			$(".month li:nth-of-type(1)").trigger("click");
			ym = last_year.html() + "-01";
			ajax_data(ym,1);
		});
		var ym_init = year.html() + "-" + m;
		$(".month li:nth-of-type("+m+")").trigger("click");
		ajax_data(ym_init,1);
		$(".month li:nth-of-type(1)").click(function(){ ym = $(".active a").html() + "-01"; ajax_data(ym,1); });
		$(".month li:nth-of-type(2)").click(function(){ ym = $(".active a").html() + "-02"; ajax_data(ym,1); });
		$(".month li:nth-of-type(3)").click(function(){ ym = $(".active a").html() + "-03"; ajax_data(ym,1); });
		$(".month li:nth-of-type(4)").click(function(){ ym = $(".active a").html() + "-04"; ajax_data(ym,1); });
		$(".month li:nth-of-type(5)").click(function(){ ym = $(".active a").html() + "-05"; ajax_data(ym,1); });
		$(".month li:nth-of-type(6)").click(function(){ ym = $(".active a").html() + "-06"; ajax_data(ym,1); });
		$(".month li:nth-of-type(7)").click(function(){ ym = $(".active a").html() + "-07"; ajax_data(ym,1); });
		$(".month li:nth-of-type(8)").click(function(){ ym = $(".active a").html() + "-08"; ajax_data(ym,1); });
		$(".month li:nth-of-type(9)").click(function(){ ym = $(".active a").html() + "-09"; ajax_data(ym,1); });
		$(".month li:nth-of-type(10)").click(function(){ ym = $(".active a").html() + "-10"; ajax_data(ym,1); });
		$(".month li:nth-of-type(11)").click(function(){ ym = $(".active a").html() + "-11"; ajax_data(ym,1); });
		$(".month li:nth-of-type(12)").click(function(){ ym = $(".active a").html() + "-12"; ajax_data(ym,1); });
	});
	function page_init(){
		$("#paging").pagination({
	        items: $("#count_id").val(),
	        itemsOnPage: 1,
	        onPageClick: function(pageNumber, event) {
	        	list_init(pageNumber);
			}
	    });
	}
	function ajax_data(ym,page){
		$.ajax({
			url : "ajaxCustomerPhotoShowbyMonth.action",
			data : {"ym": ym, "pageIndex":page}, //以键/值对的形式
			async : false,
			dataType : "json",
			success : function(data) {
				console.log(data);
				//if(data.pageCount < 1) $("#show_page").hide();
				$("#count_id").val(data.pageCount);
				$(".kpone").empty();
				for (i in data.rows){
					var list_div = "";
					var j = parseInt(i) + 1;
					if(j % 5 == 0){
						list_div += "<li class='f'>";
					} else {
						list_div += "<li>";
					}
					list_div += "<a href='showcase_content_init.action?id="+data.rows[i].id+"' title='"+data.rows[i].customerName+"' target='_blank'><img src='"+data.rows[i].url+"' /><div class='overLayer'></div><div class='intro'><div class='time'>";
					list_div += "<span class='d'>"+data.rows[i].cDate+"</span><span class='m'>"+data.rows[i].cMonth+"</span></div>";
					list_div += "<div class='title'><h6>"+data.rows[i].customerName+"</h6><span>wedding photography</span></div></div></a></li>";
					$(".kpone").append(list_div);
				}
			}
		});
	$('.kp img').jqthumb({
		width:228,
		height:342,
		after: function(imgObj){
			imgObj.css('opacity', 0).animate({opacity: 1}, 2000);
		}
	});
	}
</script>
<script>
	
</script>
</head>
<body>
<!--header start-->
<jsp:include page="top.jsp"></jsp:include>
<!--header end-->
<script src="<%=basePath%>goldfish/js/superslide.2.1.js" type="text/javascript"></script><!--幻灯片-->
<script type="text/javascript">
jQuery(".fullSlide").hover(function() {
    jQuery(this).find(".prev,.next").stop(true, true).fadeTo("show", 1)},
function() {
    jQuery(this).find(".prev,.next").fadeOut()});
jQuery(".fullSlide").slide({
    titCell: ".hd ul",
    mainCell: ".bd ul",
    effect: "fold",
    autoPlay: true,
    autoPage: true,
    trigger: "click",
    startFun: function(i) {
        var curLi = jQuery(".fullSlide .bd li").eq(i);
        if ( !! curLi.attr("_src")) {
            curLi.css("background-image", curLi.attr("_src")).removeAttr("_src")
        }
    }});
</script>
<!--main start-->
<div id="main">
  	<div class="container">
    	<div id="kepian">
     	<!--list start-->
	  	
	     	<div class="cheader">
	     		<h4 class="three"></h4>
	     		<ul class="Ccolumn column">
	     			<li><a href="javascript:void(0);)"></a></li>
	     			<li><a href="javascript:void(0);"></a></li>
	     		</ul>
	     	</div>
	     	<div class="kplist">
	      		<div class="month mone">
	        		<ul>
	        			<li><a href="#o_one"><span class="date">1月</span><span class="en">January</span></a></li>
				        <li><a href="#o_two"><span class="date">2月</span><span class="en">February</span></a></li>
				        <li><a href="#o_three"><span class="date">3月</span><span class="en">March</span></a></li>
				        <li><a href="#o_four"><span class="date">4月</span><span class="en">April</span></a></li>
				        <li><a href="#o_five"><span class="date">5月</span><span class="en">May</span></a></li>
				        <li><a href="#o_six"><span class="date">6月</span><span class="en">June</span></a></li>
				        <li><a href="#o_seven"><span class="date">7月</span><span class="en">July</span></a></li>
				        <li><a href="#o_eight"><span class="date">8月</span><span class="en">August</span></a></li>
				        <li><a href="#o_nine"><span class="date">9月</span><span class="en">September</span></a></li>
				        <li><a href="#o_ten"><span class="date">10月</span><span class="en">October</span></a></li>
				        <li><a href="#o_eleven"><span class="date">11月</span><span class="en">November</span></a></li>
				        <li><a href="#o_twelve"><span class="date">12月</span><span class="en">December</span></a></li>
	        		</ul>
	      		</div>
	       		<div class="kp">
	       		  <ul class="kpone">
	       		  </ul>
	       		</div>
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
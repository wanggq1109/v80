<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.arthouse.common.util.TopUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="com.arthouse.common.domain.PhtotoGraphyWroks" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    
     
     List<PhtotoGraphyWroks> slideList = TopUtils.getInstatnce().getallslides();
     
     request.setAttribute("slideList",slideList);

%>
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<!--header start-->
<div id="Header">
  <div class="bg"></div> 
  <div class="nav">                                        
    <ul class="dh f16">
      <div id="Logo"><a href="#"><img src="goldfish/images/logo.png" /></a></div>
	    <li class="one"><a href="<%=basePath%>index.action">首页</a></li>
	    <li class="two"><a href="<%=basePath%>photography_list.action">作品赏析</a></li>
        <li class="six"><a href="<%=basePath%>hotspotsshow_init.action">热门景点</a></li>
        <li class="five"><a href="<%=basePath%>showcase.action">客片赏析</a></li>
	    <li class="three"><a href="<%=basePath%>dressshowmenu.action">礼服欣赏</a></li>
	    <li class="four"><a href="<%=basePath%>microfilm_init.action">微电影</a></li>
	    <li class="eight"><a href="<%=basePath%>studio_service.action">摄影服务</a></li>
	    <li class="seven"><a href="<%=basePath%>studioInfo.action">工作室简介</a></li>
    </ul>
  </div>
</div>
<!--header end-->
<!--slide start-->
<div class="fullSlide">
  <div class="bd">
    <ul>
   <s:iterator value="%{#request.slideList}" var="slides" status="t">
      <li _src="url(<%=basePath%>${slides.photoUrl})"><a target="_blank" href="#"></a></li>
   </s:iterator>
    </ul>
  </div>
  <div class="hd"><ul></ul></div>
  <span class="prev"></span><span class="next"></span>
</div>
<!--slide end-->
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
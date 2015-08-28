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
<title>80视觉招聘 - 80视觉</title>
<meta name="keywords" content="{$SEO['keyword']}">
<meta name="description" content="{$SEO['description']}">
<link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
<link href="<%=basePath%>goldfish/css/base.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>goldfish/css/studio.css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>goldfish/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script>
	$(document).ready(function(){
		$('.nav .seven').addClass('cur');
	});
</script>
<script type="text/javascript" src="<%=basePath%>goldfish/js/scrollfix.min.js"></script>
<script type="text/javascript">
		$(function(){
			var fix = $("#Header");
			fix.scrollFix();
		})
</script>
</head>
<body>
<!--header start-->
<jsp:include page="top.jsp"></jsp:include>
<!--header end-->
<!--main start-->
<div id="main">
  <div class="container">
   <div id="recruit">
    <div class="header"></div>
    <ul class="recruitlist">
     <li><div class="c fl"><h3>摄影师</h3><p>1.具有优秀的职业道德，良好的行业素质，正直善良，积极乐观；<br>2.要求4年以上婚纱人像摄影工作经验，摄影功底深厚<br>3.相貌端庄，年龄24岁以上，男女均可<br>4.熟悉外景拍摄技巧，熟练运用摄影器材及图片处理软件，PS及 NX-2<br>5.工作认真细致、积极、主动，有责任感，良好的沟通表达能力及亲和力；</p></div><div class="num fl">2<span>名</span></div></li>
     <li><div class="c fl"><h3>摄影师</h3><p>1.具有优秀的职业道德，良好的行业素质，正直善良，积极乐观；<br>2.要求4年以上婚纱人像摄影工作经验，摄影功底深厚<br>3.相貌端庄，年龄24岁以上，男女均可<br>4.熟悉外景拍摄技巧，熟练运用摄影器材及图片处理软件，PS及 NX-2<br>5.工作认真细致、积极、主动，有责任感，良好的沟通表达能力及亲和力；</p></div><div class="num fl">2<span>名</span></div></li>
     <li><div class="c fl"><h3>摄影师</h3><p>1.具有优秀的职业道德，良好的行业素质，正直善良，积极乐观；<br>2.要求4年以上婚纱人像摄影工作经验，摄影功底深厚<br>3.相貌端庄，年龄24岁以上，男女均可<br>4.熟悉外景拍摄技巧，熟练运用摄影器材及图片处理软件，PS及 NX-2<br>5.工作认真细致、积极、主动，有责任感，良好的沟通表达能力及亲和力；</p></div><div class="num fl">2<span>名</span></div></li>
     <li><div class="c fl"><h3>摄影师</h3><p>1.具有优秀的职业道德，良好的行业素质，正直善良，积极乐观；<br>2.要求4年以上婚纱人像摄影工作经验，摄影功底深厚<br>3.相貌端庄，年龄24岁以上，男女均可<br>4.熟悉外景拍摄技巧，熟练运用摄影器材及图片处理软件，PS及 NX-2<br>5.工作认真细致、积极、主动，有责任感，良好的沟通表达能力及亲和力；</p></div><div class="num fl">2<span>名</span></div></li>
     <li><div class="c fl"><h3>摄影师</h3><p>1.具有优秀的职业道德，良好的行业素质，正直善良，积极乐观；<br>2.要求4年以上婚纱人像摄影工作经验，摄影功底深厚<br>3.相貌端庄，年龄24岁以上，男女均可<br>4.熟悉外景拍摄技巧，熟练运用摄影器材及图片处理软件，PS及 NX-2<br>5.工作认真细致、积极、主动，有责任感，良好的沟通表达能力及亲和力；</p></div><div class="num fl">2<span>名</span></div></li>
    </ul>
   </div>
   
  </div>
</div>
<!--main end-->

<jsp:include page="foot.jsp"></jsp:include>

</body>
</html>
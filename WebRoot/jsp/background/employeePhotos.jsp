<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String typeCode = (String) request.getAttribute("code");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>员工作品列表</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/baguettebox/baguettebox.min.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/baguettebox/lrtk.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/baguettebox/baguettebox.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqthumb/jqthumb.js"></script>
<script>
$(function(){
	$('.baguetteBoxOne img').jqthumb({
		width: 140,
		height: 140,
		after: function(imgObj){
			imgObj.css('opacity', 0).animate({opacity: 1}, 2000);
		}
	});
});
</script>
</head>
<body>
	<div class="baguetteBoxOne gallery">
		<s:iterator value="photosList" var="photos" status="t">
			<a href="${photos.url}" data-caption="${photos.employworkstopic.name}"><img src="${photos.suolue_url}"></a>
		</s:iterator>
		<script>
			baguetteBox.run('.baguetteBoxOne', {
				animation : 'fadeIn',
			});
		
			
		</script>
</body>
</html>



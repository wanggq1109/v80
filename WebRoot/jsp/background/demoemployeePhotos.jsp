<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String typeCode = (String)request.getAttribute("code");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>员工作品列表</title>

</head>
<body>
	<ul>
		<s:iterator value="photosList" var="photos" status="t">
			<img name="pic" src="<%=basePath%>${photos.suolue_url}" width="180" height="150"/>
		</s:iterator>
	</ul>
</body>
</html>



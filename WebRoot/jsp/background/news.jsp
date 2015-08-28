<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://ckfinder.com" prefix="ckfinder"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<! DOCTYPE   HTML  PUBLIC   "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href=" <%=basePath%> ">
<title>新闻</title>
</head>
<body>
	<form action="dotest.jsp" method="post">
		<textarea cols="80" id="editor1" name="editor1" rows="10"></textarea>
		<input type="submit" value="Submit" />
	</form>
	<ckfinder:setupCKEditor basePath="/v80/ckfinder/" editor="content" />
	<ckeditor:replace replace="editor1" basePath="/v80/ckeditor/" />

</body>
</html>

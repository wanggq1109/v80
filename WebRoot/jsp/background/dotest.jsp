<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>  
  <head>  
    <title>获取传递的数据 -- by jCuckoo</title>  
  </head>  
  <body>  
    <%request.setCharacterEncoding("UTF-8"); %>  
    ${newsinfo.content }
  </body>  
</html>  

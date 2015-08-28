<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/demo_defalt.js"></script>
</head>
<body class="easyui-layout">
<div region="center" style="padding:2px 2px 2px 2px;" border="false">
	<table id="demo_defalt" fit="true"></table>
</div>
<div id="div_add" title="新增" style="padding:10px;width:600px;height:200px;display:none;overflow:hidden;">
    <form id="form_add" method="post">
	<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100">
		<tr>
			<td align="right">ID：</td>
			<td align="left"><input type="text" name="id" id="add_id" /></td>
			<td align="right">姓名：</td>
			<td align="left"><input type="text" name="name" id="add_name_id" /></td>
		</tr>
		<tr>
			<td align="right">时间：</td>
			<td align="left"><input type="text" name="templatDate" id="add_templatDate_id" /></td>
		</tr>
	</table>
    </form>
</div>
<div id="div_edit" title="修改" style="padding:10px;width:600px;height:200px;display:none;overflow:hidden;">
    <form id="form_edit" method="post">
	<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100">
		<tr>
			<td align="right">ID：</td>
			<td align="left"><input type="text" name="id" id="edit_id" /></td>
			<td align="right">姓名：</td>
			<td align="left"><input type="text" name="name" id="edit_name_id" /></td>
		</tr>
		<tr>
			<td align="right">时间：</td>
			<td align="left"><input type="text" name="templatDate" id="edit_templatDate_id" /></td>
		</tr>
	</table>
    </form>
</div>
</body>
</html>



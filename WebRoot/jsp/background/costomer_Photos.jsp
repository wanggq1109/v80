<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.text.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	String id = (String)request.getAttribute("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客人照片</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.v2.0.3.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploadify/swfobject.js"></script>
<style>
#costomer_Edit img{border:3px solid #eeeeee;width:100px;height:75px;}
#enlarge_images{position:absolute;display:none;z-index:2;}
</style>
<script>
	$(function() {
		var id = "<%=id%>";
	 	table_init(id);
	});
	
	function table_init(id) {
		$("#costomer_Edit").datagrid({
			url : "myjson/customerphotosListshow.action?id="+ id,
			pagination : true,
			nowrap : false,
			striped : true,
			collapsible : true,
			loadMsg : "数据装载中,请稍后......",
			sortName : "id",
			sortOrder : "asc",
			remoteSort : true,
			fitColumns : true,
			idField : "id",
			height : 475,
			frozenColumns : [ [ { field : "ck", checkbox : true } ] ],
			columns : [ [
				{ field : "sl_url", title : "缩略图", width : 50, align : "center",
                	formatter : function(value, row, index) {
               			return "<img src='" + row.sl_url + "' height='60' />";
					}},
				{ field : "uploadTime", title : "图片上传时间", width : 80, align : "center" }
				 ] ],
			toolbar : [ {
				text : "设置封面",
				handler : function() {
					set_cover();
				}
			}, "-", {
				text : "删除",
				iconCls : "icon-remove",
				handler : function() {
					_del();
				}
			}, "-", {
				text : "刷新",
				iconCls : "icon-reload",
				handler : function() {
					window.location.reload();
				}
			} ]
		});
		showPager();
	}

	function showPager() {
		$("#costomer_Edit").datagrid("getPager").pagination({
			pageSize : 10,
			showPageList : false,
			showRefresh : false,
			displayMsg : "当前显示从{from}到{to}条，共{total}条记录",
			beforePageText : "第",
			afterPageText : "页，共{pages}页"
		});
	}
	
	// 设置封面
	function set_cover(){
		var rows = $("#costomer_Edit").datagrid("getSelected");
		var rowsLength = $("#costomer_Edit").datagrid("getSelections");
        if(rowsLength.length>1){
            $.messager.alert("设置","只能选择一条记录","warning");
        } else if(rowsLength.length==0){
            $.messager.alert("设置","请选择要设置的记录","warning");
        } else {
            if(rows){
                $.ajax({
					type : "POST",
					url : "myjson/customerphotossetfacephots.action",
					data : "id=" + rows.id,
					success : function(msg, status) {
						if ("001" == msg) {
							$.messager.alert("成功", "设置成功!", "info", function() {
								$("#costomer_Edit").datagrid("reload");
							});
						} else {
							$.messager.alert("错误", "设置失败!", "error");
						}
					}
				});
            }
        }
	}

	// 删除
	function _del() {
		var row = $("#costomer_Edit").datagrid("getSelected");
		if (row) {
			$.messager.confirm("删除", "确定删除？", function(r) {
				if (r) {
					var ids = [];
					var rows = $("#costomer_Edit").datagrid("getSelections");
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					id = ids.join(",");
					$.ajax({
						type : "POST",
						url : "myjson/customerphotosdelete.action",
						data : "id=" + id,
						success : function(msg, status) {
							if ("0000" == msg) {
								$.messager.alert("成功", "删除成功!", "info", function() {
									window.location.reload();
								});
							} else{
								
								$.messager.alert("错误", "删除失败!", "error");
							}
						}
					});
					$("#costomer_Edit").datagrid("clearSelections");
				}
			});
		} else {
			$.messager.alert("删除", "请选择要删除的行", "warning");
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'" style="padding:2px 2px 2px 2px;" border="false">
		<table id="costomer_Edit" fit="true"></table>
	</div>
</body>
</html>



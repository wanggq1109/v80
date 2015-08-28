<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.text.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	String typeCode = (String)request.getAttribute("code");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>幻灯片</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<script>
	$(function() {
	 	var id = '<%=typeCode%>';;	
	 	table_init(id);
	});

	var codeId = "<%=typeCode%>";

	function table_init(id) {
		$("#photoworks_table").datagrid({
			url : "myjson/slideShowData.action?code=" + id,
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
			height : 300,
			frozenColumns : [ [ { field : "ck", checkbox : true } ] ],
			columns : [ [
				{ field : "breviaryUrl", title : "幻灯片", width : 100, align : "center",
                	formatter : function(value, row, index) {
						return "<img src='" + row.suolueurl + "' height='60' />";
					}},
				{ field : "uploadTime", title : "上传时间", width : 80, align : "center" },
				{ field : "releaseStatus", title : "状态", width : 80, align : "center",
                	formatter : function(value, row, index) {
               			if(value == "已发布"){
							return "<span style='color:#75B468;'>已发布</span>";
						} else {
							return "<span style='color:red;'>未发布</span>";
						}
					}}
				] ],
			toolbar : [ {
				text : "上传",
				iconCls : "icon-add",
				handler : function() {
					_add();
				}
			}, "-", {
				text : "删除",
				iconCls : "icon-remove",
				handler : function() {
					_del();
				}
			}, "-", {
				text : "发布",
				handler : function() {
					_all_release();
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
		$("#photoworks_table").datagrid("getPager").pagination({
			pageSize : 10,
			showPageList : false,
			showRefresh : false,
			displayMsg : "当前显示从{from}到{to}条，共{total}条记录",
			beforePageText : "第",
			afterPageText : "页，共{pages}页"
		});
	}

	// dialog新增
	function dialog_add() {
		$("#div_add").show();
		$("#div_add").dialog({
			modal : true,
			iconCls : "icon-add",
			buttons : [{
				text : "关闭窗口",
				iconCls : "icon-ok",
				handler : function() {
					$("#photoworks_table").datagrid("reload");
					$("#div_add").dialog("close");
				}
			} ]
		});
	}


	// dialog查看
	function dialog_view() {
		$("#div_view").show();
		$("#div_view").dialog({
			modal : true,
			buttons : [ {
				text : "关闭",
				handler : function() {
					$("#div_view").dialog("close");
				}
			} ]
		});
	}
	// dialog查询
	function dialog_query() {
		$("#div_query").show();
		$("#div_query").dialog({
			modal : true,
			iconCls : "icon-search"
		});
	}
	// 新增
	function _add() {
		dialog_add();
		var url = "fileUpload.jsp?code=" + codeId;
		$("#iframeV80").attr("src", url);
	}
	// 查看
	function _add_desc() {
		dialog_add_desc();
	}
	function dialog_add_desc() {
		$("#code_desc").val(codeId);
		$("#div_add_desc").show();
		$("#div_add_desc").dialog({
			modal : true,
			iconCls : "icon-add",
			buttons : [{
				text : "保存",
				iconCls : "icon-ok",
				handler : function() {
					$("#form_add_desc").form("submit",{
						url : "",
						onSubmit : function() {
							var id = $("#add_id").val();
							var name = $("#add_name_id").val();
							var templatDate = $("#add_templatDate_id").val();
						},
						success : function(data) {
							if (data == 1) {
								$.messager.alert("成功", "保存成功!", "info", function() {
									$("#div_add_desc").dialog("close");
									$("#photoworks_table").datagrid("reload");
									$("#form_add").form("clear");
								});
							} else {
								$.messager.alert("错误", "保存失败!", "error");
								$("#div_add_desc").dialog("close");
								$("#form_add").form("clear");
							}
						}
					});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#div_add_desc").dialog("close");
					$("#form_add_desc").form("clear");
				}
			} ]
		});
	}
	// 删除
	function _del() {
		var row = $("#photoworks_table").datagrid("getSelected");
		if (row) {
			$.messager.confirm("删除", "确定删除？", function(r) {
				if (r) {
					var ids = [];
					var rows = $("#photoworks_table").datagrid("getSelections");
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					id = ids.join(",");
					$.ajax({
						type : "POST",
						url : "myjson/slideshow_delete.action",
						data : "id=" + id,
						success : function(msg, status) {
							if ("001" == msg) {
								$.messager.alert("成功", "删除成功!", "info", function() {
									window.location.reload();
								});
							} else {
								$.messager.alert("错误", "删除失败!", "error");
							}
						}
					});
					$("#photoworks_table").datagrid("clearSelections");
				}
			});
		} else {
			$.messager.alert("删除", "请选择要删除的行", "warning");
		}
	}
	
	
	// 发布
	function _all_release() {
		var row = $("#photoworks_table").datagrid("getSelected");
		if (row) {
			$.messager.confirm("发布", "确定需要发布吗？", function(r) {
				if (r) {
					var ids = [];
					var rows = $("#photoworks_table").datagrid("getSelections");
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					id = ids.join(",");
					$.ajax({
						type : "POST",
						url : "myjson/slideshow_release.action",
						data : "id=" + id,
						success : function(msg, status) {
							if ("001" == msg) {
								$.messager.alert("成功", "发布成功!", "info", function() {
									window.location.reload();
								});
							} else {
								$.messager.alert("错误", "发布失败!", "error");
							}
						}
					});
					$("#photoworks_table").datagrid("clearSelections");
				}
			});
		} else {
			$.messager.alert("发布", "请选择要发布的行", "warning");
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'" style="padding:2px 2px 2px 2px;" border="false">
		<table id="photoworks_table" fit="true"></table>
	</div>
	<div id="div_add" title="新增" style="padding:0px;width:800px;height:500px;display:none;overflow:hidden;">
		<iframe scrolling="auto" frameborder="0" src="" style="width:100%;height:99%;" id="iframeV80"></iframe>
	</div>
	<div id="div_edit" title="修改" style="padding:10px;width:600px;height:200px;display:none;overflow:hidden;">
		<form id="form_edit" method="post">
			<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100">
				<tr>
					<td align="right">ID：</td>
					<td align="left"><input type="text" name="id" id="edit_id" /></td>
					<td align="right">姓名：</td>
					<td align="left"><input type="text" name="name" id="edit_name_id" /> </td>
				</tr>
				<tr>
					<td align="right">时间：</td>
					<td align="left"><input type="text" name="templatDate" id="edit_templatDate_id" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="div_view" title="查看" style="padding:10px;width:600px;height:200px;display:none;overflow:hidden;">
		<form id="form_view" method="post">
			<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100" id="view">
				<tr>
					<td align="right">ID：</td>
					<td align="left"><input type="text" name="id" id="view_id_id" disabled="disabled" /></td>
					<td align="right">姓名：</td>
					<td align="left"><input type="text" name="name" id="view_name_id" disabled="disabled" /> </td>
				</tr>
				<tr>
					<td align="right">时间：</td>
					<td align="left"><input type="text" name="templatDate" id="view_templatDate_id" disabled="disabled" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>



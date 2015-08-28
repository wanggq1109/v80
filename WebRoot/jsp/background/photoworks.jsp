<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.text.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	String photoworkscode = (String)request.getAttribute("code");
	System.out.println("mycode---------"+photoworkscode);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>作品赏析</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.v2.0.3.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploadify/swfobject.js"></script>
<script>
	$(function() {
		if (id = "") {
			table_init();
		} else {
			table_init(id);
		}
		tree_init();
	});
	
	function file_init(id){
	    $("#file_upload1").uploadify({
	        'uploader'       : '<%=basePath%>js/uploadify/uploadify.swf',
	        'script'         : 'myjson/photoworkssuolue_picture.action',
	        'scriptData'     : {'id':id},
	        'cancelImg'      : '<%=basePath%>js/uploadify/cancel.png',
	        'folder'         : 'suolue_pic',
	        'queueID'        : 'fileQueue1',
	        'fileDataName'   : 'file_upload',
	        'auto'           : false,
	        'multi'          : false,
	        'simUploadLimit' : 1,
	        'buttonText'     : '浏览',
	        'height'         : 22,
	        'width'          : 70,
	        'buttonImg'      : '<%=basePath%>js/uploadify/browse.png',
	        'fileDesc'       : '请选择文件',
	        'fileExt'        : '*.jpg;*.png;*.gif;*.jpeg',
	        'sizeLimit'      : 0,
	        'sizeMax'        : 10240000,
	        'onSelect'       : function(e,queueId,fileObj){
	            //if(fileObj.size>10240000){
	            //   $.messager.alert('错误',"上传的图片不能大于10兆，请重新选择",'error');
	            //   $("#btnUpload"+id).attr('disabled','disabled');
	            //}
	        },'onComplete'	 : function(event,queueId,fileObj,response,data){
	            var msg = response.substr(0,4);
	            if(msg =="0000"){
	                $.messager.alert("成功","上传成功!","info",function (){
	                     $("#url").val(response.substr(4));
	                     $("#photoworks_table").datagrid("reload");
						 $("#div_brev").dialog("close");
	                 });
	            }else{
	               $.messager.alert("错误",response,"error");
	            }
	        }
	    });
	}

	var codeId = "";

	function table_init(id) {
		$("#photoworks_table").datagrid({
			url : "myjson/photoWorksDataList.action?code=" + id,
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
				{ field : "photoName", title : "原图名称", width : 130, align : "center" },
				{ field : "photoType", title : "作品类型", width : 100, align : "center" },
				{ field : "breviaryName", title : "缩略图名称", width : 100, align : "center" },
				{ field : "uploadTime", title : "原图上传时间", width : 80, align : "center" },
				{ field : "breviaryUploadTime", title : "缩略图上传时间", width : 80, align : "center" },
				{ field : "releaseStatus", title : "作品状态", width : 80, align : "center" },
				{ field : "opa", title : "操作", width : 80, align : "center",
					formatter : function(value, row, index) {
						return "<a class='add' href='javascript:add_brev(" + row.id + ")'>添加缩略图</a>";
					}
				} ] ],
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
				text : "添加描述",
				handler : function() {
					_add_desc();
				}
			}, "-", {
				text : "整体发布",
				handler : function() {
					_all_release();
				}
			}, "-", {
				text : "选择性发布",
				handler : function() {
					_release();
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

	// 添加缩略图
	function add_brev(id) {
		file_init(id);
		$("#div_brev").show();
		$("#div_brev").dialog({
			modal : true,
			iconCls : "icon-add"
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
		
			$("#div_add_desc").show();
			$("#div_add_desc").dialog({
				modal : true,
				iconCls : "icon-add",
				buttons : [{
					text : "保存",
					iconCls : "icon-ok",
					handler : function() {
						$("#form_add_desc").form("submit",{
							url : "myjson/photoWorksDesc.action?codeId="+codeId,
							onSubmit : function() {
								var id = $("#add_id").val();
								var name = $("#add_name_id").val();
								var templatDate = $("#add_templatDate_id").val();
							},
							success : function(msg, status) {
								if (msg == 1) {
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
	
	// 整体发布
	function _all_release(){
		alert(codeId);
		if(codeId != "" && codeId.length == 6){
			$.messager.confirm("发布", "确定发布所选中记录？", function(r) {
				if (r) {
					$.ajax({
						type : "POST",
						url : "myjson/photoWorks_release_all.action",
						data : "code=" + codeId,
						success : function(msg, status) {
							if ("001" == msg) {
								$.messager.alert("成功", "发布成功!", "info", function() {
									$("#photoworks_table").datagrid("reload");
								});
							} else {
								$.messager.alert("错误", "发布失败!", "error");
							}
						}
					});
				}
			});
		} else {
			$.messager.alert("提示", "请选择二级类型!", "info");
		}
	}
	
	// 选择性发布
	function _release() {
		var row = $("#photoworks_table").datagrid("getSelected");
		if (row) {
			$.messager.confirm("发布", "确定发布所选中记录？", function(r) {
				if (r) {
					var ids = [];
					var rows = $("#photoworks_table").datagrid("getSelections");
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					id = ids.join(",");
					$.ajax({
						type : "POST",
						url : "myjson/photoWorks_release.action",
						data : "id=" + id,
						success : function(msg, status) {
							if ("001" == msg) {
								$.messager.alert("成功", "发布成功!", "info", function() {
									$("#photoworks_table").datagrid("reload");
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
			$.messager.alert("发布", "请选择要发布的记录", "warning");
		}
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
						url : "myjson/photoWorks_delete.action",
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

	// 打开查询dialog
	function _query() {
		dialog_query();
		$("#query_name_id").focus();
	}

	// 查询
	function query_init() {
		var name = $("#query_name_id").val();
		var queryParams = $("#photoworks_table").datagrid("options").queryParams;
		queryParams.name = name;
		$("#photoworks_table").datagrid("reload");
		$("#div_query").dialog("close");
		$("#form_query").form("clear");
		showPager();
	}

	// 初始化树
	function tree_init() {
		var code = "<%=photoworkscode%>";
		$("#type_tree").tree({
			url : "myjson/photoWorksTreeData.action?code=" + code,
			onClick : function(node) {
				$(this).tree("toggle", node.target);
				var id = node.id;
				table_init(id);
				codeId = id;
			}
		});
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west'" split="true" style="padding:10px;width:200px; overflow:hidden;">
		<div id="type_tree" fit="true"></div>
	</div>
	<div data-options="region:'center'" style="padding:2px 2px 2px 2px;" border="false">
		<table id="photoworks_table" fit="false"></table>
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
	<div id="div_add_desc" title="添加描述" style="padding:10px;width:600px;height:300px;display:none;overflow:hidden;">
		<form id="form_add_desc" method="post">
			<input type="hidden" id="code_desc" name="code" />
			<table width="100%" cellpadding="3" cellspacing="5" border="0" height="100">
				<tr>
					<td align="right">拍摄地：</td>
					<td align="left"><input type="text" name="shotAddress" id="shotAddress"  /></td>
					<td align="right">拍摄时间：</td>
					<td align="left"><input type="text" name="shotTime" id="shotTime" /></td>
				</tr>
				<tr>
					<td align="right">摄影师：</td>
					<td align="left"><input type="text" name="cameraman" id="cameraman" /></td>
					<td align="right">造型师：</td>
					<td align="left"><input type="text" name="stylists" id="stylists" /></td>
				</tr>

				<tr>
					<td align="right">照片主题描述：</td>
					<td align="left" colspan="3">
						<textarea cols="57" rows="3" id="photGroupDesc" name="photGroupDesc"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="div_brev" title="添加缩略图" style="padding:10px;width:650px;height:200px;display:none;overflow:hidden;">
		<form action="" method="post" name="suoluePic_form">
	        <div id="uploadslp">
	            <table width="100%" cellpadding="3" cellspacing="5">
	                <tr>
	                    <td align="right">请选择缩略图：</td>
	                    <td align="right">
	                        <div id="fileQueue1"></div>
	                        <input type="text" id="url" name="url" value="" readonly="readonly" class="text_file" />
	                        <input type="file" id="file_upload1" name="file_upload" />
	                    </td>
	                    <td align="left">
	                        <input type="button" id="btnUpload1" onclick="$('#file_upload1').uploadifyUpload()" value="上传">
	                    </td>
	                </tr>
	            </table>
	            
	        </div>
		</form>
	</div>
</body>
</html>



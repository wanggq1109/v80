<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.text.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客片列表</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.v2.0.3.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploadify/swfobject.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script>
	$(function() {
		table_init();
	});
	
	var codeId = "11";
	
	function table_init(id) {
		$("#costomer_table").datagrid({
			url : "myjson/getCustomerInfo.action",
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
				{ field : "customerName", title : "新人名称", width : 80, align : "center" },
				{ field : "shootTime", title : "拍摄日期", width : 100, align : "center" },
				{ field : "remark", title : "描述", width : 180, align : "center",
					formatter : function(value, row, index) {
						if(value.length > 12){
							return value.substr(0, 12) + "...";
						} else {
							return value;
						}
					}
				 },
				{ field : "opa", title : "图片操作", width : 80, align : "center",
					formatter : function(value, row, index) {
						var a = "<a class='upload' href='javascript:upload_pic(" + row.id + ")'>上传</a>";
						var b = "<a class='edit' href='javascript:edit_pic(" + row.id + ")'>编辑</a>";
						return a + b;
					}
				} ] ],
			toolbar : [ {
				text : "新增",
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
				text : "修改",
				iconCls : "icon-edit",
				handler : function() {
					_edit();
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
	
	function upload_pic(id){
    	dialog_upload();
    	var url = "fileUpload.jsp?code="+codeId+"&id="+id;
		$("#iframeV80").attr("src", url);
    }
    
    // dialog新增
	function dialog_upload() {
		$("#div_upload").show();
		$("#div_upload").dialog({
			modal : true,
			iconCls : "icon-add",
			buttons : [{
				text : "关闭窗口",
				iconCls : "icon-ok",
				handler : function() {
					$("#costomer_table").datagrid("reload");
					$("#div_upload").dialog("close");
				}
			} ]
		});
	}
    
    function edit_pic(id){
    	parent.openMainFrame("编辑客片图片","<%=basePath%>customerInfophotosshow.action?id="+id); 
    }

	function showPager() {
		$("#costomer_table").datagrid("getPager").pagination({
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
			buttons:[{
				text:"保存",
				iconCls:"icon-ok",
				handler:function(){
                    $("#form_add").form("submit", {
                        url:"myjson/customerInfo_add.action",
                        onSubmit: function(){
							
                        },
                        success:function(data){
                            if(data==1){
                                $.messager.alert("成功","保存成功!","info",function(){
                                    $("#div_add").dialog("close");
                                    $("#costomer_table").datagrid("reload");
                                    $("#form_add").form("clear");
                                });
                            } else if(data==""||data==null){
                            	
                              	 $.messager.alert("成功","保存成功!","info",function(){
                              		 window.location.reload();
                                   });
                              	
                              }else {
                                $.messager.alert("错误","保存失败!","error");
                                $("#div_add").dialog("close");
                                $("#form_add").form("clear");
                            }
                        }
                    });
				}
			},{
				text:"取消",
				iconCls:"icon-cancel",
				handler:function(){
					$("#div_add").dialog("close");
                    $("#form_add").form("clear");
				}
			}]
		});
	}

	// 新增
	function _add() {
		dialog_add();
	}
	
	// 修改
	function _edit(){
		var rows = $("#costomer_table").datagrid("getSelected");
		var rowsLength = $("#costomer_table").datagrid("getSelections");
        if(rowsLength.length>1){
            $.messager.alert("修改","只能选择一条记录","warning");
        } else if(rowsLength.length==0){
            $.messager.alert("修改","请选择要修改的记录","warning");
        } else {
            if(rows){
                $("#div_edit").show();
                $("#customerName_edit").val(rows.customerName);
                $("#shootTime_edit").val(rows.shootTime);
                $("#remark_edit").val(rows.remark);
                
                id = rows.id;
                dialog_edit();
            }
        }
	}
	
	// dialog修改
	function dialog_edit(){
        $("#div_edit").show();
		$("#div_edit").dialog({
			modal: true,
            iconCls:"icon-edit",
			buttons:[{
				text:"保存",
				iconCls:"icon-ok",
				handler:function(){
					$("#form_edit").form("submit", {
                        url:"myjson/customerInfo_edit.action?id="+id,
                        onSubmit: function(){

                        },
                        success:function(data){
                            if(data==1){
                                $.messager.alert("成功","修改成功!","info",function(){
                                    $("#div_edit").dialog("close");
                                    $("#costomer_table").datagrid("reload");
                                    $("#form_edit").form("clear");
                                });
                            } else {
                                $.messager.alert("错误","修改失败!","error");
                                $("#div_edit").dialog("close");
                                $("#form_edit").form("clear");
                            }
                        }
                    });
				}
			},{
				text:"取消",
				iconCls:"icon-cancel",
				handler:function(){
					$("#div_edit").dialog("close");
                    $("#form_edit").form("clear");
				}
			}]
		});
	}
	
	// 删除
	function _del() {
		var row = $("#costomer_table").datagrid("getSelected");
		if (row) {
			$.messager.confirm("删除", "确定删除？", function(r) {
				if (r) {
					var ids = [];
					var rows = $("#costomer_table").datagrid("getSelections");
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					id = ids.join(",");
					$.ajax({
						type : "POST",
						url : "myjson/customer_delete.action",
						data : "id=" + id,
						success : function(msg, status) {
							if ("0000" == msg) {
								$.messager.alert("成功", "删除成功!", "info", function() {
									window.location.reload();
								});
							} else if("5555" == msg){
								
								$.messager.alert("错误", "当前客户存在照片不能删除!", "error");
								
							}else{
								
								$.messager.alert("错误", "删除失败!", "error");
							}
						}
					});
					$("#costomer_table").datagrid("clearSelections");
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
		<table id="costomer_table" fit="false"></table>
	</div>

	<div id="div_add" title="新增" style="padding:20px;width:700px;height:300px;display:none;overflow:hidden;">
		<form id="form_add" method="post">
			<table width="100%" cellpadding="5" cellspacing="5" border="0" height="100">
				<tr>
					<td align="right">新人姓名：</td>
					<td align="left"><input type="text" name="customerName" id="customerName" /></td>
					<td align="right">拍摄日期：</td>
					<td align="left"><input type="text" name="shootTime" id="shootTime" onclick="WdatePicker()" class="Wdate" /> </td>
				</tr>
				<tr>
					<td align="right">描述：</td>
					<td align="left" colspan="3">
						<textarea rows="3" cols="68" name = "remark" id="remark"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="div_edit" title="修改" style="padding:20px;width:700px;height:300px;display:none;overflow:hidden;">
		<form id="form_edit" method="post">
			<table width="100%" cellpadding="5" cellspacing="5" border="0" height="100">
				<tr>
					<td align="right">新人姓名：</td>
					<td align="left"><input type="text" name="customerName" id="customerName_edit" /></td>
					<td align="right">拍摄日期：</td>
					<td align="left"><input type="text" name="shootTime" id="shootTime_edit" onclick="WdatePicker()" class="Wdate" /> </td>
				</tr>
				<tr>
					<td align="right">描述：</td>
					<td align="left" colspan="3">
						<textarea rows="3" cols="68" name = "remark" id="remark_edit"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="div_upload" title="上传图片" style="padding:0px;width:700px;height:500px;display:none;overflow:hidden;">
		<iframe scrolling="auto" frameborder="0" src="" style="width:100%;height:99%;" id="iframeV80"></iframe>
	</div>
</body>
</html>



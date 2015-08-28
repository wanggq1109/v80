<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String typeCode = (String)request.getAttribute("code");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>员工摄影主题作品</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>

<script>
	$(function(){
        table_init();
    });
	
	var codeId = "<%=typeCode%>";
    function table_init(){
        $("#demo_defalt").datagrid({
            url:'myjson/photoTopicDataQuery.action',
            pagination:true,
            nowrap:false,
            striped: true,
            collapsible:true,
            loadMsg:"数据装载中,请稍后......",
            remoteSort:true,
			fitColumns: true,
            idField: "id",
            frozenColumns:[[
                 {field:"ck",checkbox:true}
                ]],
			columns:[[
				{field:"name",title:"主题名称",width:100,align:"center"},
				{field:"opa",title: "操作", width : 80, align : "center",
					formatter : function(value, row, index) {
						var e = '<a class="upload" href="javascript:_upload(' + row.id + ')">上传作品</a> ';
						var d = '<a class="look" href="javascript:_view(' + row.id + ')">查看作品</a>';
						return e + d;
					}
				}
			]],
            toolbar:[{
                text:"新增",
                iconCls:"icon-add",
                handler:function(){
                    _add();
                }
            },"-",{
                text:"修改",
                iconCls:"icon-edit",
                handler:function(){
                    _edit();
                }
            },"-",{
                text:"删除",
                iconCls:"icon-remove",
                handler:function(){
                    _del();
                }
            },"-",{
                text:"刷新",
                iconCls:"icon-reload",
                handler:function(){
                    window.location.reload();
                }
            }]
        });
    }
    
    function _upload(id){
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
					$("#demo_defalt").datagrid("reload");
					$("#div_upload").dialog("close");
				}
			} ]
		});
	}
    
    function _view(id){
    	parent.openMainFrame("查看作品","<%=basePath%>employeePhotoshow.action?id="+id); 
    }

	// 新增
	function _add(){
		dialog_add();
	}

    // dialog新增
	function dialog_add(){
        $("#div_add").show();
		$("#div_add").dialog({
			modal: true,
            iconCls:"icon-add",
			buttons:[{
				text:"保存",
				iconCls:"icon-ok",
				handler:function(){
                    $("#form_add").form("submit", {
                        url:"myjson/photoTopicDataAdd.action",
                        onSubmit: function(){
                            var title = $("#add_title_id").val();
                            var content = $("#add_content_id").val();
                        },
                        success:function(data){
                            if(data==1){
                                $.messager.alert("成功","保存成功!","info",function(){
                                    $("#div_add").dialog("close");
                                    $("#demo_defalt").datagrid("reload");
                                    $("#form_add").form("clear");
                                });
                            } else {
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
	
	// 修改
	function _edit(){
		var rows = $("#demo_defalt").datagrid("getSelected");
		var rowsLength = $("#demo_defalt").datagrid("getSelections");
        if(rowsLength.length>1){
            $.messager.alert("修改","只能选择一条记录","warning");
        } else if(rowsLength.length==0){
            $.messager.alert("修改","请选择要修改的记录","warning");
        } else {
            if(rows){
                $("#div_edit").show();
                $("#name_edit").val(rows.name);
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
                        url:"myjson/photoTopicDataEdit.action?id="+id,
                        onSubmit: function(){

                        },
                        success:function(data){
                            if(data==1){
                                $.messager.alert("成功","修改成功!","info",function(){
                                    $("#div_edit").dialog("close");
                                    $("#demo_defalt").datagrid("reload");
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
	function _del(){
		var row = $("#demo_defalt").datagrid("getSelected");
		if (row){
			$.messager.confirm("删除", "确定删除？", function(r){
				if (r){
					var ids = [];
					var rows = $("#demo_defalt").datagrid("getSelections");
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
					}
                    id = ids.join(",");
                    $.ajax({
                        type:"POST",
                        url:"myjson/photoTopicDataDelete.action",
                        data:"id="+id,
                        success:function(msg,status){
                            if("0000" == msg){
                                $.messager.alert("成功","删除成功!","info",function(){
                                    window.location.reload();
                                });
                            } else {
                                $.messager.alert("错误","删除失败!","error");
                            }
                        }
                    });
                    $("#demo_defalt").datagrid("clearSelections");
				}
			});
		} else {
			$.messager.alert("删除","请选择要删除的行","warning");
		}
	}
</script>
</head>
<body class="easyui-layout">
<div region="center" style="padding:2px 2px 2px 2px;" border="false">
	<table id="demo_defalt" fit="true"></table>
</div>
<div id="div_add" title="新增" style="padding:18px 50px 0 10px;width:420px;height:180px;display:none;overflow:hidden;">
    <form id="form_add" method="post">
	<table width="100%" cellpadding="0" cellspacing="5" border="0">
		<tr>
			<td align="right">主题名称：</td>
			<td align="left"><input type="text" name="name" id="name" class="text_full" /></td>
		</tr>
	</table>
    </form>
</div>
<div id="div_edit" title="修改" style="padding:18px 50px 0 10px;width:420px;height:180px;display:none;overflow:hidden;">
    <form id="form_edit" method="post">
	<table width="100%" cellpadding="0" cellspacing="5" border="0">
		<tr>
			<td align="right">主题名称：</td>
			<td align="left"><input type="text" name="name" id="name_edit" class="text_full" /></td>
		</tr>
	</table>
    </form>
</div>
<div id="div_upload" title="上传作品" style="padding:0px;width:800px;height:500px;display:none;overflow:hidden;">
	<iframe scrolling="auto" frameborder="0" src="" style="width:100%;height:99%;" id="iframeV80"></iframe>
</div>
<div id="div_view" title="查看作品" style="padding:0px;width:800px;height:500px;display:none;overflow:hidden;">
	<table align="center">
		<tr>
			<td><img src="http://www.yn.xinhuanet.com/ent/2014-10/04/133692224_14123921924061n.jpg" /></td>
			<td><img src="http://www.yn.xinhuanet.com/ent/2014-10/04/133692224_14123921924061n.jpg" /></td>
		</tr>
		<tr>
			<td><img src="http://www.yn.xinhuanet.com/ent/2014-10/04/133692224_14123921924061n.jpg" /></td>
			<td><img src="http://www.yn.xinhuanet.com/ent/2014-10/04/133692224_14123921924061n.jpg" /></td>
		</tr>
		<tr>
			<td><img src="http://www.yn.xinhuanet.com/ent/2014-10/04/133692224_14123921924061n.jpg" /></td>
			<td><img src="http://www.yn.xinhuanet.com/ent/2014-10/04/133692224_14123921924061n.jpg" /></td>
		</tr>
		<tr>
			<td><img src="http://www.yn.xinhuanet.com/ent/2014-10/04/133692224_14123921924061n.jpg" /></td>
			<td><img src="http://www.yn.xinhuanet.com/ent/2014-10/04/133692224_14123921924061n.jpg" /></td>
		</tr>
		<tr>
			<td><img src="http://www.yn.xinhuanet.com/ent/2014-10/04/133692224_14123921924061n.jpg" /></td>
			<td><img src="http://www.yn.xinhuanet.com/ent/2014-10/04/133692224_14123921924061n.jpg" /></td>
		</tr>
	</table>
</div>
</body>
</html>



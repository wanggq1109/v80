<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>招聘信息</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>

<script>
	$(function(){
        table_init();
    });

    function table_init(){
        var postUrl ="myjson/recruimentQuery.action";
        $("#demo_defalt").datagrid({
            url:postUrl,
            pagination:true,
            nowrap:false,
            striped: true,
            collapsible:true,
            loadMsg:"数据装载中,请稍后......",
            sortName:"id",
            sortOrder:"asc",
            remoteSort:true,
            fitColumns: true,
            idField: "id",
            frozenColumns:[[{field:"ck",checkbox:true}]],
            columns:[[
                {field:"jobName",title:"职位名称",width:100,align:"center"},
                {field:"intro",title:"职位介绍",width:160,align:"center"},
                {field:"sum",title:"职位名额",width:100,align:"center"}
            ]],
            toolbar:[{
                text:"新增",iconCls:"icon-add",handler:function(){ _add();}
            },"-",{
                text:"修改",iconCls:"icon-edit",handler:function(){ _edit();}
            },"-",{
                text:"删除",iconCls:"icon-remove",handler:function(){ _del(); }
            },"-",{
                text:"刷新",iconCls:"icon-reload", handler:function(){ window.location.reload(); }
            }]
        });
        showPager();
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
                        url:"myjson/recruimentAdd.action",
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
                $("#jobName_edit").val(rows.jobName);
                $("#sum_edit").val(rows.sum);
                $("#intro_edit").val(rows.intro);
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
                        url:"myjson/recruimentEdit.action?id="+id,
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
                        url:"myjson/recruimentDelete.action",
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
<div id="div_add" title="新增" style="padding:10px 10px 0 10px;width:700px;height:270px;display:none;overflow:hidden;">
    <form id="form_add" method="post">
	<table width="100%" cellpadding="5" cellspacing="5" border="0">
		<tr>
			<td align="right">职位名称：</td>
			<td align="left"><input type="text" name="jobName" id="jobName" class="text" /></td>
			<td align="right">职位名额：</td>
			<td align="left"><input type="text" name="sum" id="sum" class="text" /></td>
		</tr>
		<tr>
			<td align="right">职位内容：</td>
			<td align="left" colspan="3">
				<textarea rows="3" cols="73" name="intro" id="intro"></textarea>
			</td>
		</tr>
	</table>
    </form>
</div>
<div id="div_edit" title="修改" style="padding:10px 10px 0 10px;width:700px;height:270px;display:none;overflow:hidden;">
    <form id="form_edit" method="post">
	<table width="100%" cellpadding="5" cellspacing="5" border="0">
		<tr>
			<td align="right">职位名称：</td>
			<td align="left"><input type="text" name="jobName" id="jobName_edit" class="text"  /></td>
			<td align="right">职位名额：</td>
			<td align="left"><input type="text" name="sum" id="sum_edit" class="text"  /></td>
		</tr>
		<tr>
			<td align="right">职位内容：</td>
			<td align="left" colspan="3">
				<textarea rows="3" cols="73" name="intro" id="intro_edit"></textarea>
			</td>
		</tr>
	</table>
    </form>
</div>
</body>
</html>






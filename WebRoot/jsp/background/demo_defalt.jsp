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

<script>
	$(function(){
        table_init();
        //date_init();
    });

    function table_init(){
        $("#demo_defalt").datagrid({
            url:"",
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
            frozenColumns:[[
                 {field:"ck",checkbox:true}
                ]],
			columns:[[
				{field:"id",title:"ID",width:80,align:"center"},
				{field:"name",title:"姓名",width:100,align:"center"},
				{field:"templatDate",title:"时间",width:80,align:"center"}
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
                text:"查看明细",
                handler:function(){
                    _view();
                }
            },"-",{
                text:"查询",
                iconCls:"icon-search",
                handler:function(){
                    _query();
                }
            },"-",{
                text:"刷新",
                iconCls:"icon-reload",
                handler:function(){
                    window.location.reload();
                }
            }]
        });
        showPager();
    }

    function showPager(){
        $("#demo_defalt").datagrid("getPager").pagination({
            pageSize: 10,
            showPageList:false,
            showRefresh:false,
            displayMsg:"当前显示从{from}到{to}条，共{total}条记录",
            beforePageText:"第",
            afterPageText:"页，共{pages}页"
        });
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
                        url:"demo_add.action",
                        onSubmit: function(){
                            var id = $("#add_id").val();
                            var name = $("#add_name_id").val();
                            var templatDate = $("#add_templatDate_id").val();

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
                        url:"demo_edit.action?id="+id,
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
	// dialog查看
	function dialog_view(){
        $("#div_view").show();
		$("#div_view").dialog({
			modal: true,
			buttons:[{
				text:"关闭",
				handler:function(){
					$("#div_view").dialog("close");
				}
			}]
		});
	}
    // dialog查询
    function dialog_query(){
        $("#div_query").show();
        $("#div_query").dialog({
            modal: true,
            iconCls:"icon-search"
        });
    }
	// 新增
	function _add(){
		dialog_add();
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
                $("#edit_name_id").val(rows.name);
                $("#edit_templatDate_id").val(rows.templatDate);
                id = rows.id;
                dialog_edit();
            }
        }
	}
	// 查看
	function _view(){
        var rows = $("#demo_defalt").datagrid("getSelected");
		var rowsLength = $("#demo_defalt").datagrid("getSelections");
        if(rowsLength.length>1){
            $.messager.alert("查看","只能选择一条记录","warning");
        } else if(rowsLength.length==0){
            $.messager.alert("查看","请选择要修改的记录","warning");
        } else {
            if(rows){
                $("#div_view").show();
                $("#view_id_id").val(rows.id);
                $("#view_name_id").val(rows.name);
                $("#view_templatDate_id").val(rows.templatDate);
                dialog_view();
            }
        }
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
                        url:"demo_del.action",
                        data:"id="+id,
                        success:function(msg,status){
                            if("success" == status){
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

    // 打开查询dialog
    function _query(){
        dialog_query();
        $("#query_name_id").focus();
    }

    // 查询
    function query_init(){
        var name = $("#query_name_id").val();
        var queryParams = $("#demo_defalt").datagrid("options").queryParams;
        queryParams.name = name;
        $("#demo_defalt").datagrid("reload");
        $("#div_query").dialog("close");
		$("#form_query").form("clear");
        showPager();
    }

    function formatDate(v) {
        if (v instanceof Date) {
            var y = v.getFullYear();
            var m = v.getMonth() + 1;
            var d = v.getDate();
            var h = v.getHours();
            var i = v.getMinutes();
            var s = v.getSeconds();
            var ms = v.getMilliseconds();
            if (ms > 0)
                return y + "-" + m + "-" + d + " " + h + ":" + i + ":" + s + "." + ms;
            if (h > 0 || i > 0 || s > 0)
                return y + "-" + m + "-" + d + " " + h + ":" + i + ":" + s;
            return y + "-" + m + "-" + d;
         }
        return "";
    }

    function date_init() {
        $("#add_templatDate_id").datetimebox({
            currentText : "今天",
            closeText : "关闭",
            disabled : false,
            formatter : formatDate
        });
    }

</script>
</head>
<body class="easyui-layout">
<div region="center" style="padding:2px 2px 2px 2px;" border="false">
	<table id="demo_defalt" fit="true"></table>
</div>
<div id="div_add" title="新增书籍" style="padding:10px;width:600px;height:200px;display:none;overflow:hidden;">
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
<div id="div_edit" title="修改书籍" style="padding:10px;width:600px;height:200px;display:none;overflow:hidden;">
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
<div id="div_view" title="查看书籍" style="padding:10px;width:600px;height:200px;display:none;overflow:hidden;">
    <form id="form_view" method="post">
	<table width="100%" cellpadding="0" cellspacing="0" border="0" height="100" id="view">
        <tr>
			<td align="right">ID：</td>
			<td align="left"><input type="text" name="id" id="view_id_id" disabled="disabled" /></td>
			<td align="right">姓名：</td>
			<td align="left"><input type="text" name="name" id="view_name_id" disabled="disabled" /></td>
		</tr>
		<tr>
			<td align="right">时间：</td>
			<td align="left"><input type="text" name="templatDate" id="view_templatDate_id" disabled="disabled" /></td>
		</tr>
	</table>
    </form>
</div>
<div id="div_query" title="查询书籍" style="padding:10px;width:400px;height:100px;display:none;overflow:hidden;">
    <form id="form_query" method="post">
    <div class="toolbar" style="margin:10px 0px 0px 60px">
        姓名：<input type="text" name="name" id="query_name_id" onkeydown="if(event.keyCode==13) query_init()" />
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query_init()" >查询</a>
	</div>
    </form>
</div>
</body>
</html>



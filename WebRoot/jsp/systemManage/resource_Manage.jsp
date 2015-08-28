<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String adminName = (String)request.getAttribute("authName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css"/>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/item_selector/js/jquery.selectboxes.js"></script>
<script type="text/javascript" src="<%=basePath%>js/simple.js"></script>

<script>
	$(function(){
        table_init();
    });

    function table_init(){
        var postUrl ='myjson/resources_getDefaltJsonList.action';
        $('#demo_defalt').datagrid({
            url:postUrl,
            pagination:true,
            nowrap:false,
            striped: true,
            collapsible:true,
            loadMsg:'数据装载中,请稍后......',
            sortName:'resourceName',
            sortOrder:'asc',
            remoteSort:false,
//            fitColumns: true,
            idField: 'id',
            frozenColumns:[[{field:'ck',checkbox:true}]],
            columns:[[
                {field:'resourceName',title:'资源名称',width:150,sortable:true,align:'center',
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.resourceName+'</div>';
				}},
                {field:'resourceDesc',title:'资源描述',width:200,sortable:true,align:'center',
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.resourceDesc+'</div>';
				}},
                {field:'resourceType',title:'资源类型',width:140,sortable:true,align:'center',
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.resourceType+'</div>';
				}},
                {field:'resourceString',title:'资源路径',width:300,sortable:true,align:'center',
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.resourceString+'</div>';
				}},
                {field:'issys',title:'是否是超级',width:100,sortable:true,align:'center',
	                formatter:function(value,row,index){
	                    var s = row.issys;
	                    var e = "";
	                    if(s==true){
	                        e = '是';
	                    } else {
	                        e = '否';
	                    }
	                    return '<div style="text-align:left;">'+e+'</div>';
	                }
                },
                {field:'enabled',title:'是否可用',width:100,sortable:true,align:'center',
	                formatter:function(value,row,index){
	                    var s = row.enabled;
	                    var e = "";
	                    if(s==true){
	                        e = '是';
	                    } else {
	                        e = '否';
	                    }
	                    return '<div style="text-align:left;">'+e+'</div>';
	                }
                }
            ]],
            toolbar:[{
                text:'新增',iconCls:'icon-add',handler:function(){ _add();}
            },'-',{
                text:'删除',iconCls:'icon-remove',handler:function(){ _del();}
            },'-',{
                text:'修改',iconCls:'icon-edit',handler:function(){_edit();}
            },'-',{
                text:'刷新',iconCls:'icon-reload', handler:function(){ window.location.reload(); }
            }],
            onClickRow:function(rowIndex,row){
				$('#demo_defalt').datagrid('clearSelections');
				$('#demo_defalt').datagrid('selectRecord',row.id);
			}	
        });
        //}
        showPager();
    }

	// 新增
	function _add(){
	 $('#issys').show();
	 $('#issys option:first').attr('selected',true);
	 $("#jbxx").show();
                $('#jbxx').dialog({
                    modal: true,
                    iconCls:'icon-add',
                    buttons:[{
                    	id:'add_save',
                        text:'保存',
                        iconCls:'icon-ok',
                        handler:function(){
                      if ($("#resourceName").validatebox('isValid') &&$("#resourceDesc").validatebox('isValid')
                         &&$("#resourceType").validatebox('isValid') &&$("#resourceString").validatebox('isValid') ) {
                            $('#form_jbxx').form('submit', {
                                url:'resources_Add.action',
                                onSubmit: function(){
                                $('#add_save').linkbutton('disable'); 
                                },
                                success:function(data){
                                $('#issys').hide();
                                    if(data==0000){
                                        $.messager.alert('成功','新增成功!','info',function(){
                                            $('#jbxx').dialog('close');
                                            $('#demo_defalt').datagrid('reload');
                                            $('#form_jbxx').form('clear');
                                        });
                                    } else {
                                        $.messager.alert('错误','新增失败!','error');
 //                                        $('#add_save').linkbutton('enable');  
                                        $('#jbxx').dialog('close');
                                        $('#form_jbxx').form('clear');
                                    }
                                }
                            });
                            }/*
                    else {
                   $.messager.alert('警告', '有必填项未填或者填写错误', 'error');
                   }*/
                        }
                    },{
                        text:'取消',
                        iconCls:'icon-cancel',
                        handler:function(){
                            $('#jbxx').dialog('close');
                            $('#form_jbxx').form('clear');
                        }
                    }]
                });
            }

   // 修改
	function _edit(){
		var rows = $('#demo_defalt').datagrid('getSelected');
		var rowsLength = $('#demo_defalt').datagrid('getSelections');
        if(rowsLength.length>1){
            $.messager.alert('修改','只能选择一条记录','warning');
        } else if(rowsLength.length==0){
            $.messager.alert('修改','请选择一条记录','warning');
        } else {
            if(rows){
                var id = rows.id;
                $("#resourceName_edit").val(rows.resourceName);
                        $("#resourceDesc_edit").val(rows.resourceDesc);
                        $("#resourceType_edit").val(rows.resourceType);
                        $("#resourceString_edit").val(rows.resourceString);
                       
                resourceEdit(id,rows.issys);
            }
        }
	}

    function resourceEdit(id,ts)
    {
        $("#jbxx_edit").show();
        $('#issys_edit').show();
       // $('#issys_edit option').attr('selected',true);
       
        $('#jbxx_edit').dialog({
			modal: true,
            iconCls:'icon-edit',
			buttons:[{
				id:'edit_save',
				text:'保存',
				iconCls:'icon-ok',
				handler:function(){
                 if ($("#resourceName_edit").validatebox('isValid') &&$("#resourceDesc_edit").validatebox('isValid')
                         &&$("#resourceType_edit").validatebox('isValid') &&$("#resourceString_edit").validatebox('isValid') ) {
					$('#form_jbxx_edit').form('submit', {

                        url:'resources_Upt.action?id='+id,
                        onSubmit: function(){
                         $('#edit_save').linkbutton('disable');
                        },
                        success:function(data){    
                            if(data==1000){
                            	$('#issys_edit').hide();
                                $.messager.alert('成功','修改成功!','info',function(){
                                    $('#jbxx_edit').dialog('close');
                                    $('#demo_defalt').datagrid('reload');
                                    $('#form_jbxx_edit').form('clear');
                                });
                            } else {
                                $.messager.alert('错误','修改失败!','error');
 //                                $('#edit_save').linkbutton('enable');  
                                $('#jbxx_edit').dialog('close');
                                $('#form_jbxx_edit').form('clear');
                            }
                        }
                    });
                  }/*
                 else {
                   $.messager.alert('警告', '有必填项未填或者填写错误', 'error');
                  }*/
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#jbxx_edit').dialog('close');
                    $('#form_jbxx_edit').form('clear');
				}
			}]
		});
    }
	// 删除
	function _del(){
		var row = $('#demo_defalt').datagrid('getSelected');
		if (row){
			$.messager.confirm('删除', '确定删除？', function(r){
				if (r){
					var ids = [];
					var rows = $('#demo_defalt').datagrid('getSelections');
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
					}
                    var id = ids.join(',');
                    $.ajax({
                        type:"POST",
                        url:"resources_Del.action",
                        data:"id="+id,
                        success:function(msg,status){
                            if(msg == '0000'){
                               $.messager.alert('成功','删除成功!','info',function(){
                                   // window.location.reload();
                                   $("#demo_defalt").datagrid('reload');
                                });
                            }else{
                               $.messager.alert('错误',msg,'error');
                            }
                        }
                    });
                    $('#demo_defalt').datagrid('clearSelections');
				}
			});
		} else {
			$.messager.alert('删除','请选择要删除的行','warning');
		}
	}
</script>
</head>
<body class="easyui-layout">
<div region="center" style="padding:2px 2px 2px 2px;" border="false">
	<table id="demo_defalt" fit="true"></table>
</div>
<div id="jbxx" title="新建资源" style="padding:10px 10px 0 10px;width:600px;height:315px;display:none;overflow:hidden;">
    <form id="form_jbxx" method="post" >
            <table width="100%" align="center" cellpadding="5" cellspacing="5" >
                <tr>
<!--                    <input type="hidden" id="id" name="id" value="${sysResources.id}" />
                    <input type="hidden" id="msg" name="msg" value="${msg}" /> --> 
                    <td align="right">资源名称：</td>
                    <td align="left" ><input type="text" id="resourceName" name="resourceName" value="${sysResources.resourceName}" class="easyui-validatebox text" /></td>
                 </tr>
                 <tr>
                    <td align="right">资源路径：</td>
                    <td align="left" colspan="5"><input type="text" id="resourceString" name="resourceString" value="${sysResources.resourceString}" class="easyui-validatebox text"/></td>
                 </tr>
                 <tr>
                    <td align="right">资源类型：</td>
                    <td align="left"><input type="text" id="resourceType" name="resourceType" value="${sysResources.resourceType}" class="easyui-validatebox text" /></td>
                 </tr>
                 <tr>
                    <td align="right" rowspan="2">资源描述：</td>
                    <td align="left" rowspan="2"><textarea type="text" id="resourceDesc" name="resourceDesc" value="${sysResources.resourceDesc}" class="easyui-validatebox text"></textarea></td>                              
                 </tr>

            </table>
        </form>
        </div>
<div id="jbxx_edit" title="修改资源" style="padding:10px 10px 0 10px;width:600px;height:315px;display:none;overflow:hidden;">
    <form id="form_jbxx_edit" method="post" >
            <table width="100%" align="center" cellpadding="5" cellspacing="5" >
                <tr>
                    <td align="right">资源名称：</td>
                    <td align="left" ><input type="text" id="resourceName_edit" name="resourceName" value="${sysResources.resourceName}" class="easyui-validatebox text" /></td>               
                </tr>
                 <tr>
                    <td align="right">资源路径：</td>
                    <td align="left">
                        <input type="text" id="resourceString_edit" name="resourceString" value="${sysResources.resourceString}" class="easyui-validatebox text"/>
                    </td>                   
                </tr>
                 <tr>
                    <td align="right">资源类型：</td>
                    <td align="left" ><input type="text" id="resourceType_edit" name="resourceType" value="${sysResources.resourceType}" class="easyui-validatebox text"/></td>               
                </tr>
                 <tr>
                    <td align="right">资源描述：</td>
                    <td align="left"><input type="text" id="resourceDesc_edit" name="resourceDesc" value="${sysResources.resourceDesc}" class="easyui-validatebox text"/></td>                                  
                </tr>
            </table>
        </form>
        </div>
</body>
</html>



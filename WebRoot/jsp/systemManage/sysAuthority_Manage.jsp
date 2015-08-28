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
<script type="text/javascript" src="<%=basePath%>js/report_statistics_system.js"></script>
<script>
	$(function(){
        table_init();
    });

    function table_init(){
        var postUrl ='myjson/sysAuthority_List.action';
        $('#demo_defalt').datagrid({
            url:postUrl,
            pagination:true,
            nowrap:false,
            striped: true,
            collapsible:true,
            loadMsg:'数据装载中,请稍后......',
           	sortName: 'authorityName',
            sortOrder:'asc',
            remoteSort: false,
//            fitColumns: true,
            idField: 'id',
            frozenColumns:[[{field:'ck',checkbox:true}]],
            columns:[[
                {field:'authorityName',title:'权限名称',width:120,sortable:true,align:'center',
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.authorityName+'</div>';
				}},
                {field:'authorityDesc',title:'权限描述',width:200,sortable:true,align:'center',
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.authorityDesc+'</div>';
				}},
                {field:'issys',title:'系统管理',width:100,sortable:true,align:'center',
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
                }
            ]],
            toolbar:[{
                text:'新增',iconCls:'icon-add',handler:function(){ _add();}
            },'-',{
                text:'删除',iconCls:'icon-remove',handler:function(){ _del();}
            },'-',{
                text:'修改',iconCls:'icon-edit',handler:function(){_edit();}
            },'-',{
                text:'资源设置',iconCls:'icon-add', handler:function(){_addresources();}
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
		$('#add_issys').show();
        $('#add_issys option:first').attr('selected',true);
        $("#jbxx").show();        
                $('#jbxx').dialog({
                    modal: true,
                    iconCls:'icon-add',
                    buttons:[{
                    	id:'add_save',
                        text:'保存',
                        iconCls:'icon-ok',
                        handler:function(){
                           if ($("#authorityName").validatebox('isValid') &&$("#authorityDesc").validatebox('isValid') ) {
                            $('#form_jbxx').form('submit', {
                                url:'sysAuthority_Add.action',
                                onSubmit: function(){
                                $('#add_save').linkbutton('disable');
                                },
                                success:function(data){                               
                                    if(data==0000){
                                    	$('#add_issys').hide();
                                        $.messager.alert('成功','新增成功!','info',function(){
                                            $('#jbxx').dialog('close');
                                            $('#demo_defalt').datagrid('reload');
                                            $('#form_jbxx').form('clear');
                                        });
                                    } else {
                                        $.messager.alert('错误','新增失败!','error');
//                                      $('#add_save').linkbutton('enable');  
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
                $("#edit_authorityName").val(rows.authorityName);
                $("#edit_authorityDesc").val(rows.authorityDesc);
                authedit(id);
            }
        }
	}


    function  authedit(id)
    {
   	   $('#edit_issys').show();
//       $('#eidt_issys option:first').attr('selected',true);
       $("#jbxx_edit").show();     
        $('#jbxx_edit').dialog({
			modal: true,
            iconCls:'icon-edit',
			buttons:[{	
				id:'edit_save',
				text:'保存',
				iconCls:'icon-ok',
				handler:function(){
				  if ($("#edit_authorityName").validatebox('isValid') &&$("#edit_authorityDesc").validatebox('isValid') ) {

					$('#form_jbxx_edit').form('submit', {

                        url:'sysAuthority_Upt.action?id='+id,
                        onSubmit: function(){
                        $('#edit_save').linkbutton('disable');
                        },
                        success:function(data){
                            if(data==1000){
                            	$('#edit_issys').hide();
                                $.messager.alert('成功','修改成功!','info',function(){
                                    $('#jbxx_edit').dialog('close');
                                    $('#demo_defalt').datagrid('reload');
                                    $('#form_jbxx_edit').form('clear');
                                });
                            } else {
                                $.messager.alert('错误','修改失败!','error');
//                                 $('#edit_save').linkbutton('enable');  
                                $('#jbxx_edit').dialog('close');
                                $('#form_jbxx_edit').form('clear');
                            }
                        }
                    });
                   }
                  /*
                  else {
                   $.messager.alert('警告', '有必填项未填或者填写错误', 'error');
                   }
                   */
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
                        url:"sysAuthority_Del.action",
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
//权限设置
     function _addresources()
    {
        var rows = $('#demo_defalt').datagrid('getSelected');
		var rowsLength = $('#demo_defalt').datagrid('getSelections');
        if(rowsLength.length>1){
            $.messager.alert('权限设置','只能选择一条记录','warning');
        } else if(rowsLength.length==0){
            $.messager.alert('权限设置','请选择一条记录','warning');
        } else {
            if(rows){
                $('#div_add').show();
                 $('.container').show();
                $('#name').val(rows.authorityName);
                _getlist(rows.id);
                _getlist2(rows.id);

                editresources_dialog(rows.id);
            }
        }
    }

    function editresources_dialog(id)
    {
        $('#div_add').dialog({
			modal: true,
            iconCls:'icon-add',
			buttons:[{
				id:'editresources_save',
				text:'保存',
				iconCls:'icon-ok',
				handler:function(){
                    var d="";
                    $("#toSelectBox option").each(function() {
                    d += $(this).val()+',';});
					$('#form_add').form('submit', {

                        url:'authorities_resources_edit.action?id='+id+"&resources="+d,
                        onSubmit: function(){
                        $('#editresources_save').linkbutton('disable');
                        },
                        success:function(data){
                        $('.container').hide();
                            if(data==1){
                                $.messager.alert('成功','修改成功!','info',function(){
                                    $('#div_add').dialog('close');
                                    $('#demo_defalt').datagrid('reload');
                                    $('#form_add').form('clear');
                                });
                            } else {
                                $.messager.alert('错误','修改失败!','error');
//                                $('#editresources_save').linkbutton('enable');
                                $('#div_add').dialog('close');
                                $('#form_add').form('clear');
                            }
                        }
                    });
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#div_add').dialog('close');
                    $('#form_add').form('clear');
				}
			}]
		});
    }


    function _getlist(id)
    {
       $.ajax({
                        type:"POST",
                        url:"getresourceslist.action",
                        data:"id="+id,
                        success:function(msg,status)
                        {
                             var str=msg.split(',');
                             $("#toSelectBox").html("");
                             for(var i=0;i<str.length-1;i=i+2)
                             {
                                $("#toSelectBox").append("<option value='"+str[i+1]+"'>"+str[i]+"</option>");
                             }
                        }
                    });
    }

    function _getlist2(id)
    {
       $.ajax({
                        type:"POST",
                        url:"getresourceslist2.action",
                        data:"id="+id,
                        success:function(msg,status)
                        {
                             var str=msg.split(',');
                             $("#fromSelectBox").html("");
                             for(var i=0;i<str.length-1;i=i+2)
                             {
                                $("#fromSelectBox").append("<option value='"+str[i+1]+"'>"+str[i]+"</option>");
                             }
                        }
                    });
    }

</script>
</head>
<body class="easyui-layout">
<div region="center" style="padding:2px 2px 2px 2px;" border="false">
	<table id="demo_defalt" fit="true"></table>
</div>
<div id="div_add" title="资源设置" style="padding:10px;padding-left:18px;padding-top:18px;width:520px;height:380px;display:none;overflow:hidden;">
    <form id="form_add" method="post" >
        <table>
        <tr>
			<td align="right">权限名称：</td>
			<td align="left"><input type="text" name="name" id="name" readonly="readonly" class="text_dis" />
			</td>
			</tr>
		</table>
		<div class="container sys_container">
        <div class="from_container" >
            <table><tr><td>可选资源</td></tr></table>
            <span class="choose"> 
            <select id="fromSelectBox" multiple="multiple" name="selrole">
            </select>
            </span>
        </div>
        <div class="buttons_container" >
            <a href = "#"><img src="<%=basePath%>js/item_selector/images/right.png" border="0" onclick="moveright()"/></a>
            <br /><br />
            <a href = "#"><img src="<%=basePath%>js/item_selector/images/left.png" border="0" onclick="moveleft()"/></a>
            <br /><br />
            <a href = "#"><img src="<%=basePath%>js/item_selector/images/gif-allright.gif" border="0" onclick="selectAll('fromSelectBox')"/></a>
            <br /><br />
            <a href = "#"><img src="<%=basePath%>js/item_selector/images/gif-allleft.gif" border="0" onclick="clearAll('toSelectBox')"/></a>
        </div>
        <div class="to_container">
            <table><tr><td>可用资源</td></tr></table>
            <span class="choose"> 
            <select id="toSelectBox" multiple="multiple"></select>
            </span>
    </div>
    </div>
    </form>
</div>
<div id="jbxx" title="新增权限" style="padding:10px 10px 0 10px;width:600px;height:220px;display:none;overflow:hidden;">
        <form id="form_jbxx" method="post" >
            <table border="0" cellpadding="5" cellspacing="5" width="100%" align="center">
                <tr>
<!--                <input type="hidden" id="id" name="id" value="${sysAuthority.id}" />
                    <input type="hidden" id="msg" name="msg" value="${msg}" />--> 
                    <td align="right">权限名称：</td>
                    <td align="left"><input type="text" id="authorityName" name="authorityName" value="${sysAuthority.authorityName}" class="easyui-validatebox text" /></td>
                </tr>
                <tr>
            	 <td align="right" rowspan="2">权限描述：</td>
                    <td align="left" rowspan="2">
                        <textarea type="text" id="authorityDesc" name="authorityDesc" value="${sysAuthority.authorityDesc}" class="easyui-validatebox text describe" ></textarea>
                    </td>
                </tr>
            </table>
            </form>
        </div>
<div id="jbxx_edit" title="修改权限" style="padding:10px 10px 0 10px;width:600px;height:220px;display:none;overflow:hidden;">
    <form id="form_jbxx_edit" method="post" >
        <table width="100%" cellpadding="5" cellspacing="5">
                <tr>
                    <td align="right">权限名称：</td>
                    <td align="left" colspan="3"><input type="text" id="edit_authorityName" name="authorityName" value="${sysAuthority.authorityName}" class="easyui-validatebox text" /></td>
                </tr>
                <tr>
               		<td align="right" rowspan="2">权限描述：</td>
                    <td align="left" rowspan="2"><textarea type="text" id="edit_authorityDesc" name="authorityDesc" value="${sysAuthority.authorityDesc}" class="easyui-validatebox text describe" ></textarea></td>
                </tr>
                <tr>
                </tr>
            </table>
        </form>
        </div>
</body>
</html>



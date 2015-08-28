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
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script>
	$(function(){
        table_init();     
    });

    function table_init(){
        var postUrl ='log_getDefaltJsonList.action';
        $('#demo_defalt').datagrid({
            url:postUrl,
            height:300,
            pagination:true,
            nowrap:false,
            striped: true,
            collapsible:true,
            loadMsg:'数据装载中,请稍后......',
           	sortName: 'userAccount',
            sortOrder:'asc',
            remoteSort: false,
            //fitColumns: true,
            idField: 'id',
            frozenColumns:[[{field:'ck',checkbox:true}]],
            columns:[[
                {field:'userName',title:'用户名称',width:150,sortable:true,align:'center',
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.userName+'</div>';
				}},
				{field:'userAccount',title:'用户账号',width:150,sortable:true,align:'center',
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.userAccount+'</div>';
				}},
                {field:'date',title:'操作时间',width:150,sortable:true,align:'center',
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.date+'</div>';
				}},
				{field:'operation',title:'操作事项',width:150,sortable:true,align:'center',
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.operation+'</div>';
				}},
			   {field:'module_name',title:'操作模块',width:120,sortable:true,align:'center',
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.module_name+'</div>';
				}}
                
            ]],
            toolbar:[{
                text:'查询',iconCls:'icon-search',handler:function(){ _query();}
            }
            ,'-',{
                text:'清空',iconCls:'icon-remove', handler:function(){ _del(); }
            },'-',{
                text:'刷新',iconCls:'icon-reload', handler:function(){ window.location.reload(); }
            }
            ],
            onClickRow:function(rowIndex,row){
				$('#demo_defalt').datagrid('clearSelections');
				$('#demo_defalt').datagrid('selectRecord',row.id);
			}	
        });
        //}
        showPager();
    }
// dialog查询
    function dialog_query(){
        $('#div_query').show();
        $('#div_query').dialog({
            modal: true,
            iconCls:'icon-search',
            buttons:[{
	            text:'查询',
	            iconCls:'icon-search',
	            handler:function(){
	            query_init();
	            }
	            },
	            {
	            text:'取消',
	            iconCls:'icon-cancel',
	             handler:function(){
	                $('#div_query').dialog('close');
	                $('#form_query').form('clear');
	             }
	            }]
        });
    }

    // 打开查询dialog
    function _query(){
        dialog_query();
        $('#query_name_id').focus();
    }

    // 查询
    function query_init(){
    	var name=$('#name_query').val();
        var start_time = $('#start_query').val();
        var end_time = $('#end_query').val();  
        var module_name = $('#module_name_query').val();  
        var queryParams = $('#demo_defalt').datagrid('options').queryParams;
        queryParams.name=name;
        queryParams.start_time = start_time;
        queryParams.end_time = end_time;
        queryParams.module_name = module_name;
        $("#demo_defalt").datagrid('reload');
        $('#div_query').dialog('close');
		$('#form_query').form('clear');
        showPager();
    }    
    
    //清空
    function _del()
    {
	    $('#div_del').show();
        $('#div_del').dialog({
            modal: true,
            iconCls:'icon-remove',
            buttons:[{
	            text:'确定',
	            iconCls:'icon-ok',
	            handler:function(){
	            del();
	            }
	            },
	            {
	            text:'取消',
	            iconCls:'icon-cancel',
	             handler:function(){
	                $('#div_del').dialog('close');
	                $('#form_del').form('clear');
	             }
	            }]
        });
    }
    
    function del()
    {
    	 $('#form_del').form('submit', {
		   url:'log_del.action?start_time_del='+$('#start_del_query').val()+"&end_time_del="+$('#end_del_query').val(),
		                    success:function(data){
		                        if(data==0){
		                            $.messager.alert('成功','清空成功!','info',function(){
		                                $('#div_del').dialog('close');
		                                window.location.reload();
		                                //$('#form_edit').form('clear');
		                            });
		                        } else {
		                            $.messager.alert('错误','清空失败!','error');
		                            $('#div_del').dialog('close');
		                            $('#form_del').form('clear');
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
<div id="div_query" title="查询" style="padding:10px;width:600px;height:160px;display:none;overflow:hidden;"> 
  <form id="form_query" method="post">
    <table border="0" cellpadding="5" cellspacing="5" align="center" width="100%"> 
      <tr> 
        <td align="right">用户名称：</td> 
        <td><input type="text" name="" id="name_query" class="text" /></td> 
         <td align="right">操作模块：</td> 
        <td><input type="text" name="" id="module_name_query" class="text" /></td> 
        </tr> 
      <tr> 
        <td align="right">操作开始时间：</td> 
        <td><input type="text" name="" id="start_query" class="Wdate easyui-validatebox text" onClick="WdatePicker()" /></td>
        <td align="right">操作结束时间：</td> 
        <td><input type="text" name="" id="end_query" class="Wdate easyui-validatebox text" onClick="WdatePicker()" /></td>
      </tr>
     <!--   
       <tr> 
        <td align="center" colspan="6"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query_init();">查询</a> </td>
      </tr>  
      -->  
    </table> 
  </form>
</div> 
<div id="div_del" title="清空" style="padding:10px;width:380px;height:160px;display:none;overflow:hidden;"> 
  <form id="form_del" method="post">
    <table border="0" cellpadding="5" cellspacing="5" align="center" width="100%">   
    <tr>
    	<td align="right">操作开始时间：</td> 
        <td><input type="text" name="" id="start_del_query" class="Wdate easyui-validatebox text" onClick="WdatePicker()" /></td>
      </tr>
      <tr>
      <td align="right">操作结束时间：</td> 
      <td><input type="text" name="" id="end_del_query" class="Wdate easyui-validatebox text" onClick="WdatePicker()" /></td>
      </tr>
     <!--   
       <tr> 
        <td align="center" colspan="6"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query_init();">查询</a> </td>
      </tr>  
      -->  
    </table> 
  </form>
</div> 
</body>
</html>



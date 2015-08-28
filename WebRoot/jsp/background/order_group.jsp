<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jQuery EasyUI</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<script>
	$(function(){
        table_init();
	});

    function table_init(){
        var lastIndex;
		$("#info_table").datagrid({
            url:"myjson/orderGroupDataQuery.action",
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
				{field:"orderNo",title:"订单编号",width:50,align:"center",editor:"text"},
				{field:"itemsName",title:"套餐名称",width:100,align:"center",editor:"text"},
				{field:"price",title:"套餐价格",width:50,align:"center",editor:"text"},
				{field:"name",title:"客户姓名(真实)",width:50,align:"center",editor:"text"},
				{field:"phoneNo",title:"手机号码",width:60,align:"center",editor:"text"},
				{field:"orderTime",title:"生成订单时间",width:100,align:"center",editor:"text"},
				{field:"qq",title:"qq号",width:50,align:"center",editor:"text"},
				{field:"remark",title:"备注",width:30,align:"center",editor:"text"}
			]],
			toolbar:[
                {
                	text:"查询",
                    iconCls:"icon-search",
                    handler:function(){
                        _query();
	                }
	            },"-",{
	        	   	text:"修改",
	        	   	handler:function(){
	        	   		_edit();
	                }
	            },"-",{
	                text:"刷新",iconCls:"icon-reload", handler:function(){ window.location.reload(); }
            }]
		});
        showPager();
    }

    function showPager(){
        $("#info_table").datagrid("getPager").pagination({
            pageSize: 10,
            showPageList:false,
            showRefresh:false,
            displayMsg:"当前显示从{from}到{to}条，共{total}条记录",
            beforePageText:"第",
            afterPageText:"页，共{pages}页"
        });
    }

    // 修改
	function _edit(){
		var rows = $("#info_table").datagrid("getSelected");
		var rowsLength = $("#info_table").datagrid("getSelections");
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
                parent.open_tabs("查看详细","");
            }
        }
	}

    function _cancel(index){
        $("#info_table").datagrid("cancelEdit", index);
    }

    // 打开查询dialog
    function _query(){
        dialog_query();
        $("#name").focus();
    }

     // dialog查询
    function dialog_query(){
        $("#div_query").show();
        $("#div_query").dialog({
            modal: true,
            iconCls:"icon-search"
        });
    }

    /**
    *查询
     */
    function query_init(){
        var  name = $("#name").val();
        var orderNo = $("#ordrNo").val();
        $("#div_query").dialog("close");
		$("#form_query").form("clear");
    }

</script>
</head>
<body class="easyui-layout">
<div region="center" style="padding:2px;" border="true">
	<table id="info_table" idField="id" fit="true"></table>
</div>
<div id="div_query" title="订单查询" style="padding:10px;width:600px;height:230px;display:none;overflow:hidden;">
    <form id="form_query" method="post">
    <div class="toolbar" style="margin:10px">
    	<table cellpadding="5">
    		<tr>
    			<td>客户姓名：</td>
    			<td><input type="text" name="name" id="name" /></td>
    			<td>订单号：</td>
    			<td><input type="text" name="orderNo" id="orderNo" /></td>
    		</tr>
    		<tr>
    			<td>开始时间：</td>
    			<td><input type="text" name="beginTime" id="beginTime" /></td>
    			<td>结束时间：</td>
    			<td><input type="text" name="endTime" id="endTime" /></td>
    		</tr>
    	</table>
    	<div style="float: right; margin: 10px 10px 0 0;">
    		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query_init()" >查询</a>
    	</div>
	</div>
    </form>
</div>
</body>
</html>
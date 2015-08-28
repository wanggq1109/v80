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
<title>工作室简介</title>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploadify/swfobject.js"></script>
<script type="text/javascript" src="<%=basePath%>ckeditor/ckeditor.js"></script>  
<script type="text/javascript" src="<%=basePath%>ckfinder/ckfinder.js"></script> 
<script>
	$(function(){
        table_init();
    });
	
	var codeId = "<%=typeCode%>";
    function table_init(){
        $("#demo_defalt").datagrid({
            url:'myjson/studioPhotoQuery.action',
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
                {field:"picName",title:"图片名称",width:100,align:"center"},
                {field:"uploadTime",title:"上传时间",width:160,align:"center"}
            ]],
            toolbar:[{
                text:"上传",iconCls:"icon-add",handler:function(){ _add();}
            },"-",{
                text:"删除",iconCls:"icon-remove",handler:function(){ _del(); }
            },"-",{
                text:"刷新",iconCls:"icon-reload", handler:function(){ window.location.reload(); }
            }]
        });
        //}
        showPager();
    }

    function showPager(){
        $("#demo_defalt").datagrid("getPager").pagination({
            pageSize: 5,
            showPageList:false,
            showRefresh:false,
            displayMsg:"当前显示从{from}到{to}条，共{total}条记录",
            beforePageText:"第",
            afterPageText:"页，共{pages}页"
        });
    }

 	// 新增
	function _add() {
		dialog_add();
		var url = "fileUpload.jsp?code=" + codeId;
		$("#iframeV80").attr("src", url);
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
					$("#demo_defalt").datagrid("reload");
					$("#div_add").dialog("close");
				}
			} ]
		});
	}

	// 删除
	function _del(){
		var row = $("#demo_defalt").datagrid("getSelected");
		if (row){
			$.messager.confirm("删除", "确定删除？", function(r){
				if (r){
					var ids = [];
                    var ids1 = [];
					var rows = $("#demo_defalt").datagrid("getSelections");
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
                        ids1.push(rows[i].url);
					}
                    id = ids.join(",");
                   
                    var url = ids1.join(",");
                    $.ajax({
                        type:"POST",
                        url:"myjson/studiophotodelete.action",
                        data:"id="+id+"&url="+url,
                        success:function(msg,status){
                            if(msg == "0000"){
                               $.messager.alert("成功","删除成功!","info",function(){
                                    window.location.reload();
                                });
                            }else{
                               $.messager.alert("错误",msg,"error");
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
	
	function ckf_save(){
		ckf_form.action = "myjson/studio_add.action";
		ckf_form.target= "";
		ckf_form.submit();
	}

</script>
</head>
<body class="easyui-layout" style="padding:0px;margin:0px;">
	<div class="easyui-tabs" fit="true" border="false">
		<div title="文字介绍">
			<form action="" method="post" name="ckf_form">
				<textarea cols="80" id="editor1" name="editor1" value = "${content}" rows="10"></textarea>
				<div class="toolbar" style="text-align:center;height:50px;padding-top:20px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="ckf_save()">保存</a>
				</div>
			</form>
		</div>
		<div title="图片介绍">
			<table id="demo_defalt" fit="false"></table>
		</div>
	</div>
	<div id="div_add" title="新增" style="padding:0px;width:800px;height:500px;display:none;overflow:hidden;">
		<iframe scrolling="auto" frameborder="0" src="" style="width:100%;height:99%;" id="iframeV80"></iframe>
	</div>
	<script type="text/javascript">  
        CKEDITOR.replace('editor1',{  
            filebrowserBrowseUrl : '<%=basePath%>ckfinder/ckfinder.html',  
            filebrowserImageBrowseUrl : '<%=basePath%>ckfinder/ckfinder.html?type=Images',  
            filebrowserFlashBrowseUrl : '<%=basePath%>ckfinder/ckfinder.html?type=Flash',  
            filebrowserUploadUrl : '<%=basePath%>ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',  
            filebrowserImageUploadUrl : '<%=basePath%>ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',  
            filebrowserFlashUploadUrl : '<%=basePath%>ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'  
        });  
 </script>  
</body>
</html>



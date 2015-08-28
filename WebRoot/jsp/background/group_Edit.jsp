<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>团购管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>ckeditor/ckeditor.js"></script>  
<script type="text/javascript" src="<%=basePath%>ckfinder/ckfinder.js"></script>  
<script type="text/javascript">
$(document).ready(function(){
    showMsg();
});

function showMsg(){
    var msg = $("#msg").val();
    if(msg != ""){
        if(msg == "0000"){
            $.messager.alert("成功","新增成功!","info",function(){
                parent.close_tabs("新增团购");
                parent.reload_tabs("团购管理","<%=basePath%>orderManager_init.action");
            });

        }else if(msg == "1000"){
            $.messager.alert("成功","修改成功!","info",function(){
                parent.close_tabs("修改团购");
                parent.reload_tabs("团购管理","<%=basePath%>orderManager_init.action");
            });
        }else{
            $.messager.alert("错误",msg,"error");
            return;
        }
    }
}

function _save(){
    if($("#title").validatebox('isValid')){
        var id = $('#id').val();
        if(id !=''){
           groupEdit_form.action = "myjson/groupitemsInfo_edit.action?id="+id;
        } else {
           groupEdit_form.action = "myjson/groupitemsInfo_add.action";
        }
        groupEdit_form.target= "";
        groupEdit_form.submit();
    } else {
        $.messager.alert("警告","有必填项未填","error");
    }
}

function _cancel(){
    $.messager.confirm("取消", "确定取消本次操作？", function(r){
        if (r){
            var id = $('#id').val();
            if(id !=''){
               parent.close_tabs("修改团购");
            }else{
               parent.close_tabs("新增团购");
            }
        }
    });
}
</script>
</head>
<body>
<form action="" method="post" name="groupEdit_form">
<div id="main_tabs" style="padding:10px">
    <fieldset>
        <legend style="font-size:14px">团购信息</legend>
        <div id="jbxx">
            <table width="100%" cellpadding="3" cellspacing="5">
                <tr>
                    <input type="hidden" id="id" name="id" value="${id}" />
                    <input type="hidden" id="msg" name="msg" value="${msg}" />
                    <td align="right" width="120">标题：</td>
                    <td align="left">
                    	<input type="text" id="title" name="title" value="${groupItem.title }" class="text easyui-validatebox" required="true" missingMessage="必填项" />
                    </td>
				</tr>
				<tr>
					<td align="right">价格（优惠前）：</td>
					<td align="left"><input type="text" name="old_price" id="old_price" value="${groupItem.old_price }" class="text easyui-validatebox" required="true" missingMessage="必填项" /></td>
				</tr>
				<tr>
					<td align="right">价格（优惠后）：</td>
					<td align="left"><input type="text" name="price" id="price" value="${groupItem.price }" class="text easyui-validatebox" required="true" missingMessage="必填项" /> </td>
				</tr>
				<tr>
					<td align="right">描述：</td>
					<td align="left">
						<textarea rows="5" cols="80" name="descr" id="descr">${groupItem.descr}</textarea>
					</td>
				</tr>
                <tr>
                    <td align="right">内容：</td>
                    <td align="left">
                        <textarea cols="80" id="content" name="content" rows="10">${groupItem.content}</textarea>
                    </td>
                </tr>
            </table>
        </div>
    </fieldset>
        <div class="toolbar" style="text-align:center;height:50px;padding-top:20px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="_save()">保存</a>&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="_cancel()">取消</a>
	</div>
</div>
<script type="text/javascript">  
        CKEDITOR.replace('content',{  
            filebrowserBrowseUrl : '<%=basePath%>ckfinder/ckfinder.html',  
            filebrowserImageBrowseUrl : '<%=basePath%>ckfinder/ckfinder.html?type=Images',  
            filebrowserFlashBrowseUrl : '<%=basePath%>ckfinder/ckfinder.html?type=Flash',  
            filebrowserUploadUrl : '<%=basePath%>ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',  
            filebrowserImageUploadUrl : '<%=basePath%>ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',  
            filebrowserFlashUploadUrl : '<%=basePath%>ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'  
        });  
 </script>  
</form>
</body>
</html>
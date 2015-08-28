<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%--<%@ taglib uri="http://ckfinder.com" prefix="ckfinder"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
--%><%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻</title>
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
                parent.close_tabs("新增新闻");
                parent.reload_tabs("新闻","<%=basePath%>news_init.action");
            });

        }else if(msg == "1000"){
            $.messager.alert("成功","修改成功!","info",function(){
                parent.close_tabs("修改新闻");
                parent.reload_tabs("新闻","<%=basePath%>news_init.action");
            });
        }else{
            $.messager.alert("错误",msg,"error");
            return;
        }
    }
}

function _save(){
    if($("#name").validatebox('isValid')){
        var id = $('#id').val();
        if(id !=''){
           newsEdit_form.action = "myjson/newsinfoEdit_save.action";
        } else {
           newsEdit_form.action = "myjson/newSave.action";
        }
        newsEdit_form.target= "";
        newsEdit_form.submit();
    } else {
        $.messager.alert("警告","有必填项未填","error");
    }
}

function _cancel(){
    $.messager.confirm("取消", "确定取消本次操作？", function(r){
        if (r){
        	window.location.href="<%=basePath%>news_init.action";
        }
    });
}
</script>
</head>
<body>
<form action="" method="post" name="newsEdit_form">
<div id="main_tabs" style="padding:10px">
    <fieldset>
        <legend style="font-size:14px">新闻信息</legend>
        <div id="jbxx">
            <input type="hidden" id="id" name="id" value="${id}" />
            <input type="hidden" id="msg" name="msg" value="${msg}" />
            <table width="100%" cellpadding="3" cellspacing="5">
                <tr>
                    <td align="right" width="50">标题：</td>
                    <td align="left">
                    	<input type="text" id="name" name="name" value="${mynewsinfo.title}" class="text_full easyui-validatebox" required="true" missingMessage="必填项" />
                    </td>
                    <td align="right">描述：</td>
                    <td align="left">
                        <input type="text" id="descr" name="descr" value="${mynewsinfo.descr}" class="text_full easyui-validatebox" required="true" missingMessage="必填项" />
                    </td>
                </tr>
                <tr>
                    <td align="right">作者：</td>
                    <td align="left">
                    	<input type="text" id="author" name="author" value="${mynewsinfo.author}" class="text_full easyui-validatebox" required="true" missingMessage="必填项" />
                    </td>
                    <td align="right">来源：</td>
                    <td align="left">
                        <input type="text" id="resource" name="resource" value="${mynewsinfo.source}" class="text_full easyui-validatebox" required="true" missingMessage="必填项" />
                    </td>
                </tr>
                <tr>
                    <td align="right">内容：</td>
                    <td align="left" colspan="5">
                        <textarea cols="80" id="editor1" name="editor1" rows="10">${mynewsinfo.content}</textarea>
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
<%--<ckfinder:setupCKEditor basePath="/v80/ckfinder/" editor="content" />
<ckeditor:replace replace="editor1" basePath="/v80/ckeditor/" />
--%>
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
</form>
</body>
</html>
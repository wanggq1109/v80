<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://ckfinder.com" prefix="ckfinder"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微电影</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    showMsg();
});

function showMsg(){
    var msg = $("#msg").val();
    if(msg != ""){
        if(msg == "0000"){
            $.messager.alert("成功","新增成功!","info",function(){
                parent.reload_tabs("招聘管理","<%=basePath%>video_init.action");
                parent.close_tabs("新增招聘");
            });

        }else if(msg == "1000"){
            $.messager.alert("成功","修改成功!","info",function(){
                parent.reload_tabs("招聘管理","<%=basePath%>video_init.action");
                parent.close_tabs("修改招聘");
            });
        }else{
            $.messager.alert("错误",msg,"error");
            return;
        }
    }
}

function _save(){
    if($("#name").validatebox("isValid")){
        var id = $("#id").val();
        if(id !=""){
           videoEdit_form.action = "";
        }else{
           videoEdit_form.action = "";
        }
        videoEdit_form.target= "";
        videoEdit_form.submit();
    }else{
        $.messager.alert("警告","有必填项未填","error");
    }
}

function _cancel(){
    $.messager.confirm("取消", "确定取消本次操作？", function(r){
        if (r){
            window.location.href="";
        }
    });
}
</script>
</head>
<body>
<form action="" method="post" name="videoEdit_form">
<div id="main_tabs">
    <fieldset>
        <legend>招聘信息</legend>
        <div id="jbxx">
            <table width="100%" cellpadding="3" cellspacing="5">
                <tr>
                    <input type="hidden" id="id" name="id" value="${id}" />
                    <input type="hidden" id="msg" name="msg" value="${msg}" />
                    <td align="right">职位名称：</td>
                    <td align="left"><input type="text" id="" name="" value="" class="text" /></td>
                    <td align="right">职位人数：</td>
                    <td align="left"><input type="text" id="" name="" value="" class="text" /></td>
                </tr>
                <tr>
                    <td align="right">职位描述：</td>
                    <td align="left" colspan="5">
                        <textarea cols="80" id="editor1" name="editor1" value = "${content}" rows="10"></textarea>
                    </td>
                    </td>
                </tr>
            </table>
        </div>
    </fieldset>
        <div class="toolbar" style="text-align:center;height:50px;padding-top:20px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="_save()">保存</a>&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="_cancel()">取消</a>
	</div>
	<ckfinder:setupCKEditor basePath="/v80/ckfinder/" editor="content" />
	<ckeditor:replace replace="editor1" basePath="/v80/ckeditor/" />
</div>
</form>
</body>
</html>
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
<title>80视觉摄影婚纱</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/galleria/galleria-1.2.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/galleria/galleria.classic.js"></script>
<script type="text/javascript" src="<%=basePath%>fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/scripts/swfobject.js"></script>
<script type="text/javascript" src="<%=basePath%>js/scripts/jquery.uploadify.v2.1.0.min.js"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.v2.0.3.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploadify/swfobject.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  showMsg();
});

function showMsg(){
    var msg = $("#msg").val();
    if(msg != ""){
        if(msg == "0000"){
            $.messager.alert('成功','新增成功!','info',function(){
                parent.reload_tabs('资源列表','<%=basePath%>resources_Manage.action');
                parent.close_tabs('新增信息');
            });

        }else if(msg == "1000"){
            $.messager.alert('成功','修改成功!','info',function(){
                parent.reload_tabs('资源列表','<%=basePath%>resources_Manage.action');
                parent.close_tabs('修改信息');
            });
        }else{
            $.messager.alert('错误',msg,'error');
            return;
        }
    }
}

function ifNotCheck(){
	var c = document.getElementById("checkbox");
	var b = document.getElementById("jbxx");
  	if(c.checked){
        b.style.display='';
    } else {
    	b.style.display='none';
    }
}

function _save(){
        if($("#resourceName").validatebox('isValid')&&$("#resourceDesc").validatebox('isValid')&&$("#resourceType").validatebox('isValid')&&$("#resourceString").validatebox('isValid')
           ){
            var id = $('#id').val();
            if(id !=''){
               resourcesEdit_form.action = "resources_Upt.action";
            }else{
               resourcesEdit_form.action = "resources_Add.action";
            }
            resourcesEdit_form.target= "";
            resourcesEdit_form.submit();
        }else{
            $.messager.alert('警告','有必填项未填','error');
        }
}

function _cancel(){
    $.messager.confirm('取消', '确定取消本次操作？', function(r){
        if (r){
            var id = $('#id').val();
            if(id !=''){
               parent.close_tabs('修改资源信息');
            }else{
               parent.close_tabs('新增资源');
            }
        }
    });
}
</script>
</head>
<body>
<form action="" method="post" name="resourcesEdit_form">
<div id="main_tabs">
    <fieldset>
        <legend><input type="checkbox" id="checkbox" onclick="ifNotCheck()" checked="checked" />资源信息</legend>
        <div id="jbxx">
            <table width="100%" cellpadding="3" cellspacing="5">
                <tr>
                    <input type="hidden" id="id" name="id" value="${sysResources.id}" />
                    <input type="hidden" id="msg" name="msg" value="${msg}" />
                    <td align="right">资源名称：</td>
                    <td align="left" colspan="3"><input type="text" id="resourceName" name="resourceName" value="${sysResources.resourceName}" class="text easyui-validatebox" required="true" missingMessage="必填项" /></td>
                </tr>
                <tr>
                    <td align="right">资源描述：</td>
                    <td align="left" colspan="5">
                        <input type="text" id="resourceDesc" name="resourceDesc" value="${sysResources.resourceDesc}" class="text easyui-validatebox" required="true" missingMessage="必填项" />
                    </td>
                </tr>
                 <tr>
                    <td align="right">资源类型：</td>
                    <td align="left" colspan="5">
                        <input type="text" id="resourceType" name="resourceType" value="${sysResources.resourceType}" class="text easyui-validatebox" required="true" missingMessage="必填项" />
                    </td>
                </tr>
                   <tr>
                    <td align="right">资源路径：</td>
                    <td align="left" colspan="5">
                        <input type="text" id="resourceString" name="resourceString" value="${sysResources.resourceString}" class="text easyui-validatebox" required="true" missingMessage="必填项" />
                    </td>
                </tr>
                <tr>
                  <td align="right">是否是超级：</td>
                  <td align="left" colspan="5">
                      <s:select id="issys" name="issys" list="#{'1':'是','0':'否'}" listKey="key" listValue="value" cssClass="select" theme="simple" ></s:select>
                  </td>
                </tr>
                 <tr>
                  <td align="right">是否可用：</td>
                  <td align="left" colspan="5">
                      <s:select id="enabled" name="enabled" list="#{'1':'是','0':'否'}" listKey="key" listValue="value" cssClass="select" theme="simple"></s:select>
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
</form>
</body>
</html>


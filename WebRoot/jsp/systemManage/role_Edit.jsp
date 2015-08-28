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
<script type="text/javascript" src="<%=basePath%>js/galleria/galleria-1.2.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/galleria/galleria.classic.js"></script>
<script type="text/javascript" src="<%=basePath%>fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/scripts/swfobject.js"></script>

<script type="text/javascript">
$(document).ready(function(){
  showMsg();
});

function showMsg(){
    var msg = $("#msg").val();
    if(msg != ""){
        if(msg == "0000"){
            $.messager.alert('成功','新增成功!','info',function(){
                parent.reload_tabs('角色列表','<%=basePath%>role_ManageInit.action');
                parent.close_tabs('新增信息');
            });

        }else if(msg == "1000"){
            $.messager.alert('成功','修改成功!','info',function(){
                parent.reload_tabs('角色列表','<%=basePath%>role_ManageInit.action');
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
        if($("#roleName").validatebox('isValid')&&$("#roleDesc").validatebox('isValid')
           ){
            var id = $('#id').val();
            if(id !=''){
               roleEdit_form.action = "role_Upt.action";
            }else{
               roleEdit_form.action = "role_Add.action";
            }
            roleEdit_form.target= "";
            roleEdit_form.submit();
        }else{
            $.messager.alert('警告','有必填项未填','error');
        }
}

function _cancel(){
    $.messager.confirm('取消', '确定取消本次操作？', function(r){
        if (r){
            var id = $('#id').val();
            if(id !=''){
               parent.close_tabs('修改角色信息');
            }else{
               parent.close_tabs('新增角色');
            }
        }
    });
}
</script>
</head>
<body>
<form action="" method="post" name="roleEdit_form">
<div id="main_tabs">
    <fieldset>
        <legend><input type="checkbox" id="checkbox" onclick="ifNotCheck()" checked="checked" />角色信息</legend>
        <div id="jbxx">
            <table width="100%" cellpadding="3" cellspacing="5">
                <tr>
                    <input type="hidden" id="id" name="id" value="${role.id}" />
                    <input type="hidden" id="msg" name="msg" value="${msg}" />
                    <td align="right">角色名称：</td>
                    <td align="left" colspan="3"><input type="text" id="roleName" name="roleName" value="${role.roleName}" class="text easyui-validatebox" required="true" missingMessage="必填项" /></td>
                </tr>
                <tr>
                    <td align="right">角色描述：</td>
                    <td align="left" colspan="5">
                        <input type="text" id="roleDesc" name="roleDesc" value="${role.roleDesc}" class="text easyui-validatebox" required="true" missingMessage="必填项" />
                    </td>
                    </td>
                </tr>
                <tr>
                  <td align="right">是否管理员：</td>
                  <td align="left" colspan="5">
                      <s:select id="issys" name="issys" list="#{'true':'是','false':'否'}" listKey="key" listValue="value" cssClass="select" theme="simple" >
                      </s:select>
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
<script type="text/javascript">
   function d(){
   /*
     //var id=document.getElementById("issys");
     var slt=document.forms[0].elements["issys"];
     var t='true';
     if(${role.issys}==0){
       t='false';
     }
     for(var i=0;i<slt.options.length;i++)
     
      if(slt.options[i].value==t)
         slt.options[i].selected=true;
         */
       <s:if test="role.issys==false">
         document.getElementById("issys").options[1].selected=true;
       </s:if> 
   }
  d();
	
</script>

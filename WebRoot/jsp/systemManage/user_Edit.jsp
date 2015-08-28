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
<script type="text/javascript" src="<%=basePath%>js/scripts/jquery.uploadify.v2.1.0.min.js"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.v2.0.3.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploadify/swfobject.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  showMsg();
});

function ifNotCheck(){
	var c = document.getElementById("checkbox");
	var b = document.getElementById("jbxx");
  	if(c.checked){
        b.style.display='';
    } else {
    	b.style.display='none';
    }
}

  function showMsg() {
            var msg = $("#msg").val();
            if (msg != "") {
                if (msg == "0000") {
                    $.messager.alert('成功', '保存成功!', 'info', function() {
                         parent.reload_tabs('用户列表','<%=basePath%>user_ManageInit.action');
		                 parent.close_tabs('新增信息');
                    });
                }
                /*
                 else if (msg == "0001") {
                    $.messager.alert('错误', "密码不一致，请重新输入", 'error');user_ManageInit.action
                    return;
                } */
               else if(msg == "1000"){
		            $.messager.alert('成功','修改成功!','info',function(){
		                parent.reload_tabs('用户列表','<%=basePath%>user_ManageInit.action');
		                parent.close_tabs('修改信息');
		            });
               }
                else if (msg == "0002") {
                    $.messager.alert('错误', "用户名已存在，请重新输入", 'error');
                    return;
                } 
                else {
                    $.messager.alert('错误', msg, 'error');
                    return;
                }
            }
        }

 function _save() {
      if ($("#userAccount").validatebox('isValid') &&$("#userName").validatebox('isValid') &&
              $("#userPassword").validatebox('isValid') && $("#userPassword1").validatebox('isValid')
              ) {
            var id = $('#id').val();
            if($('#userPassword1').val()!=$('#userPassword').val()){
               $.messager.alert('警告', '密码不一致，请重新输入', 'error');
               return false;
            }
            if(id !=''){
                userEdit_form.action = "user_Upt.action";
            }else{
                userEdit_form.action = "user_Add.action";
            }
            userEdit_form.target = "";
            userEdit_form.submit();
      } else {
          $.messager.alert('警告', '有必填项未填或者填写错误', 'error');
      }
  }

function _cancel(){
    $.messager.confirm('取消', '确定取消本次操作？', function(r){
        if (r){
            var id = $('#id').val();
            if(id !=''){
               parent.close_tabs('修改用户信息');
            }else{
               parent.close_tabs('新增用户');
            }
        }
    });
}
</script>
</head>
<body>
<form action="" method="post" name="userEdit_form">
<div id="main_tabs">
    <fieldset>
        <legend><input type="checkbox" id="checkbox" onclick="ifNotCheck()" checked="checked" />用户信息</legend>
        <div id="jbxx">
            <table width="100%" cellpadding="3" cellspacing="5">
                <tr>
                    <input type="hidden" id="id" name="id" value="${k_user.id}" />
                    <input type="hidden" id="msg" name="msg" value="${msg}" />
                    <td align="right">帐户号：</td>
                    <td align="left" ><input type="text" id="userAccount" name="userAccount" value="${k_user.userAccount}" class="text easyui-validatebox" required="true" missingMessage="必填项" /></td>

                    <td align="right">用户名：</td>
                    <td align="left">
                        <input type="text" id="userName" name="userName" value="${k_user.userName}" class="text easyui-validatebox" required="true" missingMessage="必填项" />
                    </td>
                </tr>

                <tr>
                    <td align="right">密码：</td>
                    <td align="left"><input type="password" id="userPassword" name="userPassword" value="${k_user.userPassword}" class="text easyui-validatebox" required="true" missingMessage="必填项" /></td>
                     <td align="right">确认密码：</td>
                    <td align="left">
                        <input type="password" id="userPassword1" name="userPassword1" value="${k_user.userPassword}" class="text easyui-validatebox" required="true" missingMessage="必填项" />
                    </td>
                </tr>

                 <tr>
                    <td align="right">用户所在单位：</td>
                    <td align="left" ><input type="text" id="userDept" name="userDept" value="${k_user.userDept}" class="text easyui-validatebox"/></td>
                    <td align="right">用户职位：</td>
                    <td align="left">
                        <input type="text" id="userDuty" name="userDuty" value="${k_user.userDuty}" class="text easyui-validatebox" />
                    </td>
                </tr>
                 <tr>
                    <td align="right">用户备注：</td>
                    <td align="left">
                        <input type="text" id="userDesc" name="userDesc" value="${k_user.userDesc}" class="text easyui-validatebox" />
                    </td><%--

                   <td align="right">是否是超级用户：</td>
                  <td align="left">
                      <s:select id="issys" name="issys" list="#{'true':'是','false':'否'}" listKey="key" listValue="value" cssClass="select" theme="simple" >
                      </s:select>
                  </td>
                --%></tr>

                 <tr>
                   <td align="right">是否可用：</td>
                  <td align="left" colspan="5">
                      <s:select id="enabled" name="enabled" list="#{'true':'是','false':'否'}" listKey="key" listValue="value" cssClass="select" theme="simple" >
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
       <s:if test="k_user.issys==false">
         document.getElementById("issys").options[1].selected=true;
       </s:if> 
        <s:if test="k_user.enabled==false">
         document.getElementById("enabled").options[1].selected=true;
       </s:if> 
   }
  d();
</script>
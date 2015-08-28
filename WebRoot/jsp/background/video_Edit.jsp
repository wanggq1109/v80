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
<title>微电影</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.v2.0.3.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploadify/swfobject.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    showMsg();
    file_init(1);
});

function file_init(id){
   $("#file_upload1").uploadify({
        'uploader'       : '<%=basePath%>js/uploadify/uploadify.swf',
        'script'         : 'myjson/videoInfoShowUpload.action',
        'cancelImg'      : '<%=basePath%>js/uploadify/cancel.png',
        'folder'         : 'VideoUploads',
        'queueID'        : 'fileQueue1',
        'fileDataName'   : 'file_upload1',
        'auto'           : false,
        'multi'          : false,
        'simUploadLimit' : 1,
        'buttonText'     : '浏览',
        'height'         : 22,
        'width'          : 70,
        'buttonImg'      : '<%=basePath%>js/uploadify/browse.png',
        'fileDesc'       : '请选择文件',
        'fileExt'        : '*.map4;*.MP4;*.avi;',
        'sizeLimit'      : 0,
        'sizeMax'        : 307200000,
        'onSelect'       : function(e,queueId,fileObj)
        {
            if(fileObj.size>307200000){
               $.messager.alert("错误","上传的图片不能大于300兆，请重新选择","error");
               $("#btnUpload1"+id).attr("disabled","disabled");
            }
        },
        'onComplete'	 : function(event,queueId,fileObj,response,data){
            var msg = response.substr(0,4);
            if(msg =="0000"){
                $.messager.alert("成功","上传成功!","info",function (){
                	
                     $("#url").val(response.substr(4));
                 });
            }else{
               $.messager.alert("错误",response,"error");
            }
        }
    });
}

function showMsg(){
    var msg = $("#msg").val();
    if(msg != ""){
        if(msg == "0000"){
            $.messager.alert("成功","新增成功!","info",function(){
                parent.reload_tabs("微电影","<%=basePath%>video_init.action");
                parent.close_tabs("新增微电影");
            });

        }else if(msg == "1000"){
            $.messager.alert("成功","修改成功!","info",function(){
                parent.reload_tabs("微电影","<%=basePath%>video_init.action");
                parent.close_tabs("修改微电影");
            });
        }else{
            $.messager.alert("错误",msg,"error");
            return;
        }
    }
}

function _save(){
  		 var filmType= $("#filmtype").val();
        if($("#url").val()==""){
            $.messager.alert("警告","请上传微电影文件","error");
            return;
        }
        var id = $("#id").val();
        if(id !=""){
           videoEdit_form.action = "videoInfo_edit_save.action";
        }else{
           videoEdit_form.action = "videoInfo_save.action?videoType="+filmType+"";
        }
        videoEdit_form.target= "";
        videoEdit_form.submit();
    
}

function _cancel(){
    $.messager.confirm("取消", "确定取消本次操作？", function(r){
        if (r){
            window.location.href="<%=basePath%>video_init.action";
        }
    });
}
</script>
</head>
<body>
<form action="" method="post" name="videoEdit_form">
<div id="main_tabs" style="padding:10px">
    <fieldset>
        <legend style="font-size:14px">微电影信息</legend>
        <div id="jbxx">
            <table width="100%" cellpadding="3" cellspacing="5">
            	<tr>
                    <td align="right">微电影名称：</td>
                    <td align="left" colspan="3"><input type="text" id="name" name="name" value="${videoinfo.name}" class="text easyui-validatebox" required="true" missingMessage="必填项" /></td>
                </tr>
                <tr>
                    <td align="right">拍摄日期：</td>
                    <td align="left" colspan="3"><input type="text" id="shootingDate" name="shootingDate" value="${videoinfo.shootingDate}" onclick="WdatePicker()" class="Wdate text easyui-validatebox" required="true" missingMessage="必填项" /></td>
                </tr>
            	<tr>
            		<td align="right">类型：</td>
                    <td align="left" colspan="3">
                    	<select id="filmtype" name="" style="width: 212px; height: 35px;">
             	 			<option value="0">微电影</option>
             	 			<option value="1">花絮</option>
             	 		</select>
                    </td>
            	</tr>
                <tr>
                    <input type="hidden" id="id" name="id" value="${id}" />
                    <input type="hidden" id="msg" name="msg" value="${msg}" />
                </tr>
                <tr>
                    <td align="right">简介：</td>
                    <td align="left" colspan="5">
                        <textarea cols="80" rows="4" id="intro" name="intro">${videoinfo.intro}</textarea>
                    </td>
                    </td>
                </tr>
                <tr>
                    <td align="right">请选择文件：</td>
                    <td align="left" colspan="3">
                        <div id="fileQueue1"></div>
                        <input type="text" id="url" name="url" value="${videoinfo.url}" readonly="readonly" class="text_file" />
                        <input type="file" id="file_upload1" name="file_upload1" />
                        <input type="button" id="btnUpload1" onclick="$('#file_upload1').uploadifyUpload()" value="上传">
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
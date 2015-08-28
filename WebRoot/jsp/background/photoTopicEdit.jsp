<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*,java.text.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	String id = (String)request.getAttribute("id");
	String code = (String)request.getAttribute("code");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>作品赏析编辑图片</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.v2.0.3.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploadify/swfobject.js"></script>
<style>
#photoTopic_Edit img{border:3px solid #eeeeee;width:100px;height:75px;}
#enlarge_images{position:absolute;display:none;z-index:2;}
</style>
<script>
	$(function() {
		var id = "<%=id%>";
	 	table_init(id);
	});
	
	function file_init(id){
	    $("#file_upload1").uploadify({
	        'uploader'       : '<%=basePath%>js/uploadify/uploadify.swf',
	        'script'         : 'myjson/photoworkssuolue_picture.action',
	        'scriptData'     : {'id':id},
	        'cancelImg'      : '<%=basePath%>js/uploadify/cancel.png',
	        'folder'         : 'suolue_pic',
	        'queueID'        : 'fileQueue1',
	        'fileDataName'   : 'file_upload',
	        'auto'           : false,
	        'multi'          : false,
	        'simUploadLimit' : 1,
	        'buttonText'     : '浏览',
	        'height'         : 22,
	        'width'          : 70,
	        'buttonImg'      : '<%=basePath%>js/uploadify/browse.png',
	        'fileDesc'       : '请选择文件',
	        'fileExt'        : '*.jpg;*.png;*.gif;*.jpeg',
	        'sizeLimit'      : 0,
	        'sizeMax'        : 10240000,
	        'onSelect'       : function(e,queueId,fileObj){
	            //if(fileObj.size>10240000){
	            //   $.messager.alert('错误',"上传的图片不能大于10兆，请重新选择",'error');
	            //   $("#btnUpload"+id).attr('disabled','disabled');
	            //}
	        },'onComplete'	 : function(event,queueId,fileObj,response,data){
	            var msg = response.substr(0,4);
	            if(msg =="0000"){
	                $.messager.alert("成功","上传成功!","info",function (){
	                     $("#url").val(response.substr(4));
	                     $("#photoworks_table").datagrid("reload");
						 $("#div_brev").dialog("close");
	                 });
	            }else{
	               $.messager.alert("错误",response,"error");
	            }
	        }
	    });
	}
	var codeId = "<%=code%>";

	function table_init(id) {
		$("#photoTopic_Edit").datagrid({
			url : "myjson/photoWorksDataList.action?id="+ id,
			pagination : true,
			nowrap : false,
			striped : true,
			collapsible : true,
			loadMsg : "数据装载中,请稍后......",
			sortName : "id",
			sortOrder : "asc",
			remoteSort : true,
			fitColumns : true,
			idField : "id",
			height : 475,
			frozenColumns : [ [ { field : "ck", checkbox : true } ] ],
			columns : [ [
				{ field : "breviaryName", title : "缩略图", width : 50, align : "center",
                	formatter : function(value, row, index) {
               			return "<img src='" + row.breviaryName + "' height='60' />";
					}},
				{ field : "photoType", title : "作品类型", width : 80, align : "center" },
				{ field : "uploadTime", title : "原图上传时间", width : 80, align : "center" },
				{ field : "breviaryUploadTime", title : "缩略图上传时间", width : 80, align : "center" },
				{ field : "releaseStatus", title : "发布状态", width : 40, align : "center",
                	formatter : function(value, row, index) {
               			if(value == "已发布"){
							return "<span style='color:#75B468;'>已发布</span>";
						} else {
							return "<span style='color:red;'>未发布</span>";
						}
					}},
				{ field : "weekrecommend", title : "推荐状态", width : 40, align : "center",
                	formatter : function(value, row, index) {
               			if(value == "已推荐"){
							return "<span style='color:#75B468;'>已推荐</span>";
						} else {
							return "<span style='color:red;'>未推荐</span>";
						}
					}},
				{ field : "opa", title : "操作", width : 50, align : "center",
					formatter : function(value, row, index) {
						return "<a class='upload' href='javascript:add_brev(" + row.id + ")'>上传缩略图</a>";
					}
				} ] ],
			toolbar : [ {
				text : "整体发布",
				handler : function() {
					_all_release();
				}
			}, "-", {
				text : "选择性发布",
				handler : function() {
					_release();
				}
			}, "-", {
				text : "一周推荐",
				handler : function() {
					set_week();
				}
			}, "-", {
				text : "设置封面",
				handler : function() {
					set_cover();
				}
			}, "-", {
				text : "删除",
				iconCls : "icon-remove",
				handler : function() {
					_del();
				}
			}, "-", {
				text : "刷新",
				iconCls : "icon-reload",
				handler : function() {
					window.location.reload();
				}
			} ]
		});
		showPager();
	}

	function showPager() {
		$("#photoTopic_Edit").datagrid("getPager").pagination({
			pageSize : 10,
			showPageList : false,
			showRefresh : false,
			displayMsg : "当前显示从{from}到{to}条，共{total}条记录",
			beforePageText : "第",
			afterPageText : "页，共{pages}页"
		});
	}

	// 整体发布
	function _all_release(){
		$.messager.confirm("发布", "确定发布所选中记录？", function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : "myjson/photoWorks_release_all.action",
					data : "code=" + codeId,
					success : function(msg, status) {
						if ("001" == msg) {
							$.messager.alert("成功", "发布成功!", "info", function() {
								$("#photoTopic_Edit").datagrid("reload");
							});
						} else {
							$.messager.alert("错误", "发布失败!", "error");
						}
					}
				});
			}
		});
	}
	
	// 选择性发布
	function _release() {
		var row = $("#photoTopic_Edit").datagrid("getSelected");
		if (row) {
			$.messager.confirm("发布", "确定发布所选中记录？", function(r) {
				if (r) {
					var ids = [];
					var rows = $("#photoTopic_Edit").datagrid("getSelections");
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					id = ids.join(",");
					$.ajax({
						type : "POST",
						url : "myjson/photoWorks_release.action",
						data : "id=" + id,
						success : function(msg, status) {
							if ("001" == msg) {
								$.messager.alert("成功", "发布成功!", "info", function() {
									$("#photoTopic_Edit").datagrid("reload");
								});
							} else {
								$.messager.alert("错误", "发布失败!", "error");
							}
						}
					});
					$("#photoTopic_Edit").datagrid("clearSelections");
				}
			});
		} else {
			$.messager.alert("发布", "请选择要发布的记录", "warning");
		}
	}
	
	// 设置一周推荐
	function set_week(){
		var row = $("#photoTopic_Edit").datagrid("getSelected");
		if (row) {
			$.messager.confirm("设置", "确定设置为一周推荐？", function(r) {
				if (r) {
					var ids = [];
					var rows = $("#photoTopic_Edit").datagrid("getSelections");
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					id = ids.join(",");
					$.ajax({
						type : "POST",
						url : "myjson/photoWorks_weekrecommend.action",
						data : "id=" + id,
						success : function(msg, status) {
							if ("001" == msg) {
								$.messager.alert("成功", "设置成功!", "info", function() {
									$("#photoTopic_Edit").datagrid("reload");
								});
							} else {
								$.messager.alert("错误", "发布失败!", "error");
							}
						}
					});
					$("#photoTopic_Edit").datagrid("clearSelections");
				}
			});
		} else {
			$.messager.alert("设置", "请选择要设置的记录", "warning");
		}
	}
	
	// 设置封面
	function set_cover(){
		var rows = $("#photoTopic_Edit").datagrid("getSelected");
		var rowsLength = $("#photoTopic_Edit").datagrid("getSelections");
        if(rowsLength.length>1){
            $.messager.alert("设置","只能选择一条记录","warning");
        } else if(rowsLength.length==0){
            $.messager.alert("设置","请选择要设置的记录","warning");
        } else {
            if(rows){
                $.ajax({
					type : "POST",
					url : "myjson/photoworksedit_facephots.action",
					data : "id=" + rows.id,
					success : function(msg, status) {
						if ("001" == msg) {
							$.messager.alert("成功", "设置成功!", "info", function() {
								$("#photoTopic_Edit").datagrid("reload");
							});
						} else {
							$.messager.alert("错误", "设置失败!", "error");
						}
					}
				});
            }
        }
	}
	
	// 添加缩略图
	function add_brev(id) {
		file_init(id);
		$("#div_brev").show();
		$("#div_brev").dialog({
			modal : true,
			iconCls : "icon-add"
		});
	}
	
	// 删除
	function _del() {
		var row = $("#photoTopic_Edit").datagrid("getSelected");
		if (row) {
			$.messager.confirm("删除", "确定删除？", function(r) {
				if (r) {
					var ids = [];
					var rows = $("#photoTopic_Edit").datagrid("getSelections");
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					id = ids.join(",");
					$.ajax({
						type : "POST",
						url : "myjson/photoWorks_delete.action",
						data : "id=" + id,
						success : function(msg, status) {
							if ("001" == msg) {
								$.messager.alert("成功", "删除成功!", "info", function() {
									window.location.reload();
								});
							} else{
								
								$.messager.alert("错误", "删除失败!", "error");
							}
						}
					});
					$("#photoTopic_Edit").datagrid("clearSelections");
				}
			});
		} else {
			$.messager.alert("删除", "请选择要删除的行", "warning");
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'" style="padding:2px 2px 2px 2px;" border="false">
		<table id="photoTopic_Edit" fit="true"></table>
	</div>
	<div id="div_brev" title="添加缩略图" style="padding:10px;width:650px;height:200px;display:none;overflow:hidden;">
		<form action="" method="post" name="suoluePic_form">
	        <div id="uploadslp">
	            <table width="100%" cellpadding="3" cellspacing="5">
	                <tr>
	                    <td align="right">请选择缩略图：</td>
	                    <td align="right">
	                        <div id="fileQueue1"></div>
	                        <input type="text" id="url" name="url" value="" readonly="readonly" class="text_file" />
	                        <input type="file" id="file_upload1" name="file_upload" />
	                    </td>
	                    <td align="left">
                            <input type="button" id="btnUpload1" onclick="$('#file_upload1').uploadifyUpload()" value="上传">
	                    </td>
	                </tr>
	            </table>
	        </div>
		</form>
	</div>
	<div id="enlarge_images"></div>
</body>
<script>
	var demo = document.getElementById("photoTopic_Edit");
	var gg = demo.getElementsByTagName("img");
	var ei = document.getElementById("enlarge_images");
	for(i=0; i<gg.length; i++){
		var ts = gg[i];
		document.getElementsByTagName("img").onmouseover = function(event){
			event = event || window.event;
			ei.style.display = "block";
			ei.innerHTML = '<img src="' + this.src + '" />';
			ei.style.top = document.body.scrollTop + event.clientY + 10 + "px";
			ei.style.left = document.body.scrollLeft + event.clientX + 10 + "px";
		};
		document.getElementsByTagName("img").onmouseout = function(){
			ei.innerHTML = "";
			ei.style.display = "none";
		};
	}
</script>
</html>


